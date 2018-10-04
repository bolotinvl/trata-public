package com.bolotin.trata.ui.transaction.transactionsFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bolotin.trata.R;
import com.bolotin.trata.data.db.model.Category;
import com.bolotin.trata.data.db.model.Transaction;
import com.bolotin.trata.di.component.ActivityComponent;
import com.bolotin.trata.listeners.OnClickListener;
import com.bolotin.trata.ui.base.BaseFragment;
import com.bolotin.trata.ui.editTransaction.EditTransactionActivity;
import com.bolotin.trata.ui.transaction.editDialog.EditDialog;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TransactionsFragment extends BaseFragment implements TransactionsFragmentMvpView, OnClickListener{

    @Inject
    TransactionsFragmentMvpPresenter<TransactionsFragmentMvpView> presenter;

    @Inject
    LinearLayoutManager layoutManager;

    @Inject
    TransactionsAdapter adapter;

    @BindView(R.id.transactions_recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.transactions_placeholder)
    TextView placeholder;

    String month;

    public static TransactionsFragment newInstance(String month) {
        Bundle bundle = new Bundle();
        bundle.putString("month", month);
        TransactionsFragment fragment = new TransactionsFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.recycler_view_transactions, container, false);

        ActivityComponent component = getActivityComponent();
        if (component != null) {

            component.inject(this);

            setUnBinder(ButterKnife.bind(this, view));

            presenter.onAttach(this);
        }

        return view;
    }

    @Override
    public void onDestroy() {
        presenter.onDetach();
        super.onDestroy();
    }

    @Override
    protected void setUp(View view) {
        Bundle bundle = getArguments();
        if (bundle != null) {
            month = bundle.getString("month");
        }

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(this);
        adapter.setMode(presenter.getMode());
        adapter.setAmountString(getString(R.string.amount_with_currency));
        adapter.setCurrency(presenter.getCurrency());
        presenter.onViewPrepared(month);
    }

    @Override
    public void updateTransactions(List<Transaction> items) {
            if (items.size() == 0) {
                recyclerView.setVisibility(View.INVISIBLE);
                placeholder.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                placeholder.setVisibility(View.GONE);
                adapter.addItems(items);
            }
    }

    @Override
    public void updateCategories(List<Category> categories) {
        adapter.addCategories(categories);
    }

    @Override
    public void onClicked(View v, int position) {
        editTransaction(position);
    }

    @Override
    public void onLongClicked(View v, int position) {
        if (getActivity() != null) {
            EditDialog dialog = EditDialog.newInstance(adapter.getTransactionId(position));
            dialog.show(getActivity().getSupportFragmentManager());
        }
    }

    public String getMonth() {
        return month;
    }

    private void editTransaction(int position) {
        Transaction selectedTransaction = adapter.getTransaction(position);

        Intent intent = new Intent(EditTransactionActivity.getStartIntent(getActivity()));
        intent.putExtra("transactionId", selectedTransaction.getId());

        startActivity(intent);
    }
}
