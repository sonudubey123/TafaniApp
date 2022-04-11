package com.sunmi.printerhelper.nextgen_sharique.changeMpin;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import org.json.JSONObject;

import java.util.Locale;


import static com.sunmi.printerhelper.nextgen_sharique.voly_url.Url.base_url;
import static com.sunmi.printerhelper.nextgen_sharique.voly_url.Url.login_dis_changeMpin_apinname;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import tafani.sunmi.printer.R;
import com.sunmi.printerhelper.local_set.LocalSetLanguage;
import com.sunmi.printerhelper.nextgen_sharique.application_sharepreference.MyApplication;
import com.sunmi.printerhelper.nextgen_sharique.interf.InterHttpServerResponse;
import com.sunmi.printerhelper.nextgen_sharique.loading_internet.CommonUtility;
import com.sunmi.printerhelper.nextgen_sharique.voly_url.Md5;
import com.sunmi.printerhelper.nextgen_sharique.voly_url.VollyRequestResponse;
import com.sunmi.printerhelper.utils.BluetoothUtil;
import com.sunmi.printerhelper.utils.SunmiPrintHelper;


public class ChangeMpinDistributor extends AppCompatActivity implements View.OnClickListener, InterHttpServerResponse {


    private Dialog dialog;

    Button imageButton1;
    EditText et_oldPin,et_newPin,et_confirmPin;
    String oldPinStr="",newPinStr="",confirmPinStr="";
    MyApplication myApplication;

    String languageToUse="";

    String transactionStatus="",transid="",transactionDate="",transactionTime="";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            myApplication = (MyApplication) getApplicationContext();
            languageToUse=myApplication.getmSharedPreferences().getString("languageToUse","");

            if (languageToUse.trim().length() == 0) {

                languageToUse = "ar";
                LocalSetLanguage.LocalSet(languageToUse, ChangeMpinDistributor.this);
            }
            else
            {
                LocalSetLanguage.LocalSet(languageToUse,ChangeMpinDistributor.this);
            }

            setContentView(R.layout.change_mpin_dis);


            et_oldPin = (EditText) findViewById(R.id.et_oldPin);
            et_newPin = (EditText) findViewById(R.id.et_newPin);
            et_confirmPin = (EditText) findViewById(R.id.et_confirmPin);

            imageButton1 = (Button) findViewById(R.id.imageButton1);
            imageButton1.setOnClickListener(this);



