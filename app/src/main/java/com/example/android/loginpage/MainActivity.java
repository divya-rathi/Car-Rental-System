package com.example.android.loginpage;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by rathi on 01-Sep-19.
 */

public class MainActivity extends AppCompatActivity {
    EditText username, password;
    Button loginButton;
    TextView register, loginForAdmin;
    SQLiteDatabase db;
    String custid;

    int incorrect =-1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.usernameEditText);
        password = (EditText) findViewById(R.id.passwordEditText);

        loginButton = (Button) findViewById(R.id.loginButton);

        register = (TextView) findViewById(R.id.registerTextView);
        loginForAdmin = (TextView) findViewById(R.id.loginForAdminTextView);


        db = openOrCreateDatabase("CarRentalSystem", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS customer(cust_id INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL, name VARCHAR, emailAddress VARCHAR, contactNo VARCHAR, address VARCHAR, username VARCHAR, password VARCHAR);");

        loginForAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent adminPage = new Intent(MainActivity.this, LoginAdmin.class);
                startActivity(adminPage);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerPage = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registerPage);
            }
        });

        final SharedPreferences sp = getSharedPreferences("CustomerId", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = sp.edit();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String user = username.getText().toString().trim();
                    String pwd = password.getText().toString().trim();

                    Cursor c = db.rawQuery("SELECT * FROM customer", null);

                    int usernameIndex = c.getColumnIndex("username");
                    int passwordIndex = c.getColumnIndex("password");
                    int custidIndex = c.getColumnIndex("cust_id");

                    c.moveToFirst();

                    while (c != null) {

                        Log.i("username", c.getString(usernameIndex));
                        Log.i("password", c.getString(passwordIndex));
                       // c.moveToNext();
                        if(user.equals(c.getString(usernameIndex))){
                            if(pwd.equals(c.getString(passwordIndex))){
                                incorrect =0;
                                custid = c.getString(custidIndex);
                                editor.putString("custId", custid);
                                editor.commit();
                                Log.i("custiddddd", sp.getString("custId", "NA"));

                                Toast.makeText(MainActivity.this, "Logged-in Successfully", Toast.LENGTH_SHORT).show();

                                Intent bookCar = new Intent(MainActivity.this, CarBooking.class);
                                startActivity(bookCar);
                            }
                            else{
                                incorrect =0;
                                Toast.makeText(MainActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                        else{
                            incorrect = -1;
                        }
                        c.moveToNext();
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
                if(incorrect==-1){
                    Toast.makeText(MainActivity.this, "User Doesn't Exist", Toast.LENGTH_SHORT).show();
                }


            }
        });

    }
}