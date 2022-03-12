package com.sunmi.printerhelper.nextgen_sharique.main_activity;

import static com.sunmi.printerhelper.nextgen_sharique.order_pin.OrderPinActivity.current_sell_dateTime;
import static com.sunmi.printerhelper.nextgen_sharique.voly_url.Url.base_url;
import static com.sunmi.printerhelper.nextgen_sharique.voly_url.Url.login_pinsalepurchase_apinname;
import static com.sunmi.printerhelper.nextgen_sharique.voly_url.Url.login_service_apinname;
import static com.sunmi.printerhelper.nextgen_sharique.voly_url.Url.ret_bulkdown_apiname;
import static com.sunmi.printerhelper.nextgen_sharique.voly_url.Url.ret_getOnlineService_apiname;
import static com.sunmi.printerhelper.nextgen_sharique.voly_url.Url.ret_posstock_apiname;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import tafani.sunmi.printer.R;
import com.sunmi.printerhelper.local_set.LocalSetLanguage;
import com.sunmi.printerhelper.nextgen_sharique.account_balance.AccountBalanceRetailer;
import com.sunmi.printerhelper.nextgen_sharique.account_balance.DemoTemp;
import com.sunmi.printerhelper.nextgen_sharique.application_sharepreference.MyApplication;
import com.sunmi.printerhelper.nextgen_sharique.changeMpin.ChangeMpinRetailer;
import com.sunmi.printerhelper.nextgen_sharique.contact.ContactDetails;
import com.sunmi.printerhelper.nextgen_sharique.database.adapter_online.DenominationAdapter;
import com.sunmi.printerhelper.nextgen_sharique.database.adapter_online.ProductAdapter;
import com.sunmi.printerhelper.nextgen_sharique.database.adapter_online.OperatorAdapter;
import com.sunmi.printerhelper.nextgen_sharique.database.model_online.DenominationModel;
import com.sunmi.printerhelper.nextgen_sharique.database.model_online.ProductModel;
import com.sunmi.printerhelper.nextgen_sharique.database.model_online.OperatorModal;
import com.sunmi.printerhelper.nextgen_sharique.database.offline.OperatorOfflineModel;
import com.sunmi.printerhelper.nextgen_sharique.database.offline.ProductOfflineModel;
import com.sunmi.printerhelper.nextgen_sharique.database.offline.ServiceRepository;
import com.sunmi.printerhelper.nextgen_sharique.database.offline.modal_offline.BulkDownloadOfflineModel;
import com.sunmi.printerhelper.nextgen_sharique.database.offline.modal_offline.orderpin_receiptpage.OrderPinOfflineReceiptPage;
import com.sunmi.printerhelper.nextgen_sharique.database.offline.adapter.DenominationAdapterOffline;
import com.sunmi.printerhelper.nextgen_sharique.database.offline.adapter.OperatorAdapterOffline;
import com.sunmi.printerhelper.nextgen_sharique.database.offline.adapter.ProductAdapterOffline;
import com.sunmi.printerhelper.nextgen_sharique.database.offline.modal_offline.ServiceOperatorDownloadOfflineModel;
import com.sunmi.printerhelper.nextgen_sharique.database.offline.modal_offline.ServiceProductDownloadOfflineModel;
import com.sunmi.printerhelper.nextgen_sharique.interf.InterHttpServerResponse;
import com.sunmi.printerhelper.nextgen_sharique.languag_choose.LanguageChoose;
import com.sunmi.printerhelper.nextgen_sharique.loading_internet.CommonUtility;
import com.sunmi.printerhelper.nextgen_sharique.login.LoginActivityPin;

