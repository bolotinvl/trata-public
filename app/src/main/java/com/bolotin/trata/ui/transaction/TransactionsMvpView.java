package com.bolotin.trata.ui.transaction;

import com.bolotin.trata.ui.base.MvpView;

import java.util.List;

public interface TransactionsMvpView extends MvpView {

    void updatePagerData(List<String> months);

    void updateSumView(String sum);

    void setIconToSelectCategoryButton(String categoryIcon);

    String getAmountString();

    int getSelectedTabPosition();

    void onAddTransaction(String month);
}
