package cg.rbns.majitechnologie.directcash.utilities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import cg.rbns.majitechnologie.directcash.R;

public class TransProAdapter extends ArrayAdapter<TransProItem>  {


    public TransProAdapter(Context context, ArrayList<TransProItem> transProItemList) {
        super(context, 0, transProItemList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return initView(position, convertView, parent);
    }

    private View initView(int position, View transportView, ViewGroup parent){
        if (transportView == null){
            transportView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_trans_pro, parent, false
            );
        }
        TextView textView = transportView.findViewById(R.id.transport_item);
        TransProItem transProItems = getItem(position);
        if (transProItems != null) {
            textView.setText(transProItems.getmTransMode());
        }
        return transportView;
    }
}
