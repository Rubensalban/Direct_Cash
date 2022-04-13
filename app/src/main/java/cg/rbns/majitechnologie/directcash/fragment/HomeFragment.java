package cg.rbns.majitechnologie.directcash.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.Format;

import cg.rbns.majitechnologie.directcash.R;
import cg.rbns.majitechnologie.directcash.layouts.ForfaitsAirtelActivity;
import cg.rbns.majitechnologie.directcash.layouts.ForfaitsMtnActivity;
import cg.rbns.majitechnologie.directcash.layouts.MoneyShareActivity;
import cg.rbns.majitechnologie.directcash.layouts.MoneyShareActivity2;
import cg.rbns.majitechnologie.directcash.layouts.SoldeActivity;
import cg.rbns.majitechnologie.directcash.layouts.TransProActivity;
import cg.rbns.majitechnologie.directcash.layouts.TransProActivity2;

public class HomeFragment extends Fragment {

    private CardView card_trans, card_transpro, card_solde, card_forfaits;
    private int checkedItem;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Init
        card_trans = view.findViewById(R.id.home_trans);
        card_transpro = view.findViewById(R.id.home_transpro);
        card_solde = view.findViewById(R.id.home_solde);
        card_forfaits = view.findViewById(R.id.home_forfaits);


        card_trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity(), R.style.MaterialThemeDialog);
                String[] items = {getString(R.string.mtn_to_airtel), getString(R.string.airtel_to_mtn)};
                alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent i = new Intent(getActivity(), MoneyShareActivity.class);
                                startActivity(i);
                                dialog.dismiss();
                                break;
                            case 1:
                                Intent io = new Intent(getActivity(), MoneyShareActivity2.class);
                                startActivity(io);
                                dialog.dismiss();
                                break;
                        }
                    }
                });
                AlertDialog alert = alertDialog.create();
                alert.setCanceledOnTouchOutside(false);
                alert.show();
            }
        });
        card_transpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity(), R.style.MaterialThemeDialog);
                String[] items = {getString(R.string.cash), getString(R.string.virtuel)};
                alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent i = new Intent(getActivity(), TransProActivity.class);
                                startActivity(i);
                                dialog.dismiss();
                                break;
                            case 1:
                                Intent io = new Intent(getActivity(), TransProActivity2.class);
                                startActivity(io);
                                dialog.dismiss();
                                break;
                        }
                    }
                });
                AlertDialog alert = alertDialog.create();
                alert.setCanceledOnTouchOutside(false);
                alert.show();
            }
        });
        card_solde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), SoldeActivity.class);
                startActivity(i);
            }
        });
        card_forfaits.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity(), R.style.MaterialThemeDialog);
                String[] items = {getString(R.string.airtel_activation), getString(R.string.mtn_activation)};
                alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent i = new Intent(getActivity(), ForfaitsAirtelActivity.class);
                                startActivity(i);
                                dialog.dismiss();
                                break;
                            case 1:
                                Intent io = new Intent(getActivity(), ForfaitsMtnActivity.class);
                                startActivity(io);
                                dialog.dismiss();
                                break;
                        }
                    }
                });
                AlertDialog alert = alertDialog.create();
                alert.setCanceledOnTouchOutside(false);
                alert.show();
            }
        });

        return view;
    }
}