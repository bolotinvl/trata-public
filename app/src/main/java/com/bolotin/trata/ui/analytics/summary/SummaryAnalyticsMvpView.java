package com.bolotin.trata.ui.analytics.summary;

import com.bolotin.trata.ui.barChart.BarChartData;
import com.bolotin.trata.ui.base.MvpView;

import java.util.List;

public interface SummaryAnalyticsMvpView extends MvpView {

    void updateBarChartData(List<BarChartData> barChartData);

    void updateSubtitle(String sum, String currency);
}