package com.example.hexuan.androiddualsimmanager;

import android.content.Context;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import DualSimUtils.DualSimManager;

public class MainActivity extends AppCompatActivity {

    TextView mainSim, secondSim, isSupport;

    Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainSim = (TextView) findViewById(R.id.mainsim);
        secondSim = (TextView) findViewById(R.id.secondsim);
        isSupport = (TextView) findViewById(R.id.issupport);
        button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                secondSim.setText(getSecondIMSI(getApplicationContext()));
                mainSim.setText(getMainIMSI(getApplicationContext()));
                isSupport.setText(isDualSimSupport(getApplicationContext()));;
            }
        });
    }

    public static String getSecondIMSI(Context context) {
        DualSimManager uuSimManager = DualSimManager.getInstance(Build.MODEL);
        String[] strings = uuSimManager.getEntirImsi(context);

        if (strings[0].equals(uuSimManager.getSubscriberId(context))) {
            return (strings.length > 1) ? strings[1] : "";
        }else {
            return strings[0];
        }
    }

    public static String isDualSimSupport(Context context) {
        DualSimManager uuSimManager = DualSimManager.getInstance(Build.MODEL);
        return uuSimManager.isMultiSimSupport(context) ? "true" : "false";
    }

    public static String getMainIMSI(Context context) {
        DualSimManager uuSimManager = DualSimManager.getInstance(Build.MODEL);
        return uuSimManager.getSubscriberId(context);
    }

}
