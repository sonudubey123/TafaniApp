/*
package com.sunmi.printerhelper.nextgen_sharique.main_activity;

import static com.sunmi.printerhelper.nextgen_sharique.voly_url.Url.base_url;
import static com.sunmi.printerhelper.nextgen_sharique.voly_url.Url.login_pinsalepurchase_apinname;
import static com.sunmi.printerhelper.nextgen_sharique.voly_url.Url.login_service_apinname;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.sunmi.tafani_printerhelper.R;
import com.sunmi.printerhelper.local_set.LocalSetLanguage;
import com.sunmi.printerhelper.nextgen_sharique.account_balance.AccountBalanceDistributor;
import com.sunmi.printerhelper.nextgen_sharique.application_sharepreference.MyApplication;
import com.sunmi.printerhelper.nextgen_sharique.changeMpin.ChangeMpinDistributor;
import com.sunmi.printerhelper.nextgen_sharique.database.online.OnlineDisRecycleViewPage;
import com.sunmi.printerhelper.nextgen_sharique.database.online.OnlineRetRecycleViewPage;
import com.sunmi.printerhelper.nextgen_sharique.interf.InterHttpServerResponse;
import com.sunmi.printerhelper.nextgen_sharique.languag_choose.LanguageChoose;
import com.sunmi.printerhelper.nextgen_sharique.loading_internet.CommonUtility;
import com.sunmi.printerhelper.nextgen_sharique.login.LoginActivation;
import com.sunmi.printerhelper.nextgen_sharique.login.LoginActivity;
import com.sunmi.printerhelper.nextgen_sharique.login.LoginActivityPin;
import com.sunmi.printerhelper.nextgen_sharique.online_offline.pinsale.adapter.OperatorAdapter;
import com.sunmi.printerhelper.nextgen_sharique.online_offline.pinsale.adapter.ProductAdapter;
import com.sunmi.printerhelper.nextgen_sharique.online_offline.pinsale.adapter.VendorAdapter;
import com.sunmi.printerhelper.nextgen_sharique.online_offline.pinsale.model.OperatorModel;
import com.sunmi.printerhelper.nextgen_sharique.online_offline.pinsale.model.ProductModel;
import com.sunmi.printerhelper.nextgen_sharique.online_offline.pinsale.model.VendorTypeModel;
import com.sunmi.printerhelper.nextgen_sharique.transaction_history.distributor.TransactionHistoryDistributor;
import com.sunmi.printerhelper.nextgen_sharique.voly_url.Md5;
import com.sunmi.printerhelper.nextgen_sharique.voly_url.VollyRequestResponse;
import com.sunmi.printerhelper.nextgen_sharique.wallet_transfer.WalletTransferDis;

import org.json.JSONArray;
import org.json.JSONObject;

import java.nio.channels.ScatteringByteChannel;
import java.util.ArrayList;
import java.util.Locale;

public class MainActivityDis extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,View.OnClickListener, InterHttpServerResponse {

    // Declaration
    DrawerLayout drawer_layout_dis;
    FloatingActionButton fab;
    NavigationView navigationView;

    MyApplication myApplication;
    String languageToUse="";


    LinearLayout linOffline,linOnline;
    RadioGroup optionRadioGroup;
    RadioButton offlineradioButton,onlineradioButton;
    Spinner spVendorOffline,spOperatorOffline,spProductOffline,spVendorOnline,spOperatorOnline,spProductOnline;
    EditText etPin;
    Button btnPrintPinOffline,btnPrintPinOnline;
    private ArrayList<String> arrayListvendor;
    private ArrayList<VendorTypeModel> vendorList = new ArrayList<>();
    private ArrayList<ProductModel> productList = new ArrayList<>();
    private ArrayList<OperatorModel> operatorList = new ArrayList<>();
    String vendorCode,operatorCode,productCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity_dis);


        try {

            myApplication = (MyApplication) getApplicationContext();
            languageToUse = myApplication.getmSharedPreferences().getString("languageToUse", "");

            if (languageToUse.trim().length() == 0) {

                languageToUse = "ar";
                LocalSetLanguage.LocalSet(languageToUse, MainActivityDis.this);
            } else {

                LocalSetLanguage.LocalSet(languageToUse, MainActivityDis.this);
            }



            SharedPreferences.Editor Eeditor = MainActivityDis.this.getSharedPreferences("EU_MPIN", MODE_PRIVATE).edit();
            Eeditor.putString("glo_login", "main_activity_distributor");
            Eeditor.commit();


            // Initialize Widgets
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            fab = (FloatingActionButton) findViewById(R.id.fab);
            drawer_layout_dis = (DrawerLayout) findViewById(R.id.drawer_layout_dis);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_layout_dis, toolbar, R.string.nav_open, R.string.nav_close);
            drawer_layout_dis.setDrawerListener(toggle);
            toggle.syncState();

            navigationView = (NavigationView) findViewById(R.id.nav_view);


            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.left_menu_des);

            navigationView.setNavigationItemSelectedListener(this);


            getIds();
        }

        catch (Exception e)
        {
            Toast.makeText(MainActivityDis.this,e.toString(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }



    }




    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.accountbalance_des) {


            Intent intent = new Intent(MainActivityDis.this, AccountBalanceDistributor.class);
            startActivity(intent);

        }

        else if (id == R.id.wallet_transfer_des)
        {
            Intent intent = new Intent(MainActivityDis.this, WalletTransferDis.class);
            startActivity(intent);
        }

        else if (id == R.id.changempin_des)
        {
            Intent intent = new Intent(MainActivityDis.this, ChangeMpinDistributor.class);
            startActivity(intent);
        }


        else if (id == R.id.transactionHistory_des)
        {
            Intent intent = new Intent(MainActivityDis.this, TransactionHistoryDistributor.class);
            startActivity(intent);
        }



        else if (id == R.id.languageChange_des)
        {

            Intent i = new Intent(MainActivityDis.this, LoginActivation.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            MainActivityDis.this.finish();


            MyApplication.saveString("CHANGE_LANGUAGE","MainActivityDis",MainActivityDis.this);

            Intent intent = new Intent(MainActivityDis.this, LanguageChoose.class);
            startActivity(intent);
        }


        else if (id == R.id.logout_des)
        {
            Intent i = new Intent(MainActivityDis.this, LoginActivityPin.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            MainActivityDis.this.finish();
        }


        drawer_layout_dis.closeDrawer(GravityCompat.START);


        return true;
    }


    private void getIds() {
        linOffline = findViewById(R.id.linOffline);
        linOnline = findViewById(R.id.linOnline);
        optionRadioGroup = findViewById(R.id.optionRadioGroup);
        offlineradioButton = findViewById(R.id.offlineradioButton);
        onlineradioButton = findViewById(R.id.onlineradioButton);
        spVendorOffline = findViewById(R.id.spVendorOffline);
        spOperatorOffline = findViewById(R.id.spOperatorOffline);
        spProductOffline = findViewById(R.id.spProductOffline);
        spVendorOnline = findViewById(R.id.spVendorOnline);
        spOperatorOnline = findViewById(R.id.spOperatorOnline);
        spProductOnline = findViewById(R.id.spProductOnline);
        etPin = findViewById(R.id.etPin);
        btnPrintPinOffline = findViewById(R.id.btnPrintPinOffline);
        btnPrintPinOffline.setOnClickListener(this);
        btnPrintPinOnline = findViewById(R.id.btnPrintPinOnline);
        btnPrintPinOnline.setOnClickListener(this);

        optionRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i) {
                    case R.id.offlineradioButton:
                        linOffline.setVisibility(View.VISIBLE);
                        linOnline.setVisibility(View.GONE);
                        break;
                    case R.id.onlineradioButton:
                        linOnline.setVisibility(View.VISIBLE);
                        linOffline.setVisibility(View.GONE);
                        break;
                }
            }
        });

        spVendorOnline.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        // It returns the clicked item.
                        VendorTypeModel clickedItem = (VendorTypeModel)
                                parent.getItemAtPosition(position);
                        vendorCode = clickedItem.getVendorcode();
                        // Toast.makeText(PinSaleActivity.this, name + " selected", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) { }
                });
        spOperatorOnline.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        // It returns the clicked item.
                        OperatorModel clickedItem = (OperatorModel)
                                parent.getItemAtPosition(position);
                        operatorCode = clickedItem.getCode();
                        // Toast.makeText(PinSaleActivity.this, name + " selected", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) { }
                });
        spProductOnline.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        // It returns the clicked item.
                        ProductModel clickedItem = (ProductModel)
                                parent.getItemAtPosition(position);
                        productCode = clickedItem.getProductcode();
                        //Toast.makeText(PinSaleActivity.this, name + " selected", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) { }
                });

        CommonUtility.showProgressDialog(MainActivityDis.this);

        request_service_online_volly(100);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btnPrintPinOffline:

                break;
            case R.id.btnPrintPinOnline:
//                if (spVendorOnline.getSelectedItemPosition()==0) {
//                    Toast.makeText(PinSaleActivity.this, R.string.please_select_vendor_type, Toast.LENGTH_LONG).show();
//                    return;
//                }
//                if (spOperatorOnline.getSelectedItemPosition()==0) {
//                    Toast.makeText(PinSaleActivity.this, R.string.please_select_operator, Toast.LENGTH_LONG).show();
//                    return;
//                }
//                if (spProductOnline.getSelectedItemPosition()==0) {
//                    Toast.makeText(PinSaleActivity.this, R.string.please_select_product, Toast.LENGTH_LONG).show();
//                    return;
//                }
                if (etPin.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivityDis.this, R.string.enter_mpin, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (etPin.length() < 4) {
                    Toast.makeText(MainActivityDis.this, R.string.enter_mpin, Toast.LENGTH_SHORT).show();
                    return;
                }
                CommonUtility.showProgressDialog(MainActivityDis.this);
                request_pinsalepurchase_offline_volly(101);
                break;
        }
    }

    private void request_service_online_volly(int requestNo) {

        try {

            JSONObject json_main = new JSONObject();

            json_main.put("apiname", "SERVICE");

            JSONObject jsonObject_request = new JSONObject();
            jsonObject_request.put("agentcode", MyApplication.getSaveString("mobileNoString", MainActivityDis.this));
            String mPin = MyApplication.getSaveString("mpinString", MainActivityDis.this);
            String key = Md5.getMd5Hash(MyApplication.getSaveString("mobileNoString", MainActivityDis.this)+mPin).toUpperCase(Locale.ENGLISH);
            jsonObject_request.put("pin",key);
            jsonObject_request.put("terminalid","");
            jsonObject_request.put("vendorcode","TAFANI");
            jsonObject_request.put("clienttype","POS");
            jsonObject_request.put("agenttransid","56637461");
            jsonObject_request.put("comments","Ver-1.7.4");

            json_main.put("request", jsonObject_request);

            new VollyRequestResponse(MainActivityDis.this, MainActivityDis.this, requestNo, base_url+login_service_apinname, json_main.toString());

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            CommonUtility.hideProgressDialog(MainActivityDis.this);

            e.printStackTrace();
        }

    }


    @Override
    public void serverResponse(int requestNo, JSONObject serverResponse) {

        CommonUtility.hideProgressDialog(MainActivityDis.this);

        try {


            if (serverResponse.toString().contains("Time Out")) {
                showAlertDialog_sh("Time Out");
            } else if (serverResponse.toString().contains("Please try again later")) {
                showAlertDialog_sh("Please try again later");
            }



            else {

                if (requestNo == 100) {

                //    serverResponse = new JSONObject("{\"apiname\":\"SERVICE\",\"response\":{\"responsects\":\"12/17/2021 03:03:16 PM\",\"agentcode\":\"0981088935\",\"agentname\":\"Ravi Mittal\",\"vendorcode\":\"TAFANI\",\"transid\":\"45629602\",\"resultcode\":\"0\",\"resultdescription\":\"Transaction Successful\",\"clienttype\":\"\",\"recordcount\":\"24\",\"productDetails\":[{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LIBYANA\",\"vendordesc\":\"LIBYANA\",\"productcode\":\"LMP0.25\",\"productdesc\":\"LMP0.25\",\"denomination\":\"0.25\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LIBYANA\",\"vendordesc\":\"LIBYANA\",\"productcode\":\"LMP0.5\",\"productdesc\":\"LMP0.5\",\"denomination\":\"0.5\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LIBYANA\",\"vendordesc\":\"LIBYANA\",\"productcode\":\"LMP1\",\"productdesc\":\"LMP1\",\"denomination\":\"1.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LIBYANA\",\"vendordesc\":\"LIBYANA\",\"productcode\":\"LMP3\",\"productdesc\":\"LMP3\",\"denomination\":\"3.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"MADAR\",\"vendordesc\":\"MADAR\",\"productcode\":\"AAMP3\",\"productdesc\":\"AAMP3\",\"denomination\":\"3.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LIBYANA\",\"vendordesc\":\"LIBYANA\",\"productcode\":\"LMP5\",\"productdesc\":\"LMP5\",\"denomination\":\"5.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"MADAR\",\"vendordesc\":\"MADAR\",\"productcode\":\"AAMP5\",\"productdesc\":\"AAMP5\",\"denomination\":\"5.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LTT\",\"vendordesc\":\"LTT\",\"productcode\":\"LTT5\",\"productdesc\":\"LTT5\",\"denomination\":\"5.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"THLC\",\"vendordesc\":\"Hatif Libya\",\"productcode\":\"HLC5\",\"productdesc\":\"HLC5\",\"denomination\":\"5.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LIBYANA\",\"vendordesc\":\"LIBYANA\",\"productcode\":\"LMP10\",\"productdesc\":\"LMP10\",\"denomination\":\"10.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"MADAR\",\"vendordesc\":\"MADAR\",\"productcode\":\"AAMP10\",\"productdesc\":\"AAMP10\",\"denomination\":\"10.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LTT\",\"vendordesc\":\"LTT\",\"productcode\":\"LTT10\",\"productdesc\":\"LTT10\",\"denomination\":\"10.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"THLC\",\"vendordesc\":\"Hatif Libya\",\"productcode\":\"HLC10\",\"productdesc\":\"HLC10\",\"denomination\":\"10.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"MADAR\",\"vendordesc\":\"MADAR\",\"productcode\":\"AAMP20\",\"productdesc\":\"AAMP20\",\"denomination\":\"20.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LTT\",\"vendordesc\":\"LTT\",\"productcode\":\"LTT20\",\"productdesc\":\"LTT20\",\"denomination\":\"20.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"THLC\",\"vendordesc\":\"Hatif Libya\",\"productcode\":\"HLC20\",\"productdesc\":\"HLC20\",\"denomination\":\"20.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LIBYANA\",\"vendordesc\":\"LIBYANA\",\"productcode\":\"LMP30\",\"productdesc\":\"LMP30\",\"denomination\":\"30.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LTT\",\"vendordesc\":\"LTT\",\"productcode\":\"LTT30\",\"productdesc\":\"LTT30\",\"denomination\":\"30.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"MADAR\",\"vendordesc\":\"MADAR\",\"productcode\":\"AAMP40\",\"productdesc\":\"AAMP40\",\"denomination\":\"40.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LIBYANA\",\"vendordesc\":\"LIBYANA\",\"productcode\":\"LMP40\",\"productdesc\":\"LMP40\",\"denomination\":\"40.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LIBYANA\",\"vendordesc\":\"LIBYANA\",\"productcode\":\"LMP100\",\"productdesc\":\"LMP100\",\"denomination\":\"100.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"MADAR\",\"vendordesc\":\"MADAR\",\"productcode\":\"AAMP100\",\"productdesc\":\"AAMP100\",\"denomination\":\"100.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"THLC\",\"vendordesc\":\"Hatif Libya\",\"productcode\":\"HLC100\",\"productdesc\":\"HLC100\",\"denomination\":\"100.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LTT\",\"vendordesc\":\"LTT\",\"productcode\":\"LTT100\",\"productdesc\":\"LTT 100\",\"denomination\":\"100.0\"}],\"qty\":\"1000\",\"parentname\":\"\",\"parentcode\":\"\",\"terminalid\":\"\",\"operatorcount\":\"18\",\"settings\":{},\"operatorDetails\":[{\"name\":\"TAFANI\",\"code\":\"TAFANI\"},{\"name\":\"Hatif Libya\",\"code\":\"THLC\"},{\"name\":\"LTT\",\"code\":\"LTT\"},{\"name\":\"MADAR\",\"code\":\"MADAR\"},{\"name\":\"LIBYANA\",\"code\":\"LIBYANA\"},{\"name\":\"ANI Pay\",\"code\":\"ANIP\"},{\"name\":\"1st NET\",\"code\":\"FNET\"},{\"name\":\"Digital Impulse\",\"code\":\"NBDA\"},{\"name\":\"City WiFi\",\"code\":\"CFNET\"},{\"name\":\"AlRiyada\",\"code\":\"RDIT\"},{\"name\":\"XNET\",\"code\":\"XNET\"},{\"name\":\"ALKAFAA\",\"code\":\"ALKAFAA\"},{\"name\":\"CONNECT\",\"code\":\"CONNECT\"},{\"name\":\"Giga Net\",\"code\":\"GIGA\"},{\"name\":\"Souqprimo\",\"code\":\"SP\"},{\"name\":\"ZAJEL\",\"code\":\"ZAJEL\"},{\"name\":\"SPGF test\",\"code\":\"SPGF\"},{\"name\":\"PrimoTech\",\"code\":\"PT\"}]}}");

                    if (serverResponse.has("response")) {

                        JSONObject jsonObject_response = serverResponse.getJSONObject("response");

                        if (jsonObject_response.has("resultcode")) {


                            String resultcode = jsonObject_response.getString("resultcode");
                            String resultdescription = jsonObject_response.getString("resultdescription");

                            if (resultcode.equalsIgnoreCase("0")) {

                                JSONArray jsonArrayProductList = jsonObject_response.optJSONArray("productDetails");

                                for (int i = 0; i < jsonArrayProductList.length(); i++) {
                                    JSONObject jsonObject = jsonArrayProductList.optJSONObject(i);
                                    VendorTypeModel vendorTypeModel = new VendorTypeModel(
                                            jsonObject.optString("vendorcode"),
                                            jsonObject.optString("vendordesc"));

                                    ProductModel productModel = new ProductModel(
                                            jsonObject.optString("productcode"),
                                            jsonObject.optString("productdesc"));


                                    vendorList.add(vendorTypeModel);
                                    productList.add(productModel);

                                }
                                JSONArray jsonArrayOperatorList = jsonObject_response.optJSONArray("operatorDetails");
                                for (int i = 0; i < jsonArrayOperatorList.length(); i++) {
                                    JSONObject jsonObject = jsonArrayOperatorList.optJSONObject(i);
                                    OperatorModel operatorModel = new OperatorModel(
                                            jsonObject.optString("name"),
                                            jsonObject.optString("code"));

                                    operatorList.add(operatorModel);
                                }

                                VendorAdapter vendorAdapter = new VendorAdapter(this, vendorList);
                                spVendorOnline.setAdapter(vendorAdapter);
                                OperatorAdapter operatorAdapter = new OperatorAdapter(this, operatorList);
                                spOperatorOnline.setAdapter(operatorAdapter);
                                ProductAdapter productAdapter = new ProductAdapter(this, productList);
                                spProductOnline.setAdapter(productAdapter);

                                // Toast.makeText(this, amount, Toast.LENGTH_SHORT).show();


                            } else {

                                failure_case(resultdescription);

                            }
                        }
                    }

                }

              else  if (requestNo == 101) {

                    serverResponse=new JSONObject("{\"apiname\":\"PURCHASE\",\"response\":{\"responsects\":\"12/17/2021 02:34:49 PM\",\"amount\":\"3.0\",\"agenttransid\":\"id\",\"agentcode\":\"0982650605\",\"destination\":\"0982650605\",\"vendorcode\":\"LIBYANA\",\"productcode\":\"LMP3\",\"transid\":\"45629588\",\"resultcode\":\"0\",\"resultdescription\":\"Transaction Successful\",\"clienttype\":\"GPRS\",\"vat\":\"0\",\"recordcount\":\"2\",\"records\":[{\"pinno\":\"4300604796340\",\"serialno\":\"225432150060656\",\"expirydate\":\"12/31/2021 12:00:00 AM\"},{\"pinno\":\"1620489893332\",\"serialno\":\"225432150060655\",\"expirydate\":\"12/31/2021 12:00:00 AM\"}],\"qty\":\"2\",\"prewalletbalance\":\"62\",\"servicetax\":\"0.0\",\"tax\":\"0\",\"fee\":\"0.0\",\"walletbalance\":\"56\"}}");

                    if (serverResponse.has("response")) {

                        JSONObject jsonObject_response = serverResponse.getJSONObject("response");

                        if (jsonObject_response.has("resultcode")) {

                            String resultcode = jsonObject_response.getString("resultcode");
                            String resultdescription = jsonObject_response.getString("resultdescription");

                            if (resultcode.equalsIgnoreCase("0")) {

                                Toast.makeText(this, "Print Pin Online", Toast.LENGTH_SHORT).show();

                                MyApplication.saveString("ONLINE_PINSALE_DIS_API",serverResponse.toString(),MainActivityDis.this);

                                Intent intent = new Intent(MainActivityDis.this, OnlineDisRecycleViewPage.class);
                                startActivity(intent);


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

    private void showAlertDialog_sh(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivityDis.this);
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


    private void request_pinsalepurchase_offline_volly(int requestNo) {
//        "agentcode": "0982650605",
//                "pin": "E24B7D1BCA5464639ECE18EBF63ABA2C",
//                "destination": "0982650605",
//                "vendorcode": "TAFANI",
//                "productcode": "LMP3",
//                "clienttype": "GPRS",
//                "qty":"2",
//                "agenttransid":"id"

        try {

            JSONObject json_main = new JSONObject();

            json_main.put("apiname", "PURCHASE");

            JSONObject jsonObject_request = new JSONObject();
            jsonObject_request.put("agentcode", MyApplication.getSaveString("mobileNoString", MainActivityDis.this));
            String mPin = etPin.getText().toString();
            String key = Md5.getMd5Hash(MyApplication.getSaveString("mobileNoString", MainActivityDis.this)+mPin).toUpperCase(Locale.ENGLISH);
            jsonObject_request.put("pin",key);
            jsonObject_request.put("destination",MyApplication.getSaveString("mobileNoString", MainActivityDis.this));
            jsonObject_request.put("vendorcode",vendorCode);
            jsonObject_request.put("productcode",productCode);
            jsonObject_request.put("clienttype","POS");
            jsonObject_request.put("qty","2");
            jsonObject_request.put("agenttransid","56637461");

            json_main.put("request", jsonObject_request);

            new VollyRequestResponse(MainActivityDis.this, MainActivityDis.this, requestNo, base_url+login_pinsalepurchase_apinname, json_main.toString());

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            CommonUtility.hideProgressDialog(MainActivityDis.this);

            e.printStackTrace();
        }

    }


}*/
