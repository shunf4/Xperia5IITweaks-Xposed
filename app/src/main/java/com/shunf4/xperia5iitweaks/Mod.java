package com.shunf4.xperia5iitweaks;

import android.content.res.XModuleResources;
import android.content.res.XResources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_InitPackageResources;
import de.robv.android.xposed.callbacks.XC_LayoutInflated;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

class VolteSubstitutionIcons {
    static final int[] RESOURCES = new int[]{
            R.drawable.stat_sys_volte_hd_14x17,
            R.drawable.stat_sys_volte_hd_wrap_square_14x14,
            R.drawable.stat_sys_volte_volte_italic_29x11,
            R.drawable.stat_sys_volte_volte_wrap_30_4x24,
            R.drawable.stat_sys_volte_volte_wrap_square_14x14,
    };
}

public class Mod implements IXposedHookLoadPackage, IXposedHookZygoteInit, IXposedHookInitPackageResources {
    String MODULE_PATH = "";
    public static final String PACKAGE_NAME = Mod.class.getPackage().getName();
    XSharedPreferences prefs = null;
    Map<Object, List<?>> cachedReversedList = new HashMap<>();

    @Override
    public void handleLoadPackage(XC_LoadPackage.LoadPackageParam lpparam) throws Throwable {
        if (lpparam.packageName.equals("com.android.systemui")) {
            XposedBridge.log("x5iitweaks: loaded app: " + lpparam.packageName);

            int doubleTapOnAodToWake = Integer.parseInt(prefs.getString("double_tap_on_aod_to_wake", "-1"));

            XposedBridge.log("doubleTapOnAodToWake: " + doubleTapOnAodToWake);

            if (doubleTapOnAodToWake == 0) {
                XposedHelpers.findAndHookMethod("com.android.systemui.statusbar.phone.StatusBar",
                        lpparam.classLoader, "wakeUpIfDozing",
                        long.class, View.class, String.class, new XC_MethodHook() {
                            @Override
                            protected void beforeHookedMethod(MethodHookParam mhparam) throws Throwable {
                                XposedBridge.log("x5iitweaks: block wakeUpIfDozing preventing double click on AOD to wake!");
                                mhparam.setResult(null);
                            }
                        });
            }
        }

        if (lpparam.packageName.equals("com.sonymobile.launcher")) {
            XposedBridge.log("x5iitweaks: loaded app: " + lpparam.packageName);

            int reverseLauncherAllAppOwnOrder = Integer.parseInt(prefs.getString("reverse_launcher_all_app_own_order", "-1"));

            XposedBridge.log("reverseLauncherAllAppOwnOrder: " + reverseLauncherAllAppOwnOrder);

            if (reverseLauncherAllAppOwnOrder == 1) {
                XposedHelpers.findAndHookMethod("com.sonymobile.launcher.allapps.OwnOrderAppsList",
                        lpparam.classLoader, "getAdapterItems",
                        new XC_MethodHook() {
                            @Override
                            protected void afterHookedMethod(MethodHookParam mhparam) throws Throwable {
                                mhparam.setResult(cachedReversedList.computeIfAbsent(mhparam.thisObject, k -> {
                                    XposedBridge.log("x5iitweaks: reverse OwnOrderAppsList#getAdapterItems computing...");
                                    List<?> listApps = (List<?>) mhparam.getResult();
                                    return new AbstractList<Object>() {
                                        @Override
                                        public int size() {
                                            return listApps.size();
                                        }

                                        @Override
                                        public Object get(int i) {
                                            return listApps.get(listApps.size() - i - 1);
                                        }
                                    };
                                }));
                            }
                        });
            }
        }
    }

    @Override
    public void initZygote(StartupParam startupParam) throws Throwable {
        MODULE_PATH = startupParam.modulePath;

        prefs = new XSharedPreferences(PACKAGE_NAME, "settings");
        prefs.makeWorldReadable();
        prefs.reload();

        XposedBridge.log("x5iitweaks: preferences: " + prefs.getFile().getParent());

        if (startupParam.startsSystemServer) {
            XposedBridge.log("x5iitweaks: in initZygote, startupParam: " + startupParam.toString());
        }
    }

