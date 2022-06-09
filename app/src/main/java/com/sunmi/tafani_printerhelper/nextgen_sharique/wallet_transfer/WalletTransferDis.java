package com.sunmi.tafani_printerhelper.nextgen_sharique.wallet_transfer;

import static com.sunmi.tafani_printerhelper.nextgen_sharique.voly_url.Url.base_url;
import static com.sunmi.tafani_printerhelper.nextgen_sharique.voly_url.Url.login_dis_walletTransfer_apinname;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sunmi.tafani_printerhelper.R;
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

public class WalletTransferDis extends AppCompatActivity implements View.OnClickListener, InterHttpServerResponse {


    Button imagebutton_confirm;
    EditText edittext_customer_code,edittext_mpin,edittext_confirm_customer_code,edittext_amount;
    String customerCodeString="",mpinString="", confirmcustomerCodeString="",amountString="";
    AlertDialog dialog;

    String dateOfBirthString;
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
                LocalSetLanguage.LocalSet(languageToUse, WalletTransferDis.this);
            }
            else
            {
                LocalSetLanguage.LocalSet(languageToUse,WalletTransferDis.this);
            }


            setContentView(R.layout.wallet_tranfer_dis);


            edittext_customer_code = (EditText) findViewById(R.id.edittext_customer_code);
            edittext_confirm_customer_code = (EditText) findViewById(R.id.edittext_confirm_customer_code);
            edittext_amount = (EditText) findViewById(R.id.edittext_amount);
            
            edittext_mpin = (EditText) findViewById(R.id.edittext_mpin);
            imagebutton_confirm = (Button) findViewById(R.id.imagebutton_confirm);
            imagebutton_confirm.setOnClickListener(this);

            if(languageToUse.equalsIgnoreCase("ar"))
            {
                edittext_customer_code.setGravity(Gravity.RIGHT);
                edittext_confirm_customer_code.setGravity(Gravity.RIGHT);
                edittext_amount.setGravity(Gravity.RIGHT);
                edittext_mpin.setGravity(Gravity.RIGHT);
            }
            else
            {
                edittext_customer_code.setGravity(Gravity.LEFT);
                edittext_confirm_customer_code.setGravity(Gravity.LEFT);
                edittext_amount.setGravity(Gravity.LEFT);
                edittext_mpin.setGravity(Gravity.LEFT);
            }




        }
        catch (Exception e)
        {
            Toast.makeText(WalletTransferDis.this,e.toString(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }





    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imagebutton_confirm:
                if (checkValidation()) {

                    if (CommonUtility.isOnline(WalletTransferDis.this)) {

                        //  serverRequest(4);

                        try {




                            alertboxForApproval();



                              // serverRequest_login_retrofit(100);

                        } catch (Exception e) {

                            CommonUtility.hideProgressDialog(WalletTransferDis.this);

                            Toast.makeText(WalletTransferDis.this, e.toString(), Toast.LENGTH_SHORT).show();
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

        customerCodeString = edittext_customer_code.getText().toString().trim();
        confirmcustomerCodeString = edittext_confirm_customer_code.getText().toString().trim();
        amountString = edittext_amount.getText().toString().trim();
        mpinString = edittext_mpin.getText().toString().trim();


        if (customerCodeString.isEmpty()) {

            Toast.makeText(WalletTransferDis.this, getResources().getString(R.string.enterdestinationtext), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (customerCodeString.length()< 10) {
            Toast.makeText(WalletTransferDis.this, getResources().getString(R.string.enterdestinationtext), Toast.LENGTH_SHORT).show();
            return false;
        }

            if (confirmcustomerCodeString.isEmpty()) {

                Toast.makeText(WalletTransferDis.this, getResources().getString(R.string.confirmdestinationtext), Toast.LENGTH_SHORT).show();
                return false;
            }

            if (!customerCodeString.equalsIgnoreCase(confirmcustomerCodeString)) {
                Toast.makeText(WalletTransferDis.this, getResources().getString(R.string.customer_agentCode_confirm_agentCode_should_be_same), Toast.LENGTH_SHORT).show();
                return false;
            }

            if (confirmcustomerCodeString.length()< 10) {
                Toast.makeText(WalletTransferDis.this, getResources().getString(R.string.confirmdestinationtext), Toast.LENGTH_SHORT).show();
                return false;
            }
            if (amountString.equalsIgnoreCase("")) {
                Toast.makeText(WalletTransferDis.this, getResources().getString(R.string.enter_amount), Toast.LENGTH_SHORT).show();

                return false;
            }


            if (amountString.length() < 1) {
                Toast.makeText(WalletTransferDis.this, getResources().getString(R.string.enter_amount), Toast.LENGTH_SHORT).show();

                return false;
            }

            if (mpinString.length() <3) {

                Toast.makeText(WalletTransferDis.this, getResources().getString(R.string.enter_mpin), Toast.LENGTH_SHORT).show();

                return false;
            }

        return true;
    }

    private Dialog dilogue_wallet;

    protected void alertboxForApproval() {

        dilogue_wallet = new Dialog(WalletTransferDis.this);
        dilogue_wallet.getWindow().setBackgroundDrawable(new ColorDrawable(0));
		/*Button approve=(Button) findViewById(R.id.approve_button);
		Button reject=(Button) findViewById(R.id. reject_button);*/
        // dialog.getWindow().setBackgroundDrawable(d);
        dilogue_wallet.setContentView(R.layout.wallet_transfer_confirmbox);

        TextView  header_mobileNumber = (TextView) ((dilogue_wallet)).findViewById(R.id.header_mobileNumber);
        header_mobileNumber.setText(customerCodeString +" = "+getString(R.string.agent_code));

        TextView  header_amount = (TextView) ((dilogue_wallet)).findViewById(R.id.header_amount);
        header_amount.setText(amountString +" = "+getString(R.string.amount));



        dilogue_wallet.findViewById(R.id.approve_button).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                dilogue_wallet.dismiss();


                CommonUtility.showProgressDialog(WalletTransferDis.this);

                serverRequest_walletTrasfer_volly(100);


                //finish();
            }
        });

        dilogue_wallet.findViewById(R.id.reject_button).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
               /* Intent myintent = new Intent(WalletTransferDis.this, MainActivityDis.class);
                dilogue_wallet.dismiss();
                finish();
                startActivity(myintent);*/
            }
        });

        dilogue_wallet.show();
    }


    private void serverRequest_walletTrasfer_volly(int requestNo) {


/*        {
            "apiname": "WALLETTRF",
                "request": {
            "agentcode": "0910889355",
                    "source": "0910889355",
                    "pin": "A8222102878F3B92E0C633D016F047D5",
                    "amount":"15.0",
                    "discount":"0.0",
                    "tax":"0.0",
                    "fee":"0.0",
                    "destination":"0981088935",
                    "vendorcode":"TAFANI",
                    "clienttype": "GPRS",
                    "requestcts":"12/08/2021 04:31:22 PM"
        }
        }*/

        try {

            JSONObject json_main = new JSONObject();


            json_main.put("apiname", "WALLETTRF");

            JSONObject jsonObject_request = new JSONObject();
            jsonObject_request.put("posserialno",MyApplication.getSN());
            jsonObject_request.put("agentcode",MyApplication.getSaveString("mobileNoString", WalletTransferDis.this));
            jsonObject_request.put("source",MyApplication.getSaveString("mobileNoString", WalletTransferDis.this));
            String key = Md5.getMd5Hash(MyApplication.getSaveString("mobileNoString", WalletTransferDis.this)+mpinString).toUpperCase(Locale.ENGLISH);
            jsonObject_request.put("pin", key);
            jsonObject_request.put("amount", amountString);
            jsonObject_request.put("discount", "0.0");
            jsonObject_request.put("tax", "0.0");
            jsonObject_request.put("fee", "0.0");
            jsonObject_request.put("destination", customerCodeString);
            jsonObject_request.put("vendorcode", "TAFANI");
            jsonObject_request.put("clienttype", "POS");
            jsonObject_request.put("requestcts", "12/08/2021 04:15:15 PM");

            json_main.put("request", jsonObject_request);

            new VollyRequestResponse(WalletTransferDis.this, WalletTransferDis.this, requestNo, base_url+login_dis_walletTransfer_apinname, json_main.toString());

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            CommonUtility.hideProgressDialog(WalletTransferDis.this);

            e.printStackTrace();
        }

    }




    private void showAlertDialog(String msg, final int requestId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(WalletTransferDis.this);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(WalletTransferDis.this);
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

        CommonUtility.hideProgressDialog(WalletTransferDis.this);

        try {


            if (serverResponse.toString().contains("Time Out")) {
                showAlertDialog_sh("Time Out");
            } else if (serverResponse.toString().contains("Please try again later")) {
                showAlertDialog_sh("Please try again later");
            }



            else {

                if (requestNo == 100) {


                //  serverResponse = new JSONObject ("{\"apiname\":\"WALLETTRF\",\"response\":{\"requestcts\":\"12/08/2021 04:15:15 PM\",\"responsects\":\"01/02/2022 09:40:34 PM\",\"amount\":\"1500\",\"agentcode\":\"0910889355\",\"destination\":\"0981088935\",\"vendorcode\":\"TAFANI\",\"transid\":\"45630149\",\"resultcode\":\"0\",\"resultdescription\":\"Transaction Successful\",\"clienttype\":\"GPRS\",\"source\":\"0910889355\",\"email\":\"\"}}");

                    if (serverResponse.has("response")) {

                        JSONObject jsonObject_response = serverResponse.getJSONObject("response");

                        if (jsonObject_response.has("resultcode")) {


                            String resultcode = jsonObject_response.getString("resultcode");
                           String  resultdescription = jsonObject_response.getString("resultdescription");

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
 Dialog dialogtemp;
    protected void successResponse()
    {

        TextView textresponse,textheading;
        //LayoutInflater layoutInflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Drawable d = new ColorDrawable(R.color.transparent);

        dialogtemp = new Dialog(WalletTransferDis.this);
        dialogtemp.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        //dialogtemp.getWindow().setBackgroundDrawable(d);
        dialogtemp.setContentView(R.layout.dialogbox);
        textheading=(TextView)((dialogtemp)).findViewById(R.id.res_heading);
        textheading.setText(getString(R.string.transaction_result));

        textresponse=(TextView)((dialogtemp)).findViewById(R.id.responsestring);


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
                textresponse.setText(getString(R.string.wallet_trabsfer_sucess)+" \n"+getString(R.string.transaction_id) +" : "+transid);
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




        Button dialogbutton1=(Button) ((dialogtemp)).findViewById(R.id.dialogbutton1);

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
                dialogtemp.dismiss();

                WalletTransferDis.this.finish();

            }
        });

        Button printButton=(Button) ((dialogtemp)).findViewById(R.id.printButton);
        printButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                print_pos_data();

            }
        });

        dialogtemp.show();
    }

    private boolean isBold, isUnderLine;
    private String testFont;


    protected void print_pos_data() {

        if (!BluetoothUtil.isBlueToothPrinter) {


            if(languageToUse.equalsIgnoreCase("ar"))
            {
                SunmiPrintHelper.getInstance().printText(" "+ getString(R.string.distributor_capital)+  "         " + "\n", 30, true, isUnderLine, testFont);
                SunmiPrintHelper.getInstance().printText("---------------" + "\n", 50, isBold, isUnderLine, testFont);
                SunmiPrintHelper.getInstance().printText(getString(R.string.transaction_type)+"    :        "   +getString(R.string.wallet_trabsfer) + "\n", 20, isBold, isUnderLine, testFont);

                SunmiPrintHelper.getInstance().printText(getString(R.string.terminal_id)+"          :    "   +transid + "\n", 20, isBold, isUnderLine, testFont);
                SunmiPrintHelper.getInstance().printText(getString(R.string.transaction_id)+"       :        "   +transid + "\n", 20, isBold, isUnderLine, testFont);
                SunmiPrintHelper.getInstance().printText(getString(R.string.transaction_date)+"     :        "   +transactionDate + "\n", 20, isBold, isUnderLine, testFont);
                SunmiPrintHelper.getInstance().printText(getString(R.string.transaction_time)+"      :        "   +transactionTime + "\n\n", 20, isBold, isUnderLine, testFont);
                SunmiPrintHelper.getInstance().printText(getString(R.string.wallet_trabsfer_sucess) + "   :      ", 20, isBold, isUnderLine, testFont);

                SunmiPrintHelper.getInstance().printText("\n" + "---------------", 50, isBold, isUnderLine, testFont);

                SunmiPrintHelper.getInstance().feedPaper();

            }
            else
            {
                SunmiPrintHelper.getInstance().printText("         "+ getString(R.string.distributor_capital)+  "  " + "\n", 30, true, isUnderLine, testFont);
                SunmiPrintHelper.getInstance().printText("---------------" + "\n", 50, isBold, isUnderLine, testFont);
                SunmiPrintHelper.getInstance().printText(getString(R.string.transaction_type)+"    :   "   +getString(R.string.wallet_trabsfer) + "\n", 20, isBold, isUnderLine, testFont);

                SunmiPrintHelper.getInstance().printText(getString(R.string.terminal_id)+"         :      "   +transid + "\n", 20, isBold, isUnderLine, testFont);
                SunmiPrintHelper.getInstance().printText(getString(R.string.transaction_id)+"      :      "   +transid + "\n", 20, isBold, isUnderLine, testFont);
                SunmiPrintHelper.getInstance().printText(getString(R.string.transaction_date)+"    :      "   +transactionDate + "\n", 20, isBold, isUnderLine, testFont);
                SunmiPrintHelper.getInstance().printText(getString(R.string.transaction_time)+"    :      "   +transactionTime + "\n\n", 20, isBold, isUnderLine, testFont);
                SunmiPrintHelper.getInstance().printText(getString(R.string.wallet_trabsfer_sucess) + "   :      ", 20, isBold, isUnderLine, testFont);

                SunmiPrintHelper.getInstance().printText("\n" + "---------------", 50, isBold, isUnderLine, testFont);

                SunmiPrintHelper.getInstance().feedPaper();

            }


        } else {
            // SunmiPrintHelper.getInstance().printText(getString(R.string.account_balance) + " : " + amountFromServer + " LYD", 30, true, isUnderLine, testFont);
        }

    }


    Dialog dialogue_temp;

    protected void alertbox(String response ,String heading, Context context) {

        TextView textresponse, textheading;
        //LayoutInflater layoutInflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Drawable d = new ColorDrawable(R.color.transparent);

        dialogue_temp = new Dialog(context);
        dialogue_temp.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        //dialog.getWindow().setBackgroundDrawable(d);
        dialogue_temp.setContentView(R.layout.dialogbox);
        textheading = (TextView) ((dialogue_temp)).findViewById(R.id.res_heading);
        textheading.setText(heading);

        textresponse = (TextView) ((dialogue_temp)).findViewById(R.id.responsestring);

        textresponse.setText(getString(R.string.wallet_trabsfer) +" : "+response);


        Button dialogbutton1=(Button) ((dialogue_temp)).findViewById(R.id.dialogbutton1);

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
                dialogue_temp.dismiss();

                WalletTransferDis.this.finish();

            }
        });

        dialogue_temp.show();
    }

}
