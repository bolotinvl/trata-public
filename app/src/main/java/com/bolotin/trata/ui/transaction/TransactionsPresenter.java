package com.bolotin.trata.ui.transaction;

import com.bolotin.trata.data.DataManager;
import com.bolotin.trata.data.db.model.Transaction;
import com.bolotin.trata.ui.base.BasePresenter;
import com.bolotin.trata.utils.CommonUtils;
import com.bolotin.trata.utils.DateTimeConverter;
import com.bolotin.trata.utils.DecimalFormatter;
import com.bolotin.trata.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.subjects.PublishSubject;

public class TransactionsPresenter<V extends TransactionsMvpView> extends BasePresenter<V>
        implements TransactionsMvpPresenter<V> {

    private PublishSubject<String> categoryIdObservable;
    private String selectedCategoryId;

    private PublishSubject<List<Double>> sumsObservable;
    private List<Double> sums;

    @Inject
    TransactionsPresenter(DataManager dataManager,
                          SchedulerProvider schedulerProvider,
                          CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
        categoryIdObservable = PublishSubject.create();
        sumsObservable = PublishSubject.create();
    }

    @Override
    public Boolean getFirstRun() {
        return getDataManager().getFirstRun();
    }

    @Override
    public void onViewPrepared() {
        updatePagerData();
        observeTransactionsSum();
        subscribeToCategoryObservable();
        subscribeToSumsObservable();
        getDefaultCategory();
    }

    @Override
    public String getCurrencySymbol() {
        return getDataManager().getCurrencySymbol();
    }

    @Override
    public void updateSelectedCategoryId(String selectedCategoryId) {
        this.selectedCategoryId = selectedCategoryId;
        categoryIdObservable.onNext(selectedCategoryId);
    }

    @Override
    public void addTransaction(String note, String value, Double latitude, Double longitude) {
        getCompositeDisposable().add(getDataManager().getAccount().flatMap(account -> {
            Date currentDate = Calendar.getInstance().getTime();
            Transaction transaction = new Transaction(
                    UUID.randomUUID().toString(),
                    account.getId(),
                    selectedCategoryId,
                    Double.parseDouble(value),
                    DateTimeConverter.formatDb(currentDate),
                    note,
                    true,
                    false,
                    DateTimeConverter.formatDb(currentDate),
                    null,
                    latitude,
                    longitude);
            return Single.fromCallable(() -> transaction);
        }).subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(transaction -> {
                    insertTransactionToDb(transaction);
                    getMvpView().onAddTransaction(DateTimeConverter.formatFullMonth(
                            DateTimeConverter.parseDb(transaction.getDate())));
                }));
    }

    @Override
    public void getSumAndUpdateView() {
        if (sums != null) {
            updateTransactionsSumUI(sums);
        }
    }

    private void observeTransactionsSum() {
        getCompositeDisposable().add(getDataManager().getTransactionsSum()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(sums -> {
                    this.sums = sums;
                    sumsObservable.onNext(sums);
                }));
    }


    private void insertTransactionToDb(Transaction transaction) {
        getCompositeDisposable().add(getDataManager().insertTransaction(transaction)
                .subscribeOn(getSchedulerProvider().io())
                .subscribe());
    }

    private void updatePagerData() {
        getCompositeDisposable().add(getDataManager().getUniqueMonths()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(months -> {
                            List<String> formattedMonths = new ArrayList<>();

                            for (String month : months) {
                                formattedMonths.add(DateTimeConverter.formatFullMonth(
                                        DateTimeConverter.parseYearMonth(month)));
                            }

                            if (formattedMonths.size() == 0) {
                                formattedMonths.add(DateTimeConverter.formatFullMonth(
                                        Calendar.getInstance().getTime()));
                            }
                            getMvpView().updatePagerData(formattedMonths);
                        }
                ));
    }

    private void getDefaultCategory() {
        getCompositeDisposable().add(getDataManager().getDefaultCategory()
                .subscribeOn(getSchedulerProvider().io())
                .subscribe(category -> updateSelectedCategoryId(category.getId())));
    }

    private void subscribeToCategoryObservable() {
        getCompositeDisposable().add(categoryIdObservable
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(this::updateCategoryIconUI));
    }

    private void updateCategoryIconUI(String selectedCategoryId) {
        getCompositeDisposable().add(getDataManager().getCategoryIcon(selectedCategoryId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(categoryIcon ->
                        getMvpView().setIconToSelectCategoryButton(CommonUtils.replaceHyphen(categoryIcon))));
    }

    private void subscribeToSumsObservable() {
        getCompositeDisposable().add(sumsObservable
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(sums -> {
                    if (sums.size() == 0) {
                        sums.add(0d);
                    }
                    updateTransactionsSumUI(sums);
                }));
    }

    private void updateTransactionsSumUI(List<Double> sums) {
        String sum = String.format(getMvpView().getAmountString(),
                DecimalFormatter.format(sums.get(getMvpView().getSelectedTabPosition())),
                getCurrencySymbol());

        getMvpView().updateSumView(sum);
    }
}