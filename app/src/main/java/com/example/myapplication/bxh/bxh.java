package com.example.myapplication.bxh;

import android.database.Cursor;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.DatabaseHandler;
import com.example.myapplication.R;
import com.example.myapplication.batdau;

import java.util.ArrayList;

public class bxh extends AppCompatActivity {
    ArrayList<listds> topnc = null;
    ArrayAdapter<listds> adapter = null;
    listds bxh = new listds();
    DatabaseHandler db;
    int ps;
    ListView lv;
    batdau n = new batdau();
    ArrayList<batdau> bd = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bxh);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        lv = (ListView) findViewById(R.id.listnguoichoi);
        db = new DatabaseHandler(this);

        db2ListView();
    }

    //Phương thức: Load dữ liệu lên ListView
    public void db2ListView() {
        topnc = new ArrayList<listds>();
        listds row;
        Cursor cursor = db.getCursor("SELECT * FROM bxh order by sotien DESC");
        if (cursor.moveToFirst())
            do {
                row = new listds();
                row.ten = cursor.getString(0);
                row.vcoin = cursor.getInt(1);
                topnc.add(row);
            } while (cursor.moveToNext());
        //cursor.moveToNext();
        adapter = new adapds(this, R.layout.item_bxh, topnc);
        lv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        cursor.close();
        registerForContextMenu(lv);
    }
}








