package com.shunf4.xperia5iitweaks;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ScaleDrawable;
import android.graphics.drawable.VectorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import java.io.File;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.settings, new SettingsFragment())
                    .commit();
        }
    }

    public static class SettingsFragment extends PreferenceFragmentCompat implements SharedPreferences.OnSharedPreferenceChangeListener {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            getPreferenceManager().setSharedPreferencesName("settings");
            getPreferenceManager().setSharedPreferencesMode(Context.MODE_WORLD_READABLE);
            // getPreferenceManager().setStorageDeviceProtected();
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
            fixPremissionsAsync();
        }

        @Override
        public void onResume() {
            super.onResume();
            getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

        }

        @Override
        public void onPause() {
            getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
            super.onPause();
        }

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            getPreferenceManager().findPreference("volte_margin_end_dp")
                    .setOnPreferenceChangeListener((preference, newValue) -> {
                        String strNewValue = (String) newValue;
                        try {
                            int i = Integer.parseInt(strNewValue);
                            if (i < -1 || i > 40) {
                                throw new Exception();
                            }
                        } catch (Exception e) {
                            Toast.makeText(
                                    getContext(),
                                    R.string.hint_not_in_range,
                                    Toast.LENGTH_LONG
                            ).show();
                            return false;
                        }
                        return true;
                    });
            getPreferenceManager().findPreference("volte_width_dp")
                    .setOnPreferenceChangeListener((preference, newValue) -> {
                        String strNewValue = (String) newValue;
                        try {
                            int i = Integer.parseInt(strNewValue);
                            if (i < -2 || i > 100) {
                                throw new Exception();
                            }
                        } catch (Exception e) {
                            Toast.makeText(
                                    getContext(),
                                    R.string.hint_not_in_range,
                                    Toast.LENGTH_LONG
                            ).show();
                            return false;
                        }
                        return true;
                    });
            getPreferenceManager().findPreference("volte_height_dp")
                    .setOnPreferenceChangeListener((Preference.OnPreferenceChangeListener) (preference, newValue) -> {
                        String strNewValue = (String) newValue;
                        try {
                            int i = Integer.parseInt(strNewValue);
                            if (i < -2 || i > 50) {
                                throw new Exception();
                            }
                        } catch (Exception e) {
                            Toast.makeText(
                                    getContext(),
                                    R.string.hint_not_in_range,
                                    Toast.LENGTH_LONG
                            ).show();
                            return false;
                        }
                        return true;
                    });
            getPreferenceManager().findPreference("volte_substitution_icon_index")
                    .setOnPreferenceChangeListener((preference, newValue) -> {
                        String strNewValue = (String) newValue;
                        try {
                            int i = Integer.parseInt(strNewValue);
                            if (i >= 0 && i < VolteSubstitutionIcons.RESOURCES.length) {
                                Drawable icon = ResourcesCompat.getDrawable(
                                        getResources(),
                                        VolteSubstitutionIcons.RESOURCES[i],
                                        null);
                                icon.setColorFilter(0xff888888, PorterDuff.Mode.MULTIPLY);

                                preference.setIcon(
                                        icon
                                );
                            } else {
                                preference.setIcon(
                                        null
                                );
                            }
                        } catch (Exception e) {
                            Toast.makeText(
                                    getContext(),
                                    R.string.hint_not_in_range,
                                    Toast.LENGTH_LONG
                            ).show();
                            return false;
                        }
                        return true;
                    });

            ListPreference iconPreference =
                    getPreferenceManager().findPreference("volte_substitution_icon_index");
            iconPreference.callChangeListener(iconPreference.getValue());

            getPreferenceManager().findPreference("double_tap_on_aod_to_wake")
                    .setOnPreferenceChangeListener((preference, newValue) -> {
                        String strNewValue = (String) newValue;
                        try {
                            int i = Integer.parseInt(strNewValue);
                            if (i < -1 || i > 1) {
                                throw new Exception();
                            }
                        } catch (Exception e) {
                            Toast.makeText(
                                    getContext(),
                                    R.string.hint_not_in_range,
                                    Toast.LENGTH_LONG
                            ).show();
                            return false;
                        }
                        return true;
                    });

            getPreferenceManager().findPreference("reverse_launcher_all_app_own_order")
                    .setOnPreferenceChangeListener((preference, newValue) -> {
                        String strNewValue = (String) newValue;
                        try {
                            int i = Integer.parseInt(strNewValue);
                            if (i < -1 || i > 1) {
                                throw new Exception();
                            }
                        } catch (Exception e) {
                            Toast.makeText(
                                    getContext(),
                                    R.string.hint_not_in_range,
                                    Toast.LENGTH_LONG
                            ).show();
                            return false;
                        }
                        return true;
                    });

            return super.onCreateView(inflater, container, savedInstanceState);
        }

        void fixPremissionsAsync() {
            if (false) {
                AsyncTask.execute(() -> {
                    File pkgDirectory = getContext().createDeviceProtectedStorageContext()
                            .getFilesDir().getParentFile();
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
}