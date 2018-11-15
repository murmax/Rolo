package com.name.rolo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import static com.name.rolo.MainActivity.SHARED_NAME;


public class WelcomeClassDonat extends Activity implements View.OnTouchListener {

    ViewFlipper flipper;
    float fromPosition=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen_donat);
        Button btn = (Button) findViewById(R.id.btnWelcome);
        btn.setOnTouchListener(this);
        flipper = (ViewFlipper) findViewById(R.id.flipper);



    }


    @Override
    public boolean onTouch(View view, MotionEvent event) {
        //Log.d("Log","Touch");
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN: // Пользователь нажал на экран, т.е. начало движения
                // fromPosition - координата по оси X начала выполнения операции

                fromPosition = event.getX();
                break;
            case MotionEvent.ACTION_UP: // Пользователь отпустил экран, т.е. окончание движения
                float toPosition = event.getX();
                if (fromPosition > toPosition) {
                    if (flipper.getCurrentView().findViewById(R.id.WelcomeDonat2)!=null){
                        SharedPreferences sPref;

                        sPref = getSharedPreferences(SHARED_NAME,MODE_PRIVATE);
                        SharedPreferences.Editor ed = sPref.edit();
                        ed.putBoolean("firstDonat",true);
                        ed.apply();
                        ed.commit();
                        Intent intent = new Intent(this, DonatClassTrivial.class);
                        startActivity(intent);
                    }
                    else {
                        //Log.d("Log","else");
                        flipper.setInAnimation(AnimationUtils.loadAnimation(this,R.anim.go_prev_in2));
                        flipper.setOutAnimation(AnimationUtils.loadAnimation(this,R.anim.go_prev_out2));
                        flipper.showNext();
                        ImageView iv = (ImageView) findViewById(R.id.slider);
                        iv.setBackgroundDrawable(getResources().getDrawable(R.drawable.slider2_donat));
                        //flipper.showPrevious();

                    }
                }
                else if (fromPosition < toPosition) {
                    if (flipper.getCurrentView().findViewById(R.id.WelcomeDonat1)==null) {
                        //Log.d("Log", "back");
                        flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.go_prev_in));
                        flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.go_prev_out));
                        flipper.showPrevious();
                        ImageView iv = (ImageView) findViewById(R.id.slider);
                        iv.setBackgroundDrawable(getResources().getDrawable(R.drawable.slider1_donat));
                    }
                }
            default:
                break;
        }
        return true;
    }
}