import com.sunmi.printerhelper.nextgen_sharique.order_pin.OrderPinActivity;
import com.sunmi.printerhelper.nextgen_sharique.report.PosStockReport;
import com.sunmi.printerhelper.nextgen_sharique.reprint.ReprintRetailer;
import com.sunmi.printerhelper.nextgen_sharique.session.BroadcastService;
import com.sunmi.printerhelper.nextgen_sharique.transaction_history.retailer.TransactionHistoryRetailer;
import com.sunmi.printerhelper.nextgen_sharique.voly_url.Md5;
import com.sunmi.printerhelper.nextgen_sharique.voly_url.VollyRequestResponse;
import com.sunmi.printerhelper.nextgen_sharique.wallet_transfer.WalletTransferDis;
import com.sunmi.printerhelper.utils.BluetoothUtil;
import com.sunmi.printerhelper.utils.SunmiPrintHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivityRet extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,View.OnClickListener, InterHttpServerResponse, AdapterView.OnItemSelectedListener {

    // Declaration
    DrawerLayout drawer_layout_ret;
    FloatingActionButton fab;
    NavigationView navigationView;
    private String testFont;

    String amountFromServer="",transactionStatus="",vendorcode_offline="",transid_online="",transactionDate_online="",transactionTime_online="",footer_first_online="",footer_second_online="",footer_first_offline="",footer_second_offline="";
    private boolean isBold, isUnderLine;

    String select_vendorName_online="",select_vendorCode_offline="";

    LinearLayout  ll_header_print;
    ImageView image_view_logo,image_view_rightside;


    MyApplication myApplication;
    String languageToUse="";
    ServiceRepository serviceRepository;

    private AlertDialog alertDialog_orderPin;

    List<BulkDownloadOfflineModel> arraylist_details_pin_y;


    String select_denomination_offline="",select_productCode_offline="",select_vendorCode_online="";
    String select_operatorName="",select_operatorCode="",quantityString="",mpinString_orderPin="";

    String pinNumberString_online="",serialNumberString_online="",agenttransid_online="",vendorcode_online="",productcode_online="",amount_online="";



    LinearLayout linOffline,linOnline,ll_pinsale_orderpin;
    RadioGroup optionRadioGroup;
    RadioButton offlineradioButton,onlineradioButton;

    EditText edittext_quantity,editext_popup_order;


    Spinner spinner_operator_offline,spinner_product_offline,spinner_denomination_offline;
    Spinner  spinner_operator_online,spinner_productType_online,spinner_denomination_online;

    EditText etPin;
    Button btnPrintPinOnline,button_pinsale_offline;

    private ArrayList<OperatorModal> operatorList_online = new ArrayList<>();
    private ArrayList<ProductModel> productList_online = new ArrayList<>();
    private ArrayList<DenominationModel> denominationList_online = new ArrayList<>();

    String select_denominationCode="";


    private List<ServiceOperatorDownloadOfflineModel> operator_offline = new ArrayList<>();
    private List<ServiceProductDownloadOfflineModel> productList_offline = new ArrayList<>();
    private List<ServiceProductDownloadOfflineModel> productList_offline_filter = new ArrayList<>();

    List<BulkDownloadOfflineModel> arraylist_details_pin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        try {

            myApplication = (MyApplication) getApplicationContext();
            languageToUse = myApplication.getmSharedPreferences().getString("languageToUse", "");

            if (languageToUse.trim().length() == 0) {

                languageToUse = "ar";

                LocalSetLanguage.LocalSet(languageToUse, MainActivityRet.this);

            } else {
                LocalSetLanguage.LocalSet(languageToUse, MainActivityRet.this);
            }


            setContentView(R.layout.main_activity_ret);
            //startService(new Intent(this, BroadcastService.class));

            serviceRepository = new ServiceRepository(MainActivityRet.this);


            System.out.println(arraylist_details_pin_y);
            System.out.println(arraylist_details_pin_y);



/*
            Log.e("products--",""+serviceRepository.getAllProducts().toString());
            Log.e("operators--",""+serviceRepository.getAllOperators().toString());
            Log.e("---print_f_api_res-",""+serviceRepository.getList_printfOfflineModal());
            Log.e("----BULKDOWNLOAD=----",""+serviceRepository.getList_bulkdownlaod_offline());

*/





            edittext_quantity = findViewById(R.id.edittext_quantity);


            SharedPreferences.Editor Eeditor = MainActivityRet.this.getSharedPreferences("EU_MPIN", MODE_PRIVATE).edit();
            Eeditor.putString("glo_login", "main_activity_retailer");
            Eeditor.commit();


            // Initialize Widgets
            Toolbar toolbar_ret = (Toolbar) findViewById(R.id.toolbar_ret);
            setSupportActionBar(toolbar_ret);

            fab = (FloatingActionButton) findViewById(R.id.fab);
            drawer_layout_ret = (DrawerLayout) findViewById(R.id.drawer_layout_ret);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_layout_ret, toolbar_ret, R.string.nav_open, R.string.nav_close);
            drawer_layout_ret.setDrawerListener(toggle);
            toggle.syncState();


            navigationView = (NavigationView) findViewById(R.id.nav_view_ret);

            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.left_menu_ret);





           navigationView.setNavigationItemSelectedListener(this);


            getIds();

            operator_offline.clear();
            productList_offline.clear();
            productList_offline_filter.clear();


            // denominationList_pinsale.clear();


            Log.e("---Service Operator--",""+serviceRepository.getList_service_operator_download_offline());




      /*      // Get  Service Operator Name
             String aaaa = serviceRepository.get_service_operatorName();
             // Update Service Operator Name
             int name = serviceRepository.update_service_operaterName("RAHULLLLLL","THLC");
     */


             operator_offline = serviceRepository.getList_service_operator_download_offline();
             productList_offline = serviceRepository.getList_service_product_download_offline();
             productList_offline_filter = serviceRepository.getList_service_product_download_offline();


            System.out.println(operator_offline);
            System.out.println(operator_offline);


            ServiceOperatorDownloadOfflineModel serviceOperatorDownloadOfflineModel = new ServiceOperatorDownloadOfflineModel(getString(R.string.please_select_operator),getString(R.string.please_select_operator));
            operator_offline.add(0, serviceOperatorDownloadOfflineModel);
            OperatorAdapterOffline operatorAdapterOffline = new OperatorAdapterOffline(MainActivityRet.this,operator_offline);
            spinner_operator_offline.setAdapter(operatorAdapterOffline);


            ServiceProductDownloadOfflineModel serviceProductDownloadOfflineModel = new ServiceProductDownloadOfflineModel(getString(R.string.please_select_product),getString(R.string.please_select_vendor_type),getString(R.string.please_select_denomination));
            productList_offline.add(0, serviceProductDownloadOfflineModel);
            ProductAdapterOffline opproductAdapter = new ProductAdapterOffline(MainActivityRet.this, productList_offline);
            spinner_product_offline.setAdapter(opproductAdapter);


            DenominationAdapterOffline denominationAdapter = new DenominationAdapterOffline(MainActivityRet.this, productList_offline);
            spinner_denomination_offline.setAdapter(denominationAdapter);

          //  spinner_denomination_offline.setVisibility(View.GONE);



            ll_header_print = (LinearLayout) findViewById(R.id.ll_header_print);
            image_view_rightside = (ImageView) findViewById(R.id.image_view_rightside);
            image_view_logo = (ImageView) findViewById(R.id.image_view_logo);




        }

        catch (Exception e)
        {
            Toast.makeText(MainActivityRet.this,e.toString(),Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.accountbalance_ret) {

            Intent intent = new Intent(MainActivityRet.this, AccountBalanceRetailer.class);
            startActivity(intent);

        }

        else if (id == R.id.order_pin_ret)
        {
            Intent intent = new Intent(MainActivityRet.this, OrderPinActivity.class);
            startActivity(intent);
        }

        else if (id == R.id.changempin_ret)
        {
            Intent intent = new Intent(MainActivityRet.this, ChangeMpinRetailer.class);
            startActivity(intent);
        }

        else if (id == R.id.reprint_ret)
        {
            Intent intent = new Intent(MainActivityRet.this, ReprintRetailer.class);
            startActivity(intent);
        }

        else if (id == R.id.transactionHistory_ret)
        {
            Intent intent = new Intent(MainActivityRet.this, TransactionHistoryRetailer.class);
            startActivity(intent);
        }

        else if (id == R.id.posstock_report)
        {
            Intent intent = new Intent(MainActivityRet.this, PosStockReport.class);
            startActivity(intent);
        }


        else if (id == R.id.languageChange_ret)
        {

            MyApplication.saveString("CHANGE_LANGUAGE","MainActivityRet",MainActivityRet.this);


                /*
                            Intent i = new Intent(MainActivityRet.this, LoginActivation.class);
                            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(i);
                            MainActivityRet.this.finish();
                */

            Intent intent = new Intent(MainActivityRet.this, LanguageChoose.class);
            startActivity(intent);
        }


        else if (id == R.id.logout_ret)
        {
            Intent i = new Intent(MainActivityRet.this, LoginActivityPin.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            MainActivityRet.this.finish();
        }

        else if (id == R.id.contact_details_ret)
        {
            Intent intent = new Intent(MainActivityRet.this, ContactDetails.class);
            startActivity(intent);
        }




        else if (id == R.id.syn_ret)
        {

            if (CommonUtility.isOnline(MainActivityRet.this)) {


                arraylist_details_pin_y = serviceRepository.getpin_download_statrus_y();


                if (arraylist_details_pin_y.size() > 0) {

                    CommonUtility.showProgressDialog(MainActivityRet.this);

                    request_posstock_S_2nd_volly(49);   // 2nd Time Api

                } else {
                    Toast.makeText(MainActivityRet.this, getString(R.string.already_sync), Toast.LENGTH_SHORT).show();

                }
            }
              else {
                    Toast.makeText(MainActivityRet.this, getString(R.string.please_select_vendor_type), Toast.LENGTH_SHORT).show();

                }



        }




        drawer_layout_ret.closeDrawer(GravityCompat.START);


        return true;
    }

    private void alertDialogue_orderPin() {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivityRet.this);
        View v = MainActivityRet.this.getLayoutInflater().inflate(
                R.layout.alertdialouge_orderpin_offline, null);

        ((Button) v.findViewById(R.id.button_alertdialogue_cancel)).setOnClickListener(this);
        ((Button) v.findViewById(R.id.button_alertdialogue_order))
                .setOnClickListener(this);

        ((TextView) v.findViewById(R.id.text_operator)).setText(getString(R.string.operator)+"  " + select_operatorName + "");


        Double double_quantity=Double.parseDouble(quantityString);
        Double double_denomination=Double.parseDouble(select_denomination_offline);

        Double double_amount = double_quantity*double_denomination;

        String amountString=String.valueOf(double_amount);

        ((TextView) v.findViewById(R.id.textview_amount_quantity)).setText(getString(R.string.amount)+"  "+ amountString + "");
        ((TextView) v.findViewById(R.id.textview_total_quantity)).setText(getString(R.string.total_quantity)+"  " + quantityString + "");

        editext_popup_order = (EditText) v.findViewById(R.id.editext_mpin_alertDialogue);
        editext_popup_order.setHint(getString(R.string.plz_enter_5_digit_offline_pinn));


        builder.setView(v);
        builder.setCancelable(false);

        alertDialog_orderPin = builder.create();

        alertDialog_orderPin.show();
    }



    private void getIds() {


        linOffline = findViewById(R.id.linOffline);
        linOnline = findViewById(R.id.linOnline);
        ll_pinsale_orderpin = findViewById(R.id.ll_pinsale_orderpin);
        optionRadioGroup = findViewById(R.id.optionRadioGroup);
        offlineradioButton = findViewById(R.id.offlineradioButton);
        onlineradioButton = findViewById(R.id.onlineradioButton);




        spinner_operator_offline = findViewById(R.id.spinner_operator_offline);
        spinner_product_offline = findViewById(R.id.spinner_product_offline);
        spinner_denomination_offline = findViewById(R.id.spinner_denomination_offline);
       // spinner_denomination_offline.setVisibility(View.GONE);

        spinner_operator_offline.setOnItemSelectedListener(this);
        spinner_product_offline.setOnItemSelectedListener(this);
        spinner_denomination_offline.setOnItemSelectedListener(this);


        spinner_operator_online = findViewById(R.id.spinner_operator_online);
        spinner_productType_online = findViewById(R.id.spinner_productType_online);
        spinner_denomination_online = findViewById(R.id.spinner_denomination_online);
        spinner_denomination_online.setVisibility(View.GONE);

        operatorList_online.clear();
        productList_online.clear();
        denominationList_online.clear();


        OperatorModal vendorTypeMode_temp = new OperatorModal(getString(R.string.please_select_operator),getString(R.string.please_select_operator));
        operatorList_online.add(vendorTypeMode_temp);
        OperatorAdapter vendorAdapter = new OperatorAdapter(this, operatorList_online);
        spinner_operator_online.setAdapter(vendorAdapter);

        ProductModel opeproduct_temp = new ProductModel(getString(R.string.please_select_product));
        productList_online.add(opeproduct_temp);
        ProductAdapter operatorAdapter = new ProductAdapter(this, productList_online);
        spinner_productType_online.setAdapter(operatorAdapter);


        DenominationModel denominationModel_temp = new DenominationModel(getString(R.string.please_select_denomination));
        denominationList_online.add(denominationModel_temp);
        DenominationAdapter denominationAdapter = new DenominationAdapter(this, denominationList_online);
        spinner_denomination_online.setAdapter(denominationAdapter);






        etPin = findViewById(R.id.etPin);


        btnPrintPinOnline = findViewById(R.id.btnPrintPinOnline);
        btnPrintPinOnline.setOnClickListener(this);

        button_pinsale_offline = findViewById(R.id.button_pinsale_offline);
        button_pinsale_offline.setOnClickListener(MainActivityRet.this);




        optionRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch(i)
                {

                    case R.id.offlineradioButton:

                    {
                        linOffline.setVisibility(View.VISIBLE);
                        linOnline.setVisibility(View.GONE);
                        ll_pinsale_orderpin.setVisibility(View.VISIBLE);


                        operator_offline.clear();
                        productList_offline.clear();
                        productList_offline_filter.clear();
                        edittext_quantity.setText("");

                        operator_offline = serviceRepository.getList_service_operator_download_offline();
                        productList_offline = serviceRepository.getList_service_product_download_offline();
                        productList_offline_filter = serviceRepository.getList_service_product_download_offline();

                        ServiceOperatorDownloadOfflineModel serviceOperatorDownloadOfflineModel = new ServiceOperatorDownloadOfflineModel(getString(R.string.please_select_operator),getString(R.string.please_select_operator));
                        operator_offline.add(0, serviceOperatorDownloadOfflineModel);
                        OperatorAdapterOffline operatorAdapterOffline = new OperatorAdapterOffline(MainActivityRet.this,operator_offline);
                        spinner_operator_offline.setAdapter(operatorAdapterOffline);

                        ServiceProductDownloadOfflineModel serviceProductDownloadOfflineModel = new ServiceProductDownloadOfflineModel(getString(R.string.please_select_product),getString(R.string.please_select_vendor_type),getString(R.string.please_select_denomination));
                        productList_offline.add(0, serviceProductDownloadOfflineModel);
                        ProductAdapterOffline opproductAdapter = new ProductAdapterOffline(MainActivityRet.this, productList_offline);
                        spinner_product_offline.setAdapter(opproductAdapter);

                        DenominationAdapterOffline denominationAdapter = new DenominationAdapterOffline(MainActivityRet.this, productList_offline);
                        spinner_denomination_offline.setAdapter(denominationAdapter);

                   }
                        break;

                    case R.id.onlineradioButton: {

                        operatorList_online = new ArrayList<>();
                        productList_online = new ArrayList<>();
                        denominationList_online = new ArrayList<>();

                        operatorList_online.clear();
                        productList_online.clear();
                        denominationList_online.clear();

                        etPin.setText("");

                        spinner_operator_offline.setEnabled(true);
                        spinner_product_offline.setEnabled(false);
                        spinner_denomination_offline.setEnabled(false);


                        OperatorModal vendorTypeMode_temp = new OperatorModal(getString(R.string.please_select_operator), getString(R.string.please_select_operator));
                        operatorList_online.add(vendorTypeMode_temp);
                        OperatorAdapter operatorAdapter1 = new OperatorAdapter(MainActivityRet.this, operatorList_online);
                        spinner_operator_online.setAdapter(operatorAdapter1);

                        ProductModel operatorModel_temp = new ProductModel(getString(R.string.please_select_product));
                        productList_online.add(operatorModel_temp);
                        ProductAdapter productAdapter = new ProductAdapter(MainActivityRet.this, productList_online);
                        spinner_productType_online.setAdapter(productAdapter);

                        DenominationModel productModel_temp = new DenominationModel(getString(R.string.please_select_denomination));
                        denominationList_online.add(productModel_temp);
                        DenominationAdapter productAdapter2 = new DenominationAdapter(MainActivityRet.this, denominationList_online);
                        spinner_denomination_online.setAdapter(productAdapter2);


                        linOnline.setVisibility(View.VISIBLE);
                        linOffline.setVisibility(View.GONE);
                        ll_pinsale_orderpin.setVisibility(View.GONE);

                        if (CommonUtility.isOnline(MainActivityRet.this)) {

                            CommonUtility.showProgressDialog(MainActivityRet.this);

                            request_service_online_volly(100);
                        } else {
                            Toast.makeText(MainActivityRet.this, R.string.please_check_connection, Toast.LENGTH_SHORT).show();
                        }

                    }
                        break;

                }
            }
        });

        spinner_operator_online.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        // It returns the clicked item.

                        OperatorModal clickedItem = (OperatorModal)parent.getItemAtPosition(position);

                        select_vendorName_online = clickedItem.getVendordesc();




                        select_vendorCode_online = clickedItem.getVendordesc();


                        if(position==0)
                        {


                         //   Toast.makeText(MainActivityRet.this, " -----selected--name--"+select_vendorName+"-----selected---Code----"+select_vendorCode,Toast.LENGTH_SHORT).show();
                        }
                        else {

                            CommonUtility.showProgressDialog(MainActivityRet.this);

                            request_getOnlineService_volly(99);
                        }

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) { }
                });

        spinner_productType_online.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        // It returns the clicked item.
                        ProductModel clickedItem = (ProductModel)
                                parent.getItemAtPosition(position);

                        select_operatorCode = clickedItem.getProductCode();


                        if(position==0)
                        {

                        }

                        else
                            {



                                DenominationAdapter denominationAdapter1 = new DenominationAdapter(MainActivityRet.this, denominationList_online);
                                spinner_denomination_online.setAdapter(denominationAdapter1);
                                spinner_denomination_online.setSelection(position);
                            }

                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) { }
                });


        spinner_denomination_online.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        // It returns the clicked item.
                        DenominationModel clickedItem = (DenominationModel)
                                parent.getItemAtPosition(position);

                        select_denominationCode = clickedItem.getDenominationCode();

                     //   Toast.makeText(MainActivityRet.this, " -----selected--product Code--"+select_productCode,Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onNothingSelected(AdapterView<?> parent) { }
                });





    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {


            case R.id.btnPrintPinOnline:

            {
                if (spinner_operator_online.getSelectedItemPosition() == 0) {
                    Toast.makeText(MainActivityRet.this, R.string.please_select_operator, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (spinner_productType_online.getSelectedItemPosition() == 0) {
                    Toast.makeText(MainActivityRet.this, R.string.please_select_operator, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (spinner_denomination_online.getSelectedItemPosition() == 0) {
                    Toast.makeText(MainActivityRet.this, R.string.please_select_denomination, Toast.LENGTH_SHORT).show();
                    return;
                }

                if(etPin.getVisibility() == View.VISIBLE) {

                    if (etPin.getText().toString().isEmpty()) {
                        Toast.makeText(MainActivityRet.this, R.string.enter_mpin, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (etPin.length() < 4) {
                        Toast.makeText(MainActivityRet.this, R.string.enter_mpin, Toast.LENGTH_SHORT).show();
                        return;
                    }
                }

                CommonUtility.showProgressDialog(MainActivityRet.this);

                request_pinsalepurchase_online_volly(101);

              }
                break;

            case R.id.button_pinsale_offline:
            {




                quantityString = edittext_quantity.getText().toString().trim();

                if(spinner_operator_offline.getSelectedItemPosition()==0)
                {
                    Toast.makeText(MainActivityRet.this, R.string.please_select_operator, Toast.LENGTH_SHORT).show();
                    return;
                }

                if(spinner_product_offline.getSelectedItemPosition()==0)
                {
                    Toast.makeText(MainActivityRet.this, R.string.please_select_product, Toast.LENGTH_SHORT).show();
                    return;
                }

                if(spinner_denomination_offline.getSelectedItemPosition()==0)
                {
                    Toast.makeText(MainActivityRet.this, R.string.please_select_denomination, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (quantityString.isEmpty()) {
                    Toast.makeText(MainActivityRet.this, R.string.plz_enter_quentity, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Integer.parseInt(quantityString) > arraylist_details_pin.size()) {
                    Toast.makeText(MainActivityRet.this, "Only "+arraylist_details_pin.size()+" pin is left ! ", Toast.LENGTH_SHORT).show();
                    return;
                }


                operator_offline.clear();
                productList_offline.clear();
               // productList_offline_filter.clear();
                edittext_quantity.setText("");

                operator_offline.clear();
                productList_offline.clear();
                productList_offline_filter.clear();


                operator_offline = serviceRepository.getList_service_operator_download_offline();
                productList_offline = serviceRepository.getList_service_product_download_offline();
                productList_offline_filter = serviceRepository.getList_service_product_download_offline();

                ServiceOperatorDownloadOfflineModel serviceOperatorDownloadOfflineModel = new ServiceOperatorDownloadOfflineModel(getString(R.string.please_select_operator),getString(R.string.please_select_operator));
                operator_offline.add(0, serviceOperatorDownloadOfflineModel);
                OperatorAdapterOffline operatorAdapterOffline = new OperatorAdapterOffline(MainActivityRet.this,operator_offline);
                spinner_operator_offline.setAdapter(operatorAdapterOffline);

                ServiceProductDownloadOfflineModel serviceProductDownloadOfflineModel = new ServiceProductDownloadOfflineModel(getString(R.string.please_select_product),getString(R.string.please_select_vendor_type),getString(R.string.please_select_denomination));
                productList_offline.add(0, serviceProductDownloadOfflineModel);
                ProductAdapterOffline opproductAdapter = new ProductAdapterOffline(MainActivityRet.this, productList_offline);
                spinner_product_offline.setAdapter(opproductAdapter);

                DenominationAdapterOffline denominationAdapter = new DenominationAdapterOffline(MainActivityRet.this, productList_offline);
                spinner_denomination_offline.setAdapter(denominationAdapter);


                alertDialogue_orderPin();

            }
            break;

            case R.id.button_alertdialogue_cancel:
            {
                alertDialog_orderPin.cancel();
            }
            break;

            case R.id.button_alertdialogue_order:
            {
                if(checkValidation_orderPopup())
                {

                    String offlinePinString = MyApplication.getSaveString("offlinePinString", MainActivityRet.this);

                    if(offlinePinString.equalsIgnoreCase(mpinString_orderPin))
                    {


                        for(int i=0;i<Integer.parseInt(quantityString);i++)
                        {
                            serviceRepository.update_bulkdown_pinstatus(arraylist_details_pin.get(i).key,current_sell_dateTime());
                         //  serviceRepository.update_bulkdown_pinstatus(arraylist_details_pin.get(i).key,arraylist_details_pin.get(i).current_sell_dateTime());
                        }

                        System.out.println(arraylist_details_pin);
                        System.out.println(arraylist_details_pin);


                   //    Intent intent = new Intent(MainActivityRet.this, OrderPinOfflineReceiptPage.class);
                     //   startActivity(intent);


                        offline_print_popup(getString(R.string.offline_pin_sale_sucess));


                        alertDialog_orderPin.cancel();
                    }
                    else {
                        Toast.makeText(MainActivityRet.this, getString(R.string.your_offline_pin_is_incorrect), Toast.LENGTH_SHORT).show();
                    }



                }
            }
            break;


        }
    }

    private Dialog dialog;


    protected void offline_print_popup(String transactionStatus) {

        TextView textresponse, textheading;


        dialog = new Dialog(MainActivityRet.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        //dialog.getWindow().setBackgroundDrawable(d);
        dialog.setContentView(R.layout.dialogbox_offline_print);


        textresponse = (TextView) ((dialog)).findViewById(R.id.responsestring);
        textresponse.setText(transactionStatus);


        if(languageToUse.equalsIgnoreCase("ar"))
        {
            // dialogbutton1.setBackgroundResource(R.drawable.conform);

        }
        else
        {
            // dialogbutton1.setBackgroundResource(R.drawable.confirmbutton);
        }



        Button printButton=(Button) ((dialog)).findViewById(R.id.printButton);
        printButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {


                print_pos_data_offline();


                dialog.dismiss();

            }
        });

        dialog.show();
    }


    protected void online_print_popup(String transactionStatus) {

        TextView textresponse, textheading;


        dialog = new Dialog(MainActivityRet.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        //dialog.getWindow().setBackgroundDrawable(d);
        dialog.setContentView(R.layout.dialogbox);


        textresponse = (TextView) ((dialog)).findViewById(R.id.responsestring);


        Button dialogbutton1=(Button) ((dialog)).findViewById(R.id.dialogbutton1);

        textresponse.setText(transactionStatus+"\n"+
                getString(R.string.pin_number) + " : " + serialNumberString_online +"\n" +
                getString(R.string.seriol_number) + " : " + pinNumberString_online +"\n");

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

               // MainActivityRet.this.finish();

            }
        });

        Button printButton=(Button) ((dialog)).findViewById(R.id.printButton);
        printButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                try {
                    print_pos_data_online();
                }

                catch (Exception e)
                {
                    Toast.makeText(MainActivityRet.this,e.toString(), Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }
                dialog.dismiss();

            }
        });

        dialog.show();
    }


    public static String current_sell_dateTime() {

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date dateobj = new Date();

        String  currentDateTime=df.format(dateobj);

        //  currentDateTime ="31/12/21 20:08:33";

        return currentDateTime;
    }

    protected void print_pos_data_offline() {

        if (!BluetoothUtil.isBlueToothPrinter) {

            Bitmap bitmap = null;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inTargetDensity = 200;
            options.inDensity = 200;

            // Bitmap bitmap_new = getResizedBitmap(bitmap ,200);


           String vendorcode_offline_print = MyApplication.getSaveString("vendorcode_offline_print",MainActivityRet.this);



            if(vendorcode_offline_print.equalsIgnoreCase("LIBYANA"))
            {

             //   Toast.makeText(MainActivityRet.this, vendorcode_offline_print, Toast.LENGTH_SHORT).show();

                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ani_libyana, options);

                if(languageToUse.equalsIgnoreCase("ar"))
                {
                    footer_first_offline="للتعبئة: اضغط (120) ثم (الرقم السري) ثم (اتصال)";
                    footer_second_offline="www.Tafani.ly";
                }
                else {
                    footer_first_offline="To recharge: press (120) then (PIN Code) then (dial).";
                    footer_second_offline="www.Tafani.ly";

                }
            }

            else if(vendorcode_offline_print.equalsIgnoreCase("CFNET"))   // Not available
            {
               // Toast.makeText(MainActivityRet.this, vendorcode_offline_print, Toast.LENGTH_SHORT).show();
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ani_logo_jp, options);  // Not available

                if(languageToUse.equalsIgnoreCase("ar"))
                {
                    footer_first_offline="";   // Not available
                    footer_second_offline="";
                }
                else {
                    footer_first_offline="";  // Not available
                    footer_second_offline="";

                }
            }

            else if(vendorcode_offline_print.equalsIgnoreCase("RDIT"))
            {
             //   Toast.makeText(MainActivityRet.this, vendorcode_offline_print, Toast.LENGTH_SHORT).show();
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ani_airiyada, options);  // Not available

                if(languageToUse.equalsIgnoreCase("ar"))
                {
                    footer_first_offline="للتعبئة: أرسل رسالة نصية إلى الرقم (13434): رقم العقد*الرقم السري*رقم كرت التعبئة# ";
                    footer_second_offline="www.Tafani.ly";
                }
                else {
                    footer_first_offline="To top-up: send a text message to the number (13434): Contract number * PIN code * Top-up card number #";
                    footer_second_offline="www.Tafani.ly";

                }
            }

            else if(vendorcode_offline_print.equalsIgnoreCase("LTT"))
            {
             //   Toast.makeText(MainActivityRet.this, vendorcode_offline_print, Toast.LENGTH_SHORT).show();
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ani_ltt, options);

                if(languageToUse.equalsIgnoreCase("ar"))
                {
                    footer_first_offline="لتعبئة رصيد ليبيا فون اضغط *116* رقم البطاقة السري # ثم اتصل.\n" +
                            "لتعبئة رصيد خدمات الانترنت اتصل بـ 116";
                    footer_second_offline="www.Tafani.ly";
                }
                else {
                    footer_first_offline="To top up Libya Phone balance press *116* PIN number # then call.\\n\" +\n" +
                            "                            To top up the balance of internet services, call 116";
                    footer_second_offline="www.Tafani.ly";
                }
            }

            else if(vendorcode_offline_print.equalsIgnoreCase("FNET"))
            {
              //  Toast.makeText(MainActivityRet.this, vendorcode_offline_print, Toast.LENGTH_SHORT).show();
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ani_first_net, options);

                if(languageToUse.equalsIgnoreCase("ar"))
                {
                    footer_first_offline="للتعبئة: عبر الموقع 1stNET.ly" +
                            "او ارسل رسالة للرقم0945000057 بها\n" +
                            "الرقم السري#ر.ت#اخر اربعة ارقام من UserID#phone";

                    footer_second_offline=""; // not available in doc
                }
                else {
                    footer_first_offline="Password # RT # last four digits of";
                    footer_second_offline="1stNET.ly";
                }
            }


            else if(vendorcode_offline_print.equalsIgnoreCase("MADAR"))
            {
              //  Toast.makeText(MainActivityRet.this, vendorcode_offline_print, Toast.LENGTH_SHORT).show();
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ani_madar, options);

                if(languageToUse.equalsIgnoreCase("ar"))
                {
                    footer_first_offline ="للتعبئة اضغط *112* الرقم السري # ثم اتصال.";
                    footer_second_offline="www.Tafani.ly";
                }
                else {

                    footer_first_offline ="To recharge press *112*PIN Number# and dial.";
                    footer_second_offline="www.Tafani.ly";
                }
            }

            else if(vendorcode_offline_print.equalsIgnoreCase("THLC"))
            {
             //   Toast.makeText(MainActivityRet.this, vendorcode_offline_print, Toast.LENGTH_SHORT).show();
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo_ani_operator_hatif_jpg_new, options);

                if(languageToUse.equalsIgnoreCase("ar"))
                {
                    footer_first_offline ="للتعبئة اتصل بالرقم 1300 ثم اتبع التعليمات.";
                    footer_second_offline="www.Tafani.ly";
                }
                else {

                    footer_first_offline ="To recharge call 1300 and follow the instructions.";
                    footer_second_offline="www.Tafani.ly";
                }
            }


            else if(vendorcode_offline_print.equalsIgnoreCase("ALKAFAA"))
            {
              //  Toast.makeText(MainActivityRet.this, vendorcode_offline_print, Toast.LENGTH_SHORT).show();
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ani_alkafa, options);

                if(languageToUse.equalsIgnoreCase("ar"))
                {
                    footer_first_offline ="";
                    footer_second_offline=": لتعبئة البطاقة";
                }
                else {
                    footer_first_offline ="";
                    footer_second_offline="My.alkafaa.net";
                }
            }

            else if(vendorcode_offline_print.equalsIgnoreCase("NBDA"))
            {
              //  Toast.makeText(MainActivityRet.this, vendorcode_offline_print, Toast.LENGTH_SHORT).show();
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ani_digital_implus, options);
                if(languageToUse.equalsIgnoreCase("ar"))
                {
                    footer_first_offline ="للتعبئة: الموقع(user.ditt.ly) أو رسالة نصية للرقم 12012 بها *رقم العقد*اخر 4 ارقام هاتف المشترك* رقم الكرت#";
                    footer_second_offline="";
                }
                else {
                    footer_first_offline ="To fill in: the website (user.ditt.ly) or a text message to the number 12012 with *contract number* the last 4 phone numbers of the subscriber* card number#";
                    footer_second_offline="user.ditt.ly";
                }

            }

            else {

               // Toast.makeText(MainActivityRet.this, vendorcode_offline_print, Toast.LENGTH_SHORT).show();


                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ani_logo_jp, options);
                footer_first_offline="";
                footer_second_offline="";
            }

           SunmiPrintHelper.getInstance().printBitmap_nextgen(bitmap,1);


            if (languageToUse.equalsIgnoreCase("en")) {


                String currentDateTime=current_sell_dateTime(); // 26/01/2022 14:55:46

                String[]  currentDateTime_temp = currentDateTime.split(" ");
                String currentDate_offline = currentDateTime_temp[0];
                String currentTime_offline = currentDateTime_temp[1];

                SunmiPrintHelper.getInstance().printText_nextgen("\n\n" + currentDate_offline, 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen("                    " + currentTime_offline + "\n", 20, isBold, isUnderLine, testFont, 2);

                SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.terminalidId_print_colon), 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen("           " + MyApplication.getSaveString("terminalIdString", MainActivityRet.this) + "\n", 20, isBold, isUnderLine, testFont, 2);


                SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.retailer_print_colon), 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen("                   " + MyApplication.getSaveString("mobileNoString", MainActivityRet.this) + "\n", 20, isBold, isUnderLine, testFont, 2);

                SunmiPrintHelper.getInstance().printText_nextgen("---------------" + "\n", 50, isBold, isUnderLine, testFont, 0);



                int quantityString_temp=Integer.parseInt(quantityString);


                arraylist_details_pin_y = serviceRepository.getpin_download_statrus_y();

                System.out.println(arraylist_details_pin_y);
                System.out.println(arraylist_details_pin_y);


                String select_operatorName_offline = MyApplication.getSaveString("select_operatorName_offline",MainActivityRet.this);
                String select_productCode_offline = MyApplication.getSaveString("select_productCode_offline",MainActivityRet.this);

                SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.operator) +" "+ select_operatorName_offline+"\n",  20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.product_code) +" "+ select_productCode_offline+"\n",  20, isBold, isUnderLine, testFont, 0);
              //  SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.denomination) +" "+select_denomination_offline+"\n",  20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.quantity) +" "+ quantityString+"\n",  20, isBold, isUnderLine, testFont, 0);


                SunmiPrintHelper.getInstance().printText_nextgen("---------------" + "\n", 50, isBold, isUnderLine, testFont, 0);

                SunmiPrintHelper.getInstance().printText_nextgen( footer_first_online + "\n", 16, isBold, isUnderLine, testFont, 1);

                SunmiPrintHelper.getInstance().printText_nextgen(footer_second_online + "  " + "\n\n", 16, isBold, isUnderLine, testFont, 1);

                SunmiPrintHelper.getInstance().feedPaper();


            }
            else

            {

                String currentDateTime=current_sell_dateTime(); // 26/01/2022 14:55:46

                String[]  currentDateTime_temp = currentDateTime.split(" ");
                String currentDate_offline = currentDateTime_temp[0];
                String currentTime_offline = currentDateTime_temp[1];



                SunmiPrintHelper.getInstance().printText_nextgen("\n" + currentDate_offline, 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen("                    " + currentTime_offline + "\n", 20, isBold, isUnderLine, testFont, 2);


                SunmiPrintHelper.getInstance().printText_nextgen(  MyApplication.getSaveString("mobileNoString", MainActivityRet.this)+"                ", 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen( getString(R.string.terminalidId_print_colon) + "\n", 20, isBold, isUnderLine, testFont, 2);


                SunmiPrintHelper.getInstance().printText_nextgen(  MyApplication.getSaveString("terminalIdString", MainActivityRet.this)+"              ", 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen( getString(R.string.retailer_print_colon) + "\n", 20, isBold, isUnderLine, testFont, 2);

                SunmiPrintHelper.getInstance().printText_nextgen("---------------" + "\n", 50, isBold, isUnderLine, testFont, 0);



                arraylist_details_pin_y = serviceRepository.getpin_download_statrus_y();

                System.out.println(arraylist_details_pin_y);
                System.out.println(arraylist_details_pin_y);


                String select_operatorName_offline = MyApplication.getSaveString("select_operatorName_offline",MainActivityRet.this);
                String select_productCode_offline = MyApplication.getSaveString("select_productCode_offline",MainActivityRet.this);



                SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.operator) +" "+ select_operatorName_offline+"\n",  20, isBold, isUnderLine, testFont, 2);
                SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.product_code) +" "+ select_productCode_offline+"\n",  20, isBold, isUnderLine, testFont, 2);
             //   SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.denomination) +" "+select_denomination_offline+"\n",  20, isBold, isUnderLine, testFont, 2);
                SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.quantity) +" "+ quantityString+"\n",  20, isBold, isUnderLine, testFont, 2);

                SunmiPrintHelper.getInstance().printText_nextgen("---------------" + "\n", 50, isBold, isUnderLine, testFont, 0);


                SunmiPrintHelper.getInstance().printText_nextgen( footer_first_online + "\n", 16, isBold, isUnderLine, testFont, 1);

                SunmiPrintHelper.getInstance().printText_nextgen(footer_second_online + "  " + "\n\n", 16, isBold, isUnderLine, testFont, 1);

                SunmiPrintHelper.getInstance().feedPaper();


            }



        } else {
            Toast.makeText(MainActivityRet.this,"Your Printer roll is empty", Toast.LENGTH_SHORT).show();

        }

    }


    public static Bitmap mergeToPin(Bitmap back, Bitmap front) {
        Bitmap result = Bitmap.createBitmap(back.getWidth(), back.getHeight(), back.getConfig());
        Canvas canvas = new Canvas(result);
        int widthBack = back.getWidth();
        int widthFront = front.getWidth();
        float move = (widthBack - widthFront) / 2;
        canvas.drawBitmap(back, 0f, 0f, null);
        canvas.drawBitmap(front, move, move, null);
        return result;
    }

    protected void print_pos_data_online() {

        if(MyApplication.isSrviceStarted){

        }else{
            MyApplication.isSrviceStarted=true;
            startService(new Intent(this, BroadcastService.class));
        }


        if (!BluetoothUtil.isBlueToothPrinter) {

            Bitmap bitmap = null;
            Bitmap bitmap2 = null;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inTargetDensity = 200;
            options.inDensity = 200;
           // bitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.logo, options);


            // Bitmap bitmap_new = getResizedBitmap(bitmap ,200);

            if(vendorcode_online.equalsIgnoreCase("LIBYANA"))
            {
              //  Toast.makeText(MainActivityRet.this, vendorcode_online, Toast.LENGTH_SHORT).show();
              //  bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.arabic_logo, options);
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ani_libyana, options);



                if(languageToUse.equalsIgnoreCase("ar"))
                {
                    footer_first_online="للتعبئة: اضغط (120) ثم (الرقم السري) ثم (اتصال)";
                    footer_second_online="www.Tafani.ly";
                }
                else {
                    footer_first_online="To recharge: press (120) then (PIN Code) then (dial).";
                    footer_second_online="www.Tafani.ly";

                }
            }

           else if(vendorcode_online.equalsIgnoreCase("CFNET"))   // Not available
            {
               // Toast.makeText(MainActivityRet.this, vendorcode_online, Toast.LENGTH_SHORT).show();
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ani_logo_jp, options);  // Not available

                if(languageToUse.equalsIgnoreCase("ar"))
                {
                    footer_first_online="";   // Not available
                    footer_second_online="";
                }
                else {
                    footer_first_online="";  // Not available
                    footer_second_online="";

                }
            }

            else if(vendorcode_online.equalsIgnoreCase("RDIT"))
            {
                  //  Toast.makeText(MainActivityRet.this, vendorcode_online, Toast.LENGTH_SHORT).show();
                    bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ani_airiyada, options);  // Not available

                    if(languageToUse.equalsIgnoreCase("ar"))
                    {
                        footer_first_online="للتعبئة: أرسل رسالة نصية إلى الرقم (13434): رقم العقد*الرقم السري*رقم كرت التعبئة# ";
                        footer_second_online="www.Tafani.ly";
                    }
                    else {
                        footer_first_online="To top-up: send a text message to the number (13434): Contract number * PIN code * Top-up card number #";
                        footer_second_online="www.Tafani.ly";

                    }
                }

            else if(vendorcode_online.equalsIgnoreCase("LTT"))
            {
              //  Toast.makeText(MainActivityRet.this, vendorcode_online, Toast.LENGTH_SHORT).show();
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ani_ltt, options);

                if(languageToUse.equalsIgnoreCase("ar"))
                {
                    footer_first_online="لتعبئة رصيد ليبيا فون اضغط *116* رقم البطاقة السري # ثم اتصل.\n" +
                            "لتعبئة رصيد خدمات الانترنت اتصل بـ 116";
                    footer_second_online="www.Tafani.ly";
                }
                else {
                    footer_first_online="To top up Libya Phone balance press *116* PIN number # then call.\\n\" +\n" +
                            "                            To top up the balance of internet services, call 116";
                    footer_second_online="www.Tafani.ly";
                }
            }

            else if(vendorcode_online.equalsIgnoreCase("FNET"))
            {
               // Toast.makeText(MainActivityRet.this, vendorcode_online, Toast.LENGTH_SHORT).show();
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ani_first_net, options);

                if(languageToUse.equalsIgnoreCase("ar"))
                {
                    footer_first_online="للتعبئة: عبر الموقع 1stNET.ly" +
                            "او ارسل رسالة للرقم0945000057 بها\n" +
                            "الرقم السري#ر.ت#اخر اربعة ارقام من UserID#phone";

                    footer_second_online=""; // not available in doc
                }
                else {
                    footer_first_online="Password # RT # last four digits of";
                    footer_second_online="1stNET.ly";
                }
            }


            else if(vendorcode_online.equalsIgnoreCase("MADAR"))
            {
              //  Toast.makeText(MainActivityRet.this, vendorcode_online, Toast.LENGTH_SHORT).show();
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ani_madar, options);

                if(languageToUse.equalsIgnoreCase("ar"))
                {
                    footer_first_online ="للتعبئة اضغط *112* الرقم السري # ثم اتصال.";
                    footer_second_online="www.Tafani.ly";
                }
                else {

                    footer_first_online ="To recharge press *112*PIN Number# and dial.";
                    footer_second_online="www.Tafani.ly";
                }
            }

            else if(vendorcode_online.equalsIgnoreCase("THLC"))
            {
               // Toast.makeText(MainActivityRet.this, vendorcode_online, Toast.LENGTH_SHORT).show();
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.logo_ani_operator_hatif_jpg_new, options);

                if(languageToUse.equalsIgnoreCase("ar"))
                {
                    footer_first_online ="للتعبئة اتصل بالرقم 1300 ثم اتبع التعليمات.";
                    footer_second_online="www.Tafani.ly";
                }
                else {

                    footer_first_online ="To recharge call 1300 and follow the instructions.";
                    footer_second_online="www.Tafani.ly";
                }
            }


            else if(vendorcode_online.equalsIgnoreCase("ALKAFAA"))
            {
             //   Toast.makeText(MainActivityRet.this, vendorcode_online, Toast.LENGTH_SHORT).show();
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ani_alkafa, options);

                if(languageToUse.equalsIgnoreCase("ar"))
                {
                    footer_first_online ="";
                    footer_second_online=": لتعبئة البطاقة";
                }
                else {
                    footer_first_online ="";
                    footer_second_online="My.alkafaa.net";
                }
            }

            else if(vendorcode_online.equalsIgnoreCase("NBDA"))
            {
             //   Toast.makeText(MainActivityRet.this, vendorcode_online, Toast.LENGTH_SHORT).show();
                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ani_digital_implus, options);
                if(languageToUse.equalsIgnoreCase("ar"))
                {
                    footer_first_online ="للتعبئة: الموقع(user.ditt.ly) أو رسالة نصية للرقم 12012 بها *رقم العقد*اخر 4 ارقام هاتف المشترك* رقم الكرت#";
                    footer_second_online="";
                }
                else {
                    footer_first_online ="To fill in: the website (user.ditt.ly) or a text message to the number 12012 with *contract number* the last 4 phone numbers of the subscriber* card number#";
                    footer_second_online="user.ditt.ly";
                }

            }

            else {

                bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ani_logo_jp, options);
                footer_first_online="";
                footer_second_online="";
            }


           // Bitmap bitmap3=mergeToPin(bitmap,bitmap2);

            //SunmiPrintHelper.getInstance().printBitmap_nextgen(bitmap3,0);
            SunmiPrintHelper.getInstance().printBitmap_nextgen(bitmap,0);


          /*  image_view_rightside.getLayoutParams().height = 400; //can change the size according to you requirements
            image_view_rightside.getLayoutParams().width = 100; //--
            image_view_rightside.requestLayout();
            image_view_rightside.setScaleType(ImageView.ScaleType.CENTER_INSIDE);*/


          //  image_view_rightside.setBackgroundResource(R.drawable.madar_p);
          //  bitmap = Bitmap.createBitmap(ll_header_print.getDrawingCache());
          //  SunmiPrintHelper.getInstance().printBitmap_nextgen(bitmap,1);





            if (languageToUse.equalsIgnoreCase("en")) {


                SunmiPrintHelper.getInstance().printText_nextgen( "\n\n"+transactionDate_online, 20, true, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen("               " + transactionTime_online + "\n", 20, isBold, isUnderLine, testFont, 2);


                SunmiPrintHelper.getInstance().printText_nextgen( getString(R.string.transaction_id), 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen("                " + transid_online + "\n", 20, true, isUnderLine, testFont, 2);



                SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.terminal_id), 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen("            " + MyApplication.getSaveString("terminalIdString", MainActivityRet.this) + "\n", 20, isBold, isUnderLine, testFont, 2);


              /*  SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.retailer), 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen("                   " + MyApplication.getSaveString("mobileNoString", MainActivityRet.this) + "\n", 20, isBold, isUnderLine, testFont, 2);
*/
                SunmiPrintHelper.getInstance().printText_nextgen("---------------" + "\n", 50, isBold, isUnderLine, testFont, 0);

                String pinNumberString_online_print = pinNumberString_online.replaceAll("...", "$0 ");
                SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.pin_number) +" "+pinNumberString_online_print+"\n",  20, true, isUnderLine, testFont, 1);


                SunmiPrintHelper.getInstance().printText_nextgen("LYD " +" "+amount_online+"\n",  30, true, isUnderLine, testFont, 1);

                SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.seriol_number) +" "+serialNumberString_online+"\n",  20, isBold, isUnderLine, testFont, 1);

                SunmiPrintHelper.getInstance().printText_nextgen("---------------" + "\n", 50, isBold, isUnderLine, testFont, 0);

                SunmiPrintHelper.getInstance().printText_nextgen( footer_first_online + "\n", 16, isBold, isUnderLine, testFont, 1);

                SunmiPrintHelper.getInstance().printText_nextgen(footer_second_online + "  " + "\n\n", 16, isBold, isUnderLine, testFont, 1);




            //    SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.footer_heder_print) + "\n\n", 20, isBold, isUnderLine, testFont, 0);

                SunmiPrintHelper.getInstance().feedPaper();


            }
            else

                {






                    SunmiPrintHelper.getInstance().printText_nextgen("\n\n"+transactionDate_online, 20, true, isUnderLine, testFont, 0);
                    SunmiPrintHelper.getInstance().printText_nextgen("               " + transactionTime_online + "\n", 20, true, isUnderLine, testFont, 2);

                    SunmiPrintHelper.getInstance().printText_nextgen(  transid_online+"                    ", 20, isBold, isUnderLine, testFont, 0);
                    SunmiPrintHelper.getInstance().printText_nextgen( getString(R.string.transaction_id) + "\n", 20, isBold, isUnderLine, testFont, 2);


                    SunmiPrintHelper.getInstance().printText_nextgen(  MyApplication.getSaveString("mobileNoString",MainActivityRet.this)+"              ", 20, isBold, isUnderLine, testFont, 0);
                    SunmiPrintHelper.getInstance().printText_nextgen( getString(R.string.terminal_id) +"      " + "\n", 20, isBold, isUnderLine, testFont, 2);

                 /*   SunmiPrintHelper.getInstance().printText_nextgen(  MyApplication.getSaveString("terminalIdString", MainActivityRet.this)+"              ", 20, isBold, isUnderLine, testFont, 0);
                    SunmiPrintHelper.getInstance().printText_nextgen( getString(R.string.retailer)+"    " + "\n", 20, isBold, isUnderLine, testFont, 2);
*/
                    SunmiPrintHelper.getInstance().printText_nextgen("---------------" + "\n", 50, isBold, isUnderLine, testFont, 0);

                    String pinNumberString_online_print = pinNumberString_online.replaceAll("...", "$0 ");
                    SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.pin_number) +" "+pinNumberString_online_print+"\n",  20, true, isUnderLine, testFont, 1);


                    SunmiPrintHelper.getInstance().printText_nextgen("دينار " +" "+amount_online+"\n",  30, true, isUnderLine, testFont, 1);
                    SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.seriol_number) +" "+serialNumberString_online+"\n",  20, isBold, isUnderLine, testFont, 1);

                    SunmiPrintHelper.getInstance().printText_nextgen("---------------" + "\n", 50, isBold, isUnderLine, testFont, 0);

                    SunmiPrintHelper.getInstance().printText_nextgen( footer_first_online + "\n", 16, isBold, isUnderLine, testFont, 1);

                    SunmiPrintHelper.getInstance().printText_nextgen(footer_second_online + "  " + "\n\n", 16, isBold, isUnderLine, testFont, 1);

                    // SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.footer_heder_print) + "\n\n", 20, isBold, isUnderLine, testFont,0);

                SunmiPrintHelper.getInstance().feedPaper();


            }



        } else {
            // SunmiPrintHelper.getInstance().printText(getString(R.string.account_balance) + " : " + amountFromServer + " LYD", 30, true, isUnderLine, testFont);
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





    private void success_dialouge_transaction(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivityRet.this);
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


    private boolean checkValidation_orderPopup() {

        mpinString_orderPin = editext_popup_order.getText().toString().trim();

        if (mpinString_orderPin.isEmpty()) {
            Toast.makeText(MainActivityRet.this, getResources().getString(R.string.plz_enter_5_digit_offline_pinn), Toast.LENGTH_SHORT).show();
            return false;
        }

        if (mpinString_orderPin.length() < 5) {
            Toast.makeText(MainActivityRet.this, getResources().getString(R.string.plz_enter_5_digit_offline_pinn), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }


    @Override
    protected void onStart() {
        super.onStart();


        spinner_operator_online.setSelection(0);
        spinner_denomination_online.setSelection(0);
        spinner_productType_online.setSelection(0);



        spinner_operator_offline.setSelection(0);
        spinner_denomination_offline.setSelection(0);
        spinner_product_offline.setSelection(0);

        edittext_quantity.setText("");
        etPin.setText("");




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
            jsonObject_request.put("agentcode", MyApplication.getSaveString("mobileNoString", MainActivityRet.this));
            jsonObject_request.put("pin", MyApplication.getSaveString("activationCodeString", MainActivityRet.this));
            jsonObject_request.put("vendorcode","TAFANI");
            jsonObject_request.put("clienttype","POS");
            jsonObject_request.put("terminalid",MyApplication.getSaveString("terminalIdString", MainActivityRet.this));
            jsonObject_request.put("action","S");
            jsonObject_request.put("transtypecode","OFFLINEPURCHASE");

            jsonObject_request.put("comments","POSSTOCK STATUS");



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

                    jsonObject_stock.put("s", arraylist_details_pin_y.get(i).serialNumber);
                   // jsonObject_stock.put("d", "19/01/21 19:08:33");
                //    jsonObject_stock.put("d", MyApplication.current_sell_dateTime());
                    jsonObject_stock.put("d", arraylist_details_pin_y.get(i).sell_dateTime);

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
                      //  jsonObject_stock.put("d", "19/01/21 19:08:33");
                      //  jsonObject_stock.put("d", MyApplication.current_sell_dateTime());
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
                        //jsonObject_stock.put("d", "19/01/21 19:08:33");
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

            new VollyRequestResponse(MainActivityRet.this, MainActivityRet.this, requestNo, base_url+ret_posstock_apiname, json_main.toString());

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            CommonUtility.hideProgressDialog(MainActivityRet.this);

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

    private void request_service_online_volly(int requestNo) {

        try {

            JSONObject json_main = new JSONObject();

            json_main.put("apiname", "SERVICE");

            JSONObject jsonObject_request = new JSONObject();
            jsonObject_request.put("agentcode", MyApplication.getSaveString("mobileNoString", MainActivityRet.this));

            String mPin = MyApplication.getSaveString("mpinString", MainActivityRet.this);

            String key = Md5.getMd5Hash(MyApplication.getSaveString("mobileNoString", MainActivityRet.this)+mPin).toUpperCase(Locale.ENGLISH);

            jsonObject_request.put("pin",key);
            // jsonObject_request.put("terminalid",MyApplication.getSaveString("terminalIdString",MainActivityRet.this));
            jsonObject_request.put("terminalid","");  // if send Then not working
            jsonObject_request.put("vendorcode","TAFANI");
            jsonObject_request.put("clienttype","POS");
            jsonObject_request.put("agenttransid","56637461");
            jsonObject_request.put("comments","Ver-1");

            json_main.put("request", jsonObject_request);

            new VollyRequestResponse(MainActivityRet.this, MainActivityRet.this, requestNo, base_url+login_service_apinname, json_main.toString());

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            CommonUtility.hideProgressDialog(MainActivityRet.this);

            e.printStackTrace();
        }

    }




    @Override
    public void serverResponse(int requestNo, JSONObject serverResponse) {

        CommonUtility.hideProgressDialog(MainActivityRet.this);

        try {


            if (serverResponse.toString().contains("Time Out")) {
                showAlertDialog_sh("Time Out");
            } else if (serverResponse.toString().contains("Please try again later")) {
                showAlertDialog_sh("Please try again later");
            }



            else {

                if (requestNo == 100) {


                 //   serverResponse = new JSONObject("{\"apiname\":\"SERVICE\",\"response\":{\"responsects\":\"01/11/2022 09:11:16 AM\",\"agentcode\":\"0925443779\",\"agentname\":\"Saleh Abozeed Buss\",\"vendorcode\":\"TAFANI\",\"transid\":\"45631341\",\"resultcode\":\"0\",\"resultdescription\":\"Transaction Successful\",\"clienttype\":\"\",\"recordcount\":\"28\",\"productDetails\":[{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"MADAR\",\"vendordesc\":\"MADAR\",\"productcode\":\"AAMP3\",\"productdesc\":\"AAMP3\",\"denomination\":\"3.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LIBYANA\",\"vendordesc\":\"LIBYANA\",\"productcode\":\"LMP3\",\"productdesc\":\"LMP3\",\"denomination\":\"3.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"THLC\",\"vendordesc\":\"Hatif Libya\",\"productcode\":\"HLC5\",\"productdesc\":\"HLC5\",\"denomination\":\"5.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LTT\",\"vendordesc\":\"LTT\",\"productcode\":\"LTT5\",\"productdesc\":\"LTT5\",\"denomination\":\"5.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"MADAR\",\"vendordesc\":\"MADAR\",\"productcode\":\"AAMP5\",\"productdesc\":\"AAMP5\",\"denomination\":\"5.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LIBYANA\",\"vendordesc\":\"LIBYANA\",\"productcode\":\"LMP5\",\"productdesc\":\"LMP5\",\"denomination\":\"5.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LIBYANA\",\"vendordesc\":\"LIBYANA\",\"productcode\":\"LMP10\",\"productdesc\":\"LMP10\",\"denomination\":\"10.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"THLC\",\"vendordesc\":\"Hatif Libya\",\"productcode\":\"HLC10\",\"productdesc\":\"HLC10\",\"denomination\":\"10.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LTT\",\"vendordesc\":\"LTT\",\"productcode\":\"LTT10\",\"productdesc\":\"LTT10\",\"denomination\":\"10.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"MADAR\",\"vendordesc\":\"MADAR\",\"productcode\":\"AAMP10\",\"productdesc\":\"AAMP10\",\"denomination\":\"10.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LTT\",\"vendordesc\":\"LTT\",\"productcode\":\"LTT20\",\"productdesc\":\"LTT20\",\"denomination\":\"20.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"MADAR\",\"vendordesc\":\"MADAR\",\"productcode\":\"AAMP20\",\"productdesc\":\"AAMP20\",\"denomination\":\"20.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LIBYANA\",\"vendordesc\":\"LIBYANA\",\"productcode\":\"LMP30\",\"productdesc\":\"LMP30\",\"denomination\":\"30.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"THLC\",\"vendordesc\":\"Hatif Libya\",\"productcode\":\"HLC30\",\"productdesc\":\"HLC30\",\"denomination\":\"30.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LTT\",\"vendordesc\":\"LTT\",\"productcode\":\"LTT30\",\"productdesc\":\"LTT30\",\"denomination\":\"30.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"MADAR\",\"vendordesc\":\"MADAR\",\"productcode\":\"AAMP40\",\"productdesc\":\"AAMP40\",\"denomination\":\"40.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"THLC\",\"vendordesc\":\"Hatif Libya\",\"productcode\":\"HLC50\",\"productdesc\":\"HLC50\",\"denomination\":\"50.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"MADAR\",\"vendordesc\":\"MADAR\",\"productcode\":\"AAMP50\",\"productdesc\":\"AAMP50\",\"denomination\":\"50.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LIBYANA\",\"vendordesc\":\"LIBYANA\",\"productcode\":\"LMP50\",\"productdesc\":\"LMP50\",\"denomination\":\"50.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LIBYANA\",\"vendordesc\":\"LIBYANA\",\"productcode\":\"LMP80\",\"productdesc\":\"LMP80\",\"denomination\":\"80.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LTT\",\"vendordesc\":\"LTT\",\"productcode\":\"LTT80\",\"productdesc\":\"LTT80\",\"denomination\":\"80.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"LIBYANA\",\"vendordesc\":\"LIBYANA\",\"productcode\":\"LMP100\",\"productdesc\":\"LMP100\",\"denomination\":\"100.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"THLC\",\"vendordesc\":\"Hatif Libya\",\"productcode\":\"HLC100\",\"productdesc\":\"HLC100\",\"denomination\":\"100.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"MADAR\",\"vendordesc\":\"MADAR\",\"productcode\":\"AAMP100\",\"productdesc\":\"AAMP100\",\"denomination\":\"100.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"THLC\",\"vendordesc\":\"Hatif Libya\",\"productcode\":\"HLC200\",\"productdesc\":\"HLC200\",\"denomination\":\"200.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"MADAR\",\"vendordesc\":\"MADAR\",\"productcode\":\"AAMP200\",\"productdesc\":\"AAMP200\",\"denomination\":\"200.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"MADAR\",\"vendordesc\":\"MADAR\",\"productcode\":\"AAMP500\",\"productdesc\":\"AAMP500\",\"denomination\":\"500.0\"},{\"servicecode\":\"BULKDWN\",\"servicedesc\":\"Bulk Download\",\"vendortypecode\":\"\",\"vendortypedesc\":\"\",\"vendorcode\":\"THLC\",\"vendordesc\":\"Hatif Libya\",\"productcode\":\"HLC500\",\"productdesc\":\"HLC500\",\"denomination\":\"500.0\"}],\"qty\":\"1000\",\"parentname\":\"\",\"parentcode\":\"\",\"terminalid\":\"\",\"operatorcount\":\"18\",\"settings\":{},\"operatorDetails\":[{\"name\":\"TAFANI\",\"code\":\"TAFANI\"},{\"name\":\"Hatif Libya\",\"code\":\"THLC\"},{\"name\":\"LTT\",\"code\":\"LTT\"},{\"name\":\"MADAR\",\"code\":\"MADAR\"},{\"name\":\"LIBYANA\",\"code\":\"LIBYANA\"},{\"name\":\"ANI Pay\",\"code\":\"ANIP\"},{\"name\":\"1st NET\",\"code\":\"FNET\"},{\"name\":\"Digital Impulse\",\"code\":\"NBDA\"},{\"name\":\"City WiFi\",\"code\":\"CFNET\"},{\"name\":\"AlRiyada\",\"code\":\"RDIT\"},{\"name\":\"XNET\",\"code\":\"XNET\"},{\"name\":\"ALKAFAA\",\"code\":\"ALKAFAA\"},{\"name\":\"CONNECT\",\"code\":\"CONNECT\"},{\"name\":\"Giga Net\",\"code\":\"GIGA\"},{\"name\":\"Souqprimo\",\"code\":\"SP\"},{\"name\":\"ZAJEL\",\"code\":\"ZAJEL\"},{\"name\":\"SPGF test\",\"code\":\"SPGF\"},{\"name\":\"PrimoTech\",\"code\":\"PT\"}],\"onlineoperatorcount\":\"3\",\"onlineoperatorDetails\":[{\"name\":\"TAFANI\",\"code\":\"TAFANI\"},{\"name\":\"Hatif Libya\",\"code\":\"THLC\"},{\"name\":\"LTT\",\"code\":\"LTT\"}]}}");


                 //   Toast.makeText(this, "HARD CODE ----------100 ---101", Toast.LENGTH_SHORT).show();

                    if (serverResponse.has("response")) {

                        JSONObject jsonObject_response = serverResponse.getJSONObject("response");

                        if (jsonObject_response.has("resultcode")) {


                            String resultcode = jsonObject_response.getString("resultcode");
                            String resultdescription = jsonObject_response.getString("resultdescription");

                            if (resultcode.equalsIgnoreCase("0")) {

                                operatorList_online.clear();

                                JSONArray jsonArray_onlineoperatorDetails = jsonObject_response.optJSONArray("onlineoperatorDetails");

                                OperatorModal vendorTypeMode_temp = new OperatorModal(getString(R.string.please_select_operator),getString(R.string.please_select_operator));
                                operatorList_online.add(vendorTypeMode_temp);


                                for (int i = 0; i < jsonArray_onlineoperatorDetails.length(); i++) {

                                    JSONObject jsonObject = jsonArray_onlineoperatorDetails.optJSONObject(i);
                                    OperatorModal vendorTypeModel = new OperatorModal(


                                            jsonObject.optString("name"),
                                            jsonObject.optString("code"));

                                    operatorList_online.add(vendorTypeModel);

                                }



                                OperatorAdapter operatorAdapter = new OperatorAdapter(this, operatorList_online);
                                spinner_operator_online.setAdapter(operatorAdapter);



                                // ##################################  For Data Base Used #################

                                JSONArray jsonArrayProductList_offline = jsonObject_response.optJSONArray("operatorDetails");

                                for (int i = 0; i < jsonArrayProductList_offline.length(); i++)
                                {
                                    JSONObject jsonObject = jsonArrayProductList_offline.optJSONObject(i);
                                    ProductOfflineModel productOfflineModel = new ProductOfflineModel(
                                            jsonObject.optString("servicecode"),
                                            jsonObject.optString("servicedesc"),
                                            jsonObject.optString("vendortypecode"),
                                            jsonObject.optString("vendortypedesc"),
                                            jsonObject.optString("vendorcode"),
                                            jsonObject.optString("vendordesc"),
                                            jsonObject.optString("productcode"),
                                            jsonObject.optString("productdesc"),
                                            jsonObject.optString("denomination")
                                    );
                                    serviceRepository.insert(productOfflineModel);
                                }

                                JSONArray jsonArrayOperatorList_offline = jsonObject_response.optJSONArray("operatorDetails");
                                for (int i = 0; i < jsonArrayOperatorList_offline.length(); i++) {
                                    JSONObject jsonObject = jsonArrayOperatorList_offline.optJSONObject(i);
                                    OperatorOfflineModel operatorOfflineModel = new OperatorOfflineModel(jsonObject.optString("name"),
                                            jsonObject.optString("code")
                                    );

                                    serviceRepository.insert(operatorOfflineModel);

                                 // ####################################################################

                                }

                            } else {
                                failure_case(resultdescription);
                            }
                        }
                    }

                }

                else   if (requestNo == 101) {


                    //  serverResponse=new JSONObject("{\"apiname\":\"PURCHASE\",\"response\":{\"responsects\":\"12/17/2021 02:34:49 PM\",\"amount\":\"3.0\",\"agenttransid\":\"id\",\"agentcode\":\"0982650605\",\"destination\":\"0982650605\",\"vendorcode\":\"LIBYANA\",\"productcode\":\"LMP3\",\"transid\":\"45629588\",\"resultcode\":\"0\",\"resultdescription\":\"Transaction Successful\",\"clienttype\":\"GPRS\",\"vat\":\"0\",\"recordcount\":\"2\",\"records\":[{\"pinno\":\"4300604796340\",\"serialno\":\"225432150060656\",\"expirydate\":\"12/31/2021 12:00:00 AM\"},{\"pinno\":\"1620489893332\",\"serialno\":\"225432150060655\",\"expirydate\":\"12/31/2021 12:00:00 AM\"}],\"qty\":\"2\",\"prewalletbalance\":\"62\",\"servicetax\":\"0.0\",\"tax\":\"0\",\"fee\":\"0.0\",\"walletbalance\":\"56\"}}");

                    if (serverResponse.has("response")) {

                        JSONObject jsonObject_response = serverResponse.getJSONObject("response");

                        if (jsonObject_response.has("resultcode")) {

                            String resultcode = jsonObject_response.getString("resultcode");
                            String resultdescription = jsonObject_response.getString("resultdescription");

                            if (resultcode.equalsIgnoreCase("0")) {

                                //  Toast.makeText(this, "Print Pin Online Sucesss", Toast.LENGTH_SHORT).show();
                                MyApplication.saveString("PIN_ONLINE",etPin.getText().toString(),MainActivityRet.this);
                                MyApplication.saveString("ONLINE_PINSALE_RET_API", serverResponse.toString(), MainActivityRet.this);

                                etPin.setText("");




                                 agenttransid_online = jsonObject_response.getString("agenttransid");
                                 vendorcode_online = jsonObject_response.getString("vendorcode");
                                 productcode_online = jsonObject_response.getString("productcode");
                                 amount_online = jsonObject_response.getString("amount");

                                JSONArray jsonArray_record =jsonObject_response.getJSONArray("records");
                                for(int b=0;b<jsonArray_record.length();b++)
                                {
                                    JSONObject jsonObjectb = jsonArray_record.getJSONObject(b);
                                    serialNumberString_online=jsonObjectb.getString("serialno");
                                    pinNumberString_online=jsonObjectb.getString("pinno");
                                }

                                transid_online = jsonObject_response.getString("transid");

                                String transactionDateTemp = jsonObject_response.getString("responsects");
                                String[] transactionDate_array=transactionDateTemp.split(" ");
                                transactionDate_online=transactionDate_array[0];
                                transactionTime_online=transactionDate_array[1]+" "+ transactionDate_array[2];;




                               // online_print_popup(getString(R.string.online_pin_sale_sucess));

                                print_pos_data_online();


                              /*  Intent intent = new Intent(MainActivityRet.this, OnlineRetRecycleViewPage.class);
                                startActivity(intent);*/


                            } else {

                                failure_case(resultdescription);

                            }
                        }
                    }

                }


                else   if (requestNo == 99) {

                  //  serverResponse=new JSONObject("{\"apiname\":\"ONLINESERVICE\",\"response\":{\"responsects\":\"01/11/2022 12:28:25 PM\",\"agentcode\":\"0982650605\",\"agentname\":\"Prashun\",\"vendorcode\":\"TAFANI\",\"transid\":\"45631408\",\"resultcode\":\"0\",\"resultdescription\":\"Transaction Successful\",\"clienttype\":\"POS\",\"recordcount\":\"6\",\"productDetails\":[{\"servicecode\":\"PURCHASE\",\"servicedesc\":\"PIN Purchase\",\"vendorcode\":\"MADAR\",\"vendordesc\":\"MADAR\",\"productcode\":\"AAMP3\",\"productdesc\":\"AAMP3\",\"denomination\":\"3.0\",\"producttype\":\"PINS\"},{\"servicecode\":\"PURCHASE\",\"servicedesc\":\"PIN Purchase\",\"vendorcode\":\"MADAR\",\"vendordesc\":\"MADAR\",\"productcode\":\"AAMP5\",\"productdesc\":\"AAMP5\",\"denomination\":\"5.0\",\"producttype\":\"PINS\"},{\"servicecode\":\"PURCHASE\",\"servicedesc\":\"PIN Purchase\",\"vendorcode\":\"MADAR\",\"vendordesc\":\"MADAR\",\"productcode\":\"AAMP10\",\"productdesc\":\"AAMP10\",\"denomination\":\"10.0\",\"producttype\":\"PINS\"},{\"servicecode\":\"PURCHASE\",\"servicedesc\":\"PIN Purchase\",\"vendorcode\":\"MADAR\",\"vendordesc\":\"MADAR\",\"productcode\":\"AAMP20\",\"productdesc\":\"AAMP20\",\"denomination\":\"20.0\",\"producttype\":\"PINS\"},{\"servicecode\":\"PURCHASE\",\"servicedesc\":\"PIN Purchase\",\"vendorcode\":\"MADAR\",\"vendordesc\":\"MADAR\",\"productcode\":\"AAMP40\",\"productdesc\":\"AAMP40\",\"denomination\":\"40.0\",\"producttype\":\"PINS\"},{\"servicecode\":\"PURCHASE\",\"servicedesc\":\"PIN Purchase\",\"vendorcode\":\"MADAR\",\"vendordesc\":\"MADAR\",\"productcode\":\"AAMP100\",\"productdesc\":\"AAMP100\",\"denomination\":\"100.0\",\"producttype\":\"PINS\"}],\"terminalid\":\"861256041366507\",\"operator\":\"MADAR\",\"service\":\"F\",\"settings\":{\"type\":\"GPRS\",\"refill\":\"N\",\"sync\":\"01/10/2022 01:41:04 PM\",\"userid\":\"11111\",\"password\":\"11111\",\"language\":\"English\",\"phonenumber\":\"9977011209\",\"dialnumber\":\"*99***#\",\"apn\":\"airtelgprs.com\",\"ip\":\"202.131.144.156\",\"port\":\"7101\"},\"operatorDetails\":[]}}");


                    if (serverResponse.has("response")) {

                        JSONObject jsonObject_response = serverResponse.getJSONObject("response");

                        if (jsonObject_response.has("resultcode")) {

                            String resultcode = jsonObject_response.getString("resultcode");
                            String resultdescription = jsonObject_response.getString("resultdescription");

                            if (resultcode.equalsIgnoreCase("0")) {


                                productList_online.clear();
                                denominationList_online.clear();


                                ProductModel ProductModel_temp = new ProductModel(getString(R.string.please_select_product));
                                productList_online.add(ProductModel_temp);

                                DenominationModel denominationModel_temp = new DenominationModel(getString(R.string.please_select_denomination));
                                denominationList_online.add(denominationModel_temp);


                                JSONArray productDetails_operator_details = jsonObject_response.optJSONArray("productDetails");


                                for (int i = 0; i < productDetails_operator_details.length(); i++) {
                                    JSONObject jsonObject = productDetails_operator_details.optJSONObject(i);

                                    ProductModel productModel = new ProductModel(
                                            jsonObject.optString("productcode"));


                                    productList_online.add(productModel);

                                    DenominationModel denominationModel = new DenominationModel(
                                            jsonObject.optString("denomination"));


                                    denominationList_online.add(denominationModel);
                                }



                                ProductAdapter productAdapter_temp = new ProductAdapter(this, productList_online);
                                spinner_productType_online.setAdapter(productAdapter_temp);


                            } else {

                                spinner_operator_online.setSelection(0);
                                spinner_denomination_online.setSelection(0);
                                spinner_productType_online.setSelection(0);
                                etPin.setText("");
                                failure_case(resultdescription);
                            }
                        }
                    }

                }

              else   if (requestNo == 50) {


                 //    serverResponse=new JSONObject("{\"apiname\":\"SYNCH\",\"response\":{\"responsects\":\"01/19/2022 09:59:15 AM\",\"agentcode\":\"0982650605\",\"agentname\":\"Prashun\",\"vendorcode\":\"TAFANI\",\"transid\":\"45632743\",\"resultcode\":\"0\",\"resultdescription\":\"Transaction Successful\",\"clienttype\":\"POS\",\"utilities\":\"ALKAFAA|KAFA10,KAFA10,10.0;KAFA25,KAFA25,25.0;KAFA50,KAFA50,50.0;KAFA100,KAFA100,100.0:CFNET|CFNET1,CFNET1,1.0:FNET|FNET5,FNET5,5.0;FNET10,FNET10,10.0;FNET20,FNET20,20.0;FNET100,FNET100,100.0:LIBYANA|LMP3,LMP3,3.0;LMP5,LMP5,5.0;LMP10,LMP10,10.0;LMP100,LMP100,100.0:LTT|LTT5,LTT5,5.0;LTT10,LTT10,10.0;LTT20,LTT20,20.0;LTT30,LTT30,30.0;LTT 100,LTT100,100.0:MADAR|AAMP3,AAMP3,3.0;AAMP5,AAMP5,5.0;AAMP10,AAMP10,10.0;AAMP20,AAMP20,20.0;AAMP40,AAMP40,40.0;AAMP100,AAMP100,100.0:NBDA|NBDA10,NBDA10,10.0;NBDA5,NBDA5,5.0:RDIT|RDIT5,RDIT5,5.0;RDIT10,RDIT10,10.0;RDIT20,RDIT20,20.0;RDIT35,RDIT35,35.0:THLC|HLC5,HLC5,5.0;HLC10,HLC10,10.0;HLC100,HLC100,100.0;HLC20,HLC20,20.0\"}}");

                    if (serverResponse.has("response")) {

                        JSONObject jsonObject_response = serverResponse.getJSONObject("response");

                        if (jsonObject_response.has("resultcode")) {

                            String resultcode = jsonObject_response.getString("resultcode");
                            String resultdescription = jsonObject_response.getString("resultdescription");

                            if (resultcode.equalsIgnoreCase("0")) {

                                Toast.makeText(this, getString(R.string.sync_sucess), Toast.LENGTH_SHORT).show();

                                String sync_utilities = jsonObject_response.getString("utilities");
                                MyApplication.saveString("sync_utilities",sync_utilities,MainActivityRet.this);






                            } else {

                                failure_case(resultdescription);

                            }
                        }
                    }

                }

              else   if (requestNo == 49) // Sync respone
                {

                    // serverResponse = new JSONObject("");

                    if (serverResponse.has("response")) {


                        JSONObject jsonObject_response = serverResponse.getJSONObject("response");

                        if (jsonObject_response.has("resultcode")) {


                            String resultcode = jsonObject_response.getString("resultcode");
                            String resultdescription = jsonObject_response.getString("resultdescription");

                            if (resultcode.equalsIgnoreCase("0")) {


                                serviceRepository.deletAllYStatusbulkDownload();

                                Toast.makeText(MainActivityRet.this, "Sync Successfully", Toast.LENGTH_SHORT).show();

                            }
                            else {

                                Toast.makeText(MainActivityRet.this, resultdescription, Toast.LENGTH_SHORT).show();

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

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivityRet.this);
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


    private void request_pinsalepurchase_online_volly(int requestNo) {

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
            jsonObject_request.put("agentcode", MyApplication.getSaveString("mobileNoString", MainActivityRet.this));

            if(etPin.getVisibility() == View.VISIBLE)
            {
                String mPin = etPin.getText().toString();
                String key = Md5.getMd5Hash(MyApplication.getSaveString("mobileNoString", MainActivityRet.this)+mPin).toUpperCase(Locale.ENGLISH);
                jsonObject_request.put("pin",key);
            }else{
                String mPin = MyApplication.getSaveString("PIN_ONLINE",MainActivityRet.this);//need to change
                String key = Md5.getMd5Hash(MyApplication.getSaveString("mobileNoString", MainActivityRet.this)+mPin).toUpperCase(Locale.ENGLISH);
                jsonObject_request.put("pin",key);
            }

            jsonObject_request.put("destination",MyApplication.getSaveString("mobileNoString", MainActivityRet.this));
         //   jsonObject_request.put("vendorcode",vendorCode);
            jsonObject_request.put("vendorcode","TAFANI");
            jsonObject_request.put("productcode",select_operatorCode);   //
            jsonObject_request.put("clienttype","POS");
            jsonObject_request.put("qty","1");
            jsonObject_request.put("agenttransid","56637461");

            json_main.put("request", jsonObject_request);

            new VollyRequestResponse(MainActivityRet.this, MainActivityRet.this, requestNo, base_url+login_pinsalepurchase_apinname, json_main.toString());

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            CommonUtility.hideProgressDialog(MainActivityRet.this);

            e.printStackTrace();
        }


}

    private void request_getOnlineService_volly(int requestNo) {



/*        {
            "apiname": "ONLINESERVICE",
                "request": {
            "agentcode": "0982650605",
                    "pin":"1893274393509319",
                    "terminalid":"0982650606",
                    "vendorcode":"TAFANI",
                    "clienttype":"POS",
                    "agenttransid":"2086000814",
                    "comments":"SERVICE API",
                    "operator":"MADAR"
        }
        }*/

        try {

            JSONObject json_main = new JSONObject();

            json_main.put("apiname", "ONLINESERVICE");

            JSONObject jsonObject_request = new JSONObject();
            jsonObject_request.put("agentcode", MyApplication.getSaveString("mobileNoString", MainActivityRet.this));
            jsonObject_request.put("pin",MyApplication.getSaveString("activationCodeString", MainActivityRet.this));
            jsonObject_request.put("terminalid",MyApplication.getSaveString("terminalIdString", MainActivityRet.this));
            jsonObject_request.put("vendorcode","TAFANI");
            jsonObject_request.put("clienttype","POS");
            jsonObject_request.put("agenttransid",MyApplication.getSaveString("terminalIdString", MainActivityRet.this));
            jsonObject_request.put("comments","SERVICE API");
            jsonObject_request.put("operator",select_vendorCode_online);

            json_main.put("request", jsonObject_request);

            new VollyRequestResponse(MainActivityRet.this, MainActivityRet.this, requestNo, base_url+ret_getOnlineService_apiname, json_main.toString());

        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
            CommonUtility.hideProgressDialog(MainActivityRet.this);

            e.printStackTrace();
        }


    }




    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {



        switch (adapterView.getId()) {

            case R.id.spinner_operator_offline:  // first spinner
            {
                try {



                    if(i==0)
                    {
                        select_operatorName = operator_offline.get(i).getOperatorName();
                        select_operatorCode = operator_offline.get(i).getOperatorCode();


                       //   Toast.makeText(MainActivityRet.this, " -----selected--name--"+select_operatorName+"-----selected---Code----"+select_operatorCode,Toast.LENGTH_SHORT).show();

                        spinner_operator_offline.setEnabled(true);
                        spinner_product_offline.setEnabled(false);
                        spinner_denomination_offline.setEnabled(false);

                    }
                    else {


                        select_operatorName = operator_offline.get(i).getOperatorName();
                        select_operatorCode = operator_offline.get(i).getOperatorCode();
                        String  vendorcode_offline_select = operator_offline.get(i).getOperatorCode();

                     //   Toast.makeText(MainActivityRet.this, " -----selected--name--"+select_operatorName+"-----selected---Code----"+select_operatorCode,Toast.LENGTH_SHORT).show();

                       String select_operatorName_offline = operator_offline.get(i).getOperatorName();

                        MyApplication.saveString("select_operatorName_offline",select_operatorName_offline,MainActivityRet.this);


                        MyApplication.saveString("vendorcode_offline_print",vendorcode_offline_select,MainActivityRet.this);
                     //     Toast.makeText(MainActivityRet.this, " -----selected--name--"+vendorcode_offline_select+"-----selected---Code----"+select_operatorCode,Toast.LENGTH_SHORT).show();



                        spinner_operator_offline.setEnabled(true);
                        spinner_product_offline.setEnabled(true);
                        spinner_denomination_offline.setEnabled(true);

                      //  Toast.makeText(MainActivityRet.this, " --- select  **************--"+select_operatorName,Toast.LENGTH_SHORT).show();

                        productList_offline_filter.clear();
                        //  denominationList_filter_pinsale.clear();

                        ServiceProductDownloadOfflineModel serviceProductDownloadOfflineModel = new ServiceProductDownloadOfflineModel(getString(R.string.please_select_product),getString(R.string.please_select_vendor_type),getString(R.string.please_select_denomination));
                        productList_offline_filter.add(serviceProductDownloadOfflineModel);




                        for (int j = 0; j < productList_offline.size(); j++) {

                            if (select_operatorCode.equalsIgnoreCase(productList_offline.get(j).getVendorcode())) {

                                //    JSONObject jsonObject = jasonArray_productDetails_service_api.optJSONObject(j);

                                ServiceProductDownloadOfflineModel productModelOrderPin = new ServiceProductDownloadOfflineModel(
                                        productList_offline.get(j).getProductcode(),
                                        productList_offline.get(j).getVendorcode(),productList_offline.get(j).getDenomination()
                                );

                                productList_offline_filter.add(productModelOrderPin);



                                //   denominationList_filter_pinsale.add(productModelOrderPin);
                            }
                            else {

                            }

                        }


                        ProductAdapterOffline productAdapterOffline = new ProductAdapterOffline(this, productList_offline_filter);
                        spinner_product_offline.setAdapter(productAdapterOffline);




                        DenominationAdapterOffline denominationAdapterOffline = new DenominationAdapterOffline(MainActivityRet.this, productList_offline_filter);
                        spinner_denomination_offline.setAdapter(denominationAdapterOffline);




                    }
                }
                catch (Exception e) {

                    Toast.makeText(MainActivityRet.this, e.toString(), Toast.LENGTH_SHORT).show();

                    e.printStackTrace();
                }
            }
            break;

            case R.id.spinner_product_offline:
            {


                if (i == 0) {


                    select_productCode_offline = productList_offline_filter.get(i).getProductcode();
                    select_vendorCode_offline = productList_offline_filter.get(i).getVendorcode();

                  //  Toast.makeText(MainActivityRet.this, " -----Selected--Product--offline----" + select_productCode_offline, Toast.LENGTH_SHORT).show();

                    spinner_operator_offline.setEnabled(true);
                    spinner_product_offline.setEnabled(true);
                    spinner_denomination_offline.setEnabled(false);
                }

                else {


                    select_productCode_offline = productList_offline_filter.get(i).getProductcode();
                    select_vendorCode_offline = productList_offline_filter.get(i).getVendorcode();


                    MyApplication.saveString("select_productCode_offline",select_productCode_offline,MainActivityRet.this);

                 //   Toast.makeText(MainActivityRet.this, " -----Selected--Product--offline----" + select_productCode_offline, Toast.LENGTH_SHORT).show();

                    spinner_operator_offline.setEnabled(true);
                    spinner_product_offline.setEnabled(true);
                    spinner_denomination_offline.setEnabled(true);

                  //  spinner_product_offline.setSelection(i);
                    spinner_denomination_offline.setSelection(i);


                    //    Toast.makeText(MainActivityRet.this, " -----Selected--Product--offline----" + select_productCode, Toast.LENGTH_SHORT).show();
                 //   Toast.makeText(MainActivityRet.this, " -----Vendor select Offline ------  " + select_vendorcode, Toast.LENGTH_SHORT).show();

                    //  spinner_denomination_orderpin.setSelection(i);
                }

            }
            break;

            case R.id.spinner_denomination_offline:
            {

                if(i==0)
                {
                  //  Toast.makeText(MainActivityRet.this, " -----selected--denomination-offline-"+select_denomination,Toast.LENGTH_SHORT).show();


                    spinner_operator_offline.setEnabled(true);
                    spinner_product_offline.setEnabled(true);
                    spinner_denomination_offline.setEnabled(false);

                }

                else
                {

                    select_denomination_offline = productList_offline_filter.get(i).getDenomination();


                   arraylist_details_pin =  serviceRepository.getpin_download(select_productCode_offline,select_denomination_offline);


                    if(arraylist_details_pin.size()>0)
                       {
                           Log.e("---arraylist_deta--",""+arraylist_details_pin.toString());
                       }
                       else
                       {
                           Toast.makeText(MainActivityRet.this, " "+"No pin available",Toast.LENGTH_SHORT).show();
                       }

                    System.out.println(arraylist_details_pin);
                    //   Toast.makeText(MainActivityRet.this, " -----selected--denomination-offline-"+select_denomination,Toast.LENGTH_SHORT).show();

                }
            }
            break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


    private BroadcastReceiver br = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateGUI(intent); // or whatever method used to update your GUI fields
        }
    };



    @Override
    public void onResume() {
        super.onResume();
        registerReceiver(br, new IntentFilter(BroadcastService.COUNTDOWN_BR));
        Log.i(TAG, "Registered broacast receiver"+MyApplication.getSerialNumber());
        if( MyApplication.isTimerStopped){
            etPin.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        unregisterReceiver(br);

        Log.i(TAG, "Unregistered broacast receiver");
    }

    @Override
    public void onStop() {
        try {
            unregisterReceiver(br);
        } catch (Exception e) {
            // Receiver was probably already stopped in onPause()
        }
        super.onStop();
    }
    String TAG="Count Down";
    @Override
    public void onDestroy() {
        stopService(new Intent(this, BroadcastService.class));
        Log.i(TAG, "Stopped service");
        super.onDestroy();
    }

    private void updateGUI(Intent intent) {
        if (intent.getExtras() != null) {
            long millisUntilFinished = intent.getLongExtra("countdown", 0);
            Log.i(TAG, "Countdown seconds remaining: " + millisUntilFinished / 1000 +SunmiPrintHelper.getInstance().getDeviceModel());
            if((millisUntilFinished / 1000)==1){
                MyApplication.isTimerStopped=true;
                MyApplication.isSrviceStarted=false;
                etPin.setVisibility(View.VISIBLE);
            }else{
                MyApplication.isTimerStopped=false;

                etPin.setVisibility(View.INVISIBLE);
            }
        }
    }

}