package com.sunmi.printerhelper.nextgen_sharique.account_balance;


import static com.sunmi.printerhelper.nextgen_sharique.voly_url.Url.base_url;
import static com.sunmi.printerhelper.nextgen_sharique.voly_url.Url.login_ret_balance_apinname;

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


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



import org.json.JSONObject;

import java.util.Locale;


public class AccountBalanceRetailer extends AppCompatActivity implements View.OnClickListener, InterHttpServerResponse {


    private Dialog dialog;

    private boolean isBold, isUnderLine;
    private String testFont;


    String amountFromServer="",transactionStatus="",transid="",transactionDate="",transactionTime="";

    Button imageButton1;
    EditText edittext_mpin;
    String mpinString = "";

    String dateOfBirthString;
    MyApplication myApplication;

    String languageToUse = "";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            myApplication = (MyApplication) getApplicationContext();
            languageToUse = myApplication.getmSharedPreferences().getString("languageToUse", "");

            if (languageToUse.trim().length() == 0) {

                languageToUse = "ar";
                LocalSetLanguage.LocalSet(languageToUse, AccountBalanceRetailer.this);
            }
            else
            {
                LocalSetLanguage.LocalSet(languageToUse,AccountBalanceRetailer.this);
            }

            setContentView(R.layout.account_balance_retailer);


            edittext_mpin = (EditText) findViewById(R.id.edittext_mpin);
            imageButton1 = (Button) findViewById(R.id.imageButton1);
            imageButton1.setOnClickListener(this);


