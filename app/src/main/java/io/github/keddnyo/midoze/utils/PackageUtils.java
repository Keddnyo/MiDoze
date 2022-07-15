package io.github.keddnyo.midoze.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

public class PackageUtils {
    public String getPackageVersion(Context context, String packageName) {
        try {
            PackageInfo eInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            return eInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }
}
