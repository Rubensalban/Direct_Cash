package cg.rbns.majitechnologie.directcash.layouts;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;

import java.util.ArrayList;

import cg.rbns.majitechnologie.directcash.MainActivity;
import cg.rbns.majitechnologie.directcash.R;
import cg.rbns.majitechnologie.directcash.utilities.TransProAdapter;
import cg.rbns.majitechnologie.directcash.utilities.TransProItem;

public class TransProActivity2 extends AppCompatActivity {
    private AppCompatSpinner transport_Spinner;
    private ArrayAdapter mAdapter;
    private ArrayList<TransProItem> mTransProItemList;
    private EditText user_name, user_address, user_contact, user_price;
    private AppCompatButton btn_send, btn_cancel;
    private String transport_mode;
    private TelephonyManager telephonyManager;
    private ImageView btn_back;
    String my_operator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_pro);

        // Init
        user_name = findViewById(R.id.transport_name);
        user_address = findViewById(R.id.transport_address);
        user_contact = findViewById(R.id.transport_contact);
        user_price = findViewById(R.id.transport_price);
        transport_Spinner = findViewById(R.id.trans_spinner);
        btn_send = findViewById(R.id.btn_transport_validate);
        btn_cancel = findViewById(R.id.btn_transport_cancel);
        btn_back = findViewById(R.id.trans_back);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        //Operator Init
        my_operator = telephonyManager.getSimOperatorName();

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back_to_preview();
            }
        });

        // Reset EditText
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = user_name.getText().toString();
                String address = user_address.getText().toString();
                String price = user_price.getText().toString();
                String contact = user_contact.getText().toString();
                if (!name.isEmpty()){
                    user_name.setText(null);
                }
                if (!address.isEmpty()){
                    user_address.setText(null);
                }
                if (!price.isEmpty()){
                    user_price.setText(null);
                }
                if (!contact.isEmpty()){
                    user_contact.setText(null);
                }
            }
        });

        // Send
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = user_name.getText().toString();
                String address = user_address.getText().toString();
                String price = user_price.getText().toString();
                String contact = user_contact.getText().toString();
                if (!name.isEmpty() && address.isEmpty() && price.isEmpty() && contact.isEmpty()){
                    user_name.setFocusable(true);
                    user_name.setError(getString(R.string.user_name));
                    user_address.setFocusable(true);
                    user_address.setError(getString(R.string.user_address));
                    user_price.setFocusable(true);
                    user_price.setError(getString(R.string.user_price));
                    user_contact.setFocusable(true);
                    user_contact.setError(getString(R.string.user_contact));
                } else {
                    send_sms(my_operator, name, address, price, contact);
                }
            }
        });

        // Initialize a new list and Spinner
        mTransProItemList = new ArrayList<>();
        mTransProItemList.add(new TransProItem(getString(R.string.virtuel)));
        mTransProItemList.add(new TransProItem(getString(R.string.cash)));
        mAdapter = new TransProAdapter(this, mTransProItemList);
        transport_Spinner.setAdapter(mAdapter);
        transport_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                TransProItem selectedItemText = (TransProItem) adapterView.getItemAtPosition(i);
                transport_mode = selectedItemText.getmTransMode();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void send_sms(String my_operator, String name, String address, String price, String contact) {
        String result =  transport_mode + "-" + name + "-" + address + "-" + contact + "-" + price;
        if (contact.length() < 9) {
            Toast.makeText(TransProActivity2.this, getString(R.string.msg_error), Toast.LENGTH_SHORT).show();
        } else {
            if (my_operator.equals("MTN-CG")){
                Uri tel_number;
                tel_number = Uri.parse("smsto:" + getString(R.string.sav_mtn));
                Intent sms_intent = new Intent(Intent.ACTION_SENDTO, tel_number);
                sms_intent.putExtra("sms_body", result);
                startActivity(sms_intent);
            } else {
                Uri tel_number;
                tel_number = Uri.parse("smsto:" + getString(R.string.sav_airtel));
                Intent sms_intent = new Intent(Intent.ACTION_SENDTO, tel_number);
                sms_intent.putExtra("sms_body", result);
                startActivity(sms_intent);
            }
        }
    }


    @Override
    public void onBackPressed() {
        back_to_preview();
        this.finish();
    }

    private void back_to_preview() {
        Intent i = new Intent(TransProActivity2.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }
}
