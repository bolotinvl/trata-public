package com.bolotin.trata.ui.analytics.details.category;

import com.bolotin.trata.ui.base.MvpPresenter;

public interface CategoryAnalyticsDetailsMvpPresenter<V extends CategoryAnalyticsDetailsMvpView>
        extends MvpPresenter<V> {

    void onViewPrepared(String categoryId, String month);
}