            if (languageToUse.equalsIgnoreCase("ar")) {
               // imageButton1.setBackgroundResource(R.drawable.conform);
              //  edittext_mpin.setGravity(Gravity.RIGHT);

            } else {
              //  imageButton1.setBackgroundResource(R.drawable.confirmbutton);
              //  edittext_mpin.setGravity(Gravity.LEFT);

            }


        } catch (Exception e) {
            Toast.makeText(AccountBalanceRetailer.this, e.toString(), Toast.LENGTH_LONG).show();
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
            SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.transaction_type)+" : "+getString(R.string.account_balance) + "\n", 20, isBold, isUnderLine, testFont,0);
            SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.terminal_id)+" : "+transid + "\n", 20, isBold, isUnderLine, testFont,0);
            SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.transaction_id)+" : "+transid + "\n", 20, isBold, isUnderLine, testFont,0);
            SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.transaction_date)+" : "+transactionDate + "\n", 20, isBold, isUnderLine, testFont,0);
            SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.transaction_time)+" : "+transactionTime + "\n", 20, isBold, isUnderLine, testFont,0);
            SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.account_balance) +" : "+ amountFromServer + " LYD", 20, isBold, isUnderLine, testFont,0);
            SunmiPrintHelper.getInstance().printText_nextgen("\n" + "---------------", 50, isBold, isUnderLine, testFont,0);

            SunmiPrintHelper.getInstance().feedPaper();


        } else {
           // SunmiPrintHelper.getInstance().printText(getString(R.string.account_balance) + " : " + amountFromServer + " LYD", 30, true, isUnderLine, testFont);
        }

    }

    protected void print_pos_data_demo_new() {

        if (!BluetoothUtil.isBlueToothPrinter) {


            Bitmap bitmap = null;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inTargetDensity = 200;
            options.inDensity = 200;

            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo, options);
            SunmiPrintHelper.getInstance().printBitmap_nextgen(bitmap,1);

            //   transactionDate = "17-12-2017 2017";
            //  transactionTime = "02:25:11 PM";



            if (languageToUse.equalsIgnoreCase("en")) {

                SunmiPrintHelper.getInstance().printText_nextgen("\n" + transactionDate, 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen("                " + transactionTime + "\n", 20, isBold, isUnderLine, testFont, 2);

                SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.terminalidId_print_colon), 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen("           " + MyApplication.getSaveString("terminalIdString", AccountBalanceRetailer.this) + "\n", 20, isBold, isUnderLine, testFont, 2);


                SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.retailer_print_colon), 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen("                   " + MyApplication.getSaveString("mobileNoString", AccountBalanceRetailer.this) + "\n", 20, isBold, isUnderLine, testFont, 2);

                SunmiPrintHelper.getInstance().printText_nextgen("---------------" + "\n", 50, isBold, isUnderLine, testFont, 0);

                SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.pin_number), 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen("           " + "100055500145101" + "\n", 20, isBold, isUnderLine, testFont, 2);


                SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.amount_print), 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen("                           " + 1.000 + "\n", 20, isBold, isUnderLine, testFont, 2);

                SunmiPrintHelper.getInstance().printText_nextgen("---------------" + "\n", 50, isBold, isUnderLine, testFont, 0);

                SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.activitionCode_print) + "\n", 20, isBold, isUnderLine, testFont, 0);

                SunmiPrintHelper.getInstance().printText_nextgen("     " + "1234 5678 34563" + "  " + "\n", 30, isBold, isUnderLine, testFont, 0);

                SunmiPrintHelper.getInstance().printText_nextgen("---------------" + "\n\n", 50, isBold, isUnderLine, testFont, 0);



                //    SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.footer_heder_print) + "\n\n", 20, isBold, isUnderLine, testFont, 0);

                SunmiPrintHelper.getInstance().feedPaper();


            }
            else

            {

                SunmiPrintHelper.getInstance().printText_nextgen("\n" + transactionDate, 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen("                 " + transactionTime + "\n", 20, isBold, isUnderLine, testFont, 2);


                SunmiPrintHelper.getInstance().printText_nextgen(  MyApplication.getSaveString("mobileNoString", AccountBalanceRetailer.this)+"                ", 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen( getString(R.string.terminalidId_print_colon) + "\n", 20, isBold, isUnderLine, testFont, 2);


                SunmiPrintHelper.getInstance().printText_nextgen(  MyApplication.getSaveString("terminalIdString", AccountBalanceRetailer.this)+"              ", 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen( getString(R.string.retailer_print_colon) + "\n", 20, isBold, isUnderLine, testFont, 2);

                SunmiPrintHelper.getInstance().printText_nextgen("---------------" + "\n", 50, isBold, isUnderLine, testFont, 0);

                SunmiPrintHelper.getInstance().printText_nextgen( "100055500145101"+"            ", 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen( getString(R.string.pin_number) + "\n", 20, isBold, isUnderLine, testFont, 2);


                SunmiPrintHelper.getInstance().printText_nextgen( "1.000"+"                           " , 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen( getString(R.string.amount_print) + "\n", 20, isBold, isUnderLine, testFont, 2);

                SunmiPrintHelper.getInstance().printText_nextgen("---------------" + "\n", 50, isBold, isUnderLine, testFont, 0);

                SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.activitionCode_print) + "\n", 20, isBold, isUnderLine, testFont, 2);

                SunmiPrintHelper.getInstance().printText_nextgen("     " + "1234 5678 34563" + "  " + "\n", 30, isBold, isUnderLine, testFont, 0);

                SunmiPrintHelper.getInstance().printText_nextgen("---------------" + "\n\n", 50, isBold, isUnderLine, testFont, 0);

                // SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.footer_heder_print) + "\n\n", 20, isBold, isUnderLine, testFont,0);

                SunmiPrintHelper.getInstance().feedPaper();


            }


        } else {
            // SunmiPrintHelper.getInstance().printText(getString(R.string.account_balance) + " : " + amountFromServer + " LYD", 30, true, isUnderLine, testFont);
        }

    }


    private Bitmap scaleImage(Bitmap bitmap1) {
        int width = bitmap1.getWidth();
        int height = bitmap1.getHeight();
        // 设置想要的大小
        int newWidth = (width/8+1)*8;
        // 计算缩放比例
        float scaleWidth = ((float) newWidth) / width;
        // 取得想要缩放的matrix参数
        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, 1);
        // 得到新的图片
        return Bitmap.createBitmap(bitmap1, 0, 0, width, height, matrix, true);
    }



    protected void successResponse() {

        TextView textresponse, textheading;
        //LayoutInflater layoutInflater=(LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        //Drawable d = new ColorDrawable(R.color.transparent);

        dialog = new Dialog(AccountBalanceRetailer.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        //dialog.getWindow().setBackgroundDrawable(d);
        dialog.setContentView(R.layout.dialogbox_without_print);
        textheading = (TextView) ((dialog)).findViewById(R.id.res_heading);
        textheading.setText(transactionStatus);
        textresponse = (TextView) ((dialog)).findViewById(R.id.responsestring);
        textresponse.setText(getString(R.string.account_balance) + " : " + amountFromServer + " LYD");


        Button dialogbutton1=(Button) ((dialog)).findViewById(R.id.dialogbutton1);

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

                AccountBalanceRetailer.this.finish();

            }
        });

        Button printButton=(Button) ((dialog)).findViewById(R.id.printButton);
        printButton.setVisibility(View.GONE);

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
                if (checkValidation()) {

                    if (CommonUtility.isOnline(AccountBalanceRetailer.this)) {

                        //  serverRequest(4);

                        try {

                            CommonUtility.showProgressDialog(AccountBalanceRetailer.this);



                               serverRequest_balance_volly(101);

                              // serverRequest_login_retrofit(100);

                        } catch (Exception e) {

                            CommonUtility.hideProgressDialog(AccountBalanceRetailer.this);

                            Toast.makeText(AccountBalanceRetailer.this, e.toString(), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(AccountBalanceRetailer.this, getResources().getString(R.string.enter_mpin), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mpinString.length() < 4) {
            Toast.makeText(AccountBalanceRetailer.this, getResources().getString(R.string.enter_mpin), Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }



    private void serverRequest_balance_volly(int requestNo) {


/*
        {
            "apiname": "BALANCE",
                "request": {
                    "agentcode": "0982650605",
                    "pin": "E24B7D1BCA5464639ECE18EBF63ABA2C",
                    "destination": "0982650605",
                    "vendorcode": "TAFANI",
                    "clienttype": "GPRS"
        }
        }
*/

        try {




            JSONObject json_main = new JSONObject();

            json_main.put("apiname", "BALANCE");

            JSONObject jsonObject_request = new JSONObject();
            jsonObject_request.put("agentcode",MyApplication.getSaveString("mobileNoString",AccountBalanceRetailer.this));
            String key = Md5.getMd5Hash(MyApplication.getSaveString("mobileNoString",AccountBalanceRetailer.this)+mpinString).toUpperCase(Locale.ENGLISH);
            jsonObject_request.put("posserialno",MyApplication.getSN());
            jsonObject_request.put("pin",key);
            jsonObject_request.put("destination",MyApplication.getSaveString("mobileNoString",AccountBalanceRetailer.this));
            jsonObject_request.put("vendorcode","TAFANI");
            jsonObject_request.put("clienttype","POS");

            json_main.put("request", jsonObject_request);

            new VollyRequestResponse(AccountBalanceRetailer.this, AccountBalanceRetailer.this, requestNo, base_url+login_ret_balance_apinname, json_main.toString());

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            CommonUtility.hideProgressDialog(AccountBalanceRetailer.this);

            e.printStackTrace();
        }

    }




    private void showAlertDialog(String msg, final int requestId) {

        AlertDialog.Builder builder = new AlertDialog.Builder(AccountBalanceRetailer.this);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(AccountBalanceRetailer.this);
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

        CommonUtility.hideProgressDialog(AccountBalanceRetailer.this);

        try {


            if (serverResponse.toString().contains("Time Out")) {
                showAlertDialog_sh("Time Out");
            } else if (serverResponse.toString().contains("Please try again later")) {
                showAlertDialog_sh("Please try again later");
            }



            else {

                if (requestNo == 101) {



                //    serverResponse = new JSONObject("{\"apiname\":\"BALANCE\",\"response\":{\"responsects\":\"17-12-2021 02:25:11 PM\",\"amount\":\"62.00\",\"agentcode\":\"0982650605\",\"agentname\":\"Prashun\",\"destination\":\"0982650605\",\"vendorcode\":\"TAFANI\",\"transid\":\"45629584\",\"resultcode\":\"0\",\"resultdescription\":\"Transaction Successful\",\"clienttype\":\"GPRS\"}}");

                    if (serverResponse.has("response")) {

                        JSONObject jsonObject_response = serverResponse.getJSONObject("response");

                        if (jsonObject_response.has("resultcode")) {


                            String resultcode = jsonObject_response.getString("resultcode");
                            String resultdescription = jsonObject_response.getString("resultdescription");

                            if (resultcode.equalsIgnoreCase("0")) {

                                 amountFromServer = jsonObject_response.getString("amount");
                                 transactionStatus = jsonObject_response.getString("resultdescription");

                                transid = jsonObject_response.getString("transid");
                                String transactionDateTemp = jsonObject_response.getString("responsects");
                                String[] transactionDate_array=transactionDateTemp.split(" ");
                                 transactionDate=transactionDate_array[0];
                                 transactionTime=transactionDate_array[1]+" "+ transactionDate_array[2];;



                                successResponse();


                                // Toast.makeText(this, amount, Toast.LENGTH_SHORT).show();




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
