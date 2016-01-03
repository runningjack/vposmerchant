package com.vpos.amedora.vposmerchant;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.WindowManager;
import android.widget.Button;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.encoder.QRCode;
import com.vpos.amedora.vposmerchant.helper.Installation;

import java.io.File;


/**
 * Created by Amedora on 7/19/2015.
 */
public class MainActivity extends AppCompatActivity {
    private BlueToothService mBTService = null;
    private String tag = "MainActivity";
    private static final int REQUEST_EX = 1;
    public static final int MESSAGE_STATE_CHANGE = 1;
    public static final int MESSAGE_READ = 2;
    public static final int MESSAGE_WRITE = 3;
    public static final int MESSAGE_DEVICE_NAME = 4;
    public static final int MESSAGE_TOAST = 5;

    private String LOG_TAG ="GenerateQRCode";
    Button btnGen ;
    public final static int WHITE = 0xFFFFFFFF;
    public final static int BLACK = 0xFF000000;
    public final static String STR = "A string to be encoded as QR code";
    private Handler mHandler = new Handler();
    private static final String INSTALLATION = "INSTALLATION";

    private Button bt_image;
    protected void onCreate(Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);
        //setContentView(R.layout.activity_main);

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                File installation = new File(getApplicationContext().getFilesDir(),INSTALLATION);
                if(installation.exists()){
                    String AppID = Installation.readInstallationFile(installation);
                    if (AppID ==null){
                        Intent intent = new Intent(MainActivity.this, RegisterActivity1.class);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(MainActivity.this, MainLandingActivity.class);
                        startActivity(intent);
                    }
                }else{
                    Intent intent = new Intent(MainActivity.this, RegisterActivity1.class);
                    startActivity(intent);
                }
            }
        }, 4000); // 4 seconds
    }
}
