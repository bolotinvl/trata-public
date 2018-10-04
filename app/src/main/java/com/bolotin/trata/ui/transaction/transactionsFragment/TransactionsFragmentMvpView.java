package com.bolotin.trata.ui.transaction.transactionsFragment;

import com.bolotin.trata.data.db.model.Category;
import com.bolotin.trata.data.db.model.Transaction;
import com.bolotin.trata.ui.base.MvpView;

import java.util.List;

public interface TransactionsFragmentMvpView extends MvpView {

    void updateTransactions(List<Transaction> items);

    void updateCategories(List<Category> categories);
}
