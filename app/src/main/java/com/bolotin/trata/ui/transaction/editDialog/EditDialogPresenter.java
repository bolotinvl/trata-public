package com.bolotin.trata.ui.transaction.editDialog;

import com.bolotin.trata.data.DataManager;
import com.bolotin.trata.data.db.model.Transaction;
import com.bolotin.trata.ui.base.BasePresenter;
import com.bolotin.trata.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class EditDialogPresenter<V extends EditDialogMvpView>
        extends BasePresenter<V> implements EditDialogMvpPresenter<V> {


    @Inject
    public EditDialogPresenter(DataManager dataManager,
                               SchedulerProvider schedulerProvider,
                               CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void deleteTransaction(String transactionId) {
        getCompositeDisposable().add(getDataManager().getTransactionById(transactionId)
                .firstOrError()
                .subscribeOn(getSchedulerProvider().io())
                .subscribe(transaction -> {
                    transaction.setActive(false);
                    setTransactionInactiveInDb(transaction);
                }));
    }

    private void setTransactionInactiveInDb(Transaction transaction) {
        getCompositeDisposable().add(getDataManager().updateTransaction(transaction)
                .subscribeOn(getSchedulerProvider().io())
                .subscribe());
    }
}