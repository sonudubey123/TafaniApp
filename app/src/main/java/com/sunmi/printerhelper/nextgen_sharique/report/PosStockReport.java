package com.sunmi.printerhelper.nextgen_sharique.report;


import static com.sunmi.printerhelper.nextgen_sharique.voly_url.Url.base_url;
import static com.sunmi.printerhelper.nextgen_sharique.voly_url.Url.login_ret_reprint_apinname;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import tafani.sunmi.printer.R;
import com.sunmi.printerhelper.local_set.LocalSetLanguage;
import com.sunmi.printerhelper.nextgen_sharique.application_sharepreference.MyApplication;
import com.sunmi.printerhelper.nextgen_sharique.database.offline.ServiceRepository;
import com.sunmi.printerhelper.nextgen_sharique.database.offline.modal_offline.BulkDownloadOfflineModel;
import com.sunmi.printerhelper.nextgen_sharique.database.offline.modal_offline.PosstockODownloadOfflineModel;
import com.sunmi.printerhelper.nextgen_sharique.database.offline.modal_offline.ServiceOperatorDownloadOfflineModel;
import com.sunmi.printerhelper.nextgen_sharique.database.offline.modal_offline.ServiceProductDownloadOfflineModel;
import com.sunmi.printerhelper.nextgen_sharique.interf.InterHttpServerResponse;
import com.sunmi.printerhelper.nextgen_sharique.loading_internet.CommonUtility;
import com.sunmi.printerhelper.nextgen_sharique.main_activity.MainActivityRet;
import com.sunmi.printerhelper.nextgen_sharique.order_pin.OrderPinActivity;
import com.sunmi.printerhelper.nextgen_sharique.order_pin.adapter_orderpin.OperatorAdapterOrderPin;
import com.sunmi.printerhelper.nextgen_sharique.order_pin.modal_orderpin.OperatorModalOrderPin;
import com.sunmi.printerhelper.nextgen_sharique.voly_url.VollyRequestResponse;
import com.sunmi.printerhelper.utils.BluetoothUtil;
import com.sunmi.printerhelper.utils.SunmiPrintHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PosStockReport extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    private List<ServiceOperatorDownloadOfflineModel> operator_offline = new ArrayList<>();
    private List<ServiceProductDownloadOfflineModel> productList_offline = new ArrayList<>();
    private List<ServiceProductDownloadOfflineModel> productList_offline_filter = new ArrayList<>();
    private List<PosstockODownloadOfflineModel> posStock_o_offline = new ArrayList<>();
    private List<BulkDownloadOfflineModel> bulkDownload_record_List = new ArrayList<>();
    private List<ModalPosStockReport> arrayList_report = new ArrayList<>();

    Button close_button,print_button;

    RecyclerView recycler_view;

    LinearLayout ll_secondPage;
    RelativeLayout rr_firstPage;

    private Dialog dialog;

    private boolean isBold, isUnderLine;
    private String testFont;
    ServiceRepository serviceRepository;


    String operatorFromServer="",transactionStatus="",transid="",transactionDate="",transactionTime="",pinNumberFromServer="",serialNumberFromServer="";

    private ArrayList<OperatorModalOrderPin> operator_pinsale = new ArrayList<>();
    Button imageButton1;
    EditText edittext_serialno;
    String serialnoStringh = "";

    String dateOfBirthString;
    MyApplication myApplication;

    String languageToUse = "";
    Spinner spinner_operator_orderPin;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        try {
            myApplication = (MyApplication) getApplicationContext();
            languageToUse = myApplication.getmSharedPreferences().getString("languageToUse", "");

            if (languageToUse.trim().length() == 0) {

                languageToUse = "ar";
                LocalSetLanguage.LocalSet(languageToUse, PosStockReport.this);
            }
            else
            {
                LocalSetLanguage.LocalSet(languageToUse, PosStockReport.this);
            }

            setContentView(R.layout.posstock_report);



        } catch (Exception e) {
            Toast.makeText(PosStockReport.this, e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        try{
            edittext_serialno = (EditText) findViewById(R.id.edittext_serialno);
            recycler_view = (RecyclerView) findViewById(R.id.recycler_view);
            imageButton1 = (Button) findViewById(R.id.imageButton1);
            imageButton1.setOnClickListener(this);

            close_button = (Button) findViewById(R.id.close_button);
            print_button = (Button) findViewById(R.id.print_button);

            close_button.setOnClickListener(this);
            print_button.setOnClickListener(this);

            spinner_operator_orderPin = findViewById(R.id.spinner_operator_orderPin);
            spinner_operator_orderPin.setOnItemSelectedListener(this);


            rr_firstPage = (RelativeLayout) findViewById(R.id.rr_firstPage);
            ll_secondPage = (LinearLayout) findViewById(R.id.ll_secondPage);

            rr_firstPage.setVisibility(View.VISIBLE);
            ll_secondPage.setVisibility(View.GONE);


            if (languageToUse.equalsIgnoreCase("ar")) {
                // imageButton1.setBackgroundResource(R.drawable.conform);
                //  edittext_serialno.setGravity(Gravity.RIGHT);

            } else {
                //  imageButton1.setBackgroundResource(R.drawable.confirmbutton);
                //  edittext_serialno.setGravity(Gravity.LEFT);

            }

            serviceRepository = new ServiceRepository(PosStockReport.this);

            operator_offline = serviceRepository.getList_service_operator_download_offline();
            productList_offline = serviceRepository.getList_service_product_download_offline();
            productList_offline_filter = serviceRepository.getList_service_product_download_offline();
            posStock_o_offline = serviceRepository.getList_posstock_O_download();


            bulkDownload_record_List = serviceRepository.getList_bulkdownlaod_offline();

            System.out.println(bulkDownload_record_List);
            ArrayList<String> OperatorList=new ArrayList<>();
            OperatorList.clear();
            OperatorModalOrderPin operatorModal_temp = new OperatorModalOrderPin(getString(R.string.please_select_operator),getString(R.string.please_select_operator));
            operator_pinsale.add(operatorModal_temp);
            for(int i=0;i<bulkDownload_record_List.size();i++){
                if (i == 0) {
                    operator_pinsale.add(new OperatorModalOrderPin(
                            bulkDownload_record_List.get(i).getVendorcode(),
                            bulkDownload_record_List.get(i).getVendorcode()
                    ));

                } else {

                    int hasdata = hasValueOp(operator_pinsale, "productcode", bulkDownload_record_List.get(i).getVendorcode());

                    if (hasdata != -1) {

                     /*   operator_pinsale.set(hasdata,
                                new OperatorModalOrderPin(
                                        bulkDownload_record_List.get(i).getVendorcode(),
                                        bulkDownload_record_List.get(i).getVendorcode()
                                ));*/

                    } else {
                        operator_pinsale.add(new OperatorModalOrderPin(
                                bulkDownload_record_List.get(i).getVendorcode(),
                                bulkDownload_record_List.get(i).getVendorcode()
                        ));
                    }

                }

            }

            if(operator_pinsale.size()==1){
                Toast.makeText(PosStockReport.this,getResources().getString(R.string.no_pin_available_in_stock),Toast.LENGTH_LONG).show();
            }

            System.out.println("Operator Code  "+OperatorList.toString());


            OperatorAdapterOrderPin operatorAdapter = new OperatorAdapterOrderPin(this, operator_pinsale);
            spinner_operator_orderPin.setAdapter(operatorAdapter);
            int count=0;
            arrayList_report.clear();

            JSONArray jsonArray = new JSONArray();

        }catch(Exception e){

        }
    }

    public int hasValue(List<ModalPosStockReport> json, String key, String value) {

        int t=-1;
        for(int i = 0; i < json.size(); i++) {  // iterate through the JsonArray

            if(json.get(i).getProductCode().equals(value))
            {
                if(t==-1)
                {
                    t=i;

                }

                //return i;
            }
        }
        return t;
    }

    public int hasValueOp(List<OperatorModalOrderPin> json, String key, String value) {

        int t=-1;
        for(int i = 0; i < json.size(); i++) {  // iterate through the JsonArray

            if(json.get(i).getCode().equals(value))
            {
                if(t==-1)
                {
                    t=i;

                }

                //return i;
            }
        }
        return t;
    }

    protected void print_pos_data() {

        if (!BluetoothUtil.isBlueToothPrinter) {

            Bitmap bitmap = null;
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inTargetDensity = 200;
            options.inDensity = 200;


            bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ani_logo_jp, options);

            SunmiPrintHelper.getInstance().printBitmap_nextgen(bitmap,1);



            if (languageToUse.equalsIgnoreCase("en")) {

                SunmiPrintHelper.getInstance().printText_nextgen("            "+getString(R.string.stock_report)+"\n\n", 30, isBold, isUnderLine, testFont, 0);
                String currentDateTime=current_sell_dateTime(); // 26/01/2022 14:55:46

                String[]  currentDateTime_temp = currentDateTime.split(" ");
                String currentDate_offline = currentDateTime_temp[0];
                String currentTime_offline = currentDateTime_temp[1];

                SunmiPrintHelper.getInstance().printText_nextgen(  currentDate_offline, 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen("                    " + currentTime_offline + "\n", 20, isBold, isUnderLine, testFont, 2);




                SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.terminalidId_print_colon), 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen("           " + MyApplication.getSaveString("terminalIdString", PosStockReport.this) + "\n", 20, isBold, isUnderLine, testFont, 2);

               /* SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.retailer_print_colon), 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen("                   " + MyApplication.getSaveString("mobileNoString", PosStockReport.this) + "\n", 20, isBold, isUnderLine, testFont, 2);
*/
                SunmiPrintHelper.getInstance().printText_nextgen("---------------" + "\n", 50, isBold, isUnderLine, testFont, 0);


                for(int i=0;i<arrayList_report.size();i++)
                {
                    SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.product_code) +" "+ arrayList_report.get(i).productCode+" = " +arrayList_report.get(i).quent+"\n",  20, isBold, isUnderLine, testFont, 0);
                   // SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.quantity) +" "+ arrayList_report.get(i).quent+"\n\n",  20, isBold, isUnderLine, testFont, 0);
                }

                SunmiPrintHelper.getInstance().printText_nextgen("---------------" + "\n", 50, isBold, isUnderLine, testFont, 0);


                //    SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.footer_heder_print) + "\n\n", 20, isBold, isUnderLine, testFont, 0);

                SunmiPrintHelper.getInstance().feedPaper();


            }
            else

            {

                SunmiPrintHelper.getInstance().printText_nextgen("           "+getString(R.string.stock_report)+"\n\n", 30, isBold, isUnderLine, testFont, 2);



                String currentDateTime=current_sell_dateTime(); // 26/01/2022 14:55:46

                String[]  currentDateTime_temp = currentDateTime.split(" ");
                String currentDate_offline = currentDateTime_temp[0];
                String currentTime_offline = currentDateTime_temp[1];

                SunmiPrintHelper.getInstance().printText_nextgen(currentDate_offline, 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen("                    " + currentTime_offline + "\n", 20, isBold, isUnderLine, testFont, 2);

                SunmiPrintHelper.getInstance().printText_nextgen(  MyApplication.getSaveString("mobileNoString",PosStockReport.this)+"              ", 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen( getString(R.string.terminal_id) +"      " + "\n", 20, isBold, isUnderLine, testFont, 2);

              /*  SunmiPrintHelper.getInstance().printText_nextgen(  MyApplication.getSaveString("terminalIdString", PosStockReport.this)+"              ", 20, isBold, isUnderLine, testFont, 0);
                SunmiPrintHelper.getInstance().printText_nextgen( getString(R.string.retailer)+"    " + "\n", 20, isBold, isUnderLine, testFont, 2);
*/

                SunmiPrintHelper.getInstance().printText_nextgen("---------------" + "\n", 50, isBold, isUnderLine, testFont, 0);


                for(int i=0;i<arrayList_report.size();i++)
                {

                   // SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.product_code) +" "+ arrayList_report.get(i).quent+" = "+ arrayList_report.get(i).productCode+"\n",  20, isBold, isUnderLine, testFont, 2);
                    SunmiPrintHelper.getInstance().printText_nextgen( arrayList_report.get(i).quent+" = "+ arrayList_report.get(i).productCode+" الفئة "+"\n",  20, isBold, isUnderLine, testFont, 2);
                  //  SunmiPrintHelper.getInstance().printText_nextgen( arrayList_report.get(i).productCode+" = "+ arrayList_report.get(i).quent+"الفئة"+"\n",  20, isBold, isUnderLine, testFont, 2);
                }

                SunmiPrintHelper.getInstance().printText_nextgen("---------------" + "\n", 50, isBold, isUnderLine, testFont, 0);


                //    SunmiPrintHelper.getInstance().printText_nextgen(getString(R.string.footer_heder_print) + "\n\n", 20, isBold, isUnderLine, testFont, 0);

                SunmiPrintHelper.getInstance().feedPaper();


            }



        } else {
            // SunmiPrintHelper.getInstance().printText(getString(R.string.account_balance) + " : " + amountFromServer + " LYD", 30, true, isUnderLine, testFont);
        }

    }





    protected void successResponse() {

        TextView textresponse, textheading;


        dialog = new Dialog(PosStockReport.this);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
        //dialog.getWindow().setBackgroundDrawable(d);
        dialog.setContentView(R.layout.dialogbox);
        textheading = (TextView) ((dialog)).findViewById(R.id.res_heading);
        textheading.setText(transactionStatus);
        textresponse = (TextView) ((dialog)).findViewById(R.id.responsestring);

        textresponse.setText(getString(R.string.transaction_type)+ " : " + getString(R.string.reprint_capital_print) +"\n"+
                getString(R.string.operator) + " : " + operatorFromServer +"\n" +
                getString(R.string.transaction_id)+" : "+transid);


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

                PosStockReport.this.finish();

            }
        });

        Button printButton=(Button) ((dialog)).findViewById(R.id.printButton);
        printButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                print_pos_data();

                dialog.dismiss();
                edittext_serialno.setText("");

               }
        });

        dialog.show();
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()) {


            case R.id.imageButton1: {

                try {

                    if(spinner_operator_orderPin.getSelectedItemPosition()==0)
                    {
                        Toast.makeText(PosStockReport.this, R.string.please_select_operator, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    if (arrayList_report.size() > 0) {


                        rr_firstPage.setVisibility(View.GONE);
                        ll_secondPage.setVisibility(View.VISIBLE);

                        recycler_view.setLayoutManager(new LinearLayoutManager(PosStockReport.this));
                        AdapterPosStock recyclerViewAdapter = new AdapterPosStock(PosStockReport.this, arrayList_report);
                        recycler_view.setAdapter(recyclerViewAdapter);

                    } else {

                        Toast.makeText(PosStockReport.this, getString(R.string.record_not_found), Toast.LENGTH_SHORT).show();

                        rr_firstPage.setVisibility(View.GONE);
                        ll_secondPage.setVisibility(View.VISIBLE);
                    }


                } catch (Exception e) {

                    CommonUtility.hideProgressDialog(PosStockReport.this);

                    Toast.makeText(PosStockReport.this, e.toString(), Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
            break;

            case R.id.close_button:
            {
                rr_firstPage.setVisibility(View.VISIBLE);
                ll_secondPage.setVisibility(View.GONE);
            }

            break;

            case R.id.print_button:
            {
                 print_pos_data();
            }
            break;
        }
    }



    private boolean checkValidation() {

        serialnoStringh = edittext_serialno.getText().toString().trim();

        if (serialnoStringh.isEmpty()) {
            Toast.makeText(PosStockReport.this, getResources().getString(R.string.serial_no), Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }





    private void showAlertDialog_sh(String msg) {

        AlertDialog.Builder builder = new AlertDialog.Builder(PosStockReport.this);
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

    String select_operatorName;
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int l, long id) {
        switch (parent.getId()) {

            case R.id.spinner_operator_orderPin:  // first spinner
            {
                try {


                     select_operatorName = operator_pinsale.get(l).getName();
                    String select_operatorCode = operator_pinsale.get(l).getCode();
                    arrayList_report.clear();
                    for(int i=0;i<bulkDownload_record_List.size();i++) {


                        if(bulkDownload_record_List.get(i).status_check.equalsIgnoreCase("Y"))
                        {



                        }

                        else {
                            String productcode = bulkDownload_record_List.get(i).productcode;
                            System.out.println("-------*****--productcode---" + productcode);

                            if(bulkDownload_record_List.get(i).getVendorcode().equalsIgnoreCase(select_operatorName)) {
                                if (i == 0) {
                                    arrayList_report.add(new ModalPosStockReport(bulkDownload_record_List.get(i).productcode, 1));

                                } else {

                                    int hasdata = hasValue(arrayList_report, "productcode", bulkDownload_record_List.get(i).productcode);

                                    if (hasdata != -1) {

                                        arrayList_report.set(hasdata, new ModalPosStockReport(bulkDownload_record_List.get(i).productcode, arrayList_report.get(hasdata).getQuent() + 1));

                                    } else {
                                        arrayList_report.add(new ModalPosStockReport(bulkDownload_record_List.get(i).productcode, 1));
                                    }

                                }
                            }
                        }


                    }






                    System.out.println(arrayList_report);
                } catch (Exception e) {

                }
            }

        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public static String current_sell_dateTime() {

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date dateobj = new Date();

        String  currentDateTime=df.format(dateobj);

        //  currentDateTime ="31/12/21 20:08:33";

        return currentDateTime;
    }
}
