package com.bolotin.trata.ui.analytics.basic;

import com.bolotin.trata.data.db.model.Category;
import com.bolotin.trata.ui.barChart.BarChartData;
import com.bolotin.trata.ui.base.MvpView;

import java.util.Date;
import java.util.List;

public interface BasicAnalyticsMvpView extends MvpView {

    void updateBarChartData(List<BarChartData> barChartData);

    void updateCategories(List<Category> categories);

    void updateTitle(String startDate, String endDate);

    void updateSubtitle(String sum, String currency);

    void updateDatesRange(Date startDate, Date endDate);
}
