package cg.rbns.majitechnologie.directcash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import cg.rbns.majitechnologie.directcash.airtel.AirtelActivity;
import cg.rbns.majitechnologie.directcash.mtn.MtnActivity;
import cg.rbns.majitechnologie.directcash.upgrade.UpdateHelper;

public class MainActivity extends AppCompatActivity implements UpdateHelper.OnUpdateCheckListener {

    private ImageButton btn_airtel, btn_mtn, btn_send_to;
    private ImageButton btn_mtn_solde, btn_airtel_solde;
    private boolean singleBack = false;
    private EditText desti;
    private TelephonyManager telephonyManager;
    private String TAG;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init
        btn_airtel = findViewById(R.id.btn_airtel_to_mtn);
        btn_mtn = findViewById(R.id.btn_mtn_to_airtel);
        btn_send_to = findViewById(R.id.btn_send_to_other);
        desti = findViewById(R.id.tel_destinataire);
        btn_mtn_solde = findViewById(R.id.btn_solde_mtn);
        btn_airtel_solde = findViewById(R.id.btn_solde_airtel);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        //Firebase Messaging
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        String token = task.getResult();
                        String msg = "Done";
                        Log.d(TAG, msg);
                    }
                });

        //Update Verification
        UpdateHelper.with(this)
                .onUpdateCheck(this)
                .check();


        //Operator Init
        String my_operator = telephonyManager.getSimOperatorName();

        //Solde Mtn
        btn_mtn_solde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_solde_mtn();
            }
        });

        //Solde Airtel
        btn_airtel_solde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                get_solde_airtel();
            }
        });

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
                dialogBuilder.setTitle("Partagez à un amis");
                dialogBuilder.setPositiveButton(getString(R.string.valider), new DialogInterface.OnClickListener() {
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
                            Toast.makeText(MainActivity.this, getString(R.string.svp_number), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                dialogBuilder.setNegativeButton(getString(R.string.annuler), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                    }
                });
                AlertDialog b = dialogBuilder.create();
                b.show();
            }
        });
    }

    private void get_solde_airtel() {
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.CALL_PHONE}, 1);
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
        if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[] {Manifest.permission.CALL_PHONE}, 1);
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

    private void sendto(String dest, String my_operator) {
        if (dest.length() < 9) {
            Toast.makeText(MainActivity.this, getString(R.string.msg_error), Toast.LENGTH_SHORT).show();
        } else {
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

    @Override
    public void onUpdateCheckListener(String urlApp) {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(getString(R.string.new_version))
                .setMessage(getString(R.string.new_disponible))
                .setPositiveButton(getString(R.string.update), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(urlApp));
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        alertDialog.show();
    }
}