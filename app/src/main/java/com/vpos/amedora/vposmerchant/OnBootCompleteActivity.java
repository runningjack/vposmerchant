package com.vpos.amedora.vposmerchant;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Amedora on 9/7/2015.
 */
public class OnBootCompleteActivity extends BroadcastReceiver {
    public void onReceive(Context context,Intent intent){

        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)){
            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
}
