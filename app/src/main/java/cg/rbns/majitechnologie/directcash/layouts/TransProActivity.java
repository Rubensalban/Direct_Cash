package cg.rbns.majitechnologie.directcash.layouts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import cg.rbns.majitechnologie.directcash.MainActivity;
import cg.rbns.majitechnologie.directcash.R;

public class TransProActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_pro);
    }

    @Override
    public void onBackPressed() {
        back_to_preview();
        super.onBackPressed();
    }

    private void back_to_preview() {
        Intent i = new Intent(TransProActivity.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
}