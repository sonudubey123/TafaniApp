package com.sunmi.printerhelper.nextgen_sharique.login;

import static com.sunmi.printerhelper.nextgen_sharique.voly_url.Url.base_url;
import static com.sunmi.printerhelper.nextgen_sharique.voly_url.Url.login_ret_dis_apinname;
import static com.sunmi.printerhelper.nextgen_sharique.voly_url.Url.login_service_apinname;
import static com.sunmi.printerhelper.nextgen_sharique.voly_url.Url.printf_ret_apiname;
import static com.sunmi.printerhelper.nextgen_sharique.voly_url.Url.ret_bulkdown_apiname;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import tafani.sunmi.printer.R;
import com.sunmi.printerhelper.local_set.LocalSetLanguage;
import com.sunmi.printerhelper.nextgen_sharique.application_sharepreference.MyApplication;
import com.sunmi.printerhelper.nextgen_sharique.database.offline.ServiceRepository;
import com.sunmi.printerhelper.nextgen_sharique.database.offline.modal_offline.BulkDownloadOfflineModel;
import com.sunmi.printerhelper.nextgen_sharique.database.offline.modal_offline.ServiceOperatorDownloadOfflineModel;
import com.sunmi.printerhelper.nextgen_sharique.database.offline.modal_offline.ServiceProductDownloadOfflineModel;
import com.sunmi.printerhelper.nextgen_sharique.interf.InterHttpServerResponse;
import com.sunmi.printerhelper.nextgen_sharique.loading_internet.CommonUtility;
import com.sunmi.printerhelper.nextgen_sharique.main_activity.MainActivityRet;
import com.sunmi.printerhelper.nextgen_sharique.order_pin.modal_orderpin.BulkDownlaodModalOnline;
import com.sunmi.printerhelper.nextgen_sharique.voly_url.Md5;
import com.sunmi.printerhelper.nextgen_sharique.voly_url.VollyRequestResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Locale;

public class SetPin extends AppCompatActivity implements View.OnClickListener, InterHttpServerResponse {


    TextView btn_submit;
    EditText edittext_offlinePin,edittext_mpin;
    String offlinePinString="",mpinString;
    AlertDialog dialog;

    String dateOfBirthString;
    MyApplication myApplication;

    String languageToUse="";

    ServiceRepository serviceRepository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            myApplication = (MyApplication) getApplicationContext();
            languageToUse=myApplication.getmSharedPreferences().getString("languageToUse","");

            if (languageToUse.trim().length() == 0) {

                languageToUse = "ar";
                LocalSetLanguage.LocalSet(languageToUse, SetPin.this);
            }
            else
            {
                LocalSetLanguage.LocalSet(languageToUse, SetPin.this);
            }


            setContentView(R.layout.set_pin);

            serviceRepository = new ServiceRepository(SetPin.this);



            edittext_offlinePin = (EditText) findViewById(R.id.edittext_offlinePin);
            edittext_mpin = (EditText) findViewById(R.id.edittext_mpin);
            btn_submit = (TextView) findViewById(R.id.btn_submit);
            btn_submit.setOnClickListener(this);

