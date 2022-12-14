package com.sunmi.tafani_printerhelper.nextgen_sharique.login;


import static com.sunmi.tafani_printerhelper.nextgen_sharique.voly_url.Url.base_url;
import static com.sunmi.tafani_printerhelper.nextgen_sharique.voly_url.Url.login_ret_changeMpin_apinname;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sunmi.tafani_printerhelper.local_set.LocalSetLanguage;
import com.sunmi.tafani_printerhelper.nextgen_sharique.application_sharepreference.MyApplication;
import com.sunmi.tafani_printerhelper.nextgen_sharique.interf.InterHttpServerResponse;
import com.sunmi.tafani_printerhelper.nextgen_sharique.loading_internet.CommonUtility;
import com.sunmi.tafani_printerhelper.nextgen_sharique.voly_url.Md5;
import com.sunmi.tafani_printerhelper.nextgen_sharique.voly_url.VollyRequestResponse;
import com.sunmi.tafani_printerhelper.utils.BluetoothUtil;
import com.sunmi.tafani_printerhelper.utils.SunmiPrintHelper;

import org.json.JSONObject;

import java.util.Locale;

import com.sunmi.tafani_printerhelper.R;

/**
 * Created by Estel_WP on 28-Mar-19.
 */

public class ForgotOfflineMpinRetailer extends AppCompatActivity implements View.OnClickListener, InterHttpServerResponse {


    private boolean isBold, isUnderLine;
    private String testFont;

    String transactionStatus="",transid="",transactionDate="",transactionTime="";


    private Dialog dialog;

    Button imageButton1;
    EditText et_oldPin,et_newPin,et_confirmPin,edittext_ans;
    String oldPinStr="",newPinStr="",confirmPinStr="";
    MyApplication myApplication;

    String languageToUse="";
    Spinner spinner_seq_offline;
    String offlinePinString="",mpinString,ansString;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            myApplication = (MyApplication) getApplicationContext();

            languageToUse=myApplication.getmSharedPreferences().getString("languageToUse","");
            if (languageToUse.trim().length() == 0) {

                languageToUse = "ar";
                LocalSetLanguage.LocalSet(languageToUse, ForgotOfflineMpinRetailer.this);
            }
            else
            {
                LocalSetLanguage.LocalSet(languageToUse, ForgotOfflineMpinRetailer.this);
            }



            setContentView(R.layout.forgot_offline_mpin_ret);


            et_oldPin = (EditText) findViewById(R.id.et_oldPin);
            et_newPin = (EditText) findViewById(R.id.et_newPin);
            et_confirmPin = (EditText) findViewById(R.id.et_confirmPin);
            spinner_seq_offline = (Spinner) findViewById(R.id.spinner_seq_offline);
            edittext_ans = (EditText) findViewById(R.id.edittext_ans);
            imageButton1 = (Button) findViewById(R.id.imageButton1);
            imageButton1.setOnClickListener(this);



