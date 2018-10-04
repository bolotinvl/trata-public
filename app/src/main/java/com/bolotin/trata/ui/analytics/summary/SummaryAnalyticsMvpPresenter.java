package com.bolotin.trata.ui.analytics.summary;

import com.bolotin.trata.ui.base.MvpPresenter;

public interface SummaryAnalyticsMvpPresenter<V extends SummaryAnalyticsMvpView> extends MvpPresenter<V> {

    void onViewPrepared();

    String getCurrency();
}