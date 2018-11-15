package com.name.rolo;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;


public class CelebrityClass extends AppCompatActivity {
    static private int month = 0;
    static private int id = -1;
    static Dialog dialog;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                    Intent intent = new Intent(this,CalendarClass.class);
                        intent.putExtra("Month",month);
                    startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    View.OnClickListener oclBtn4 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(MainActivity.MARKET_ADRESS));
                startActivity(intent);
            } catch (Exception e){
                try{
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(MainActivity.BROWSER_ADRESS));
                    startActivity(intent);
                } catch (Exception ignored){}
            }
        }
    };


    View.OnClickListener oclBtn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getApplicationContext(),RecipiesClass.class);
            intent.putExtra("RP",v.getId()-8000);
            startActivity(intent);
        }
    };

    View.OnClickListener oclBtn1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog.show();
        }
    };

    TextView TxtD;
    String[] MonthNames=new String[12];
    int p =1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_celebrity);
        TxtD = (TextView) findViewById(R.id.TxtCelDate);
        LinearLayout LnLayoutV = (LinearLayout) findViewById(R.id.LnLayoutV);
        if (getIntent().getIntExtra("id",-1)!=-1)
            id = getIntent().getIntExtra("id",-1);
        LayoutInflater ltInflater = getLayoutInflater();
        if (getIntent().getIntExtra("Month",-1)!=-1)
            month = getIntent().getIntExtra("Month",CalendarClass.Now.getMonth());

        final String[] Array = getResources().getStringArray(getResources().getIdentifier("Celebrities"+String.valueOf(month), "array", getPackageName()));
        final String[] Posts = getResources().getStringArray(getResources().getIdentifier("Posts", "array", getPackageName()));

        short recipies=0;



        dialog = new Dialog(CelebrityClass.this);
        dialog.setTitle("ПОКУПКА ПОЛНОЙ ВЕРСИИ");
        dialog.setContentView(R.layout.zaglushka);
        ((TextView) dialog.findViewById(R.id.zaglushkaName1)).setTypeface(MainActivity.CF);
        ((TextView) dialog.findViewById(R.id.zaglushkaName2)).setTypeface(MainActivity.CF);
        ((TextView) dialog.findViewById(R.id.zaglushkaName3)).setTypeface(MainActivity.CF);

        Button btn = (Button) dialog.findViewById(R.id.zaglushkaBtn);
        btn.setTypeface(MainActivity.CF);
        btn.setOnClickListener(oclBtn4);

        MonthNames[0]="Января";
        MonthNames[1]="Февраля";
        MonthNames[2]="Марта";
        MonthNames[3]="Апреля";
        MonthNames[4]="Мая";
        MonthNames[5]="Июня";
        MonthNames[6]="Июля";
        MonthNames[7]="Августа";
        MonthNames[8]="Сентября";
        MonthNames[9]="Октября";
        MonthNames[10]="Ноября";
        MonthNames[11]="Декабря";
        char r = Array[id].charAt(0);
        int a = Integer.valueOf(String.valueOf(r))*10;
        r = Array[id].charAt(1);
        a = a + Integer.valueOf(String.valueOf(r));
        r = Array[id].charAt(3);
        int b = Integer.valueOf(String.valueOf(r))*10;
        r = Array[id].charAt(4);
        b = b + Integer.valueOf(String.valueOf(r));
        String out;
        out = String.valueOf(b) + " " + String.valueOf(MonthNames[a]) + " 2016 Года";


        TxtD.setText(out);
        String io = Array[id];
        for (int i=1;!String.valueOf(io.charAt(i)).equals("~");i++)
            p=i;
        io=io.substring(p+2);


        p=0;
        int k;
        for (int i=1;!String.valueOf(io.charAt(i)).equals("*");i++){
            if (String.valueOf(io.charAt(i)).equals("/")){



                View item = ltInflater.inflate(R.layout.itemcel, LnLayoutV, false);
                TextView tvName = (TextView) item.findViewById(R.id.tvName);
                item.getLayoutParams().width = FrameLayout.LayoutParams.MATCH_PARENT;
                String Wer = io.substring(p,i);
                p=i+3;
                tvName.setText(Wer);
                //Цвета текста
                if (String.valueOf(io.charAt(i+1)).equals("Ч"))         //Черный
                    tvName.setTextColor(Color.BLACK);
                if (String.valueOf(io.charAt(i+1)).equals("Н"))         //Синий
                    tvName.setTextColor(Color.BLUE);
                if (String.valueOf(io.charAt(i+1)).equals("К"))
                    tvName.setTextColor(Color.RED);                     //Красный
                if (String.valueOf(io.charAt(i+1)).equals("З"))         //Зеленый
                    tvName.setTextColor(Color.GREEN);
                if (String.valueOf(io.charAt(i+1)).equals("В"))
                    tvName.setTextColor(Color.parseColor("#FFFF6200")); //Коричневый
                if (String.valueOf(io.charAt(i+1)).equals("Ф"))         //Фиолетовый
                    tvName.setTextColor(Color.parseColor("#FFAA4AAF"));
                if (String.valueOf(io.charAt(i+1)).equals("С"))         //Серый
                    tvName.setTextColor(Color.parseColor("#FF444444"));
                //Фоны
                if (i+2<io.length()) {
                    if (String.valueOf(io.charAt(i + 2)).equals("Ж"))
                        item.setBackgroundColor(Color.parseColor("#46FFD900"));
                    if (String.valueOf(io.charAt(i + 2)).equals("С")) {
                        item.setBackgroundColor(Color.parseColor("#FFEBEFF0"));
                        tvName.setTypeface(null, Typeface.BOLD);
                    }
                    if (String.valueOf(io.charAt(i + 2)).equals("В"))
                        item.setBackgroundColor(Color.parseColor("#46FF0000"));
                    if (String.valueOf(io.charAt(i + 2)).equals("З"))
                        item.setBackgroundColor(Color.parseColor("#46239600"));
                    LnLayoutV.addView(item);

                    if (recipies==1){
                        Button GTRecepies = new Button(getApplicationContext());
                        LinearLayout.LayoutParams LP = new LinearLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT);
                        LP.setMargins(50,15,50,5);
                        GTRecepies.setElevation(15.0f);
                        GTRecepies.setBackgroundColor(Color.WHITE);
                        GTRecepies.setLayoutParams(LP);
                        GTRecepies.setText("ПОСМОТРЕТЬ РЕЦЕПТЫ");
                        GTRecepies.setTextColor(Color.BLACK);
                        GTRecepies.setTextSize(18f);
                        Log.d("LOG",Wer);

                        for (k=0;k<Posts.length;k++) {
                            if (Wer.contains(Posts[k])) {
                                GTRecepies.setId(8000 + k);
                                GTRecepies.setOnClickListener(oclBtn);
                                break;
                            }
                            else GTRecepies.setOnClickListener(oclBtn1);
                        }
                        LnLayoutV.addView(GTRecepies);
                        recipies=-1;
                    }
                    if (((String)tvName.getText()).contains("Пост")&&(recipies==0))
                        recipies=1;

                }


            }



       }
    }
}
