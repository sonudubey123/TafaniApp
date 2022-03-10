package com.sunmi.printerhelper.nextgen_sharique.languag_choose;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.sunmi.printerhelper.local_set.LocalSetLanguage;
import com.sunmi.printerhelper.nextgen_sharique.application_sharepreference.MyApplication;
import com.sunmi.printerhelper.nextgen_sharique.main_activity.MainActivityRet;

import tafani.sunmi.printer.R;
import com.sunmi.printerhelper.nextgen_sharique.login.LoginActivation;


public class LanguageChoose extends AppCompatActivity {

     MyApplication myApplication;
    String languageToUse="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        myApplication = (MyApplication) getApplicationContext();
        languageToUse=myApplication.getmSharedPreferences().getString("languageToUse","");


        if (languageToUse.trim().length() == 0) {

            languageToUse = "ar";
            LocalSetLanguage.LocalSet(languageToUse,LanguageChoose.this);
        }
        else
        {
            LocalSetLanguage.LocalSet(languageToUse,LanguageChoose.this);
        }



        setContentView(R.layout.language_choose_menu);


    }


    @Override
    protected void onResume() {
        super.onResume();

        ((Button) findViewById(R.id.englishLanguageChooseButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                myApplication.getmSharedPreferences().edit().putString("languageToUse", "en").commit();
                myApplication.getmSharedPreferences().edit().putString("isFirstRun", "NO").commit();

                Toast.makeText(LanguageChoose.this,"English Choose Language",Toast.LENGTH_LONG).show();



                if(MyApplication.getSaveString("CHANGE_LANGUAGE", LanguageChoose.this).equalsIgnoreCase("MainActivityRet"))
                {
                    Intent i = new Intent(LanguageChoose.this, MainActivityRet.class);
                    startActivity(i);
                    LanguageChoose.this.finish();
                }

              else if(MyApplication.getSaveString("CHANGE_LANGUAGE", LanguageChoose.this).equalsIgnoreCase("MainActivityDis"))
                {

                  /*  Intent i = new Intent(LanguageChoose.this, MainActivityDis.class);
                    startActivity(i);
                    LanguageChoose.this.finish();*/
                }

              else
                {
                    Intent i = new Intent(LanguageChoose.this, LoginActivation.class);
                    startActivity(i);
                    LanguageChoose.this.finish();
                }


            }
        });

        ((Button) findViewById(R.id.frenchLanguageChooseButton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myApplication.getmSharedPreferences().edit().putString("languageToUse", "ar").commit();
                myApplication.getmSharedPreferences().edit().putString("isFirstRun", "NO").commit();

                Toast.makeText(LanguageChoose.this,"اختيار اللغة العربية\n",Toast.LENGTH_LONG).show();


                if(MyApplication.getSaveString("CHANGE_LANGUAGE", LanguageChoose.this).equalsIgnoreCase("MainActivityRet"))
                {
                    Intent i = new Intent(LanguageChoose.this, MainActivityRet.class);
                    startActivity(i);
                    LanguageChoose.this.finish();
                }

                else if(MyApplication.getSaveString("CHANGE_LANGUAGE", LanguageChoose.this).equalsIgnoreCase("MainActivityDis"))
                {
                   /* Intent i = new Intent(LanguageChoose.this, MainActivityDis.class);
                    startActivity(i);
                    LanguageChoose.this.finish();*/
                }

                else
                {
                    Intent i = new Intent(LanguageChoose.this, LoginActivation.class);
                    startActivity(i);
                    LanguageChoose.this.finish();
                }

            }
        });
    }
}
