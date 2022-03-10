package com.sunmi.printerhelper.nextgen_sharique.database.online;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sunmi.printerhelper.nextgen_sharique.modal.OnlineListModal;
import com.sunmi.printerhelper.nextgen_sharique.modal.TransactionHistoryRetModal;

import java.util.ArrayList;

import tafani.sunmi.printer.R;


public class OnlineListRetAdapter extends RecyclerView.Adapter<OnlineListRetAdapter.ViewHolder> {
    String finalDate;
    private Context context;

    ArrayList<OnlineListModal> arrayList_onlineList;

    public OnlineListRetAdapter(Context context, ArrayList<OnlineListModal> arrayList_onlineList) {
        this.context = context;
        this.arrayList_onlineList = arrayList_onlineList;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.pin_number.setText(arrayList_onlineList.get(i).getPinno());
        viewHolder.serial_number.setText(arrayList_onlineList.get(i).getSerialno());
        viewHolder.expiry_date.setText(arrayList_onlineList.get(i).getExpirydate());
    }

    @Override
    public int getItemCount() {
        return arrayList_onlineList.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_online_ret, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView pin_number,serial_number,expiry_date;
        Button action_click;

        public ViewHolder(View itemView) {
            super(itemView);

            pin_number = (TextView) itemView.findViewById(R.id.pin_number);
            serial_number = (TextView) itemView.findViewById(R.id.serial_number);
            expiry_date = (TextView) itemView.findViewById(R.id.expiry_date);

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
