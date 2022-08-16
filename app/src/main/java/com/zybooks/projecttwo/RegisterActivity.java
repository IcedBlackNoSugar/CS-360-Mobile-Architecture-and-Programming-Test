package com.zybooks.projecttwo;

import static com.zybooks.projecttwo.R.layout.activity_register;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class RegisterActivity extends AppCompatActivity {

    Button CancelButton, RegisterButton;
    EditText TextName, TextPhone, TextEmail, TextPassword;
    boolean isEmptyText;
    UserSQLiteHandler handler;
    SQLiteDatabase db;
    String emailExists = "Email Does not Exist.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_register);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RegisterButton = findViewById(R.id.buttonRegister);
        CancelButton = findViewById(R.id.buttonCancel);
        TextName = findViewById(R.id.editTextRegisterName);
        TextPhone = findViewById(R.id.editTextRegisterPhoneNumber);
        TextEmail = findViewById(R.id.editTextRegisterEmail);
        TextPassword = findViewById(R.id.editTextRegisterPassword);

        handler = new UserSQLiteHandler(this);

        RegisterButton.setOnClickListener(view -> {
            String message = CheckTextBoxEmpty();

            if (!isEmptyText){
                CheckUserAlreadyExists();
                Toast.makeText(RegisterActivity.this,"User has been created.",Toast.LENGTH_LONG).show();
                this.finish();
            }else{
                Toast.makeText(this, message, Toast.LENGTH_LONG).show();
            }

        });

        CancelButton.setOnClickListener(view -> {
            startActivity(new Intent(RegisterActivity.this, MainActivity.class));
            this.finish();
        });



    }
    public void InsertUserDatabase(){

        String name = TextName.getText().toString().trim();
        String password = TextPassword.getText().toString().trim();
        String email = TextEmail.getText().toString().trim();
        String phone = TextPhone.getText().toString().trim();

        User user = new User(name, password, email, phone);
        handler.createUser(user);

        Toast.makeText(RegisterActivity.this, "User Created Successfully", Toast.LENGTH_LONG).show();

        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
        this.finish();
    }

        public void CheckUserAlreadyExists(){
            String email = TextEmail.getText().toString().trim();
            db = handler.getReadableDatabase();

            Cursor cursor = db.query(UserSQLiteHandler.TABLE_NAME,
                    null,
                    " "
                            + UserSQLiteHandler.COLUMN_3_EMAIL + "= ?",
                    new String[]{email},
                    null,
                    null,
                    null);

            while (cursor.moveToNext()) {
                if (cursor.isFirst()) {
                    cursor.moveToFirst();
                    // If email exists then set result variable value as Email Found
                    emailExists = "Email Found";
                    // Closing cursor.
                    cursor.close();
                }
            }
            handler.close();

            if(emailExists.equalsIgnoreCase("Email Found")){
                Toast.makeText(RegisterActivity.this, "Email already exists in the database", Toast.LENGTH_LONG).show();

            }else{
                InsertUserDatabase();
            }
            emailExists = "Email Does not Exist.";
        }


        public String CheckTextBoxEmpty() {
            String message = "";
            String name = TextName.getText().toString().trim();
            String password = TextPassword.getText().toString().trim();
            String email = TextEmail.getText().toString().trim();
            String phone = TextPhone.getText().toString().trim();

            if (name.isEmpty()) {
                TextName.requestFocus();
                isEmptyText = true;
                message = "Name is Empty.";
            } else if (password.isEmpty()) {
                TextPassword.requestFocus();
                isEmptyText = true;
                message = "Password is Empty.";

            } else if (email.isEmpty()) {
                TextEmail.requestFocus();
                isEmptyText = true;
                message = "Email is Empty.";

            } else if (phone.isEmpty()) {
                TextPhone.requestFocus();
                isEmptyText = true;
                message = "Phone is Empty.";
            } else {
                isEmptyText = false;
            }

            return message;

            }



    }

