package com.bolotin.trata.ui.analytics;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;

import com.bolotin.trata.R;
import com.bolotin.trata.ui.analytics.basic.BasicAnalyticsActivity;
import com.bolotin.trata.ui.analytics.summary.SummaryAnalyticsActivity;
import com.bolotin.trata.ui.base.BaseActivity;
import com.bolotin.trata.ui.category.analyticsCategory.AnalyticsCategoryActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnalyticsMenuActivity extends BaseActivity {

    @BindView(R.id.analytics_toolbar)
    Toolbar toolbar;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, AnalyticsMenuActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_common_analytics);

        setUnBinder(ButterKnife.bind(this));

        setUp(toolbar);
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    @OnClick(R.id.analytics_back_button)
    void onBackButtonClick() {
        onBackPressed();
    }

    @OnClick(R.id.basic_analytics_layout)
    void onBasicAnalyticsClick() {
        startActivity(BasicAnalyticsActivity.getStartIntent(this));
    }

    @OnClick(R.id.category_analytics_layout)
    void onCategoryAnalyticsClick() {
        startActivity(AnalyticsCategoryActivity.getStartIntent(this));
    }

    @OnClick(R.id.summary_analytics_layout)
    void onSummaryAnalyticsClick() {
        startActivity(SummaryAnalyticsActivity.getStartIntent(this));
    }
}