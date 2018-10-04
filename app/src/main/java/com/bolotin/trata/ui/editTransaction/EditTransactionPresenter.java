package com.bolotin.trata.ui.editTransaction;

import com.bolotin.trata.data.DataManager;
import com.bolotin.trata.data.db.model.Transaction;
import com.bolotin.trata.ui.base.BasePresenter;
import com.bolotin.trata.utils.CommonUtils;
import com.bolotin.trata.utils.DateTimeConverter;
import com.bolotin.trata.utils.DecimalFormatter;
import com.bolotin.trata.utils.rx.SchedulerProvider;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class EditTransactionPresenter<V extends EditTransactionMvpView> extends BasePresenter<V>
        implements EditTransactionMvpPresenter<V> {

    private String transactionId;

    @Inject
    EditTransactionPresenter(DataManager dataManager,
                             SchedulerProvider schedulerProvider,
                             CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewPrepared(String transactionId) {
        this.transactionId = transactionId;
        getCompositeDisposable().add(getDataManager().getTransactionById(transactionId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(transaction -> {
                    Date transactionDate = DateTimeConverter.parseDb(transaction.getDate());
                    String date = DateTimeConverter.formatDatePicker(transactionDate);
                    String time = DateTimeConverter.formatTimePicker(transactionDate);
                    String value = DecimalFormatter.format(transaction.getValue());
                    String note = transaction.getNote();
                    getMvpView().updateUITransactionData(date, time, value, note);

                    String categoryId = transaction.getCategoryId();
                    updateUICategoryData(categoryId);
                }));
    }

    @Override
    public void updateTransaction(Double value, String note) {
        getCompositeDisposable().add(getDataManager().getTransactionById(transactionId)
                .firstOrError()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().io())
                .subscribe(transaction -> {
                    transaction.setValue(value);
                    transaction.setNote(note);
                    updateTransactionInDb(transaction);
                }));
    }

    @Override
    public void updateTransactionDate(int year, int month, int dayOfMonth) {
        getCompositeDisposable().add(getDataManager().getTransactionById(transactionId)
                .firstOrError()
                .subscribeOn(getSchedulerProvider().io())
                .subscribe(transaction -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(DateTimeConverter.parseDb(transaction.getDate()));
                    calendar.set(year, month, dayOfMonth);
                    String updatedDate = DateTimeConverter.formatDb(calendar.getTime());
                    transaction.setDate(updatedDate);
                    updateTransactionInDb(transaction);
                }));
    }

    @Override
    public void updateTransactionTime(int hours, int minutes) {
        getCompositeDisposable().add(getDataManager().getTransactionById(transactionId)
                .firstOrError()
                .subscribeOn(getSchedulerProvider().io())
                .subscribe(transaction -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(DateTimeConverter.parseDb(transaction.getDate()));
                    calendar.set(Calendar.HOUR_OF_DAY, hours);
                    calendar.set(Calendar.MINUTE, minutes);
                    String updatedDate = DateTimeConverter.formatDb(calendar.getTime());
                    transaction.setDate(updatedDate);
                    updateTransactionInDb(transaction);
                }));
    }

    @Override
    public void updateCategoryId(String categoryId) {
        getCompositeDisposable().add(getDataManager().getTransactionById(transactionId)
                .firstOrError()
                .subscribeOn(getSchedulerProvider().io())
                .subscribe(transaction -> {
                    transaction.setCategoryId(categoryId);
                    updateTransactionInDb(transaction);
                }));
    }

    @Override
    public void updateTransactionLocation(Double latitude, Double longitude) {
        getCompositeDisposable().add(getDataManager().getTransactionById(transactionId)
                .firstOrError()
                .subscribeOn(getSchedulerProvider().ui())
                .subscribe(transaction -> {
                    transaction.setLocationLatitude(latitude);
                    transaction.setLocationLongtitude(longitude);
                    updateTransactionInDb(transaction);
                }));
    }

    @Override
    public void getTransactionDateAndStartDatePicker() {
        getCompositeDisposable().add(getDataManager().getTransactionById(transactionId)
                .firstOrError()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(transaction -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(DateTimeConverter.parseDb(transaction.getDate()));
                    getMvpView().showDatePicker(calendar.get(Calendar.YEAR),
                            calendar.get(Calendar.MONTH),
                            calendar.get(Calendar.DAY_OF_MONTH));
                }));
    }

    @Override
    public void getTransactionTimeAndStartTimePicker() {
        getCompositeDisposable().add(getDataManager().getTransactionById(transactionId)
                .firstOrError()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(transaction -> {
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(DateTimeConverter.parseDb(transaction.getDate()));
                    getMvpView().showTimePicker(calendar.get(Calendar.HOUR_OF_DAY),
                            calendar.get(Calendar.MINUTE));
                }));
    }

    @Override
    public void saveLastKnownLocation(Double latitude, Double longitude) {
        getDataManager().setLatitude(latitude);
        getDataManager().setLongitude(longitude);
    }

    @Override
    public Double getLastKnownLatitude() {
        return getDataManager().getLatitude();
    }

    @Override
    public Double getLastKnownLongitude() {
        return getDataManager().getLongitude();
    }

    @Override
    public void getTransactionLocationAndUpdateMap() {
        getCompositeDisposable().add(getDataManager().getTransactionById(transactionId)
                .firstOrError()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(transaction -> getMvpView().updateUIMapData(transaction.getLocationLatitude(),
                        transaction.getLocationLongtitude())));
    }

    private void updateUICategoryData(String categoryId) {
        getCompositeDisposable().add(getDataManager().getCategoryById(categoryId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(category -> {
                    String categoryIcon = CommonUtils.replaceHyphen(category.getIcon());
                    String categoryName = category.getName();
                    getMvpView().updateUICategoryData(categoryIcon, categoryName);
                }));
    }

    private void updateTransactionInDb(Transaction transaction) {
        transaction.setUpdatedAt(DateTimeConverter.formatDb(Calendar.getInstance().getTime()));
        getCompositeDisposable().add(getDataManager().updateTransaction(transaction)
                .subscribeOn(getSchedulerProvider().io())
                .subscribe());
    }
}