package com.sunmi.tafani_printerhelper.nextgen_sharique.session;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.util.Log;

public class LogoutService extends Service {
    public static CountDownTimer timer;


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful
        timer = new CountDownTimer(1 *60 * 1000, 1000) {
            public void onTick(long millisUntilFinished) {
                //Some code
                Log.v("Strted Service", "Service Started");
            }

            public void onFinish() {
                Log.v("Stop Service", "Call Logout by Service");
                // Code for Logout
                stopSelf();
            }
        };
        return Service.START_NOT_STICKY;
    }
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
