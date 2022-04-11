package com.sunmi.printerhelper.nextgen_sharique.transaction_history.retailer;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import org.json.JSONObject;

import java.util.Locale;

import static com.sunmi.printerhelper.nextgen_sharique.voly_url.Url.base_url;
import static com.sunmi.printerhelper.nextgen_sharique.voly_url.Url.login_ret_transactiionHistory_apinname;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import tafani.sunmi.printer.R;
import com.sunmi.printerhelper.local_set.LocalSetLanguage;
import com.sunmi.printerhelper.nextgen_sharique.application_sharepreference.MyApplication;
import com.sunmi.printerhelper.nextgen_sharique.interf.InterHttpServerResponse;
import com.sunmi.printerhelper.nextgen_sharique.loading_internet.CommonUtility;
import com.sunmi.printerhelper.nextgen_sharique.voly_url.Md5;
import com.sunmi.printerhelper.nextgen_sharique.voly_url.VollyRequestResponse;

import android.app.AlertDialog;


public class TransactionHistoryRetailer extends AppCompatActivity implements View.OnClickListener, InterHttpServerResponse {



    private Dialog dialog;

    Button imageButton1;
    EditText edittext_mpin;
    String mpinString="";

    String dateOfBirthString;
    MyApplication myApplication;

    String languageToUse="";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            myApplication = (MyApplication) getApplicationContext();
            languageToUse=myApplication.getmSharedPreferences().getString("languageToUse","");

            if (languageToUse.trim().length() == 0) {

                languageToUse = "ar";
                LocalSetLanguage.LocalSet(languageToUse, TransactionHistoryRetailer.this);
            }
            else
            {
                LocalSetLanguage.LocalSet(languageToUse,TransactionHistoryRetailer.this);
            }
            setContentView(R.layout.transhistory_retailer);


            edittext_mpin = (EditText) findViewById(R.id.edittext_mpin);
            imageButton1 = (Button) findViewById(R.id.imageButton1);
            imageButton1.setOnClickListener(this);



