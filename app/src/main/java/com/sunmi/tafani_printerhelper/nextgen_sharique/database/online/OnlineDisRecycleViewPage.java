package com.sunmi.tafani_printerhelper.nextgen_sharique.database.online;


import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sunmi.tafani_printerhelper.local_set.LocalSetLanguage;
import com.sunmi.tafani_printerhelper.nextgen_sharique.application_sharepreference.MyApplication;
import com.sunmi.tafani_printerhelper.nextgen_sharique.modal.OnlineListModal;
import com.sunmi.tafani_printerhelper.utils.BluetoothUtil;
import com.sunmi.tafani_printerhelper.utils.SunmiPrintHelper;

import com.sunmi.tafani_printerhelper.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class OnlineDisRecycleViewPage extends AppCompatActivity implements View.OnClickListener{

    Button close_button,print_button;


    private Dialog dialog;

    ImageButton imageButton1;
    String mpinString="";

     RecyclerView recycler_view;

    String dateOfBirthString;
    MyApplication myApplication;

    String languageToUse="";

    ArrayList<OnlineListModal> arrayList_OnlineListModal;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            myApplication = (MyApplication) getApplicationContext();
            languageToUse=myApplication.getmSharedPreferences().getString("languageToUse","");

            if (languageToUse.trim().length() == 0) {

                languageToUse = "ar";
                LocalSetLanguage.LocalSet(languageToUse, OnlineDisRecycleViewPage.this);
            }
            else
            {
                LocalSetLanguage.LocalSet(languageToUse, OnlineDisRecycleViewPage.this);
            }

            setContentView(R.layout.online_recycleview_list_dis);

            close_button = (Button) findViewById(R.id.close_button);
            close_button.setOnClickListener(this);


            print_button = (Button) findViewById(R.id.print_button);
            print_button.setOnClickListener(this);


            recycler_view = (RecyclerView)findViewById(R.id.recycler_view);

            if(languageToUse.equalsIgnoreCase("ar"))
            {
              //  imageButton1.setBackgroundResource(R.drawable.conform);
            }
            else
            {
             //  imageButton1.setBackgroundResource(R.drawable.confirmbutton);
            }

            arrayList_OnlineListModal = new ArrayList<OnlineListModal>();
            arrayList_OnlineListModal.clear();


            String ONLINE_LIST_DIS_API= MyApplication.getSaveString("ONLINE_PINSALE_DIS_API", OnlineDisRecycleViewPage.this);
            JSONObject jsonObject = new JSONObject(ONLINE_LIST_DIS_API);

            JSONObject response_json = jsonObject.getJSONObject("response");

            JSONArray jsonArray_records = response_json.getJSONArray("records");

            for(int i=0;i<jsonArray_records.length();i++) {

                OnlineListModal onlineListModal = new OnlineListModal();

                JSONObject jsonObject3 = jsonArray_records.getJSONObject(i);
                // String walletOwnerCode = jsonObject3.getString("walletOwnerCode");


                if (jsonObject3.has("pinno")) {
                    String pinno = jsonObject3.getString("pinno");
                    onlineListModal.setPinno(pinno);
                }

                if (jsonObject3.has("serialno")) {
                    String serialno = jsonObject3.getString("serialno");
                    onlineListModal.setSerialno(serialno);
                }


                if (jsonObject3.has("expirydate")) {
                    String expirydate = jsonObject3.getString("expirydate");
                    onlineListModal.setExpirydate(expirydate);
                }


                arrayList_OnlineListModal.add(onlineListModal);

            }

            recycler_view.setLayoutManager(new LinearLayoutManager(OnlineDisRecycleViewPage.this));

            OnlineListDisAdapter recyclerViewAdapter = new OnlineListDisAdapter(OnlineDisRecycleViewPage.this,arrayList_OnlineListModal);
            recycler_view.setAdapter(recyclerViewAdapter);





        }
        catch (Exception e)
        {
            Toast.makeText(OnlineDisRecycleViewPage.this,e.toString(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }





    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.close_button:


                finish();

                break;



            case R.id.print_button:


                try {

                     print_pos_data();
                }
                catch (Exception e)
                {
                    Toast.makeText(OnlineDisRecycleViewPage.this,e.toString(),Toast.LENGTH_LONG).show();

                    e.printStackTrace();
                }

                break;

        }


    }

    boolean isBold, isUnderLine;
    String testFont;

    protected void print_pos_data() {

        if (!BluetoothUtil.isBlueToothPrinter) {


            Bitmap bitmap = null;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inTargetDensity = 200;
            options.inDensity = 200;

            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo, options);
            SunmiPrintHelper.getInstance().printBitmap_nextgen(bitmap,1);

            SunmiPrintHelper.getInstance().printText_nextgen("\n\n"+ getString(R.string.retailer_capital)+  "  " + "\n", 30, true, isUnderLine, testFont,1);
            SunmiPrintHelper.getInstance().printText_nextgen("---------------" + "\n", 50, isBold, isUnderLine, testFont,0);

            SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.transaction_type)+" : "+getString(R.string.online) + "\n", 20, isBold, isUnderLine, testFont,0);

            for(int i=0;i<arrayList_OnlineListModal.size();i++)
            {

                SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.pin_number)+" : "+arrayList_OnlineListModal.get(i).getPinno() + "\n", 20, isBold, isUnderLine, testFont,0);
                SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.serial_no)+" : "+arrayList_OnlineListModal.get(i).getSerialno() + "\n", 20, isBold, isUnderLine, testFont,0);
                SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.expiry_date)+" : "+arrayList_OnlineListModal.get(i).getExpirydate() + "\n\n", 20, isBold, isUnderLine, testFont,0);
            }

            SunmiPrintHelper.getInstance().printText_nextgen("---------------" + "\n", 50, isBold, isUnderLine, testFont,0);


            SunmiPrintHelper.getInstance().feedPaper();


        } else {
            // SunmiPrintHelper.getInstance().printText(getString(R.string.account_balance) + " : " + amountFromServer + " LYD", 30, true, isUnderLine, testFont);
        }

    }


