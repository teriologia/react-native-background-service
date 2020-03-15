package com.rnheartbeat;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.telecom.Call;
import android.util.Log;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.io.File;

import javax.annotation.Nonnull;

import static android.content.Context.ACTIVITY_SERVICE;
import static android.content.Context.LOCATION_SERVICE;

public class HeartbeatModule extends ReactContextBaseJavaModule {

    public static final String REACT_CLASS = "Heartbeat";
    private static ReactApplicationContext reactContext;
    protected final int GPS_REQ = 191823;

    public HeartbeatModule(@Nonnull ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Nonnull
    @Override
    public String getName() {
        return REACT_CLASS;
    }

    @ReactMethod
    public void startService(int test) {
        new HeartbeartService(this.reactContext, test);
        this.reactContext.startService(new Intent(this.reactContext, HeartbeartService.class));
    }

    @ReactMethod
    public void stopService() {
        this.reactContext.stopService(new Intent(this.reactContext, HeartbeartService.class));
    }

    @ReactMethod
    public void clearCache(Callback cb){
        Context context = this.reactContext;
        clearAppData(context);
//        deleteCache(context);
        cb.invoke("test");
    }

    @ReactMethod
    public void openOnOffGpsSettings(){
        Intent gpsOptionsIntent = new Intent(
                Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        gpsOptionsIntent.addCategory(Intent.CATEGORY_DEFAULT);
        gpsOptionsIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        reactContext.startActivity(gpsOptionsIntent);
    }

    @ReactMethod
    public void openPermissionSettings(){
        Intent myAppSettings = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + this.getReactApplicationContext().getPackageName()));
        myAppSettings.addCategory(Intent.CATEGORY_DEFAULT);
        myAppSettings.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        reactContext.startActivity(myAppSettings);
    }

    @ReactMethod
    public void getPermission(Callback cb){
        LocationManager manager = (LocationManager) this.reactContext.getSystemService(LOCATION_SERVICE);
        boolean statusOfGPS = manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        cb.invoke(statusOfGPS);
    }

    private void clearAppData(Context context) {
        try {
            // clearing app data
            if (Build.VERSION_CODES.KITKAT <= Build.VERSION.SDK_INT) {
                ((ActivityManager) context.getSystemService(ACTIVITY_SERVICE)).clearApplicationUserData(); // note: it has a return value!
            } else {
                String packageName = reactContext.getApplicationContext().getPackageName();
                Runtime runtime = Runtime.getRuntime();
                runtime.exec("pm clear "+packageName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




//    public static void deleteCache(Context context) {
//        try {
//            File dir = context.getCacheDir();
//            deleteDir(dir);
//            Log.d("denemeCache", "1");
//        } catch (Exception e) {
//            Log.d("denemeCache", "2");
//        }
//    }
//    public static boolean deleteDir(File dir) {
//        if (dir != null && dir.isDirectory()) {
//            Log.d("denemeCache", "3");
//            String[] children = dir.list();
//            for (int i = 0; i < children.length; i++) {
//                Log.d("denemeCache", "4");
//                boolean success = deleteDir(new File(dir, children[i]));
//                if (!success) {
//                    Log.d("denemeCache", "5");
//                    return false;
//                }
//            }
//            Log.d("denemeCache", "6");
//            return dir.delete();
//        } else if(dir!= null && dir.isFile()) {
//            Log.d("denemeCache", "7");
//            return dir.delete();
//        } else {
//            Log.d("denemeCache", "8");
//            return false;
//        }
//    }
}
