package com.bolotin.trata.ui.appearance;

import com.bolotin.trata.data.DataManager;
import com.bolotin.trata.ui.base.BasePresenter;
import com.bolotin.trata.utils.rx.SchedulerProvider;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

public class AppearancePresenter<V extends AppearanceMvpView> extends BasePresenter<V>
        implements AppearanceMvpPresenter<V> {

    @Inject
    public AppearancePresenter(DataManager dataManager,
                               SchedulerProvider schedulerProvider,
                               CompositeDisposable compositeDisposable) {
        super(dataManager, schedulerProvider, compositeDisposable);
    }

    @Override
    public String getMode() {
        return getDataManager().getMode();
    }

    @Override
    public void updateTheme(String theme) {
        getDataManager().setTheme(theme);
    }

    @Override
    public void updateMode(String mode) {
        getDataManager().setMode(mode);
    }
}
