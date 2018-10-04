package com.bolotin.trata.ui.analytics.details.summary;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.bolotin.trata.R;
import com.bolotin.trata.data.db.model.Transaction;
import com.bolotin.trata.ui.analytics.details.AbstractAnalyticsDetailsActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SummaryAnalyticsDetailsActivity extends AbstractAnalyticsDetailsActivity
        implements SummaryAnalyticsDetailsMvpView {

    @Inject
    SummaryAnalyticsDetailsMvpPresenter<SummaryAnalyticsDetailsMvpView> presenter;

    @BindView(R.id.analytics_details_title)
    TextView title;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, SummaryAnalyticsDetailsActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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
        String month = getIntent().getStringExtra("month");

        presenter.onViewPrepared(month);
    }

    @Override
    public void updateTransactions(List<Transaction> transactions) {
        super.updateTransactions(transactions);
    }

    @Override
    public void setTitle(String title) {
        this.title.setText(title);
    }
}