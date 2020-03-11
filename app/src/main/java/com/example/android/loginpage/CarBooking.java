package com.example.android.loginpage;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CarBooking extends AppCompatActivity  {

     EditText bookingDate;
     EditText returnDate;
     DatePickerDialog[] datePickerDialog;
     Spinner spinner;
     Spinner locSpinner;
    List<String> categories = new ArrayList<String>();
    List<String> locations = new ArrayList<String>();
    Button nextButton;
     SQLiteDatabase db;
    String driver = "";
   // Date bookingdate_date, returndate_date;
    //long noOfDays, diff;


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radioButton:
                if (checked)
                    driver="Yes";
                    break;
            case R.id.radioButton2:
                if (checked)
                    driver="No";
                    break;
        }

        Log.i("Driver: ", driver);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_booking);

        datePickerDialog = new DatePickerDialog[1];

        bookingDate = findViewById(R.id.bookingDateEditText);
        returnDate = findViewById(R.id.returnDateEditText);
        spinner = findViewById(R.id.spinner);
        locSpinner = findViewById(R.id.locationSpinner);

        nextButton = (Button) findViewById(R.id.nextButton);

        RadioButton yesradioButton = (RadioButton) findViewById(R.id.radioButton);
        RadioButton noradioButton = (RadioButton) findViewById(R.id.radioButton2);

        db = openOrCreateDatabase("CarRentalSystem", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS car (model VARCHAR NOT NULL, reg_no VARCHAR UNIQUE NOT NULL, mileage INT(2), make VARCHAR, ModelYear INT(4), status VARCHAR, category VARCHAR)");
//        db.execSQL("CREATE TABLE Location (address VARCHAR, lname VARCHAR, locId VARCHAR)");
       // db.execSQL("INSERT INTO car VALUES('Civic','TN31BZ3499',23,'Honda', 2018, 'Available','Sedan')");
        //db.execSQL("INSERT INTO car(model, reg_no, mileage, make, ModelYear, status, category) VALUES('Prius','TN70R7000',24,'Toyota', 2016, 'Available','SUV')");
       // db.execSQL("INSERT INTO car VALUES('Corona','TN37AD2099',24,'Toyota',1973,'Unavailable','MiniVan')");
       //db.execSQL("INSERT INTO car VALUES('Civic','TS34AM2720',23,'Honda',2018,'Available','Hatchback')");
       //db.execSQL("INSERT INTO car VALUES('Polo','KL17VA1826',23,'Volkswagen',2017,'Available','Sedan')");
       //db.execSQL("INSERT INTO car VALUES('Sentra','PY01DH1234',24,'Nissan',2017,'Unavailable','Coupe')");

        //db.execSQL("insert into Location values('Omni Bus Stand,Gandhipuram,Coimbatore','Gandhipuram','OB34')");
        //db.execSQL("insert into Location values('Ghandhipuram Bus Stand Front Gate,Coimbatore','Gandhipuram','GN12')");
        //db.execSQL("insert into Location values('Ukkadam Bus Stand Back Gate,Coimbatore','Ukkadam','UKK4')");
       // db.execSQL("insert into Location values('Amrita Vishwa Vidyapeetham,Ettimadai,Coimbatore','Ettimadai','ETT11')");
      //  db.execSQL("insert into Location values('Infront of SBI ATM, Saibaba colony,Coimbatore','Saibaba Colony','SBC11')");

        //db.execSQL("UPDATE car SET status = 'Available' WHERE status = 'Unavailable' AND reg_no <> 'KL17VA1826'");

        bookingDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                final int month = calendar.get(Calendar.MONTH);
                //int date = calendar.get(Calendar.DATE);
                final int day = calendar.get(Calendar.DAY_OF_MONTH);
                datePickerDialog[0] = new DatePickerDialog(CarBooking.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        bookingDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);

                    }
                }, year, month, day);
                datePickerDialog[0].getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog[0].show();
            }
        });

        returnDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar1 = Calendar.getInstance();
                int year = calendar1.get(Calendar.YEAR);
                final int month = calendar1.get(Calendar.MONTH);
                //int date = calendar.get(Calendar.DATE);
                final int day = calendar1.get(Calendar.DAY_OF_MONTH);
                datePickerDialog[0] = new DatePickerDialog(CarBooking.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        returnDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, year, month, day);
                datePickerDialog[0].getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog[0].show();
            }
        });

//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        try {
//            bookingdate_date = sdf.parse(bookingDate.getText().toString());
//            returndate_date = sdf.parse(returnDate.getText().toString());
//
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        diff = returndate_date.getTime() - bookingdate_date.getTime();
//        noOfDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        try{

            Cursor c = db.rawQuery("SELECT DISTINCT(category) FROM car", null);

            int categoryIndex = c.getColumnIndex("category");

            c.moveToFirst();

            while(c!=null){
                Log.i("category", c.getString(categoryIndex));
                categories.add(c.getString(categoryIndex));
                c.moveToNext();
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }

        try{

            Cursor c = db.rawQuery("SELECT DISTINCT(lname) FROM Location", null);

            int lIndex = c.getColumnIndex("lname");

            c.moveToFirst();

            while(c!=null){
                Log.i("lname", c.getString(lIndex));
                locations.add(c.getString(lIndex));
                c.moveToNext();
            }

        }
        catch (Exception e){
            e.printStackTrace();
        }


        //ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, categories);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(CarBooking.this,  android.R.layout.simple_list_item_1, categories);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getApplicationContext(), spinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> locadapter = new ArrayAdapter<String>(CarBooking.this,  android.R.layout.simple_list_item_1, locations);
        locSpinner.setAdapter(locadapter);

        locSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Toast.makeText(getApplicationContext(), locSpinner.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });





        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                if(noOfDays<0){
//                    Toast.makeText(CarBooking.this, "Enter dates properly!", Toast.LENGTH_SHORT).show();
//                }
//                else{
                    Intent displayCars = new Intent(CarBooking.this, DisplayCar.class);

                    Log.i("bbbb", bookingDate.getText().toString() );
                    displayCars.putExtra("category", spinner.getSelectedItem().toString());
                    displayCars.putExtra("bdate", bookingDate.getText().toString());
                    displayCars.putExtra("rdate", returnDate.getText().toString());
                    displayCars.putExtra("driver", driver);
                    displayCars.putExtra("location", locSpinner.getSelectedItem().toString());
                    startActivity(displayCars);

               // }

            }
        });
    }

}