            if(languageToUse.equalsIgnoreCase("ar"))
            {
                edittext_offlinePin.setGravity(Gravity.RIGHT);
                edittext_mpin.setGravity(Gravity.RIGHT);
            }
            else
            {
                edittext_offlinePin.setGravity(Gravity.LEFT);
                edittext_mpin.setGravity(Gravity.LEFT);
            }



        }
        catch (Exception e)
        {
            Toast.makeText(SetPin.this,e.toString(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


        SharedPreferences.Editor Eeditor = SetPin.this.getSharedPreferences("EU_MPIN", MODE_PRIVATE).edit();
        Eeditor.putString("glo_login", "set_pin");
        Eeditor.commit();


    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                if (checkValidation()) {

                    if (CommonUtility.isOnline(SetPin.this)) {

                        //  serverRequest(4);

                        try {

                            MyApplication.saveString("offlinePinString",offlinePinString,SetPin.this);
                            MyApplication.saveString("mpinString",mpinString,SetPin.this);


                            Toast.makeText(SetPin.this,getString(R.string.your_mpin_offline_is) +" :- " +offlinePinString , Toast.LENGTH_SHORT).show();

                            // serverRequest_login_retrofit(100);



                            CommonUtility.showProgressDialog(SetPin.this);

                            request_service_online_volly(100);


                        } catch (Exception e) {

                            CommonUtility.hideProgressDialog(SetPin.this);

                            Toast.makeText(SetPin.this, e.toString(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                        // callApiLogin(100);


                    } else {
                        Toast.makeText(this, R.string.please_check_connection, Toast.LENGTH_SHORT).show();
                    }
                }
                break;

//            case R.id.text_forgot_password:
//
//                showForgotPasswordAlertDialog();
//
//                break;




        }
    }






        private boolean checkValidation() {

        offlinePinString = edittext_offlinePin.getText().toString().trim();
            mpinString = edittext_mpin.getText().toString().trim();

        if (offlinePinString.isEmpty()) {
            Toast.makeText(SetPin.this, getResources().getString(R.string.plz_enter_5_digit_offline_pin), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (offlinePinString.length() < 5) {
            Toast.makeText(SetPin.this, getResources().getString(R.string.plz_enter_5_digit_offline_pin), Toast.LENGTH_SHORT).show();
            return false;
        }

            if (mpinString.isEmpty()) {
                Toast.makeText(SetPin.this, getResources().getString(R.string.enter_mpin_balance_text), Toast.LENGTH_SHORT).show();
                return false;
            }

            if (mpinString.length() < 4) {
                Toast.makeText(SetPin.this, getResources().getString(R.string.enter_mpin_balance_text), Toast.LENGTH_SHORT).show();
                return false;
            }



            return true;
    }






    private void showAlertDialog(String msg, final int requestId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(SetPin.this);
        builder.setTitle(getResources().getString(R.string.transaction_result));
        builder.setCancelable(false);
        builder.setMessage(msg);
        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                if (requestId == 17) {
                    dialogInterface.dismiss();
                    dialog.dismiss();
                } else {
                    dialogInterface.dismiss();
                }

            }
        });
        builder.show();
    }

//    private void showForgotPasswordAlertDialog() {
//
//        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
//
//        LayoutInflater inflater = LayoutInflater.from(this);
//        builder.setCancelable(false);
//        passwordDialogBinding = DataBindingUtil.inflate(inflater, R.layout.forgat_password_dialog, null, false);
//        builder.setView(passwordDialogBinding.getRoot());
//
//        passwordDialogBinding.btnSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                if (!passwordDialogBinding.editPhoneNumber.getText().toString().isEmpty() && passwordDialogBinding.editPhoneNumber.getText().toString().trim().length() > 8) {
//
//
//                    if (!passwordDialogBinding.editDob.getText().toString().isEmpty()) {
//
//
//                        CommonUtility.showProgressDialog(LoginActivity.this);
//
//                        serverRequest_forgot(102);
//
//
//                    } else {
//                        Toast.makeText(LoginActivity.this, getResources().getString(R.string.please_select_dob), Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(LoginActivity.this, "Please Enter Mobile No", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//
//
//        passwordDialogBinding.editDob.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showDatePickerDialog();
//            }
//        });
//
//        passwordDialogBinding.btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog = builder.create();
//        dialog.show();
//
//    }



    private void showAlertDialog_sh(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(SetPin.this);
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

    }


    @Override
    public void serverResponse(int requestNo, JSONObject serverResponse) {

        CommonUtility.hideProgressDialog(SetPin.this);

        try {


            if (serverResponse.toString().contains("Time Out")) {
                showAlertDialog_sh("Time Out");
            } else if (serverResponse.toString().contains("Please try again later")) {
                showAlertDialog_sh("Please try again later");
            }


            else {

                if (requestNo == 200) // **********Login Activation
                {

                    //   serverResponse = new JSONObject("{\"apiname\":\"ACTIVATION\",\"response\":{\"requestcts\":\"06-01-2022 07:48:57 AM\",\"responsects\":\"06-01-2022 07:48:57 AM\",\"agentcode\":\"0982650605\",\"agentname\":\"Prashun\",\"vendorcode\":\"TAFANI\",\"transid\":\"45630627\",\"resultcode\":\"0\",\"resultdescription\":\"Transaction Successful\",\"clienttype\":\"POS\",\"responsevalue\":\"0\",\"walletbalance\":\"49792.0\",\"agenttype\":\"RET1\",\"terminalid\":\"0982650606\",\"activationcode\":\"1893274393509319\"}}");

                    if (serverResponse.has("response")) {

                        JSONObject jsonObject_response = serverResponse.getJSONObject("response");

                        if (jsonObject_response.has("resultcode")) {

                            if (jsonObject_response.has("clienttype")) {

                                String clienttype = jsonObject_response.getString("clienttype");

                                //  if (clienttype.equalsIgnoreCase("POS")) {

                                String resultcode = jsonObject_response.getString("resultcode");
                                String resultdescription = jsonObject_response.getString("resultdescription");

                                if (resultcode.equalsIgnoreCase("0")) {




                                    String loginType = jsonObject_response.getString("agenttype");

                                    if (loginType.equalsIgnoreCase("RET1")) {

                                        MyApplication.saveString("LOGIN_APP", "RETAILER", SetPin.this);


                                        CommonUtility.showProgressDialog(SetPin.this);

                                        request_service_online_volly(100);


                                    }

                                    if (loginType.equalsIgnoreCase("DIST")) {

                                        Toast.makeText(this, getString(R.string.distributor_login), Toast.LENGTH_SHORT).show();

                                        MyApplication.saveString("LOGIN_APP", "DISTRIBUTOR", SetPin.this);

                                        Intent intent = new Intent(SetPin.this, SetPin.class);
                                        startActivity(intent);
                                        finish();
                                    }

                                } else {

                                    failure_case(resultdescription);
                                }
                               /* }
                               else {
                                    Toast.makeText(this,"This application Run POS Device", Toast.LENGTH_SHORT).show();
                                }*/
                            }
                        }
                    }

                }


                else   if (requestNo == 100) //*******************   SERVICE_RESPONE *******************
                {

                    //  serverResponse = new JSONObject("{\"apiname\":\"SERVICE\",\"response\":{\"responsects\":\"01/12/2022 01:59:09 PM\",\"agentcode\":\"0982650605\",\"agentname\":\"Prashun\",\"vendorcode\":\"TAFANI\",\"transid\":\"45631784\",\"resultcode\":\"0\",\"resultdescription\":\"Transaction Successful\",\"clienttype\":\"GPRS\",\"recordcount\":\"20\",\"productDetails\":[{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LIBYANA\",\"vendordesc\":\"LIBYANA\",\"productcode\":\"LMP3\",\"productdesc\":\"LMP3\",\"denomination\":\"3.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"MADAR\",\"vendordesc\":\"MADAR\",\"productcode\":\"AAMP3\",\"productdesc\":\"AAMP3\",\"denomination\":\"3.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LIBYANA\",\"vendordesc\":\"LIBYANA\",\"productcode\":\"LMP5\",\"productdesc\":\"LMP5\",\"denomination\":\"5.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"MADAR\",\"vendordesc\":\"MADAR\",\"productcode\":\"AAMP5\",\"productdesc\":\"AAMP5\",\"denomination\":\"5.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LTT\",\"vendordesc\":\"LTT\",\"productcode\":\"LTT5\",\"productdesc\":\"LTT5\",\"denomination\":\"5.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"THLC\",\"vendordesc\":\"Hatif Libya\",\"productcode\":\"HLC5\",\"productdesc\":\"HLC5\",\"denomination\":\"5.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LIBYANA\",\"vendordesc\":\"LIBYANA\",\"productcode\":\"LMP10\",\"productdesc\":\"LMP10\",\"denomination\":\"10.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"MADAR\",\"vendordesc\":\"MADAR\",\"productcode\":\"AAMP10\",\"productdesc\":\"AAMP10\",\"denomination\":\"10.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LTT\",\"vendordesc\":\"LTT\",\"productcode\":\"LTT10\",\"productdesc\":\"LTT10\",\"denomination\":\"10.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"THLC\",\"vendordesc\":\"Hatif Libya\",\"productcode\":\"HLC10\",\"productdesc\":\"HLC10\",\"denomination\":\"10.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"MADAR\",\"vendordesc\":\"MADAR\",\"productcode\":\"AAMP20\",\"productdesc\":\"AAMP20\",\"denomination\":\"20.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LTT\",\"vendordesc\":\"LTT\",\"productcode\":\"LTT20\",\"productdesc\":\"LTT20\",\"denomination\":\"20.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"THLC\",\"vendordesc\":\"Hatif Libya\",\"productcode\":\"HLC20\",\"productdesc\":\"HLC20\",\"denomination\":\"20.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LIBYANA\",\"vendordesc\":\"LIBYANA\",\"productcode\":\"LMP30\",\"productdesc\":\"LMP30\",\"denomination\":\"30.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LTT\",\"vendordesc\":\"LTT\",\"productcode\":\"LTT30\",\"productdesc\":\"LTT30\",\"denomination\":\"30.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"MADAR\",\"vendordesc\":\"MADAR\",\"productcode\":\"AAMP40\",\"productdesc\":\"AAMP40\",\"denomination\":\"40.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LIBYANA\",\"vendordesc\":\"LIBYANA\",\"productcode\":\"LMP100\",\"productdesc\":\"LMP100\",\"denomination\":\"100.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"MADAR\",\"vendordesc\":\"MADAR\",\"productcode\":\"AAMP100\",\"productdesc\":\"AAMP100\",\"denomination\":\"100.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LTT\",\"vendordesc\":\"LTT\",\"productcode\":\"LTT100\",\"productdesc\":\"LTT 100\",\"denomination\":\"100.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"THLC\",\"vendordesc\":\"Hatif Libya\",\"productcode\":\"HLC100\",\"productdesc\":\"HLC100\",\"denomination\":\"100.0\"}],\"qty\":\"1000\",\"parentname\":\"\",\"parentcode\":\"\",\"terminalid\":\"\",\"operatorcount\":\"18\",\"onlineoperatorcount\":\"9\",\"settings\":{},\"operatorDetails\":[{\"name\":\"TAFANI\",\"code\":\"TAFANI\"},{\"name\":\"Hatif Libya\",\"code\":\"THLC\"},{\"name\":\"LTT\",\"code\":\"LTT\"},{\"name\":\"MADAR\",\"code\":\"MADAR\"},{\"name\":\"LIBYANA\",\"code\":\"LIBYANA\"},{\"name\":\"ANI Pay\",\"code\":\"ANIP\"},{\"name\":\"1st NET\",\"code\":\"FNET\"},{\"name\":\"Digital Impulse\",\"code\":\"NBDA\"},{\"name\":\"City WiFi\",\"code\":\"CFNET\"},{\"name\":\"AlRiyada\",\"code\":\"RDIT\"},{\"name\":\"XNET\",\"code\":\"XNET\"},{\"name\":\"ALKAFAA\",\"code\":\"ALKAFAA\"},{\"name\":\"CONNECT\",\"code\":\"CONNECT\"},{\"name\":\"Giga Net\",\"code\":\"GIGA\"},{\"name\":\"Souqprimo\",\"code\":\"SP\"},{\"name\":\"ZAJEL\",\"code\":\"ZAJEL\"},{\"name\":\"SPGF test\",\"code\":\"SPGF\"},{\"name\":\"PrimoTech\",\"code\":\"PT\"}],\"onlineoperatorDetails\":[{\"name\":\"1st NET\",\"code\":\"FNET\"},{\"name\":\"City WiFi\",\"code\":\"CFNET\"},{\"name\":\"LTT\",\"code\":\"LTT\"},{\"name\":\"LIBYANA\",\"code\":\"LIBYANA\"},{\"name\":\"MADAR\",\"code\":\"MADAR\"},{\"name\":\"ALKAFAA\",\"code\":\"ALKAFAA\"},{\"name\":\"Digital Impulse\",\"code\":\"NBDA\"},{\"name\":\"AlRiyada\",\"code\":\"RDIT\"},{\"name\":\"Hatif Libya\",\"code\":\"THLC\"}]}}");

                    if (serverResponse.has("response")) {

                        JSONObject jsonObject_response = serverResponse.getJSONObject("response");

                        if (jsonObject_response.has("resultcode")) {

                            String resultcode = jsonObject_response.getString("resultcode");
                            String resultdescription = jsonObject_response.getString("resultdescription");

                            if (resultcode.equalsIgnoreCase("0")) {

                                //  operator_pinsale.clear();

                                JSONArray jsonArray_operatorDetails = jsonObject_response.optJSONArray("operatorDetails");

                                // ServiceOperatorDownloadOfflineModel operatorModal_temp = new ServiceOperatorDownloadOfflineModel(getString(R.string.please_select_operator),getString(R.string.please_select_operator));
                                /// operator_pinsale.add(operatorModal_temp);

                                for (int i = 0; i < jsonArray_operatorDetails.length(); i++) {

                                    JSONObject jsonObject = jsonArray_operatorDetails.optJSONObject(i);

                                    // ##################  Database  Operator Details ##################

                                    ServiceOperatorDownloadOfflineModel serviceDownloadOfflineModel = new ServiceOperatorDownloadOfflineModel(
                                            jsonObject.optString("name"),
                                            jsonObject.optString("code")
                                    );

                                    serviceRepository.insert(serviceDownloadOfflineModel);
                                }

                                // ####################################################################

                                JSONArray jasonArray_productDetails_service_api = jsonObject_response.optJSONArray("productDetails");

                                for (int i = 0; i < jasonArray_productDetails_service_api.length(); i++) {

                                    JSONObject jsonObject = jasonArray_productDetails_service_api.optJSONObject(i);

                                    // ##################  Database  Product Details  ##################

                                    ServiceProductDownloadOfflineModel serviceDownloadOfflineModel = new ServiceProductDownloadOfflineModel(
                                            jsonObject.getString("productcode"),
                                            jsonObject.getString("vendorcode"),
                                            jsonObject.getString("denomination")
                                    );
                                    serviceRepository.insert(serviceDownloadOfflineModel);

                                }
                                // not call bevause Posstock 0 select option
                                //   CommonUtility.showProgressDialog(SetPin.this);
                                ///   request_posstock_o_volly(105);



                               // Toast.makeText(this,"*********** Service Api Success ***********", Toast.LENGTH_SHORT).show();


                                Toast.makeText(this, getString(R.string.retailer_login), Toast.LENGTH_SHORT).show();

                                MyApplication.saveString("LOGIN_APP", "RETAILER", SetPin.this);

                                Intent intent = new Intent(SetPin.this, LoginActivityPin.class);
                                startActivity(intent);
                                finish();



                                //  CommonUtility.showProgressDialog(SetPin.this);

                             //   request_bulkdown_volly(106);


                                // ####################################################################

                            } else {
                                failure_case(resultdescription);
                            }
                        }
                    }
                }


         /*       else   if (requestNo == 106)    //*******************    BULK_DOWNLOAD_RESPONE *******************
                {

                    // serverResponse=new JSONObject("{\"apiname\":\"BULKDWN\",\"response\":{\"responsects\":\"12-01-2022 08:03:13 PM\",\"agentcode\":\"0982650605\",\"vendorcode\":\"TAFANI\",\"transid\":\"45631855\",\"resultcode\":\"0\",\"resultdescription\":\"Transaction Successful\",\"clienttype\":\"POS\",\"recordcount\":\"2\",\"records\":[{\"vendorcode\":\"LIBYANA\",\"productcode\":\"LMP3\",\"denomination\":\"3.0\",\"recordcount\":\"2\",\"r\":[{\"p\":\"69DAE1518FFBA9982279E86462E2B7A2\",\"s\":\"225432150060726\"},{\"p\":\"D228284DEC131410D8FE21B0AE182022\",\"s\":\"225432150060727\"}]}],\"terminalid\":\"861256041366507\"}}");

                    if (serverResponse.has("response")) {

                        JSONObject jsonObject_response = serverResponse.getJSONObject("response");

                        if (jsonObject_response.has("resultcode")) {

                            String resultcode = jsonObject_response.getString("resultcode");
                            String resultdescription = jsonObject_response.getString("resultdescription");

                            //  bulkdownloadList_pinsale = new ArrayList<>();
                            //   bulkdownloadList_pinsale.clear();


                            if (resultcode.equalsIgnoreCase("0")) {

                                MyApplication.saveString("ORDERPIN_RECEIPTPAGE_API",serverResponse.toString(), SetPin.this);

                                JSONArray jsonArray_records = jsonObject_response.getJSONArray("records");

                                for(int i=0;i<jsonArray_records.length();i++) {

                                    BulkDownlaodModalOnline bulkDownlaodModalOnline = new BulkDownlaodModalOnline();

                                    JSONObject jsonObject3 = jsonArray_records.getJSONObject(i);

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

                                        // ##################   bulkdownload Database ##################

                                        BulkDownloadOfflineModel bulkDownloadOfflineModel = new BulkDownloadOfflineModel(vendorcode_bulkdownlaod,
                                                productcode_bulkdownlaod,denomination_bulkdownlaod,recordcount_bulkdownlaod,
                                                jsonObject4.optString("p"),
                                                jsonObject4.optString("s"),"N");


                                        serviceRepository.insert(bulkDownloadOfflineModel);

                                        // #################################################################

                                    }
                                }

                               *//* if(currentserialnos_temp.contains(","))
                                {
                                    if ((currentserialnos_temp != null) && (currentserialnos_temp.length() > 0)) {
                                        currentserialnos = currentserialnos_temp.substring(0, currentserialnos_temp.length() - 1);
                                    }
                                }*//*


                                // Not Call because Offline Mode how Select product Details
                                //  CommonUtility.showProgressDialog(SetPin.this);
                                //  request_posstock_S_1st_volly(107);





                                Toast.makeText(this,"*********** Bulk Download  Success ***********", Toast.LENGTH_SHORT).show();


                                Toast.makeText(this, getString(R.string.retailer_login), Toast.LENGTH_SHORT).show();

                                MyApplication.saveString("LOGIN_APP", "RETAILER", SetPin.this);

                                Intent intent = new Intent(SetPin.this, LoginActivityPin.class);
                                startActivity(intent);
                                finish();







                            } else {


                                if(resultdescription.equalsIgnoreCase("Pins Not Found"))
                                {

                                   // Toast.makeText(this, resultdescription, Toast.LENGTH_SHORT).show();




                                    Toast.makeText(this, getString(R.string.retailer_login), Toast.LENGTH_SHORT).show();

                                    MyApplication.saveString("LOGIN_APP", "RETAILER", SetPin.this);

                                    Intent intent = new Intent(SetPin.this, LoginActivityPin.class);
                                    startActivity(intent);
                                    finish();

                                }
                                else {
                                    failure_case(resultdescription);

                                }

                            }
                        }
                    }

                }
*/



            }


        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            finish();
            e.printStackTrace();
        }


    }


    private void request_service_online_volly(int requestNo) {

        try {

            JSONObject json_main = new JSONObject();

            json_main.put("apiname", "SERVICE");

            JSONObject jsonObject_request = new JSONObject();
            jsonObject_request.put("agentcode", MyApplication.getSaveString("mobileNoString", SetPin.this));

            String mPin = MyApplication.getSaveString("mpinString", SetPin.this);

            String key = Md5.getMd5Hash(MyApplication.getSaveString("mobileNoString", SetPin.this)+mPin).toUpperCase(Locale.ENGLISH);

            jsonObject_request.put("pin",key);
            // jsonObject_request.put("terminalid",MyApplication.getSaveString("terminalIdString", OrderPinActivity.this));
            jsonObject_request.put("terminalid","");  // if send Then not working
            jsonObject_request.put("vendorcode","TAFANI");
            jsonObject_request.put("clienttype","POS");
            jsonObject_request.put("agenttransid","56637461");
            jsonObject_request.put("comments","Ver-1");

            json_main.put("request", jsonObject_request);

            new VollyRequestResponse(SetPin.this, SetPin.this, requestNo, base_url+login_service_apinname, json_main.toString());

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            CommonUtility.hideProgressDialog(SetPin.this);

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
            jsonObject_request.put("agentcode", MyApplication.getSaveString("mobileNoString", SetPin.this));
            jsonObject_request.put("agenttransid",MyApplication.getSaveString("terminalIdString", SetPin.this));
            jsonObject_request.put("vendorcode","TAFANI");
            jsonObject_request.put("terminalid",MyApplication.getSaveString("terminalIdString", SetPin.this));
            jsonObject_request.put("requestcts","12/13/21 10:22:20");
            jsonObject_request.put("clienttype","POS");
            jsonObject_request.put("userid","33333");    // hard code acording to rohit
            jsonObject_request.put("comments","PIN BULKDOWNLOAD");

            json_main.put("request", jsonObject_request);

            new VollyRequestResponse(SetPin.this, SetPin.this, requestNo, base_url+ret_bulkdown_apiname, json_main.toString());

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            CommonUtility.hideProgressDialog(SetPin.this);

            e.printStackTrace();
        }

    }

    private void request_printf_offline_volly(int requestNo) {


            /*
            {
                "apiname": "PINTRF",
                "request": {
                    "agentcode": "0982650605",
                    "udv2":"Pin select",
                    "destination":"0982650606",
                    "pin":"1893274393509319",
                    "terminalid":"0982650606",
                    "vendorcode":"TAFANI",
                    "clienttype":"POS",
                    "comments":"ORDER PIN",
                    "records":[{
                        "productcode":"LMP3",
                        "qty":"2"
                    }]
                }
            }
             */

        try {

            JSONObject json_main = new JSONObject();

            json_main.put("apiname", "PINTRF");

            JSONObject jsonObject_request = new JSONObject();
             jsonObject_request.put("agentcode", MyApplication.getSaveString("mobileNoString", SetPin.this));
           // jsonObject_request.put("agentcode","0982650605");
            jsonObject_request.put("udv2","Pin select");

            jsonObject_request.put("destination",MyApplication.getSaveString("mobileNoString", SetPin.this));
            jsonObject_request.put("destination","0982650606");


            // String key = Md5.getMd5Hash(MyApplication.getSaveString("mobileNoString", SetPin.this)+mpinString).toUpperCase(Locale.ENGLISH);
            //  jsonObject_request.put("pin",key);

            jsonObject_request.put("pin","1893274393509319");

            //  jsonObject_request.put("terminalid",MyApplication.getSaveString("terminalIdString", SetPin.this));
            jsonObject_request.put("terminalid","0982650606");

            jsonObject_request.put("vendorcode","TAFANI");
            jsonObject_request.put("clienttype","POS");
            jsonObject_request.put("comments","ORDER PIN");


            JSONArray jsonArray=new JSONArray();

            for(int i=0;i<1;i++){

                JSONObject jsonObject_inner=new JSONObject();
                jsonObject_inner.put("productcode","LMP3");
                jsonObject_inner.put("qty","2");

                jsonArray.put(jsonObject_inner);
            }

            jsonObject_request.put("records",jsonArray);

            json_main.put("request", jsonObject_request);

            new VollyRequestResponse(SetPin.this, SetPin.this, requestNo, base_url+printf_ret_apiname, json_main.toString());

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            CommonUtility.hideProgressDialog(SetPin.this);

            e.printStackTrace();
        }


    }



}
