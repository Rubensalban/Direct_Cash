package cg.rbns.majitechnologie.directcash.mtn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cg.rbns.majitechnologie.directcash.MainActivity;
import cg.rbns.majitechnologie.directcash.R;
import cg.rbns.majitechnologie.directcash.airtel.AirtelActivity;

public class MtnActivity extends AppCompatActivity {

    private EditText destinataire, montant, confirm_destinataire;
    private AppCompatButton btn_validate, btn_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mtn);

        //Init
        destinataire = findViewById(R.id.mtn_tel_destinataire);
        confirm_destinataire = findViewById(R.id.mtn_confirm_tel_destination);
        montant = findViewById(R.id.mtn_tel_prix);
        btn_validate = findViewById(R.id.btn_mtn_v);
        btn_cancel = findViewById(R.id.btn_mtn_cancel);

        // Back to home
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MtnActivity.this, MainActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(i);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });

        //Send Mtn
        btn_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String my_dest = destinataire.getText().toString().trim();
                String my_confirm_dest = confirm_destinataire.getText().toString().trim();
                String my_montant = montant.getText().toString().trim();
                int nb = Integer.parseInt(my_montant);
                if (!my_montant.isEmpty()){
                    if ((my_confirm_dest.equals(my_dest)) && (nb > 49)){
                        send_sms(nb, my_confirm_dest);
                    } else {
                        Toast.makeText(MtnActivity.this, "Veuillez verifier le numero du destinaire", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MtnActivity.this, "Veuillez renseigner un montant à envoyer", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void send_sms(int nb, String my_confirm_dest) {
        String result =  my_confirm_dest + "*" + nb + "*" + my_confirm_dest;
        Uri uriSms =  Uri.parse("smsto:" + getString(R.string.srv_mtn));
        Intent sms_intent = new Intent(Intent.ACTION_SENDTO, uriSms);
        sms_intent.putExtra("sms_body", result);
        startActivity(sms_intent);
    }
}