package cg.rbns.majitechnologie.directcash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.app.Activity;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import cg.rbns.majitechnologie.directcash.fragment.HomeFragment;
import cg.rbns.majitechnologie.directcash.fragment.ServiceClientFragment;
import cg.rbns.majitechnologie.directcash.upgrade.UpdateHelper;

public class MainActivity extends AppCompatActivity implements UpdateHelper.OnUpdateCheckListener {

    private ImageButton btn_airtel, btn_mtn, btn_send_to;
    private boolean singleBack = false;
    private EditText desti;
    private TelephonyManager telephonyManager;
    private String TAG;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;
    String my_operator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init
        desti = findViewById(R.id.tel_destinataire);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        bottomNavigationView = findViewById(R.id.navigation);
        toolbar = findViewById(R.id.toolbar);

        //Operator Init
        my_operator = telephonyManager.getSimOperatorName();


        // Load Default Fragment
        loadFragment(new HomeFragment());

        // Toolbar
        toolbar.setTitle("");
        toolbar.inflateMenu(R.menu.menu);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                int id = item.getItemId();
                if (id == R.id.navigation_share){
                    sms_share();
                    return true;
                } else if (id == R.id.navigation_share_link){
                    playstore_share();
                    return true;
                }
                return true;
            }
        });

        // Bottom Navigation Menu
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected( MenuItem menuItem) {
                int id = menuItem.getItemId();
                if(id == R.id.navigation_home) {
                    loadFragment(new HomeFragment());
                    return true;
                } else if (id == R.id.navigation_client){
                    loadFragment(new ServiceClientFragment());
                    return true;
                }
                return true;
            }
        });

        //Firebase Messaging
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            //Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                            return;
                        }
                        /*String token = task.getResult();
                        String msg = "Done";
                        Log.d(TAG, msg);*/
                    }
                });

        //Update Verification
        UpdateHelper.with(this)
                .onUpdateCheck(this)
                .check();



    }

    private void playstore_share() {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
            String shareMessage= "Permettez-moi de vous recommander cette application\n";
            shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
            startActivity(Intent.createChooser(shareIntent, "choisissez-en un"));
        } catch(Exception e) {
            //e.toString();
        }
    }

    private void sms_share() {
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

    public void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_layout, fragment);
        transaction.commit();
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
        new AlertDialog.Builder(this)
                .setTitle(getString(R.string.exit_verify))
                .setMessage(getString(R.string.exit_msg))
                .setPositiveButton(getString(R.string.exit_yes), new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.this.finish();
                        System.exit(0);
                    }
                })
                .setNegativeButton(getString(R.string.exit_no), null)
                .show();
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