/*
package com.sunmi.printerhelper.nextgen_sharique.database.adapter_online;*/
package com.sunmi.printerhelper.nextgen_sharique.database.adapter_online;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import tafani.sunmi.printer.R;
import com.sunmi.printerhelper.nextgen_sharique.database.model_online.DenominationModel;

import java.util.ArrayList;

public class DenominationAdapter extends ArrayAdapter<DenominationModel> {

    public DenominationAdapter(Context context,ArrayList<DenominationModel> productList)
    {
        super(context, 0, productList);
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
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_spinner, parent, false);
        }

        TextView tvName = convertView.findViewById(R.id.tvName);
        DenominationModel currentItem = getItem(position);

        // It is used the name to the TextView when the
        // current item is not null.
        if (currentItem != null) {
            tvName.setText(currentItem.getDenominationCode());
        }
        return convertView;
    }
}


