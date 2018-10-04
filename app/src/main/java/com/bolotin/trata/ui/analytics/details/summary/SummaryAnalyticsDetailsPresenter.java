package com.bolotin.trata.ui.analytics.details.summary;

import android.util.Log;

import com.bolotin.trata.data.DataManager;
import com.bolotin.trata.ui.base.BasePresenter;
import com.bolotin.trata.utils.DateTimeConverter;
import com.bolotin.trata.utils.rx.SchedulerProvider;

import java.text.ParseException;
import java.util.Date;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SummaryAnalyticsDetailsPresenter<V extends SummaryAnalyticsDetailsMvpView> extends BasePresenter<V>
        implements SummaryAnalyticsDetailsMvpPresenter<V> {

    @Inject
    public SummaryAnalyticsDetailsPresenter(DataManager dataManager,
                                            SchedulerProvider schedulerProvider,
                                            CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewPrepared(String month) {
        try {
            Date date = DateTimeConverter.parseCategoryAnalytics(month);
            String dbMonth = DateTimeConverter.formatDb(date).substring(0, 7);
            String title = DateTimeConverter.formatFullMonth(date);

            getCompositeDisposable().add(getDataManager().getTransactionsByMonth(dbMonth)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(transactions -> getMvpView().updateTransactions(transactions)));

            getMvpView().setTitle(title);
        } catch (ParseException e) {
            Log.e("ParseException", e.getMessage());
        }
    }
}