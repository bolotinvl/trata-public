package com.bolotin.trata.ui.base;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.bolotin.trata.R;
import com.bolotin.trata.TrataApplication;
import com.bolotin.trata.di.component.ActivityComponent;
import com.bolotin.trata.di.component.DaggerActivityComponent;
import com.bolotin.trata.di.module.ActivityModule;
import com.bolotin.trata.utils.AppConstants;

import javax.inject.Inject;

import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity implements MvpView {

    @Inject
    MvpPresenter<MvpView> presenter;

    private ActivityComponent activityComponent;

    private Unbinder unBinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activityComponent = DaggerActivityComponent.builder()
                .activityModule(new ActivityModule(this))
                .applicationComponent(((TrataApplication) getApplication()).getComponent())
                .build();

        activityComponent.inject(this);

        setTheme();
    }

    @Override
    protected void onDestroy() {
        if (unBinder != null) {
            unBinder.unbind();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @Override
    public void hideKeyboard() {
        View view = getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view != null && imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    public void setUnBinder(Unbinder unBinder) {
        this.unBinder = unBinder;
    }

    protected void setUp(Toolbar toolbar) {
        setSupportActionBar(toolbar);
    }

    protected void showSnackBar(String message) {
        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content),
                message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    protected void requestPermissionsSafely(String[] permissions, int requestCode) {
        ActivityCompat.requestPermissions(this, permissions, requestCode);
    }

    protected boolean hasPermission(String permission) {
        return ContextCompat.checkSelfPermission(getApplicationContext(),
                permission) == PackageManager.PERMISSION_GRANTED;
    }

    private void setTheme() {
        String theme = presenter.getTheme();
        switch (theme) {
            case AppConstants.DEFAULT_THEME:
                setTheme(R.style.AppTheme_Default);
                break;
            case AppConstants.DARK_THEME:
                setTheme(R.style.AppTheme_Dark);
                break;
        }
    }
}