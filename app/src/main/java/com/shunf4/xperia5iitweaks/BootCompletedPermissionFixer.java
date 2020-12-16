package com.shunf4.xperia5iitweaks;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

import java.io.File;

public class BootCompletedPermissionFixer extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_LOCKED_BOOT_COMPLETED.equals(intent.getAction())) {
            AsyncTask.execute(() -> {
                File pkgDirectory = context.getFilesDir().getParentFile();
                if (pkgDirectory.exists()) {
                    pkgDirectory.setExecutable(true, false);
                    pkgDirectory.setReadable(true, false);
                }

                File sharedPrefsDir = new File(pkgDirectory.getAbsolutePath() + "/shared_prefs");
                if (sharedPrefsDir.exists()) {
                    pkgDirectory.setExecutable(true, false);
                    sharedPrefsDir.setReadable(true, false);
                }

                File sharedPrefs = new File(sharedPrefsDir.getAbsolutePath() + "/settings.xml");
                if (sharedPrefs.exists()) {
                    sharedPrefs.setExecutable(true, false);
                    sharedPrefs.setReadable(true, false);
                }
            });
        }
    }
}
