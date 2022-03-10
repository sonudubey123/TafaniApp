package com.sunmi.printerhelper.nextgen_sharique.transaction_history.distributor;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tafani.sunmi.printer.R;
import com.sunmi.printerhelper.local_set.LocalSetLanguage;
import com.sunmi.printerhelper.nextgen_sharique.application_sharepreference.MyApplication;
import com.sunmi.printerhelper.nextgen_sharique.modal.TransactionHistoryRetModal;
import com.sunmi.printerhelper.nextgen_sharique.transaction_history.retailer.TransactionHistoryRetAdapter;
import com.sunmi.printerhelper.utils.BluetoothUtil;
import com.sunmi.printerhelper.utils.SunmiPrintHelper;

import android.app.Dialog;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Estel_WP on 28-Mar-19.
 */

public class TransactionDetailsDistributorRetRecycleView extends AppCompatActivity implements View.OnClickListener{

    Button close_button,print_button;


    private Dialog dialog;

    ImageButton imageButton1;
    String mpinString="";

     RecyclerView recycler_view;

    String dateOfBirthString;
    MyApplication myApplication;

    String languageToUse="";

    ArrayList<TransactionHistoryRetModal> arrayList_transactionHistoryRet;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            myApplication = (MyApplication) getApplicationContext();
            languageToUse=myApplication.getmSharedPreferences().getString("languageToUse","");

            if (languageToUse.trim().length() == 0) {

                languageToUse = "ar";
                LocalSetLanguage.LocalSet(languageToUse, TransactionDetailsDistributorRetRecycleView.this);
            }
            else
            {
                LocalSetLanguage.LocalSet(languageToUse,TransactionDetailsDistributorRetRecycleView.this);
            }

            setContentView(R.layout.transhistory_dis_recycleview);

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

            arrayList_transactionHistoryRet = new ArrayList<TransactionHistoryRetModal>();
            arrayList_transactionHistoryRet.clear();


            String TRANSACTION_HISTORY_RET_API= MyApplication.getSaveString("TRANSACTION_HISTORY_DES_API", TransactionDetailsDistributorRetRecycleView.this);
            JSONObject jsonObject = new JSONObject(TRANSACTION_HISTORY_RET_API);

            JSONObject response_json = jsonObject.getJSONObject("response");


            JSONArray jsonArray_records = response_json.getJSONArray("records");

            for(int i=0;i<jsonArray_records.length();i++) {

                TransactionHistoryRetModal transactionHistoryRetModal = new TransactionHistoryRetModal();

                JSONObject jsonObject3 = jsonArray_records.getJSONObject(i);
                // String walletOwnerCode = jsonObject3.getString("walletOwnerCode");


                if (jsonObject3.has("transid")) {
                    String transid = jsonObject3.getString("transid");
                    transactionHistoryRetModal.setTransid(transid);
                }

                if (jsonObject3.has("responsects")) {
                    String dateTime = jsonObject3.getString("responsects");
                    transactionHistoryRetModal.setResponsects(dateTime);
                }


                if (jsonObject3.has("walletbalance")) {
                    String wallet_balance = jsonObject3.getString("walletbalance");
                    transactionHistoryRetModal.setWalletbalance(wallet_balance);
                }

                if (jsonObject3.has("amount")) {
                    String amount = jsonObject3.getString("amount");
                    transactionHistoryRetModal.setAmount(amount);
                }

                if (jsonObject3.has("transtype")) {
                    String transtype = jsonObject3.getString("transtype");
                    transactionHistoryRetModal.setTranstype(transtype);
                }


                arrayList_transactionHistoryRet.add(transactionHistoryRetModal);

            }

            recycler_view.setLayoutManager(new LinearLayoutManager(TransactionDetailsDistributorRetRecycleView.this));

            TransactionHistoryRetAdapter recyclerViewAdapter = new TransactionHistoryRetAdapter(TransactionDetailsDistributorRetRecycleView.this,arrayList_transactionHistoryRet);
             //   recyclerView.setHasFixedSize(true);
              //  RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
              //  recycler_view.setLayoutManager(mLayoutManager);
              //  recycler_view.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
                recycler_view.setAdapter(recyclerViewAdapter);
           //     recyclerView.setAdapter(new ScaleInAnimationAdapter(recyclerViewAdapter));
             //   recyclerView.setAdapter(new ScaleInAnimationAdapter(recyclerViewAdapter));   // for animation




        }
        catch (Exception e)
        {
            Toast.makeText(TransactionDetailsDistributorRetRecycleView.this,e.toString(),Toast.LENGTH_LONG).show();
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
                    Toast.makeText(TransactionDetailsDistributorRetRecycleView.this,e.toString(),Toast.LENGTH_LONG).show();

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

            SunmiPrintHelper.getInstance().printText_nextgen("\n\n"+ getString(R.string.distributor_capital)+  "  " + "\n", 30, true, isUnderLine, testFont,1);
            SunmiPrintHelper.getInstance().printText_nextgen("---------------" + "\n", 50, isBold, isUnderLine, testFont,0);

            SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.transaction_type)+" : "+getString(R.string.transaction_history) + "\n", 20, isBold, isUnderLine, testFont,0);

            for(int i=0;i<arrayList_transactionHistoryRet.size();i++)
            {
                String[] transactionDate_array=arrayList_transactionHistoryRet.get(i).getResponsects().split(" ");

                String transactionDate=transactionDate_array[0];
                String transactionTime=transactionDate_array[1] +" "+ transactionDate_array[2];

                SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.transaction_id)+" : "+arrayList_transactionHistoryRet.get(i).getTransid() + "\n", 20, isBold, isUnderLine, testFont,0);
                SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.transaction_date)+" : "+transactionDate + "\n", 20, isBold, isUnderLine, testFont,0);
                SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.transaction_time)+" : "+transactionTime + "\n", 20, isBold, isUnderLine, testFont,0);
                SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.transaction_type)+" : "+arrayList_transactionHistoryRet.get(i).getTranstype() + "\n", 20, isBold, isUnderLine, testFont,0);
                SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.amount)+" : "+arrayList_transactionHistoryRet.get(i).getAmount()  + "\n\n", 20, isBold, isUnderLine, testFont,0);
            }

            SunmiPrintHelper.getInstance().feedPaper();


        } else {
            // SunmiPrintHelper.getInstance().printText(getString(R.string.account_balance) + " : " + amountFromServer + " LYD", 30, true, isUnderLine, testFont);
        }

    }

}
