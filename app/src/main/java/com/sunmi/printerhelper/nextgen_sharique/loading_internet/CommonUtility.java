package com.sunmi.printerhelper.nextgen_sharique.loading_internet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.kaopiz.kprogresshud.KProgressHUD;
import tafani.sunmi.printer.R;

/**
 * Created by Estel_WP on 01-Apr-19.
 */

public class CommonUtility {

    private static KProgressHUD kProgressHUD;

    public static void showProgressDialog(Context context){
        kProgressHUD=   KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel(context.getResources().getString(R.string.please_wait))
                .setCancellable(false)
                .setWindowColor(context.getResources().getColor(R.color.colorPrimaryDark))
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }
    public static void hideProgressDialog(Context context){

        kProgressHUD.dismiss();
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


}
