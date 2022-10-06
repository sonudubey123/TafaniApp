package com.sunmi.tafani_printerhelper.nextgen_sharique.contact;


import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sunmi.tafani_printerhelper.local_set.LocalSetLanguage;
import com.sunmi.tafani_printerhelper.nextgen_sharique.application_sharepreference.MyApplication;

import com.sunmi.tafani_printerhelper.R;


public class WebViewUrl extends AppCompatActivity implements View.OnClickListener {


    private Dialog dialog;

    private boolean isBold, isUnderLine;
    private String testFont;


    String operatorFromServer="",transactionStatus="",transid="",transactionDate="",transactionTime="",pinNumberFromServer="",serialNumberFromServer="";

    Button imageButton1;
    EditText edittext_serialno;
    String serialnoStringh = "";

    String dateOfBirthString;
    MyApplication myApplication;

    String languageToUse = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            myApplication = (MyApplication) getApplicationContext();
            languageToUse = myApplication.getmSharedPreferences().getString("languageToUse", "");

            if (languageToUse.trim().length() == 0) {

                languageToUse = "ar";
                LocalSetLanguage.LocalSet(languageToUse, WebViewUrl.this);
            }
            else
            {
                LocalSetLanguage.LocalSet(languageToUse, WebViewUrl.this);
            }

            setContentView(R.layout.webview_url);



            WebView webView=(WebView)findViewById(R.id.webview);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl("www.tafani.ly");

            finish();
            

        } catch (Exception e) {
            Toast.makeText(WebViewUrl.this, e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


    }


    @Override
    public void onClick(View v) {

    }
}