    @Override
    public void handleInitPackageResources(XC_InitPackageResources.InitPackageResourcesParam resparam) throws Throwable {
        if (resparam.packageName.equals("com.android.systemui")) {
            XposedBridge.log("x5iitweaks: loaded app resource: " + resparam.packageName);
            int volteMarginEnd = Integer.parseInt(prefs.getString("volte_margin_end_dp", "-1"));
            int volteWidthDp = Integer.parseInt(prefs.getString("volte_width_dp", "-1"));
            int volteHeightDp = Integer.parseInt(prefs.getString("volte_height_dp", "-1"));
            int volteSubstitutionIconIndex =
                    Integer.parseInt(prefs.getString("volte_substitution_icon_index", "-1"));
            String navBarLayout =
                    prefs.getString("nav_bar_layout_str", "");
            navBarLayout = navBarLayout.replaceAll("\\s", "");

            XposedBridge.log("volteMarginEnd: " + volteMarginEnd);
            XposedBridge.log("volteWidthDp: " + volteWidthDp);
            XposedBridge.log("volteHeightDp: " + volteHeightDp);
            XposedBridge.log("volteSubstitutionIconIndex: " + volteSubstitutionIconIndex);

            float pixelsPerDp = resparam.res
                    .getDisplayMetrics()
                    .density;

//            XModuleResources modRes = XModuleResources.createInstance(MODULE_PATH, resparam.res);
//            resparam.res.setReplacement(
//                    "com.android.systemui",
//                    "dimen",
//                    "status_bar_volte_margin_end",
//                    modRes.fwd(R.dimen.status_bar_volte_margin_end_replaced));

            if (volteMarginEnd >= 0 && volteMarginEnd <= 40) {
                resparam.res.setReplacement(
                        "com.android.systemui",
                        "dimen",
                        "status_bar_volte_margin_end",
                        Integer.toString(volteMarginEnd) + "dp");
            }

            if ((volteWidthDp >= 0 && volteWidthDp <= 100 || volteWidthDp == -2)
                && (volteHeightDp >= 0 && volteHeightDp <= 50 || volteHeightDp == -2)) {
                resparam.res.hookLayout(
                        "com.android.systemui",
                        "layout",
                        "status_bar_mobile_signal_group",
                        new XC_LayoutInflated() {
                            @Override
                            public void handleLayoutInflated(LayoutInflatedParam liparam) throws Throwable {
                                View volte = (View) liparam.view.findViewById(
                                        liparam.res.getIdentifier(
                                                "volte",
                                                "id",
                                                "com.android.systemui"
                                        )
                                );
                                ViewGroup.LayoutParams lp = volte.getLayoutParams();
                                lp.width = (int) (
                                        volteWidthDp == -2 ?
                                                ViewGroup.LayoutParams.WRAP_CONTENT
                                                : volteWidthDp * pixelsPerDp
                                );
                                lp.height = (int) (
                                        volteWidthDp == -2 ?
                                                ViewGroup.LayoutParams.WRAP_CONTENT
                                                : volteHeightDp * pixelsPerDp
                                );
                                volte.setLayoutParams(lp);
                            }
                        });
            }

            if (volteSubstitutionIconIndex >= 0
                    && volteSubstitutionIconIndex < VolteSubstitutionIcons.RESOURCES.length) {
                XModuleResources modRes = XModuleResources.createInstance(MODULE_PATH, resparam.res);
                resparam.res.setReplacement(
                    "com.android.systemui",
                    "drawable",
                    "stat_sys_volte",
                    modRes.fwd(
                            VolteSubstitutionIcons.RESOURCES[volteSubstitutionIconIndex]
                    ));
            }

            if (!navBarLayout.equals("")) {
                resparam.res.setReplacement(
                        "com.android.systemui",
                        "string",
                        "config_navBarLayout",
                        navBarLayout);
            }
        }
    }
}
