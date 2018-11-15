package com.name.rolo;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CalendarClass extends Activity {


    final int[] Month = {31,29,31,30,31,30,31,31,30,31,30,31};
    final String[] MonthNames = {"ЯНВАРЬ","ФЕВРАЛЬ","МАРТ","АПРЕЛЬ","МАЙ","ИЮНЬ","ИЮЛЬ","АВГУСТ","СЕНТЯБРЬ","ОКТЯБРЬ","НОЯБРЬ","ДЕКАБРЬ"};
    final String[] MonthNamesDecl = {"ЯНВАРЯ","ФЕВРАЛЯ","МАРТА","АПРЕЛЯ","МАЯ","ИЮНЯ","ИЮЛЬ","АВГУСТА","СЕНТЯБРЯ","ОКТЯБРЯ","НОЯБРЯ","ДЕКАБРЯ"};
    final int colorGray = Color.GRAY;
    final int colorBlack = Color.BLACK;
    final int colorBlue = Color.BLUE;
    final int colorGreen = Color.GREEN;
    final int colorRed = Color.RED;
    final int colorViolet = Color.parseColor("#FFAA4AAF");
    final int colorOrange = Color.parseColor("#FFFF6200");
    final int colorLightGreen = Color.parseColor("#46239600");
    final int colorLightYellow = Color.parseColor("#46FFD900");
    final int colorLightBrown = Color.parseColor("#46FF5900");
    final int colorBBlack = Color.parseColor("#090909");
    public static Date Now;



    ViewFlipper flipper;
    int o=0;
    static int line=0;
    private static int idToday;


    TextView MonthName;
    TextView[] Comps = new TextView[43];


    public void Today(View v){
        Intent intent = new Intent(getApplicationContext(),CelebrityClass.class);
        intent.putExtra("id",idToday);
        intent.putExtra("Month",Now.getMonth());
        //Log.d("TAG",String.valueOf(idToday));
        //intent.putExtra("Month",getIntent().getIntExtra("Month",-1));
        startActivity(intent);
    }

    private ArrayList<View> getChilds(Object o) {
        ArrayList<View> views = new ArrayList<>();
        if (o instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) o;
            views.add(vg);
            int count = vg.getChildCount();
            for (int i = 0; i < count; i++) {
                views.addAll(getChilds(vg.getChildAt(i)));
            }
        } else if (o instanceof View) {
            views.add((View) o);
        }
        return views;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View mainView =  LayoutInflater.from(this).inflate(R.layout.calendar_activity, null);
        setContentView(mainView);
        LinearLayout LnLayoutV = (LinearLayout) findViewById(R.id.ShortLayoutCal);
        LayoutInflater ltInflater = getLayoutInflater();



        View.OnClickListener oclBtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("Debug","Кнопка "+ v.getId()+" Была нажата");
                v.setBackground(getResources().getDrawable(R.drawable.marcer_down));
                Intent intent = new Intent(getApplicationContext(),CelebrityClass.class);
                intent.putExtra("id",v.getId()-90000);
                intent.putExtra("Month",getIntent().getIntExtra("Month",Now.getMonth()));
               // intent.putExtra("Month",getIntent().getIntExtra("Month",-1));
                startActivity(intent);
            }
        };
        View.OnClickListener oclBtn2 = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId()==R.id.ButtonCalendarMonthBack){
                    Intent intent = new Intent(v.getContext(),CalendarClass.class);
                    Date Now = Calendar.getInstance().getTime();
                    o = getIntent().getIntExtra("Month",Now.getMonth());
                    if (o==0)
                        intent.putExtra("Month",11);
                    else
                        intent.putExtra("Month",o-1);
                    intent.putExtra("line",line);
                    startActivity(intent);
                } else
                if (v.getId()==R.id.ButtonCalendarMonthNext){
                    Intent intent = new Intent(v.getContext(),CalendarClass.class);
                    Date Now = Calendar.getInstance().getTime();
                    o = getIntent().getIntExtra("Month",Now.getMonth());
                    if (o==11)
                        intent.putExtra("Month",0);
                    else
                        intent.putExtra("Month",o+1);
                    intent.putExtra("line",line);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "В этот день нет ни праздников ни постов", Toast.LENGTH_SHORT).show();
                }
            }
        };
        (findViewById(R.id.ButtonCalendarMonthBack)).setOnClickListener(oclBtn2);
        (findViewById(R.id.ButtonCalendarMonthNext)).setOnClickListener(oclBtn2);

        MonthName = (TextView) findViewById(R.id.TxtMonthName);
        Calendar clndr = Calendar.getInstance();
        Now = clndr.getTime();

        if (Now.getYear()%4==0) Month[1]=29;
        else Month[1]=28;

        ((TextView) findViewById(R.id.NumbDateTvCal)).setText(String.valueOf(clndr.getTime().getDate()));
        ((TextView) findViewById(R.id.DateTvCal)).setText(MonthNamesDecl[Now.getMonth()]);
        if (getIntent().getIntExtra("Month",Now.getMonth())==-1)
            o=Now.getMonth();
        else
            o = getIntent().getIntExtra("Month",Now.getMonth());
        findViewById(R.id.ButtonBackMonth).setOnClickListener(oclBtn2);
        findViewById(R.id.ButtonNextMonth).setOnClickListener(oclBtn2);
        flipper = (ViewFlipper) findViewById(R.id.flipercalendar);
        final String[] Array = getResources().getStringArray(getResources().getIdentifier("Celebrities"+String.valueOf(o), "array", getPackageName()));


        ArrayList<View> allViews = getChilds(mainView);
        int b=1;
        //System.out.println("View count: " + allViews.size());
        for (View t : allViews) {
            if ((t instanceof TextView)&&((TextView)t).getText().equals("Ч")) {
                Comps[b]=(TextView) t;
                b++;
            }

        }



        for (int y=1;y<43;y++){
            Comps[y].setOnClickListener(oclBtn2);
        }

        int k=1; //день недели, с которого начинается наш месяц
        int j;
        if (o<Now.getMonth()) {
            j = Now.getDate()-1;
            for (int i = o; i < Now.getMonth(); i++)
                j = j + Month[i];
            k = (7+Now.getDay()-j%7)%7;
        }
        if (o>Now.getMonth()) {
            j = Month[Now.getMonth()]-Now.getDate()+1;
            if (o-1>Now.getMonth())
                for (int i = o-1; i > Now.getMonth(); i--)
                    j = j + Month[i];
            k = (Now.getDay()+(j%7))%7;
        }
        if (o==Now.getMonth()){
            j=Now.getDate()-1;
            k=(7+Now.getDay()-j%7)%7;
        }
        if (k==0) k=7;
        for (int i = 1; i<=Month[o];i++) {
            Comps[k+i-1].setText(Integer.toString(i));
            //Comps[k+i-1].setBackgroundColor(colorInvis[i%2]);
        }
        for (int i =k-1;i>0;i--){
            Comps[i].setVisibility(View.INVISIBLE);
        }
        for (int i = k+Month[o];i<=42;i++){
            Comps[i].setVisibility(View.INVISIBLE);
        }
        for (int i = 1; i<43;i++){
            if (Comps[i].getText().equals(Integer.toString(Now.getDate())) && Now.getMonth()==getIntent().getIntExtra("Month",Now.getMonth())&&Comps[i].getTextColors().getDefaultColor()!=colorBBlack){
                Comps[i].setBackground(getResources().getDrawable(R.drawable.marcer_r));
            }
        }
        char r,r1;
        int l=0;
        if (getIntent().getIntExtra("line",-1)!=-1)
            line = getIntent().getIntExtra("line",0);
        if ((line!=0)&&(getIntent().getIntExtra("Month",-1)!=Now.getMonth())){
            final String aqw = getResources().getStringArray(getResources().getIdentifier("Celebrities"+String.valueOf(Now.getMonth()), "array", getPackageName()))[line];
            int start = 6;

            for (int q = 6;!String.valueOf(aqw.charAt(q)).equals("~");q++){
                if (String.valueOf(aqw.charAt(q)).equals("/")){
                    if (l>2) break;
                    View item = ltInflater.inflate(R.layout.itemcel, LnLayoutV, false);
                    TextView tvName = (TextView) item.findViewById(R.id.tvName);
                    item.getLayoutParams().width = FrameLayout.LayoutParams.MATCH_PARENT;
                    String Wer = aqw.substring(start,q);
                    start=q+3;
                    tvName.setText(Wer);
                    r=aqw.charAt(q+1);
                    r1=aqw.charAt(q+2);

                    if (String.valueOf(r).equals("Ч"))
                        tvName.setTextColor(colorBlack);
                    else
                    if (String.valueOf(r).equals("Н"))
                        tvName.setTextColor(colorBlue);
                    else
                    if (String.valueOf(r).equals("К"))
                        tvName.setTextColor(colorRed);
                    else
                    if (String.valueOf(r).equals("З"))
                        tvName.setTextColor(colorGreen);
                    else
                    if (String.valueOf(r).equals("Ф"))
                        tvName.setTextColor(colorViolet);
                    else
                    if (String.valueOf(r).equals("В")) //коричневый
                        tvName.setTextColor(colorOrange);
                    else
                    if (String.valueOf(r).equals("С"))//Серый
                        tvName.setTextColor(colorGray);
                    if (String.valueOf(r1).equals("С"))//Серый
                    {
                        item.setBackgroundColor(Color.parseColor("#FFC1C1C1"));
                        tvName.setTypeface(null, Typeface.BOLD);
                    }
                    else
                    if (String.valueOf(r1).equals("Ж"))//Желтый
                        item.setBackgroundColor(colorLightYellow);
                    else
                    if (String.valueOf(r1).equals("В"))//Коричневый
                        item.setBackgroundColor(colorLightBrown);
                    else
                    if (String.valueOf(r1).equals("З"))//зеленый
                        item.setBackgroundColor(colorLightGreen);
                    LnLayoutV.addView(item);
                    l++;

                }
            }
        }
        int a;

        //Log.d("LOG",String.valueOf(Array.length));
        MonthName.setText(MonthNames[o]);
        for (int i = 0;i<Array.length;i++){
            r = Array[i].charAt(0);
            a = (((int) r) - 48)*10;
            r = Array[i].charAt(1);
            a = a + (((int) r) - 48);
            /*Log.d("LOG","a = "+String.valueOf(a));
            Log.d("LOG","o = "+String.valueOf(o));*/
            if (a>o) break;
            if (a==o){
                r = Array[i].charAt(3);
                a = Integer.valueOf(String.valueOf(r))*10;  // число, когда праздник
                r = Array[i].charAt(4);
                a = a + Integer.valueOf(String.valueOf(r));  // число, когда праздник
                if ((a+k-1<=42)&&(Comps[a+k-1].getVisibility()==View.VISIBLE)) {
                    r=Array[i].charAt(5);
                    if (!(r=='Б'))
                        Comps[a + k-1].setTextColor(Color.WHITE);
                    if (!(((int)r>(int)'А')&&(((int)r<(int)'Я')))) {
                        Comps[a + k - 1].setTextColor(Color.RED);
                        if (r=='P')
                            Comps[a + k - 1].setBackground(getResources().getDrawable(R.drawable.marcer_gr));
                        else
                        if (r=='C')
                            Comps[a + k-1].setBackground(getResources().getDrawable(R.drawable.marcer_b));
                        else
                        if (r==';')
                            Comps[a + k-1].setBackground(getResources().getDrawable(R.drawable.marcer_y));
                        else
                        if (r=='D')
                            Comps[a + k-1].setBackground(getResources().getDrawable(R.drawable.marcer_br));
                        else
                        if (r=='V')
                            Comps[a + k-1].setBackground(getResources().getDrawable(R.drawable.marcer_m)); //морской
                        else
                        if (r=='N')
                            Comps[a + k-1].setBackground(getResources().getDrawable(R.drawable.marcer_t)); //темно-зеленый
                        else
                        if (r=='J')
                            Comps[a + k-1].setBackground(getResources().getDrawable(R.drawable.marcer_o));
                    }

                    if (r=='З')
                        Comps[a + k - 1].setBackground(getResources().getDrawable(R.drawable.marcer_gr));
                    else
                    if (r=='С')
                        Comps[a + k-1].setBackground(getResources().getDrawable(R.drawable.marcer_b));
                    else
                    if (r=='Ж')
                        Comps[a + k-1].setBackground(getResources().getDrawable(R.drawable.marcer_y));
                    else
                    if (r=='В')
                        Comps[a + k-1].setBackground(getResources().getDrawable(R.drawable.marcer_br));
                    else
                    if (r=='М')
                        Comps[a + k-1].setBackground(getResources().getDrawable(R.drawable.marcer_m)); //морской
                    else
                    if (r=='Т')
                        Comps[a + k-1].setBackground(getResources().getDrawable(R.drawable.marcer_t)); //темно-зеленый
                    else
                    if (r=='Ф')
                        Comps[a + k-1].setBackground(getResources().getDrawable(R.drawable.marcer_v)); //темно-зеленый
                    else
                    if (r=='О')
                        Comps[a + k-1].setBackground(getResources().getDrawable(R.drawable.marcer_o));
                    //    Comps[a + k-1].setBackgroundColor(Color.WHITE);
                    Comps[a+k-1].setOnClickListener(oclBtn);
                    Comps[a+k-1].setId(i+90000);
                    if (Comps[a+k-1].getText().equals(String.valueOf(Now.getDate())) && Now.getMonth()==o && Comps[a + k-1].getTextColors().getDefaultColor()!=colorBBlack){
                        int start = 6;
                        line = i;
                        idToday = i;
                        String aqw = Array[i];
                        for (int q = 6;!String.valueOf(aqw.charAt(q)).equals("~");q++){
                            if (String.valueOf(aqw.charAt(q)).equals("/")){
                                if (l>2) break;
                                View item = ltInflater.inflate(R.layout.itemcel, LnLayoutV, false);
                                TextView tvName = (TextView) item.findViewById(R.id.tvName);
                                item.getLayoutParams().width = FrameLayout.LayoutParams.MATCH_PARENT;
                                String Wer = aqw.substring(start,q);
                                start=q+3;
                                tvName.setText(Wer);
                                r=aqw.charAt(q+1);
                                r1=aqw.charAt(q+2);
                                if (String.valueOf(r).equals("Ч"))
                                    tvName.setTextColor(colorBlack);
                                else
                                if (String.valueOf(r).equals("Н"))
                                    tvName.setTextColor(colorBlue);
                                else
                                if (String.valueOf(r).equals("К"))
                                    tvName.setTextColor(colorRed);
                                else
                                if (String.valueOf(r).equals("З"))
                                    tvName.setTextColor(colorGreen);
                                else
                                if (String.valueOf(r).equals("Ф"))
                                    tvName.setTextColor(colorViolet);
                                else
                                if (String.valueOf(r).equals("В")) //коричневый
                                    tvName.setTextColor(colorOrange);
                                else
                                if (String.valueOf(r).equals("С"))//Серый
                                    tvName.setTextColor(colorGray);
                                if (String.valueOf(r1).equals("С"))//Серый
                                {
                                    item.setBackgroundColor(Color.parseColor("#FFC1C1C1"));
                                    tvName.setTypeface(null, Typeface.BOLD);
                                }
                                else
                                if (String.valueOf(r1).equals("Ж"))//Желтый
                                    item.setBackgroundColor(colorLightYellow);
                                else
                                if (String.valueOf(r1).equals("В"))//Коричневый
                                    item.setBackgroundColor(colorLightBrown);
                                else
                                if (String.valueOf(r1).equals("З"))//зеленый
                                    item.setBackgroundColor(colorLightGreen);
                                l++;
                                LnLayoutV.addView(item);
                            }
                        }
                        Comps[a + k-1].setBackground(getResources().getDrawable(R.drawable.marcer_r));
                        Comps[a + k-1].setTextColor(Color.WHITE);
                    }
                }
            }
        }
    }
}
