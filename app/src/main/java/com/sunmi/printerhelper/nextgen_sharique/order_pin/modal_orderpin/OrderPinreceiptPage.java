package com.sunmi.printerhelper.nextgen_sharique.order_pin.modal_orderpin;


import android.app.Dialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tafani.sunmi.printer.R;
import com.sunmi.printerhelper.local_set.LocalSetLanguage;
import com.sunmi.printerhelper.nextgen_sharique.application_sharepreference.MyApplication;
import com.sunmi.printerhelper.nextgen_sharique.database.offline.ServiceRepository;
import com.sunmi.printerhelper.nextgen_sharique.order_pin.adapter_orderpin.OrderPinAdapterReceiptPage;
import com.sunmi.printerhelper.nextgen_sharique.order_pin.adapter_orderpin.OrderPinModalReceiptPage;
import com.sunmi.printerhelper.utils.BluetoothUtil;
import com.sunmi.printerhelper.utils.SunmiPrintHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class OrderPinreceiptPage extends AppCompatActivity implements View.OnClickListener{



    private Dialog dialog;

    Button close_button,print_button;
    String mpinString="";

     RecyclerView recycler_view;

    String dateOfBirthString;
    MyApplication myApplication;

    String languageToUse="";

    ArrayList<OrderPinModalReceiptPage> arrayList_OrderPinModalReceiptPage;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            myApplication = (MyApplication) getApplicationContext();
            languageToUse=myApplication.getmSharedPreferences().getString("languageToUse","");

            if (languageToUse.trim().length() == 0) {

                languageToUse = "ar";
                LocalSetLanguage.LocalSet(languageToUse, OrderPinreceiptPage.this);
            }
            else
            {
                LocalSetLanguage.LocalSet(languageToUse, OrderPinreceiptPage.this);
            }



           ServiceRepository serviceRepository = new ServiceRepository(OrderPinreceiptPage.this);

            Log.e("-----BULK----",""+serviceRepository.getList_bulkdownlaod_offline());
            Log.e("-----BULK----",""+serviceRepository.getList_bulkdownlaod_offline());
            Log.e("-----BULK----",""+serviceRepository.getList_posstock_s_download());
            Log.e("-----BULK----",""+serviceRepository.getList_posstock_O_download());
            Log.e("-----BULK----",""+serviceRepository.getList_service_operator_download_offline());
            Log.e("-----BULK----",""+serviceRepository.getList_service_product_download_offline());

           // serviceRepository.deleteAllProducts();
           // serviceRepository.deleteAllOperators();
          //  serviceRepository.deleteAllBullkDownload();

            Log.e("-----BULK----",""+serviceRepository.getList_bulkdownlaod_offline());




            setContentView(R.layout.orderpin_ret_reciptpage);


            close_button = (Button) findViewById(R.id.close_button);
            close_button.setOnClickListener(this);


            print_button = (Button) findViewById(R.id.print_button);
            print_button.setOnClickListener(this);



            recycler_view = (RecyclerView)findViewById(R.id.recycler_view);

            if(languageToUse.equalsIgnoreCase("ar"))
            {
             //   imageButton1.setBackgroundResource(R.drawable.conform);

            }
            else
            {
               // imageButton1.setBackgroundResource(R.drawable.confirmbutton);

            }

            arrayList_OrderPinModalReceiptPage = new ArrayList<OrderPinModalReceiptPage>();
            arrayList_OrderPinModalReceiptPage.clear();




            String orderpin_receiptpage_api= MyApplication.getSaveString("ORDERPIN_RECEIPTPAGE_API", OrderPinreceiptPage.this);
            JSONObject jsonObject = new JSONObject(orderpin_receiptpage_api);

            JSONObject response_json = jsonObject.getJSONObject("response");


            JSONArray jsonArray_records = response_json.getJSONArray("records");

            for(int i=0;i<jsonArray_records.length();i++) {


                JSONObject jsonObject3 = jsonArray_records.getJSONObject(i);

                /*
                                String vendorcode_bulkdownlaod=jsonObject3.getString("vendorcode");
                                String productcode_bulkdownlaod=jsonObject3.getString("productcode");
                                String denomination_bulkdownlaod=jsonObject3.getString("denomination");
                                String recordcount_bulkdownlaod=jsonObject3.getString("recordcount");

                                orderPinModalReceiptPage.setVendorcode(vendorcode_bulkdownlaod);
                                orderPinModalReceiptPage.setProductcode(productcode_bulkdownlaod);
                                orderPinModalReceiptPage.setDenomination(denomination_bulkdownlaod);
                                orderPinModalReceiptPage.setRecordcount(recordcount_bulkdownlaod);
                */


                JSONArray jsonArray_r = jsonObject3.getJSONArray("r");


                for(int j=0;j<jsonArray_r.length();j++) {

                    OrderPinModalReceiptPage  orderPinModalReceiptPage = new OrderPinModalReceiptPage();


                    JSONObject jsonObject4 = jsonArray_r.getJSONObject(j);

                    if (jsonObject4.has("p")) {

                        String pinno = jsonObject4.getString("p");
                        orderPinModalReceiptPage.setSerialNo_key(pinno);





                    }

                    if (jsonObject4.has("s")) {

                        String serialno = jsonObject4.getString("s");
                        orderPinModalReceiptPage.setPinNumber_encript(serialno);


                    }

                    arrayList_OrderPinModalReceiptPage.add(orderPinModalReceiptPage);
                }

            }

            recycler_view.setLayoutManager(new LinearLayoutManager(OrderPinreceiptPage.this));

            OrderPinAdapterReceiptPage recyclerViewAdapter = new OrderPinAdapterReceiptPage(OrderPinreceiptPage.this,arrayList_OrderPinModalReceiptPage);
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
            Toast.makeText(OrderPinreceiptPage.this,e.toString(),Toast.LENGTH_LONG).show();
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

                    finish();
                }
                catch (Exception e)
                {
                    Toast.makeText(OrderPinreceiptPage.this,e.toString(),Toast.LENGTH_LONG).show();

                    e.printStackTrace();
                }

                break;

        }
    }



    private boolean isBold, isUnderLine;
    private String testFont;



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

            SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.order_pin)+" : "+getString(R.string.order_pin) + "\n", 20, isBold, isUnderLine, testFont,0);

            for(int i=0;i<arrayList_OrderPinModalReceiptPage.size();i++)
            {

              //  String[] transactionDate_array=arrayList_OrderPinModalReceiptPage.get(i).getResponsects().split(" ");

             //   String transactionDate=transactionDate_array[0];
              //  String transactionTime=transactionDate_array[1] +" "+ transactionDate_array[2];

                SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.pin_number)+" : "+arrayList_OrderPinModalReceiptPage.get(i).getPinNumber_encript() + "\n", 20, isBold, isUnderLine, testFont,0);
             //   SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.transaction_date)+" : "+transactionDate + "\n", 20, isBold, isUnderLine, testFont,0);
              //  SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.transaction_time)+" : "+transactionTime + "\n", 20, isBold, isUnderLine, testFont,0);
            //    SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.transaction_type)+" : "+arrayList_transactionHistoryRet.get(i).getTranstype() + "\n", 20, isBold, isUnderLine, testFont,0);
                SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.seriol_number)+" : "+arrayList_OrderPinModalReceiptPage.get(i).getSerialNo_key()  + "\n\n", 20, isBold, isUnderLine, testFont,0);


                SunmiPrintHelper.getInstance().printText_nextgen("\n" + "---------------", 50, isBold, isUnderLine, testFont,0);

            }

            SunmiPrintHelper.getInstance().feedPaper();


        } else {
            // SunmiPrintHelper.getInstance().printText(getString(R.string.account_balance) + " : " + amountFromServer + " LYD", 30, true, isUnderLine, testFont);
        }

    }



}
