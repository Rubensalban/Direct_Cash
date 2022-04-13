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

public class ReseauxAdapter extends ArrayAdapter<ReseauxItem> {

    public ReseauxAdapter(Context context, ArrayList<ReseauxItem> reseauxItemArrayList) {
        super(context, 0, reseauxItemArrayList);
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

    private View initView(int position, View reseauView, ViewGroup parent){
        if (reseauView == null){
            reseauView = LayoutInflater.from(getContext()).inflate(
                    R.layout.item_money, parent, false
            );
        }

        TextView textView = reseauView.findViewById(R.id.reseaux_item_name);
        ReseauxItem reseauxItems = getItem(position);

        if (reseauxItems != null) {
            textView.setText(reseauxItems.getmReseauxName());
        }

        return reseauView;
    }
}
