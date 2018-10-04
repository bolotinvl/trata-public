package com.bolotin.trata.di.component;

import android.app.Application;
import android.content.Context;

import com.bolotin.trata.TrataApplication;
import com.bolotin.trata.data.DataManager;
import com.bolotin.trata.di.ApplicationContext;
import com.bolotin.trata.di.module.ApplicationModule;
import com.google.android.gms.location.FusedLocationProviderClient;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {

    void inject(TrataApplication trataApplication);

    @ApplicationContext
    Context getContext();

    Application getApplication();

    DataManager getDataManager();

    FusedLocationProviderClient getFusedLocationProviderClient();
}
