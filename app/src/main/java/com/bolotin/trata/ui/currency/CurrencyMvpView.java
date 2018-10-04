package com.bolotin.trata.ui.currency;

import com.bolotin.trata.ui.base.MvpView;

import java.util.HashMap;
import java.util.List;

public interface CurrencyMvpView extends MvpView {

    void updateCurrencies(List<HashMap<String, String>> currencies);

    void toggleSelection(int position);
}
