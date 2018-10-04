package com.bolotin.trata.ui.analytics.category;

import com.bolotin.trata.ui.base.MvpPresenter;

public interface CategoryAnalyticsMvpPresenter<V extends CategoryAnalyticsMvpView> extends MvpPresenter<V> {

    void onViewPrepared(String categoryId);

    String getCurrency();
}