package cg.rbns.majitechnologie.directcash.layouts;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import cg.rbns.majitechnologie.directcash.MainActivity;
import cg.rbns.majitechnologie.directcash.R;
import cg.rbns.majitechnologie.directcash.utilities.ReseauxAdapter;
import cg.rbns.majitechnologie.directcash.utilities.ReseauxItem;

public class MoneyShareActivity2 extends AppCompatActivity {

    private EditText tel_1, tel_2, tel_price;
    private AppCompatButton btn_send, btn_cancel;
    private ImageView btn_tel1, btn_tel2;
    private ImageView btn_back;
    private AppCompatSpinner network_Spinner;
    private ArrayAdapter mAdapter;
    private String network;
    private ArrayList<ReseauxItem> mReseauxList;
    private static final int CONTACT_PERMISSION_CODE = 1;
    private static final int CONTACT_PICK_CODE = 2;
    private TelephonyManager telephonyManager;
    String my_operator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_money_share);


        // Init
        tel_1 = findViewById(R.id.money_tel_1);
        tel_2 = findViewById(R.id.money_tel_2);
        tel_price = findViewById(R.id.money_tel_price);
        btn_send = findViewById(R.id.btn_money_validate);
        btn_cancel = findViewById(R.id.btn_money_cancel);
        btn_tel1 = findViewById(R.id.btn_contact1);
        btn_tel2 = findViewById(R.id.btn_contact2);
        btn_back = findViewById(R.id.back_money);
        network_Spinner = findViewById(R.id.spinner_money);
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        //Operator Init
        my_operator = telephonyManager.getSimOperatorName();
        tel_1.setHint(getString(R.string.tel_06));
        tel_2.setHint(getString(R.string.tel_06_valide));

        // Initialize a new list and Spinner
        mReseauxList = new ArrayList<>();
        mReseauxList.add(new ReseauxItem(getString(R.string.airtel_to_mtn2)));
        mReseauxList.add(new ReseauxItem(getString(R.string.mtn_to_airtel2)));
        mAdapter = new ReseauxAdapter(this, mReseauxList);
        network_Spinner.setAdapter(mAdapter);
        network_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                ReseauxItem selectedItemText = (ReseauxItem) adapterView.getItemAtPosition(i);
                network = selectedItemText.toString();
                int position = network_Spinner.getSelectedItemPosition();
                if (position==0){
                    tel_1.setHint(getString(R.string.tel_06));
                    tel_2.setHint(getString(R.string.tel_06_valide));
                }
                if (position==1){
                    tel_1.setHint(getString(R.string.tel_05));
                    tel_2.setHint(getString(R.string.tel_05_valide));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btn_tel1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chekContactPermission()){
                    pickContactIntent();
                } else {
                    requestContactPermission();
                }
            }
        });

        btn_tel2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (chekContactPermission()){
                    pickContactIntent();
                } else {
                    requestContactPermission();
                }
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tel_one = tel_1.getText().toString();
                String tel_two = tel_2.getText().toString();
                String price = tel_price.getText().toString();
                if (!tel_one.isEmpty()){
                    tel_1.setText(null);
                }
                if (!tel_two.isEmpty()){
                    tel_2.setText(null);
                }
                if (!price.isEmpty()){
                    tel_price.setText(null);
                }
            }
        });

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String my_dest = tel_1.getText().toString();
                String my_confirm_dest = tel_2.getText().toString();
                String my_montant = tel_price.getText().toString();
                if (my_dest.isEmpty()){
                    tel_1.setFocusable(true);
                    tel_1.setError(getString(R.string.renseiger_destinaire));
                }
                if (my_confirm_dest.isEmpty()){
                    tel_2.setFocusable(true);
                    tel_2.setError(getString(R.string.confim_renseiger_destinaire));
                }
                if (my_montant.isEmpty()){
                    tel_price.setFocusable(true);
                    tel_price.setError(getString(R.string.svp_montant));
                }

                if (!my_dest.isEmpty() && !my_confirm_dest.isEmpty()){
                    String firstFourChars = "";
                    String firstFourChars2 = "";
                    String c_firstFourChars = "";
                    String c_firstFourChars2 = "";
                    if (my_dest.length() > 2) {
                        firstFourChars = my_dest.substring(0, 2);
                    } else {
                        firstFourChars = my_dest;
                    }
                    if (my_dest.length() > 7) {
                        firstFourChars2 = my_dest.substring(0, 7);
                    } else {
                        firstFourChars2 = my_dest;
                    }
                    if (my_dest.length() > 2) {
                        c_firstFourChars = my_dest.substring(0, 2);
                    } else {
                        c_firstFourChars = my_dest;
                    }
                    if (my_dest.length() > 7) {
                        c_firstFourChars2 = my_dest.substring(0, 7);
                    } else {
                        c_firstFourChars2 = my_dest;
                    }
                    if (firstFourChars.equals("06") && c_firstFourChars.equals("06")){
                        check(my_dest, my_montant, my_confirm_dest);
                    } else if (firstFourChars2.equals("0024206") && c_firstFourChars2.equals("0024206")){
                        check(my_dest, my_montant, my_confirm_dest);
                    } else if (firstFourChars.equals("05") && c_firstFourChars.equals("05")){
                        check(my_dest, my_montant, my_confirm_dest);
                    } else if (firstFourChars2.equals("0024205") && c_firstFourChars2.equals("0024205")){
                        check(my_dest, my_montant, my_confirm_dest);
                    } else if (firstFourChars.equals("04") && c_firstFourChars.equals("04")){
                        check(my_dest, my_montant, my_confirm_dest);
                    } else if (firstFourChars2.equals("0024204") && c_firstFourChars2.equals("0024204")) {
                        check(my_dest, my_montant, my_confirm_dest);
                    } else {
                        Toast.makeText(MoneyShareActivity2.this, getString(R.string.verify_airtel), Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back_to_preview();
            }
        });


    }

    private Boolean chekContactPermission(){
        boolean result = ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
        ) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }
    private void requestContactPermission(){
        String[] permission = {Manifest.permission.READ_CONTACTS};
        ActivityCompat.requestPermissions(this, permission, CONTACT_PERMISSION_CODE);
    }
    private void pickContactIntent(){
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, CONTACT_PICK_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CONTACT_PERMISSION_CODE){
            if (grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                pickContactIntent();
            } else {
                Toast.makeText(MoneyShareActivity2.this, "Permission refuser", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK){
            if (requestCode == CONTACT_PICK_CODE){
                tel_1.setText("");
                Cursor cursor1, cursor2;
                Uri uri = data.getData();
                cursor1 = getContentResolver().query(uri, null,null,null,null);
                if (cursor1.moveToFirst()){
                    String contactId = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts._ID));
                    String idResults = cursor1.getString(cursor1.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                    int idResultHold = Integer.parseInt(idResults);

                    if (idResultHold ==1){
                        cursor2 = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                null,
                                ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId,
                                null,
                                null
                        );
                        while (cursor2.moveToNext()){
                            String contact_number = cursor2.getString(cursor2.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                            if (network_Spinner.getSelectedItemPosition() == 0){
                                setcontact(contact_number);
                            }
                            if (network_Spinner.getSelectedItemPosition() == 1) {
                                setcontact2(contact_number);
                            }
                        }
                        cursor2.close();
                    }
                    cursor1.close();
                }
            }
        } else {

        }
    }

    private void setcontact2(String contact_number) {
        String reset_tel = contact_number.replaceAll("\\s+", "");
        String final_tel = "";
        String tel = "";
        if (reset_tel.length() ==14){
            final_tel = reset_tel.substring(5);
            tel = final_tel.substring(0,2);
            if (tel.equals("05")  | tel.equals("04")){
                tel_1.setText(final_tel);
                tel_2.setText(final_tel);
                tel_price.setFocusable(true);
            } else {
                tel_1.setText("");
                tel_2.setText("");
                Toast.makeText(MoneyShareActivity2.this, getString(R.string.send_to_airtel), Toast.LENGTH_SHORT).show();
            }
        } else if (reset_tel.length() == 13){
            final_tel = reset_tel.substring(4);
            tel = final_tel.substring(0,2);
            if (tel.equals("05")  | tel.equals("04")){
                tel_1.setText(final_tel);
                tel_2.setText(final_tel);
                tel_price.setFocusable(true);
            } else {
                tel_1.setText("");
                tel_2.setText("");
                Toast.makeText(MoneyShareActivity2.this, getString(R.string.send_to_airtel), Toast.LENGTH_SHORT).show();
            }
        } else if (reset_tel.length() == 9) {
            tel = reset_tel.substring(0, 2);
            if (tel.equals("05")  | tel.equals("04")){
                tel_1.setText(reset_tel);
                tel_2.setText(reset_tel);
                tel_price.setFocusable(true);
            } else {
                tel_1.setText("");
                tel_2.setText("");
                Toast.makeText(MoneyShareActivity2.this, getString(R.string.send_to_airtel), Toast.LENGTH_SHORT).show();
            }
        } else {
            //Toast.makeText(MoneyShareActivity.this, "Echec, verifier que tous les champs sont correct!", Toast.LENGTH_SHORT).show();
        }
    }

    private void setcontact(String number_trim) {
        String reset_tel = number_trim.replaceAll("\\s+", "");
        String final_tel = "";
        String tel = "";
        if (reset_tel.length() ==14){
            final_tel = reset_tel.substring(5);
            tel = final_tel.substring(0,2);
            if (tel.equals("06")){
                tel_1.setText(final_tel);
                tel_2.setText(final_tel);
                tel_price.setFocusable(true);
            } else {
                tel_1.setText("");
                tel_2.setText("");
                Toast.makeText(MoneyShareActivity2.this, getString(R.string.send_to_mtn), Toast.LENGTH_SHORT).show();
            }
        } else if (reset_tel.length() == 13){
            final_tel = reset_tel.substring(4);
            tel = final_tel.substring(0,2);
            if (tel.equals("06")){
                tel_1.setText(final_tel);
                tel_2.setText(final_tel);
                tel_price.setFocusable(true);
            } else {
                tel_1.setText("");
                tel_2.setText("");
                Toast.makeText(MoneyShareActivity2.this, getString(R.string.send_to_mtn), Toast.LENGTH_SHORT).show();
            }
        } else if (reset_tel.length() == 9) {
            tel = reset_tel.substring(0, 2);
            if (tel.equals("06")){
                tel_1.setText(reset_tel);
                tel_2.setText(reset_tel);
                tel_price.setFocusable(true);
            } else {
                tel_1.setText("");
                tel_2.setText("");
                Toast.makeText(MoneyShareActivity2.this, getString(R.string.send_to_mtn), Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(MoneyShareActivity2.this, "Echec, verifier que tous les champs sont correct!", Toast.LENGTH_SHORT).show();
        }
    }


    private void check(String my_dest, String my_montant, String my_confirm_dest) {
        if (!my_montant.isEmpty()) {
            if (my_dest.length() > 8 && my_confirm_dest.length() > 8) {
                if (my_confirm_dest.equals(my_dest)){
                    int nb = Integer.parseInt(my_montant);
                    if (nb < 499) {
                        Toast.makeText(MoneyShareActivity2.this, getString(R.string.verify_montant), Toast.LENGTH_LONG).show();
                    } else {
                        send_sms(nb, my_confirm_dest);
                    }
                } else {
                    Toast.makeText(MoneyShareActivity2.this, getString(R.string.identik_destinataire), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(MoneyShareActivity2.this, getString(R.string.verify_desti), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void send_sms(int nb, String my_confirm_dest) {
        Uri tel_number;
        String phone = my_confirm_dest.substring(0,2);
        if (phone.equals("06")){
            tel_number = Uri.parse("smsto:" + getString(R.string.srv_airtel));
            send(tel_number,my_confirm_dest, nb);
        } else {
            tel_number = Uri.parse("smsto:" + getString(R.string.srv_mtn));
            send(tel_number,my_confirm_dest, nb);
        }
    }

    private void send(Uri tel_number, String my_confirm_dest, int nb) {
        String result =  my_confirm_dest + "*" + nb + "*" + my_confirm_dest;
        Intent sms_intent = new Intent(Intent.ACTION_SENDTO, tel_number);
        sms_intent.putExtra("sms_body", result);
        startActivity(sms_intent);
    }

    @Override
    public void onBackPressed() {
        back_to_preview();
        this.finish();
    }

    private void back_to_preview() {
        Intent i = new Intent(MoneyShareActivity2.this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
    }
}
