package com.sunmi.tafani_printerhelper.nextgen_sharique.contact;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sunmi.tafani_printerhelper.local_set.LocalSetLanguage;
import com.sunmi.tafani_printerhelper.nextgen_sharique.application_sharepreference.MyApplication;

import com.sunmi.tafani_printerhelper.R;


public class ContactDetails extends AppCompatActivity {


    private Dialog dialog;

    private boolean isBold, isUnderLine;
    private String testFont;
    TextView website_click, facebook_url;


    String operatorFromServer = "", transactionStatus = "", transid = "", transactionDate = "", transactionTime = "", pinNumberFromServer = "", serialNumberFromServer = "";

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
                LocalSetLanguage.LocalSet(languageToUse, ContactDetails.this);
            } else {
                LocalSetLanguage.LocalSet(languageToUse, ContactDetails.this);
            }

            setContentView(R.layout.webview_contact);

            website_click = (TextView) findViewById(R.id.website_click);
            website_click.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(ContactDetails.this, WebViewUrl.class);
                    startActivity(intent);
                    //  finish();

                }
            });

            facebook_url = (TextView) findViewById(R.id.facebook_url);
            facebook_url.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(ContactDetails.this, WebViewUrlFaceBook.class);
                    startActivity(intent);
                    //  finish();

                }
            });


        }catch (Exception e) {
            Toast.makeText(ContactDetails.this, e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


    }


}



