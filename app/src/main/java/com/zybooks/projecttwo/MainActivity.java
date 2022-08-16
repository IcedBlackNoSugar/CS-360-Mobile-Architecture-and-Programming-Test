package com.zybooks.projecttwo;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static String emailHolder;
    SQLiteDatabase db;
    UserSQLiteHandler handler;
    TextView textUsername, textPassword;
    String nameHolder, phoneHolder;
    boolean isEmptyText;
    String tempPassword = "Not Found";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        textPassword = findViewById(R.id.editTextTextPassword);
        textUsername = findViewById(R.id.editTextTextPersonName);
        handler = new UserSQLiteHandler(this);


        registerButton();
        loginButton();


    }

    public String CheckTextBoxEmpty() {
        String message = "";

        String password = textPassword.getText().toString().trim();
        String email = textUsername.getText().toString().trim();


        if (password.isEmpty()) {
            textPassword.requestFocus();
            isEmptyText = true;
            message = "Password is Empty.";

        } else if (email.isEmpty()) {
            textUsername.requestFocus();
            isEmptyText = true;
            message = "Email is Empty.";
        } else {
            isEmptyText = false;
        }

        return message;

    }

    @SuppressLint("Range")
    public void login(){
        String message = CheckTextBoxEmpty();

        if(!isEmptyText){
            db = handler.getWritableDatabase();
            emailHolder = textUsername.getText().toString().trim();

            Cursor cursor = db.query(UserSQLiteHandler.TABLE_NAME,
                    null,
                    " " +
                            UserSQLiteHandler.COLUMN_3_EMAIL + "=?",
                    new String[]{emailHolder},
                    null,
                    null,
                    null);

            while (cursor.moveToNext()){
                if (cursor.isFirst()){
                    cursor.moveToFirst();

                    tempPassword = cursor.getString(cursor.getColumnIndex(UserSQLiteHandler.COLUMN_4_PASSWORD));
                    nameHolder = cursor.getString(cursor.getColumnIndex(UserSQLiteHandler.COLUMN_1_NAME));
                    phoneHolder = cursor.getString(cursor.getColumnIndex(UserSQLiteHandler.COLUMN_2_PHONE_NUMBER));

                    cursor.close();
                }
            }
        }
        handler.close();

        passwordCheck();
    }
    private void registerButton(){
        Button button = findViewById(R.id.registerButton);
        button.setOnClickListener((v -> {
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            startActivity(intent);
        }));
    }

    public void passwordCheck(){
        String checkPassword = textPassword.getText().toString().trim();
        if(tempPassword.equals(checkPassword)){
            Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();

            Bundle bundle = new Bundle();
            bundle.putString("user_email",emailHolder);
            bundle.putString("user_name", nameHolder);
            bundle.putString("user_password", tempPassword);
            bundle.putString("user_phone", phoneHolder);

            Intent intent = new Intent(MainActivity.this, Inventory.class);
            EmptyEditTextAfterDataInsert();
            startActivity(intent);
        }else{
            Toast.makeText(MainActivity.this, "Username or Password is wrong.", Toast.LENGTH_LONG).show();

        }
    }
    private void loginButton(){
        Button button = findViewById(R.id.loginButton);
        button.setOnClickListener((v -> login()));

    }

    public void EmptyEditTextAfterDataInsert() {
        textUsername.setText("");
        textPassword.setText("");
    }
}