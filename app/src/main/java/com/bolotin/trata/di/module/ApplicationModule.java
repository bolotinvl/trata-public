package com.bolotin.trata.di.module;

import android.app.Application;
import android.content.Context;

import com.bolotin.trata.data.AppDataManager;
import com.bolotin.trata.data.DataManager;
import com.bolotin.trata.data.db.AppDatabase;
import com.bolotin.trata.data.db.AppDbHelper;
import com.bolotin.trata.data.db.DbHelper;
import com.bolotin.trata.data.prefs.AppPreferencesHelper;
import com.bolotin.trata.data.prefs.PreferencesHelper;
import com.bolotin.trata.di.ApplicationContext;
import com.bolotin.trata.di.PreferenceInfo;
import com.bolotin.trata.utils.AppConstants;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class ApplicationModule {

    private final Application application;

    public ApplicationModule(Application application) {
        this.application = application;
    }

    @Provides
    @ApplicationContext
    Context provideContext() {
        return application;
    }

    @Provides
    Application provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    AppDatabase provideAppDatabase() {
        return AppDatabase.getDatabase(application);
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @PreferenceInfo
    String providePreferenceName() {
        return AppConstants.PREF_NAME;
    }

    @Provides
    @Singleton
    FusedLocationProviderClient provideLocationProviderClient() {
        return LocationServices.getFusedLocationProviderClient(application);
    }
}
