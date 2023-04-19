package com.example.myapplication;

import android.os.Bundle;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class caidat extends AppCompatActivity {
   // Sound sound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caidat);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Switch switchOne = (Switch) findViewById(R.id.sound);

        switchOne.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                        if (isChecked) {
                             // sound.playSound() = true;
                            Toast.makeText(caidat.this,

                                    "Đã bật âm thanh", Toast.LENGTH_SHORT).show();
                        } else {
                             //sound.sound = false;
                            Toast.makeText(caidat.this,
                                    "Đã tắt âm thanh", Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }

}

























































