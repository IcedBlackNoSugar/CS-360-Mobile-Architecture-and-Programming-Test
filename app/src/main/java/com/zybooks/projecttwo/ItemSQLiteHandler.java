package com.zybooks.projecttwo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ItemSQLiteHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "ItemDatabase.db";
    private static final String TABLE_NAME = "ItemsTable";

    private static final String COLUMN_0_ID = "id";
    private static final String COLUMN_1_USER_EMAIL = "userEmail";
    private static final String COLUMN_2_ITEM_DESCRIPTION = "itemDescription";
    private static final String COLUMN_3_QUANTITY = "itemQuantity";
    private static final String COLUMN_4_UNIT = "itemUnit";

    private static final String CREATE_ITEMS_TABLE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" + COLUMN_0_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_1_USER_EMAIL + " VARCHAR, " +
            COLUMN_2_ITEM_DESCRIPTION + " VARCHAR, " +
            COLUMN_3_QUANTITY + " VARCHAR, " +
            COLUMN_4_UNIT + "VARCHAR" + ");";

    public ItemSQLiteHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) { db.execSQL(CREATE_ITEMS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
    *Database CRUD Operations
    */

    public void createItem(Item item){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_1_USER_EMAIL, item.getUser_email());
        values.put(COLUMN_2_ITEM_DESCRIPTION, item.getName());
        values.put(COLUMN_3_QUANTITY, item.getCount());
        values.put(COLUMN_4_UNIT, item.getUnit());

        db.insert(TABLE_NAME, null, values);
        db.close();

    }


    public Item readItem(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[] { COLUMN_0_ID, COLUMN_1_USER_EMAIL, COLUMN_2_ITEM_DESCRIPTION, COLUMN_3_QUANTITY, COLUMN_4_UNIT}, COLUMN_0_ID + " = ?",
                new String[] {String.valueOf(id)}, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Item item = new Item(Integer.parseInt((Objects.requireNonNull(cursor).getString(0))
        ), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));

        cursor.close();

        return item;
    }


    public int updateItem(Item item){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_1_USER_EMAIL, item.getUser_email());
        values.put(COLUMN_2_ITEM_DESCRIPTION, item.getName());
        values.put(COLUMN_3_QUANTITY, item.getCount());
        values.put(COLUMN_4_UNIT, item.getUnit());

        return db.update(TABLE_NAME, values, COLUMN_0_ID + " = ?", new String[] {String.valueOf(item.getId())});

    }

    public void deleteItem(Item item){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, COLUMN_0_ID + " = ?",
                new String[] { String.valueOf(item.getId())});
        db.close();
    }

    //Get all Items
    public List<Item> getAllItems(){
        List<Item> itemList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()){
            do{
                Item item = new Item();
                item.setId(Integer.parseInt(cursor.getString(0)));
                item.setUser_email(cursor.getString(1));
                item.setName(cursor.getString(2));
                item.setCount(cursor.getString(3));
                item.setUnit(cursor.getString(4));

            }
            while (cursor.moveToNext());

        }
        cursor.close();

        return itemList;
    }

    public void deleteAllItems(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME,null,null);
        db.close();
    }
}
