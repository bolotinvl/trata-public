package com.bolotin.trata.ui.currency;

import com.bolotin.trata.ui.base.MvpPresenter;

public interface CurrencyMvpPresenter<V extends CurrencyMvpView> extends MvpPresenter<V> {

    void onViewPrepared();

    void updateCurrencySymbol(String currencySymbol);

    void updateCurrencyCode(String currencyCode);

    void disableFirstRun();

    void initializeCategories();
}
