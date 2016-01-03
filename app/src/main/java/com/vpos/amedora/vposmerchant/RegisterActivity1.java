package com.vpos.amedora.vposmerchant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.vpos.amedora.vposmerchant.helper.Validation;


/**
 * Created by Amedora on 7/10/2015.
 */
public class RegisterActivity1 extends AppCompatActivity {
    EditText edtStore,edtContact,edtEmail;
    String StoreName,ContactPerson,Email;
    Button btnNext;
    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_register_screen1);
        btnNext = (Button) findViewById(R.id.btnregnext1);
        edtStore = (EditText)findViewById(R.id.edtStoreName);
        edtContact = (EditText)findViewById(R.id.edtStoreContact);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        StoreName   = edtStore.getText().toString();
        ContactPerson   =   edtContact.getText().toString();
        Email   = edtEmail.getText().toString();

        edtEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                Validation.isEmailAddress(edtEmail, true);
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkValidation ()){
                    sendData();
                }else{
                    Toast.makeText(getApplicationContext(),
                            "Company name or contact name or email fields must not be empty",
                            Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void sendData(){
// Submit your form here. your form is valid

        Toast.makeText(this, "Submitting form...", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(RegisterActivity1.this,RegisterActivity2.class);

        intent.putExtra("SName",edtStore.getText().toString());
        intent.putExtra("SContact",edtContact.getText().toString());
        intent.putExtra("Email",edtEmail.getText().toString());
        startActivity(intent);
    }




    private boolean checkValidation() {
        boolean ret = true;

        if (!Validation.hasText(edtStore)) ret = false;
        if (!Validation.hasText(edtContact)) ret = false;
        if (!Validation.isEmailAddress(edtEmail, true)) ret = false;
        //if (!Validation.isPhoneNumber(edtLname, false)) ret = false;

        return ret;
    }



}
