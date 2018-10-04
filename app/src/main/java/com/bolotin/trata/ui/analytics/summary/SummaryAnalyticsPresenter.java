package com.bolotin.trata.ui.analytics.summary;

import com.bolotin.trata.data.DataManager;
import com.bolotin.trata.ui.barChart.BarChartUtil;
import com.bolotin.trata.ui.base.BasePresenter;
import com.bolotin.trata.utils.DecimalFormatter;
import com.bolotin.trata.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class SummaryAnalyticsPresenter<V extends SummaryAnalyticsMvpView> extends BasePresenter<V>
        implements SummaryAnalyticsMvpPresenter<V> {

    @Inject
    public SummaryAnalyticsPresenter(DataManager dataManager,
                                     SchedulerProvider schedulerProvider,
                                     CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewPrepared() {
        getCompositeDisposable().add(getDataManager().getBarChartDataSummary()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(barChartData -> {
                    getMvpView().updateBarChartData(barChartData);
                    getMvpView().updateSubtitle(DecimalFormatter.format(BarChartUtil.getSum(barChartData)), getCurrency());
                }));
    }

    @Override
    public String getCurrency() {
        return getDataManager().getCurrencySymbol();
    }
}