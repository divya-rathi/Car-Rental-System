package com.example.android.loginpage;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DisplayCar extends AppCompatActivity {

    SQLiteDatabase db;
    ArrayList<String> listItem;
    ArrayAdapter<String> adapter;
    TextView categoryName;
    List<String> categories = new ArrayList<String>();
    String bdate, rdate, driver, loc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_car);

        Bundle b=getIntent().getExtras();
        String cat=b.getString("category");
         bdate=b.getString("bdate");
        rdate=b.getString("rdate");
         driver=b.getString("driver");
         loc = b.getString("location");

       db = openOrCreateDatabase("CarRentalSystem", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS car (model VARCHAR NOT NULL, reg_no VARCHAR UNIQUE NOT NULL, mileage INT(2), make VARCHAR, ModelYear INT(4), status VARCHAR, category VARCHAR)");


        listItem = new ArrayList<>();

        final ListView listView =  (ListView) findViewById(R.id.listView);
        categoryName = (TextView) findViewById(R.id.categoryNameTextView);

        categoryName.setText(cat + "  Cars" );


        try{

            Cursor c = db.rawQuery("SELECT * FROM car", null);
            int statusIndex = c.getColumnIndex("status");
            c.moveToFirst();
            while (c!=null){
                //Log.i("hello",c.getString(categoryIndex));
                if ((c.getString(statusIndex)).equals("Available")){
                    if(c.getString(c.getColumnIndex("category")).equals(cat)){
                        Log.i("car", c.getString((c.getColumnIndex("model"))));
                        listItem.add(c.getString(c.getColumnIndex("model")) + "  " + c.getString(c.getColumnIndex("reg_no")));

                    }
                }
                c.moveToNext();
            }



        }
        catch (Exception e){
            e.printStackTrace();
        }

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listItem);
        listView.setAdapter(adapter);




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(DisplayCar.this, listItem.get(position), Toast.LENGTH_LONG);
                String lineOfItems = listItem.get(position);
                String[] carDetails = lineOfItems.split("  ");
                Log.i("reg No:", carDetails[1]);
                Log.i("Model No: ", carDetails[0]);

Log.i("loca", loc);
                Intent bookedCarDetails = new Intent(DisplayCar.this, Booking.class);
                bookedCarDetails.putExtra("regno", carDetails[1]);
                bookedCarDetails.putExtra("bdate", bdate);
                bookedCarDetails.putExtra("rdate", rdate);
                bookedCarDetails.putExtra("driver", driver);
                bookedCarDetails.putExtra("location", loc);
                startActivity(bookedCarDetails);
            }
        });


    }
}
