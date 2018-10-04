package com.bolotin.trata.ui.settings;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.bolotin.trata.R;
import com.bolotin.trata.ui.transaction.TransactionsActivity;
import com.bolotin.trata.ui.appearance.AppearanceActivity;
import com.bolotin.trata.ui.base.BaseActivity;
import com.bolotin.trata.ui.category.settingsCategory.SettingsCategoryActivity;
import com.bolotin.trata.ui.currency.normal.NormalCurrencyActivity;
import com.bolotin.trata.utils.AppConstants;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends BaseActivity implements SettingsMvpView {

    private static final int READ_REQUEST_CODE = 42;
    private static final int WRITE_REQUEST_CODE = 43;

    @Inject
    SettingsMvpPresenter<SettingsMvpView> presenter;

    @BindView(R.id.settings_toolbar)
    Toolbar toolbar;

    @BindView(R.id.settings_progress_bar)
    ProgressBar progressBar;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SettingsActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_settings);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        presenter.onAttach(this);

        setUp(toolbar);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        startActivity(TransactionsActivity.getStartIntent(this));
        super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == WRITE_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
            }
            presenter.exportDB(uri);
        }

        if (requestCode == READ_REQUEST_CODE && resultCode == RESULT_OK) {
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
            }
            presenter.importDB(uri);
        }
    }

    @OnClick(R.id.settings_import_layout)
    void onImportDataClick() {
        searchDBFiles();
    }

    @OnClick(R.id.settings_export_layout)
    void onExportDataClick() {
        createDBFile(presenter.getExportFileName());
    }

    @OnClick(R.id.settings_categories_layout)
    void onCategoriesClick() {
        Intent categoryIntent = SettingsCategoryActivity.getStartIntent(this);
        categoryIntent.putExtra("mode", "fromSettings");
        startActivity(categoryIntent);
    }

    @OnClick(R.id.settings_currency_layout)
    void onCurrencyClick() {
        startActivity(NormalCurrencyActivity.getStartIntent(this));
    }

    @OnClick(R.id.settings_appearance_layout)
    void onAppearanceClick() {
        startActivity(AppearanceActivity.getStartIntent(this));
    }

    @OnClick(R.id.settings_back_button)
    void onBackButtonClick() {
        onBackPressed();
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    @Override
    public void showSuccessfulExportSnackbar() {
        showSnackBar(getString(R.string.export_data_successful));
    }

    @Override
    public void showUnsuccessfulExportSnackbar() {
        showSnackBar(getString(R.string.export_data_error));
    }

    @Override
    public void showSuccessfulImportSnackbar() {
        showSnackBar(getString(R.string.import_data_successful));
    }

    @Override
    public void showUnsuccessfulImportSnackbar() {
        showSnackBar(getString(R.string.import_data_error));
    }

    //Creates database file using Storage Access Framework
    private void createDBFile(String fileName) {
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType(AppConstants.DB_MIME_TYPE);
        intent.putExtra(Intent.EXTRA_TITLE, fileName);
        startActivityForResult(intent, WRITE_REQUEST_CODE);
    }

    private void searchDBFiles() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.setType("*/*");
        startActivityForResult(intent, READ_REQUEST_CODE);
    }
}