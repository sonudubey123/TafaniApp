package com.sunmi.printerhelper.nextgen_sharique.login;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import static com.sunmi.printerhelper.nextgen_sharique.voly_url.Url.activation_des_ret_apiname;
import static com.sunmi.printerhelper.nextgen_sharique.voly_url.Url.base_url;
import static com.sunmi.printerhelper.nextgen_sharique.voly_url.Url.login_pinsalepurchase_apinname;
import static com.sunmi.printerhelper.nextgen_sharique.voly_url.Url.login_service_apinname;
import static com.sunmi.printerhelper.nextgen_sharique.voly_url.Url.printf_ret_apiname;
import static com.sunmi.printerhelper.nextgen_sharique.voly_url.Url.ret_bulkdown_apiname;
import static com.sunmi.printerhelper.nextgen_sharique.voly_url.Url.ret_posstock_apiname;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.sunmi.printerhelper.local_set.LocalSetLanguage;
import com.sunmi.printerhelper.nextgen_sharique.application_sharepreference.MyApplication;
import com.sunmi.printerhelper.nextgen_sharique.database.offline.OperatorOfflineModel;
import com.sunmi.printerhelper.nextgen_sharique.database.offline.PrintfOfflineModal;
import com.sunmi.printerhelper.nextgen_sharique.database.offline.ProductOfflineModel;
import com.sunmi.printerhelper.nextgen_sharique.database.offline.ServiceRepository;
import com.sunmi.printerhelper.nextgen_sharique.database.offline.modal_offline.BulkDownloadOfflineModel;
import com.sunmi.printerhelper.nextgen_sharique.database.offline.modal_offline.PosstockODownloadOfflineModel;
import com.sunmi.printerhelper.nextgen_sharique.database.offline.modal_offline.ServiceOperatorDownloadOfflineModel;
import com.sunmi.printerhelper.nextgen_sharique.database.offline.modal_offline.ServiceProductDownloadOfflineModel;
import com.sunmi.printerhelper.nextgen_sharique.interf.InterHttpServerResponse;
import com.sunmi.printerhelper.nextgen_sharique.loading_internet.CommonUtility;

import com.sunmi.printerhelper.nextgen_sharique.voly_url.VollyRequestResponse;

import android.app.AlertDialog;

import tafani.sunmi.printer.R;

public class LoginActivation extends AppCompatActivity implements View.OnClickListener, InterHttpServerResponse {


    TextView btn_submit;
    EditText edit_mobile_number,edittext_mpin,edittext_activationCode,edittext_terminalId;
    String mobileNoString="",mpinString="",activationCodeString="";
    AlertDialog dialog;

    String dateOfBirthString;
    MyApplication myApplication;

    String languageToUse="";

    String imeiString="";
    int PERMISSIONS_REQUEST_READ_PHONE_STATE=100;



    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            myApplication = (MyApplication) getApplicationContext();
            languageToUse=myApplication.getmSharedPreferences().getString("languageToUse","");

            if (languageToUse.trim().length() == 0) {

                languageToUse = "ar";
                LocalSetLanguage.LocalSet(languageToUse,LoginActivation.this);
            }
            else
            {
                LocalSetLanguage.LocalSet(languageToUse,LoginActivation.this);
            }


            setContentView(R.layout.login_activation);






           // CommonUtility.showProgressDialog(LoginActivation.this);
          //  request_service_offline_volly(200);        // API CALL  getService




            edit_mobile_number = (EditText) findViewById(R.id.edit_mobile_number);
            edittext_mpin = (EditText) findViewById(R.id.edittext_mpin);
            edittext_activationCode = (EditText) findViewById(R.id.edittext_activationCode);
            edittext_terminalId = (EditText) findViewById(R.id.edittext_terminalId);


            btn_submit = (TextView) findViewById(R.id.btn_submit);
            btn_submit.setOnClickListener(this);


            if(languageToUse.equalsIgnoreCase("ar"))
            {
                edit_mobile_number.setGravity(Gravity.RIGHT);
                edittext_mpin.setGravity(Gravity.RIGHT);
                edittext_activationCode.setGravity(Gravity.RIGHT);
                edittext_terminalId.setGravity(Gravity.RIGHT);
            }
            else
            {
                edit_mobile_number.setGravity(Gravity.LEFT);
                edittext_mpin.setGravity(Gravity.LEFT);
                edittext_activationCode.setGravity(Gravity.LEFT);
                edittext_terminalId.setGravity(Gravity.LEFT);

            }



            SharedPreferences.Editor Eeditor = LoginActivation.this.getSharedPreferences("EU_MPIN", MODE_PRIVATE).edit();
            Eeditor.putString("glo_login", "glo_activation");
            Eeditor.commit();