            if(languageToUse.equalsIgnoreCase("ar"))
            {
              //  imageButton1.setBackgroundResource(R.drawable.conform);
                edittext_mpin.setGravity(Gravity.RIGHT);

            }
            else
            {
              //  imageButton1.setBackgroundResource(R.drawable.confirmbutton);
                edittext_mpin.setGravity(Gravity.LEFT);

            }




        }
        catch (Exception e)
        {
            Toast.makeText(TransactionHistoryRetailer.this,e.toString(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }





    }


    protected void alertbox(String mymessage,String heading, Context context)
    {


        try {


            if (mymessage.equalsIgnoreCase("Invalid PIN")) {

                Toast.makeText(TransactionHistoryRetailer.this,getString(R.string.invalid_pin), Toast.LENGTH_SHORT).show();

            } else if ((mymessage.equalsIgnoreCase("Subscriber Blocked"))) {

                Toast.makeText(TransactionHistoryRetailer.this,getString(R.string.subscriber_blocked), Toast.LENGTH_SHORT).show();

            }

            else if ((mymessage.equalsIgnoreCase("Agent Blocked"))) {

                Toast.makeText(TransactionHistoryRetailer.this,getString(R.string.agent_bloack), Toast.LENGTH_SHORT).show();

            }

            else if ((mymessage.equalsIgnoreCase("Subscriber/Agent Blocked"))) {

                Toast.makeText(TransactionHistoryRetailer.this,getString(R.string.subscriber_agent_blocked), Toast.LENGTH_SHORT).show();

            }

            else if ((mymessage.equalsIgnoreCase("Your account is Blocked"))) {
                Toast.makeText(TransactionHistoryRetailer.this,getString(R.string.subscriber_agent_blocked), Toast.LENGTH_SHORT).show();

            }

            else if ((mymessage.equalsIgnoreCase("Insufficient Fund"))) {

                Toast.makeText(TransactionHistoryRetailer.this,getString(R.string.insuffience_fund), Toast.LENGTH_SHORT).show();
            }

            else if ((mymessage.equalsIgnoreCase("Insufficient Wallet"))) {

                Toast.makeText(TransactionHistoryRetailer.this,getString(R.string.insuffience_wallet), Toast.LENGTH_SHORT).show();

            }

            else if ((mymessage.equalsIgnoreCase("Records Not found"))) {

                Toast.makeText(TransactionHistoryRetailer.this,getString(R.string.record_not_found), Toast.LENGTH_SHORT).show();

            }

            else {

                Toast.makeText(TransactionHistoryRetailer.this,mymessage, Toast.LENGTH_SHORT).show();

            }
        }
        catch (Exception e)
        {
            Toast.makeText(TransactionHistoryRetailer.this,e.toString(), Toast.LENGTH_SHORT).show();

            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageButton1:
                if (checkValidation()) {

                    if (CommonUtility.isOnline(TransactionHistoryRetailer.this)) {

                        //  serverRequest(4);

                        try {

                            CommonUtility.showProgressDialog(TransactionHistoryRetailer.this);



                               serverRequest_transactionHistory_volly(103);

                              // serverRequest_login_retrofit(100);

                        } catch (Exception e) {

                            CommonUtility.hideProgressDialog(TransactionHistoryRetailer.this);

                            Toast.makeText(TransactionHistoryRetailer.this, e.toString(), Toast.LENGTH_SHORT).show();
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

        mpinString = edittext_mpin.getText().toString().trim();

        if (mpinString.isEmpty()) {
            Toast.makeText(TransactionHistoryRetailer.this, getResources().getString(R.string.enter_mpin), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mpinString.length() < 4) {
            Toast.makeText(TransactionHistoryRetailer.this, getResources().getString(R.string.enter_mpin), Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }



    private void serverRequest_transactionHistory_volly(int requestNo) {


/*
       {
            "apiname": "TRANSHISTORY",
            "request": {
                "agentcode": "0982650605",
                "pin": "E24B7D1BCA5464639ECE18EBF63ABA2C",
                "vendorcode": "TAFANI",
                "clienttype": "GPRS",
                "comments":"These are PROFILEHISTORY comments",
                "cnt":"12",
                "criteria":{
                    "datefrom": "06/04/2021 12:47:20 PM",
                    "dateto": "12/01/2021 12:47:20 PM",
                    "transtype": "WALLETADJ,WALLETTOPUP,TOPUP,WALLETTRF,PURCHASE"
                }
            }
}

*/

        try {




            JSONObject json_main = new JSONObject();

            json_main.put("apiname", "TRANSHISTORY");

            JSONObject jsonObject_request = new JSONObject();
            jsonObject_request.put("posserialno",MyApplication.getSN());
            jsonObject_request.put("agentcode",MyApplication.getSaveString("mobileNoString", TransactionHistoryRetailer.this));
            String key = Md5.getMd5Hash(MyApplication.getSaveString("mobileNoString", TransactionHistoryRetailer.this)+mpinString).toUpperCase(Locale.ENGLISH);
            jsonObject_request.put("pin",key);
            jsonObject_request.put("vendorcode","TAFANI");
            jsonObject_request.put("clienttype","POS");
            jsonObject_request.put("comments","These are PROFILEHISTORY comments");
            jsonObject_request.put("cnt","50");

            JSONObject jsonObject_criteria = new JSONObject();

            jsonObject_criteria.put("datefrom","06/04/2018 12:47:20 PM");
            jsonObject_criteria.put("dateto","12/01/2023 12:47:20 PM");
            jsonObject_criteria.put("transtype","WALLETADJ,WALLETTOPUP,TOPUP,WALLETTRF,PURCHASE,PINTRF,OFFLINEPURCHASE");

            jsonObject_request.put("criteria", jsonObject_criteria);

            json_main.put("request", jsonObject_request);

            new VollyRequestResponse(TransactionHistoryRetailer.this, TransactionHistoryRetailer.this, requestNo, base_url+login_ret_transactiionHistory_apinname, json_main.toString());

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            CommonUtility.hideProgressDialog(TransactionHistoryRetailer.this);

            e.printStackTrace();
        }

    }




    private void showAlertDialog(String msg, final int requestId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(TransactionHistoryRetailer.this);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(TransactionHistoryRetailer.this);
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
    public void serverResponse(int requestNo, JSONObject serverResponse) {

        CommonUtility.hideProgressDialog(TransactionHistoryRetailer.this);

        try {


            if (serverResponse.toString().contains("Time Out")) {
                showAlertDialog_sh("Time Out");
            } else if (serverResponse.toString().contains("Please try again later")) {
                showAlertDialog_sh("Please try again later");
            }



            else {

                if (requestNo == 103) {

                 //   serverResponse = new JSONObject("{\"apiname\":\"TRANSHISTORY\",\"response\":{\"requestcts\":\"12/24/2021 11:31:20 PM\",\"responsects\":\"12/24/2021 11:31:21 PM\",\"agentcode\":\"0910889355\",\"vendorcode\":\"TAFANI\",\"transid\":\"45629910\",\"resultcode\":\"0\",\"resultdescription\":\"Transaction Successful\",\"clienttype\":\"GPRS\",\"recordcount\":\"10\",\"cnt\":\"12\",\"records\":[{\"resultcode\":\"0\",\"resultdescription\":\"Transaction Successful\",\"reversal\":\"F\",\"amount\":\"2.0\",\"destination\":\"0981088935\",\"transid\":\"45611593\",\"transtype\":\"WALLETTRF\",\"pinno\":\"0\",\"serialno\":\"0\",\"expirydate\":\"\",\"responsects\":\"06/18/2020 08:18:26 AM\",\"clienttype\":\"GPRS\",\"vendorcode\":\"TAFANI\",\"productcode\":\"\",\"fee\":\"0.0\",\"prewalletbalance\":\"4420.39\",\"walletbalance\":\"4418.39\",\"agenttransid\":\"\"},{\"resultcode\":\"0\",\"resultdescription\":\"Transaction Successful\",\"reversal\":\"F\",\"amount\":\"15.0\",\"destination\":\"0981088935\",\"transid\":\"45611591\",\"transtype\":\"WALLETTRF\",\"pinno\":\"0\",\"serialno\":\"0\",\"expirydate\":\"\",\"responsects\":\"06/18/2020 08:16:55 AM\",\"clienttype\":\"GPRS\",\"vendorcode\":\"TAFANI\",\"productcode\":\"\",\"fee\":\"0.0\",\"prewalletbalance\":\"4435.39\",\"walletbalance\":\"4420.39\",\"agenttransid\":\"\"},{\"resultcode\":\"0\",\"resultdescription\":\"Transaction Successful\",\"reversal\":\"F\",\"amount\":\"150.0\",\"destination\":\"0981088935\",\"transid\":\"45611573\",\"transtype\":\"WALLETTRF\",\"pinno\":\"0\",\"serialno\":\"0\",\"expirydate\":\"\",\"responsects\":\"06/18/2020 07:54:10 AM\",\"clienttype\":\"GPRS\",\"vendorcode\":\"TAFANI\",\"productcode\":\"\",\"fee\":\"0.0\",\"prewalletbalance\":\"4585.35\",\"walletbalance\":\"4435.35\",\"agenttransid\":\"\"},{\"resultcode\":\"0\",\"resultdescription\":\"Transaction Successful\",\"reversal\":\"F\",\"amount\":\"15.0\",\"destination\":\"0981088935\",\"transid\":\"45611441\",\"transtype\":\"WALLETTRF\",\"pinno\":\"0\",\"serialno\":\"0\",\"expirydate\":\"\",\"responsects\":\"06/08/2020 12:30:55 PM\",\"clienttype\":\"GPRS\",\"vendorcode\":\"TAFANI\",\"productcode\":\"\",\"fee\":\"0.0\",\"prewalletbalance\":\"4600.35\",\"walletbalance\":\"4585.35\",\"agenttransid\":\"\"},{\"resultcode\":\"0\",\"resultdescription\":\"Transaction Successful\",\"reversal\":\"F\",\"amount\":\"10.0\",\"destination\":\"0981088935\",\"transid\":\"45611433\",\"transtype\":\"WALLETTRF\",\"pinno\":\"0\",\"serialno\":\"0\",\"expirydate\":\"\",\"responsects\":\"06/08/2020 12:25:35 PM\",\"clienttype\":\"GPRS\",\"vendorcode\":\"TAFANI\",\"productcode\":\"\",\"fee\":\"0.0\",\"prewalletbalance\":\"4610.32\",\"walletbalance\":\"4600.32\",\"agenttransid\":\"\"},{\"resultcode\":\"0\",\"resultdescription\":\"Transaction Successful\",\"reversal\":\"F\",\"amount\":\"50.0\",\"destination\":\"0981088935\",\"transid\":\"45611416\",\"transtype\":\"WALLETTRF\",\"pinno\":\"0\",\"serialno\":\"0\",\"expirydate\":\"\",\"responsects\":\"06/08/2020 11:58:42 AM\",\"clienttype\":\"GPRS\",\"vendorcode\":\"TAFANI\",\"productcode\":\"\",\"fee\":\"0.0\",\"prewalletbalance\":\"4660.25\",\"walletbalance\":\"4610.25\",\"agenttransid\":\"\"},{\"resultcode\":\"0\",\"resultdescription\":\"Transaction Successful\",\"reversal\":\"F\",\"amount\":\"100.0\",\"destination\":\"0981088935\",\"transid\":\"45592690\",\"transtype\":\"WALLETTRF\",\"pinno\":\"0\",\"serialno\":\"0\",\"expirydate\":\"\",\"responsects\":\"08/03/2019 01:56:00 PM\",\"clienttype\":\"GPRS\",\"vendorcode\":\"TAFANI\",\"productcode\":\"\",\"fee\":\"0.0\",\"prewalletbalance\":\"4760.25\",\"walletbalance\":\"4660.25\",\"agenttransid\":\"\"},{\"resultcode\":\"0\",\"resultdescription\":\"Transaction Successful\",\"reversal\":\"F\",\"amount\":\"100.0\",\"destination\":\"0981088935\",\"transid\":\"45592689\",\"transtype\":\"WALLETTRF\",\"pinno\":\"0\",\"serialno\":\"0\",\"expirydate\":\"\",\"responsects\":\"08/03/2019 01:51:52 PM\",\"clienttype\":\"GPRS\",\"vendorcode\":\"TAFANI\",\"productcode\":\"\",\"fee\":\"0.0\",\"prewalletbalance\":\"4860.25\",\"walletbalance\":\"4760.25\",\"agenttransid\":\"\"},{\"resultcode\":\"0\",\"resultdescription\":\"Transaction Successful\",\"reversal\":\"F\",\"amount\":\"100.0\",\"destination\":\"0981088935\",\"transid\":\"45592688\",\"transtype\":\"WALLETTRF\",\"pinno\":\"0\",\"serialno\":\"0\",\"expirydate\":\"\",\"responsects\":\"08/03/2019 01:43:12 PM\",\"clienttype\":\"GPRS\",\"vendorcode\":\"TAFANI\",\"productcode\":\"\",\"fee\":\"0.0\",\"prewalletbalance\":\"4960.25\",\"walletbalance\":\"4860.25\",\"agenttransid\":\"\"},{\"resultcode\":\"0\",\"resultdescription\":\"Transaction Successful\",\"reversal\":\"F\",\"amount\":\"100.0\",\"destination\":\"0981088935\",\"transid\":\"45592576\",\"transtype\":\"WALLETTRF\",\"pinno\":\"0\",\"serialno\":\"0\",\"expirydate\":\"\",\"responsects\":\"07/23/2019 12:16:13 PM\",\"clienttype\":\"GPRS\",\"vendorcode\":\"TAFANI\",\"productcode\":\"\",\"fee\":\"0.0\",\"prewalletbalance\":\"5060.25\",\"walletbalance\":\"4960.25\",\"agenttransid\":\"\"}]}}");

                    if (serverResponse.has("response")) {

                        JSONObject jsonObject_response = serverResponse.getJSONObject("response");

                        if (jsonObject_response.has("resultcode")) {


                            String resultcode = jsonObject_response.getString("resultcode");
                            String resultdescription = jsonObject_response.getString("resultdescription");

                            if (resultcode.equalsIgnoreCase("0")) {


                               // Toast.makeText(this, amount, Toast.LENGTH_SHORT).show();

                                MyApplication.saveString("TRANSACTION_HISTORY_RET_API",serverResponse.toString(),TransactionHistoryRetailer.this);


                                Intent intent = new Intent(TransactionHistoryRetailer.this, TransactionDetailsRetRecycleView.class);  // temporary
                                startActivity(intent);
                                finish();



                            } else {

                                alertbox(resultdescription,getString(R.string.transaction_result), TransactionHistoryRetailer.this);

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
}
