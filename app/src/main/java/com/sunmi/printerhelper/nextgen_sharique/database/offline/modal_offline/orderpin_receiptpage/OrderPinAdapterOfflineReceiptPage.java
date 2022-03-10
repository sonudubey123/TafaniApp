package com.sunmi.printerhelper.nextgen_sharique.database.offline.modal_offline.orderpin_receiptpage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import tafani.sunmi.printer.R;
import com.sunmi.printerhelper.nextgen_sharique.ase_encription.AESEncryption;
import com.sunmi.printerhelper.nextgen_sharique.order_pin.adapter_orderpin.OrderPinModalReceiptPage;

import java.util.ArrayList;


public class OrderPinAdapterOfflineReceiptPage extends RecyclerView.Adapter<OrderPinAdapterOfflineReceiptPage.ViewHolder> {
    String finalDate;
    private Context context;

    ArrayList<OrderPinModalOfflineReceiptPage> arrayList_modalUserData;

    public OrderPinAdapterOfflineReceiptPage(Context context, ArrayList<OrderPinModalOfflineReceiptPage> arrayList_modalUserData) {
        this.context = context;
        this.arrayList_modalUserData = arrayList_modalUserData;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.order_pin_textview.setText(arrayList_modalUserData.get(i).getPinNumber_encript());


        try {

           /* String encryptionData = arrayList_modalUserData.get(i).getSerialNo_key();

            AESEncryption aseEncryption2 = new AESEncryption();
            String key_2 = AESEncryption.SHA256("#@123#@123", 32); //32 bytes = 256 bit
            String iv_2 = "e816e2a32196ef0b"; //16 bytes = 128 bit
            String decrrptData = aseEncryption2.decrypt(encryptionData, key_2, iv_2);

            viewHolder.serial_Number_textview.setText(decrrptData);*/

            viewHolder.serial_Number_textview.setText(arrayList_modalUserData.get(i).getSerialNo_key());


        }
        catch (Exception e)
        {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return arrayList_modalUserData.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_orderpin_recepitpage_ret, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView order_pin_textview, serial_Number_textview;
        Button action_click;

        public ViewHolder(View itemView) {
            super(itemView);

            order_pin_textview = (TextView) itemView.findViewById(R.id.order_pin_textview);
            serial_Number_textview = (TextView) itemView.findViewById(R.id.serial_Number_textview);



        }
    }

//    public void filter(String charText) {
//        charText = charText.toLowerCase(Locale.getDefault());
//        arrayList_modalUserData.clear();
//        if (charText.length() == 0) {
//            arrayList_modalUserData.addAll(arrayListTemp);
//        } else {
//            for (UserDetail wp : arrayListTemp) {
//                if (wp.getTransTypeName().toLowerCase(Locale.getDefault()).contains(charText)) {
//                    arrayList_modalUserData.add(wp);
//                }
//            }
//        }
//        notifyDataSetChanged();
//    }
}
