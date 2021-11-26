package cg.rbns.majitechnologie.directcash.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import cg.rbns.majitechnologie.directcash.R;


public class ServiceClientFragment extends Fragment {

    private ImageView btn_whatsapp, btn_facebook, btn_instagram, btn_youtube;
    private LinearLayout btn_mtn, btn_airtel;

    public ServiceClientFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_service_client, container, false);

        // Init
        btn_airtel = view.findViewById(R.id.layout_airtel);
        btn_mtn = view.findViewById(R.id.layout_mtn);
        btn_whatsapp = view.findViewById(R.id.btn_whatsapp);
        btn_facebook = view.findViewById(R.id.btn_facebook);
        btn_instagram = view.findViewById(R.id.btn_instagramm);
        btn_youtube = view.findViewById(R.id.btn_youtube);

        btn_youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/channel/UCDsQdQsHFyHs8-sKvjhv7g"));
                startActivity(i);
            }
        });

        btn_instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/maji.technologies/"));
                startActivity(i);
            }
        });

        btn_facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/profile.php?id=100068363735217"));
                startActivity(i);
            }
        });

        btn_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number = "00242064004646";
                String url = "https://wa.me/"+number;
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(i);
            }
        });

        btn_airtel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:00242050774444"));
                if (ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        });

        btn_mtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:00242064004646"));
                if (ActivityCompat.checkSelfPermission(getContext(),
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(callIntent);
            }
        });

        return view;
    }
}