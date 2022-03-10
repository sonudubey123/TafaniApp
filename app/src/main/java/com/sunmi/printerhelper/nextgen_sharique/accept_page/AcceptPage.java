package com.sunmi.printerhelper.nextgen_sharique.accept_page;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import tafani.sunmi.printer.R;


public class AcceptPage extends AppCompatActivity implements View.OnClickListener {

    Button button_accept,button_notaccept;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accept_page);


        button_accept=(Button)findViewById(R.id.button_accept);
        button_notaccept=(Button)findViewById(R.id.button_notaccept);
        button_accept.setOnClickListener(this);
        button_notaccept.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.button_accept:
            {

              break;
            }

            case R.id.button_notaccept:
            {

                finish();
                break;
            }
        }
    }
}
