package com.sunmi.tafani_printerhelper.nextgen_sharique.order_pin;

import static com.sunmi.tafani_printerhelper.nextgen_sharique.voly_url.Url.base_url;
import static com.sunmi.tafani_printerhelper.nextgen_sharique.voly_url.Url.login_service_apinname;
import static com.sunmi.tafani_printerhelper.nextgen_sharique.voly_url.Url.ret_bulkdown_apiname;
import static com.sunmi.tafani_printerhelper.nextgen_sharique.voly_url.Url.ret_posstock_apiname;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.sunmi.tafani_printerhelper.R;
import com.sunmi.tafani_printerhelper.local_set.LocalSetLanguage;
import com.sunmi.tafani_printerhelper.nextgen_sharique.application_sharepreference.MyApplication;

import com.sunmi.tafani_printerhelper.nextgen_sharique.database.offline.OperatorOfflineModel;
import com.sunmi.tafani_printerhelper.nextgen_sharique.database.offline.ServiceRepository;
import com.sunmi.tafani_printerhelper.nextgen_sharique.database.offline.modal_offline.BulkDownloadOfflineModel;
import com.sunmi.tafani_printerhelper.nextgen_sharique.database.offline.modal_offline.PosstockODownloadOfflineModel;
import com.sunmi.tafani_printerhelper.nextgen_sharique.database.offline.modal_offline.PostockSDownloadOfflineModel;
import com.sunmi.tafani_printerhelper.nextgen_sharique.database.offline.modal_offline.ServiceOperatorDownloadOfflineModel;
import com.sunmi.tafani_printerhelper.nextgen_sharique.database.offline.modal_offline.ServiceProductDownloadOfflineModel;
import com.sunmi.tafani_printerhelper.nextgen_sharique.interf.InterHttpServerResponse;
import com.sunmi.tafani_printerhelper.nextgen_sharique.loading_internet.CommonUtility;
import com.sunmi.tafani_printerhelper.nextgen_sharique.main_activity.MainActivityRet;
import com.sunmi.tafani_printerhelper.nextgen_sharique.order_pin.adapter_orderpin.ProductAdapterOrderPin;
import com.sunmi.tafani_printerhelper.nextgen_sharique.order_pin.adapter_orderpin.DenominationAdapterOrderPin;
import com.sunmi.tafani_printerhelper.nextgen_sharique.order_pin.adapter_orderpin.OperatorAdapterOrderPin;
import com.sunmi.tafani_printerhelper.nextgen_sharique.order_pin.modal_orderpin.BulkDownlaodModalOnline;
import com.sunmi.tafani_printerhelper.nextgen_sharique.order_pin.modal_orderpin.PosStockOModalOnline;
import com.sunmi.tafani_printerhelper.nextgen_sharique.order_pin.modal_orderpin.PosStockSModalOnline;
import com.sunmi.tafani_printerhelper.nextgen_sharique.order_pin.modal_orderpin.ProductModelOrderPin;
import com.sunmi.tafani_printerhelper.nextgen_sharique.order_pin.modal_orderpin.OperatorModalOrderPin;
import com.sunmi.tafani_printerhelper.nextgen_sharique.voly_url.Md5;
import com.sunmi.tafani_printerhelper.nextgen_sharique.voly_url.VollyRequestResponse;
import com.sunmi.tafani_printerhelper.utils.BluetoothUtil;
import com.sunmi.tafani_printerhelper.utils.SunmiPrintHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class OrderPinActivity extends AppCompatActivity
        implements View.OnClickListener, InterHttpServerResponse, AdapterView.OnItemSelectedListener {

    private boolean isBold, isUnderLine;

    private AlertDialog alertDialog_orderPin;

    MyApplication myApplication;
    String languageToUse="";
    ServiceRepository serviceRepository;

  String currentserialnos="";
  String currentserialnos_temp = "";

    String transid_online="",transactionDate_online="",transactionTime_online="",footer_first_online="",footer_second_online;
    private String testFont;
    String productCode="";



    JSONArray jasonArray_productDetails_service_api;

    Spinner  spinner_operator_orderPin,spinner_product_orderpin,spinner_denomination_orderpin;

    EditText edittext_quantity,editext_popup_order;
    Button button_orderNow;

    private ArrayList<OperatorModalOrderPin> operator_pinsale = new ArrayList<>();
    private ArrayList<ProductModelOrderPin> productList_pinsale = new ArrayList<>();
    private ArrayList<ProductModelOrderPin> productList_pinsale_filter = new ArrayList<>();

/*    private ArrayList<DenominationModelOrderPin> denominationList_pinsale = new ArrayList<>();
    private ArrayList<DenominationModelOrderPin> denominationList_filter_pinsale = new ArrayList<>();
    */

    private ArrayList<BulkDownlaodModalOnline> bulkdownloadList_pinsale = new ArrayList<>();
    private ArrayList<PosStockSModalOnline> posstock_s_List_pinsale = new ArrayList<>();
    private ArrayList<PosStockOModalOnline> posstock_o_List_pinsale = new ArrayList<>();

    String select_denomination="",select_productCode="",select_vendorcode="";
    String select_operatorName="",select_operatorCode="",quantityString="",mpinString_orderPin="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {

            myApplication = (MyApplication) getApplicationContext();
            languageToUse = myApplication.getmSharedPreferences().getString("languageToUse", "");

            if (languageToUse.trim().length() == 0) {

                languageToUse = "ar";

                LocalSetLanguage.LocalSet(languageToUse, OrderPinActivity.this);

            } else {
                LocalSetLanguage.LocalSet(languageToUse, OrderPinActivity.this);
            }


            setContentView(R.layout.order_pin_activity);

            serviceRepository = new ServiceRepository(OrderPinActivity.this);

            Log.e("products--",""+serviceRepository.getAllProducts().toString());
            Log.e("operators--",""+serviceRepository.getAllOperators().toString());
            Log.e("---print_f_api_res-",""+serviceRepository.getList_printfOfflineModal());






            SharedPreferences.Editor Eeditor = OrderPinActivity.this.getSharedPreferences("EU_MPIN", MODE_PRIVATE).edit();
            Eeditor.putString("glo_login", "main_activity_retailer");
            Eeditor.commit();


            // Initialize Widgets
         /*   Toolbar toolbar_ret = (Toolbar) findViewById(R.id.toolbar_ret);
            setSupportActionBar(toolbar_ret);*/



            spinner_operator_orderPin = findViewById(R.id.spinner_operator_orderPin);
            spinner_product_orderpin = findViewById(R.id.spinner_product_orderpin);
            spinner_denomination_orderpin = findViewById(R.id.spinner_denomination_orderpin);

            spinner_operator_orderPin.setOnItemSelectedListener(this);
            spinner_product_orderpin.setOnItemSelectedListener(this);
            spinner_denomination_orderpin.setOnItemSelectedListener(this);


            operator_pinsale.clear();
            productList_pinsale.clear();
            productList_pinsale_filter.clear();
           // denominationList_pinsale.clear();

            OperatorModalOrderPin operatorModal_temp = new OperatorModalOrderPin(getString(R.string.please_select_operator),getString(R.string.please_select_operator));
            operator_pinsale.add(operatorModal_temp);
            OperatorAdapterOrderPin operatorAdapter = new OperatorAdapterOrderPin(this, operator_pinsale);
            spinner_operator_orderPin.setAdapter(operatorAdapter);

            ProductModelOrderPin productModel_temp = new ProductModelOrderPin(getString(R.string.please_select_product),getString(R.string.please_select_vendor_type),getString(R.string.please_select_denomination));
            productList_pinsale.add(productModel_temp);
            ProductAdapterOrderPin opproductAdapter = new ProductAdapterOrderPin(this, productList_pinsale);
            spinner_product_orderpin.setAdapter(opproductAdapter);

          //  DenominationModelOrderPin denomination_temp_temp = new DenominationModelOrderPin(getString(R.string.please_select_denomination));
          //  denominationList_pinsale.add(denomination_temp_temp);

            DenominationAdapterOrderPin denominationAdapter = new DenominationAdapterOrderPin(this, productList_pinsale);
            spinner_denomination_orderpin.setAdapter(denominationAdapter);



            edittext_quantity = findViewById(R.id.edittext_quantity);



            button_orderNow = findViewById(R.id.button_orderNow);
            button_orderNow.setOnClickListener(this);


            if (CommonUtility.isOnline(OrderPinActivity.this)) {


                CommonUtility.showProgressDialog(OrderPinActivity.this);

                request_service_online_volly(100);
            }
            else
            {
                Toast.makeText(this, R.string.please_check_connection, Toast.LENGTH_SHORT).show();
                finish();

            }


        }

        catch (Exception e)
        {
            Toast.makeText(OrderPinActivity.this,e.toString(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    private boolean checkValidation_orderPopup() {

        mpinString_orderPin = editext_popup_order.getText().toString().trim();

        if (mpinString_orderPin.isEmpty()) {
            Toast.makeText(OrderPinActivity.this, getResources().getString(R.string.enter_mpin), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mpinString_orderPin.length() < 4) {
            Toast.makeText(OrderPinActivity.this, getResources().getString(R.string.enter_mpin), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private Dialog dialog;

    protected void online_print_popup(String transactionStatus) {

        TextView textresponse, textheading;


        dialog = new Dialog(OrderPinActivity.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        //dialog.getWindow().setBackgroundDrawable(d);
        dialog.setContentView(R.layout.dialogbox);


        textresponse = (TextView) ((dialog)).findViewById(R.id.responsestring);


        Button dialogbutton1=(Button) ((dialog)).findViewById(R.id.dialogbutton1);

        textresponse.setText(transactionStatus);

        if(languageToUse.equalsIgnoreCase("ar"))
        {
            // dialogbutton1.setBackgroundResource(R.drawable.conform);

        }
        else
        {
            // dialogbutton1.setBackgroundResource(R.drawable.confirmbutton);
        }

        dialogbutton1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();

                // MainActivityRet.this.finish();

            }
        });

        Button printButton=(Button) ((dialog)).findViewById(R.id.printButton);
        printButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                try {
                    print_pos_data_online();
                }

                catch (Exception e)
                {
                    Toast.makeText(OrderPinActivity.this,e.toString(), Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }
                dialog.dismiss();

            }
        });

        dialog.show();
    }


    @Override
    public void onClick(View v) {
        switch(v.getId()) {


            case R.id.button_alertdialogue_cancel:
            {
                alertDialog_orderPin.cancel();
            }
            break;

            case R.id.button_alertdialogue_order:
            {
                if(checkValidation_orderPopup())
                {
                    CommonUtility.showProgressDialog(OrderPinActivity.this);

                    request_posstock_o_volly(105);

                    alertDialog_orderPin.cancel();

                }
            }
            break;

            case R.id.button_orderNow:


                quantityString = edittext_quantity.getText().toString().trim();

                if(spinner_operator_orderPin.getSelectedItemPosition()==0)
                {
                    Toast.makeText(OrderPinActivity.this, R.string.please_select_operator, Toast.LENGTH_SHORT).show();
                    return;
                }

                if(spinner_product_orderpin.getSelectedItemPosition()==0)
                {
                    Toast.makeText(OrderPinActivity.this, R.string.please_select_product, Toast.LENGTH_SHORT).show();
                    return;
                }

                if(spinner_denomination_orderpin.getSelectedItemPosition()==0)
                {
                    Toast.makeText(OrderPinActivity.this, R.string.please_select_denomination, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (quantityString.isEmpty()) {
                    Toast.makeText(OrderPinActivity.this, R.string.plz_enter_quentity, Toast.LENGTH_SHORT).show();
                    return;
                }

              /*  if (edittext_quantity.length() < 1) {
                    Toast.makeText(OrderPinActivity.this, R.string.plz_enter_quentity, Toast.LENGTH_SHORT).show();
                    return;
                }*/

                alertDialogue_orderPin();


                break;
        }
    }

    private void alertDialogue_orderPin() {

        AlertDialog.Builder builder = new AlertDialog.Builder(OrderPinActivity.this);
        View v = OrderPinActivity.this.getLayoutInflater().inflate(
                R.layout.alertdialouge_orderpin, null);

        ((Button) v.findViewById(R.id.button_alertdialogue_cancel)).setOnClickListener(this);
        ((Button) v.findViewById(R.id.button_alertdialogue_order))
                .setOnClickListener(this);

        ((TextView) v.findViewById(R.id.text_operator)).setText(getString(R.string.operator)+"  " + select_operatorName + "");


        Double double_quantity=Double.parseDouble(quantityString);
        Double double_denomination=Double.parseDouble(select_denomination);

        Double double_amount = double_quantity*double_denomination;

        String amountString=String.valueOf(double_amount);

        ((TextView) v.findViewById(R.id.textview_amount_quantity)).setText(getString(R.string.amount)+"  "+ amountString + "");
        ((TextView) v.findViewById(R.id.textview_total_quantity)).setText(getString(R.string.total_quantity)+"  " + quantityString + "");

        editext_popup_order = (EditText) v.findViewById(R.id.editext_mpin_alertDialogue);




        builder.setView(v);
        builder.setCancelable(false);

        alertDialog_orderPin = builder.create();

        alertDialog_orderPin.show();
    }


    private void request_service_online_volly(int requestNo) {

        try {

            JSONObject json_main = new JSONObject();

            json_main.put("apiname", "SERVICE");

            JSONObject jsonObject_request = new JSONObject();
            jsonObject_request.put("agentcode", MyApplication.getSaveString("mobileNoString", OrderPinActivity.this));
            jsonObject_request.put("posserialno",MyApplication.getSN());
            String mPin = MyApplication.getSaveString("mpinString", OrderPinActivity.this);

            String key = Md5.getMd5Hash(MyApplication.getSaveString("mobileNoString", OrderPinActivity.this)+mPin).toUpperCase(Locale.ENGLISH);

            jsonObject_request.put("pin",key);
           // jsonObject_request.put("terminalid",MyApplication.getSaveString("terminalIdString", OrderPinActivity.this));
            jsonObject_request.put("terminalid","");  // if send Then not working
            jsonObject_request.put("vendorcode","TAFANI");
            jsonObject_request.put("clienttype","POS");
            jsonObject_request.put("agenttransid","56637461");
            jsonObject_request.put("comments","Ver-1");

            json_main.put("request", jsonObject_request);

            new VollyRequestResponse(OrderPinActivity.this, OrderPinActivity.this, requestNo, base_url+login_service_apinname, json_main.toString());

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            CommonUtility.hideProgressDialog(OrderPinActivity.this);

            e.printStackTrace();
        }

    }

    private void request_posstock_o_volly(int requestNo) {

        try {


        /*    {
                "apiname": "POSSTOCK",
                    "request": {
                        "agentcode": "0982650605",
                        "pin":"1893274393509319",
                        "vendorcode":"TAFANI",
                        "clienttype":"POS",
                        "terminalid":"0982650606",
                        "action":"O",
                        "recordcount":"1",
                        "comments":"ORDER PIN",
                        "records":[{
                                    "productcode":"LMP3",
                                     "denomination":"3",
                                    "qty":"10"
                               }]
                   }
         }

         */

            JSONObject json_main = new JSONObject();

            json_main.put("apiname", "POSSTOCK");

            JSONObject jsonObject_request = new JSONObject();
            jsonObject_request.put("agentcode", MyApplication.getSaveString("mobileNoString", OrderPinActivity.this));
            //jsonObject_request.put("pin", MyApplication.getSaveString("activationCodeString", OrderPinActivity.this));
            jsonObject_request.put("posserialno",MyApplication.getSN());
            String key = Md5.getMd5Hash(MyApplication.getSaveString("mobileNoString", OrderPinActivity.this)+mpinString_orderPin).toUpperCase(Locale.ENGLISH);
            jsonObject_request.put("pin", MyApplication.getSaveString("activationCodeString", OrderPinActivity.this));
            jsonObject_request.put("pinnew", key);
            jsonObject_request.put("vendorcode","TAFANI");
            jsonObject_request.put("clienttype","POS");
            jsonObject_request.put("terminalid",MyApplication.getSaveString("terminalIdString", OrderPinActivity.this));
            jsonObject_request.put("action","O");
            jsonObject_request.put("recordcount","1");
            jsonObject_request.put("comments","ORDER PIN");



            JSONObject jsonObject_inner = new JSONObject();
            JSONArray jsonArray = new JSONArray();
            for(int i=0;i<1;i++)
            {
                jsonObject_inner.put("productcode",select_productCode);
                jsonObject_inner.put("denomination",select_denomination);
                jsonObject_inner.put("qty",quantityString);

                jsonArray.put(jsonObject_inner);
            }

            jsonObject_request.put("records",jsonArray);




            // String key = Md5.getMd5Hash(MyApplication.getSaveString("mobileNoString", OrderPinActivity.this)+mpinString_orderPin).toUpperCase(Locale.ENGLISH);
          //  jsonObject_request.put("pin",key);


            json_main.put("request", jsonObject_request);

            new VollyRequestResponse(OrderPinActivity.this, OrderPinActivity.this, requestNo, base_url+ret_posstock_apiname, json_main.toString());

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            CommonUtility.hideProgressDialog(OrderPinActivity.this);

            e.printStackTrace();
        }

    }
    private void request_posstock_S_1st_volly(int requestNo) {

        try {

/*            {
                "apiname": "POSSTOCK",
                    "request": {
                        "agentcode": "0982650605",
                        "pin":"1893274393509319",
                        "vendorcode":"TAFANI",
                        "clienttype":"POS",
                        "terminalid":"0982650606",
                        "action":"S",
                        "recordcount":"1",
                        "comments":"POSSTOCK STATUS",
                        "records":[{
                                 "productcode":"LMP3",
                                "denomination":"3",
                                "currentserialnos":"225432150060657,225432150060658,225432150060661,225432150060662"
                         }]
            }
            }*/


            JSONObject json_main = new JSONObject();

            json_main.put("apiname", "POSSTOCK");

            JSONObject jsonObject_request = new JSONObject();
            jsonObject_request.put("agentcode", MyApplication.getSaveString("mobileNoString", OrderPinActivity.this));
           // String mPin = MyApplication.getSaveString("mpinString", OrderPinActivity.this);
            jsonObject_request.put("posserialno",MyApplication.getSN());
            /*String key = Md5.getMd5Hash(MyApplication.getSaveString("mobileNoString", OrderPinActivity.this)+mpinString_orderPin).toUpperCase(Locale.ENGLISH);

            jsonObject_request.put("pin",key);*/

            jsonObject_request.put("pin", MyApplication.getSaveString("activationCodeString", OrderPinActivity.this));

            jsonObject_request.put("vendorcode","TAFANI");
            jsonObject_request.put("clienttype","POS");
            jsonObject_request.put("terminalid",MyApplication.getSaveString("terminalIdString", OrderPinActivity.this));
            jsonObject_request.put("action","S");
           // jsonObject_request.put("transtypecode","OFFLINEPURCHASE");

            jsonObject_request.put("recordcount","1");  // annu
            jsonObject_request.put("comments","POSSTOCK STATUS");


            JSONObject jsonObject_inner = new JSONObject();

            JSONArray jsonArray = new JSONArray();


                jsonObject_inner.put("productcode",select_productCode);
                jsonObject_inner.put("denomination",select_denomination);
                jsonObject_inner.put("currentserialnos",currentserialnos);

                jsonArray.put(jsonObject_inner);


            jsonObject_request.put("records",jsonArray);




            // String key = Md5.getMd5Hash(MyApplication.getSaveString("mobileNoString", OrderPinActivity.this)+mpinString_orderPin).toUpperCase(Locale.ENGLISH);
            //  jsonObject_request.put("pin",key);


            json_main.put("request", jsonObject_request);

            new VollyRequestResponse(OrderPinActivity.this, OrderPinActivity.this, requestNo, base_url+ret_posstock_apiname, json_main.toString());

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            CommonUtility.hideProgressDialog(OrderPinActivity.this);

            e.printStackTrace();
        }

    }


    private void request_bulkdown_volly(int requestNo) {

        try {

           /* {
                   "apiname": "BULKDWN",
                    "request": {
                        "agentcode": "0982650605",
                        "agenttransid":"1270216262",
                        "vendorcode":"TAFANI",
                        "terminalid":"0982650606",
                        "requestcts":"12/13/21 10:22:20",
                        "clienttype":"POS",
                        "userid":"33333",
                        "comments":"PIN BULKDOWNLOAD"
                 }
            }*/


            JSONObject json_main = new JSONObject();

            json_main.put("apiname", "BULKDWN");

            JSONObject jsonObject_request = new JSONObject();
            jsonObject_request.put("agentcode", MyApplication.getSaveString("mobileNoString", OrderPinActivity.this));
            jsonObject_request.put("posserialno",MyApplication.getSN());
            jsonObject_request.put("agenttransid",MyApplication.getSaveString("terminalIdString", OrderPinActivity.this));
            jsonObject_request.put("vendorcode","TAFANI");
            jsonObject_request.put("terminalid",MyApplication.getSaveString("terminalIdString", OrderPinActivity.this));
            jsonObject_request.put("requestcts","12/13/21 10:22:20");
            jsonObject_request.put("clienttype","POS");
            jsonObject_request.put("userid","33333");    // hard code acording to rohit
            jsonObject_request.put("comments","PIN BULKDOWNLOAD");

            json_main.put("request", jsonObject_request);

            new VollyRequestResponse(OrderPinActivity.this, OrderPinActivity.this, requestNo, base_url+ret_bulkdown_apiname, json_main.toString());

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            CommonUtility.hideProgressDialog(OrderPinActivity.this);

            e.printStackTrace();
        }

    }


    @Override
    public void serverResponse(int requestNo, JSONObject serverResponse) {

        CommonUtility.hideProgressDialog(OrderPinActivity.this);

        try {


            if (serverResponse.toString().contains("Time Out")) {
                showAlertDialog_sh("Time Out");
            } else if (serverResponse.toString().contains("Please try again later")) {
                showAlertDialog_sh("Please try again later");
            }



            else {

                if (requestNo == 100)     //*******************   SERVICE_RESPONE *******************
                {

                  //  serverResponse = new JSONObject("{\"apiname\":\"SERVICE\",\"response\":{\"responsects\":\"01/12/2022 01:59:09 PM\",\"agentcode\":\"0982650605\",\"agentname\":\"Prashun\",\"vendorcode\":\"TAFANI\",\"transid\":\"45631784\",\"resultcode\":\"0\",\"resultdescription\":\"Transaction Successful\",\"clienttype\":\"GPRS\",\"recordcount\":\"20\",\"productDetails\":[{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LIBYANA\",\"vendordesc\":\"LIBYANA\",\"productcode\":\"LMP3\",\"productdesc\":\"LMP3\",\"denomination\":\"3.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"MADAR\",\"vendordesc\":\"MADAR\",\"productcode\":\"AAMP3\",\"productdesc\":\"AAMP3\",\"denomination\":\"3.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LIBYANA\",\"vendordesc\":\"LIBYANA\",\"productcode\":\"LMP5\",\"productdesc\":\"LMP5\",\"denomination\":\"5.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"MADAR\",\"vendordesc\":\"MADAR\",\"productcode\":\"AAMP5\",\"productdesc\":\"AAMP5\",\"denomination\":\"5.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LTT\",\"vendordesc\":\"LTT\",\"productcode\":\"LTT5\",\"productdesc\":\"LTT5\",\"denomination\":\"5.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"THLC\",\"vendordesc\":\"Hatif Libya\",\"productcode\":\"HLC5\",\"productdesc\":\"HLC5\",\"denomination\":\"5.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LIBYANA\",\"vendordesc\":\"LIBYANA\",\"productcode\":\"LMP10\",\"productdesc\":\"LMP10\",\"denomination\":\"10.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"MADAR\",\"vendordesc\":\"MADAR\",\"productcode\":\"AAMP10\",\"productdesc\":\"AAMP10\",\"denomination\":\"10.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LTT\",\"vendordesc\":\"LTT\",\"productcode\":\"LTT10\",\"productdesc\":\"LTT10\",\"denomination\":\"10.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"THLC\",\"vendordesc\":\"Hatif Libya\",\"productcode\":\"HLC10\",\"productdesc\":\"HLC10\",\"denomination\":\"10.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"MADAR\",\"vendordesc\":\"MADAR\",\"productcode\":\"AAMP20\",\"productdesc\":\"AAMP20\",\"denomination\":\"20.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LTT\",\"vendordesc\":\"LTT\",\"productcode\":\"LTT20\",\"productdesc\":\"LTT20\",\"denomination\":\"20.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"THLC\",\"vendordesc\":\"Hatif Libya\",\"productcode\":\"HLC20\",\"productdesc\":\"HLC20\",\"denomination\":\"20.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LIBYANA\",\"vendordesc\":\"LIBYANA\",\"productcode\":\"LMP30\",\"productdesc\":\"LMP30\",\"denomination\":\"30.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LTT\",\"vendordesc\":\"LTT\",\"productcode\":\"LTT30\",\"productdesc\":\"LTT30\",\"denomination\":\"30.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"MADAR\",\"vendordesc\":\"MADAR\",\"productcode\":\"AAMP40\",\"productdesc\":\"AAMP40\",\"denomination\":\"40.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LIBYANA\",\"vendordesc\":\"LIBYANA\",\"productcode\":\"LMP100\",\"productdesc\":\"LMP100\",\"denomination\":\"100.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"MADAR\",\"vendordesc\":\"MADAR\",\"productcode\":\"AAMP100\",\"productdesc\":\"AAMP100\",\"denomination\":\"100.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LTT\",\"vendordesc\":\"LTT\",\"productcode\":\"LTT100\",\"productdesc\":\"LTT 100\",\"denomination\":\"100.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"THLC\",\"vendordesc\":\"Hatif Libya\",\"productcode\":\"HLC100\",\"productdesc\":\"HLC100\",\"denomination\":\"100.0\"}],\"qty\":\"1000\",\"parentname\":\"\",\"parentcode\":\"\",\"terminalid\":\"\",\"operatorcount\":\"18\",\"onlineoperatorcount\":\"9\",\"settings\":{},\"operatorDetails\":[{\"name\":\"TAFANI\",\"code\":\"TAFANI\"},{\"name\":\"Hatif Libya\",\"code\":\"THLC\"},{\"name\":\"LTT\",\"code\":\"LTT\"},{\"name\":\"MADAR\",\"code\":\"MADAR\"},{\"name\":\"LIBYANA\",\"code\":\"LIBYANA\"},{\"name\":\"ANI Pay\",\"code\":\"ANIP\"},{\"name\":\"1st NET\",\"code\":\"FNET\"},{\"name\":\"Digital Impulse\",\"code\":\"NBDA\"},{\"name\":\"City WiFi\",\"code\":\"CFNET\"},{\"name\":\"AlRiyada\",\"code\":\"RDIT\"},{\"name\":\"XNET\",\"code\":\"XNET\"},{\"name\":\"ALKAFAA\",\"code\":\"ALKAFAA\"},{\"name\":\"CONNECT\",\"code\":\"CONNECT\"},{\"name\":\"Giga Net\",\"code\":\"GIGA\"},{\"name\":\"Souqprimo\",\"code\":\"SP\"},{\"name\":\"ZAJEL\",\"code\":\"ZAJEL\"},{\"name\":\"SPGF test\",\"code\":\"SPGF\"},{\"name\":\"PrimoTech\",\"code\":\"PT\"}],\"onlineoperatorDetails\":[{\"name\":\"1st NET\",\"code\":\"FNET\"},{\"name\":\"City WiFi\",\"code\":\"CFNET\"},{\"name\":\"LTT\",\"code\":\"LTT\"},{\"name\":\"LIBYANA\",\"code\":\"LIBYANA\"},{\"name\":\"MADAR\",\"code\":\"MADAR\"},{\"name\":\"ALKAFAA\",\"code\":\"ALKAFAA\"},{\"name\":\"Digital Impulse\",\"code\":\"NBDA\"},{\"name\":\"AlRiyada\",\"code\":\"RDIT\"},{\"name\":\"Hatif Libya\",\"code\":\"THLC\"}]}}");


                 //   Toast.makeText(this, "HARD CODE ----------100 ---101", Toast.LENGTH_SHORT).show();

                    if (serverResponse.has("response")) {

                        JSONObject jsonObject_response = serverResponse.getJSONObject("response");

                        if (jsonObject_response.has("resultcode")) {


                            String resultcode = jsonObject_response.getString("resultcode");
                            String resultdescription = jsonObject_response.getString("resultdescription");

                            if (resultcode.equalsIgnoreCase("0")) {

                                serviceRepository.deletAllServiceOperator();
                                serviceRepository.deletAllServiceProduct();


                                operator_pinsale.clear();

                                JSONArray jsonArray_operatorDetails = jsonObject_response.optJSONArray("operatorDetails");

                                OperatorModalOrderPin operatorModal_temp = new OperatorModalOrderPin(getString(R.string.please_select_operator),getString(R.string.please_select_operator));
                                operator_pinsale.add(operatorModal_temp);

                                for (int i = 0; i < jsonArray_operatorDetails.length(); i++) {

                                    JSONObject jsonObject = jsonArray_operatorDetails.optJSONObject(i);
                                    OperatorModalOrderPin operatorModal = new OperatorModalOrderPin(

                                            jsonObject.optString("name"),
                                            jsonObject.optString("code"));

                                    operator_pinsale.add(operatorModal);

                                // ##################  Database  Operator Details ##################

                                    ServiceOperatorDownloadOfflineModel serviceDownloadOfflineModel = new ServiceOperatorDownloadOfflineModel(
                                        jsonObject.optString("name"),
                                        jsonObject.optString("code")
                                    );

                                serviceRepository.insert(serviceDownloadOfflineModel);

                                }

                                // #################################################################


                                JSONArray jsonArrayOperatorList_offline = jsonObject_response.optJSONArray("operatorDetails");
                                for (int i = 0; i < jsonArrayOperatorList_offline.length(); i++)
                                {
                                    JSONObject jsonObject = jsonArrayOperatorList_offline.optJSONObject(i);
                                    OperatorOfflineModel operatorOfflineModel = new OperatorOfflineModel(jsonObject.optString("name"),
                                            jsonObject.optString("code")
                                    );
                                    serviceRepository.insert(operatorOfflineModel);
                                }


                                // ####################################################################

                                OperatorAdapterOrderPin OperatorAdapter = new OperatorAdapterOrderPin(this, operator_pinsale);
                                spinner_operator_orderPin.setAdapter(OperatorAdapter);


                                productList_pinsale.clear();
                                //denominationList_pinsale.clear();


                                ProductModelOrderPin operatorModel_temp = new ProductModelOrderPin(getString(R.string.please_select_product),getString(R.string.please_select_vendor_type),getString(R.string.please_select_denomination));
                                productList_pinsale.add(operatorModel_temp);

                /*                DenominationModelOrderPin productModel_temp = new DenominationModelOrderPin(getString(R.string.please_select_denomination));
                                denominationList_pinsale.add(productModel_temp);*/


                                 jasonArray_productDetails_service_api = jsonObject_response.optJSONArray("productDetails");


                                for (int i = 0; i < jasonArray_productDetails_service_api.length(); i++) {

                                    JSONObject jsonObject = jasonArray_productDetails_service_api.optJSONObject(i);

                                    ProductModelOrderPin productModelOrderPin = new ProductModelOrderPin(
                                            jsonObject.getString("productcode"),
                                            jsonObject.getString("vendorcode"),
                                            jsonObject.getString("denomination")
                                    );

                                    productList_pinsale.add(productModelOrderPin);


                                    // ##################  Database  Product Details  ##################

                                    ServiceProductDownloadOfflineModel serviceDownloadOfflineModel = new ServiceProductDownloadOfflineModel(
                                            jsonObject.getString("productcode"),
                                            jsonObject.getString("vendorcode"),
                                            jsonObject.getString("denomination")
                                    );
                                    serviceRepository.insert(serviceDownloadOfflineModel);

                                }

                                // #################################################################





                                ProductAdapterOrderPin operatorAdapter = new ProductAdapterOrderPin(this, productList_pinsale);
                                spinner_product_orderpin.setAdapter(operatorAdapter);

                                DenominationAdapterOrderPin productAdapter = new DenominationAdapterOrderPin(OrderPinActivity.this, productList_pinsale);
                                spinner_denomination_orderpin.setAdapter(productAdapter);


                                // ####################################################################

                            } else {
                                failure_case(resultdescription);
                                finish();
                            }
                        }
                    }

                }



                else   if (requestNo == 105)    //*******************   POS_STOCK_O_RESPONE *******************
                {

                 //  serverResponse=new JSONObject("{\"apiname\":\"POSSTOCK\",\"response\":{\"comments\":\"ORDER PIN\",\"responsects\":\"23-12-2021 09:51:42 AM\",\"agentcode\":\"0982650605\",\"vendorcode\":\"TAFANI\",\"transid\":\"45629778\",\"resultcode\":\"0\",\"resultdescription\":\"Transaction Successful\",\"clienttype\":\"POS\",\"recordcount\":\"1\",\"records\":[{\"productcode\":\"LMP3\",\"denomination\":\"3.0\",\"qty\":\"10\"}],\"terminalid\":\"0982650606\",\"service\":\"true\",\"action\":\"O\",\"dcnt\":\"32\"}}");

                    if (serverResponse.has("response")) {

                        JSONObject jsonObject_response = serverResponse.getJSONObject("response");

                        if (jsonObject_response.has("resultcode")) {

                            String resultcode = jsonObject_response.getString("resultcode");
                            String resultdescription = jsonObject_response.getString("resultdescription");


                            posstock_o_List_pinsale.clear();

                            if (resultcode.equalsIgnoreCase("0")) {


                                JSONArray jsonArray_records = jsonObject_response.getJSONArray("records");

                                for(int i=0;i<jsonArray_records.length();i++) {

                                    PosStockOModalOnline posStockSModalOnline = new PosStockOModalOnline();

                                        JSONObject jsonObject3 = jsonArray_records.getJSONObject(i);

                                        if (jsonObject3.has("productcode")) {
                                            String productcode = jsonObject3.getString("productcode");
                                            posStockSModalOnline.setProductcode(productcode);
                                        }

                                        if (jsonObject3.has("denomination")) {
                                            String denomination = jsonObject3.getString("denomination");
                                            posStockSModalOnline.setDenomination(denomination);
                                        }
                                    if (jsonObject3.has("qty")) {
                                        String qty = jsonObject3.getString("qty");
                                        posStockSModalOnline.setQty(qty);
                                    }

                                        posstock_o_List_pinsale.add(posStockSModalOnline);


                                    // ##################  Database  Product Details  ##################

                                    PosstockODownloadOfflineModel posstockODownloadOfflineModel = new PosstockODownloadOfflineModel(
                                            jsonObject3.getString("productcode"),
                                            jsonObject3.getString("denomination"),
                                            jsonObject3.getString("qty")
                                    );
                                    serviceRepository.insert(posstockODownloadOfflineModel);

                                }

                                // #################################################################


                                System.out.println(posstock_o_List_pinsale);

                             //  Toast.makeText(this, "--------------105--- Response -----Postock - 0------", Toast.LENGTH_SHORT).show();

                                CommonUtility.showProgressDialog(OrderPinActivity.this);

                                request_bulkdown_volly(106);


                            } else {

                                failure_case(resultdescription);

                            }
                        }
                    }

                }


                else   if (requestNo == 106)    //*******************    BULK_DOWNLOAD_RESPONE *******************
                {

                    currentserialnos ="";
                    currentserialnos_temp ="";

                    System.out.println(currentserialnos);
                    System.out.println(currentserialnos_temp);

                    //  serverResponse=new JSONObject("{\"apiname\":\"BULKDWN\",\"response\":{\"responsects\":\"22-12-2021 01:07:32 PM\",\"agentcode\":\"0982650605\",\"vendorcode\":\"TAFANI\",\"transid\":\"45629771\",\"resultcode\":\"0\",\"resultdescription\":\"Transaction Successful\",\"clienttype\":\"POS\",\"recordcount\":\"10\",\"records\":[{\"vendorcode\":\"LIBYANA\",\"productcode\":\"LMP3\",\"denomination\":\"3.0\",\"recordcount\":\"10\",\"r\":[{\"p\":\"20B7370D6DBA190F7E2F2C16120A45E0\",\"s\":\"225432150060661\"},{\"p\":\"1E0DDB1C2DE1C84C0567EA09F4E807AB\",\"s\":\"225432150060662\"},{\"p\":\"BD469144C5BFD1D7E059E986FA9DDAC9\",\"s\":\"225432150060663\"},{\"p\":\"E5B2F6C66DBF79C3A6786C13D1EC2999\",\"s\":\"225432150060664\"},{\"p\":\"097F4747A5F5B9D6E5B4444A7BA144B8\",\"s\":\"225432150060669\"},{\"p\":\"BA635ACE189E9995604D60545CD58C85\",\"s\":\"225432150060670\"},{\"p\":\"76DC132A08D8521D2D3736BDAA6D52A4\",\"s\":\"225432150060671\"},{\"p\":\"AB1F4DCE65784EB0383F0B883D06C745\",\"s\":\"225432150060672\"},{\"p\":\"258D8FB9F81C66F6CA7412831D7B2272\",\"s\":\"225432150060673\"},{\"p\":\"375989A69EEEE441867F2DE89DCF5CFF\",\"s\":\"225432150060674\"}]}],\"terminalid\":\"0982650606\"}}");

                    if (serverResponse.has("response")) {

                        JSONObject jsonObject_response = serverResponse.getJSONObject("response");

                        if (jsonObject_response.has("resultcode")) {

                            String resultcode = jsonObject_response.getString("resultcode");
                            String resultdescription = jsonObject_response.getString("resultdescription");

                            bulkdownloadList_pinsale = new ArrayList<>();
                            bulkdownloadList_pinsale.clear();


                            if (resultcode.equalsIgnoreCase("0")) {

                               // serviceRepository.deletAllbulkDownload();



                             //   JSONArray jsonArray_records = jsonObject_response.getJSONArray("records");

                                MyApplication.jsonArray_bulkdownload=jsonObject_response.getJSONArray("records");


                                for(int i=0;i<MyApplication.jsonArray_bulkdownload.length();i++) {


                                    JSONObject jsonObject3 = MyApplication.jsonArray_bulkdownload.getJSONObject(i);



                                    JSONArray jsonArray_r = jsonObject3.getJSONArray("r");


                                    for(int j=0;j<jsonArray_r.length();j++) {

                                        JSONObject jsonObject4 = jsonArray_r.getJSONObject(j);

                                        if (jsonObject4.has("p")) {
                                            String pinno = jsonObject4.getString("p");

                                        }

                                        if (jsonObject4.has("s")) {

                                            String serialno = jsonObject4.getString("s");

                                            currentserialnos_temp=currentserialnos_temp+serialno+",";
                                        }




                                    }
                                }

                                if(currentserialnos_temp.contains(","))
                                {
                                    if ((currentserialnos_temp != null) && (currentserialnos_temp.length() > 0)) {
                                        currentserialnos = currentserialnos_temp.substring(0, currentserialnos_temp.length() - 1);
                                    }
                                }



                                System.out.println(currentserialnos);
                                System.out.println(currentserialnos_temp);

                                CommonUtility.showProgressDialog(OrderPinActivity.this);

                                request_posstock_S_1st_volly(107);


                           //     CommonUtility.showProgressDialog(OrderPinActivity.this);
                            //    request_posstock_S_2nd_volly(108);   // 2nd Time Api




                            } else {

                                failure_case(resultdescription);

                            }
                        }
                    }

                }


                else   if (requestNo == 107) {   //*******************   POSSTOCK_S_RESPONE *******************

                  //  serverResponse=new JSONObject("{\"apiname\":\"POSSTOCK\",\"response\":{\"comments\":\"POSSTOCK STATUS\",\"responsects\":\"23-12-2021 11:02:13 AM\",\"agentcode\":\"0982650605\",\"vendorcode\":\"TAFANI\",\"transid\":\"45629791\",\"resultcode\":\"0\",\"resultdescription\":\"Transaction Successful\",\"clienttype\":\"POS\",\"recordcount\":\"1\",\"records\":[{\"productcode\":\"LMP3\",\"denomination\":\"3.0\",\"currentserialnos\":[{\"s\":\"225432150060658\",\"k\":\"4E251B12C01D96CB\"},{\"s\":\"225432150060657\",\"k\":\"DC72ABEE0C02E976\"},{\"s\":\"225432150060661\",\"k\":\"48BF51E48B17B137\"},{\"s\":\"225432150060662\",\"k\":\"D7438230CD05192B\"}]}],\"terminalid\":\"0982650606\",\"service\":\"true\",\"action\":\"S\"}}");

                    if (serverResponse.has("response")) {

                        JSONObject jsonObject_response = serverResponse.getJSONObject("response");

                        if (jsonObject_response.has("resultcode")) {

                            String resultcode = jsonObject_response.getString("resultcode");
                            String resultdescription = jsonObject_response.getString("resultdescription");

                            posstock_s_List_pinsale = new ArrayList<>();
                            posstock_s_List_pinsale.clear();


                            if (resultcode.equalsIgnoreCase("0")) {

                                JSONArray jsonArray_records = jsonObject_response.getJSONArray("records");


                                for(int i=0;i<jsonArray_records.length();i++) {

                                    JSONObject jsonObject = jsonArray_records.getJSONObject(i);
                                     productCode=jsonObject.optString("productcode");
                                    // String productcode=jsonObject.getString("productcode");
                                    //  String denomination_bulkdownlaod=jsonObject.getString("denomination");

                                    JSONArray jsonArray_currentserialnos = jsonObject.getJSONArray("currentserialnos");

                                    transid_online = jsonObject_response.getString("transid");
                                    for (int j = 0; j < jsonArray_currentserialnos.length(); j++) {


                                        PosStockSModalOnline posStockSModalOnline = new PosStockSModalOnline();

                                        JSONObject jsonObject3 = jsonArray_currentserialnos.getJSONObject(j);

                                        if (jsonObject3.has("s")) {
                                            String serialNumber = jsonObject3.getString("s");
                                            posStockSModalOnline.setSeriolNumber(serialNumber);
                                        }

                                        if (jsonObject3.has("k")) {
                                            String k_number = jsonObject3.getString("k");
                                            posStockSModalOnline.setkNumber(k_number);
                                        }

                                        posstock_s_List_pinsale.add(posStockSModalOnline);

                                        // ##################  Database  Product Details  ##################

                                        PostockSDownloadOfflineModel postockSDownloadOfflineModel = new PostockSDownloadOfflineModel(
                                                jsonObject3.getString("s"),
                                                jsonObject3.getString("k")
                                        );
                                        serviceRepository.insert(postockSDownloadOfflineModel);

                                    }

                                    // #################################################################

                                    }


                                System.out.println(posstock_s_List_pinsale);


                                for(int i=0;i<MyApplication.jsonArray_bulkdownload.length();i++) {

                                    BulkDownlaodModalOnline  bulkDownlaodModalOnline = new BulkDownlaodModalOnline();

                                    JSONObject jsonObject3 = MyApplication.jsonArray_bulkdownload.getJSONObject(i);

                                    String vendorcode_bulkdownlaod=jsonObject3.getString("vendorcode");
                                    String productcode_bulkdownlaod=jsonObject3.getString("productcode");
                                    String denomination_bulkdownlaod=jsonObject3.getString("denomination");
                                    String recordcount_bulkdownlaod=jsonObject3.getString("recordcount");

                                    bulkDownlaodModalOnline.setVendorcode(vendorcode_bulkdownlaod);
                                    bulkDownlaodModalOnline.setProductcode(productcode_bulkdownlaod);
                                    bulkDownlaodModalOnline.setDenomination(denomination_bulkdownlaod);
                                    bulkDownlaodModalOnline.setRecordcount(recordcount_bulkdownlaod);


                                    JSONArray jsonArray_r = jsonObject3.getJSONArray("r");


                                    for(int j=0;j<jsonArray_r.length();j++) {

                                        JSONObject jsonObject4 = jsonArray_r.getJSONObject(j);

                                        if (jsonObject4.has("p")) {
                                            String pinno = jsonObject4.getString("p");
                                            bulkDownlaodModalOnline.setSerialNo_key(pinno);

                                        }

                                        if (jsonObject4.has("s")) {
                                            String serialno = jsonObject4.getString("s");
                                            bulkDownlaodModalOnline.setPinNumber_encript(serialno);

                                       //     currentserialnos_temp=currentserialnos_temp+serialno+",";
                                        }

                                        bulkdownloadList_pinsale.add(bulkDownlaodModalOnline);

                                        // ##################   bulkdownload Database ##################



                                        String sell_currentDateTime=current_sell_dateTime();

                                        for(int p=0;p<posstock_s_List_pinsale.size();p++)
                                        {
                                            if(posstock_s_List_pinsale.get(p).getSeriolNumber().equalsIgnoreCase(jsonObject4.optString("s")))
                                            {
                                                BulkDownloadOfflineModel bulkDownloadOfflineModel = new BulkDownloadOfflineModel(vendorcode_bulkdownlaod,
                                                        productcode_bulkdownlaod,denomination_bulkdownlaod,recordcount_bulkdownlaod,
                                                        jsonObject4.optString("p"),
                                                        jsonObject4.optString("s"),posstock_s_List_pinsale.get(p).getkNumber(),"N",sell_currentDateTime);

                                                serviceRepository.insert(bulkDownloadOfflineModel);
                                            }

                                        }



                                        // #################################################################


                                    }
                                }

                               /* if(currentserialnos_temp.contains(","))
                                {
                                    if ((currentserialnos_temp != null) && (currentserialnos_temp.length() > 0)) {
                                        currentserialnos = currentserialnos_temp.substring(0, currentserialnos_temp.length() - 1);
                                    }
                                }*/



                    //            Toast.makeText(this, "--------------107--- Response -----POSSTOCK S------", Toast.LENGTH_SHORT).show();


                                System.out.println(serviceRepository);


                                //     CommonUtility.showProgressDialog(OrderPinActivity.this);
                              //     request_posstock_S_2nd_volly(108);





                             //   Intent intent = new Intent(OrderPinActivity.this, OrderPinreceiptPage.class);
                             //   startActivity(intent);

                                String transactionDateTemp = jsonObject_response.getString("responsects");
                                String[] transactionDate_array=transactionDateTemp.split(" ");
                                transactionDate_online=transactionDate_array[0];
                                transactionTime_online=transactionDate_array[1]+" "+ transactionDate_array[2];;



                                online_print_popup(getString(R.string.orderpin_success));   // annu print_



                            } else {

                                failure_case(resultdescription);

                            }
                        }
                    }

                }


                else   if (requestNo == 108) {

                  //  serverResponse=new JSONObject("{\"apiname\":\"BULKDWN\",\"response\":{\"responsects\":\"22-12-2021 01:07:32 PM\",\"agentcode\":\"0982650605\",\"vendorcode\":\"TAFANI\",\"transid\":\"45629771\",\"resultcode\":\"0\",\"resultdescription\":\"Transaction Aful\",\"clienttype\":\"POS\",\"recordcount\":\"10\",\"records\":[{\"vendorcode\":\"LIBYANA\",\"productcode\":\"LMP3\",\"denomination\":\"3.0\",\"recordcount\":\"10\",\"r\":[{\"p\":\"20B7370D6DBA190F7E2F2C16120A45E0\",\"s\":\"225432150060661\"},{\"p\":\"1E0DDB1C2DE1C84C0567EA09F4E807AB\",\"s\":\"225432150060662\"},{\"p\":\"BD469144C5BFD1D7E059E986FA9DDAC9\",\"s\":\"225432150060663\"},{\"p\":\"E5B2F6C66DBF79C3A6786C13D1EC2999\",\"s\":\"225432150060664\"},{\"p\":\"097F4747A5F5B9D6E5B4444A7BA144B8\",\"s\":\"225432150060669\"},{\"p\":\"BA635ACE189E9995604D60545CD58C85\",\"s\":\"225432150060670\"},{\"p\":\"76DC132A08D8521D2D3736BDAA6D52A4\",\"s\":\"225432150060671\"},{\"p\":\"AB1F4DCE65784EB0383F0B883D06C745\",\"s\":\"225432150060672\"},{\"p\":\"258D8FB9F81C66F6CA7412831D7B2272\",\"s\":\"225432150060673\"},{\"p\":\"375989A69EEEE441867F2DE89DCF5CFF\",\"s\":\"225432150060674\"}]}],\"terminalid\":\"0982650606\"}}");

                    if (serverResponse.has("response")) {

                        JSONObject jsonObject_response = serverResponse.getJSONObject("response");

                        if (jsonObject_response.has("resultcode")) {

                            String resultcode = jsonObject_response.getString("resultcode");
                            String resultdescription = jsonObject_response.getString("resultdescription");

                            if (resultcode.equalsIgnoreCase("0")) {







                             //   Intent intent = new Intent(OrderPinActivity.this, OrderPinreceiptPage.class);
                              //  startActivity(intent);

                            } else {

                                failure_case(resultdescription);

                            }
                        }
                    }

                }



            }


        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


    }


    void  failure_case(String resultdescription )
    {
        if (resultdescription.equalsIgnoreCase("Invalid PIN")) {
            Toast.makeText(this, getString(R.string.invalid_pin), Toast.LENGTH_SHORT).show();
        }
        else if ((resultdescription.equalsIgnoreCase("Subscriber Blocked"))) {

            Toast.makeText(this, getString(R.string.subscriber_blocked), Toast.LENGTH_SHORT).show();

        }

        else if ((resultdescription.equalsIgnoreCase("Agent Blocked"))) {
            Toast.makeText(this, getString(R.string.agent_bloack), Toast.LENGTH_SHORT).show();
        }

        else if ((resultdescription.equalsIgnoreCase("Subscriber/Agent Blocked"))) {
            Toast.makeText(this, getString(R.string.subscriber_agent_blocked), Toast.LENGTH_SHORT).show();
        }

        else if ((resultdescription.equalsIgnoreCase("Your account is Blocked"))) {
            Toast.makeText(this, getString(R.string.subscriber_agent_blocked), Toast.LENGTH_SHORT).show();

        }

        else if ((resultdescription.equalsIgnoreCase("Insufficient Fund"))) {
            Toast.makeText(this, getString(R.string.insuffience_fund), Toast.LENGTH_SHORT).show();
        } else if ((resultdescription.equalsIgnoreCase("Insufficient Wallet"))) {
            Toast.makeText(this, getString(R.string.insuffience_wallet), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, resultdescription, Toast.LENGTH_SHORT).show();
        }

        //   finish();

    }

    public static String current_sell_dateTime() {

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date dateobj = new Date();

        String  currentDateTime=df.format(dateobj);

        //  currentDateTime ="31/12/21 20:08:33";

        return currentDateTime;
    }

    private void success_dialouge_transaction(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(OrderPinActivity.this);
        builder.setTitle(getResources().getString(R.string.transaction_result));
        builder.setCancelable(false);
        builder.setMessage(msg);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {


                Intent iiii = new Intent(OrderPinActivity.this, MainActivityRet.class);
                iiii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(iiii);
                OrderPinActivity.this.finish();



                dialogInterface.dismiss();

            }
        });
        builder.show();
    }




    private void showAlertDialog_sh(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(OrderPinActivity.this);
        builder.setTitle(getResources().getString(R.string.transaction_result));
        builder.setCancelable(false);
        builder.setMessage(msg);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                dialogInterface.dismiss();

            }
        });
        builder.show();
    }





        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {



            switch (adapterView.getId()) {

                case R.id.spinner_operator_orderPin:  // first spinner
                {
                    try {


                    select_operatorName = operator_pinsale.get(i).getName();
                    select_operatorCode = operator_pinsale.get(i).getCode();





                        if(i==0)
                    {

                        //   Toast.makeText(MainActivityRet.this, " -----selected--name--"+select_operatorName+"-----selected---Code----"+select_operatorCode,Toast.LENGTH_SHORT).show();
                    }
                    else {

                          //  Toast.makeText(OrderPinActivity.this, " ---Operator--"+select_operatorName+"-----selected---Code----"+select_operatorCode,Toast.LENGTH_SHORT).show();

                        productList_pinsale_filter.clear();
                      //  denominationList_filter_pinsale.clear();

                        ProductModelOrderPin operatorModel_temp = new ProductModelOrderPin(getString(R.string.please_select_product),getString(R.string.please_select_vendor_type),getString(R.string.please_select_denomination));
                        productList_pinsale_filter.add(operatorModel_temp);



                        System.out.println(select_operatorCode);  //
                        System.out.println(productList_pinsale);
                        System.out.println(jasonArray_productDetails_service_api);
                        System.out.println("asasas");



                        for (int j = 0; j < productList_pinsale.size(); j++) {


                            if (select_operatorCode.equalsIgnoreCase(productList_pinsale.get(j).getVendorcode())) {

                            //    JSONObject jsonObject = jasonArray_productDetails_service_api.optJSONObject(j);

                                ProductModelOrderPin productModelOrderPin = new ProductModelOrderPin(
                                        productList_pinsale.get(j).getProductcode(),
                                        productList_pinsale.get(j).getVendorcode(),productList_pinsale.get(j).getDenomination()
                                );

                                productList_pinsale_filter.add(productModelOrderPin);

                             //   denominationList_filter_pinsale.add(productModelOrderPin);
                            }
                            else {

                            }
                        }


                        ProductAdapterOrderPin operatorAdapter = new ProductAdapterOrderPin(this, productList_pinsale_filter);
                        spinner_product_orderpin.setAdapter(operatorAdapter);

                        DenominationAdapterOrderPin productAdapter = new DenominationAdapterOrderPin(OrderPinActivity.this, productList_pinsale_filter);
                        spinner_denomination_orderpin.setAdapter(productAdapter);

                    }
                    }
                    catch (Exception e) {

                        Toast.makeText(OrderPinActivity.this, e.toString(), Toast.LENGTH_SHORT).show();

                        e.printStackTrace();
                    }
                }
                break;

                case R.id.spinner_product_orderpin:
                {

                        if (i == 0) {

                        }

                        else {

                            select_productCode = productList_pinsale_filter.get(i).getProductcode();
                            select_vendorcode = productList_pinsale_filter.get(i).getVendorcode();




                           // Toast.makeText(OrderPinActivity.this, " ---Product --"+select_productCode+"-----Vendor----"+select_vendorcode,Toast.LENGTH_SHORT).show();


                            spinner_denomination_orderpin.setSelection(i);


                            //  spinner_denomination_orderpin.setSelection(i);
                        }

                }
                break;

                case R.id.spinner_denomination_orderpin:
                {

                    if(i==0)
                    {

                    }

                    else
                    {

                        select_denomination = productList_pinsale_filter.get(i).getDenomination();

                      //  Toast.makeText(OrderPinActivity.this, " ----- DENOMINATION --"+select_denomination,Toast.LENGTH_SHORT).show();

                    }
                }
                break;
            }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    protected void print_pos_data_online() {

        if (!BluetoothUtil.isBlueToothPrinter) {

            Bitmap bitmap = null;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inTargetDensity = 200;
            options.inDensity = 200;


            // Bitmap bitmap_new = getResizedBitmap(bitmap ,200);

            if(select_operatorCode.equalsIgnoreCase("LIBYANA"))
            {
                Toast.makeText(OrderPinActivity.this, select_operatorCode, Toast.LENGTH_SHORT).show();
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ani_libyana, options);

                if(languageToUse.equalsIgnoreCase("ar"))
                {
                    footer_first_online="للتعبئة: اضغط (120) ثم (الرقم السري) ثم (اتصال)";
                    footer_second_online="www.Tafani.ly";
                }
                else {
                    footer_first_online="To recharge: press (120) then (PIN Code) then (dial).";
                    footer_second_online="www.Tafani.ly";

                }
            }


            else if(select_operatorCode.equalsIgnoreCase("CFNET"))   // Not available
            {
                Toast.makeText(OrderPinActivity.this, select_operatorCode, Toast.LENGTH_SHORT).show();
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.city_wifi, options);  // Not available

                if(languageToUse.equalsIgnoreCase("ar"))
                {
                    footer_first_online="";   // Not available
                    footer_second_online="";
                }
                else {
                    footer_first_online="";  // Not available
                    footer_second_online="";

                }
            }

            else if(select_operatorCode.equalsIgnoreCase("RDIT"))
            {
                Toast.makeText(OrderPinActivity.this, select_operatorCode, Toast.LENGTH_SHORT).show();
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ani_airiyada, options);  // Not available

                if(languageToUse.equalsIgnoreCase("ar"))
                {
                    footer_first_online="للتعبئة: أرسل رسالة نصية إلى الرقم (13434): رقم العقد*الرقم السري*رقم كرت التعبئة# ";
                    footer_second_online="www.Tafani.ly";
                }
                else {
                    footer_first_online="To top-up: send a text message to the number (13434): Contract number * PIN code * Top-up card number #";
                    footer_second_online="www.Tafani.ly";

                }
            }

            else if(select_operatorCode.equalsIgnoreCase("LTT"))
            {
                Toast.makeText(OrderPinActivity.this, select_operatorCode, Toast.LENGTH_SHORT).show();
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ani_ltt, options);

                if(languageToUse.equalsIgnoreCase("ar"))
                {
                    footer_first_online="لتعبئة رصيد ليبيا فون اضغط *116* رقم البطاقة السري # ثم اتصل.\n" +
                            "لتعبئة رصيد خدمات الانترنت اتصل بـ 116";
                    footer_second_online="www.Tafani.ly";
                }
                else {
                    footer_first_online="To top up Libya Phone balance press *116* PIN number # then call.\\n\" +\n" +
                            "                            To top up the balance of internet services, call 116";
                    footer_second_online="www.Tafani.ly";
                }
            }

            else if(select_operatorCode.equalsIgnoreCase("FNET"))
            {
                Toast.makeText(OrderPinActivity.this, select_operatorCode, Toast.LENGTH_SHORT).show();
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ani_first_net, options);

                if(languageToUse.equalsIgnoreCase("ar"))
                {
                    footer_first_online="للتعبئة: عبر الموقع 1stNET.ly" +
                            "او ارسل رسالة للرقم0945000057 بها\n" +
                            "الرقم السري#ر.ت#اخر اربعة ارقام من UserID#phone";

                    footer_second_online=""; // not available in doc
                }
                else {
                    footer_first_online="Password # RT # last four digits of";
                    footer_second_online="1stNET.ly";
                }
            }


            else if(select_operatorCode.equalsIgnoreCase("MADAR"))
            {
                Toast.makeText(OrderPinActivity.this, select_operatorCode, Toast.LENGTH_SHORT).show();
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ani_madar, options);

                if(languageToUse.equalsIgnoreCase("ar"))
                {
                    footer_first_online ="للتعبئة اضغط *112* الرقم السري # ثم اتصال.";
                    footer_second_online="www.Tafani.ly";
                }
                else {

                    footer_first_online ="To recharge press *112*PIN Number# and dial.";
                    footer_second_online="www.Tafani.ly";
                }
            }

            else if(select_operatorCode.equalsIgnoreCase("THLC"))
            {
                Toast.makeText(OrderPinActivity.this, select_operatorCode, Toast.LENGTH_SHORT).show();
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo_ani_operator_hatif_new, options);

                if(languageToUse.equalsIgnoreCase("ar"))
                {
                    footer_first_online ="للتعبئة اتصل بالرقم 1300 ثم اتبع التعليمات.";
                    footer_second_online="www.Tafani.ly";
                }
                else {

                    footer_first_online ="To recharge call 1300 and follow the instructions.";
                    footer_second_online="www.Tafani.ly";
                }
            }


            else if(select_operatorCode.equalsIgnoreCase("ALKAFAA"))
            {
                Toast.makeText(OrderPinActivity.this, select_operatorCode, Toast.LENGTH_SHORT).show();
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ani_alkafa, options);

                if(languageToUse.equalsIgnoreCase("ar"))
                {
                    footer_first_online ="";
                    footer_second_online=": لتعبئة البطاقة";
                }
                else {
                    footer_first_online ="";
                    footer_second_online="My.alkafaa.net";
                }
            }

            else if(select_operatorCode.equalsIgnoreCase("NBDA"))
            {
                Toast.makeText(OrderPinActivity.this, select_operatorCode, Toast.LENGTH_SHORT).show();
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ani_digital_implus, options);
                if(languageToUse.equalsIgnoreCase("ar"))
                {
                    footer_first_online ="للتعبئة: الموقع(user.ditt.ly) أو رسالة نصية للرقم 12012 بها *رقم العقد*اخر 4 ارقام هاتف المشترك* رقم الكرت#";
                    footer_second_online="";
                }
                else {
                    footer_first_online ="To fill in: the website (user.ditt.ly) or a text message to the number 12012 with *contract number* the last 4 phone numbers of the subscriber* card number#";
                    footer_second_online="user.ditt.ly";
                }

            }

            else if(select_operatorCode.equalsIgnoreCase("GIGA"))
            {
                //  Toast.makeText(MainActivityRet.this, vendorcode_offline_print, Toast.LENGTH_SHORT).show();
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ani_giga_net, options);
                if(languageToUse.equalsIgnoreCase("ar"))
                {
                    footer_first_online="";
                    footer_second_online="";
                }
                else {
                    footer_first_online="";
                    footer_second_online="";
                }

            }

            else if(select_operatorCode.equalsIgnoreCase("ZAJEL"))
            {
                //  Toast.makeText(MainActivityRet.this, vendorcode_offline_print, Toast.LENGTH_SHORT).show();
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ani_zajel, options);
                if(languageToUse.equalsIgnoreCase("ar"))
                {
                    footer_first_online="";
                    footer_second_online="";
                }
                else {
                    footer_first_online="";
                    footer_second_online="";
                }

            }
            else if(select_operatorCode.equalsIgnoreCase("PT"))
            {
                //  Toast.makeText(MainActivityRet.this, vendorcode_offline_print, Toast.LENGTH_SHORT).show();
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ani_primotech, options);
                if(languageToUse.equalsIgnoreCase("ar"))
                {
                    footer_first_online="";
                    footer_second_online="";
                }
                else {
                    footer_first_online="";
                    footer_second_online="";
                }

            }


            else {

                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo_stock, options);
                footer_first_online="";
                footer_second_online="";
            }


            SunmiPrintHelper.getInstance().printBitmap_nextgen(bitmap,1);



          /*  image_view_rightside.getLayoutParams().height = 400; //can change the size according to you requirements
            image_view_rightside.getLayoutParams().width = 100; //--
            image_view_rightside.requestLayout();
            image_view_rightside.setScaleType(ImageView.ScaleType.CENTER_INSIDE);*/


            //  image_view_rightside.setBackgroundResource(R.drawable.madar_p);
            //  bitmap = Bitmap.createBitmap(ll_header_print.getDrawingCache());
            //  SunmiPrintHelper.getInstance().printBitmap_nextgen(bitmap,1);





            if (languageToUse.equalsIgnoreCase("en")) {

                SunmiPrintHelper.getInstance().printText_nextgen("\n\n" + transactionDate_online, 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen("                 " + transactionTime_online + "\n", 20, isBold, isUnderLine, testFont, 2);

                SunmiPrintHelper.getInstance().printText_nextgen( getString(R.string.transaction_id), 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen("                " + transid_online + "\n", 20, true, isUnderLine, testFont, 2);



                SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.terminalidId_print_colon), 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen("             " + MyApplication.getSaveString("terminalIdString", OrderPinActivity.this) + "\n", 20, isBold, isUnderLine, testFont, 2);

            //    SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.product_code), 30, true, isUnderLine, testFont, 0);



               /* SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.retailer_print_colon), 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen("                   " + MyApplication.getSaveString("mobileNoString", OrderPinActivity.this) + "\n", 20, isBold, isUnderLine, testFont, 2);
*/
                SunmiPrintHelper.getInstance().printText_nextgen( "\n"+productCode + "\n", 30, true, isUnderLine, testFont, 1);
                SunmiPrintHelper.getInstance().printText_nextgen("\n"+"---------------" + "\n", 50, isBold, isUnderLine, testFont, 0);



                for(int i=0;i<posstock_s_List_pinsale.size();i++)
                {

                    SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.seriol_number)+" "+  posstock_s_List_pinsale.get(i).getSeriolNumber()+ "\n",20, isBold, isUnderLine, testFont, 0);
                    //SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.pin_number)+" "+   posstock_s_List_pinsale.get(i).getkNumber()+ "\n\n",20, isBold, isUnderLine, testFont, 0);


                      /*try {

                          AESEncryption aseEncryption2 = new AESEncryption();
                          String key_2 = AESEncryption.SHA256("#@123#@123", 32); //32 bytes = 256 bit
                          String iv_2 = "e816e2a32196ef0b"; //16 bytes = 128 bit
                          String decrrptData = aseEncryption2.decrypt(posstock_s_List_pinsale.get(i).getkNumber(), key_2, iv_2);
                          SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.pin_number)+" "+   decrrptData+ "\n\n",20, isBold, isUnderLine, testFont, 0);


                      }
                      catch (Exception e)

                      {
                          Toast.makeText(this,e.toString(), Toast.LENGTH_SHORT).show();
                      }
                 */
                }

                SunmiPrintHelper.getInstance().printText_nextgen("---------------" + "\n", 50, isBold, isUnderLine, testFont, 0);


                SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.denomination)+" "+  select_denomination+ "\n",20, isBold, isUnderLine, testFont, 0);



                SunmiPrintHelper.getInstance().printText_nextgen( footer_first_online + "\n", 20, isBold, isUnderLine, testFont, 2);

                SunmiPrintHelper.getInstance().printText_nextgen("             " + footer_second_online + "  " + "\n\n", 20, isBold, isUnderLine, testFont, 0);




                //    SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.footer_heder_print) + "\n\n", 20, isBold, isUnderLine, testFont, 0);

                SunmiPrintHelper.getInstance().feedPaper();


            }
            else

            {

                SunmiPrintHelper.getInstance().printText_nextgen("\n" + transactionDate_online, 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen("                 " + transactionTime_online + "\n", 20, isBold, isUnderLine, testFont, 2);

                SunmiPrintHelper.getInstance().printText_nextgen(  transid_online+"                    ", 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen( getString(R.string.transaction_id) + "\n", 20, isBold, isUnderLine, testFont, 2);


               /* SunmiPrintHelper.getInstance().printText_nextgen(  MyApplication.getSaveString("mobileNoString", OrderPinActivity.this)+"                ", 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen( getString(R.string.terminalidId_print_colon) + "\n", 20, isBold, isUnderLine, testFont, 2);
*/

              /*  SunmiPrintHelper.getInstance().printText_nextgen(  MyApplication.getSaveString("terminalIdString", OrderPinActivity.this)+"              ", 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen( getString(R.string.retailer_print_colon) + "\n", 20, isBold, isUnderLine, testFont, 2);
*/
                SunmiPrintHelper.getInstance().printText_nextgen(MyApplication.getSaveString("terminalIdString", OrderPinActivity.this) + "                  ", 20, isBold, isUnderLine, testFont, 2);
                SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.terminalidId_print_colon), 20, isBold, isUnderLine, testFont, 0);


              //  SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.product_code), 30, true, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen( "\n\n"+productCode + "\n", 30, true, isUnderLine, testFont, 1);
                SunmiPrintHelper.getInstance().printText_nextgen("\n"+"---------------" + "\n", 50, isBold, isUnderLine, testFont, 0);


                for(int i=0;i<posstock_s_List_pinsale.size();i++)
                {

                    SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.seriol_number)+" "+  posstock_s_List_pinsale.get(i).getSeriolNumber()+ "\n",20, isBold, isUnderLine, testFont, 2);
                   // SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.pin_number)+" "+   posstock_s_List_pinsale.get(i).getkNumber()+ "\n\n",20, isBold, isUnderLine, testFont, 2);


                 /*   try {


                        AESEncryption aseEncryption2 = new AESEncryption();
                        String key_2 = AESEncryption.SHA256("#@123#@123", 32); //32 bytes = 256 bit
                        String iv_2 = "e816e2a32196ef0b"; //16 bytes = 128 bit
                        String decrrptData = aseEncryption2.decrypt(posstock_s_List_pinsale.get(i).getkNumber(), key_2, iv_2);

                        SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.pin_number)+" "+   decrrptData+ "\n\n",20, isBold, isUnderLine, testFont, 0);


                    }
                    catch (Exception e)

                    {
                        Toast.makeText(this,e.toString(), Toast.LENGTH_SHORT).show();

                    }*/

                }
                SunmiPrintHelper.getInstance().printText_nextgen("---------------" + "\n", 50, isBold, isUnderLine, testFont, 0);

                SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.denomination)+" "+  select_denomination+ "\n",20, isBold, isUnderLine, testFont, 2);



                SunmiPrintHelper.getInstance().printText_nextgen( footer_first_online + "\n", 20, isBold, isUnderLine, testFont, 2);

                SunmiPrintHelper.getInstance().printText_nextgen("             " + footer_second_online + "  " + "\n\n", 20, isBold, isUnderLine, testFont, 0);

                // SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.footer_heder_print) + "\n\n", 20, isBold, isUnderLine, testFont,0);

                SunmiPrintHelper.getInstance().feedPaper();


            }



        } else {
            // SunmiPrintHelper.getInstance().printText(getString(R.string.account_balance) + " : " + amountFromServer + " LYD", 30, true, isUnderLine, testFont);
        }

    }

   /* Intent i;
    @Override
    protected void onResume() {
        super.onResume();

         i= new Intent(OrderPinActivity.this, LogoutService.class);
// potentially add data to the intent
        i.putExtra("KEY1", "Value to be used by the service");
        startService(i);
        LogoutService.timer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        //stopService(i);
    }
*/
}