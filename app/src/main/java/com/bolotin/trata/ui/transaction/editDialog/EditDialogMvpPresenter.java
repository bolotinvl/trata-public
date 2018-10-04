package com.bolotin.trata.ui.transaction.editDialog;

import com.bolotin.trata.ui.base.MvpPresenter;

public interface EditDialogMvpPresenter<V extends EditDialogMvpView>
        extends MvpPresenter<V> {

    void deleteTransaction(String transactionId);
}