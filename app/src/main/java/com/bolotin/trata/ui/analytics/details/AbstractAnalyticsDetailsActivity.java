package com.bolotin.trata.ui.analytics.details;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.bolotin.trata.R;
import com.bolotin.trata.data.db.model.Transaction;
import com.bolotin.trata.ui.base.BaseActivity;
import com.bolotin.trata.ui.transaction.transactionsFragment.TransactionsAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public abstract class AbstractAnalyticsDetailsActivity extends BaseActivity implements AbstractAnalyticsDetailsMvpView {

    @Inject
    AbstractAnalyticsDetailsMvpPresenter<AbstractAnalyticsDetailsMvpView> presenter;

    @Inject
    protected LinearLayoutManager layoutManager;

    @Inject
    protected TransactionsAdapter adapter;

    @BindView(R.id.analytics_details_toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.analytics_details_recycler_view)
    protected RecyclerView recyclerView;

    @BindView(R.id.analytics_details_placeholder)
    protected TextView placeholder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_analytics_details);

        getActivityComponent().inject(this);

        setUnBinder(ButterKnife.bind(this));

        presenter.onAttach(this);
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
        adapter.setMode(presenter.getMode());
        adapter.setAmountString(getString(R.string.amount_with_currency));
        adapter.setCurrency(presenter.getCurrency());
    }

    @OnClick(R.id.analytics_details_back_button)
    void onBackButtonClick() {
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        finish();
        super.onBackPressed();
    }

    protected void updateTransactions(List<Transaction> transactions) {
        if (transactions.size() == 0) {
            recyclerView.setVisibility(View.INVISIBLE);
            placeholder.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            placeholder.setVisibility(View.GONE);
            adapter.addItems(transactions);
        }
    }
}