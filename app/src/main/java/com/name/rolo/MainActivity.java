package com.name.rolo;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity implements View.OnTouchListener{

    private LinearLayout News;
    private LinearLayout Cal;
    private LinearLayout Word;
    private LinearLayout Donat;
    public static final String SHARED_NAME = "SharesRolo";
    public static final String MAIN_FONT_ADRESS = "times.ttf";
    public static final String MOLS_FONT_ADRESS = "times.ttf";
    public static Dialog dialog;
    static public Typeface CF;


    public static final String MARKET_ADRESS = "market://details?id=com.name.rfvpbst";
    public static final String BROWSER_ADRESS = "http://play.google.com/store/apps/details?id=com.name.rfvpbst";


    @Override
    protected void onResume() {
        //News.setBackground(getResources().getDrawable(R.drawable.button_news));
        News.setBackground(getResources().getDrawable(R.drawable.button_news));
        Cal.setBackground(getResources().getDrawable(R.drawable.button_calendar));
        Word.setBackground(getResources().getDrawable(R.drawable.button_word));
        Donat.setBackground(getResources().getDrawable(R.drawable.button_blagotvor));




        super.onResume();
    }


    View.OnClickListener oclBtn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(MARKET_ADRESS));
                startActivity(intent);
            } catch (Exception e){
                try{
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(BROWSER_ADRESS));
                    startActivity(intent);
                } catch (Exception ignored){}
            }
        }
    };

    View.OnClickListener oclBtn2 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            dialog.show();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sPref;
        CF = Typeface.createFromAsset(getAssets(), MainActivity.MAIN_FONT_ADRESS);

        sPref = getSharedPreferences(SHARED_NAME,MODE_PRIVATE);
        if (!sPref.getBoolean("first", false)&&getIntent().getBooleanExtra("Firstend",true)){
            Log.d("Log","Putting");
            SharedPreferences.Editor ed = sPref.edit();
            ed.putBoolean("first",true);
            ed.apply();
            ed.commit();
            startActivity(new Intent(this, WelcomeClass.class));
        }

            dialog = new Dialog(MainActivity.this);
            dialog.setTitle("ПОКУПКА ПОЛНОЙ ВЕРСИИ");
            dialog.setContentView(R.layout.zaglushka);
            ((TextView) dialog.findViewById(R.id.zaglushkaName1)).setTypeface(CF);
            ((TextView) dialog.findViewById(R.id.zaglushkaName2)).setTypeface(CF);
            ((TextView) dialog.findViewById(R.id.zaglushkaName3)).setTypeface(CF);

            Button btn = (Button) dialog.findViewById(R.id.zaglushkaBtn);
            btn.setTypeface(CF);
            btn.setOnClickListener(oclBtn);

        try {
            getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            getSupportActionBar().setCustomView(R.layout.action_bar_layout);
            getSupportActionBar().getCustomView().findViewById(R.id.ProBtn).setOnClickListener(oclBtn2);
            //getActionBar().show();
        } catch (NullPointerException e){
            Log.e("LOG","No Action Bar");
        }




        News= (LinearLayout) findViewById(R.id.BtnMainNews);
        Cal= (LinearLayout) findViewById(R.id.BtnMainCal);
        Word= (LinearLayout) findViewById(R.id.BtnMainWord);
        Donat= (LinearLayout) findViewById(R.id.BtnMainDonat);
        News.setBackground(getResources().getDrawable(R.drawable.button_news));
        Cal.setBackground(getResources().getDrawable(R.drawable.button_calendar));
        Word.setBackground(getResources().getDrawable(R.drawable.button_word));
        Donat.setBackground(getResources().getDrawable(R.drawable.button_blagotvor));
        News.setOnTouchListener(this);
        Cal.setOnTouchListener(this);
        Word.setOnTouchListener(this);
        Donat.setOnTouchListener(this);
        //Map= (Button) findViewById(R.id.BtnMainMap);
    }

    public boolean onTouch(View v, MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (v.getId()==R.id.BtnMainNews){
                News.setBackground(getResources().getDrawable(R.drawable.button_news_pressed));
                //dialog.show();
                Intent intent = new Intent(this, NewsActivity.class);
                startActivity(intent);
            }
            if (v.getId()==R.id.BtnMainCal){
                Cal.setBackground(getResources().getDrawable(R.drawable.button_calendar_pressed));
                Intent intent = new Intent(this, CalendarClass.class);
                startActivity(intent);
            }
            if (v.getId()==R.id.BtnMainWord){
                Word.setBackground(getResources().getDrawable(R.drawable.button_word_pressed));
                Intent intent = new Intent(this, WordChoiceClass.class);
                startActivity(intent);
            }
            if (v.getId()==R.id.BtnMainDonat){
                SharedPreferences sPref;
                sPref = getSharedPreferences(SHARED_NAME,MODE_PRIVATE);
                if (sPref.getBoolean("firstDonat", false)) {
                    Donat.setBackground(getResources().getDrawable(R.drawable.button_blagotvor_pressed));
                    Intent intent = new Intent(this, DonatClassTrivial.class);
                    startActivity(intent);
                }
                else {
                    Donat.setBackground(getResources().getDrawable(R.drawable.button_blagotvor_pressed));
                    Intent intent = new Intent(this, WelcomeClassDonat.class);
                    startActivity(intent);

                }
            }
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            News.setBackground(getResources().getDrawable(R.drawable.button_news));
            Cal.setBackground(getResources().getDrawable(R.drawable.button_calendar));
            Word.setBackground(getResources().getDrawable(R.drawable.button_word));
            Donat.setBackground(getResources().getDrawable(R.drawable.button_blagotvor));

        }
        return true;
    }
}


