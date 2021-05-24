package cg.rbns.majitechnologie.directcash;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import cg.rbns.majitechnologie.directcash.airtel.AirtelActivity;
import cg.rbns.majitechnologie.directcash.mtn.MtnActivity;

public class MainActivity extends AppCompatActivity {

    private ImageButton btn_airtel, btn_mtn, btn_send_to;
    private boolean singleBack = false;
    private EditText desti;
    private TelephonyManager telephonyManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init
        btn_airtel = findViewById(R.id.btn_airtel_to_mtn);
        btn_mtn = findViewById(R.id.btn_mtn_to_airtel);
        btn_send_to = findViewById(R.id.btn_send_to_other);
        desti = findViewById(R.id.tel_destinataire);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        //Operator
        String my_operator = telephonyManager.getSimOperatorName();

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


        //
        btn_send_to.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
                dialogBuilder.setView(dialogView);

                final EditText edt = (EditText) dialogView.findViewById(R.id.tel_destinataire);

                dialogBuilder.setTitle("Destinataire");
                dialogBuilder.setPositiveButton("Valider", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String dest = edt.getText().toString();
                        if (!dest.isEmpty()){
                            String firstFourChars = "";
                            if (dest.length() > 2) {
                                firstFourChars = dest.substring(0, 2);
                            } else {
                                firstFourChars = dest;
                            }
                            if (firstFourChars.equals("06")){
                                sendto(dest, my_operator);
                            }
                            if (firstFourChars.equals("05")){
                                sendto(dest, my_operator);
                            }
                            if (firstFourChars.equals("04")){
                                sendto(dest, my_operator);
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Veuillez renseinger un numero de téléphone", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialogBuilder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
                AlertDialog b = dialogBuilder.create();
                b.show();

            }
        });
    }

    private void sendto(String dest, String my_operator) {
        if (my_operator.equals("MTN-CG")){
            String result =  "info*" + dest;
            Uri uriSms =  Uri.parse("smsto:" + getString(R.string.srv_mtn));
            Intent sms_intent = new Intent(Intent.ACTION_SENDTO, uriSms);
            sms_intent.putExtra("sms_body", result);
            startActivity(sms_intent);
        } else {
            String result =  "info*" + dest;
            Uri uriSms =  Uri.parse("smsto:" + getString(R.string.srv_airtel));
            Intent sms_intent = new Intent(Intent.ACTION_SENDTO, uriSms);
            sms_intent.putExtra("sms_body", result);
            startActivity(sms_intent);
        }

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