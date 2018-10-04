package com.bolotin.trata.ui.settings;

import android.net.Uri;

import com.bolotin.trata.ui.base.MvpPresenter;

public interface SettingsMvpPresenter<V extends SettingsMvpView> extends MvpPresenter<V> {

    void exportDB(Uri uri);

    void importDB(Uri uri);

    String getExportFileName();

    boolean hasExported();
}
