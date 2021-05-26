package cg.rbns.majitechnologie.directcash.airtel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cg.rbns.majitechnologie.directcash.MainActivity;
import cg.rbns.majitechnologie.directcash.R;

public class AirtelActivity extends AppCompatActivity {
    private EditText destinataire, montant, confirm_destinataire;
    private AppCompatButton btn_validate, btn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airtel);

        //Init
        destinataire = findViewById(R.id.airte_tel_destinataire);
        confirm_destinataire = findViewById(R.id.airtel_confirm_tel_destination);
        montant = findViewById(R.id.airtel_tel_prix);
        btn_validate = findViewById(R.id.btn_airtel_v);
        btn_cancel = findViewById(R.id.btn_airtel_cancel);

        //Send Airtel
        btn_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String my_dest = destinataire.getText().toString().trim();
                String my_confirm_dest = confirm_destinataire.getText().toString().trim();
                String my_montant = montant.getText().toString();
                if (!my_montant.isEmpty()){
                    if (my_confirm_dest.equals(my_dest)){
                        int nb = Integer.parseInt(my_montant);
                        send_sms(nb, my_confirm_dest);
                    } else {
                        Toast.makeText(AirtelActivity.this, getString(R.string.identik_destinataire), Toast.LENGTH_SHORT).show();
                    }
                }
                if (my_dest.isEmpty()){
                    destinataire.setFocusable(true);
                    destinataire.setError(getString(R.string.renseiger_destinaire));
                }
                if (my_confirm_dest.isEmpty()){
                    confirm_destinataire.setFocusable(true);
                    confirm_destinataire.setError(getString(R.string.confim_renseiger_destinaire));
                }
                if (my_montant.isEmpty()){
                    montant.setFocusable(true);
                    montant.setError(getString(R.string.svp_montant));
                }
                
            }
        });

        // Back to home
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AirtelActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    private void send_sms(int nb, String my_confirm_dest) {
        String result =  my_confirm_dest + "*" + nb + "*" + my_confirm_dest;
        Uri uriSms =  Uri.parse("smsto:" + getString(R.string.srv_airtel));
        Intent sms_intent = new Intent(Intent.ACTION_SENDTO, uriSms);
        sms_intent.putExtra("sms_body", result);
        startActivity(sms_intent);
    }

}