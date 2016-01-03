package com.vpos.amedora.vposmerchant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.vpos.amedora.vposmerchant.helper.DatabaseHelper;
import com.vpos.amedora.vposmerchant.helper.Validation;
import com.vpos.amedora.vposmerchant.model.Bank;

/**
 * Created by Amedora on 7/20/2015.
 */
public class AddBankActivity extends AppCompatActivity {
    EditText edtShort,edtName,edtCode;
    Button btnAdd;Long success;
    Bank bank2;
    DatabaseHelper db = new DatabaseHelper(this);
    public void onCreate(Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_addbank);
        edtShort = (EditText)findViewById(R.id.edtBankShortName);
        edtName =   (EditText)findViewById(R.id.edtBankName);
        edtCode = (EditText)findViewById(R.id.edtSortCode);
        btnAdd = (Button)findViewById(R.id.btnAddBank);
        bank2 = new Bank();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*if(edtAccountno.length()>0) {
                    bank2.setAccount_no(Long.valueOf(edtAccountno.getText().toString()));
                }*/
                if(!db.ifExists(bank2)){
                    if(createAccount()){
                        Intent intent = new Intent(AddBankActivity.this,AccountListingActivity.class);
                        startActivity(intent);
                    }else{

                        Toast.makeText(getBaseContext(), "Invalid Input", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(getBaseContext(),"Record already existing",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private boolean createAccount(){
        if(checkValidation()){
            Bank bank = new Bank();
            bank.setSort_code(edtCode.getText().toString());
            bank.setShort_name(edtShort.getText().toString());
            bank.setBank_name(edtName.getText().toString());
            success = db.createBank(bank);
            if(success != null)
                return true;
            return false ;
        }
        return false;
    }

    private boolean checkValidation() {
        boolean ret = true;

        if (!Validation.hasText(edtShort)) ret = false;
        if (!Validation.hasText(edtCode)) ret = false;
        if (!Validation.hasText(edtName)) ret = false;
        if (!Validation.isAccountNo(edtCode,9)) ret = false;
        //if (!Validation.isPhoneNumber(edtLname, false)) ret = false;

        return ret;
    }

}
