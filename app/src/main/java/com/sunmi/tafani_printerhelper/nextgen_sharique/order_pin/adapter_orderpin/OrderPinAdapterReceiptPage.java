package com.sunmi.tafani_printerhelper.nextgen_sharique.order_pin.adapter_orderpin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sunmi.tafani_printerhelper.R;

import java.util.ArrayList;


public class OrderPinAdapterReceiptPage extends RecyclerView.Adapter<OrderPinAdapterReceiptPage.ViewHolder> {
    String finalDate;
    private Context context;

    ArrayList<OrderPinModalReceiptPage> arrayList_modalUserData;

    public OrderPinAdapterReceiptPage(Context context, ArrayList<OrderPinModalReceiptPage> arrayList_modalUserData) {
        this.context = context;
        this.arrayList_modalUserData = arrayList_modalUserData;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.order_pin_textview.setText(arrayList_modalUserData.get(i).getPinNumber_encript());
        viewHolder.serial_Number_textview.setText(arrayList_modalUserData.get(i).getSerialNo_key());

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
