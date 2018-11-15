package com.name.rolo;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RecipiesChooseClass extends Activity implements View.OnTouchListener {
    static int RecipePostCH=-1;
    float fromPosition=0;



    View.OnClickListener oclBtn = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //v.setBackground(getResources().getDrawable(R.drawable.marcer_down));
            Intent intent = new Intent(getApplicationContext(),RecipiesClass.class);
            intent.putExtra("RP",RecipePostCH);
            intent.putExtra("RT",v.getId()-2000);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_activity);

        LinearLayout linLayout = (LinearLayout) findViewById(R.id.linLayout);
        final String[] PostsNames = getResources().getStringArray(getResources().getIdentifier("PostsNames", "array", getPackageName()));
        //findViewById(R.id.scrollprml).setOnTouchListener(this);

        LayoutInflater ltInflater = getLayoutInflater();
        View item;
        TextView tvNews;
        ImageView Pic;
        Button btn;
        if (getIntent().getIntExtra("RP", -1)!=-1)
            RecipePostCH=getIntent().getIntExtra("RP", -1);
        Log.d("LOG","Post: "+RecipePostCH);
        try {
            if (RecipePostCH<PostsNames.length)
                getActionBar().setTitle(PostsNames[RecipePostCH]);
            else getActionBar().setTitle("Рецепты");
        } catch (NullPointerException ignored){}



        if (RecipePostCH!=-1) {
            final String[] aqw = getResources().getStringArray(getResources().getIdentifier("RecipieTypes", "array", getPackageName()));
            final String[] postDescrs = getResources().getStringArray(getResources().getIdentifier("Recipies"+RecipePostCH, "array", getPackageName()));
            String string;
            int j;
            int a=aqw.length,b=postDescrs.length;
            for (int i = 0; i < a; i++) {
                /*j=0;
                while (j<b) {
                    string=postDescrs[j];
                    if (string.charAt(0)==i+48) break;
                    j++;
                }
                if (j==b) continue;*/


                item = ltInflater.inflate(R.layout.item_rec, linLayout, false);
                tvNews = (TextView) item.findViewById(R.id.textNews);
                Pic = (ImageView) item.findViewById(R.id.imageViewItemNews);
                btn = (Button) item.findViewById(R.id.NewsElemButton);
                tvNews.setText(aqw[i]);
                try {
                    Pic.setBackground(getResources().getDrawable(getResources().getIdentifier("recipie"+i,"drawable",getPackageName())));
                }
                catch (Exception e){
                    Pic.setVisibility(View.GONE);
                    Log.d("LOG","Error at recipie drawable");
                }
                item.getLayoutParams().width = FrameLayout.LayoutParams.MATCH_PARENT;
                btn.setId(i+2000);
                btn.setOnClickListener(oclBtn);
                //btn.setOnTouchListener(this);
                linLayout.addView(item);
            }
        }





    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        Log.d("LOG","On touch");
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN: // Пользователь нажал на экран, т.е. начало движения
                // fromPosition - координата по оси X начала выполнения операции

                fromPosition = event.getX();
                break;
            case MotionEvent.ACTION_UP: // Пользователь отпустил экран, т.е. окончание движения
                float toPosition = event.getX();
                if (fromPosition-50 > toPosition) {

                    Intent intent = new Intent(getApplicationContext(),RecipiesChooseClass.class);
                    intent.putExtra("RP",getIntent().getIntExtra("RP",0)-1);
                    startActivity(intent);
                    //overridePendingTransition(R.anim.go_prev_in2,R.anim.go_prev_out2);


                }
                else if (fromPosition+50 < toPosition) {
                    Intent intent = new Intent(getApplicationContext(),RecipiesChooseClass.class);
                    intent.putExtra("RP",getIntent().getIntExtra("RP",0)+1);
                    startActivity(intent);
                    //overridePendingTransition(R.anim.go_prev_in,R.anim.go_prev_out);

                }
                else{
                    Intent intent = new Intent(getApplicationContext(),RecipiesClass.class);
                    intent.putExtra("RP",RecipePostCH);
                    intent.putExtra("RT",view.getId()-2000);
                    startActivity(intent);
                }
            default:
                break;
        }
        return true;
    }
}
