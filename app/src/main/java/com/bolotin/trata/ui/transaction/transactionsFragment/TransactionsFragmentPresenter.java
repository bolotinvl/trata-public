package com.bolotin.trata.ui.transaction.transactionsFragment;

import com.bolotin.trata.data.DataManager;
import com.bolotin.trata.ui.base.BasePresenter;
import com.bolotin.trata.utils.DateTimeConverter;
import com.bolotin.trata.utils.rx.SchedulerProvider;

import java.text.ParseException;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class TransactionsFragmentPresenter<V extends TransactionsFragmentMvpView> extends BasePresenter<V>
        implements TransactionsFragmentMvpPresenter<V> {

    @Inject
    TransactionsFragmentPresenter(DataManager dataManager,
                                  SchedulerProvider schedulerProvider,
                                  CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewPrepared(String date) {
        setObservableToTransactionsInDb(date);
        updateCategoriesUI();
    }

    @Override
    public String getMode() {
        return getDataManager().getMode();
    }

    @Override
    public String getCurrency() {
        return getDataManager().getCurrencySymbol();
    }

    private void setObservableToTransactionsInDb(String date) {
        try {
            getCompositeDisposable().add(getDataManager()
                    .getTransactionsByMonth(DateTimeConverter.formatYearMonth(DateTimeConverter.parseFullMonth(date)))
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(transactions -> getMvpView().updateTransactions(transactions)));

        } catch (ParseException e) {
            e.getMessage();
        }
    }

    private void updateCategoriesUI() {
        getCompositeDisposable().add(getDataManager().getCategories()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(categories -> getMvpView().updateCategories(categories)));
    }
}