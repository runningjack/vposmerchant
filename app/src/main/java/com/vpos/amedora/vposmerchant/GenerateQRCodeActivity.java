package com.vpos.amedora.vposmerchant;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.android.Contents;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.encoder.QRCode;
import com.vpos.amedora.vposmerchant.helper.CurrencyFormat;
import com.vpos.amedora.vposmerchant.helper.DatabaseHelper;
import com.vpos.amedora.vposmerchant.helper.Installation;
import com.vpos.amedora.vposmerchant.helper.MCrypt;
import com.vpos.amedora.vposmerchant.model.Apps;
import com.vpos.amedora.vposmerchant.model.Bank;
import com.vpos.amedora.vposmerchant.model.Transaction;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Amedora on 7/19/2015.
 */
public class GenerateQRCodeActivity extends AppCompatActivity  {
    private String LOG_TAG ="GenerateQRCode";
    Button btnGen ;
    public final static int WHITE = 0xFFFFFFFF;
    public final static int BLACK = 0xFF000000;
    HttpClient httpclient;
    //OkHttpClient okHttp;
    HttpPost httppost;
    boolean success = false;
    String ERRORMSG;
    List<NameValuePair> nameValuePairs;
    ProgressDialog dialog  =null;
    String tr;
    public final static String STR = "A string to be encoded as QR code";
    private String current = "";
    ImageView imageView;
    DatabaseHelper db = new DatabaseHelper(this);

    public void onCreate(Bundle savedInstanceState){
        /*super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_genqrcode);*/

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genqrcode);
        imageView = (ImageView) findViewById(R.id.imgQrCode);
        btnGen =(Button)findViewById(R.id.btnGenerate);
        final EditText edtAmt = (EditText)findViewById(R.id.edtAmount);

        int maxLength = 13; // prevent entering a number too large
        edtAmt.setFilters(new InputFilter[] {
                new InputFilter.LengthFilter(maxLength)});
        edtAmt.addTextChangedListener(CurrencyFormat.
                getCurrencyInputTextWatcher(edtAmt));


        btnGen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText edt = (EditText)findViewById(R.id.edtAmount);
                MCrypt mcrypt = new MCrypt();
                try {
                    final Apps myapp = db.getApp(Installation.appId(getApplicationContext()));
                    String transId  = Transaction.mTransId(db.getLastTransId());
                   System.out.println("ENCRYPTED:"+ MCrypt.bytesToHex(mcrypt.encrypt(myapp.getAcc_no())));
                   // nameValuePairs.add(new BasicNameValuePair("merchData","47,640.00;0000000001;944d4a7b-6e2e-4157-9ed0-bff94009a4b3;Citi Ban;023;3466778899"));
                    //tr = "{'transaction':{'trans_amount':'"+ edt.getText().toString()+"','merch_app_id':'"+myapp.getApp_id()
                      //      +"','merch_bank_code':'"+myapp.getSort_code()+"','merch_bank_no':'"+ mcrypt.encrypt(myapp.getAcc_no()).toString()+"','trans_id':'"+transId+"'}}";


                    tr =  edt.getText().toString()+";"+transId+";"+myapp.getApp_id()+";"+myapp.getSort_code()+";"+ MCrypt.bytesToHex(mcrypt.encrypt(myapp.getAcc_no()));
                  /*  dialog = ProgressDialog.show(GenerateQRCodeActivity.this, "",
                            "Processing request..please wait", true);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            Looper.prepare();
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    register(tr,myapp.getApp_id());
                                    handler.removeCallbacks(this);
                                    Looper.myLooper().quit();
                                }
                            }, 2000);
                            Looper.loop();
                        }
                    }).start();*/
                   // if(success){
                        Transaction trans = new Transaction();
                        trans.setAccount_no(myapp.getAcc_no());
                        trans.setApp_id(myapp.getApp_id());
                        trans.setTrans_amount(CurrencyFormat.currencyStringToDouble(edt.getText().toString()));
                        trans.setNarration("Transaction Initiated");
                        trans.setTrans_type("Sales");
                        trans.setTransId(transId);

                        if(db.createTransaction(trans) != null){
                            JSONObject obj = new JSONObject();//
                            Bitmap bitmap = encodeAsBitmap(edt.getText().toString() + ";" + transId + ";" + myapp.getApp_id()
                                    +";"+myapp.getBank_name()+";"+myapp.getSort_code()+";"+myapp.getAcc_no());
                            imageView.setImageBitmap(bitmap);
                        }else{
                            //Toast.makeText(this,)
                        }
                    //}
                } catch (WriterException e) {
                    System.out.println("Exception Error " + e.getMessage());
                    e.printStackTrace();
                }catch(Exception e){

                }
            }
        });
    }

    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        WindowManager manager = (WindowManager) getSystemService(WINDOW_SERVICE);
        Display display = manager.getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        int width = point.x;
        int height = point.y;
        try {
            result = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, width, width, null);
        }catch (IllegalArgumentException iae) {
            return null;
        }
        int w = result.getWidth();
        int h = result.getHeight();
        int[] pixels = new int[w * h];
        for (int y = 0; y < h; y++) {
            int offset = y * w;
            for (int x = 0; x < w; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, w, h);
        return bitmap;
    }

    private void register(String jobj, String AppID){
        try{
            httpclient=new DefaultHttpClient();
            httppost= new HttpPost("http://mylcpschoolbook.net/VirtualPOS/vposapi/");

            nameValuePairs = new ArrayList<>(2);
            nameValuePairs.add(new BasicNameValuePair("controller","translog"));
            nameValuePairs.add(new BasicNameValuePair("action",  "create"));
            nameValuePairs.add(new BasicNameValuePair("json", jobj));


            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

            HttpResponse response = httpclient.execute(httppost);

            Integer status = response.getStatusLine().getStatusCode();

            if(status == 200){
                String result = EntityUtils.toString(response.getEntity());
                JSONObject jsonResponse;
                jsonResponse = new JSONObject(result);
                // CONVERT RESPONSE STRING TO JSON ARRAY
                JSONArray ja = jsonResponse.getJSONArray("data");
                // ITERATE THROUGH AND RETRIEVE CLUB FIELDS
                int n = ja.length();
                for (int i = 0; i < n; i++) {
                    // GET INDIVIDUAL JSON OBJECT FROM JSON ARRAY
                    JSONObject jo = ja.getJSONObject(i);
                    success = jo.optBoolean("success");
                    //tvMessage.setText(jo.optString("errmsg"));
                    //userId = jo.getInt("id");
                    ERRORMSG =jo.optString("errmsg");
                    System.out.println("Status " +jo.optString("errmsg"));
                    if(i>0){
                        break;
                    }
                }
                if(success){

                } else {

                }

            } else {

            }

        }catch(Exception e){
            // dialog.dismiss();
            System.out.println("Exception : " + e.getMessage());
            //Log.e("Exception", e.getMessage());
        }
        dialog.dismiss();
    }
}
