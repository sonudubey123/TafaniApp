package com.sunmi.printerhelper.nextgen_sharique.contact;


import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sunmi.printerhelper.local_set.LocalSetLanguage;
import com.sunmi.printerhelper.nextgen_sharique.application_sharepreference.MyApplication;

import tafani.sunmi.printer.R;


public class WebViewUrlFaceBook extends AppCompatActivity implements View.OnClickListener {


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
                LocalSetLanguage.LocalSet(languageToUse, WebViewUrlFaceBook.this);
            }
            else
            {
                LocalSetLanguage.LocalSet(languageToUse, WebViewUrlFaceBook.this);
            }

            setContentView(R.layout.webview_url);



            WebView webView=(WebView)findViewById(R.id.webview);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.loadUrl("www.facebook.com/3tlibya");

            finish();
            

        } catch (Exception e) {
            Toast.makeText(WebViewUrlFaceBook.this, e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


    }


    @Override
    public void onClick(View v) {

    }
}
