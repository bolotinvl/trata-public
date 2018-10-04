package com.bolotin.trata.ui.analytics.summary;

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
import com.bolotin.trata.listeners.OnClickListener;
import com.bolotin.trata.ui.analytics.CommonAnalyticsAdapter;
import com.bolotin.trata.ui.analytics.details.summary.SummaryAnalyticsDetailsActivity;
import com.bolotin.trata.ui.barChart.BarChartData;
import com.bolotin.trata.ui.barChart.BarChartUtil;
import com.bolotin.trata.ui.barChart.BarChartView;
import com.bolotin.trata.ui.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SummaryAnalyticsActivity extends BaseActivity implements SummaryAnalyticsMvpView, OnClickListener {

    @Inject
    SummaryAnalyticsMvpPresenter<SummaryAnalyticsMvpView> presenter;

    @Inject
    LinearLayoutManager layoutManager;

    @Inject
    CommonAnalyticsAdapter adapter;

    @BindView(R.id.summary_analytics_toolbar)
    Toolbar toolbar;

    @BindView(R.id.summary_analytics_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.summary_analytics_title)
    TextView title;

    @BindView(R.id.summary_analytics_subtitle)
    TextView subTitle;

    @BindView(R.id.summary_analytics_bar_chart)
    BarChartView barChartView;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SummaryAnalyticsActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_summary_analytics);

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
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        presenter.onViewPrepared();
        adapter.setOnClickListener(this);
        adapter.setColors(BarChartView.getColors());
        adapter.setCurrency(presenter.getCurrency());
        title.setText(getString(R.string.summary_analytics_title));
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @OnClick(R.id.summary_analytics_back_button)
    void onBackButtonClick() {
        onBackPressed();
    }

    @Override
    public void onClicked(View v, int position) {
        Intent intent = SummaryAnalyticsDetailsActivity.getStartIntent(this);
        intent.putExtra("month", adapter.getXValue(position));
        startActivity(intent);
    }

    @Override
    public void updateBarChartData(List<BarChartData> barChartData) {
        adapter.addItems(barChartData);
        barChartView.setLabels(BarChartUtil.getLabels(barChartData));
        barChartView.setBarChartData(barChartData);
    }

    @Override
    public void updateSubtitle(String sum, String currency) {
        subTitle.setText(String.format(getResources().getString(R.string.amount_with_currency),
                sum,
                currency));
    }
}