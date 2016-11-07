package com.huami.mifitheme;

import android.app.AlertDialog;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

public class MyService extends Service {
    AlertDialog alertDialog;


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        night();
        return super.onStartCommand(intent, flags, startId);
    }

    public void night() {
        if (alertDialog != null) {
            View v = View.inflate(this, R.layout.fragment_edit_text, null);
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setView(v);
            alertDialog = builder.create();
//        alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY);
            alertDialog.show();


            WindowManager.LayoutParams lp = alertDialog.getWindow().getAttributes();
            WindowManager wm = (WindowManager) getSystemService(WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            DisplayMetrics dd = new DisplayMetrics();
            display.getMetrics(dd);
            if (dd.heightPixels > dd.widthPixels) {
                lp.width = 0;
            } else {
                lp.height = 0;
            }
            alertDialog.getWindow().setAttributes(lp);
        }

    }
}
