package com.sunmi.tafani_printerhelper.nextgen_sharique.database.offline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sunmi.tafani_printerhelper.R;
import com.sunmi.tafani_printerhelper.nextgen_sharique.database.offline.modal_offline.ServiceProductDownloadOfflineModel;

import java.util.List;

public class ProductAdapterOffline extends ArrayAdapter<ServiceProductDownloadOfflineModel> {

    public ProductAdapterOffline(Context context, List<ServiceProductDownloadOfflineModel> operatorList)
    {
        super(context, 0, operatorList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent)
    {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable
            View convertView, @NonNull ViewGroup parent)
    {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View convertView,
                          ViewGroup parent)
    {
        // It is used to set our custom view.
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_product_offline, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.tvName);
        ServiceProductDownloadOfflineModel currentItem = getItem(position);

        // It is used the name to the TextView when the
        // current item is not null.
        if (currentItem != null) {
            tvName.setText(currentItem.getProductcode());
        }
        return convertView;
    }
}