            if(languageToUse.equalsIgnoreCase("ar"))
            {
              //  imageButton1.setBackgroundResource(R.drawable.conform);
                et_oldPin.setGravity(Gravity.RIGHT);
                et_newPin.setGravity(Gravity.RIGHT);
                et_confirmPin.setGravity(Gravity.RIGHT);

            }
            else
            {
               // imageButton1.setBackgroundResource(R.drawable.confirmbutton);
                et_oldPin.setGravity(Gravity.LEFT);
                et_newPin.setGravity(Gravity.LEFT);
                et_confirmPin.setGravity(Gravity.LEFT);

            }




        }
        catch (Exception e)
        {
            Toast.makeText(ChangeMpinDistributor.this,e.toString(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }





    }


    protected void alertbox(String mymessage,String heading, Context context,String transid)
    {

        TextView textresponse,textheading;
        //LayoutInflater layoutInflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Drawable d = new ColorDrawable(R.color.transparent);

        dialog = new Dialog(context);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        //dialog.getWindow().setBackgroundDrawable(d);
        dialog.setContentView(R.layout.dialogbox);
        textheading=(TextView)((dialog)).findViewById(R.id.res_heading);
        textheading.setText(heading);

        textresponse=(TextView)((dialog)).findViewById(R.id.responsestring);


        try {


            if (mymessage.equalsIgnoreCase("Invalid PIN")) {

                textresponse.setText(getString(R.string.invalid_pin));
            }
            else if ((mymessage.equalsIgnoreCase("Subscriber Blocked"))) {
                textresponse.setText(getString(R.string.subscriber_blocked));

            }

            else if ((mymessage.equalsIgnoreCase("Your account is Blocked"))) {
                textresponse.setText("العميل محظور");  // Subscriber Blocked

                textresponse.setText(getString(R.string.subscriber_blocked));

            }
            else if ((mymessage.equalsIgnoreCase("Insufficient Fund"))) {

                textresponse.setText(getString(R.string.insuffience_fund));
            }

            else if ((mymessage.equalsIgnoreCase("Insufficient Wallet"))) {

                textresponse.setText(getString(R.string.insuffience_wallet));
            }
            else if ((mymessage.equalsIgnoreCase("Transaction Successful")))
            {
               textresponse.setText(getString(R.string.your_mpin_hasbeen_changed_your_transaction_id_is)+" "+transid);
            }

            else
                {
                textresponse.setText(mymessage);
               }
        }
        catch (Exception e)
        {
            textresponse.setText("نتيجة العملية");
            e.printStackTrace();
        }




        Button dialogbutton1=(Button) ((dialog)).findViewById(R.id.dialogbutton1);

        if(languageToUse.equalsIgnoreCase("ar"))
        {
            dialogbutton1.setBackgroundResource(R.drawable.conform);

        }
        else
        {
            dialogbutton1.setBackgroundResource(R.drawable.confirmbutton);
        }

        dialogbutton1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();

                ChangeMpinDistributor.this.finish();

            }
        });

        dialog.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.imageButton1:

                if (validation_Details()) {

                    if (CommonUtility.isOnline(ChangeMpinDistributor.this)) {

                        //  serverRequest(4);

                        try {

                            CommonUtility.showProgressDialog(ChangeMpinDistributor.this);



                               serverRequest_changempin_volly(101);

                              // serverRequest_login_retrofit(100);

                        } catch (Exception e) {

                            CommonUtility.hideProgressDialog(ChangeMpinDistributor.this);

                            Toast.makeText(ChangeMpinDistributor.this, e.toString(), Toast.LENGTH_SHORT).show();
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


    boolean validation_Details()
    {

        oldPinStr = et_oldPin.getText().toString().trim();
        newPinStr = et_newPin.getText().toString().trim();
        confirmPinStr = et_confirmPin.getText().toString().trim();

        if(oldPinStr.isEmpty()) {


            Toast.makeText(ChangeMpinDistributor.this,getString(R.string.plz_enter_old_pin), Toast.LENGTH_SHORT).show();


            return false;
        }

        else if(oldPinStr.length() < 3) {

            Toast.makeText(ChangeMpinDistributor.this,getString(R.string.plz_enter_old_pin), Toast.LENGTH_SHORT).show();


            return false;
        }

        else if(newPinStr.isEmpty()) {

            Toast.makeText(ChangeMpinDistributor.this,getString(R.string.plz_enter_new_pin), Toast.LENGTH_SHORT).show();


            return false;
        }

        else if(newPinStr.length() < 3) {


            Toast.makeText(ChangeMpinDistributor.this,getString(R.string.plz_enter_new_pin), Toast.LENGTH_SHORT).show();


            return false;
        }

        else if(confirmPinStr.isEmpty()) {

            Toast.makeText(ChangeMpinDistributor.this,getString(R.string.plz_confirm_new_pin), Toast.LENGTH_SHORT).show();


            return false;
        }

        else if(confirmPinStr.length() < 3) {

            Toast.makeText(ChangeMpinDistributor.this,getString(R.string.plz_confirm_new_pin), Toast.LENGTH_SHORT).show();


            return false;
        }

        else if(!newPinStr.equalsIgnoreCase(confirmPinStr)) {

            Toast.makeText(ChangeMpinDistributor.this,getString(R.string.new_coinfirm_should_be_same), Toast.LENGTH_SHORT).show();


            return false;
        }

        return true;
    }



    private void serverRequest_changempin_volly(int requestNo) {

/*        {
            "apiname": "CHANGEPIN",
                "request": {
            "agentcode": "0982650605",
                    "pin": "E24B7D1BCA5464639ECE18EBF63ABA2C",
                    "source": "0982650605",
                    "newpin": "E24B7D1BCA5464639ECE18EBF63ABA2C",
                    "vendorcode":"TAFANI",
                    "clienttype": "GPRS",
                    "requestcts":"2021-12-08 15:40:46"
        }
        }*/

        try {

            JSONObject json_main = new JSONObject();

            json_main.put("apiname", "CHANGEPIN");

            JSONObject jsonObject_request = new JSONObject();
            jsonObject_request.put("agentcode",MyApplication.getSaveString("mobileNoString", ChangeMpinDistributor.this));
            jsonObject_request.put("posserialno",MyApplication.getSN());
            String key_old_pin = Md5.getMd5Hash(MyApplication.getSaveString("mobileNoString", ChangeMpinDistributor.this)+oldPinStr).toUpperCase(Locale.ENGLISH);
            String key_new_pin = Md5.getMd5Hash(MyApplication.getSaveString("mobileNoString", ChangeMpinDistributor.this)+newPinStr).toUpperCase(Locale.ENGLISH);

            jsonObject_request.put("pin",key_old_pin);
            jsonObject_request.put("newpin",key_new_pin);
            jsonObject_request.put("source",MyApplication.getSaveString("mobileNoString", ChangeMpinDistributor.this));
            jsonObject_request.put("vendorcode","TAFANI");
            jsonObject_request.put("clienttype","POS");
            jsonObject_request.put("requestcts","2021-12-08 15:40:46");

            json_main.put("request", jsonObject_request);

            new VollyRequestResponse(ChangeMpinDistributor.this, ChangeMpinDistributor.this, requestNo, base_url+login_dis_changeMpin_apinname, json_main.toString());

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            CommonUtility.hideProgressDialog(ChangeMpinDistributor.this);

            e.printStackTrace();
        }

    }




    private void showAlertDialog(String msg, final int requestId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ChangeMpinDistributor.this);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(ChangeMpinDistributor.this);
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

        CommonUtility.hideProgressDialog(ChangeMpinDistributor.this);

        try {


            if (serverResponse.toString().contains("Time Out")) {
                showAlertDialog_sh("Time Out");
            } else if (serverResponse.toString().contains("Please try again later")) {
                showAlertDialog_sh("Please try again later");
            }



            else {

                if (requestNo == 101) {

                //    serverResponse = new JSONObject("{ \"apiname\": \"CHANGEPIN\", \"response\": { \"responsects\": \"12/17/2021 02:38:12 PM\", \"agentcode\": \"0982650605\", \"vendorcode\": \"TAFANI\", \"transid\": \"45629590\", \"resultcode\": \"0\", \"resultdescription\": \"Transaction Successful\", \"clienttype\": \"GPRS\", \"walletbalance\": \"56.0\", \"source\": \"0982650605\" } }");

                    if (serverResponse.has("response")) {

                        JSONObject jsonObject_response = serverResponse.getJSONObject("response");

                        if (jsonObject_response.has("resultcode")) {


                            String resultcode = jsonObject_response.getString("resultcode");
                            String resultdescription = jsonObject_response.getString("resultdescription");

                            if (resultcode.equalsIgnoreCase("0")) {

                                transid = jsonObject_response.getString("transid");
                                transactionStatus = jsonObject_response.getString("resultdescription");

                                // Toast.makeText(this, amount, Toast.LENGTH_SHORT).show();

                                String transactionDateTemp = jsonObject_response.getString("responsects");
                                String[] transactionDate_array=transactionDateTemp.split(" ");
                                transactionDate=transactionDate_array[0];
                                transactionTime=transactionDate_array[1]+" "+ transactionDate_array[2];;


                                successResponse();

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

    protected void successResponse()
    {

        TextView textresponse,textheading;
        //LayoutInflater layoutInflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Drawable d = new ColorDrawable(R.color.transparent);

        dialog = new Dialog(ChangeMpinDistributor.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        //dialog.getWindow().setBackgroundDrawable(d);
        dialog.setContentView(R.layout.dialogbox);
        textheading=(TextView)((dialog)).findViewById(R.id.res_heading);
        textheading.setText(getString(R.string.transaction_result));

        textresponse=(TextView)((dialog)).findViewById(R.id.responsestring);


        try {


            if (transactionStatus.equalsIgnoreCase("Invalid PIN")) {

                textresponse.setText(getString(R.string.invalid_pin));
            }
            else if ((transactionStatus.equalsIgnoreCase("Subscriber Blocked"))) {
                textresponse.setText(getString(R.string.subscriber_blocked));

            }

            else if ((transactionStatus.equalsIgnoreCase("Your account is Blocked"))) {
                textresponse.setText("العميل محظور");  // Subscriber Blocked

                textresponse.setText(getString(R.string.subscriber_blocked));

            }
            else if ((transactionStatus.equalsIgnoreCase("Insufficient Fund"))) {

                textresponse.setText(getString(R.string.insuffience_fund));
            }

            else if ((transactionStatus.equalsIgnoreCase("Insufficient Wallet"))) {

                textresponse.setText(getString(R.string.insuffience_wallet));
            }
            else if ((transactionStatus.equalsIgnoreCase("Transaction Successful")))
            {
                textresponse.setText(getString(R.string.your_mpin_hasbeen_changed_your_transaction_id_is)+" "+transid);
            }

            else
            {
                textresponse.setText(transactionStatus);
            }
        }
        catch (Exception e)
        {
            textresponse.setText("نتيجة العملية");
            e.printStackTrace();
        }




        Button dialogbutton1=(Button) ((dialog)).findViewById(R.id.dialogbutton1);

        if(languageToUse.equalsIgnoreCase("ar"))
        {
            //  dialogbutton1.setBackgroundResource(R.drawable.conform);

        }
        else
        {
            //  dialogbutton1.setBackgroundResource(R.drawable.confirmbutton);
        }



        dialogbutton1.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                dialog.dismiss();

                ChangeMpinDistributor.this.finish();

            }
        });

        Button printButton=(Button) ((dialog)).findViewById(R.id.printButton);
        printButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                print_pos_data();

            }
        });

        dialog.show();
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

            SunmiPrintHelper.getInstance().printText_nextgen("\n\n"+ getString(R.string.distributor_capital)+  "  " + "\n", 30, true, isUnderLine, testFont,1);
            SunmiPrintHelper.getInstance().printText_nextgen("---------------" + "\n", 50, isBold, isUnderLine, testFont,0);
            SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.transaction_type)+" : "+getString(R.string.change_mpin) + "\n", 20, isBold, isUnderLine, testFont,0);
            SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.terminal_id)+" : "+transid + "\n", 20, isBold, isUnderLine, testFont,0);
            SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.transaction_id)+" : "+transid + "\n", 20, isBold, isUnderLine, testFont,0);
            SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.transaction_date)+" : "+transactionDate + "\n", 20, isBold, isUnderLine, testFont,0);
            SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.transaction_time)+" : "+transactionTime + "\n", 20, isBold, isUnderLine, testFont,0);
            SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.your_mpin_hasbeen_changed_your_transaction_id_is), 20, isBold, isUnderLine, testFont,0);
            SunmiPrintHelper.getInstance().printText_nextgen("\n" + "---------------", 50, isBold, isUnderLine, testFont,0);

            SunmiPrintHelper.getInstance().feedPaper();
        }

        else {
            // SunmiPrintHelper.getInstance().printText(getString(R.string.account_balance) + " : " + amountFromServer + " LYD", 30, true, isUnderLine, testFont);
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

        finish();

    }
}
