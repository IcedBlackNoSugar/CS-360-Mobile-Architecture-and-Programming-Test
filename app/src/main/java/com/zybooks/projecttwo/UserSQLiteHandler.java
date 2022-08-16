package com.zybooks.projecttwo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Objects;

public class UserSQLiteHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "UserDatabase.db";
    public static final String TABLE_NAME = "UserTable";

    public static final String COLUMN_0_ID = "id";
    public static final String COLUMN_1_NAME = "name";
    public static final String COLUMN_2_PHONE_NUMBER = "phone_number";
    public static final String COLUMN_3_EMAIL = "email";
    public static final String COLUMN_4_PASSWORD = "password";

    private static final String CREATE_USERS_TABLE = "CREATE TABLE IF NOT EXISTS " +
            TABLE_NAME + " (" +
            COLUMN_0_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
            COLUMN_1_NAME + " VARCHAR, " +
            COLUMN_2_PHONE_NUMBER + " VARCHAR, " +
            COLUMN_3_EMAIL + " VARCHAR, " +
            COLUMN_4_PASSWORD + " VARCHAR" + ");";

    public UserSQLiteHandler(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     *Database CRUD Operations
     */

    public void createUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_1_NAME, user.getUserName());
        values.put(COLUMN_2_PHONE_NUMBER, user.getUserPhone());
        values.put(COLUMN_3_EMAIL, user.getUserEmail());
        values.put(COLUMN_4_PASSWORD, user.getUserPassword());

        db.insert(TABLE_NAME, null, values);
        db.close();

    }

    public User readUser(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME,
                new String[] { COLUMN_0_ID, COLUMN_1_NAME, COLUMN_2_PHONE_NUMBER, COLUMN_3_EMAIL,
                        COLUMN_4_PASSWORD}, COLUMN_0_ID + " = ?",
                new String[] {String.valueOf(id)}, null, null,
                null, null);

        if (cursor != null)
            cursor.moveToFirst();

        User user = new User(Integer.parseInt((Objects.requireNonNull(cursor).getString(0))
        ), cursor.getString(1), cursor.getString(2), cursor.getString(3),
                cursor.getString(4));

        cursor.close();

        return user;
    }

    public int updateUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_1_NAME, user.getUserName());
        values.put(COLUMN_2_PHONE_NUMBER, user.getUserPhone());
        values.put(COLUMN_3_EMAIL, user.getUserEmail());
        values.put(COLUMN_4_PASSWORD, user.getUserPassword());

        return db.update(TABLE_NAME, values, COLUMN_0_ID + " = ?",
                new String[] {String.valueOf(user.getId())});

    }

    public void deleteUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_NAME, COLUMN_0_ID + " = ?",
                new String[] {String.valueOf(user.getId())});
        db.close();
    }
}
