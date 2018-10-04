package com.bolotin.trata.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import com.bolotin.trata.di.ApplicationContext;
import com.bolotin.trata.di.PreferenceInfo;
import com.bolotin.trata.utils.AppConstants;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AppPreferencesHelper implements PreferencesHelper {

    private static final String PREF_KEY_FIRST_RUN = "PREF_KEY_FIRST_RUN";
    private static final String PREF_KEY_MODE = "PRER_KEY_MODE";
    private static final String PREF_KEY_THEME = "PREF_KEY_THEME";
    private static final String PREF_KEY_CURRENCY_SYMBOL = "PREF_KEY_CURRENCY_SYMBOL";
    private static final String PREF_KEY_TAB_POSITION = "PREF_KEY_TAB_POSITION";
    private static final String PREF_KEY_SCROLL_POSITION = "PREF_KEY_SCROLL_POSITION";

    private final SharedPreferences sharedPreferences;

    @Inject
    AppPreferencesHelper(@ApplicationContext Context context,
                                @PreferenceInfo String prefName) {
        sharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
    }

    @Override
    public Boolean getFirstRun() {
        return sharedPreferences.getBoolean(PREF_KEY_FIRST_RUN, true);
    }

    @Override
    public void setFirstRun(Boolean firstRun) {
        sharedPreferences.edit().putBoolean(PREF_KEY_FIRST_RUN, firstRun).apply();
    }

    @Override
    public String getMode() {
        return sharedPreferences.getString(PREF_KEY_MODE, AppConstants.DEFAULT_MODE);
    }

    @Override
    public void setMode(String mode) {
        sharedPreferences.edit().putString(PREF_KEY_MODE, mode).apply();
    }

    @Override
    public String getTheme() {
        return sharedPreferences.getString(PREF_KEY_THEME, AppConstants.DEFAULT_THEME);
    }

    @Override
    public void setTheme(String theme) {
        sharedPreferences.edit().putString(PREF_KEY_THEME, theme).apply();
    }

    @Override
    public String getCurrencySymbol() {
        return sharedPreferences.getString(PREF_KEY_CURRENCY_SYMBOL, AppConstants.DEFAULT_CURRENCY_SYMBOL);
    }

    @Override
    public void setCurrencySymbol(String currencySymbol) {
        sharedPreferences.edit().putString(PREF_KEY_CURRENCY_SYMBOL, currencySymbol).apply();
    }

    @Override
    public Integer getTabPosition() {
        return sharedPreferences.getInt(PREF_KEY_TAB_POSITION, 0);
    }

    @Override
    public void setTabPosition(Integer tabPosition) {
        sharedPreferences.edit().putInt(PREF_KEY_TAB_POSITION, tabPosition).apply();
    }

    @Override
    public Integer getScrollPosition() {
        return sharedPreferences.getInt(PREF_KEY_SCROLL_POSITION, 0);
    }

    @Override
    public void setScrollPosition(Integer scrollPosition) {
        sharedPreferences.edit().putInt(PREF_KEY_SCROLL_POSITION, scrollPosition).apply();
    }

    @Override
    public Double getLatitude() {
        return (double) sharedPreferences.getFloat("latutude", 55.0084f);
    }

    @Override
    public void setLatitude(Double latitude) {
        sharedPreferences.edit().putFloat("latitude", latitude.floatValue()).apply();
    }

    @Override
    public Double getLongitude() {
        return (double) sharedPreferences.getFloat("longitude", 82.9357f);
    }

    @Override
    public void setLongitude(Double longitude) {
        sharedPreferences.edit().putFloat("longitude", longitude.floatValue()).apply();
    }
}
