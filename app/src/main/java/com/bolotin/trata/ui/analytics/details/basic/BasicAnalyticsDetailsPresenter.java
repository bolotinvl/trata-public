package com.bolotin.trata.ui.analytics.details.basic;

import com.bolotin.trata.data.DataManager;
import com.bolotin.trata.ui.base.BasePresenter;
import com.bolotin.trata.utils.DateTimeConverter;
import com.bolotin.trata.utils.rx.SchedulerProvider;

import java.util.Date;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class BasicAnalyticsDetailsPresenter<V extends BasicAnalyticsDetailsMvpView> extends BasePresenter<V>
        implements BasicAnalyticsDetailsMvpPresenter<V> {

    @Inject
    public BasicAnalyticsDetailsPresenter(DataManager dataManager,
                                          SchedulerProvider schedulerProvider,
                                          CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewPrepared(String categoryId, String startDate, String endDate) {
        updateTransactionsUI(categoryId, startDate, endDate);
        updateCategoriesUI();
    }

    @Override
    public String formatDateToTitle(Date date) {
        return DateTimeConverter.formatDayOfMonth(date);
    }

    @Override
    public String formatDateToDb(Date date) {
        return DateTimeConverter.formatAnalytics(date);
    }

    private void updateTransactionsUI(String categoryId, String startDate, String endDate) {
        getCompositeDisposable().add(getDataManager()
                .getTransactionsByCategoryAndDate(categoryId, startDate, endDate)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(transactions -> getMvpView().updateTransactions(transactions)));
    }

    private void updateCategoriesUI() {
        V mvpView = getMvpView();
        getCompositeDisposable().add(getDataManager().getCategories()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(categories -> getMvpView().updateCategories(categories)));
    }
}
