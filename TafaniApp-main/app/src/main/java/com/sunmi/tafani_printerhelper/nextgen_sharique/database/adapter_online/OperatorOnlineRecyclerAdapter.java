package com.sunmi.tafani_printerhelper.nextgen_sharique.database.adapter_online;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.sunmi.tafani_printerhelper.R;
import com.sunmi.tafani_printerhelper.nextgen_sharique.database.model_online.OperatorModal;
import com.sunmi.tafani_printerhelper.nextgen_sharique.database.offline.modal_offline.ServiceOperatorDownloadOfflineModel;

import java.util.ArrayList;
import java.util.List;

public class OperatorOnlineRecyclerAdapter extends RecyclerView.Adapter<OperatorOnlineRecyclerAdapter.ViewHolder>{
    private Context context;
    private List<OperatorModal> operatorModals = new ArrayList<>();

    public OperatorOnlineRecyclerAdapter(Context context, List<OperatorModal> operatorModalList) {
        this.context = context;
        this.operatorModals = operatorModalList;

    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.operator_list_rowdesign, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        OperatorModal feeDetailModel = operatorModals.get(position);

        holder.operatornameText.setText(feeDetailModel.getVendorcode());

    }


    @Override
    public int getItemCount() {
        return operatorModals.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView operatornameText;
        public ViewHolder(View itemView) {
            super(itemView);
            operatornameText = itemView.findViewById(R.id.operatornameText);
        }
    }
}