package com.bolotin.trata.ui.transaction.transactionsFragment;

import com.bolotin.trata.ui.base.MvpPresenter;

public interface TransactionsFragmentMvpPresenter<V extends TransactionsFragmentMvpView>
        extends MvpPresenter<V> {

    void onViewPrepared(String date);

    String getMode();

    String getCurrency();
}
