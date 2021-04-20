package com.example.josh.akanyenyeri;

/**
 * Created by JOSH on 5/4/2017.
 */

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.LinkedList;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "BYIHUSEDB";

    // Contacts table name
     static final String TABLE_COMPANY = "COMPANY";
     static final String TABLE_COMPANY_DEP = "COMPANY_DEP";
     static final String TABLE_SHORTCODE = "SHORTCODE";
     static final String TABLE_SERVICE = "SERVICE";
     static final String TABLE_LANGUE = "LANGUE";
    static final String TABLE_CATEGORIE = "CATEGORIE";
    static final String TABLE_MESSAGE = "MESSAGES";
    static final String TABLE_OPERATION = "OPERATIONS";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PH_NO = "phone_number";

    public DbHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        createDatabase(db);
    }


    public void createDatabase(SQLiteDatabase db)
    {
        String CREATE_COMPANY_TABLE = "CREATE TABLE IF NOT EXISTS " +  TABLE_COMPANY+ "(ID_COMPANY INTEGER PRIMARY KEY," +
                "  CODE_COMPANY TEXT ," +
                "  NAME_COMPANY TEXT ," +
                "  LOGO TEXT ," +
                "  COUNTRY TEXT ," +
                "  MONEY_SYSTEM TEXT ," +
                "  SHORT_CODE TEXT )";

        String CREATE_COMPANY_DEP_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_COMPANY_DEP + "(ID_COMPANY_DEP INTEGER PRIMARY KEY," +
                "  CODE_COMPANY_DEP TEXT ," +
                "  NAME_COMPANY_DEP TEXT ," +
                "  LOGO_DEP TEXT ," +
                "  COUNTRY TEXT )";



        String CREATE_SHORTCODE_TABLE  = "CREATE TABLE IF NOT EXISTS " + TABLE_SHORTCODE + "(ID_SHORT_CODE INTEGER PRIMARY KEY," +
                "  SHORTCODE  TEXT," +
                "  EXPLANATION  TEXT," +
                "  CODE_COMPANY_OWNER  TEXT," +
                "  CODE_COMPANY  TEXT," +
                "  INTERFACE  TEXT," +
                "  CHARGE_AMOUNT  DOUBLE," +
                "  IMAGE_PATH  TEXT )";


        String CREATE_LANGUE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_LANGUE + "(ID_LANGUE INTEGER PRIMARY KEY," +
                "  CODE_LANGUE  TEXT," +
                "  KINYARWANDA TEXT," +
                "  KIRUNDI TEXT," +
                "  FRANCAIS TEXT," +
                "  ENGLISH)";

        String CREATE_SERVICE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_SERVICE + "(ID_SERVICE INTEGER PRIMARY KEY," +
                "  CODE_SERVICE TEXT," +
                "  NAME_SERVICE TEXT," +
                "  CATEGORIE TEXT)";

        String CREATE_CATEGORIE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CATEGORIE + "(ID_CATEGORIE INTEGER PRIMARY KEY," +
                    " NAME_CATEGORIE  TEXT," +
                     " HINT_CATEGORIE  TEXT," +
                    " DIGIT_NUMBER_CATEGORIE INTEGER)";


        String CREATE_MESSAGE_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_MESSAGE + "(ID_MESSAGE INTEGER PRIMARY KEY," +
                " CONTENT_MESSAGE  TEXT," +
                " SENDER_MESSAGE  TEXT," +
                " SYNC_MESSAGE_STATUS  TEXT," +
                " TIME_MESSAGE TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL)";


        String CREATE_OPERATION_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_OPERATION + "(ID_OPERATION INTEGER PRIMARY KEY," +
                " NAME_OPERATION  TEXT," +
                " TIER_PHONE_OPERATION  TEXT," +
                " REASON_OPERATION  TEXT," +
                " COMPANY_OPERATION  TEXT," +
                " AMOUNT_OPERATION  DOUBLE," +
                " TIME_OPERATION  TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL," +
                " STATUS_OPERATION TEXT)";


        db.execSQL(CREATE_COMPANY_TABLE);
        db.execSQL(CREATE_COMPANY_DEP_TABLE);
        db.execSQL(CREATE_SHORTCODE_TABLE);
        db.execSQL(CREATE_LANGUE_TABLE);
        db.execSQL(CREATE_SERVICE_TABLE);
        db.execSQL(CREATE_CATEGORIE_TABLE);
        db.execSQL(CREATE_MESSAGE_TABLE);
        db.execSQL(CREATE_OPERATION_TABLE);
    }



    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPANY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMPANY_DEP);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SHORTCODE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LANGUE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIE);
        // Create tables again
        onCreate(db);
    }





    // Getting single company
    Services getService(String categorie) {
        SQLiteDatabase db = this.getReadableDatabase();

        Services services = null;
        Cursor cursor = db.query(TABLE_SERVICE, new String[] { "ID_SERVICE",
                        "CODE_SERVICE", "NAME_SERVICE",   "CATEGORIE" }, "CATEGORIE" + " LIKE ?",
                new String[] {"%"+ categorie+ "%" }, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {

            System.out.println("kuri getcompanydep " + cursor.getInt(0));
            System.out.println("kuri getcompanydep " + cursor.getString(1));
            System.out.println("kuri getcompanydep " + cursor.getString(2));
            System.out.println("kuri getcompanydep " + cursor.getString(3));

            services = new Services(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2), cursor.getString(3));
        }
        // return contact
        return services;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public LinkedList<Company> getAllCompany() {
        LinkedList<Company> companyLinkedList = new LinkedList<Company>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_COMPANY;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Company company = new Company(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3)
                        ,cursor.getString(4),cursor.getString(5),cursor.getString(6));

                 companyLinkedList.add(company);
            } while (cursor.moveToNext());
        }
        return companyLinkedList;
    }




    public LinkedList<Companydep> getAllCompanyDep() {
        LinkedList<Companydep> companydepLinkedList = new LinkedList<Companydep>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_COMPANY_DEP;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Companydep arette = new Companydep(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
                // Adding contact to list
                companydepLinkedList.add(arette);
            } while (cursor.moveToNext());
        }
        return companydepLinkedList;
    }



    public LinkedList<Categorie> getAllCategories() {
        LinkedList<Categorie> categorieLinkedList = new LinkedList<Categorie>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CATEGORIE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Categorie arette = new Categorie(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2),cursor.getInt(3));
                // Adding contact to list
                categorieLinkedList.add(arette);
            } while (cursor.moveToNext());
        }
        return categorieLinkedList;
    }


    public LinkedList<Shortcode> getAllShortCode(String sqlToAdd) {
        LinkedList<Shortcode> shortcodeLinkedList = new LinkedList<Shortcode>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SHORTCODE+sqlToAdd;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                System.out.println("0 VALUEOFOO "+cursor.getString(0));
                System.out.println("1 VALUEOFOO "+cursor.getString(1));
                System.out.println("2 VALUEOFOO "+cursor.getString(2));
                System.out.println("3 VALUEOFOO "+cursor.getString(3));
                System.out.println("4 VALUEOFOO "+cursor.getString(4));
                System.out.println("5 VALUEOFOO "+cursor.getString(5));
                System.out.println("6 VALUEOFOO "+cursor.getString(6));
                Shortcode shortcode = new Shortcode(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getDouble(6), cursor.getString(7));
                // Adding contact to list
                shortcodeLinkedList.add(shortcode);
            } while (cursor.moveToNext());
        }
        return shortcodeLinkedList;
    }



    public int getMaxID(String selectQuery) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
// looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                if(cursor.getString(0)==null)
                {
                    return 0;
                }
                else
                    return Integer.parseInt(cursor.getString(0)) ;
            } while (cursor.moveToNext());
        }
        return 0;
    }


    public LinkedList<Services> getAllServices() {
        LinkedList<Services> servicesLinkedList = new LinkedList<Services>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SERVICE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Services services = new Services(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3));
                // Adding contact to list
                servicesLinkedList.add(services);
            } while (cursor.moveToNext());
        }
        return servicesLinkedList;
    }


    public LinkedList<Messages> getAllMessageToSync() {
        LinkedList<Messages> messagesLinkedList = new LinkedList<Messages>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_MESSAGE +" WHERE SYNC_MESSAGE_STATUS = 'NON'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Messages services = new Messages(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2), cursor.getString(4),cursor.getString(3));
                // Adding contact to list
                messagesLinkedList.add(services);
            } while (cursor.moveToNext());
        }
        return messagesLinkedList;
    }


    public LinkedList<Services> getAllServicesByCategorie(String categorie) {
        LinkedList<Services> servicesLinkedList = new LinkedList<Services>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_SERVICE+" WHERE CATEGORIE LIKE'%"+categorie+"%'";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Services services = new Services(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3));
                // Adding contact to list
                servicesLinkedList.add(services);
            } while (cursor.moveToNext());
        }
        return servicesLinkedList;
    }

    public LinkedList<OPERATIONS> getAllOperationsByPeriod(String sql) {
        LinkedList<OPERATIONS> servicesLinkedList = new LinkedList<OPERATIONS>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_OPERATION+sql;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                OPERATIONS services = new OPERATIONS(Integer.parseInt(cursor.getString(0)),
                        cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getDouble(5)
                        , cursor.getString(6), cursor.getString(7));
                // Adding contact to list
                servicesLinkedList.add(services);
            } while (cursor.moveToNext());
        }
        return servicesLinkedList;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // Updating single contact
    public int updateCompany(Company company) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("ID_COMPANY", company.getID_COMPANY());
        values.put("CODE_COMPANY", company.getCODE_COMPANY());
        values.put("NAME_COMPANY", company.getNAME_COMPANY());
        values.put("LOGO", company.getLOGO());
        values.put("COUNTRY", company.getCOUNTRY());

        // updating row
        return db.update(TABLE_COMPANY, values, KEY_ID + " = ?",
                new String[] { String.valueOf(company.getID_COMPANY()) });
    }



    // Updating single message
    public int updateMessage(String messageId) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("SYNC_MESSAGE_STATUS", "YES");

        // updating row
        return db.update(TABLE_MESSAGE, values,  "ID_MESSAGE = ?",
                new String[] { String.valueOf(messageId) });
    }

    // Updating single message
    public int updateVariable(String variable,String value) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("NAME_SERVICE", value);

        // updating row
        return db.update(TABLE_SERVICE, values,  "CODE_SERVICE = ?",
                new String[] { String.valueOf(variable) });
    }


    //////////////////////////////////////////////////////////////////////////////////////////////////////


    public void deleteAllFromTable(String table) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(table, null,null);
        db.close();
    }

    // Deleting single contact
    public void deleteCompany(Company company) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COMPANY, KEY_ID + " = ?",
                new String[] { String.valueOf(company.getID_COMPANY()) });
        db.close();
    }


    // Getting contacts Count
    public int getCompanyCount() {
        String countQuery = "SELECT  * FROM " + TABLE_COMPANY;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

}