package io.github.keddnyo.midoze.utils;

import java.util.Locale;

public class LocaleUtils {
    public Locale getCurrentLocale() {
        return Locale.getDefault();
    }

    public String getCurrentLanguage() {
        return getCurrentLocale().getLanguage();
    }

    public String getCurrentCountry() {
        return getCurrentLocale().getCountry();
    }
}