package com.example.myapplication;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;

;

public class DatabaseHandler {
    private static final String TABLE_NAME = "cauhoi";
    private static final String dbName = "cauhoi.sqlite";
    private static final String dbPath = "/data/data/com.trungtv.ailatrieuphu/databases/cauhoi.sqlite";
    public static final String TABLE_ID = "id";
    public static final String TABLE_Cauhoi = "cauhoi";
    public static final String TABLE_cau_A = "cauA";
    public static final String TABLE_Cau_B = "cauB";
    public static final String TABLE_Cau_C = "cauC";
    public static final String TABLE_Cau_D = "cauD";
    public static final String TABLE_dapandung = "Dapan";
    public static final String LV= "level";
    private Context context;
    private SQLiteDatabase database;

    public DatabaseHandler(Context context) {
        this.context = context;
        new  Thread(new Runnable() {
            @Override
            public void run() {
                copyDB2SDCard(context);
            }
        }).start();

    }


    private void copyDB2SDCard(Context context) {
        File file = new File(dbPath);
        if (file.exists()) {
            return;
        }
        File parent = file.getParentFile();
        parent.mkdirs();
        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            InputStream inputStream = context.getAssets().open(dbName);
            byte[] b = new byte[1024];
            int count = inputStream.read(b);
            while (count != -1) {
                outputStream.write(b, 0, count);
                inputStream.read(b);
            }
            outputStream.close();
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void executeSQL(String strSQL) {
        //B1
        openDataBase();
        //B2
        database.execSQL(strSQL);
        //B3
        closeDaTaBase();
    }
    public int GetCount(String sql) {
        //B1
        openDataBase();
        //B2
        Cursor cur = database.rawQuery(sql, null);
        int count = cur.getCount();
        //B3
        closeDaTaBase();
        //B4
        return count;
    }
    public Cursor getCursor(String strSelect) {
        //B1
        openDataBase();
        //B2
        Cursor cursor = database.rawQuery(strSelect, null);
        //B3
        return cursor;
    }
    public void openDataBase() {
        database = context.openOrCreateDatabase(dbName, context.MODE_PRIVATE, null);
    }

    public void closeDaTaBase() {
        database.close();
    }


    public ArrayList<listcauhoi> getData() {

        openDataBase();
        ArrayList<listcauhoi> CauHoi = new ArrayList<>();
        for (int i = 1; i < 16; i++) {
            String table = TABLE_NAME ;
            String sql = "SELECT * FROM " + table + " where level like 1 order by random() limit 1 ";
            Cursor cursor = database.rawQuery(sql, null);
            int iId = cursor.getColumnIndex(TABLE_ID);
            int icauhoi = cursor.getColumnIndex(TABLE_Cauhoi);
            int icauA = cursor.getColumnIndex(TABLE_cau_A);
            int icauB = cursor.getColumnIndex(TABLE_Cau_B);
            int icauC = cursor.getColumnIndex(TABLE_Cau_C);
            int icauD = cursor.getColumnIndex(TABLE_Cau_D);
            int idapan = cursor.getColumnIndex(TABLE_dapandung);
            if (cursor != null && cursor.moveToNext()) {
                int Id = cursor.getInt(iId);
                String Cauhoi = cursor.getString(icauhoi);
                String CauA = cursor.getString(icauA);
                String CauB = cursor.getString(icauB);
                String CauC = cursor.getString(icauC);
                String CauD = cursor.getString(icauD);
                int Dapan = cursor.getInt(idapan);
                listcauhoi cauhoitt = new listcauhoi(Id, Cauhoi, CauA, CauB, CauC, CauD, Dapan);
                CauHoi.add(cauhoitt);
            }
            cursor.close();
        }
        closeDaTaBase();
        return CauHoi;

    }
    public ArrayList<listcauhoi> getData2() {

        openDataBase();
        ArrayList<listcauhoi> CauHoi = new ArrayList<>();
        for (int i = 1; i < 16; i++) {
            String table = TABLE_NAME ;
            String sql = "SELECT * FROM " + table + " where level like 2 order by random() limit 1 ";

            Cursor cursor = database.rawQuery(sql, null);
            int iId = cursor.getColumnIndex(TABLE_ID);
            int icauhoi = cursor.getColumnIndex(TABLE_Cauhoi);
            int icauA = cursor.getColumnIndex(TABLE_cau_A);
            int icauB = cursor.getColumnIndex(TABLE_Cau_B);
            int icauC = cursor.getColumnIndex(TABLE_Cau_C);
            int icauD = cursor.getColumnIndex(TABLE_Cau_D);
            int idapan = cursor.getColumnIndex(TABLE_dapandung);
            if (cursor != null && cursor.moveToNext()) {
                int Id = cursor.getInt(iId);
                String Cauhoi = cursor.getString(icauhoi);
                String CauA = cursor.getString(icauA);
                String CauB = cursor.getString(icauB);
                String CauC = cursor.getString(icauC);
                String CauD = cursor.getString(icauD);
                int Dapan = cursor.getInt(idapan);
                listcauhoi cauhoitt = new listcauhoi(Id, Cauhoi, CauA, CauB, CauC, CauD, Dapan);
                CauHoi.add(cauhoitt);
            }
            cursor.close();
        }
        closeDaTaBase();
        return CauHoi;

    }

    public ArrayList<listcauhoi> getData3() {

        openDataBase();
        ArrayList<listcauhoi> CauHoi = new ArrayList<>();
        for (int i = 1; i < 16; i++) {
            String table = TABLE_NAME ;
            String sql = "SELECT * FROM " + table + " where level like 3 order by random() limit 1 ";
            Cursor cursor = database.rawQuery(sql, null);
            int iId = cursor.getColumnIndex(TABLE_ID);
            int icauhoi = cursor.getColumnIndex(TABLE_Cauhoi);
            int icauA = cursor.getColumnIndex(TABLE_cau_A);
            int icauB = cursor.getColumnIndex(TABLE_Cau_B);
            int icauC = cursor.getColumnIndex(TABLE_Cau_C);
            int icauD = cursor.getColumnIndex(TABLE_Cau_D);
            int idapan = cursor.getColumnIndex(TABLE_dapandung);
            if (cursor != null && cursor.moveToNext()) {
                int Id = cursor.getInt(iId);
                String Cauhoi = cursor.getString(icauhoi);
                String CauA = cursor.getString(icauA);
                String CauB = cursor.getString(icauB);
                String CauC = cursor.getString(icauC);
                String CauD = cursor.getString(icauD);
                int Dapan = cursor.getInt(idapan);
                listcauhoi cauhoitt = new listcauhoi(Id, Cauhoi, CauA, CauB, CauC, CauD, Dapan);
                CauHoi.add(cauhoitt);
            }
            cursor.close();
        }
        closeDaTaBase();
        return CauHoi;

    }

}
