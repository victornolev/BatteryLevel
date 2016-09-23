package com.example.user.batterylevel;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

public class BatteryService extends Service {

    Runnable runnable;
    private final int interval = 3600000; // 1 hour
    private Handler handler = new Handler();

    int currentBatteryLevel;
    int batteryLevelAfterHour;
    int batteryPercentageDrop;
    Context mContext;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Toast.makeText(this, "Service is started!", Toast.LENGTH_LONG).show();

        Intent batteryIntent = registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        currentBatteryLevel = batteryIntent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);

        Toast.makeText(this, "Battery now is " + currentBatteryLevel, Toast.LENGTH_SHORT).show();

        runnable = new Runnable(){
            public void run() {
                mContext = BatteryService.this;

                Intent batteryIntentAfterHour = registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
                batteryLevelAfterHour = batteryIntentAfterHour.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);

                Toast.makeText(mContext, "Battery after one hour is " + batteryLevelAfterHour, Toast.LENGTH_SHORT).show();

                batteryPercentageDrop = currentBatteryLevel - batteryLevelAfterHour;

                Toast.makeText(mContext, "Battery percentage drop is " + batteryPercentageDrop, Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra("Percentage drop of battery", batteryPercentageDrop);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);


                startService(new Intent(mContext, BatteryService.class));
            }

        };

        handler.postAtTime(runnable, System.currentTimeMillis() + interval);
        handler.postDelayed(runnable, interval);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "Service is stopped!", Toast.LENGTH_LONG).show();
    }
}
