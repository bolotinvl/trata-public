package com.bolotin.trata.ui.analytics.basic;

import com.bolotin.trata.ui.base.MvpPresenter;

import java.util.Date;

public interface BasicAnalyticsMvpPresenter<V extends BasicAnalyticsMvpView> extends MvpPresenter<V> {

    void onViewPrepared();

    String getCurrency();

    void updateDateRange(Date startDate, Date endDate);
}
