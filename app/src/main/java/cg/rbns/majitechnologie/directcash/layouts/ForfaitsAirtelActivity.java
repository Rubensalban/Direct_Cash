package cg.rbns.majitechnologie.directcash.layouts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import cg.rbns.majitechnologie.directcash.MainActivity;
import cg.rbns.majitechnologie.directcash.R;
import cg.rbns.majitechnologie.directcash.data.ReseauxAdapter;
import cg.rbns.majitechnologie.directcash.data.ReseauxItem;

public class ForfaitsAirtelActivity extends AppCompatActivity {
    private ImageView btn_back;
    private ConstraintLayout btn_25, btn_50, btn_145, btn_350, btn_300, btn_money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forfaits_airtel);

        // Init
        btn_back = findViewById(R.id.forfait_back);
        btn_25 = findViewById(R.id.sms_25);
        btn_50 = findViewById(R.id.sms_50);
        btn_145 = findViewById(R.id.sms_140);
        btn_350 = findViewById(R.id.sms_350);
        btn_300 = findViewById(R.id.sms_300);
        btn_money = findViewById(R.id.sms_300_money);

        btn_25.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_25();
            }
        });
        btn_50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_50();
            }
        });
        btn_145.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_145();
            }
        });
        btn_350.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_350();
            }
        });
        btn_300.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_300();
            }
        });
        btn_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_300_money();
            }
        });

        // Back
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back_to_preview();
            }
        });
    }

    private void get_25() {
        if (ActivityCompat.checkSelfPermission(ForfaitsAirtelActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ForfaitsAirtelActivity.this, new String[] {Manifest.permission.CALL_PHONE}, 1);
        } else {
            String ussdCode = "*121*3*1#";
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(ussdToCallUri(ussdCode));
            try {
                startActivity(intent);
            } catch (SecurityException e) {
                e.printStackTrace();
            }

        }
    }
    private void get_50() {
        if (ActivityCompat.checkSelfPermission(ForfaitsAirtelActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ForfaitsAirtelActivity.this, new String[] {Manifest.permission.CALL_PHONE}, 1);
        } else {
            String ussdCode = "*121*3*2#";
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(ussdToCallUri(ussdCode));
            try {
                startActivity(intent);
            } catch (SecurityException e) {
                e.printStackTrace();
            }

        }
    }

    private void get_145() {
        if (ActivityCompat.checkSelfPermission(ForfaitsAirtelActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ForfaitsAirtelActivity.this, new String[] {Manifest.permission.CALL_PHONE}, 1);
        } else {
            String ussdCode = "*121*3*3#";
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(ussdToCallUri(ussdCode));
            try {
                startActivity(intent);
            } catch (SecurityException e) {
                e.printStackTrace();
            }

        }
    }

    private void get_350() {
        if (ActivityCompat.checkSelfPermission(ForfaitsAirtelActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ForfaitsAirtelActivity.this, new String[] {Manifest.permission.CALL_PHONE}, 1);
        } else {
            String ussdCode = "*121*3*4#";
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(ussdToCallUri(ussdCode));
            try {
                startActivity(intent);
            } catch (SecurityException e) {
                e.printStackTrace();
            }

        }
    }

    private void get_300() {
        if (ActivityCompat.checkSelfPermission(ForfaitsAirtelActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ForfaitsAirtelActivity.this, new String[] {Manifest.permission.CALL_PHONE}, 1);
        } else {
            String ussdCode = "*121*3*5#";
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(ussdToCallUri(ussdCode));
            try {
                startActivity(intent);
            } catch (SecurityException e) {
                e.printStackTrace();
            }

        }
    }

    private void get_300_money() {
        if (ActivityCompat.checkSelfPermission(ForfaitsAirtelActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ForfaitsAirtelActivity.this, new String[] {Manifest.permission.CALL_PHONE}, 1);
        } else {
            String ussdCode = "*125*4*2*5#";
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        back_to_preview();
    }

    private void back_to_preview() {
        Intent i = new Intent(ForfaitsAirtelActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }

}