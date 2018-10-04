package com.bolotin.trata.ui.analytics.details;

import com.bolotin.trata.data.DataManager;
import com.bolotin.trata.ui.base.BasePresenter;
import com.bolotin.trata.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class AbstractAnalyticsDetailsPresenter<V extends AbstractAnalyticsDetailsMvpView> extends BasePresenter<V>
        implements AbstractAnalyticsDetailsMvpPresenter<V> {

    @Inject
    public AbstractAnalyticsDetailsPresenter(DataManager dataManager,
                                             SchedulerProvider schedulerProvider,
                                             CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public String getMode() {
        return getDataManager().getMode();
    }

    @Override
    public String getCurrency() {
        return getDataManager().getCurrencySymbol();
    }
}
