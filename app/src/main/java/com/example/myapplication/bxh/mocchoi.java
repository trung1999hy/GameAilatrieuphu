package com.example.myapplication.bxh;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapplication.MainActivity;
import com.example.myapplication.R;
import com.example.myapplication.batdau;


public class  mocchoi extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moc);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Button bt = (Button) findViewById(R.id.btnSanSang);
        playSound(R.raw.luatchoi_b);
    }
    public void sansang(View view){ 
        mediaPlayer.pause();
        AlertDialog.Builder sn= new AlertDialog.Builder(this);
        sn.setMessage("Người chơi đã sẵn sàng!!!");

        playSound(R.raw.ailatrieuphu);

        sn.setPositiveButton("Chơi", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mediaPlayer.pause();
                Intent it = new Intent(mocchoi.this, batdau.class) ;
                startActivity(it);
                fileList();
            }
        });
////
        sn.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent it = new Intent(mocchoi.this, MainActivity.class) ;
                startActivity(it);
            }
        });
        sn.create().show();
    }
    public void playSound(int type) {
        mediaPlayer = MediaPlayer.create(this, type);
        mediaPlayer.start();
    }
}

