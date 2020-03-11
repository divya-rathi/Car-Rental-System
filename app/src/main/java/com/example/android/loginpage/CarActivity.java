package com.example.android.loginpage;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class CarActivity extends AppCompatActivity {

    EditText model, regNo, mileage, make, modelYear, status, category;
    EditText regNoR;
    EditText regNoM1, modelM, regNoM, mileageM, makeM, modelYearM, statusM, categoryM;
    Button addCarButton, removeCarButton, modifyCarButton, modifyCarButton1;

    SQLiteDatabase db;

    LinearLayout addCarLayout, removeCarLayout, modifyCarLayout, modifyCarLayout1;

    int regNoExists =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);

        addCarLayout = (LinearLayout) findViewById(R.id.addCarLayout);
        removeCarLayout = (LinearLayout) findViewById(R.id.removeCarLayout);
        modifyCarLayout = (LinearLayout) findViewById(R.id.modifyCarLayout);
        modifyCarLayout1 = (LinearLayout) findViewById(R.id.modifyCarLayout1);

        db = openOrCreateDatabase("CarRentalSystem", Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS car (model VARCHAR NOT NULL, reg_no VARCHAR UNIQUE NOT NULL, mileage INT(2), make VARCHAR, ModelYear INT(4), status VARCHAR, category VARCHAR)");

        Bundle b = getIntent().getExtras();
        String func = b.getString("buttonClicked");

        Log.i("b",func);



        if(func.equals("addcar")){
            addCarLayout.setVisibility(View.VISIBLE);

            model = (EditText) findViewById(R.id.modelEditText);
            regNo = (EditText) findViewById(R.id.regNoEditText);
            mileage = (EditText) findViewById(R.id.mileageEditText);
            make = (EditText) findViewById(R.id.makeEditText);
            modelYear = (EditText) findViewById(R.id.modelYearEditText);
            status = (EditText) findViewById(R.id.statusEditText);
            category = (EditText) findViewById(R.id.categoryEditText);

            addCarButton = (Button) findViewById(R.id.addCarButton);

            addCarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try{
                        Cursor c = db.rawQuery("SELECT * FROM car", null);

                        int regNoIndex = c.getColumnIndex("reg_no");
                        String regno = regNo.getText().toString();
                        String regnodb ="";
                        c.moveToFirst();

                        while(c!=null){
                            regnodb = c.getString(regNoIndex);
                            if(regno.equals(regnodb)){
                                regNoExists = 1;
                                Log.i("reg", regnodb);
                                break;
                            }
                            else{
                                c.moveToNext();
                            }
                        }

                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                    Log.i("exists", Integer.toString(regNoExists));
                    if(model.getText().toString().isEmpty() || regNo.getText().toString().isEmpty() || mileage.getText().toString().isEmpty() || make.getText().toString().isEmpty() || modelYear.getText().toString().isEmpty() || status.getText().toString().isEmpty() || category.getText().toString().isEmpty()){
                        showMessage("Error", "Please enter all values");
                    }
                    else if(regNoExists==1){
                        showMessage("Error", "Registration Number already exists!!");
                        regNoExists =0;
                    }
                    else{
                        db.execSQL("INSERT INTO car VALUES('" + model.getText() + "','" + regNo.getText() + "','" +  mileage.getText() +  "','" + make.getText() + "','" + modelYear.getText() + "','" + status.getText() + "','" + category.getText() + "')");
                        Toast.makeText(CarActivity.this, "Added!!", Toast.LENGTH_SHORT).show();
                        clearText();
                    }
                }
            });

        }

        else if(func.equals("removecar")){

            removeCarLayout.setVisibility(View.VISIBLE);
            regNoR = (EditText) findViewById(R.id.regNoEditTextR);
            removeCarButton = (Button) findViewById(R.id.removeCarButton);
            regNoExists =0;
            removeCarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        Log.i("regnoexists111", Integer.toString(regNoExists));

                        Cursor c = db.rawQuery("SELECT * FROM car", null);
                        int regNoIndex = c.getColumnIndex("reg_no");
                        String regno = regNoR.getText().toString();
                        String regnodb ="";
                        c.moveToFirst();

                        while(c!=null){
                            regnodb = c.getString(regNoIndex);
                            if(regno.equals(regnodb)){
                                regNoExists = 1;
                                Log.i("reg", regnodb);
                                break;
                            }
                            else{
                                c.moveToNext();
                            }
                        }


                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                    if(regNoR.getText().toString().isEmpty()){
                        showMessage("Error", "Enter Registration Number!");
                    }
                    else{

                        Log.i("regnoexists", Integer.toString(regNoExists));
                        if(regNoExists==1){
                            Log.i("Entered", "1st");
                            db.execSQL("DELETE FROM car WHERE reg_no = '" + regNoR.getText() + "'");
                            Log.i("Entered", "2nd");
                            Toast.makeText(CarActivity.this, "Deleted Successfully!!", Toast.LENGTH_SHORT).show();
                            regNoR.setText("");
                        }
                        else {
                            regNoExists=0;
                            Toast.makeText(CarActivity.this, "Doesn't Exists!!", Toast.LENGTH_SHORT).show();
                        }

                    }

                }
            });

        }

        else if (func.equals("modifycar")){
            modifyCarLayout.setVisibility(View.VISIBLE);

            modelM = (EditText) findViewById(R.id.modelEditTextm);
            regNoM = (EditText) findViewById(R.id.regNoEditTextm);
            regNoM1 = (EditText) findViewById(R.id.regNoEditText2);
            mileageM = (EditText) findViewById(R.id.mileageEditTextm);
            makeM = (EditText) findViewById(R.id.makeEditTextm);
            modelYearM = (EditText) findViewById(R.id.modelYearEditTextm);
            statusM = (EditText) findViewById(R.id.statusEditTextm);
            categoryM = (EditText) findViewById(R.id.categoryEditTextm);

            modifyCarButton = (Button) findViewById(R.id.modifyCarButton);
            modifyCarButton1 = (Button) findViewById(R.id.modifyCarButton1);

            modifyCarButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try{
                        Log.i("regnoexists1234", Integer.toString(regNoExists));

                        Cursor c = db.rawQuery("SELECT * FROM car", null);

                        int regNoIndex = c.getColumnIndex("reg_no");
                        String regno = regNoM1.getText().toString();
                        String regnodb ="";
                        c.moveToFirst();

                        while (c!=null){
                            Log.i("regnoexists14", Integer.toString(regNoExists));

                            regnodb=c.getString(regNoIndex);
                            if(regno.equals(regnodb)){
                                regNoExists=1;
                                Log.i("exists", Integer.toString(regNoExists));
                                break;
                            }
                            else{
                                c.moveToNext();
                            }
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }


                    if(regNoM1.getText().toString().isEmpty()){
                        showMessage("Error", "Enter Registration Number!");
                    }
                    try{
                        Log.i("Entered", "2nd");
                        Cursor c = db.rawQuery("SELECT * FROM car", null);

                        int regNoIndex = c.getColumnIndex("reg_no");
                        int modelIndex = c.getColumnIndex("model");
                        int mileageIndex = c.getColumnIndex("mileage");
                        int makeIndex = c.getColumnIndex("make");
                        int modelYearIndex = c.getColumnIndex("ModelYear");
                        int statusIndex = c.getColumnIndex("status");
                        int categoryIndex  = c.getColumnIndex("category");

                        c.moveToFirst();


                        while (c!=null){
                            Log.i("Entered", "third");
                            //regnodb1 = c.getString(regNoIndex);
                            if(regNoM1.getText().toString().equals(c.getString(regNoIndex))){
                                modelM.setText(c.getString(modelIndex));
                                makeM.setText(c.getString(makeIndex));
                                mileageM.setText(c.getString(mileageIndex));
                                modelYearM.setText(c.getString(modelYearIndex));
                                statusM.setText(c.getString(statusIndex));
                                categoryM.setText(c.getString(categoryIndex));

                                break;
                            }
                            else{
                                c.moveToNext();
                            }
                        }
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }

                        if(regNoExists==1) {
                            modifyCarLayout.setVisibility(View.GONE);
                            modifyCarLayout1.setVisibility(View.VISIBLE);
                            regNoM.setText(regNoM1.getText());

                            modifyCarButton1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Log.i("Entered", "4th");
                                    db.execSQL("UPDATE car SET model = '" + modelM.getText() + "', reg_no = '" + regNoM.getText() + "', mileage = '" + mileageM.getText() + "', make = '" + makeM.getText() + "',  ModelYear = '" + modelYearM.getText() + "', status = '" + statusM.getText() + "', category = '" + categoryM.getText() + "' " +" WHERE reg_no = '" + regNoM.getText() + "'");
                                    Toast.makeText(CarActivity.this, "Updated Successfully!!", Toast.LENGTH_SHORT).show();
                                    modifyCarLayout1.setVisibility(View.GONE);
                                    modifyCarLayout.setVisibility(View.VISIBLE);
                                }
                            });

                        }
                        else {
                            if(!(regNoM1.getText().toString().isEmpty())){
                                regNoExists=0;
                                Toast.makeText(CarActivity.this, "Doesn't Exists!!", Toast.LENGTH_SHORT).show();
                            }

                        }






                }
            });


        }

    }

    public void showMessage(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void clearText(){

        model.setText("");
        regNo.setText("");
        mileage.setText("");
        make.setText("");
        modelYear.setText("");
        status.setText("");
        category.setText("");
    }


}
