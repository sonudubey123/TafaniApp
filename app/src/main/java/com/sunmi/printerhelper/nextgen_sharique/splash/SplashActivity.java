package com.sunmi.printerhelper.nextgen_sharique.splash;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import static com.sunmi.printerhelper.nextgen_sharique.order_pin.OrderPinActivity.current_sell_dateTime;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import tafani.sunmi.printer.R;

import com.sunmi.printerhelper.nextgen_sharique.application_sharepreference.MyApplication;
import com.sunmi.printerhelper.nextgen_sharique.ase_encription.AESEncryption;
import com.sunmi.printerhelper.nextgen_sharique.languag_choose.LanguageChoose;
import com.sunmi.printerhelper.nextgen_sharique.login.LoginActivation;
import com.sunmi.printerhelper.nextgen_sharique.login.LoginActivityPin;
import com.sunmi.printerhelper.nextgen_sharique.login.SetPin;
import com.sunmi.printerhelper.nextgen_sharique.voly_url.DecryptionLogic;
import com.sunmi.printerhelper.nextgen_sharique.voly_url.Triple_DES;
import com.sunmi.printerhelper.utils.BluetoothUtil;
import com.sunmi.printerhelper.utils.SunmiPrintHelper;

import java.security.Provider;
import java.security.Security;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        initApplication();

        System.out.println("Serial number "+ MyApplication.getSerialNumber());


        try {


            //-------------------------- encryptionData  ----------------------------

         /*     String p = "69DAE1518FFBA9982279E86462E2B7A2";
            String s = "225432150060726";

            AESEncryption aseEncryption = new AESEncryption();
            String key = AESEncryption.SHA256("#@123#@123", 32); //32 bytes = 256 bit
            String iv = AESEncryption.generateRandomIV(16); //16 bytes = 128 bit
                  iv = "e816e2a32196ef0b"; //16 bytes = 128 bit

            String  encryptionData = aseEncryption.encrypt(p, key, iv);  // Encryption

            System.out.println(encryptionData);*/


            //-------------------------- Decript  ----------------------------

           /* String encryptionData="fQZMhiIGoui127UG5WHjjJJQIuhzoEBN4GQJLnSW2Qu2rOa/7Ev4ZmLjEBCykBB8";

            AESEncryption aseEncryption2 = new AESEncryption();
            String key_2 = AESEncryption.SHA256("#@123#@123", 32); //32 bytes = 256 bit
            String iv_2 = "e816e2a32196ef0b"; //16 bytes = 128 bit
            String decrrptData = aseEncryption2.decrypt(encryptionData, key_2, iv_2);

            System.out.println(decrrptData);
            System.out.println(decrrptData);*/

          /*  DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            Date dateobj = new Date();

            String  currentDateTime=df.format(dateobj);
            System.out.println(currentDateTime);
*/



/*
            if (!BluetoothUtil.isBlueToothPrinter) {

                Toast.makeText(SplashActivity.this, "---------IF--------", Toast.LENGTH_SHORT).show();

            } else {
                Toast.makeText(SplashActivity.this, "---------ELSE --------", Toast.LENGTH_SHORT).show();
            }
*/

          /*  String pin_number_temp = "8401054522000000001";
            pin_number_temp = pin_number_temp.replaceAll("...", "$0 ");
            System.out.println(pin_number_temp);   // 840 105 452 291 0
            System.out.println(pin_number_temp);*/


        }
        catch (Exception e)
        {
            Toast.makeText(SplashActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    private void initApplication() {


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                PermissionListener permissionlistener = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {


                        SharedPreferences prefs = getSharedPreferences("EU_MPIN", MODE_PRIVATE);
                        String checkLogin = prefs.getString("glo_login", null);

                        if(checkLogin == null||checkLogin.equalsIgnoreCase("splash_page"))
                        {
                            SharedPreferences.Editor Eeditor = SplashActivity.this.getSharedPreferences("EU_MPIN", MODE_PRIVATE).edit();
                            Eeditor.putString("glo_login", "splash_page");
                            Eeditor.commit();

                            Intent intent = new Intent(SplashActivity.this, LanguageChoose.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }

                        else if(checkLogin.equalsIgnoreCase("language_choose"))
                        {
                            SharedPreferences.Editor Eeditor = SplashActivity.this.getSharedPreferences("EU_MPIN", MODE_PRIVATE).edit();
                            Eeditor.putString("glo_login", "language_choose");
                            Eeditor.commit();

                            Intent intent = new Intent(SplashActivity.this, LanguageChoose.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }

                        else if(checkLogin.equalsIgnoreCase("glo_login"))
                        {
                            SharedPreferences.Editor Eeditor = SplashActivity.this.getSharedPreferences("EU_MPIN", MODE_PRIVATE).edit();
                            Eeditor.putString("glo_login", "glo_login");
                            Eeditor.commit();

                            //  Intent intent = new Intent(SplashActivity.this, LoginActivity.class);

                            Intent intent = new Intent(SplashActivity.this, LoginActivityPin.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }

                        else if(checkLogin.equalsIgnoreCase("glo_activation"))
                        {
                            SharedPreferences.Editor Eeditor = SplashActivity.this.getSharedPreferences("EU_MPIN", MODE_PRIVATE).edit();
                            Eeditor.putString("glo_login", "glo_activation");
                            Eeditor.commit();

                            //  Intent intent = new Intent(SplashActivity.this, LoginActivity.class);

                            Intent intent = new Intent(SplashActivity.this, LoginActivation.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }

                        else if(checkLogin.equalsIgnoreCase("set_pin"))
                        {
                            SharedPreferences.Editor Eeditor = SplashActivity.this.getSharedPreferences("EU_MPIN", MODE_PRIVATE).edit();
                            Eeditor.putString("glo_login", "set_pin");
                            Eeditor.commit();


                            //  Intent intent = new Intent(SplashActivity.this, LoginActivity.class);

                            Intent intent = new Intent(SplashActivity.this, SetPin.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }






                        else if(checkLogin.equalsIgnoreCase("main_activity_retailer"))
                        {
                            SharedPreferences.Editor Eeditor = SplashActivity.this.getSharedPreferences("EU_MPIN", MODE_PRIVATE).edit();
                            Eeditor.putString("glo_login", "main_activity_retailer");
                            Eeditor.commit();



                  /*  Intent intent = new Intent(SplashActivity.this, MainActivityRet.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();*/

                            Intent intent = new Intent(SplashActivity.this, LoginActivityPin.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();


                        }

                        else if(checkLogin.equalsIgnoreCase("main_activity_distributor"))
                        {
                            SharedPreferences.Editor Eeditor = SplashActivity.this.getSharedPreferences("EU_MPIN", MODE_PRIVATE).edit();
                            Eeditor.putString("glo_login", "main_activity_distributor");
                            Eeditor.commit();

                    /*Intent intent = new Intent(SplashActivity.this, MainActivityDis.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();*/

                            Intent intent = new Intent(SplashActivity.this, LoginActivityPin.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();

                        }
                        else {

                            SharedPreferences.Editor Eeditor = SplashActivity.this.getSharedPreferences("EU_MPIN", MODE_PRIVATE).edit();
                            Eeditor.putString("glo_login", "glo_login");
                            Eeditor.commit();

                            //  Intent intent = new Intent(SplashActivity.this, LoginActivity.class);

                            Intent intent = new Intent(SplashActivity.this, LoginActivityPin.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {
                        Toast.makeText(SplashActivity.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT)
                                .show();
                    }


                };

                TedPermission.with(SplashActivity.this)
                        .setPermissionListener(permissionlistener)
                        .setDeniedMessage("If you reject permission, you can not use this service.\n\nPlease turn on permissions at [Setting] > [Permission]")
                        .setGotoSettingButtonText("Go to settings")
                        .setPermissions(READ_PHONE_STATE) //
                        //                 .setPermissions(CAMERA, READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE)
                        .check();

                // This method will be executed once the timer is over

            }
        }, 2000);


    }
}
