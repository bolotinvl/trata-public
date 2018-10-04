package com.bolotin.trata.ui.transaction;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.PorterDuff;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bolotin.trata.R;
import com.bolotin.trata.ui.analytics.AnalyticsMenuActivity;
import com.bolotin.trata.ui.base.BaseActivity;
import com.bolotin.trata.ui.category.selectCategory.SelectCategoryActivity;
import com.bolotin.trata.ui.currency.firstLaunch.FirstLaunchCurrencyActivity;
import com.bolotin.trata.ui.settings.SettingsActivity;
import com.bolotin.trata.ui.transaction.transactionsFragment.TransactionsAdapter;
import com.bolotin.trata.utils.AppConstants;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.tasks.Task;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TransactionsActivity extends BaseActivity implements TransactionsMvpView {

    private static final int PICK_CATEGORY_REQUEST = 0;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 99;

    @Inject
    TransactionsMvpPresenter<TransactionsMvpView> presenter;

    @Inject
    LinearLayoutManager layoutManager;

    @Inject
    TransactionsAdapter adapter;

    @Inject
    TransactionsPagerAdapter pagerAdapter;

    @Inject
    FusedLocationProviderClient fusedLocationProviderClient;

    @BindView(R.id.transactions_toolbar)
    Toolbar toolbar;

    @BindView(R.id.transactions_tab_layout)
    TabLayout tabLayout;

    @BindView(R.id.transactions_view_pager)
    CustomViewPager viewPager;

    @BindView(R.id.transactions_sum_text_view)
    TextView sumTextView;

    @BindView(R.id.transaction_select_category_button)
    ImageView selectCategoryButton;

    @BindView(R.id.transactions_note_edit_view)
    EditText noteView;

    @BindView(R.id.transactions_value_edit_view)
    EditText value;

    private Location lastKnownLocation;
    private boolean hasLocationPermission;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, TransactionsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);

        presenter.onAttach(this);

        if (presenter.getFirstRun()) {
            startActivity(FirstLaunchCurrencyActivity.getStartIntent(this));
        }

        setContentView(R.layout.activity_transactions);

        setUnBinder(ButterKnife.bind(this));

        setUp(toolbar);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp(Toolbar toolbar) {
        super.setUp(toolbar);

        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(5);
        tabLayout.setupWithViewPager(viewPager);

        setThemeToSelectCategoryButton();
        addOnEditTextListener();
        setHintToValueView();
        getLocationPermission();

        presenter.onViewPrepared();

        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                presenter.getSumAndUpdateView();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    @Override
    public void updatePagerData(List<String> months) {
        pagerAdapter.setMonths(months);
    }

    @Override
    public void updateSumView(String sum) {
        sumTextView.setText(sum);
    }

    @Override
    public void setIconToSelectCategoryButton(String categoryIcon) {
        selectCategoryButton.setImageResource(
                getResources().getIdentifier(categoryIcon, "drawable", getPackageName()));
    }

    @Override
    public String getAmountString() {
        return getString(R.string.amount_with_currency);
    }

    @Override
    public int getSelectedTabPosition() {
        return tabLayout.getSelectedTabPosition();
    }

    @Override
    public void onAddTransaction(String month) {
        viewPager.setCurrentItem(pagerAdapter.getMonthPosition(month));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_CATEGORY_REQUEST && resultCode == RESULT_OK) {
            presenter.updateSelectedCategoryId(data.getStringExtra("categoryId"));

            EditText editNoteView = findViewById(R.id.transactions_note_edit_view);
            editNoteView.requestFocus();

            adapter.notifyDataSetChanged();

            hideKeyboard();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        hasLocationPermission = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    hasLocationPermission = true;
                }
            }
        }
    }

    @OnClick(R.id.add_transaction_button)
    void onAddTransactionButtonClick() {
        presenter.addTransaction(
                noteView.getText().toString(),
                value.getText().toString(),
                lastKnownLocation == null ? null : lastKnownLocation.getLatitude(),
                lastKnownLocation == null ? null : lastKnownLocation.getLongitude());

        noteView.setText("");
        value.setText("");
        noteView.requestFocus();
        hideKeyboard();
    }

    @OnClick(R.id.transaction_select_category_button)
    void onSelectCategoryButtonClick() {
        Intent intent = SelectCategoryActivity.getStartIntent(this);
        intent.putExtra("mode", "fromTransactions");
        startActivityForResult(intent, PICK_CATEGORY_REQUEST);
    }

    @OnClick(R.id.settings_button)
    void onSettingsButtonClick() {
        startActivity(SettingsActivity.getStartIntent(this));
        finish();
    }

    @OnClick(R.id.reports_button)
    void onReportsClickButton() {
        startActivity(AnalyticsMenuActivity.getStartIntent(this));
    }

    private void setThemeToSelectCategoryButton() {
        if (presenter.getTheme().contentEquals(AppConstants.DEFAULT_THEME)) {
            selectCategoryButton.setColorFilter(
                    getResources().getColor(R.color.bottomBarCategoryIconColorDefault), PorterDuff.Mode.SRC_IN);
        } else {
            selectCategoryButton.setColorFilter(
                    getResources().getColor(R.color.bottomBarCategoryIconColorDark), PorterDuff.Mode.SRC_IN);
        }
        selectCategoryButton.setBackground(getResources().getDrawable(R.drawable.round_corner, getTheme()));
    }

    private void setHintToValueView() {
        value.setHint("0 " + presenter.getCurrencySymbol());
    }

    private void addOnEditTextListener() {
        noteView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (hasLocationPermission) {
                    updateCurrentLocation();
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private void getLocationPermission() {
        hasLocationPermission = hasPermission(android.Manifest.permission.ACCESS_FINE_LOCATION);
        if (!hasLocationPermission) {
            requestPermissionsSafely(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    private void updateCurrentLocation() {
        try {
            if (hasLocationPermission) {
                Task<Location> locationResult = fusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, task -> {
                    if (task.isSuccessful() && task.getResult() != null) {
                        lastKnownLocation = task.getResult();
                    }
                });
            }
        } catch (SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }
}