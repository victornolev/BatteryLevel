package com.example.user.batterylevel;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity{

    TextView tvBatteryPercentageDrop;
    Intent batteryServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvBatteryPercentageDrop = (TextView) findViewById(R.id.tv_batteryPercentageDrop);

        Intent batteryIntent = getIntent();
        if (batteryIntent != null){
            String batteryDropInPercentages = batteryIntent.getStringExtra("Percentage drop of battery");
            tvBatteryPercentageDrop.setVisibility(View.VISIBLE);
            tvBatteryPercentageDrop.setText(batteryDropInPercentages);
        }
    }


    public void onBatteryServiceStarted(View view) {
        batteryServiceIntent = new Intent(this, BatteryService.class);
        startService(batteryServiceIntent);

    }

    public void onBatteryServiceStopped(View view){
        if(batteryServiceIntent == null){
            batteryServiceIntent = new Intent(this, BatteryService.class);
        }
        stopService(batteryServiceIntent);

    }
}

