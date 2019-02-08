package com.nanowheel.nanoux.nanowheel;

import android.app.Activity;
import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.SubMenu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.button.MaterialButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;

/**
 * The configuration screen for the {@link WidgetBatteryBar WidgetBatteryBar} AppWidget.
 */
public class WidgetBatteryBarConfigureActivity extends Activity {

    private static final String PREFS_NAME = "WidgetBatteryBar";
    private static final String PREF_PREFIX_KEY = "appwidget_";
    public static final String PREFIX_TEXT = "_text";
    public static final String PREFIX_BACK = "_back";
    int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;

    static View mView;

    MaterialButton whiteT, blackT, whiteB,blackB;
    static int textC,backC;

    View.OnClickListener mOnClickListener = new View.OnClickListener() {
        public void onClick(View v) {
            final Context context = WidgetBatteryBarConfigureActivity.this;

            // When the button is clicked, store the string locally
            //--String widgetText = mAppWidgetText.getText().toString();
            //--saveTitlePref(context, mAppWidgetId, widgetText);
            saveColor(context,mAppWidgetId,PREFIX_TEXT,textC);
            saveColor(context,mAppWidgetId,PREFIX_BACK,backC);

            // It is the responsibility of the configuration activity to updateBattery the app widget
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            WidgetBatteryBar.updateAppWidget(context, appWidgetManager, mAppWidgetId);

            // Make sure we pass back the original appWidgetId
            Intent resultValue = new Intent();
            resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
            setResult(RESULT_OK, resultValue);
            finish();
        }
    };

    public WidgetBatteryBarConfigureActivity() {
        super();
    }


    // Write the prefix to the SharedPreferences object for this widget
    static void saveColor(Context context, int appWidgetId,String type, int color) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.putInt(PREF_PREFIX_KEY + appWidgetId + type, color);
        prefs.apply();
    }

    // Read the prefix from the SharedPreferences object for this widget.
    // If there is no preference saved, get the default from a resource
    static int loadTitlePref(Context context, int appWidgetId, String type) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, 0);
        int Color = prefs.getInt(PREF_PREFIX_KEY + appWidgetId + type, 0);
        return Color;
    }


    static void deleteTitlePref(Context context, int appWidgetId, String type) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(PREFS_NAME, 0).edit();
        prefs.remove(PREF_PREFIX_KEY + appWidgetId + type);
        prefs.apply();
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        // Set the result to CANCELED.  This will cause the widget host to cancel
        // out of the widget placement if the user presses the back button.
        setResult(RESULT_CANCELED);

        textC = 0;
        backC = 1;

        setContentView(R.layout.widget_battery_bar_configure);
        findViewById(R.id.add_button).setOnClickListener(mOnClickListener);

        // Find the widget id from the intent.
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(
                    AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        // If this activity was started with an intent without an app widget ID, finish with an error.
        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {
            finish();
            return;
        }

        //mAppWidgetText.setText(loadTitlePref(WidgetBatteryBarConfigureActivity.this, mAppWidgetId));
    }

    public void showPopupBack(View view){
        final View v = view;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick a color");
        builder.setItems(names, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setBackColor(which,v);
            }
        });
        builder.show();
    }

    public void showPopupText(View view){
        final View v = view;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick a color");
        builder.setItems(names, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setTextColor(which,v);
            }
        });
        builder.show();
    }
    public static String[] ids = {"b","w"};
    public static int[] sources = {R.color.black,R.color.white};
    public static String[] names = {"Black","White"};
    static void setTextColor(int which, View v){
        WidgetBatteryBarConfigureActivity.textC = which;
        TextView hi = v.getRootView().findViewById(R.id.batteryTextC);
        hi.setTextColor(v.getResources().getColor(sources[which]));
        TextView hello = v.getRootView().findViewById(R.id.add_button_text);
        hello.setText(names[which]);
    }
    static void setBackColor(int which, View v){
        WidgetBatteryBarConfigureActivity.backC = which;
        ImageView hi = v.getRootView().findViewById(R.id.batteryBarBackC);
        hi.setImageResource(sources[which]);
        TextView hello = v.getRootView().findViewById(R.id.add_button_back);
        hello.setText(names[which]);
    }
}

