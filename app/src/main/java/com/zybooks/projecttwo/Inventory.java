package com.zybooks.projecttwo;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


public class Inventory extends AppCompatActivity {

    String emailHolder;
    Button smsButton, addButton;
    androidx.appcompat.app.AlertDialog AlertDialog = null;
    public static final String userEmail ="";
    private static final int USER_PERMISSIONS_REQUEST_SEND_SMS = 0;
    private static boolean smsAuthorized = false;

    boolean emptyHolder;
    private Button newAddItem, newAddItemConfirm, newCancelItem;
    private String newItemEmail, nameHolder, unitHolder, quantityHolder;
    private EditText newItemName, newItemCount, newItemUnit;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    ItemSQLiteHandler db;



    RecyclerView mRecyclerView;
    ArrayList<Item> data = new ArrayList<>();
    List<Item> dbData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.inventory_grid);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AtomicReference<Intent> intent = new AtomicReference<Intent>(getIntent());

        emailHolder = intent.get().getStringExtra(MainActivity.emailHolder);


        smsButton = findViewById(R.id.sms_text_permissions);
        addButton = findViewById(R.id.add_item_button);
        mRecyclerView = findViewById(R.id.recyclerView);






        data.add((new Item("Steve","Coffee Beans (In Pounds)", "45", "lb")));
        data.add((new Item("Steve","Hot Cups (In Sleeves)", "14", "lb")));
        data.add((new Item("Steve","Iced Cups (In Sleeves)", "15", "lb")));
        data.add((new Item("Steve","Chocolate Syrup", "14", "lb")));
        data.add((new Item("Steve","Sugar Syrup", "1", "lb")));
        data.add((new Item("Steve","Caramel Syrup", "34", "lb")));
        data.add((new Item("Steve","Chai Tea Mix", "19", "lb")));
        data.add((new Item("Steve","Mocha Syrup", "14", "lb")));
        data.add((new Item("Steve","Ice", "1", "lb")));
        data.add((new Item("Steve","Sugar (In Pounds)", "34", "lb")));
        data.add((new Item("Steve","Milk (Full Fat)", "19", "lb")));
        data.add((new Item("Steve","Napkin Boxes", "14", "lb")));
        data.add((new Item("Steve","Half and Half", "1", "lb")));
        data.add((new Item("Steve","Hot Lids", "34", "lb")));
        data.add((new Item("Steve","Iced Lids", "19", "lb")));
        data.add((new Item("Steve","Stirrers", "14", "lb")));

        smsButton.setOnClickListener(view ->{
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.SEND_SMS)
                    != PackageManager.PERMISSION_GRANTED) {

                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                        Manifest.permission.SEND_SMS)) {
                    Toast.makeText(this,"Device SMS Permission is Needed", Toast.LENGTH_LONG).show();
                } else {
                    ActivityCompat.requestPermissions(this,
                            new String[] {Manifest.permission.SEND_SMS},
                            USER_PERMISSIONS_REQUEST_SEND_SMS);
                }
            } else {
                Toast.makeText(this,"Device SMS Permission is Allowed", Toast.LENGTH_LONG).show();
            }
            // Open SMS Alert Dialog
            AlertDialog = AD_SMSNotifications.smsButton(this);
            AlertDialog.show();

        });

        db = new ItemSQLiteHandler(this);


        AdapterClass adapter = new AdapterClass(data);
        mRecyclerView.setAdapter(adapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));



        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                createNewItemDialog();

                dbData = db.getAllItems();

                for (Item item : dbData){
                    if (!data.contains(item)){
                        data.add(new Item(item.getUser_email(),item.getName(), item.getCount(), item.getUnit()));
                    }

                }
                adapter.notifyDataSetChanged();
            }
        });

    }

    public void createNewItemDialog(){
        dialogBuilder = new AlertDialog.Builder(this);
        final View addItemDialog = getLayoutInflater().inflate(R.layout.add_item_popup, null);

        newItemName = (EditText) addItemDialog.findViewById(R.id.add_item_name);
        newItemCount = (EditText) addItemDialog.findViewById(R.id.add_item_count);
        newItemUnit = (EditText) addItemDialog.findViewById(R.id.add_item_unit);

        newAddItemConfirm = (Button) addItemDialog.findViewById(R.id.add_item_dialog_save);
        newCancelItem = (Button) addItemDialog.findViewById(R.id.add_item_cancel);

        dialogBuilder.setView(addItemDialog);
        dialog = dialogBuilder.create();
        dialog.show();


        newAddItemConfirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                InsertItemDatabase();            }
        });

        newCancelItem.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        }));
    }

    public static void AllowSendSMS() {
        smsAuthorized = true;
    }

    public static void DenySendSMS() {
        smsAuthorized = false;
    }

    public void InsertItemDatabase(){
        String message = checkTextBoxNotEmpty();

        if (!emptyHolder){
            String email = emailHolder;
            String name = nameHolder;
            String unit = unitHolder;
            String quantity = quantityHolder;

            Item item = new Item(email,name, quantity, unit);

            db = new ItemSQLiteHandler(this);

            Toast.makeText(this,"Item Added Successfully",Toast.LENGTH_LONG).show();

            Intent add = new Intent();
            setResult(RESULT_OK, add);

            dialog.dismiss();


        }

    }

    public String checkTextBoxNotEmpty(){

        String message = "";

        nameHolder = newItemName.getText().toString().trim();
        unitHolder = newItemUnit.getText().toString().trim();
        quantityHolder = newItemCount.getText().toString().trim();

        if (nameHolder.isEmpty()){
            newItemName.requestFocus();
            emptyHolder = true;
            message = "Item Name is Empty.";
        } else if (unitHolder.isEmpty()){
            newItemUnit.requestFocus();
            emptyHolder = true;
            message = "Item Unit is Empty.";
        } else if (quantityHolder.isEmpty()){
            newItemCount.requestFocus();
            emptyHolder = true;
            message = "Item Quantity is Empty.";
        }
        return message;
    }
}