package com.vpos.amedora.vposmerchant;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.vpos.amedora.vposmerchant.helper.Installation;

import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.apache.http.NameValuePair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Amedora on 7/10/2015.
 */
public class MainLandingActivity extends AppCompatActivity {
    ImageButton imgBtnAddAcc,imgBtnBill,imgBtnList,imgTransList;


    boolean success =false;
    String ERRORMSG;
    ProgressDialog pDialog  =null;
    ProgressDialog pDialog2  =null;
    public static String TAG_BANK_NAME,TAG_SHORT_NAME;
    //DatabaseHelper db = new DatabaseHelper(this);
    ArrayList<HashMap<String, String>> accountList;
    List<NameValuePair> nameValuePairs;
    HttpClient httpclient;
    HttpResponse response;
    //OkHttpClient okHttp;
    HttpPost httppost;

    protected void onCreate(Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);

        setContentView(R.layout.activity_mainlanding);

        imgBtnAddAcc = (ImageButton)findViewById(R.id.imgBtnMainAddAccount);
        imgBtnList  = (ImageButton)findViewById(R.id.imgBtnAccountListing);
        imgBtnBill   =   (ImageButton)findViewById(R.id.imgBtnMakeBill);
        imgTransList = (ImageButton)findViewById(R.id.imgBtnTransactions);




        imgBtnList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainLandingActivity.this,AccountListingActivity.class);
                startActivity(intent);

            }
        });

        imgBtnBill.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainLandingActivity.this,GenerateQRCodeActivity.class);
                startActivity(intent);
            }
        });

        imgTransList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainLandingActivity.this,TransactionListingActivity.class);
                startActivity(intent);


                /*new Thread(new Runnable() {
                    @Override
                    public void run() {

                        Looper.prepare();

                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                processTransaction();
                                handler.removeCallbacks(this);

                                Looper.myLooper().quit();
                            }
                        }, 2000);

                        Looper.loop();
                    }
                }).start();*/
            }
        });


    }



}
