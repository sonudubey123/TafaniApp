package com.sunmi.tafani_printerhelper.nextgen_sharique.application_sharepreference;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

import com.sunmi.tafani_printerhelper.utils.SunmiPrintHelper;

import org.json.JSONArray;

import java.lang.reflect.Method;
import java.util.regex.Pattern;


public class MyApplication extends Application {

    public static MyApplication appInstance;
    public static Boolean isSrviceStarted=false;
    private SharedPreferences mSharedPreferences;
    private String PREF_NAME = "NEXTGEN";
    private static boolean ISMobileT=false;

    public  static JSONArray jsonArray_bulkdownload= new JSONArray();
    public static Boolean isTimerStopped=false;


    public static String getSN() {
        if(ISMobileT){
           return "P210211B00388MOBILE";
        }else {


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                return Build.getSerial();
            } else
                return Build.SERIAL;
        }
    }

    public static String getSerialNumber() {
        String serialNumber;

        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);

            serialNumber = (String) get.invoke(c, "gsm.sn1");
            if (serialNumber.equals(""))
                serialNumber = (String) get.invoke(c, "ril.serialnumber");
            if (serialNumber.equals(""))
                serialNumber = (String) get.invoke(c, "ro.serialno");
            if (serialNumber.equals(""))
                serialNumber = (String) get.invoke(c, "sys.serialnumber");
            if (serialNumber.equals(""))
                serialNumber = Build.SERIAL;

            // If none of the methods above worked
            if (serialNumber.equals(""))
                serialNumber = null;
        } catch (Exception e) {
            e.printStackTrace();
            serialNumber = null;
        }

        return serialNumber;
    }

    public SharedPreferences getmSharedPreferences() {
        if (mSharedPreferences == null) {
            mSharedPreferences = getSharedPreferences(PREF_NAME, 0);
        }
        return mSharedPreferences;
    }

    public static MyApplication getInstance() {
        return appInstance;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        appInstance = this;

        init();

    }

    private void init(){
        SunmiPrintHelper.getInstance().initSunmiPrinterService(this);
    }
    public static boolean isEmail(String email) {
        return Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$").matcher(email).matches();
    }

    public static boolean email_validation(String email) {
        return Pattern.compile("^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$").matcher(email).matches();
    }

    public boolean isPassword(String pass) {
        return Pattern.compile("^(?=.*[A-Za-z])(?=.*\\\\d)(?=.*[$@$!%*#?&])[A-Za-z\\\\d$@$!%*#?&]{6,}$").matcher(pass).matches();
    }



    public static void saveString(String key, String value, Context activity) {
        SharedPreferences preferences =activity.getSharedPreferences("PROJECT_NAME", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getSaveString(String key, Context activity) {
        SharedPreferences preferences = activity.getSharedPreferences("PROJECT_NAME", Context.MODE_PRIVATE);
        String json = preferences.getString(key,"");
        return json;
    }


    public static void saveInt(String key, int value, Context activity) {
        SharedPreferences preferences =activity.getSharedPreferences("PROJECT_NAME", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static Integer getSaveInt(String key, Context activity) {
        SharedPreferences preferences = activity.getSharedPreferences("PROJECT_NAME", Context.MODE_PRIVATE);
        int json = preferences.getInt(key,0);
        return json;
    }
    public static void saveBool(String key, Boolean value, Context activity) {
        SharedPreferences preferences =activity.getSharedPreferences("PROJECT_NAME", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static Boolean getSaveBool(String key, Context activity) {
        SharedPreferences preferences = activity.getSharedPreferences("PROJECT_NAME", Context.MODE_PRIVATE);
        Boolean json = preferences.getBoolean(key,false);
        return json;
    }



}
