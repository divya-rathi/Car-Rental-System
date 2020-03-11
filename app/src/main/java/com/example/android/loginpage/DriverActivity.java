package com.example.android.loginpage;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class DriverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);

        Bundle b = getIntent().getExtras();
        String func = b.getString("buttonClicked");

        Log.i("b",func);
    }
}
