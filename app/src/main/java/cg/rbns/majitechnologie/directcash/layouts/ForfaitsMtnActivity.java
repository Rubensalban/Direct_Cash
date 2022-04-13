package cg.rbns.majitechnologie.directcash.layouts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import cg.rbns.majitechnologie.directcash.MainActivity;
import cg.rbns.majitechnologie.directcash.R;

public class ForfaitsMtnActivity extends AppCompatActivity {
    private ImageView btn_back;
    private ConstraintLayout btn_15, btn_25, btn_50, btn_100, btn_300, btn_500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forfaits_mtn);

        // Init
        btn_back = findViewById(R.id.forfait_back_mtn);
        btn_15 = findViewById(R.id.sms_15_mtn);
        btn_25 = findViewById(R.id.sms_25_mtn);
        btn_50 = findViewById(R.id.sms_50_mtn);
        btn_100 = findViewById(R.id.sms_100_mtn);
        btn_300 = findViewById(R.id.sms_300_mtn);
        btn_500 = findViewById(R.id.sms_500_mtn);


        btn_15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_15();
            }
        });
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
        btn_100.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_100();
            }
        });
        btn_300.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_300();
            }
        });
        btn_500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get_500();
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

    private void get_15() {
        if (ActivityCompat.checkSelfPermission(ForfaitsMtnActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ForfaitsMtnActivity.this, new String[] {Manifest.permission.CALL_PHONE}, 1);
        } else {
            String ussdCode = "*125*2*4*1#";
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(ussdToCallUri(ussdCode));
            try {
                startActivity(intent);
            } catch (SecurityException e) {
                e.printStackTrace();
            }

        }
    }
    private void get_25() {
        if (ActivityCompat.checkSelfPermission(ForfaitsMtnActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ForfaitsMtnActivity.this, new String[] {Manifest.permission.CALL_PHONE}, 1);
        } else {
            String ussdCode = "*125*2*4*2#";
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
        if (ActivityCompat.checkSelfPermission(ForfaitsMtnActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ForfaitsMtnActivity.this, new String[] {Manifest.permission.CALL_PHONE}, 1);
        } else {
            String ussdCode = "*125*2*4*3#";
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(ussdToCallUri(ussdCode));
            try {
                startActivity(intent);
            } catch (SecurityException e) {
                e.printStackTrace();
            }

        }
    }

    private void get_100() {
        if (ActivityCompat.checkSelfPermission(ForfaitsMtnActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ForfaitsMtnActivity.this, new String[] {Manifest.permission.CALL_PHONE}, 1);
        } else {
            String ussdCode = "*125*2*4*4#";
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
        if (ActivityCompat.checkSelfPermission(ForfaitsMtnActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ForfaitsMtnActivity.this, new String[] {Manifest.permission.CALL_PHONE}, 1);
        } else {
            String ussdCode = "*125*2*4*5#";
            Intent intent = new Intent(Intent.ACTION_CALL);
            intent.setData(ussdToCallUri(ussdCode));
            try {
                startActivity(intent);
            } catch (SecurityException e) {
                e.printStackTrace();
            }

        }
    }

    private void get_500() {
        if (ActivityCompat.checkSelfPermission(ForfaitsMtnActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(ForfaitsMtnActivity.this, new String[] {Manifest.permission.CALL_PHONE}, 1);
        } else {
            String ussdCode = "*125*2*4*6#";
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
        back_to_preview();
        this.finish();
    }

    private void back_to_preview() {
        Intent i = new Intent(ForfaitsMtnActivity.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
}