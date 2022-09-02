package io.github.keddnyo.midoze.utils;

import java.util.Locale;

public class DozeLocale {
    public Locale getCurrentLocale() {
        return Locale.getDefault();
    }

    public String getCurrentLanguage() {
        return getCurrentLocale().getLanguage();
    }

    public String getCurrentLocaleString() {
        return getCurrentLocale().toString();
    }

    public String getCurrentCountry() {
        return getCurrentLocale().getCountry();
    }
}