package com.bolotin.trata.ui.settings;

import com.bolotin.trata.ui.base.MvpView;

public interface SettingsMvpView extends MvpView {

    void showProgressBar();

    void hideProgressBar();

    void showSuccessfulExportSnackbar();

    void showUnsuccessfulExportSnackbar();

    void showSuccessfulImportSnackbar();

    void showUnsuccessfulImportSnackbar();
}
