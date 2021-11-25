package cg.rbns.majitechnologie.directcash.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cg.rbns.majitechnologie.directcash.R;
import cg.rbns.majitechnologie.directcash.layouts.ForfaitsActivity;
import cg.rbns.majitechnologie.directcash.layouts.MoneyShareActivity;
import cg.rbns.majitechnologie.directcash.layouts.SoldeActivity;
import cg.rbns.majitechnologie.directcash.layouts.TransProActivity;

public class HomeFragment extends Fragment {

    private CardView card_trans, card_transpro, card_solde, card_forfaits;

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
                Intent intent = new Intent(getActivity(), MoneyShareActivity.class);
                startActivity(intent);
            }
        });
        card_transpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i_pro = new Intent(getActivity(), TransProActivity.class);
                startActivity(i_pro);
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
                Intent i_net = new Intent(getActivity(), ForfaitsActivity.class);
                startActivity(i_net);
            }
        });

        return view;
    }
}