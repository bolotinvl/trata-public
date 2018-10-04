package com.bolotin.trata.ui.analytics.category;

import com.bolotin.trata.data.DataManager;
import com.bolotin.trata.ui.barChart.BarChartUtil;
import com.bolotin.trata.ui.base.BasePresenter;
import com.bolotin.trata.utils.DecimalFormatter;
import com.bolotin.trata.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class CategoryAnalyticsPresenter<V extends CategoryAnalyticsMvpView> extends BasePresenter<V>
        implements CategoryAnalyticsMvpPresenter<V> {

    @Inject
    public CategoryAnalyticsPresenter(DataManager dataManager,
                                      SchedulerProvider schedulerProvider,
                                      CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewPrepared(String categoryId) {
        getCompositeDisposable().add(getDataManager().getBarChartDataByCategory(categoryId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(barChartData -> {
                    getMvpView().updateBarChartData(barChartData);
                    getMvpView().updateSubtitle(DecimalFormatter.format(BarChartUtil.getSum(barChartData)), getCurrency());
                }));

        getCompositeDisposable().add(getDataManager().getCategoryName(categoryId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(categoryName -> getMvpView().updateTitle(categoryName)));
    }

    @Override
    public String getCurrency() {
        return getDataManager().getCurrencySymbol();
    }
}