package cg.rbns.majitechnologie.directcash.layouts;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import cg.rbns.majitechnologie.directcash.MainActivity;
import cg.rbns.majitechnologie.directcash.R;

public class MoneyShareActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_share);


    }

    @Override
    public void onBackPressed() {
        back_to_preview();
        super.onBackPressed();
    }

    private void back_to_preview() {
        Intent i = new Intent(MoneyShareActivity.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
}