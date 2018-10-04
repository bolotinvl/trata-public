package com.bolotin.trata.ui.analytics.category;

import com.bolotin.trata.ui.barChart.BarChartData;
import com.bolotin.trata.ui.base.MvpView;

import java.util.List;

public interface CategoryAnalyticsMvpView extends MvpView {

    void updateBarChartData(List<BarChartData> barChartData);

    void updateTitle(String categoryName);

    void updateSubtitle(String sum, String currency);
}