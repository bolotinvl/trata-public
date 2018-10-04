package com.bolotin.trata.ui.analytics.basic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bolotin.trata.R;
import com.bolotin.trata.data.db.model.Category;
import com.bolotin.trata.listeners.OnClickListener;
import com.bolotin.trata.ui.analytics.CommonAnalyticsAdapter;
import com.bolotin.trata.ui.analytics.customDatePicker.CustomDatePickerDialog;
import com.bolotin.trata.ui.analytics.details.basic.BasicAnalyticsDetailsActivity;
import com.bolotin.trata.ui.barChart.BarChartData;
import com.bolotin.trata.ui.barChart.BarChartUtil;
import com.bolotin.trata.ui.barChart.BarChartView;
import com.bolotin.trata.ui.base.BaseActivity;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BasicAnalyticsActivity extends BaseActivity
        implements BasicAnalyticsMvpView, OnClickListener, CustomDatePickerDialog.OnDateChangedListener {

    @Inject
    BasicAnalyticsMvpPresenter<BasicAnalyticsMvpView> presenter;

    @Inject
    LinearLayoutManager layoutManager;

    @Inject
    CommonAnalyticsAdapter adapter;

    @BindView(R.id.basic_analytics_toolbar)
    Toolbar toolbar;

    @BindView(R.id.basic_analytics_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.basic_analytics_title)
    TextView title;

    @BindView(R.id.basic_analytics_subtitle)
    TextView subTitle;

    @BindView(R.id.basic_analytics_bar_chart)
    BarChartView barChartView;

    private Date startDate;
    private Date endDate;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, BasicAnalyticsActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_basic_analytics);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        presenter.onAttach(this);

        setUp(toolbar);
    }

    @Override
    protected void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp(Toolbar toolbar) {
        super.setUp(toolbar);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        presenter.onViewPrepared();
        adapter.setOnClickListener(this);
        adapter.setColors(BarChartView.getColors());
        adapter.setCurrency(presenter.getCurrency());
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @OnClick(R.id.basic_analytics_back_button)
    void onBackButtonClick() {
        onBackPressed();
    }

    @OnClick(R.id.basic_analytics_date_select_button)
    void onDatePickerClick() {
        CustomDatePickerDialog.newInstance(startDate, endDate).show(getSupportFragmentManager());
    }

    @Override
    public void onClicked(View v, int position) {
        Intent intent = BasicAnalyticsDetailsActivity.getStartIntent(this);
        Bundle bundle = new Bundle();
        bundle.putString("categoryId", adapter.getXValue(position));
        bundle.putSerializable("startDate", startDate);
        bundle.putSerializable("endDate", endDate);
        intent.putExtra("com.bolotin.trata.bundle", bundle);
        startActivity(intent);
    }

    @Override
    public void updateBarChartData(List<BarChartData> barChartData) {
        adapter.addItems(barChartData);
        barChartView.setLabels(BarChartUtil.getLabels(barChartData));
        barChartView.setBarChartData(barChartData);
    }

    @Override
    public void updateCategories(List<Category> categories) {
        adapter.setCategories(categories);
    }

    @Override
    public void updateTitle(String startDate, String endDate) {
        title.setText(String.format(getResources().getString(R.string.date_range),
                startDate,
                endDate));
    }

    @Override
    public void updateSubtitle(String sum, String currency) {
        subTitle.setText(String.format(getResources().getString(R.string.amount_with_currency),
                sum,
                currency));
    }

    @Override
    public void updateDatesRange(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public void updateDate(Date startDate, Date endDate) {
        presenter.updateDateRange(startDate, endDate);
    }
}