            if(languageToUse.equalsIgnoreCase("ar"))
            {
              //  imageButton1.setBackgroundResource(R.drawable.conform);
                et_oldPin.setGravity(Gravity.RIGHT);
                et_newPin.setGravity(Gravity.RIGHT);
                et_confirmPin.setGravity(Gravity.RIGHT);
                edittext_ans.setGravity(Gravity.RIGHT);

            }
            else
            {
               // imageButton1.setBackgroundResource(R.drawable.confirmbutton);
                et_oldPin.setGravity(Gravity.LEFT);
                et_newPin.setGravity(Gravity.LEFT);
                et_confirmPin.setGravity(Gravity.LEFT);
                edittext_ans.setGravity(Gravity.LEFT);

            }




        }
        catch (Exception e)
        {
            Toast.makeText(ForgotOfflineMpinRetailer.this,e.toString(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }





    }


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


    protected void successResponse()
    {

        TextView textresponse,textheading;
        //LayoutInflater layoutInflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Drawable d = new ColorDrawable(R.color.transparent);

        dialog = new Dialog(ForgotOfflineMpinRetailer.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        //dialog.getWindow().setBackgroundDrawable(d);
        dialog.setContentView(R.layout.dialogbox_without_print);
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
                textresponse.setText("???????????? ??????????");  // Subscriber Blocked

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
            textresponse.setText("?????????? ??????????????");
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

                ForgotOfflineMpinRetailer.this.finish();

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


    protected void successResponseOffline()
    {

        TextView textresponse,textheading;
        //LayoutInflater layoutInflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Drawable d = new ColorDrawable(R.color.transparent);

        dialog = new Dialog(ForgotOfflineMpinRetailer.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        //dialog.getWindow().setBackgroundDrawable(d);
        dialog.setContentView(R.layout.dialogbox_without_print);
        textheading=(TextView)((dialog)).findViewById(R.id.res_heading);
        textheading.setText(getString(R.string.transaction_result));

        textresponse=(TextView)((dialog)).findViewById(R.id.responsestring);


        try {

            textresponse.setText(getString(R.string.your_offline_pin_is_successfully_changed)+" "+transid);

        }
        catch (Exception e)
        {
            textresponse.setText("?????????? ??????????????");
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

                ForgotOfflineMpinRetailer.this.finish();

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.imageButton1:

                if (validation_Details()) {
                    MyApplication.saveString("offlinePinString",offlinePinString,ForgotOfflineMpinRetailer.this);
                    successResponseOffline();
                   /* if (CommonUtility.isOnline(ChangeOfflineMpinRetailer.this)) {

                        //  serverRequest(4);

                        try {

                            CommonUtility.showProgressDialog(ChangeOfflineMpinRetailer.this);



                               serverRequest_changempin_volly(101);

                              // serverRequest_login_retrofit(100);

                        } catch (Exception e) {

                            CommonUtility.hideProgressDialog(ChangeOfflineMpinRetailer.this);

                            Toast.makeText(ChangeOfflineMpinRetailer.this, e.toString(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                        // callApiLogin(100);


                    } else {
                        Toast.makeText(this, R.string.please_check_connection, Toast.LENGTH_SHORT).show();
                    }*/
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
       // String offlinePinString = MyApplication.getSaveString("offlinePinString", ForgotOfflineMpinRetailer.this);
        oldPinStr = et_oldPin.getText().toString().trim();
        newPinStr = et_newPin.getText().toString().trim();
        confirmPinStr = et_confirmPin.getText().toString().trim();
        ansString=edittext_ans.getText().toString().trim();
        offlinePinString = et_confirmPin.getText().toString().trim();



        String answer= MyApplication.getSaveString("ANSWER",ForgotOfflineMpinRetailer.this);
        String question=MyApplication.getSaveString("QUESTION", ForgotOfflineMpinRetailer.this);

        if(languageToUse.equalsIgnoreCase("ar"))
        {
         if(question.equalsIgnoreCase("What was the model of your first car?")){
             question="???? ???? ?????? ???????????? ?????? ?????????? ???????????????? ??";
         }
            if(question.equalsIgnoreCase("In what city were you born?")){
                question="?????? ?????????? ???????? ??";
            }

            if(question.equalsIgnoreCase("What is the name of your first school?")){
                question="???? ???? ?????? ?????? ?????????? ???????? ?????? ??";
            }

        }
        else
        {
            // imageButton1.setBackgroundResource(R.drawable.confirmbutton);


        }


        if(spinner_seq_offline.getSelectedItemPosition()==0)
        {
            Toast.makeText(ForgotOfflineMpinRetailer.this, R.string.choose_Security_question, Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!spinner_seq_offline.getSelectedItem().toString().equalsIgnoreCase(question))
        {
            Toast.makeText(ForgotOfflineMpinRetailer.this, R.string.choose_correct_Security_question, Toast.LENGTH_SHORT).show();
            return false;
        }
        if (ansString.isEmpty()) {
            Toast.makeText(ForgotOfflineMpinRetailer.this, getResources().getString(R.string.enter_ans), Toast.LENGTH_SHORT).show();
            return false;
        }
        if(!ansString.equalsIgnoreCase(answer))
        {
            Toast.makeText(ForgotOfflineMpinRetailer.this, R.string.your_answer_is_incorrect_please_enter_again, Toast.LENGTH_SHORT).show();
            return false;
        }


       /* if(oldPinStr.isEmpty()) {


            Toast.makeText(ForgotOfflineMpinRetailer.this,getString(R.string.enter_old_5_digit_offline_pin), Toast.LENGTH_SHORT).show();


            return false;
        }

        else if(oldPinStr.length() >5) {

            Toast.makeText(ForgotOfflineMpinRetailer.this,getString(R.string.enter_old_5_digit_offline_pin), Toast.LENGTH_SHORT).show();


            return false;
        }*/

        else if(newPinStr.isEmpty()) {

            Toast.makeText(ForgotOfflineMpinRetailer.this,getString(R.string.enter_new_5_digit_new_offline_pin), Toast.LENGTH_SHORT).show();


            return false;
        }

        else if(newPinStr.length() > 5) {


            Toast.makeText(ForgotOfflineMpinRetailer.this,getString(R.string.enter_new_5_digit_new_offline_pin), Toast.LENGTH_SHORT).show();


            return false;
        }

        else if(confirmPinStr.isEmpty()) {

            Toast.makeText(ForgotOfflineMpinRetailer.this,getString(R.string.confirm_5_digit_new_offline_pin), Toast.LENGTH_SHORT).show();


            return false;
        }

        else if(confirmPinStr.length() > 5) {

            Toast.makeText(ForgotOfflineMpinRetailer.this,getString(R.string.confirm_5_digit_new_offline_pin), Toast.LENGTH_SHORT).show();


            return false;
        }

        else if(!newPinStr.equalsIgnoreCase(confirmPinStr)) {

            Toast.makeText(ForgotOfflineMpinRetailer.this,getString(R.string.new_coinfirm_should_be_same), Toast.LENGTH_SHORT).show();


            return false;
        }


       /* else if(!oldPinStr.equalsIgnoreCase(offlinePinString)) {

            Toast.makeText(ForgotOfflineMpinRetailer.this,getString(R.string.old_offline_pin_is_not_correct_please_enter_correct_offline_old_pin), Toast.LENGTH_SHORT).show();


            return false;
        }
*/
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
            jsonObject_request.put("agentcode",MyApplication.getSaveString("mobileNoString", ForgotOfflineMpinRetailer.this));
            jsonObject_request.put("posserialno",MyApplication.getSN());
            String key_old_pin = Md5.getMd5Hash(MyApplication.getSaveString("mobileNoString", ForgotOfflineMpinRetailer.this)+oldPinStr).toUpperCase(Locale.ENGLISH);
            String key_new_pin = Md5.getMd5Hash(MyApplication.getSaveString("mobileNoString", ForgotOfflineMpinRetailer.this)+newPinStr).toUpperCase(Locale.ENGLISH);

            jsonObject_request.put("pin",key_old_pin);
            jsonObject_request.put("newpin",key_new_pin);
            jsonObject_request.put("source",MyApplication.getSaveString("mobileNoString", ForgotOfflineMpinRetailer.this));
            jsonObject_request.put("vendorcode","TAFANI");
            jsonObject_request.put("clienttype","POS");
            jsonObject_request.put("requestcts","2021-12-08 15:40:46");

            json_main.put("request", jsonObject_request);

            new VollyRequestResponse(ForgotOfflineMpinRetailer.this, ForgotOfflineMpinRetailer.this, requestNo, base_url+login_ret_changeMpin_apinname, json_main.toString());

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            CommonUtility.hideProgressDialog(ForgotOfflineMpinRetailer.this);

            e.printStackTrace();
        }

    }




    private void showAlertDialog(String msg, final int requestId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ForgotOfflineMpinRetailer.this);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(ForgotOfflineMpinRetailer.this);
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

        CommonUtility.hideProgressDialog(ForgotOfflineMpinRetailer.this);

        try {


            if (serverResponse.toString().contains("Time Out")) {
                showAlertDialog_sh("Time Out");
            } else if (serverResponse.toString().contains("Please try again later")) {
                showAlertDialog_sh("Please try again later");
            }



            else {

                if (requestNo == 101) {

                  // serverResponse = new JSONObject("{ \"apiname\": \"CHANGEPIN\", \"response\": { \"responsects\": \"12/17/2021 02:38:12 PM\", \"agentcode\": \"0982650605\", \"vendorcode\": \"TAFANI\", \"transid\": \"45629590\", \"resultcode\": \"0\", \"resultdescription\": \"Transaction Successful\", \"clienttype\": \"GPRS\", \"walletbalance\": \"56.0\", \"source\": \"0982650605\" } }");

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
