package com.sunmi.printerhelper.nextgen_sharique.account_balance;


import static com.sunmi.printerhelper.nextgen_sharique.voly_url.Url.base_url;
import static com.sunmi.printerhelper.nextgen_sharique.voly_url.Url.login_ret_balance_apinname;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

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

import org.json.JSONObject;

import java.util.Locale;


public class DemoTemp extends AppCompatActivity implements View.OnClickListener, InterHttpServerResponse {


    private Dialog dialog;

    private boolean isBold, isUnderLine;
    private String testFont;
    LinearLayout  ll_header_print;
    ImageView image_view_logo,image_view_rightside;

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

            languageToUse = "en";



            if (languageToUse.trim().length() == 0) {

                languageToUse = "ar";
                LocalSetLanguage.LocalSet(languageToUse, DemoTemp.this);
            }
            else
            {
                LocalSetLanguage.LocalSet(languageToUse, DemoTemp.this);
            }

            setContentView(R.layout.demo_temp);


            edittext_mpin = (EditText) findViewById(R.id.edittext_mpin);


            ll_header_print = (LinearLayout) findViewById(R.id.ll_header_print);
            image_view_rightside = (ImageView) findViewById(R.id.image_view_rightside);
            image_view_logo = (ImageView) findViewById(R.id.image_view_logo);

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
            Toast.makeText(DemoTemp.this, e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


    }

    private class TableItem {
        private String[] text;
        private int[] width;
        private int[] align;

        public TableItem() {
            text = new String[]{"test","test","test"};
            width = new int[]{1,1,1};
            align = new int[]{0,0,0};
        }

        public String[] getText() {
            return text;
        }

        public void setText(String[] text) {
            this.text = text;
        }

        public int[] getWidth() {
            return width;
        }

        public void setWidth(int[] width) {
            this.width = width;
        }

        public int[] getAlign() {
            return align;
        }

        public void setAlign(int[] align) {
            this.align = align;
        }
    }

    public Bitmap getResizedBitmap(Bitmap image, int maxSize) {

        int width = image.getWidth();
        int height = image.getHeight();

        float bitmapRatio = (float)width / (float) height;
        if (bitmapRatio > 1) {
            width = maxSize+100;
            height = (int) (width / bitmapRatio);
        } else {
            height = maxSize;
            width = (int) (height * bitmapRatio);
        }

        return Bitmap.createScaledBitmap(image, 400, 60, false);
    }


