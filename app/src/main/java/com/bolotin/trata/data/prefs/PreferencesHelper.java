package com.bolotin.trata.data.prefs;

public interface PreferencesHelper {

    Boolean getFirstRun();

    void setFirstRun(Boolean firstRun);

    String getMode();

    void setMode(String mode);

    String getTheme();

    void setTheme(String theme);

    String getCurrencySymbol();

    void setCurrencySymbol(String currencySymbol);

    Integer getTabPosition();

    void setTabPosition(Integer tabPosition);

    Integer getScrollPosition();

    void setScrollPosition(Integer scrollPosition);

    Double getLatitude();

    void setLatitude(Double latitude);

    Double getLongitude();

    void setLongitude(Double longitude);
}
