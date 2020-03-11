package com.example.android.loginpage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Booking extends AppCompatActivity {

    SQLiteDatabase db;
    String bdate, rdate, driverbtn, location;
    Date bookingdate_date, returndate_date;
    TextView regno, category, model, make, driver, bookingdate, returndate, drivercost, carcost;
    String custid, driverid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        Bundle b=getIntent().getExtras();
        String reg=b.getString("regno");
        bdate=b.getString("bdate");
        rdate=b.getString("rdate");
        driverbtn=b.getString("driver");
        location=b.getString("location");
        SharedPreferences sp = getSharedPreferences("CustomerId", Context.MODE_PRIVATE);
        custid = sp.getString("custId", "NA");

        Log.i("custidd", custid);

        ArrayList<Integer> did = new ArrayList<>();



        db = openOrCreateDatabase("CarRentalSystem", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS car (model VARCHAR NOT NULL, reg_no VARCHAR UNIQUE NOT NULL, mileage INT(2), make VARCHAR, ModelYear INT(4), status VARCHAR, category VARCHAR)");
        db.execSQL("CREATE TABLE IF NOT EXISTS booking (booking_id INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL, FromDateTime VARCHAR, ReturnDateTime VARCHAR, ActualReturnDateTime VARCHAR, cust_id VARCHAR, driver_id VARCHAR, Reg_No VARCHAR, Amount NUMERIC(10,2), ExtraCharge NUMERIC(7,2), location VARCHAR)");
        //db.execSQL("CREATE TABLE IF NOT EXISTS billing (bill_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, bill_status VARCAR, ");
        //db.execSQL("CREATE TABLE  driver (driver_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, driver_name VARCHAR, driver_phno VARCHAR, driver_lic VARCHAR, driver_status VARCHAR)");
        //db.execSQL("DROP TABLE diver");
       // db.execSQL("insert into driver(driver_name, driver_phno, driver_lic, driver_status) values('Murugan','9999912121','ZA8745673','Available');");
       // db.execSQL("insert into driver(driver_name, driver_phno, driver_lic, driver_status) values('Karthik','9834567880', 'AX9812111','Available');");
        //db.execSQL("insert into driver(driver_name, qdriver_phno, driver_lic, driver_status) values('Ajay','9435799082', 'TT9876788','Unavailable');");
        //db.execSQL("insert into driver(driver_name, driver_phno, driver_lic, driver_status) values('Naveen','6949806802', 'ZA1223338','Available');");
        //db.execSQL("insert into driver(driver_name, driver_phno, driver_lic, driver_status) values('Praneshia','9564926578', 'LL9987567','Unavailable');");
        //db.execSQL("insert into driver(driver_name, driver_phno, driver_lic, driver_status) values('John','8457990876','KL5676459','Available');");
        //db.execSQL("DELETE FROM booking");
         regno = (TextView) findViewById(R.id.regnoTextView);
         category = (TextView) findViewById(R.id.categoryTextView);
         model = (TextView) findViewById(R.id.modelTextView);
         make = (TextView) findViewById(R.id.makeTextView);
         driver = (TextView) findViewById(R.id.driverTextView);
         bookingdate = (TextView) findViewById(R.id.bookingdateTextView);
         returndate = (TextView) findViewById(R.id.returndateTextView);
         drivercost = (TextView) findViewById(R.id.drivercostTextView);
         carcost = (TextView) findViewById(R.id.carcostTextView);

        Button confirmBookingButton = (Button) findViewById(R.id.confirmBookingButton);
        regno.setText(reg);


        driver.setText(driverbtn);
        bookingdate.setText(bdate);
        returndate.setText(rdate);

        try{

            Cursor c = db.rawQuery("SELECT * FROM car", null);
            int regNoIndex = c.getColumnIndex("reg_no");

            c.moveToFirst();
            while (c!=null){

                if (reg.equals(c.getString(regNoIndex))) {
                    model.setText(c.getString(c.getColumnIndex("model")));
                    category.setText(c.getString(c.getColumnIndex("category")));
                    make.setText(c.getString(c.getColumnIndex("make")));
                }
                    c.moveToNext();
            }




        }
        catch (Exception e){
            e.printStackTrace();
        }

        try{
            Cursor c1 = db.rawQuery("SELECT * FROM driver", null);
            int idIndex = c1.getColumnIndex("driver_id");
            c1.moveToFirst();
            while (c1!=null){
                Log.i("item", c1.getString(idIndex));
                did.add(Integer.parseInt(c1.getString(idIndex)));
                c1.moveToNext();
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        for (int item : did){
            Log.i("Member name12: ", Integer.toString(item) + "xy");
        }

        Random rand = new Random();
        Integer randomInt = did.get(rand.nextInt(did.size()));
        driverid = Integer.toString(randomInt);
        Log.i("ra", driverid);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        try {
            bookingdate_date = sdf.parse(bookingdate.getText().toString());
            returndate_date = sdf.parse(returndate.getText().toString());

        } catch (ParseException e) {
            e.printStackTrace();
        }

        long dc, cc;

        if(category.getText().toString().equals("Sedan")){
            cc = 1500;
        }
        else if(category.getText().toString().equals("SUV")){
            cc = 1990;
        }
        else if(category.getText().toString().equals("Mini")){
            cc = 1100;
        }
        else if(category.getText().toString().equals("Minivan")){
            cc = 1400;
        }
        else if(category.getText().toString().equals("HatchBack")){
            cc = 2250;
        }
        else{
            cc = 980;
        }
        long diff = returndate_date.getTime() - bookingdate_date.getTime();
        long noOfDays = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);

        Log.i("noOfDays", Long.toString(noOfDays));
        if (noOfDays < 10 ){
            dc = noOfDays*700;
            cc = (long) (cc*noOfDays - (0.15*cc*noOfDays));
        }
        else {
            dc = noOfDays*500;
            cc = cc*noOfDays;
        }

        if(driver.getText().toString().equals("No")){
            drivercost.setText("0.00 INR");
        }
        else{
            drivercost.setText(Long.toString(dc) + " INR");
        }

        carcost.setText(Long.toString(cc));

        drivercost.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean  onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    if (event.getX() >= (drivercost.getRight() - drivercost.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())){
                        showMessage("Driver Cost", "Rs. 700/day. If service is chosen for more than 10 days, the driver cost is Rs. 500/day ");
                        return true;
                    }
                }
                return false;

            }
        });

        carcost.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean  onTouch(View v, MotionEvent event) {
                final int DRAWABLE_LEFT = 0;
                final int DRAWABLE_TOP = 1;
                final int DRAWABLE_RIGHT = 2;
                final int DRAWABLE_BOTTOM = 3;

                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    if (event.getX() >= (carcost.getRight() - carcost.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())){
                        showMessage("Car Cost", " Sedan: 1500/day \n SUV: 1990/day \n MiniVan: 1400/day \n Mini: 1100/day \n HatchBack: 2250/day \n Others: 980/day \n 15% Discount is given if the number of days exceeds 10.");

                        return true;
                    }
                }
                return false;

            }
        });

        final long total = cc + dc;
        confirmBookingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.execSQL("INSERT INTO booking(FromDateTime, ReturnDateTime, ActualReturnDateTime, cust_id, driver_id,  Reg_No, Amount, ExtraCharge, location) VALUES ('" +bookingdate.getText() + "','"  +returndate.getText() + "','" + returndate.getText() + "','" + custid + "','" + driverid + "','" + regno.getText() + "','" + total + "','0.00','"  +  location + "');");
                db.execSQL("UPDATE car SET status = 'Unavailable' WHERE reg_no = '" + regno.getText().toString() + "'");
                db.execSQL("UPDATE driver SET driver_status = 'Unavailable' WHERE driver_id = '" + driverid + "'");
                Toast.makeText(Booking.this, "Booking Confirmed!!", Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}