/*
    protected void print_pos_data() {

        if (!BluetoothUtil.isBlueToothPrinter) {

            if(languageToUse.equalsIgnoreCase("ar"))
            {
                SunmiPrintHelper.getInstance().printText(" "+ getString(R.string.retailer_capital)+  "         " + "\n", 30, true, isUnderLine, testFont);
                SunmiPrintHelper.getInstance().printText("---------------" + "\n", 50, isBold, isUnderLine, testFont);
                // SunmiPrintHelper.getInstance().printText(getString(R.string.transaction_type)+"   :"+getString(R.string.transaction_history) + "\n", 20, isBold, isUnderLine, testFont);

                for(int i=0;i<arrayList_OnlineListModal.size();i++)
                {
                    SunmiPrintHelper.getInstance().printText(getString(R.string.pin_number)+"      :             "+arrayList_OnlineListModal.get(i).getPinno()+ "\n", 20, isBold, isUnderLine, testFont);

                    String[] transactionDate_array=arrayList_OnlineListModal.get(i).getResponsects().split(" ");

                    String transactionDate=transactionDate_array[0];
                    String transactionTime=transactionDate_array[1] +" "+ transactionDate_array[2];

                    SunmiPrintHelper.getInstance().printText(getString(R.string.transaction_date)+"             :   "+transactionDate + "\n", 20, isBold, isUnderLine, testFont);
                    SunmiPrintHelper.getInstance().printText(getString(R.string.transaction_time)+"             :   "+transactionTime + "\n", 20, isBold, isUnderLine, testFont);
                    SunmiPrintHelper.getInstance().printText(getString(R.string.transaction_type) +"           :     "+arrayList_OnlineListModal.get(i).getTranstype()  + " \n", 20, isBold, isUnderLine, testFont);
                    SunmiPrintHelper.getInstance().printText(getString(R.string.amount) +          "            :             "+arrayList_OnlineListModal.get(i).getAmount() +"\n\n" , 20, isBold, isUnderLine, testFont);

                }

                SunmiPrintHelper.getInstance().printText("\n" + "---------------", 50, isBold, isUnderLine, testFont);

                SunmiPrintHelper.getInstance().feedPaper();

            }
            else
            {

                SunmiPrintHelper.getInstance().printText("         "+ getString(R.string.retailer_capital)+  "  " + "\n", 30, true, isUnderLine, testFont);
                SunmiPrintHelper.getInstance().printText("---------------" + "\n", 50, isBold, isUnderLine, testFont);
                // SunmiPrintHelper.getInstance().printText(getString(R.string.transaction_type)+"   :"+getString(R.string.transaction_history) + "\n", 20, isBold, isUnderLine, testFont);


                for(int i=0;i<arrayList_OnlineListModal.size();i++)
                {
                    SunmiPrintHelper.getInstance().printText(getString(R.string.transaction_id)+"     : "   +arrayList_OnlineListModal.get(i).getTransid()+ "\n", 20, isBold, isUnderLine, testFont);

                    String[] transactionDate_array=arrayList_OnlineListModal.get(i).getResponsects().split(" ");

                    String transactionDate=transactionDate_array[0];
                    String transactionTime=transactionDate_array[1] +" "+ transactionDate_array[2];

                    SunmiPrintHelper.getInstance().printText(getString(R.string.transaction_date)+"   : "   +transactionDate + "\n", 20, isBold, isUnderLine, testFont);
                    SunmiPrintHelper.getInstance().printText(getString(R.string.transaction_time)+"   : "   +transactionTime + "\n", 20, isBold, isUnderLine, testFont);

                    SunmiPrintHelper.getInstance().printText(getString(R.string.transaction_type) +"   : "   + arrayList_OnlineListModal.get(i).getTranstype()  + " \n", 20, isBold, isUnderLine, testFont);
                    SunmiPrintHelper.getInstance().printText(getString(R.string.amount) +        "             : "   + arrayList_OnlineListModal.get(i).getAmount() +"\n\n" , 20, isBold, isUnderLine, testFont);

                }

                SunmiPrintHelper.getInstance().printText("\n" + "---------------", 50, isBold, isUnderLine, testFont);

                SunmiPrintHelper.getInstance().feedPaper();


            }


        } else {
            // SunmiPrintHelper.getInstance().printText(getString(R.string.account_balance) + " : " + amountFromServer + " LYD", 30, true, isUnderLine, testFont);
        }

    }
*/

}
