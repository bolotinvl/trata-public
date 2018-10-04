package com.bolotin.trata.ui.analytics.details.category;

import com.bolotin.trata.data.db.model.Transaction;
import com.bolotin.trata.ui.base.MvpView;

import java.util.List;

public interface CategoryAnalyticsDetailsMvpView extends MvpView {

    void updateTransactions(List<Transaction> transactions);

    void setTitle(String title);
}