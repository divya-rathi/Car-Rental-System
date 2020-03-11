package com.example.android.loginpage;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginAdmin extends AppCompatActivity {

    EditText usernameEditText, passwordEditText;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        usernameEditText = (EditText) findViewById(R.id.usernameEditText);
        passwordEditText = (EditText) findViewById(R.id.passwordEditText);
        loginButton = (Button) findViewById(R.id.loginButton);

       // usernameEditText.getBackground().setColorFilter(RED, PorterDuff.Mode.SRC_IN);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(usernameEditText.getText().toString().equals("a")){
                    if(passwordEditText.getText().toString().equals("a")){
                        Toast.makeText(LoginAdmin.this,"Success!",Toast.LENGTH_SHORT).show();
                        Intent adminActivity = new Intent(LoginAdmin.this, AdminActivities.class);
                        startActivity(adminActivity);
                    }
                    else{
                        Toast.makeText(LoginAdmin.this,"Incorrect!",Toast.LENGTH_SHORT).show();

                    }
                }
                else{
                    Toast.makeText(LoginAdmin.this,"Invalid Credentials!",Toast.LENGTH_SHORT).show();

                }
            }
        });
    }
}
