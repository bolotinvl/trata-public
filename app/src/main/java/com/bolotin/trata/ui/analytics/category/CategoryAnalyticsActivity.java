package com.bolotin.trata.ui.analytics.category;

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
import com.bolotin.trata.ui.analytics.details.category.CategoryAnalyticsDetailsActivity;
import com.bolotin.trata.ui.barChart.BarChartData;
import com.bolotin.trata.ui.barChart.BarChartUtil;
import com.bolotin.trata.ui.barChart.BarChartView;
import com.bolotin.trata.ui.base.BaseActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CategoryAnalyticsActivity extends BaseActivity implements CategoryAnalyticsMvpView, OnClickListener {

    @Inject
    CategoryAnalyticsMvpPresenter<CategoryAnalyticsMvpView> presenter;

    @Inject
    LinearLayoutManager layoutManager;

    @Inject
    CommonAnalyticsAdapter adapter;

    @BindView(R.id.category_analytics_toolbar)
    Toolbar toolbar;

    @BindView(R.id.category_analytics_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.category_analytics_title)
    TextView title;

    @BindView(R.id.category_analytics_subtitle)
    TextView subTitle;

    @BindView(R.id.category_analytics_bar_chart)
    BarChartView barChartView;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, CategoryAnalyticsActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_category_analytics);

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
        presenter.onViewPrepared(getIntent().getStringExtra("categoryId"));
        adapter.setOnClickListener(this);
        adapter.setColors(BarChartView.getColors());
        adapter.setCurrency(presenter.getCurrency());
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    @OnClick(R.id.category_analytics_back_button)
    void onBackButtonClick() {
        onBackPressed();
    }

    @Override
    public void onClicked(View v, int position) {
        Intent intent = CategoryAnalyticsDetailsActivity.getStartIntent(this);
        intent.putExtra("month", adapter.getXValue(position));
        intent.putExtra("categoryId", getIntent().getStringExtra("categoryId"));
        startActivity(intent);
    }

    @Override
    public void updateBarChartData(List<BarChartData> barChartData) {
        adapter.addItems(barChartData);
        barChartView.setLabels(BarChartUtil.getLabels(barChartData));
        barChartView.setBarChartData(barChartData);
    }

    @Override
    public void updateTitle(String categoryName) {
        title.setText(categoryName);
    }

    @Override
    public void updateSubtitle(String sum, String currency) {
        subTitle.setText(String.format(getResources().getString(R.string.amount_with_currency),
                sum,
                currency));
    }
}