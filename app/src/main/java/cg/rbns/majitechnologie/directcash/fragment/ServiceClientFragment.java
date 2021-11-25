package cg.rbns.majitechnologie.directcash.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cg.rbns.majitechnologie.directcash.R;


public class ServiceClientFragment extends Fragment {

    public ServiceClientFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service_client, container, false);

        return view;
    }
}