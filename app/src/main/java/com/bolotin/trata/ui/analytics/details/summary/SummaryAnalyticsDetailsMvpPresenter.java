package com.bolotin.trata.ui.analytics.details.summary;

import com.bolotin.trata.ui.base.MvpPresenter;

public interface SummaryAnalyticsDetailsMvpPresenter<V extends SummaryAnalyticsDetailsMvpView>
        extends MvpPresenter<V> {

    void onViewPrepared(String month);
}