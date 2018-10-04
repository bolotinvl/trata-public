package com.bolotin.trata.ui.currency;

import com.bolotin.trata.data.DataManager;
import com.bolotin.trata.data.db.model.Account;
import com.bolotin.trata.data.db.model.Category;
import com.bolotin.trata.ui.base.BasePresenter;
import com.bolotin.trata.utils.AppConstants;
import com.bolotin.trata.utils.DateTimeConverter;
import com.bolotin.trata.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class CurrencyPresenter<V extends CurrencyMvpView> extends BasePresenter<V>
        implements CurrencyMvpPresenter<V> {

    private List<HashMap<String, String>> currencies;

    private String currencyCode;

    @Inject
    CurrencyPresenter(DataManager dataManager,
                      SchedulerProvider schedulerProvider,
                      CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public void onViewPrepared() {
        getCompositeDisposable().add(getDataManager()
                .getCurrencies()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(currencies -> {
                            this.currencies = currencies;
                            getMvpView().updateCurrencies(currencies);
                            if (getDataManager().getFirstRun()) {
                                selectLocaleCurrency();
                                createAccount();
                            } else {
                                selectCurrentCurrency();
                            }
                        }
                ));
    }

    @Override
    public void updateCurrencySymbol(String currencySymbol) {
        getDataManager().setCurrencySymbol(currencySymbol);
    }

    @Override
    public void updateCurrencyCode(String currencyCode) {
        Date currentDate = Calendar.getInstance().getTime();
        getCompositeDisposable().add(getDataManager()
                .getAccount()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(account -> {
                    account.setCurrencyCode(currencyCode);
                    account.setUpdatedAt(DateTimeConverter.formatDb(currentDate));

                    getCompositeDisposable().add(getDataManager()
                            .updateAccount(account)
                            .subscribeOn(getSchedulerProvider().io())
                            .observeOn(getSchedulerProvider().ui())
                            .subscribe());
                }));
    }

    @Override
    public void disableFirstRun() {
        getDataManager().setFirstRun(false);
    }

    @Override
    public void initializeCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(createNewCategory("Home", "house", 0));
        categories.add(createNewCategory("Entertainment", "gamepad", 1));
        categories.add(createNewCategory("Transport", "bus", 2));
        categories.add(createNewCategory("Phone", "smart-phone", 3));
        categories.add(createNewCategory("Restaurants & Cafes", "coffee", 4));
        categories.add(createNewCategory("Groceries", "store", 5));
        categories.add(createNewCategory("Flights", "airplane", 6));

        getCompositeDisposable().add(getDataManager().insertCategories(categories)
                .subscribeOn(getSchedulerProvider().io())
                .subscribe());
    }

    private void selectLocaleCurrency() {
        Locale locale = Locale.getDefault();
        currencyCode = Currency.getInstance(locale).getCurrencyCode();
        toggleSelection();
    }

    private void selectCurrentCurrency() {
        getCompositeDisposable().add(getDataManager().getCurrencyCode()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(currencyCode -> {
                    this.currencyCode = currencyCode;
                    toggleSelection();
                }));
    }

    private void toggleSelection() {
        Iterator<HashMap<String, String>> iterator = currencies.iterator();
        int position = 0;
        while (iterator.hasNext()) {
            HashMap<String, String> currency = iterator.next();
            if (currency.get("currencyCode").equals(this.currencyCode)) {
                getMvpView().toggleSelection(position);
                return;
            }
            position++;
        }
        getMvpView().toggleSelection(position);
        this.currencyCode = AppConstants.DEFAULT_CURRENCY_CODE;
    }

    private void createAccount() {
        Date currentDate = Calendar.getInstance().getTime();
        Account account = new Account(UUID.randomUUID().toString(),
                currencyCode,
                DateTimeConverter.formatDb(currentDate),
                DateTimeConverter.formatDb(currentDate));

        getCompositeDisposable().add(getDataManager()
                .insertAccount(account)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe());
    }

    private Category createNewCategory(String name, String icon, Integer order) {
        Date currentDate = Calendar.getInstance().getTime();
        return new Category(UUID.randomUUID().toString(),
                name,
                icon,
                order,
                DateTimeConverter.formatDb(currentDate),
                DateTimeConverter.formatDb(currentDate));
    }
}