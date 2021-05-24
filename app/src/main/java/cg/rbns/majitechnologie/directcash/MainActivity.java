package cg.rbns.majitechnologie.directcash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import cg.rbns.majitechnologie.directcash.airtel.AirtelActivity;
import cg.rbns.majitechnologie.directcash.mtn.MtnActivity;

public class MainActivity extends AppCompatActivity {

    private ImageButton btn_airtel, btn_mtn;
    private boolean singleBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init
        btn_airtel = findViewById(R.id.btn_airtel_to_mtn);
        btn_mtn = findViewById(R.id.btn_mtn_to_airtel);

        //Start Airtel
        btn_airtel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_mtn = new Intent(MainActivity.this, AirtelActivity.class);
                startActivity(intent_mtn);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        //Start Mtn
        btn_mtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_aritel = new Intent(MainActivity.this, MtnActivity.class);
                startActivity(intent_aritel);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (singleBack) {
            super.onBackPressed();
            return;
        }
        this.singleBack = true;
        Toast.makeText(this, getString(R.string.exit_to_app), Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                singleBack = false;
            }
        }, 2000);
    }
}