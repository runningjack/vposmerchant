package com.vpos.amedora.vposmerchant;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.vpos.amedora.vposmerchant.helper.DatabaseHelper;
import com.vpos.amedora.vposmerchant.helper.Installation;
import com.vpos.amedora.vposmerchant.helper.Validation;
import com.vpos.amedora.vposmerchant.model.Bank;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Amedora on 7/30/2015.
 */
public class RegisterActivityBank extends AppCompatActivity {
    Spinner spinner;
    String[] dias;
    String StoreName,StoreContact,SEmail,pass,bankName,accNo;
    Button btnNext,btnBack;
    EditText edtAccNo;
    public static String TAG_BANK_NAME,TAG_SHORT_NAME;
    DatabaseHelper db = new DatabaseHelper(this);
    ArrayList<HashMap<String, String>> bankList;
    protected void onCreate(Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_register_screen_bank);
        spinner = (Spinner)findViewById(R.id.spinner);
        edtAccNo = (EditText)findViewById(R.id.edtAccNo);

        Bundle bundle = getIntent().getExtras();
        StoreName 		= bundle.getString("SName");
        StoreContact 		= bundle.getString("SContact");
        SEmail              =   bundle.getString("Email");
        pass                = bundle.getString("Password");

        btnNext = (Button)findViewById(R.id.btnNextBank);
        btnBack = (Button)findViewById(R.id.btnRegBackBank);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData();
            }
        });
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.YELLOW);
                ((TextView) parent.getChildAt(0)).setTextSize(25);
                bankName = parent.getItemAtPosition(position).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        bankList = new ArrayList<HashMap<String, String>>();
        List<Bank> bank = db.getAllBank();
        System.out.println(bank.size());
        // loop through each website
        dias = new String[bank.size()];
        for (int i = 0; i < bank.size(); i++) {
            Bank s = bank.get(i);
            // creating new HashMap
            HashMap<String, String> map = new HashMap<String, String>();
            // adding each child node to HashMap key => value

            map.put(TAG_BANK_NAME, String.valueOf(s.getBank_name()));
            // adding HashList to ArrayList
            bankList.add(map);
            // add sqlite id to array
            // used when deleting a website from sqlite
            dias[i] = String.valueOf(s.getShort_name());
        }


        ArrayAdapter adp = new ArrayAdapter(this,android.R.layout.simple_spinner_item,dias);
        adp.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adp);
    }

    private void  sendData(){
        if(bankName !="" && checkValidation()){
            Intent intent = new Intent(RegisterActivityBank.this,RegisterActivity3.class);
            //TelephonyManager tmgt = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
            //imei = tmgt.getDeviceId();
            intent.putExtra("SContact",StoreContact);
            intent.putExtra("AppID", Installation.appId(getApplicationContext()));
            intent.putExtra("SName",StoreName);
            intent.putExtra("Email",SEmail);
            intent.putExtra("Password",pass);
            intent.putExtra("BankName",bankName);
            intent.putExtra("AccNo",edtAccNo.getText().toString());
            Log.e("AccountNo", edtAccNo.getText().toString());
            Log.e("Bank Name", bankName);
            startActivity(intent);
        }else{
            Toast.makeText(getBaseContext(), "Ensure that you enter a valid account and select a bank", Toast.LENGTH_LONG).show();
        }

    }

    private boolean checkValidation() {
        boolean ret = true;
        if (!Validation.hasText(edtAccNo)) ret = false;
        if (!Validation.isAccountNo(edtAccNo, 10)) ret = false;
        return ret;
    }
}
