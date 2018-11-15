package com.name.rolo;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import java.util.ArrayList;

import static java.lang.Math.abs;


public class WordActivity extends AppCompatActivity {

    ScrollView scrollView;
    SharedPreferences sPref;
    int t;
    int scrollPos;
    ArrayList<String> Imena = new ArrayList<>();
    Typeface CF;
    final int border=60;

    static int folder=-2;
    static int id=-2;
    static int draw=-2;
    static String string=null;
    static Dialog dialog;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:

                    String ADB = string;
                if (ADB==null) ADB="";

                if ((folder==1)) {
                    Intent intent = new Intent(this, WordChoiceClass.class);
                    startActivity(intent);
                }
                else {
                    Intent intent = new Intent(this, WordActivity.class);
                    intent.putExtra("TypeMol", getIntent().getBooleanExtra("TypeMol", true));
                    intent.putExtra("folder",folder-1);
                    if (folder>2)
                        intent.putExtra("id", id);
                    if (id!=-1) {
                        int a = -1, b = -1;
                        for (int i = 1; i < ADB.length(); i++) {
                            if (ADB.charAt(i) == '/') {
                                b = a;
                                a = i;
                            }
                        }
                        ADB = ADB.substring(0, b + 1);
                        intent.putExtra("string", ADB);
                    }
                    startActivity(intent);
                }
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

    String[] names;

    int[] colors = new int[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_activity);
        CF = Typeface.createFromAsset(getAssets(), MainActivity.MAIN_FONT_ADRESS);
        colors[0] = Color.parseColor("#559966CC");
        colors[1] = getResources().getColor(R.color.colorGrey);

        if ((getIntent().getIntExtra("folder", -2)!=-2)||(folder==-2))
            folder=getIntent().getIntExtra("folder", 1);
        if (((getIntent().getIntExtra("id", -2)!=-2)||(id==-2))||(folder==1))
            id = getIntent().getIntExtra("id",0);
        if (((getIntent().getIntExtra("draw", -2)!=-2)||(draw==-2))||(folder==1))
            draw = getIntent().getIntExtra("draw",0);
        if (getIntent().getStringExtra("string")!=null)
            string = getIntent().getStringExtra("string");


