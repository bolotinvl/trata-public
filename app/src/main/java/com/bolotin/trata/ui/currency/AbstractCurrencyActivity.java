package com.bolotin.trata.ui.currency;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Button;

import com.bolotin.trata.R;
import com.bolotin.trata.listeners.OnClickListener;
import com.bolotin.trata.ui.base.BaseActivity;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class AbstractCurrencyActivity extends BaseActivity implements CurrencyMvpView, OnClickListener {

    @Inject
    protected CurrencyMvpPresenter<CurrencyMvpView> presenter;

    @Inject
    protected LinearLayoutManager layoutManager;

    @Inject
    protected CurrencyAdapter adapter;

    @BindView(R.id.currency_toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.currencies_recycler_view)
    protected RecyclerView recyclerView;

    @BindView(R.id.continue_button)
    protected Button continueButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_currency);

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
        adapter.setOnClickListener(this);
        presenter.onViewPrepared();
    }

    @Override
    public void updateCurrencies(List<HashMap<String, String>> currencies) {
        adapter.addItems(currencies);
    }

    @Override
    public void toggleSelection(int position) {
        adapter.toggleSelection(position);
    }
}
