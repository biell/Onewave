package com.nanowheel.nanoux.nanowheel;

import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;

public class WidgetUpdater {


    public static void updateBattery(Application app){
        int[] ids = AppWidgetManager.getInstance(app).getAppWidgetIds(new ComponentName(app, WidgetBatteryBar.class));
        if (ids.length > 0) {
            Intent intent = new Intent(app, WidgetBatteryBar.class);
            intent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
            app.sendBroadcast(intent);
        }
    }
    public static void updateSpeed(Application app){

    }
    public static void updateRange(Application app){

    }
    public static void updateLog(Application app){

    }

    public static void checkLogWarning(int a, FragmentMap b){

    }

    public static void setupLog(Application app){

    }
    public static void markLog(){

    }
    public static void revLog(){

    }
    public static void endLog(){

    }
    public static boolean backProcess(int a, FragmentMap b){
        return false;
    }

}
