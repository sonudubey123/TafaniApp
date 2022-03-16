package com.sunmi.printerhelper.nextgen_sharique.login;

import static com.sunmi.printerhelper.nextgen_sharique.voly_url.Url.base_url;
import static com.sunmi.printerhelper.nextgen_sharique.voly_url.Url.login_ret_dis_apinname;
import static com.sunmi.printerhelper.nextgen_sharique.voly_url.Url.ret_posstock_apiname;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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
import com.sunmi.printerhelper.nextgen_sharique.database.offline.modal_offline.PostockSDownloadOfflineModel;
import com.sunmi.printerhelper.nextgen_sharique.interf.InterHttpServerResponse;
import com.sunmi.printerhelper.nextgen_sharique.loading_internet.CommonUtility;
import com.sunmi.printerhelper.nextgen_sharique.main_activity.MainActivityRet;
import com.sunmi.printerhelper.nextgen_sharique.order_pin.OrderPinActivity;
import com.sunmi.printerhelper.nextgen_sharique.order_pin.modal_orderpin.BulkDownlaodModalOnline;
import com.sunmi.printerhelper.nextgen_sharique.order_pin.modal_orderpin.PosStockSModalOnline;
import com.sunmi.printerhelper.nextgen_sharique.voly_url.Md5;
import com.sunmi.printerhelper.nextgen_sharique.voly_url.VollyRequestResponse;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class LoginActivityPin extends AppCompatActivity implements View.OnClickListener, InterHttpServerResponse {


    TextView btn_submit;
    EditText edit_mobile_number,edittext_mpin;
    String mobileNoString="",mpinString="";
    AlertDialog dialog;

    String dateOfBirthString;
    MyApplication myApplication;

    String languageToUse="";

    ServiceRepository serviceRepository;
    List<BulkDownloadOfflineModel> arraylist_details_pin_y;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            myApplication = (MyApplication) getApplicationContext();
            languageToUse=myApplication.getmSharedPreferences().getString("languageToUse","");

            if (languageToUse.trim().length() == 0) {

                languageToUse = "ar";
                LocalSetLanguage.LocalSet(languageToUse, LoginActivityPin.this);
            }
            else
            {
                LocalSetLanguage.LocalSet(languageToUse, LoginActivityPin.this);
            }


            setContentView(R.layout.login_activity_pin);


            edit_mobile_number = (EditText) findViewById(R.id.edit_mobile_number);
            edittext_mpin = (EditText) findViewById(R.id.edittext_mpin);
            btn_submit = (TextView) findViewById(R.id.btn_submit);
            btn_submit.setOnClickListener(this);

            if(languageToUse.equalsIgnoreCase("ar"))
            {
                edit_mobile_number.setGravity(Gravity.RIGHT);
                edittext_mpin.setGravity(Gravity.RIGHT);
            }
            else
            {
                edit_mobile_number.setGravity(Gravity.LEFT);
                edittext_mpin.setGravity(Gravity.LEFT);


            }




            serviceRepository = new ServiceRepository(LoginActivityPin .this);

            arraylist_details_pin_y = serviceRepository.getpin_download_statrus_y();

            SharedPreferences.Editor Eeditor = LoginActivityPin.this.getSharedPreferences("EU_MPIN", MODE_PRIVATE).edit();
            Eeditor.putString("glo_login", "glo_login");
            Eeditor.commit();


            mobileNoString = MyApplication.getSaveString("mobileNoString", LoginActivityPin.this);
            edit_mobile_number.setText(mobileNoString);



        }
        catch (Exception e)
        {
            Toast.makeText(LoginActivityPin.this,e.toString(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }





    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.btn_submit:

                if (checkValidation()) {

                 //   if (CommonUtility.isOnline(LoginActivityPin.this)) {

                        //  serverRequest(4);

                        try {


                            String offlinePinString = MyApplication.getSaveString("offlinePinString", LoginActivityPin.this);


                           if(offlinePinString.equalsIgnoreCase(mpinString))
                           {

                               String LOGIN_APP = MyApplication.getSaveString("LOGIN_APP", LoginActivityPin.this);

                               if(LOGIN_APP.equalsIgnoreCase("RETAILER")) {



                                  if (CommonUtility.isOnline(LoginActivityPin.this)) {


                                      if(arraylist_details_pin_y.size()>0)
                                      {


                                          CommonUtility.showProgressDialog(LoginActivityPin.this);

                                          request_posstock_S_2nd_volly(49);   // 2nd Time Api

/*
                                          Intent i = new Intent(LoginActivityPin.this, MainActivityRet.class);
                                          i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                          startActivity(i);
                                          LoginActivityPin.this.finish();*/

                                      }

                                      else {

                                          Intent i = new Intent(LoginActivityPin.this, MainActivityRet.class);
                                          i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                          startActivity(i);
                                          LoginActivityPin.this.finish();
                                      }




                                  }

                                  else {
                                      Intent i = new Intent(LoginActivityPin.this, MainActivityRet.class);
                                      i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                      startActivity(i);
                                      LoginActivityPin.this.finish();
                                  }








                               }
                               else
                               {
                                  /* Intent i = new Intent(LoginActivityPin.this, MainActivityDis.class);
                                   i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                   startActivity(i);
                                   LoginActivityPin.this.finish();*/

                                   Intent i = new Intent(LoginActivityPin.this, MainActivityRet.class);
                                   i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                   startActivity(i);
                                   LoginActivityPin.this.finish();
                               }



                           }
                           else
                           {
                               Toast.makeText(LoginActivityPin.this, getString(R.string.your_offline_pin_is_incorrect), Toast.LENGTH_SHORT).show();
                           }



                              // serverRequest_login_retrofit(100);

                        } catch (Exception e) {

                            CommonUtility.hideProgressDialog(LoginActivityPin.this);

                            Toast.makeText(LoginActivityPin.this, e.toString(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                        // callApiLogin(100);


                   /* } else {
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


    private void request_posstock_S_2nd_volly(int requestNo) {

        try {

/*            {
                "apiname": "POSSTOCK",
                    "request": {
                       "agentcode": "0982650605",
                        "pin":"1893274393509319",
                        "vendorcode":"TAFANI",
                        "clienttype":"POS",
                        "terminalid":"861256041366507",
                        "action":"S",
                        "recordcount":"1",
                        "comments":"POSSTOCK STATUS",

                        "records":[{
                          "productcode":"LMP3",
                            "denomination":"3",
                            "stockcount":"2",
                                      "stock":[{
                                                 "s":"225432150060727",
                                                 "d":"31/12/21 20:08:33"
                                              },
                                           {
                                                 "s":"225432150060726",
                                                 "d":"31/12/21 20:09:11"
                                            }]
                           }]
            }
            }*/

            JSONObject json_main = new JSONObject();

            json_main.put("apiname", "POSSTOCK");

            JSONObject jsonObject_request = new JSONObject();
            jsonObject_request.put("agentcode", MyApplication.getSaveString("mobileNoString", LoginActivityPin.this));
            jsonObject_request.put("pin", MyApplication.getSaveString("activationCodeString", LoginActivityPin.this));
            jsonObject_request.put("vendorcode","TAFANI");
            jsonObject_request.put("clienttype","POS");
            jsonObject_request.put("terminalid",MyApplication.getSaveString("terminalIdString", LoginActivityPin.this));
            jsonObject_request.put("action","S");
            jsonObject_request.put("comments","POSSTOCK STATUS");
            jsonObject_request.put("transtypecode","OFFLINEPURCHASE");




            JSONArray jsonArray = new JSONArray();


            System.out.println(arraylist_details_pin_y);


            JSONArray aaaaaa = new JSONArray(arraylist_details_pin_y);

            System.out.println(aaaaaa);


            for(int i=0;i<arraylist_details_pin_y.size();i++)
            {


                if(i==0)
                {

                    JSONArray jsonArray_stock = new JSONArray();
                    JSONObject jsonObject_inner = new JSONObject();

                    jsonObject_inner.put("productcode",arraylist_details_pin_y.get(i).productcode);
                    jsonObject_inner.put("denomination",arraylist_details_pin_y.get(i).denomination);
                    jsonObject_inner.put("stockcount","1");

                    JSONObject jsonObject_stock = new JSONObject();

                    jsonObject_stock.put("s",arraylist_details_pin_y.get(i).serialNumber);
                 //   jsonObject_stock.put("d",MyApplication.current_sell_dateTime());

                    jsonObject_stock.put("d", arraylist_details_pin_y.get(i).sell_dateTime);

                    // jsonObject_stock.put("d", "19/01/21 19:08:33");

                    jsonArray_stock.put(jsonObject_stock);

                    jsonObject_inner.put("stock",jsonArray_stock);

                    jsonArray.put(jsonObject_inner);

                }
                else {

                     int hasdata=hasValue(jsonArray,"productcode",arraylist_details_pin_y.get(i).productcode);

                     if(hasdata!= -1)
                     {

                         JSONObject jsonObject_stock = new JSONObject();

                         jsonObject_stock.put("s", arraylist_details_pin_y.get(i).serialNumber);
                        // jsonObject_stock.put("d", "19/01/21 19:08:33");
                      //   jsonObject_stock.put("d",MyApplication.current_sell_dateTime());
                      //   jsonObject_stock.put("d",MyApplication.current_sell_dateTime());
                         jsonObject_stock.put("d", arraylist_details_pin_y.get(i).sell_dateTime);


                         jsonArray.optJSONObject(hasdata).put("stockcount", (Integer.parseInt(jsonArray.optJSONObject(hasdata).optString("stockcount"))+1)+"");

                         jsonArray.optJSONObject(hasdata).optJSONArray("stock").put(jsonObject_stock);
                     }
                     else {
                         JSONArray jsonArray_stock = new JSONArray();
                         JSONObject jsonObject_inner = new JSONObject();

                         jsonObject_inner.put("productcode",arraylist_details_pin_y.get(i).productcode);
                         jsonObject_inner.put("denomination",arraylist_details_pin_y.get(i).denomination);
                         jsonObject_inner.put("stockcount","1");

                         JSONObject jsonObject_stock = new JSONObject();

                         jsonObject_stock.put("s", arraylist_details_pin_y.get(i).serialNumber);
                       //  jsonObject_stock.put("d", "19/01/21 19:08:33");
                       //  jsonObject_stock.put("d", MyApplication.current_sell_dateTime());
                         jsonObject_stock.put("d", arraylist_details_pin_y.get(i).sell_dateTime);

                         jsonArray_stock.put(jsonObject_stock);

                         jsonObject_inner.put("stock",jsonArray_stock);
                         jsonArray.put(jsonObject_inner);

                     }
                }

            }
            jsonObject_request.put("recordcount",jsonArray.length());

            jsonObject_request.put("records",jsonArray);
            json_main.put("request", jsonObject_request);


            System.out.println(json_main);

           new VollyRequestResponse(LoginActivityPin.this, LoginActivityPin.this, requestNo, base_url+ret_posstock_apiname, json_main.toString());

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            CommonUtility.hideProgressDialog(LoginActivityPin.this);

            e.printStackTrace();
        }

    }


    public int hasValue(JSONArray json, String key, String value) {
        for(int i = 0; i < json.length(); i++) {  // iterate through the JsonArray
            // first I get the 'i' JsonElement as a JsonObject, then I get the key as a string and I compare it with the value
            if(json.optJSONObject(i).optString(key).equals(value)) return i;
        }
        return -1;
    }

    private boolean checkValidation() {

        mobileNoString = edit_mobile_number.getText().toString().trim();
        mpinString = edittext_mpin.getText().toString().trim();



        if (mpinString.isEmpty()) {
            Toast.makeText(LoginActivityPin.this, getResources().getString(R.string.plz_enter_5_digit_offline_pinn), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mpinString.length() < 5) {
            Toast.makeText(LoginActivityPin.this, getResources().getString(R.string.plz_enter_5_digit_offline_pinn), Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }





    private void showAlertDialog(String msg, final int requestId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivityPin.this);
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




    private void showAlertDialog_sh(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivityPin.this);
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

        CommonUtility.hideProgressDialog(LoginActivityPin.this);

        try {


            if (serverResponse.toString().contains("Time Out")) {
                showAlertDialog_sh("Time Out");
            } else if (serverResponse.toString().contains("Please try again later")) {
                showAlertDialog_sh("Please try again later");
            }


            else {

                if (requestNo == 49) // **********Login Activation
                {

                   // serverResponse = new JSONObject("");

                    if (serverResponse.has("response")) {


                        JSONObject jsonObject_response = serverResponse.getJSONObject("response");

                        if (jsonObject_response.has("resultcode")) {


                            String resultcode = jsonObject_response.getString("resultcode");
                            String resultdescription = jsonObject_response.getString("resultdescription");

                            if (resultcode.equalsIgnoreCase("0")) {


                             serviceRepository.deletAllYStatusbulkDownload();

                                Toast.makeText(LoginActivityPin.this, "Sync Successfully", Toast.LENGTH_SHORT).show();


                                Intent i = new Intent(LoginActivityPin.this, MainActivityRet.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                LoginActivityPin.this.finish();

                            }
                            else {

                                failure_case(resultdescription);

                              //  Toast.makeText(LoginActivityPin.this, "Sync Successfully", Toast.LENGTH_SHORT).show();


                                Intent i = new Intent(LoginActivityPin.this, MainActivityRet.class);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                                LoginActivityPin.this.finish();
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


    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
        finishAffinity();

    }


}
