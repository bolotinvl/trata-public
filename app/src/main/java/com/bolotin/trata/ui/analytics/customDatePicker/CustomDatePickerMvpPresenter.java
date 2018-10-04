package com.bolotin.trata.ui.analytics.customDatePicker;

import com.bolotin.trata.ui.base.MvpPresenter;

import java.util.Date;

public interface CustomDatePickerMvpPresenter<V extends CustomDatePickerMvpView> extends MvpPresenter<V> {

    Date getDate(int year, int month, int dayOfMonth);
}