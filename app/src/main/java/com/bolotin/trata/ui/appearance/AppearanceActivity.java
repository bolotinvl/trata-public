package com.bolotin.trata.ui.appearance;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bolotin.trata.R;
import com.bolotin.trata.ui.base.BaseActivity;
import com.bolotin.trata.ui.settings.SettingsActivity;
import com.bolotin.trata.utils.AppConstants;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AppearanceActivity extends BaseActivity implements AppearanceMvpView{

    @Inject
    AppearanceMvpPresenter<AppearanceMvpView> presenter;

    @BindView(R.id.appearance_toolbar)
    Toolbar toolbar;

    @BindView(R.id.default_theme_tick)
    ImageView defaultThemeTick;

    @BindView(R.id.dark_theme_tick)
    ImageView darkThemeTick;

    @BindView(R.id.default_mode_tick)
    ImageView defaultModeTick;

    @BindView(R.id.compact_mode_tick)
    ImageView compactModeTick;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, AppearanceActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_appearance);

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
        startActivity(SettingsActivity.getStartIntent(this));
        super.onBackPressed();
    }

    @Override
    protected void setUp(Toolbar toolbar) {
        changeThemeTick();
        changeModeTick();
        super.setUp(toolbar);
    }

    @OnClick(R.id.default_theme_layout)
    void onDefaultThemeClick() {
        presenter.updateTheme(AppConstants.DEFAULT_THEME);
        recreate();
    }

    @OnClick(R.id.dark_theme_layout)
    void onDarkThemeClick() {
        presenter.updateTheme(AppConstants.DARK_THEME);
        recreate();
    }

    @OnClick(R.id.default_mode_layout)
    void onDefaultModeClick() {
        presenter.updateMode(AppConstants.DEFAULT_MODE);
        changeModeTick();
    }

    @OnClick(R.id.compact_mode_layout)
    void onCompactModeClick() {
        presenter.updateMode(AppConstants.COMPACT_MODE);
        changeModeTick();
    }

    @OnClick(R.id.appearance_back_button)
    void onBackButtonClick() {
        onBackPressed();
    }

    private void changeThemeTick() {
        switch (presenter.getTheme()) {
            case AppConstants.DEFAULT_THEME:
                darkThemeTick.setVisibility(View.INVISIBLE);
                break;
            case AppConstants.DARK_THEME:
                defaultThemeTick.setVisibility(View.INVISIBLE);
                break;
        }
    }

    private void changeModeTick() {
        switch (presenter.getMode()) {
            case AppConstants.DEFAULT_MODE:
                defaultModeTick.setVisibility(View.VISIBLE);
                compactModeTick.setVisibility(View.INVISIBLE);
                break;
            case AppConstants.COMPACT_MODE:
                compactModeTick.setVisibility(View.VISIBLE);
                defaultModeTick.setVisibility(View.INVISIBLE);
                break;
        }
    }
}
