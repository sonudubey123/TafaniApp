package com.sunmi.printerhelper.nextgen_sharique.report;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import tafani.sunmi.printer.R;
import com.sunmi.printerhelper.nextgen_sharique.modal.TransactionHistoryRetModal;

import java.util.ArrayList;
import java.util.List;


public class AdapterPosStock extends RecyclerView.Adapter<AdapterPosStock.ViewHolder> {
    String finalDate;
    private Context context;

    List<ModalPosStockReport> arrayList_report;

    public AdapterPosStock(Context context, List<ModalPosStockReport> arrayList_modalUserData) {
        this.context = context;
        this.arrayList_report = arrayList_modalUserData;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.product_code.setText(arrayList_report.get(i).getProductCode());

        int  quentity = arrayList_report.get(i).getQuent();
        String quentityStr=Integer.toString(quentity);

        viewHolder.quentity.setText(quentityStr);

    }

    @Override
    public int getItemCount() {
        return arrayList_report.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_posstock_report, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView product_code, quentity;

        public ViewHolder(View itemView) {
            super(itemView);

            product_code = (TextView) itemView.findViewById(R.id.product_code);
            quentity = (TextView) itemView.findViewById(R.id.quentity);

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
