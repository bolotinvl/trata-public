package com.bolotin.trata.ui.analytics.customDatePicker;

import com.bolotin.trata.data.DataManager;
import com.bolotin.trata.ui.base.BasePresenter;
import com.bolotin.trata.utils.rx.SchedulerProvider;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class CustomDatePickerPresenter<V extends CustomDatePickerMvpView> extends BasePresenter<V>
        implements CustomDatePickerMvpPresenter<V> {

    @Inject
    public CustomDatePickerPresenter(DataManager dataManager,
                                     SchedulerProvider schedulerProvider,
                                     CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public Date getDate(int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, dayOfMonth);
        return calendar.getTime();
    }
}