package com.sunmi.printerhelper.nextgen_sharique.transaction_history.distributor;

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


public class TransactionHistoryDisAdapter extends RecyclerView.Adapter<TransactionHistoryDisAdapter.ViewHolder> {
    String finalDate;
    private Context context;

    ArrayList<TransactionHistoryRetModal> arrayList_modalUserData;

    public TransactionHistoryDisAdapter(Context context, ArrayList<TransactionHistoryRetModal> arrayList_modalUserData) {
        this.context = context;
        this.arrayList_modalUserData = arrayList_modalUserData;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {

        viewHolder.transaction_id.setText(arrayList_modalUserData.get(i).getTransid());
        viewHolder.transaction_date.setText(arrayList_modalUserData.get(i).getResponsects());
        viewHolder.transaction_amount.setText(arrayList_modalUserData.get(i).getAmount());
        viewHolder.transaction_type.setText(arrayList_modalUserData.get(i).getTranstype());
        viewHolder.wallet_balance.setText(arrayList_modalUserData.get(i).getWalletbalance());
    }

    @Override
    public int getItemCount() {
        return arrayList_modalUserData.size();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_transaction_history_dis, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView transaction_id, transaction_date,transaction_type,transaction_amount,wallet_balance;
        Button action_click;

        public ViewHolder(View itemView) {
            super(itemView);

            transaction_id = (TextView) itemView.findViewById(R.id.transaction_id);
            transaction_date = (TextView) itemView.findViewById(R.id.transaction_date);
            transaction_amount = (TextView) itemView.findViewById(R.id.transaction_amount);
            transaction_type = (TextView) itemView.findViewById(R.id.transaction_type);
            wallet_balance = (TextView) itemView.findViewById(R.id.wallet_balance);



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
