package com.bolotin.trata;

import android.app.Application;

import com.bolotin.trata.data.DataManager;
import com.bolotin.trata.di.component.ApplicationComponent;
import com.bolotin.trata.di.component.DaggerApplicationComponent;
import com.bolotin.trata.di.module.ApplicationModule;

import javax.inject.Inject;

public class TrataApplication extends Application {

    @Inject
    public DataManager dataManager;

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();

        applicationComponent.inject(this);
    }

    public ApplicationComponent getComponent() {
        return applicationComponent;
    }
}