package com.bolotin.trata.ui.analytics.details.basic;

import com.bolotin.trata.ui.base.MvpPresenter;

import java.util.Date;

public interface BasicAnalyticsDetailsMvpPresenter<V extends BasicAnalyticsDetailsMvpView> extends MvpPresenter<V> {

    void onViewPrepared(String categoryId, String startDate, String endDate);

    String formatDateToTitle(Date date);

    String formatDateToDb(Date date);
}
