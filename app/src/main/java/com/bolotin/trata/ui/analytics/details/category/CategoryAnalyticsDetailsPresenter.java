package com.bolotin.trata.ui.analytics.details.category;

import android.util.Log;

import com.bolotin.trata.data.DataManager;
import com.bolotin.trata.ui.base.BasePresenter;
import com.bolotin.trata.utils.DateTimeConverter;
import com.bolotin.trata.utils.rx.SchedulerProvider;

import java.text.ParseException;
import java.util.Date;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class CategoryAnalyticsDetailsPresenter<V extends CategoryAnalyticsDetailsMvpView> extends BasePresenter<V>
        implements CategoryAnalyticsDetailsMvpPresenter<V> {

    @Inject
    public CategoryAnalyticsDetailsPresenter(DataManager dataManager,
                                             SchedulerProvider schedulerProvider,
                                             CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewPrepared(String categoryId, String month) {
        try {
            Date date = DateTimeConverter.parseCategoryAnalytics(month);
            String dbMonth = DateTimeConverter.formatDb(date).substring(0, 7);
            String title = DateTimeConverter.formatFullMonth(date);

            getCompositeDisposable().add(getDataManager().getTransactionsByMonthAndCategory(dbMonth, categoryId)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(transactions -> getMvpView().updateTransactions(transactions)));

            getMvpView().setTitle(title);
        } catch (ParseException e) {
            Log.e("ParseException", e.getMessage());
        }
    }
}