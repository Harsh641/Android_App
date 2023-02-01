package com.example.ecampus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DataBaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Harsh.db";
    private static final String TABLE_NAME = "STUDENTS";
    private static final String COLUMN_ID = "ID";
    private static final String COLUMN_FIRST_NAME = "FIRST_NAME";
    private static final String COLUMN_LAST_NAME = "LAST_NAME";
    private static final String COLUMN_EMAIL = "EMAIL";
    private static final String COLUMN_PASSWORD = "PASSWORD";

    private SQLiteDatabase sqLiteDatabase;

    public DataBaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table " + TABLE_NAME +
                "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_FIRST_NAME + " VARCHAR, "
                + COLUMN_LAST_NAME + " VARCHAR, "
                + COLUMN_EMAIL + " VARCHAR, "
                + COLUMN_PASSWORD + " VARCHAR);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void insertData(UserModel userModel){
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FIRST_NAME, userModel.getFirstName());
        contentValues.put(COLUMN_LAST_NAME, userModel.getLastName());
        contentValues.put(COLUMN_EMAIL, userModel.getEmail());
        contentValues.put(COLUMN_PASSWORD, userModel.getPassword());
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
        sqLiteDatabase.close();
    }

    public ArrayList<UserModel> displayUsers(){
        sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor2 = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);

        ArrayList<UserModel> userModelArrayList = new ArrayList<UserModel>();
        if (cursor2.getCount() > 0){
            for (int i = 0; i < cursor2.getCount(); i++){
                cursor2.moveToNext();
                UserModel userModel = new UserModel();
                userModel.setID(cursor2.getString(0));
                userModel.setFirstName(cursor2.getString(1));
                userModel.setLastName(cursor2.getString(2));
                userModel.setEmail(cursor2.getString(3));
                userModel.setPassword(cursor2.getString(4));
                userModelArrayList.add(userModel);
            }
        }

        cursor2.close();
        sqLiteDatabase.close();

        return userModelArrayList;
    }

    public UserModel authenticate(UserModel userModel){
        sqLiteDatabase = this.getReadableDatabase();

        String[] columns = {"ID", "FIRST_NAME", "LAST_NAME", "EMAIL", "PASSWORD"};
        String selection = "EMAIL = ? and PASSWORD = ?";
        String[] selectionArgs = {userModel.getEmail(), userModel.getPassword()};

        userModel = new UserModel();
        Cursor cursor = sqLiteDatabase.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();

        if (count > 0){
            cursor.moveToFirst();
            userModel = new UserModel(cursor.getString(0), cursor.getString(1),
                    cursor.getString(2), cursor.getString(3), cursor.getString(4));
        }

        cursor.close();
        sqLiteDatabase.close();
        return userModel;
    }

    public void updateData(UserModel userModel){
        sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FIRST_NAME, userModel.getFirstName());
        contentValues.put(COLUMN_LAST_NAME, userModel.getLastName());
        sqLiteDatabase.update(TABLE_NAME, contentValues, COLUMN_ID + " = ?", new String[]{userModel.getID()});
        sqLiteDatabase.close();
    }

    public void deleteData(UserModel userModel){
        sqLiteDatabase = this.getReadableDatabase();
        sqLiteDatabase.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{userModel.getID()});
        sqLiteDatabase.close();
    }
}
