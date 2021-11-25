package cg.rbns.majitechnologie.directcash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

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

import cg.rbns.majitechnologie.directcash.airtel.AirtelActivity;
import cg.rbns.majitechnologie.directcash.fragment.HomeFragment;
import cg.rbns.majitechnologie.directcash.fragment.ServiceClientFragment;
import cg.rbns.majitechnologie.directcash.mtn.MtnActivity;
import cg.rbns.majitechnologie.directcash.upgrade.UpdateHelper;

public class MainActivity extends AppCompatActivity implements UpdateHelper.OnUpdateCheckListener {

    private ImageButton btn_airtel, btn_mtn, btn_send_to;
    private boolean singleBack = false;
    private EditText desti;
    private TelephonyManager telephonyManager;
    private String TAG;
    private BottomNavigationView bottomNavigationView;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Init
        desti = findViewById(R.id.tel_destinataire);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        bottomNavigationView = findViewById(R.id.navigation);
        toolbar = findViewById(R.id.toolbar);


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
                    return true;
                } else if (id == R.id.navigation_share_link){
                    return true;
                } else if (id == R.id.navigation_about){
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