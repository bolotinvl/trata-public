package com.bolotin.trata.ui.analytics.details.basic;

import com.bolotin.trata.data.db.model.Category;
import com.bolotin.trata.data.db.model.Transaction;
import com.bolotin.trata.ui.base.MvpView;

import java.util.List;

public interface BasicAnalyticsDetailsMvpView extends MvpView {

    void updateTransactions(List<Transaction> transactions);

    void updateCategories(List<Category> categories);
}
