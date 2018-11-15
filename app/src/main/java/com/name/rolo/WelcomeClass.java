package com.name.rolo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ViewFlipper;


public class WelcomeClass extends Activity implements View.OnTouchListener {

    ViewFlipper flipper;
    float fromPosition=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_screen);
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
                if (fromPosition-50 > toPosition) {
                    if (flipper.getCurrentView().findViewById(R.id.Lego3)!=null){
                        Log.d("Log","true");
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.putExtra("Firstend",false);
                        startActivity(intent);
                    }
                    else {
                        //Log.d("Log","else");
                        flipper.setInAnimation(AnimationUtils.loadAnimation(this,R.anim.go_prev_in2));
                        flipper.setOutAnimation(AnimationUtils.loadAnimation(this,R.anim.go_prev_out2));
                        flipper.showNext();
                        if (flipper.getCurrentView().findViewById(R.id.Lego3)!=null) {
                            ImageView iv = (ImageView) findViewById(R.id.slider);
                            iv.setBackgroundDrawable(getResources().getDrawable(R.drawable.slider3));
                        }else {
                            ImageView iv = (ImageView) findViewById(R.id.slider);
                            iv.setBackgroundDrawable(getResources().getDrawable(R.drawable.slider2));


                        }
                        //flipper.showPrevious();

                    }
                }
                else if (fromPosition+50 < toPosition) {
                    if (flipper.getCurrentView().findViewById(R.id.Lego1)==null) {
                        //Log.d("Log", "back");
                        flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.go_prev_in));
                        flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.go_prev_out));
                        flipper.showPrevious();
                        if (flipper.getCurrentView().findViewById(R.id.Lego1)!=null) {
                            ImageView iv = (ImageView) findViewById(R.id.slider);
                            iv.setBackgroundDrawable(getResources().getDrawable(R.drawable.slider1));
                        }else {
                            ImageView iv = (ImageView) findViewById(R.id.slider);
                            iv.setBackgroundDrawable(getResources().getDrawable(R.drawable.slider2));


                        }
                    }
                }
            default:
                break;
        }
        return true;
    }
}