        if (getIntent().getBooleanExtra("TypeMol", true)) {

            View.OnClickListener oclBtnFile = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), DescrActivity.class);
                    intent.putExtra("id", v.getId() - 10000);
                    TextView textView = (TextView) v.findViewById(R.id.tvName);
                    intent.putExtra("name", textView.getText().toString());
                    intent.putExtra("folder",getIntent().getIntExtra("folder", 1));
                    intent.putExtra("TypeMol", true); //определяем содержимое DescrActivity
                    if (getIntent().getIntExtra("id",0)==-1)
                        intent.putExtra("string", "NO");
                    startActivity(intent);
                }
            };

            View.OnClickListener oclBtn = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), WordActivity.class);
                        intent.putExtra("id", v.getId() - 10000 + 1);
                        if (folder == 1)
                            intent.putExtra("draw", v.getId() - 10000 + 1);
                        else
                            intent.putExtra("draw", draw);
                    if (v.getId()!=10000-2)
                        intent.putExtra("string", Imena.get(v.getId() - 10000 - 1));
                    intent.putExtra("folder", folder + 1);
                    intent.putExtra("TypeMol", true); //определяем содержимое DescrActivity
                    startActivity(intent);
                }
            };


            LinearLayout linLayout = (LinearLayout) findViewById(R.id.linLayout);
            LayoutInflater ltInflater = getLayoutInflater();
            int o=0;
            int resourseId;
            int rr=0;
            String now;


            sPref = getSharedPreferences("MolsLikes",MODE_PRIVATE);

            t = getResources().getStringArray(R.array.Mols).length;
            names = getResources().getStringArray(R.array.Mols);
            View item;
            TextView tvName;
            int ex;
            int n;
            int af;
            int u;
            boolean file;
            ImageView imageView;
            int a;
            int j;
            if (id!=-1) {
                for (int i = 0; i < t; i++) {

                    item = ltInflater.inflate(R.layout.item, linLayout, false);
                    tvName = (TextView) item.findViewById(R.id.tvName);
                    now = names[i];
                    ex = -1;
                    n = 0;
                    af = -1;


                    u = -1;
                    for (a = -1; a < folder; a++) {
                        for (j = af + 1; ((j < now.length()) && !(now.charAt(j) == '/')); j++) {
                            af = j + 1;
                            if (folder - a == 2) {
                                n = j + 1;
                            }
                            if (folder - a == 3) {
                                ex = j + 1;
                            }
                        }
                    }
                    file = n == now.length();
                    String before = "";
                    if ((ex < n) && (u + 1 < names[i].length())) {
                        now = names[i].substring(ex + 1, n);
                        before = names[i].substring(0, ex + 1);
                    }
                    String intents = string;
                    if (intents == null) intents = "";
                    if ((!Imena.contains(intents + now + "/")) && (((string == null)) || (before.equals(intents)))) {
                        o++;
                        tvName.setText(now);
                        tvName.setTypeface(CF);

                        if (string != null)
                            Imena.add(intents + now + "/");
                        else
                            Imena.add(now + "/");
                        item.getLayoutParams().width = FrameLayout.LayoutParams.MATCH_PARENT;

                        if (file) {
                            item.setOnClickListener(oclBtnFile); //при нажатии любой кнопки перехождим к oclBtn
                            item.setId(i + 10000); //по этому значению будем определять, какая кнопка нажата(+10000 - чтобы не было конфликтов)
                           try {
                                resourseId = getResources().getIdentifier("mols_icon_" + String.valueOf((draw - 1) % 3), "drawable", this.getPackageName());
                                imageView = (ImageView) item.findViewById(R.id.mol_image);
                                imageView.setBackground(getResources().getDrawable(resourseId));
                           } catch (Exception ignored){}
                        } else {
                            imageView = (ImageView) item.findViewById(R.id.mol_image);
                            rr++;

                            try {
                                if (draw == 0) {
                                    resourseId = getResources().getIdentifier("mols_icon_" + String.valueOf(rr % 3), "drawable", this.getPackageName());
                                    imageView.setBackground(getResources().getDrawable(resourseId));
                                } else {
                                    resourseId = getResources().getIdentifier("mols_icon_" + String.valueOf((draw - 1) % 3), "drawable", this.getPackageName());
                                    imageView.setBackground(getResources().getDrawable(resourseId));
                                }
                            } catch (Exception ignored){}
                            item.setId(o + 10000); //по этому значению будем определять, какая кнопка нажата
                            item.setOnClickListener(oclBtn); //при нажатии любой кнопки перехождим к oclBtn
                        }
                        linLayout.addView(item);
                    }
                }
            }
        } else {
            scrollView = (ScrollView) findViewById(R.id.scrollprml);
            final ImageView imageView1 = (ImageView) findViewById(R.id.ButtonUpDown);
            scrollPos = getIntent().getIntExtra("position", 0);
            if (scrollPos!=0) {
                imageView1.setBackground(getResources().getDrawable(R.drawable.down_button_selector));
                imageView1.setVisibility(View.VISIBLE);
            }
            if (getActionBar()!=null)
            getActionBar().setTitle("Притчи");
            View.OnClickListener oclBtn = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), DescrActivity.class);
                    //if (getIntent().getBooleanExtra("TypeMol", true))
                    //    intent.putExtra("id-folder", getIntent().getIntExtra("id", 1));
                    intent.putExtra("id-file", v.getId() - 10000);
                    intent.putExtra("position", scrollView.getScrollY());
                    intent.putExtra("TypeMol", getIntent().getBooleanExtra("TypeMol", true)); //определяем содержимое DescrActivity
                    startActivity(intent);
                }
            };

            dialog = new Dialog(WordActivity.this);
            dialog.setTitle("ПОКУПКА ПОЛНОЙ ВЕРСИИ");
            dialog.setContentView(R.layout.zaglushka);
            ((TextView) dialog.findViewById(R.id.zaglushkaName1)).setTypeface(CF);
            ((TextView) dialog.findViewById(R.id.zaglushkaName2)).setTypeface(CF);
            ((TextView) dialog.findViewById(R.id.zaglushkaName3)).setTypeface(CF);

            Button btn = (Button) dialog.findViewById(R.id.zaglushkaBtn);
            btn.setTypeface(CF);
            btn.setOnClickListener(oclBtn4);

            View.OnClickListener oclBtn3 = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //Log.d("LOG","Show Dialog");
                    dialog.show();
                }
            };

            View.OnTouchListener otlBtn = new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent motionEvent) {
                    if ((imageView1.getVisibility()==View.INVISIBLE )&&(abs(v.getScrollY()-scrollPos)>1000))
                        imageView1.setVisibility(View.VISIBLE);
                    else if ((imageView1.getVisibility()==View.VISIBLE )&&(abs(v.getScrollY()-scrollPos)<1000)) {
                        imageView1.setVisibility(View.INVISIBLE);
                        scrollPos=0;
                    }
                    if ((imageView1.getVisibility()==View.VISIBLE)&&(scrollView.getScrollY()<scrollPos))
                        imageView1.setBackground(getResources().getDrawable(R.drawable.down_button_selector));
                    else if ((imageView1.getVisibility()==View.VISIBLE)&&(scrollView.getScrollY()>scrollPos))
                        imageView1.setBackground(getResources().getDrawable(R.drawable.up_button_selector));
                    return false;
                }
            };




            scrollView.setOnTouchListener(otlBtn);
            View.OnClickListener oclBtn2 = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    scrollView.smoothScrollTo(0,scrollPos);
                        imageView1.setVisibility(View.INVISIBLE);
                }
            };
           imageView1.setOnClickListener(oclBtn2);
            int resourceID;
                resourceID = getResources().getIdentifier("Pritchs", "array", this.getPackageName());
                t = getResources().getStringArray(resourceID).length;

            LinearLayout linLayout = (LinearLayout) findViewById(R.id.linLayout);
            LayoutInflater ltInflater = getLayoutInflater();

            sPref = getSharedPreferences("PritchsData3",MODE_PRIVATE);
            String str = sPref.getString("Read","");
            names = getResources().getStringArray(resourceID);

            for (int i = 0;i < t-1; i++) {
                if (!names[i].equals("")) {
                    if ((i<border)&&(i%3==0)) {
                        View item = ltInflater.inflate(R.layout.item, linLayout, false);
                        TextView tvName = (TextView) item.findViewById(R.id.tvName);
                        tvName.setText(names[i]);
                        tvName.setTypeface(CF);
                        item.setId(i + 10000); //по этому значению будем определять, какая кнопка нажата
                        item.getLayoutParams().width = FrameLayout.LayoutParams.MATCH_PARENT;
                        linLayout.addView(item);
                        item.setOnClickListener(oclBtn); //при нажатии любой кнопки перехождим к oclBtn
                        ImageView imageView = (ImageView) item.findViewById(R.id.mol_image);
                        imageView.setBackground(getResources().getDrawable(R.drawable.button_pritcha));
                    } else if (i>border) {
                        View item = ltInflater.inflate(R.layout.item, linLayout, false);
                        TextView tvName = (TextView) item.findViewById(R.id.tvName);
                        tvName.setText(names[i]);
                        tvName.setTypeface(CF);
                        item.setId(i + 10000); //по этому значению будем определять, какая кнопка нажата
                        item.getLayoutParams().width = FrameLayout.LayoutParams.MATCH_PARENT;
                        linLayout.addView(item);
                        item.setOnClickListener(oclBtn3); //при нажатии любой кнопки перехождим к oclBtn
                        ImageView imageView = (ImageView) item.findViewById(R.id.mol_image);
                        item.findViewById(R.id.itemFrame).setBackground(getResources().getDrawable(R.drawable.item_selector_unused));
                        imageView.setBackground(getResources().getDrawable(R.drawable.button_pritcha));

                    }
                }
            }
            int j = 0;
            for (int i=0;i<str.length();i++){
                if (str.charAt(i)!='/'){
                    if (str.charAt(i)!='!')
                        j=j*10+ ((int) str.charAt(i)-48);
                }
                else
                {
                    View item = findViewById(j + 10000);
                    item.findViewById(R.id.itemFrame).setBackground(getResources().getDrawable(R.drawable.item_selector_grey));
                    j=0;
                }
            }
        }
    }
}
