package cg.rbns.majitechnologie.directcash.layouts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import cg.rbns.majitechnologie.directcash.MainActivity;
import cg.rbns.majitechnologie.directcash.R;

public class SoldeActivity extends AppCompatActivity {

    private ImageView back;
    private LinearLayout btn_airtel;
    private LinearLayout btn_mtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solde);

        // Init
        btn_airtel = findViewById(R.id.solde_airtel);
        btn_mtn = findViewById(R.id.solde_mtn);
        back = findViewById(R.id.solde_back);

        // Back to preview
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back_layout();
            }
        });

        // Airtel
        btn_airtel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_solde_airtel();
            }
        });

        // Mtn
        btn_mtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_solde_mtn();
            }
        });



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        back_layout();
    }

    private void back_layout() {
        Intent i = new Intent(SoldeActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

    private void get_solde_airtel() {
        if (ActivityCompat.checkSelfPermission(SoldeActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SoldeActivity.this, new String[] {Manifest.permission.CALL_PHONE}, 1);
        } else {
            String ussdCode = "*128*7*2#";
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(ussdToCallUri(ussdCode));
            try {
                startActivity(intent);
            } catch (SecurityException e) {
                e.printStackTrace();
            }

        }
    }

    private void get_solde_mtn() {
        if (ActivityCompat.checkSelfPermission(SoldeActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SoldeActivity.this, new String[] {Manifest.permission.CALL_PHONE}, 1);
        } else {
            String ussdCode = "*105#";
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(ussdToCallUri(ussdCode));
            try {
                startActivity(intent);
            } catch (SecurityException e) {
                e.printStackTrace();
            }

        }
    }

    private Uri ussdToCallUri(String ussdCode) {
        StringBuilder uriString = new StringBuilder();
        if (!ussdCode.startsWith("tel:"))
            uriString.append("tel:");
        for (char c : ussdCode.toCharArray()){
            if (c == '#')
                uriString.append(Uri.encode("#"));
            else
                uriString.append(c);
        }
        return Uri.parse(uriString.toString());
    }

}