    protected void print_pos_data() {

        if (!BluetoothUtil.isBlueToothPrinter) {

            Bitmap bitmap = null;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inTargetDensity = 200;
            options.inDensity = 200;

            //ll_header_print.setVisibility(View.VISIBLE);

            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.annu, options);

            ll_header_print.setDrawingCacheEnabled(true);
            ll_header_print.buildDrawingCache(true);

          // Bitmap bitmap_new = getResizedBitmap(bitmap ,200);

            SunmiPrintHelper.getInstance().printBitmap_nextgen(bitmap,1);

         //   ll_header_print.setVisibility(View.GONE);



/*
            if (languageToUse.equalsIgnoreCase("en")) {

                SunmiPrintHelper.getInstance().printText_nextgen("\n\n" + transactionDate, 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen("                 " + transactionTime + "\n", 20, isBold, isUnderLine, testFont, 2);

                SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.terminalidId_print_colon), 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen("           " + MyApplication.getSaveString("terminalIdString", DemoTemp.this) + "\n", 20, isBold, isUnderLine, testFont, 2);


                SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.retailer_print_colon), 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen("                   " + MyApplication.getSaveString("mobileNoString", DemoTemp.this) + "\n", 20, isBold, isUnderLine, testFont, 2);

                SunmiPrintHelper.getInstance().printText_nextgen("---------------" + "\n", 50, isBold, isUnderLine, testFont, 0);

                SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.activitionCode_print), 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen("        " + "100055500145101" + "\n", 20, isBold, isUnderLine, testFont, 2);


                SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.amount_print), 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen("                     " + 1.000 + "\n", 20, isBold, isUnderLine, testFont, 2);

                SunmiPrintHelper.getInstance().printText_nextgen("---------------" + "\n", 50, isBold, isUnderLine, testFont, 0);

                SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.pin_number) + "\n", 20, isBold, isUnderLine, testFont, 0);

                SunmiPrintHelper.getInstance().printText_nextgen("     " + "1234 5678 34563" + "  " + "\n", 30, isBold, isUnderLine, testFont, 0);

                SunmiPrintHelper.getInstance().printText_nextgen("---------------" + "\n", 50, isBold, isUnderLine, testFont, 0);

                SunmiPrintHelper.getInstance().printText_nextgen( "To recharge call 1300 and follow the instructions." + "\n", 20, isBold, isUnderLine, testFont, 2);

                SunmiPrintHelper.getInstance().printText_nextgen("             " + "www.Tafani.ly." + "  " + "\n\n", 20, isBold, isUnderLine, testFont, 0);




            //    SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.footer_heder_print) + "\n\n", 20, isBold, isUnderLine, testFont, 0);

                SunmiPrintHelper.getInstance().feedPaper();


            }
            else

                {

                    SunmiPrintHelper.getInstance().printText_nextgen("\n" + transactionDate, 20, isBold, isUnderLine, testFont, 0);
                    SunmiPrintHelper.getInstance().printText_nextgen("                 " + transactionTime + "\n", 20, isBold, isUnderLine, testFont, 2);


                    SunmiPrintHelper.getInstance().printText_nextgen(  MyApplication.getSaveString("mobileNoString", DemoTemp.this)+"                ", 20, isBold, isUnderLine, testFont, 0);
                    SunmiPrintHelper.getInstance().printText_nextgen( getString(R.string.terminalidId_print_colon) + "\n", 20, isBold, isUnderLine, testFont, 2);


                    SunmiPrintHelper.getInstance().printText_nextgen(  MyApplication.getSaveString("terminalIdString", DemoTemp.this)+"              ", 20, isBold, isUnderLine, testFont, 0);
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
*/


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

        dialog = new Dialog(DemoTemp.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        //dialog.getWindow().setBackgroundDrawable(d);
        dialog.setContentView(R.layout.dialogbox);
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

                DemoTemp.this.finish();

            }
        });

        Button printButton=(Button) ((dialog)).findViewById(R.id.printButton);
      //  printButton.setVisibility(View.GONE);

        printButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                try {


                    print_pos_data();





                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }

               }
        });

        dialog.show();
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.imageButton1:
                if (checkValidation()) {

                    if (CommonUtility.isOnline(DemoTemp.this)) {

                        //  serverRequest(4);

                        try {

                            CommonUtility.showProgressDialog(DemoTemp.this);



                               serverRequest_balance_volly(101);

                              // serverRequest_login_retrofit(100);

                        } catch (Exception e) {

                            CommonUtility.hideProgressDialog(DemoTemp.this);

                            Toast.makeText(DemoTemp.this, e.toString(), Toast.LENGTH_SHORT).show();
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
            Toast.makeText(DemoTemp.this, getResources().getString(R.string.enter_mpin), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mpinString.length() < 4) {
            Toast.makeText(DemoTemp.this, getResources().getString(R.string.enter_mpin), Toast.LENGTH_SHORT).show();
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
            jsonObject_request.put("agentcode",MyApplication.getSaveString("mobileNoString", DemoTemp.this));
            String key = Md5.getMd5Hash(MyApplication.getSaveString("mobileNoString", DemoTemp.this)+mpinString).toUpperCase(Locale.ENGLISH);
            jsonObject_request.put("posserialno",MyApplication.getSN());
            jsonObject_request.put("pin",key);
            jsonObject_request.put("destination",MyApplication.getSaveString("mobileNoString", DemoTemp.this));
            jsonObject_request.put("vendorcode","TAFANI");
            jsonObject_request.put("clienttype","POS");

            json_main.put("request", jsonObject_request);

            new VollyRequestResponse(DemoTemp.this, DemoTemp.this, requestNo, base_url+login_ret_balance_apinname, json_main.toString());

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            CommonUtility.hideProgressDialog(DemoTemp.this);

            e.printStackTrace();
        }

    }




    private void showAlertDialog(String msg, final int requestId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(DemoTemp.this);
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

        AlertDialog.Builder builder = new AlertDialog.Builder(DemoTemp.this);
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

        CommonUtility.hideProgressDialog(DemoTemp.this);

        try {

            serverResponse = new JSONObject("{\"apiname\":\"BALANCE\",\"response\":{\"responsects\":\"17-12-2021 02:25:11 PM\",\"amount\":\"62.00\",\"agentcode\":\"0982650605\",\"agentname\":\"Prashun\",\"destination\":\"0982650605\",\"vendorcode\":\"TAFANI\",\"transid\":\"45629584\",\"resultcode\":\"0\",\"resultdescription\":\"Transaction Successful\",\"clienttype\":\"GPRS\"}}");


            if (serverResponse.toString().contains("Time Out")) {
                showAlertDialog_sh("Time Out");
            }

            else if (serverResponse.toString().contains("Please try again later"))
            {
                showAlertDialog_sh("Please try again later");
            }



            else {

                if (requestNo == 101) {



                    serverResponse = new JSONObject("{\"apiname\":\"BALANCE\",\"response\":{\"responsects\":\"17-12-2021 02:25:11 PM\",\"amount\":\"62.00\",\"agentcode\":\"0982650605\",\"agentname\":\"Prashun\",\"destination\":\"0982650605\",\"vendorcode\":\"TAFANI\",\"transid\":\"45629584\",\"resultcode\":\"0\",\"resultdescription\":\"Transaction Successful\",\"clienttype\":\"GPRS\"}}");

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
