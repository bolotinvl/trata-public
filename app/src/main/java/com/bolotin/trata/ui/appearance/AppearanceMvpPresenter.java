package com.bolotin.trata.ui.appearance;

import com.bolotin.trata.ui.base.MvpPresenter;

public interface AppearanceMvpPresenter<V extends AppearanceMvpView> extends MvpPresenter<V> {

    String getMode();

    void updateTheme(String theme);

    void updateMode(String mode);
}
