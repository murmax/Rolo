package com.name.rolo;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class RecipiesClass extends Activity {
    static int RecipeType=-1;
    static int RecipePost=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.word_activity);

        View.OnClickListener oclBtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.setBackground(getResources().getDrawable(R.drawable.marcer_down));
                Intent intent = new Intent(getApplicationContext(),RecipiesDescriptionClass.class);
                if (v.getId()<2000) {
                    intent.putExtra("RP", RecipePost);
                    intent.putExtra("ID", v.getId() - 1000);
                }
                else {
                    intent.putExtra("RP", (v.getId()-2000)/100);
                    intent.putExtra("ID", (v.getId())%100);
                }
                startActivity(intent);
            }
        };


        LinearLayout linLayout = (LinearLayout) findViewById(R.id.linLayout);
        LayoutInflater ltInflater = getLayoutInflater();
        View item;
        TextView tvNews;
        ImageView Pic;
        Button btn;
        if (getIntent().getIntExtra("RP", -1)!=-1) {
            RecipePost = getIntent().getIntExtra("RP", -1);
        }
        /*if (getIntent().getIntExtra("RT", -1)!=-1) {
            RecipeType = getIntent().getIntExtra("RT", -1);
        }*/
        RecipeType=0;

        final int lengthPost = getResources().getStringArray(getResources().getIdentifier("Posts", "array", getPackageName())).length;

        int i;
        int count=0;

        String[] aqw;
        String anAqw;
        if (RecipePost!=-1) {
            aqw = getResources().getStringArray(getResources().getIdentifier("Recipies" + RecipePost, "array", getPackageName()));

            for (i = 0; i < aqw.length; i++) {
                anAqw = aqw[i];
                //Log.d("LOG", "First letter: "+String.valueOf((int)anAqw.charAt(0)));
                if (anAqw.charAt(0) == RecipeType + 48) {
                    RecipeType++;
                    item = ltInflater.inflate(R.layout.item_rec, linLayout, false);
                    tvNews = (TextView) item.findViewById(R.id.textNews);
                    //tvNews.setTextColor(Color.RED);
                    Pic = (ImageView) item.findViewById(R.id.imageViewItemNews);
                    btn = (Button) item.findViewById(R.id.NewsElemButton);
                    try {
                        tvNews.setText(anAqw.substring(1, anAqw.indexOf('~')));
                    } catch (Exception e) {
                        Log.d("LOG", "Error at recepie text");
                        continue;
                    }
                    try {
                        Pic.setBackground(getResources().getDrawable(getResources().getIdentifier("recipies" + RecipePost + "_" + i, "drawable", getPackageName())));
                    } catch (Exception e) {
                        Pic.setVisibility(View.GONE);
                    }
                    btn.setId(i + 1000);
                    btn.setOnClickListener(oclBtn);

                    item.getLayoutParams().width = FrameLayout.LayoutParams.MATCH_PARENT;
                    linLayout.addView(item);
                    count++;
                }
            }
        }

        if (count==0) {
            Toast.makeText(getApplicationContext(),"Сегодня нет поста",Toast.LENGTH_SHORT).show();
            finish();
        }
        //Log.d("LOG", "lengthPost is: "+String.valueOf(lengthPost));
        /*for (int j =0;j<lengthPost;j++){
            if (j!=RecipePost) {
                Log.d("LOG", "Post now is: " + String.valueOf(j));
                try {
                    aqw = getResources().getStringArray(getResources().getIdentifier("Recipies" + j, "array", getPackageName()));

                    for (i = 0; i < aqw.length; i++) {
                        anAqw = aqw[i];
                        Log.d("LOG", "First letter: " + String.valueOf((int) anAqw.charAt(0)));
                        if (anAqw.charAt(0) == RecipeType + 48) {
                            item = ltInflater.inflate(R.layout.item_rec, linLayout, false);
                            tvNews = (TextView) item.findViewById(R.id.textNews);
                            Pic = (ImageView) item.findViewById(R.id.imageViewItemNews);
                            btn = (Button) item.findViewById(R.id.NewsElemButton);
                            try {
                                tvNews.setText(anAqw.substring(1, anAqw.indexOf('~')));
                            } catch (Exception e) {
                                Log.d("LOG", "Error at recepie text");
                                continue;
                            }
                            try {
                                Pic.setBackground(getResources().getDrawable(getResources().getIdentifier("recipies" + j + "_" + i, "drawable", getPackageName())));
                            } catch (Exception e) {
                                Pic.setVisibility(View.GONE);
                            }
                            btn.setId(i + 2000 + 100 * j);
                            btn.setOnClickListener(oclBtn);

                            item.getLayoutParams().width = FrameLayout.LayoutParams.MATCH_PARENT;
                            linLayout.addView(item);
                        }
                    }
                } catch (Exception ignored) {
                }
            }
        }*/






    }

}

