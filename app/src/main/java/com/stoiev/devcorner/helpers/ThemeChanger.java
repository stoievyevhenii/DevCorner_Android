package com.stoiev.devcorner.helpers;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Build;
import android.view.View;

public class ThemeChanger {



    public static void setTheme(View view, String elementsForChange) {
        View activityRootView;
        activityRootView = view;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkCurrentTheme(activityRootView.getRootView(), elementsForChange);
        }
    }

    private static void checkCurrentTheme(View view, String elementsForChange) {
        int currentNightMode = view.getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;
        if (currentNightMode == Configuration.UI_MODE_NIGHT_NO) {
            switch(elementsForChange){
                case "STATUS_BAR":
                    stylesForStatusBar(view);
                    break;
                case "NAVIGATION_BAR":
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        stylesForNavigationBar(view);
                    }
                    break;
                case "BOTH":
                    stylesForStatusBar(view);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        stylesForNavigationBar(view);
                    }
                    break;
            }
        }
    }

    private static void stylesForStatusBar(View view) {
        int flags = view.getSystemUiVisibility();
        flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
        view.setSystemUiVisibility(flags);
    }

    @SuppressLint("InlinedApi")
    private static void stylesForNavigationBar(View view) {
        int flags = view.getSystemUiVisibility();
        flags |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
        view.setSystemUiVisibility(flags);
    }

}
