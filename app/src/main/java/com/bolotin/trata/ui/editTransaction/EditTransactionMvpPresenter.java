package com.bolotin.trata.ui.editTransaction;

import com.bolotin.trata.ui.base.MvpPresenter;

public interface EditTransactionMvpPresenter<V extends EditTransactionMvpView> extends MvpPresenter<V> {

    void onViewPrepared(String transactionId);

    void updateTransaction(Double value, String note);

    void getTransactionDateAndStartDatePicker();

    void getTransactionTimeAndStartTimePicker();

    void updateTransactionDate(int year, int month, int dayOfMonth);

    void updateTransactionTime(int hour, int minute);

    void updateCategoryId(String categoryId);

    void updateTransactionLocation(Double latitude, Double longitude);

    void saveLastKnownLocation(Double latitude, Double longitude);

    Double getLastKnownLatitude();

    Double getLastKnownLongitude();

    void getTransactionLocationAndUpdateMap();
}