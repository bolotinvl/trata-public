package com.bolotin.trata.ui.editTransaction;

import com.bolotin.trata.ui.base.MvpView;

public interface EditTransactionMvpView extends MvpView {

    void updateUITransactionData(String date, String time, String value, String note);

    void updateUICategoryData(String categoryIconId, String categoryName);

    void updateUIMapData(Double latitude, Double longitude);

    void showDatePicker(int year, int month, int dayOfMonth);

    void showTimePicker(int hours, int minutes);
}