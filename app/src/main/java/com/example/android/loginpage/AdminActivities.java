package com.example.android.loginpage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class AdminActivities extends AppCompatActivity implements View.OnClickListener {

    Button addCar, removeCar, modifyCar;
    Button addDriver, removeDriver, modifyDriver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_activities);

        addCar = (Button) findViewById(R.id.addCarButton);
        removeCar = (Button) findViewById(R.id.removeCarButton);
        modifyCar = (Button) findViewById(R.id.modifyCarButton);
        addDriver = (Button) findViewById(R.id.addDriverButton);
        removeDriver = (Button) findViewById(R.id.removeDriverButton);
        modifyDriver = (Button) findViewById(R.id.modifyDriverButton);

        addCar.setOnClickListener(this);
        removeCar.setOnClickListener(this);
        modifyCar.setOnClickListener(this);
        addDriver.setOnClickListener(this);
        removeDriver.setOnClickListener(this);
        modifyDriver.setOnClickListener(this);

        addDriver.setEnabled(false);
        removeDriver.setEnabled(false);
        modifyDriver.setEnabled(false);


    }
    @Override
    public void onClick(View v) {
        String function="";
        Intent driverOrCar;
        if(v==addCar){
            function = "addcar";
        }
        else if(v==removeCar){
            function="removecar";
        }
        else if(v==modifyCar){
            function="modifycar";
        }
        else if(v==addDriver){
            function = "adddriver";
        }
        else if(v==removeDriver){
            function="removedriver";
        }
        else if(v==removeDriver){
            function="modifydriver";
        }
        else
            ;

        if(function.equals("addcar") || function.equals("removecar") ||function.equals("modifycar")){
            driverOrCar = new Intent(this, CarActivity.class);
        }
        else{
            driverOrCar = new Intent(this, DriverActivity.class);
        }


        driverOrCar.putExtra("buttonClicked", function);
        startActivity(driverOrCar);

    }
}
