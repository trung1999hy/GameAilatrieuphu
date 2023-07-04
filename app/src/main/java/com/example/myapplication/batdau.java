package com.example.myapplication;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.myapplication.bxh.listds;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Random;

public class batdau extends AppCompatActivity {
    ArrayList<listds> topnc = null;
    ArrayAdapter<listds> adapter = null;
    DatabaseHandler databaseHandler;
    ArrayList<listcauhoi> listcauhoiArrayList = new ArrayList<>();
    ImageView tg5050, khangia, call, stop, changeQuestion;
    Button caua, caub, cauc, caud;
    ImageView imageView;
    //  int[] imgid;
    int[] imghu;
    int tt, dem;
    int i;

    ListView lv;
    EditText ten;
    private int TGcho;
    Dialog dialog;
    Animation an;
    ImageView chipu, ngoctrinh, mezu, quynhanh;
    TextView tvvang, tvSoCau, txcauhoi, tvthoigian;
    MediaPlayer mediaPlayer;
    private boolean check;
    private boolean run;
    private boolean limit;
    private int vang;
    private int Dapandung, Tg, thoigian;
    LayoutInflater layoutInflater;
    TextToSpeech textToSpeech;
    private CountDownTimer w, delay;
    private AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_batdau);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        anhxa();
        hienthi(i);
        TroGiup(thoigian, Tg);
        run();

    }

    public void anhxa() {
        i = 0;
        dem = 30;
        TGcho = 30;
        // vang = 0;
        ten = (EditText) findViewById(R.id.eddten);
        lv = (ListView) findViewById(R.id.listnguoichoi);
        ////////////trợ giúp
        tvthoigian = (TextView) findViewById(R.id.tv_thoigian);
        tg5050 = (ImageView) findViewById(R.id.help5050);
        khangia = (ImageView) findViewById(R.id.helpkhangia);
        call = (ImageView) findViewById(R.id.helpcall);
        stop = (ImageView) findViewById(R.id.dungcuocchoi);
        changeQuestion = (ImageView) findViewById(R.id.change_question);

        ////////////// dap an
        caua = (Button) findViewById(R.id.cauA);
        caub = (Button) findViewById(R.id.cauB);
        cauc = (Button) findViewById(R.id.cauC);
        caud = (Button) findViewById(R.id.cauD);
        tvvang = (TextView) findViewById(R.id.tien);
        tvSoCau = (TextView) findViewById(R.id.tvSoCauhoi);
        txcauhoi = (TextView) findViewById(R.id.txt_cauhoi);
        imageView = (ImageView) findViewById(R.id.imClock);
        /////hieu ứng
        //    imgid = new int[]{R.drawable.ic_all_out_black_24dp,};
        imghu = new int[]{R.anim.xoay};
        Button bx = (Button) findViewById(R.id.Bxh);
        anh();
    }

    ///am thanh cau hoi
    public static final int[] SOUND_CAUHOI = {
            R.raw.cau1,
            R.raw.cau2,
            R.raw.cau3,
            R.raw.cau4,
            R.raw.cau5,
            R.raw.cau6,
            R.raw.cau7,
            R.raw.cau8,
            R.raw.cau9,
            R.raw.cau10,
            R.raw.cau11,
            R.raw.cau12,
            R.raw.cau13,
            R.raw.cau14,
            R.raw.cau15,

    };

    public void Doc() {
        textToSpeech = new TextToSpeech(batdau.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i != TextToSpeech.ERROR) {

                    Locale language = new Locale("vie");
                    textToSpeech.setLanguage(language);
                }
            }
        });
    }

    public void hienthi(final int i) {
        if (i < 15) {
            Doc();
            if (txcauhoi != null) {
                textToSpeech.speak(txcauhoi.getText().toString(), TextToSpeech.QUEUE_FLUSH, new HashMap<>());
            }
            databaseHandler = new DatabaseHandler(this);
            run = true;
            int ch = i + 1;
            tvvang.setText(MainApp.newInstance().getPreference().getValueCoin() + "");
            tvSoCau.setText("Câu hỏi số " + ch + " :");
            if (ch < 5)
                amthanh(R.raw.moc1);
            listcauhoiArrayList = databaseHandler.getData();
            if (ch >= 5) {
                amthanh(R.raw.moc2);
                listcauhoiArrayList = databaseHandler.getData2();
            }
            if (ch >= 10) {
                amthanh(R.raw.moc3);
                listcauhoiArrayList = databaseHandler.getData3();
            }
            txcauhoi.setText(listcauhoiArrayList.get(i).getCauhoi());

            amthanh(SOUND_CAUHOI[i]);
            limit = true;
            caua.setBackgroundResource(R.drawable.button);
            caua.setText("A. " + listcauhoiArrayList.get(i).getCauA());
            caua.setEnabled(true);
            caub.setBackgroundResource(R.drawable.button);
            caub.setText("B. " + listcauhoiArrayList.get(i).getCauB());
            caub.setEnabled(true);
            cauc.setBackgroundResource(R.drawable.button);
            cauc.setText("C. " + listcauhoiArrayList.get(i).getCauC());
            cauc.setEnabled(true);
            caud.setBackgroundResource(R.drawable.button);
            caud.setText("D. " + listcauhoiArrayList.get(i).getCauD());
            caud.setEnabled(true);
            dem = 30;
            switch (listcauhoiArrayList.get(i).getDapandung()) {
                case 1:
                    Dapandung = R.id.cauA;
                    break;
                case 2:
                    Dapandung = R.id.cauB;
                    break;
                case 3:
                    Dapandung = R.id.cauC;
                    break;
                case 4:
                    Dapandung = R.id.cauD;
                    break;
            }
        } else if (i == 15) {
            //Ketthuc(vang);
            Thangcuoc(vang);
        }
    }

    public void onClick(View v) {
        run = false;
        if (!limit) {
            return;
        }
        v.setBackground(ContextCompat.getDrawable(this, R.drawable.xanhduong));
        switch (v.getId()) {
            case R.id.cauA:
                amthanh(R.raw.phuongana);
                break;
            case R.id.cauB:
                amthanh(R.raw.phuonganb);
                break;
            case R.id.cauC:
                amthanh(R.raw.phuonganc);
                break;
            case R.id.cauD:
                amthanh(R.raw.phuongand);
                break;
        }
        if (v.getId() == Dapandung) {
            check = true;
            TGcho = 0;
            vang = i + 1;

        } else {
            check = false;
            TGcho = 0;
        }
        limit = false;

    }

    public void onclicktrogiup(View v) {

        if (v.getId() == R.id.help5050) {
            alertDialog = new AlertDialog.Builder(this).setTitle("Bạn có chắc  sự trợ giúp 50 : 50 không ?").setMessage("Dùng mất 1 vàng bạn có chắc không ?");
            alertDialog.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (MainApp.newInstance().getPreference().getValueCoin() > 0) {
                        MainApp.newInstance().getPreference().setValueCoin(MainApp.newInstance().getPreference().getValueCoin() - 1);
                        limit = false;
                        thoigian = 0;
                        Tg = R.id.help5050;
                        amthanh(R.raw.trogiup_5050);
                        tg5050.setEnabled(false);
                        tg5050.setImageResource(R.drawable.trogiup_5050_x);
                        tvvang.setText(MainApp.newInstance().getPreference().getValueCoin() + "");
                    } else startActivity(new Intent(batdau.this, PurchaseInAppActivity.class));

                }

            }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alertDialog.create().show();


        } else if (v.getId() == R.id.helpkhangia) {
            alertDialog = new AlertDialog.Builder(this).setTitle("Bạn có chắc  sự trợ giúp từ khán giả không ?").setMessage("Dùng mất 1 vàng bạn có chắc không ?");
            alertDialog.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (MainApp.newInstance().getPreference().getValueCoin() > 0) {
                        MainApp.newInstance().getPreference().setValueCoin(MainApp.newInstance().getPreference().getValueCoin() - 1);
                        limit = false;
                        thoigian = 0;
                        Tg = R.id.helpkhangia;
                        khangia.setEnabled(false);
                        amthanh(R.raw.khan_gia);
                        khangia.setImageResource(R.drawable.khangia_x);
                        tvvang.setText(MainApp.newInstance().getPreference().getValueCoin() + "");
                    } else startActivity(new Intent(batdau.this, PurchaseInAppActivity.class));

                }

            }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alertDialog.create().show();


        } else if (v.getId() == R.id.helpcall) {
            alertDialog = new AlertDialog.Builder(this).setTitle("Bạn có chắc  sự trợ giúp từ người thân không ?").setMessage("Dùng mất 1 vàng bạn có chắc không ?");
            alertDialog.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (MainApp.newInstance().getPreference().getValueCoin() > 0) {
                        MainApp.newInstance().getPreference().setValueCoin(MainApp.newInstance().getPreference().getValueCoin() - 1);
                        limit = false;
                        thoigian = 0;
                        Tg = R.id.helpcall;
                        call.setEnabled(false);
                        amthanh(R.raw.call);
                        call.setImageResource(R.drawable.call_x);
                        tvvang.setText(MainApp.newInstance().getPreference().getValueCoin() + "");
                    } else startActivity(new Intent(batdau.this, PurchaseInAppActivity.class));
                }

            }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alertDialog.create().show();


        } else if (v.getId() == R.id.dungcuocchoi) {
            androidx.appcompat.app.AlertDialog.Builder sn = new androidx.appcompat.app.AlertDialog.Builder(this);
            sn.setMessage("Bạn Đã Chắc Với Quyết Định Của Mình?");
            sn.setPositiveButton("Dừng Cuộc Chơi!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    limit = false;
                    if (!isFinishing())
                        Ketthuc(vang);
                    // Thangcuoc(vang);
                }
            });
            sn.setNegativeButton("Chơi Tiếp", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
            sn.create().show();
        } else if (v.getId() == R.id.change_question) {
            alertDialog = new AlertDialog.Builder(this).setTitle("Bạn có muốn đổi câu hỏi khác ?").setMessage("Dùng mất 1 vàng bạn có chắc không ?");
            alertDialog.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (MainApp.newInstance().getPreference().getValueCoin() > 0) {
                        MainApp.newInstance().getPreference().setValueCoin(MainApp.newInstance().getPreference().getValueCoin() - 1);
                        changeQuestion();
                        tvvang.setText(MainApp.newInstance().getPreference().getValueCoin() + "");
                    } else startActivity(new Intent(batdau.this, PurchaseInAppActivity.class));
                }

            }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            alertDialog.create().show();
        }
    }


    public void animnButon(Button btn) {
        Animation animationButton = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.nhay);
        btn.startAnimation(animationButton);
    }

    public void amthanh(int nhac) {
        mediaPlayer = MediaPlayer.create(this, nhac);
        mediaPlayer.start();

    }

    public void run() {
        w = new CountDownTimer(180000, 1000) {
            public void onTick(long tg) {
                int phut = (int) ((tg / 1000) / 60);
                int giay = (int) (tg / 1000) % 60;
                String time = String.format(Locale.getDefault(), "%02d:%02d", phut, giay);
                tvthoigian.setText(time);
                TGcho++;
                thoigian++;
                TroGiup(thoigian, Tg);
                if (TGcho == 4 && check) {
                    switch (Dapandung) {
                        case R.id.cauA:
                            caua.setBackgroundResource(R.drawable.dung);
                            amthanh(R.raw.true_a);
                            animnButon(caua);
                            MainApp.newInstance().getPreference().setValueCoin(MainApp.newInstance().getPreference().getValueCoin() + 50);
                            tvvang.setText(MainApp.newInstance().getPreference().getValueCoin() + "");
                            break;
                        case R.id.cauB:
                            caub.setBackgroundResource(R.drawable.dung);
                            amthanh(R.raw.true_b);
                            animnButon(caub);
                            MainApp.newInstance().getPreference().setValueCoin(MainApp.newInstance().getPreference().getValueCoin() + 50);
                            tvvang.setText(MainApp.newInstance().getPreference().getValueCoin() + "");
                            break;
                        case R.id.cauC:
                            cauc.setBackgroundResource(R.drawable.dung);
                            amthanh(R.raw.true_c);
                            animnButon(cauc);
                            MainApp.newInstance().getPreference().setValueCoin(MainApp.newInstance().getPreference().getValueCoin() + 50);
                            tvvang.setText(MainApp.newInstance().getPreference().getValueCoin() + "");
                            break;
                        case R.id.cauD:
                            caud.setBackgroundResource(R.drawable.dung);
                            amthanh(R.raw.true_d);
                            animnButon(caud);
                            MainApp.newInstance().getPreference().setValueCoin(MainApp.newInstance().getPreference().getValueCoin() + 50);
                            tvvang.setText(MainApp.newInstance().getPreference().getValueCoin() + "");
                            break;
                    }
                }
                if (TGcho == 4 && !check) {
                    switch (Dapandung) {
                        case R.id.cauA:
                            caua.setBackgroundResource(R.drawable.sai);
                            amthanh(R.raw.lose_a);
                            break;
                        case R.id.cauB:
                            caub.setBackgroundResource(R.drawable.sai);
                            amthanh(R.raw.lose_b);
                            break;
                        case R.id.cauC:
                            cauc.setBackgroundResource(R.drawable.sai);
                            amthanh(R.raw.lose_c);
                            break;
                        case R.id.cauD:
                            caud.setBackgroundResource(R.drawable.sai);
                            amthanh(R.raw.lose_d);
                            break;
                    }
                }
                if (TGcho == 6 && check && i < 16) {
                    hienthi(++i);
                } else if ((TGcho == 6 && !check) || dem == 0 || i == 15) {
                    Ketthuc(vang);
                }

            }

            @Override
            public void onFinish() {
                tvthoigian.setText("00.00");
                Ketthuc(vang);
                return;
            }
        }.start();
    }

    private void changeQuestion() {
        hienthi(i);
        changeQuestion.setVisibility(View.GONE);
        amthanh(SOUND_CAUHOI[i]);
        limit = true;
    }

    public void Call() {
        dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        LayoutInflater call = LayoutInflater.from(this);
        View view = call.inflate(R.layout.goidien, null);
        chipu = (ImageView) view.findViewById(R.id.chipu);
        setOnclick(chipu);
        ngoctrinh = (ImageView) view.findViewById(R.id.ngoctrinh);
        setOnclick(ngoctrinh);
        mezu = (ImageView) view.findViewById(R.id.mezu);
        setOnclick(mezu);
        quynhanh = (ImageView) view.findViewById(R.id.quynhanh);
        setOnclick(quynhanh);
        amthanh(R.raw.help_callb);
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.show();
    }

    private void setOnclick(ImageView img) {
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                traloi();
            }
        });
    }

    public void traloi() {
        dialog.dismiss();
        dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.dialoggoidien, null);
        final Button ok = (Button) view.findViewById(R.id.dongy);
        if (Dapandung == R.id.cauA) {
            ok.setText("A");
        } else if (Dapandung == R.id.cauB) {
            ok.setText("B");
        } else if (Dapandung == R.id.cauC) {
            ok.setText("C");
        } else if (Dapandung == R.id.cauD) {
            ok.setText("D");
        }
        Button btnOk = (Button) view.findViewById(R.id.dongy);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                run = true;
            }
        });
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.show();
    }

    public void khangia() {
        dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        View view = layoutInflater.inflate(R.layout.khangia, null);
        TextView tvchonA = (TextView) view.findViewById(R.id.chonA);
        TextView tvchonB = (TextView) view.findViewById(R.id.chonB);
        TextView tvchonC = (TextView) view.findViewById(R.id.chonC);
        TextView tvchonD = (TextView) view.findViewById(R.id.chonD);
        Button btnOk = (Button) view.findViewById(R.id.btokkhangia);
        Random random = new Random();
        int rd1dung = random.nextInt(100);
        int rdsai1 = random.nextInt(50);
        int rdsai2 = random.nextInt(50);
        int rdsai3 = random.nextInt(50);
        ;
        if (Dapandung == R.id.cauA) {
            tvchonA.setText(rd1dung + "%");
            tvchonB.setText(rdsai1 + "%");
            tvchonC.setText(rdsai2 + "%");
            tvchonD.setText(rdsai3 + "%");
        } else if (Dapandung == R.id.cauB) {
            tvchonA.setText(rdsai1 + "%");
            tvchonB.setText(rd1dung + "%");
            tvchonC.setText(rdsai2 + "%");
            tvchonD.setText(rdsai3 + "%");
        } else if (Dapandung == R.id.cauC) {
            tvchonA.setText(rdsai1 + "%");
            tvchonB.setText(rdsai2 + "%");
            tvchonC.setText(rd1dung + "%");
            tvchonD.setText(rdsai3 + "%");
        } else if (Dapandung == R.id.cauD) {
            tvchonA.setText(rdsai1 + "%");
            tvchonB.setText(rdsai2 + "%");
            tvchonC.setText(rdsai3 + "%");
            tvchonD.setText(rd1dung + "%");
        }
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                run = true;
            }
        });
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.show();
    }

    public void Thangcuoc(int vang) {
        dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        layoutInflater = LayoutInflater.from(this);
        final View view = layoutInflater.inflate(R.layout.activity_ketthuc, null);
        TextView tvFinish = (TextView) view.findViewById(R.id.ketthuc);
        ten = (EditText) view.findViewById(R.id.eddten);
        Button btketthuc = (Button) view.findViewById(R.id.btnketthuc);
        tvFinish.setText("Bạn đã dành được " + vang + " điểm. Bạn là người chơi " +
                "xuất sắc nhất từ đầu của chương trình. Chúc bạn thành công trong cuộc sống !!!");
        amthanh(R.raw.thangcuoc);
        btketthuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addngchoi();
                onBackPressed();
                dialog.dismiss();

            }

        });
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.show();

    }

    public void Ketthuc(int vang) {
        dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Light_Dialog_NoActionBar_MinWidth);
        layoutInflater = LayoutInflater.from(this);
        final View view = layoutInflater.inflate(R.layout.activity_ketthuc, null);
        TextView tvFinish = (TextView) view.findViewById(R.id.ketthuc);
        ten = (EditText) view.findViewById(R.id.eddten);
        Button btketthuc = (Button) view.findViewById(R.id.btnketthuc);
        tvFinish.setText("Bạn đã dành được " + vang + "câu. Cảm ơn bạn đã tham gia " +
                "chương trình. Chúc bạn thành công trong cuộc sống !!!");
        amthanh(R.raw.lose2);
        btketthuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addngchoi();
                onBackPressed();
                dialog.dismiss();

            }

        });
        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.show();
    }


    public void TroGiup(int thoigian, int Tg) {
        if (thoigian == 4 && Tg == R.id.help5050) {
            if (Dapandung == R.id.cauA) {
                caub.setEnabled(false);
                cauc.setEnabled(false);
            } else if (Dapandung == R.id.cauB) {
                caua.setEnabled(false);
                caud.setEnabled(false);
            } else if (Dapandung == R.id.cauC) {
                caua.setEnabled(false);
                caub.setEnabled(false);
            } else if (Dapandung == R.id.cauD) {
                cauc.setEnabled(false);
                caua.setEnabled(false);
            }
            amthanh(R.raw.s50);
            //run = true;
            limit = true;

        } else if (thoigian == 3 && Tg == R.id.helpcall) {
            limit = true;
            Call();
        } else if (thoigian == 6 && Tg == R.id.helpkhangia) {
            limit = true;
            khangia();
        }
    }

    private void anh() {
        an = AnimationUtils.loadAnimation(getApplicationContext(), imghu[tt % imghu.length]);
        // imageView.setImageResource(imgid[tt % imgid.length]);
        imageView.startAnimation(an);
        an.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                anh();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        tt++;

    }

    public void onPause() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }

    public void addngchoi() {
        databaseHandler.executeSQL("insert into bxh values('" + ten.getText().toString().trim() + "','" + vang + "')");
        Toast.makeText(getApplicationContext(), "Thêm thành công!!!", Toast.LENGTH_LONG).show();
        Intent back = new Intent(batdau.this, MainActivity.class);
        batdau.this.startActivity(back);
    }
}



