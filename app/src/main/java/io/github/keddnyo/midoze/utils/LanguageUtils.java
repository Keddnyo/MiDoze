package io.github.keddnyo.midoze.utils;

import java.util.Locale;

public class LanguageUtils {
    Locale getCurrentLocale() {
        return Locale.getDefault();
    }

    public String getCurrentCountry() {
        return getCurrentLocale().getCountry();
    }

    public String getCurrentLanguage() {
        return getCurrentLocale().getLanguage();
    }
}
