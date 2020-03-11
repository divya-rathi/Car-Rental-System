package com.example.android.loginpage;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity  {


    EditText name, emailAddress,contactNo, username, password, confirmPassword, address;
    Button registerButton;
    TextView alreadyRegistered, login;
    SQLiteDatabase db;

    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    //@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText) findViewById(R.id.Name);
        emailAddress = (EditText) findViewById(R.id.emailAddress);
        address = (EditText) findViewById(R.id.address);
        contactNo = (EditText) findViewById(R.id.contactNo);
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        registerButton = (Button) findViewById(R.id.registerButton);

        alreadyRegistered = (TextView) findViewById(R.id.alreadyRegisteredTextView);
        login = (TextView) findViewById(R.id.loginTextView);


        db = openOrCreateDatabase("CarRentalSystem", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS customer(cust_id INTEGER  PRIMARY KEY AUTOINCREMENT NOT NULL, name VARCHAR, emailAddress VARCHAR, contactNo VARCHAR, address VARCHAR, username VARCHAR, password VARCHAR);");

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(name.getText().toString().trim().length()==0 || emailAddress.getText().toString().trim().length()==0 ||
                        contactNo.getText().toString().trim().length()==0 || address.getText().toString().trim().length()==0 || username.getText().toString().trim().length()==0 ||
                        password.getText().toString().trim().length()==0 || confirmPassword.getText().toString().trim().length()==0){
                    showMessage("Error", "Please enter all values");
                    return;
                }
                if(!(confirmPassword.getText().toString().equals(password.getText().toString()))){
                    Toast.makeText(RegisterActivity.this, "Password didn't match!!", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!emailAddress.getText().toString().trim().matches(emailPattern)) {
                    Toast.makeText(getApplicationContext(),"Invalid email address",Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    String user = username.getText().toString().trim();
                    String pwd = password.getText().toString().trim();

                    Cursor c = db.rawQuery("SELECT * FROM customer", null);

                    int usernameIndex = c.getColumnIndex("username");
                    c.moveToFirst();

                    while (c != null) {

                        if (user.equals(c.getString(usernameIndex))) {
                            Toast.makeText(RegisterActivity.this, "Username already exists!!", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        c.moveToNext();
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }

                db.execSQL("INSERT INTO customer(name, emailAddress, contactNo, address, username, password) VALUES('" + name.getText() + "','" + emailAddress.getText() + "','" +  contactNo.getText()+ "','" + address.getText() + "','" + username.getText() + "','" + password.getText() + "');");
                Toast.makeText(RegisterActivity.this, "You have been registered!!", Toast.LENGTH_SHORT).show();
                clearText();
                return;
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginPage = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(loginPage);

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
    public void clearText(){

        name.setText("");
        emailAddress.setText("");
        contactNo.setText("");
        address.setText("");
        username.setText("");
        password.setText("");
        confirmPassword.setText("");
    }
}
