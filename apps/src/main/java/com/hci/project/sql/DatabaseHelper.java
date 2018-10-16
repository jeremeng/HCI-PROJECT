package com.hci.project.sql;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hci.project.model.User;
import com.hci.project.model.Work;

import java.util.ArrayList;
import java.util.List;


public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "Users.db";

    private static final String TABLE_USER = "user";



    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_NAME = "user_name";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";


    private String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_USER_NAME + " TEXT,"
            + COLUMN_USER_EMAIL + " TEXT," + COLUMN_USER_PASSWORD + " TEXT" + ")";


    private String DROP_WORKOUT_TABLE = "DROP TABLE IF EXISTS " + TABLE_USER;

    private static final String TABLE_WORKOUT = "work";

    private static final String COLUMN_WORK_ID = "workout_id";
    private static final String COLUMN_WORK_USER = "workout_user";
    private static final String COLUMN_WORK_NAME = "workout_name";
    private static final String COLUMN_WORK_TIME = "workout_time";
    private static final String COLUMN_WORK_REP = "workout_rep";
    private static final String COLUMN_WORK_REST = "workout_rest";

    private String CREATE_WORKOUT_TABLE = "CREATE TABLE " + TABLE_WORKOUT + "("
            + COLUMN_WORK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COLUMN_WORK_USER + " TEXT,"+  COLUMN_WORK_NAME + " TEXT,"
            + COLUMN_WORK_TIME + " LONG," + COLUMN_WORK_REP + " LONG," + COLUMN_WORK_REST + " LONG" + ")";
    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE_WORKOUT;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_WORKOUT_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


        db.execSQL(DROP_USER_TABLE);
        db.execSQL(DROP_WORKOUT_TABLE);

        onCreate(db);

    }


    public void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());


        db.insert(TABLE_USER, null, values);
        db.close();
    }
    public void addWorkout(Work work) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_WORK_USER, work.getUser());
        values.put(COLUMN_WORK_NAME, work.getName());
        values.put(COLUMN_WORK_TIME, work.getTime());
        values.put(COLUMN_WORK_REP, work.getRep());
        values.put(COLUMN_WORK_REST, work.getRest());


        db.insert(TABLE_WORKOUT, null, values);
        db.close();
    }


    public List<User> getAllUser() {

        String[] columns = {
                COLUMN_USER_ID,
                COLUMN_USER_EMAIL,
                COLUMN_USER_NAME,
                COLUMN_USER_PASSWORD
        };

        String sortOrder =
                COLUMN_USER_NAME + " ASC";
        List<User> userList = new ArrayList<User>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_USER,
                columns,
                null,
                null,
                null,
                null,
                sortOrder);


        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_USER_ID))));
                user.setName(cursor.getString(cursor.getColumnIndex(COLUMN_USER_NAME)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));

                userList.add(user);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();


        return userList;
    }

        public List<Work> getAllWorkout(String uname) {

            String[] columns = {
                    COLUMN_WORK_ID,
                    COLUMN_WORK_USER,
                    COLUMN_WORK_NAME,
                    COLUMN_WORK_TIME,
                    COLUMN_WORK_REP,
                    COLUMN_WORK_REST
            };

            String sortOrder =
                    COLUMN_WORK_NAME + " ASC";
            List<Work> workList = new ArrayList<Work>();

            SQLiteDatabase db = this.getReadableDatabase();

            Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_WORKOUT + " WHERE " + COLUMN_WORK_USER + " = '" + uname + "'" ,null);


            if (cursor.moveToFirst()) {
                do {
                    Work work = new Work();
                    work.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(COLUMN_WORK_ID))));
                    work.setUser(cursor.getString(cursor.getColumnIndex(COLUMN_WORK_USER)));
                    work.setName(cursor.getString(cursor.getColumnIndex(COLUMN_WORK_NAME)));
                    work.setRep(Long.parseLong(cursor.getString(cursor.getColumnIndex(COLUMN_WORK_TIME))));
                    work.setRep(Long.parseLong(cursor.getString(cursor.getColumnIndex(COLUMN_WORK_REP))));
                    work.setRest(Long.parseLong( cursor.getString(cursor.getColumnIndex(COLUMN_WORK_REST))));

                    workList.add(work);
                } while (cursor.moveToNext());
            }
            cursor.close();
            db.close();


            return workList;
        }

    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_NAME, user.getName());
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());


        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }


    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
        db.close();
    }


    public boolean checkUser(String email) {


        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();


        String selection = COLUMN_USER_EMAIL + " = ?";


        String[] selectionArgs = {email};

        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }

        return false;
    }


    public boolean checkUser(String email, String password) {


        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();

        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";


        String[] selectionArgs = {email, password};


        Cursor cursor = db.query(TABLE_USER,
                columns,
                selection,
                selectionArgs,
                null,
                null,
                null);

        int cursorCount = cursor.getCount();

        cursor.close();
        db.close();
        if (cursorCount > 0) {
            return true;
        }

        return false;
    }

    public List<String> getRecord(String uname, String pword) {
        List<String> recordList = new ArrayList<String>();
        SQLiteDatabase dataBase = this.getReadableDatabase();
        String getSQL = "SELECT * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_EMAIL + " = '" + uname + "' AND " + COLUMN_USER_PASSWORD + " = '" + pword + "'";
        Cursor cursor = dataBase.rawQuery(getSQL , null);
        cursor.moveToFirst();
        String fName = cursor.getString(1);
        String lName = cursor.getString(2);
        String eMail = cursor.getString(3);
        recordList.add(fName);
        recordList.add(lName);
        recordList.add(eMail);
        dataBase.close();
        return recordList;
    }
}
