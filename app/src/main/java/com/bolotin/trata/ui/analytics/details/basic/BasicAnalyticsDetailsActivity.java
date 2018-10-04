package com.bolotin.trata.ui.analytics.details.basic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.bolotin.trata.R;
import com.bolotin.trata.data.db.model.Category;
import com.bolotin.trata.data.db.model.Transaction;
import com.bolotin.trata.ui.analytics.details.AbstractAnalyticsDetailsActivity;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BasicAnalyticsDetailsActivity extends AbstractAnalyticsDetailsActivity implements BasicAnalyticsDetailsMvpView {

    @Inject
    BasicAnalyticsDetailsMvpPresenter<BasicAnalyticsDetailsMvpView> presenter;

    @BindView(R.id.analytics_details_title)
    TextView title;

    public static Intent getStartIntent(Context context) {
        return new Intent(context, BasicAnalyticsDetailsActivity.class);
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
        Bundle bundle = getIntent().getBundleExtra("com.bolotin.trata.bundle");
        String categoryId = bundle.getString("categoryId");
        Date startDate = (Date) bundle.getSerializable("startDate");
        Date endDate = (Date) bundle.getSerializable("endDate");
        String startDateTitle = presenter.formatDateToTitle(startDate);
        String endDateTitle = presenter.formatDateToTitle(endDate);

        title.setText(String.format(getResources().getString(R.string.date_range),
                startDateTitle,
                endDateTitle));

        presenter.onViewPrepared(categoryId, presenter.formatDateToDb(startDate), presenter.formatDateToDb(endDate));
    }

    @Override
    public void updateTransactions(List<Transaction> transactions) {
        super.updateTransactions(transactions);
    }

    @Override
    public void updateCategories(List<Category> categories) {
        adapter.addCategories(categories);
    }
}