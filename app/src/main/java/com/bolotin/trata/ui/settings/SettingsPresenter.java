package com.bolotin.trata.ui.settings;

import android.net.Uri;

import com.bolotin.trata.data.DataManager;
import com.bolotin.trata.ui.base.BasePresenter;
import com.bolotin.trata.utils.AppConstants;
import com.bolotin.trata.utils.DateTimeConverter;
import com.bolotin.trata.utils.rx.SchedulerProvider;

import java.util.Calendar;
import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.observers.DisposableSingleObserver;

public class SettingsPresenter<V extends SettingsMvpView> extends BasePresenter<V>
        implements SettingsMvpPresenter<V> {

    private boolean hasExported;

    @Inject
    SettingsPresenter(DataManager dataManager,
                      SchedulerProvider schedulerProvider,
                      CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
        hasExported = false;
    }

    @Override
    public void exportDB(Uri uri) {
        getMvpView().showProgressBar();
        hasExported = true;

        getCompositeDisposable().add(getDataManager().exportDB(uri)
                .subscribeOn(getSchedulerProvider().ui())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(
                        isExportSuccess -> {
                            getMvpView().hideProgressBar();
                            getMvpView().showSuccessfulExportSnackbar();
                        },
                        throwable -> {
                            getMvpView().hideProgressBar();
                            getMvpView().showUnsuccessfulExportSnackbar();
                        }
                ));
    }

    @Override
    public void importDB(Uri uri) {
        getMvpView().showProgressBar();

        getCompositeDisposable().add(getDataManager().importDB(uri)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(
                        isImportSuccess -> {
                            updateCurrencySymbol();
                            getMvpView().hideProgressBar();
                            getMvpView().showSuccessfulImportSnackbar();
                        },
                        throwable -> {
                            getMvpView().hideProgressBar();
                            getMvpView().showUnsuccessfulImportSnackbar();
                        }));
    }

    @Override
    public String getExportFileName() {
        String currentDate = DateTimeConverter.formatExport(Calendar.getInstance().getTime());
        return currentDate + "-" + AppConstants.DB_NAME;
    }

    @Override
    public boolean hasExported() {
        return hasExported;
    }

    private void updateCurrencySymbol() {
        getCompositeDisposable().add(Single.
                zip(getDataManager().getCurrencyCode(),
                        getDataManager().getCurrencies(),
                        (currencyCode, currencies) -> {
                            String currency = AppConstants.DEFAULT_CURRENCY_SYMBOL;
                            for (HashMap<String, String> currencyItem : currencies) {
                                if (currencyItem.get("currencyCode").contentEquals(currencyCode)) {
                                    currency = currencyItem.get("currencySymbol");
                                }
                            }
                            return currency;
                        })
                .subscribeOn(getSchedulerProvider().io())
                .subscribeWith(new DisposableSingleObserver<String>() {
                    @Override
                    public void onSuccess(String s) {
                        getDataManager().setCurrencySymbol(s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getDataManager().setCurrencySymbol(AppConstants.DEFAULT_CURRENCY_SYMBOL);
                    }
                }));
    }
}