            getDeviceImei();



/*

           // getDeviceImei();

       */
/*     if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                    != PackageManager.PERMISSION_GRANTED)
            {
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},PERMISSIONS_REQUEST_READ_PHONE_STATE);
            }

            else {
                getDeviceImei();
            }*//*


            if (mayRequestPhoneState()) {

                getDeviceImei();

            }
*/

        }
        catch (Exception e)
        {
            Toast.makeText(LoginActivation.this,e.toString(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }





    }
    private static final int READ_PHONE_STATE = 5;


    private boolean mayRequestPhoneState()

    {

        try {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                return true;
            }

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                    == PackageManager.PERMISSION_GRANTED)



            {
                return true;
            }

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                    == PackageManager.PERMISSION_DENIED) {
                if (shouldShowRequestPermissionRationale(Manifest.permission.READ_PHONE_STATE)) {
                    //  requestPermissions(new String[]{Manifest.permission.RECEIVE_SMS}, REQUEST_SMS_PHONE);
                } else {

                }
                requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE}, READ_PHONE_STATE);

            }




        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(LoginActivation.this,e.toString(), Toast.LENGTH_SHORT).show();

        }
        return false;
    }

    void getDeviceImei()
    {
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

        // imeiString = Settings.Secure.getString(LoginActivation.this.getContentResolver(), Settings.Secure.ANDROID_ID);
            imeiString = MyApplication.getSN();
          //Toast.makeText(this,"TerminalId is=" +imeiString, Toast.LENGTH_SHORT).show();

        }
        else {
           /* TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            imeiString = telephonyManager.getDeviceId();*/

            imeiString = MyApplication.getSN();

         //   Toast.makeText(this,"TerminalId is=" +imeiString, Toast.LENGTH_SHORT).show();

        }

    }

    private static final int REQUEST_CODE = 101;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission granted.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Permission denied.", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit:
                if (checkValidation()) {

                    if (CommonUtility.isOnline(LoginActivation.this)) {

                        //  serverRequest(4);

                        try {

                            CommonUtility.showProgressDialog(LoginActivation.this);

                            serverRequest_activationCode_volly(200);


                        } catch (Exception e) {

                            CommonUtility.hideProgressDialog(LoginActivation.this);

                            Toast.makeText(LoginActivation.this, e.toString(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }



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

        mobileNoString = edit_mobile_number.getText().toString().trim();
        mpinString = edittext_mpin.getText().toString().trim();
        activationCodeString = edittext_activationCode.getText().toString().trim();

        //  terminalIdString = edittext_terminalId.getText().toString().trim();


        if (mobileNoString.isEmpty()) {

            Toast.makeText(LoginActivation.this, getResources().getString(R.string.enter_agent_code), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mobileNoString.length()< 9) {
            Toast.makeText(LoginActivation.this, getResources().getString(R.string.enter_agent_code), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (activationCodeString.isEmpty()) {

            Toast.makeText(LoginActivation.this, getResources().getString(R.string.please_enter_activitionCode), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (activationCodeString.length()< 8) {
            Toast.makeText(LoginActivation.this, getResources().getString(R.string.please_enter_activitionCode), Toast.LENGTH_SHORT).show();
            return false;
        }

     /*   if (terminalIdString.isEmpty()) {

            Toast.makeText(LoginActivation.this, getResources().getString(R.string.please_enter_terminal_id), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (terminalIdString.length()< 8) {
            Toast.makeText(LoginActivation.this, getResources().getString(R.string.please_enter_terminal_id), Toast.LENGTH_SHORT).show();
            return false;
        }*/

 /*       if (mpinString.isEmpty()) {
            Toast.makeText(LoginActivation.this, getResources().getString(R.string.enter_mpin), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mpinString.length() < 4) {
            Toast.makeText(LoginActivation.this, getResources().getString(R.string.enter_mpin), Toast.LENGTH_SHORT).show();
            return false;
        }*/


        return true;
    }


    private void serverRequest_activationCode_volly(int requestNo) {


        try {

            JSONObject json_main = new JSONObject();



/*            {
                "apiname": "ACTIVATION",
                    "request": {
                "agentcode": "0982650605",
                        "pin":"5244F6C28C4A956B3EEC4101BB077560",
                        "activationcode":"1893274393509319",
                        "terminalid":"0982650606",
                        "vendorcode":"TAFANI"
            }
            }*/

            MyApplication.saveString("mobileNoString",mobileNoString,LoginActivation.this);
            MyApplication.saveString("activationCodeString",activationCodeString,LoginActivation.this);
            MyApplication.saveString("terminalIdString",imeiString,LoginActivation.this);



            //   MyApplication.saveString("terminalIdString","861256041366507",LoginActivation.this);


            json_main.put("apiname", "ACTIVATION");
            JSONObject jsonObject_request = new JSONObject();
            jsonObject_request.put("agentcode", mobileNoString);

          //  String key = Md5.getMd5Hash(mobileNoString+mpinString).toUpperCase(Locale.ENGLISH);
          //  jsonObject_request.put("pin", key);
            jsonObject_request.put("activationcode", activationCodeString);
            jsonObject_request.put("terminalid", imeiString);

           // Toast.makeText(this,"TerminalId is=" +imeiString, Toast.LENGTH_SHORT).show();

            //  jsonObject_request.put("terminalid", "861256041366507");
            jsonObject_request.put("vendorcode", "TAFANI");

            json_main.put("request", jsonObject_request);

            new VollyRequestResponse(LoginActivation.this, LoginActivation.this, requestNo, base_url+activation_des_ret_apiname, json_main.toString());

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            CommonUtility.hideProgressDialog(LoginActivation.this);

            e.printStackTrace();
        }

    }




    private void showAlertDialog(String msg, final int requestId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivation.this);
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
//                        CommonUtility.showProgressDialog(LoginActivation.this);
//
//                        serverRequest_forgot(102);
//
//
//                    } else {
//                        Toast.makeText(LoginActivation.this, getResources().getString(R.string.please_select_dob), Toast.LENGTH_SHORT).show();
//                    }
//                } else {
//                    Toast.makeText(LoginActivation.this, "Please Enter Mobile No", Toast.LENGTH_SHORT).show();
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

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivation.this);
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

        CommonUtility.hideProgressDialog(LoginActivation.this);

        try {


            if (serverResponse.toString().contains("Time Out")) {
                showAlertDialog_sh("Time Out");
            } else if (serverResponse.toString().contains("Please try again later")) {
                showAlertDialog_sh("Please try again later");
            }


            else {

                if (requestNo == 200) // **********Login Activation
                {

                   //    serverResponse = new JSONObject("{\"apiname\":\"ACTIVATION\",\"response\":{\"requestcts\":\"06-01-2022 07:48:57 AM\",\"responsects\":\"06-01-2022 07:48:57 AM\",\"agentcode\":\"0982650605\",\"agentname\":\"Prashun\",\"vendorcode\":\"TAFANI\",\"transid\":\"45630627\",\"resultcode\":\"0\",\"resultdescription\":\"Transaction Successful\",\"clienttype\":\"android\",\"responsevalue\":\"0\",\"walletbalance\":\"49792.0\",\"agenttype\":\"RET1\",\"terminalid\":\"0982650606\",\"activationcode\":\"1893274393509319\"}}");

                    if (serverResponse.has("response")) {

                        JSONObject jsonObject_response = serverResponse.getJSONObject("response");

                        if (jsonObject_response.has("resultcode")) {

                            if (jsonObject_response.has("clienttype")) {

                                String clienttype = jsonObject_response.getString("clienttype");


                                String resultcode = jsonObject_response.getString("resultcode");
                                String resultdescription = jsonObject_response.getString("resultdescription");

                                if (resultcode.equalsIgnoreCase("0")) {

                                    if (clienttype.equalsIgnoreCase("pos")||
                                            clienttype.equalsIgnoreCase("POS")) {

                                  /*  if (clienttype.equalsIgnoreCase("android")||
                                            clienttype.equalsIgnoreCase("Android")||
                                            clienttype.equalsIgnoreCase("pos")||
                                            clienttype.equalsIgnoreCase("POS")||
                                            clienttype.equalsIgnoreCase("")) {*/

                                        String loginType = jsonObject_response.getString("agenttype");

                                        if (loginType.equalsIgnoreCase("RET1")) {


                                            Intent intent = new Intent(LoginActivation.this, SetPin.class);
                                            startActivity(intent);
                                            finish();


                                        }

                                        if (loginType.equalsIgnoreCase("DIST")) {

                                            Toast.makeText(this, "Login only retailer app", Toast.LENGTH_SHORT).show();


                                           /* Toast.makeText(this, getString(R.string.distributor_login), Toast.LENGTH_SHORT).show();

                                            MyApplication.saveString("LOGIN_APP", "DISTRIBUTOR", LoginActivation.this);

                                            Intent intent = new Intent(LoginActivation.this, SetPin.class);
                                            startActivity(intent);
                                            finish();*/
                                        }

                                } else {
                                    Toast.makeText(this, getText(R.string.this_application_run_pos), Toast.LENGTH_SHORT).show();
                                }
                            }
                                else {

                                    failure_case(resultdescription);
                                }

                            }
                        }
                    }

                }









            }


        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            finish();
            e.printStackTrace();
        }


    }



}
