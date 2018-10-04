package com.bolotin.trata.ui.analytics.details;

import com.bolotin.trata.ui.base.MvpPresenter;

public interface AbstractAnalyticsDetailsMvpPresenter<V extends AbstractAnalyticsDetailsMvpView> extends MvpPresenter<V> {

    String getMode();

    String getCurrency();
}
