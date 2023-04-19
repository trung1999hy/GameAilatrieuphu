package com.example.myapplication.bxh;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.example.myapplication.R;

import java.util.ArrayList;

public class adapds extends ArrayAdapter<listds> {
    ArrayList<listds> arrayList = new ArrayList<listds>();
    Activity context;
    int layout;

    public adapds(Context context, int resource, ArrayList<listds> objects) {
        super(context, resource, objects);
        this.context = (Activity) context;
        arrayList = objects;
        layout = resource;
    }

    @Override
    public View getView(int i, View convertview, ViewGroup parent) {
        LayoutInflater inflater =(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view= inflater.inflate(layout , null);
        ListView listView =(ListView)view.findViewById(R.id.listnguoichoi);
        TextView tten=(TextView)view.findViewById(R.id.txtTen);
        TextView adapcoin=(TextView)view.findViewById(R.id.tx_coin);

        listds tt= arrayList.get(i);
        tten.setText(tt.ten);
       adapcoin.setText(tt.vcoin+"");
        return view;
    }
}
