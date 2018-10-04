package com.bolotin.trata.ui.transaction;

import com.bolotin.trata.ui.base.MvpPresenter;

public interface TransactionsMvpPresenter<V extends TransactionsMvpView> extends MvpPresenter<V> {

    Boolean getFirstRun();

    void onViewPrepared();

    String getCurrencySymbol();

    void updateSelectedCategoryId(String selectedCategoryId);

    void addTransaction(String note, String value, Double latitude, Double longitude);

    void getSumAndUpdateView();
}
