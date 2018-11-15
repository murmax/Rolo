package com.name.rolo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;


public class WordChoiceClass extends Activity implements View.OnTouchListener{
    private Button mols;
    private Button pritch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.word_choice_layout);
        mols = (Button) findViewById(R.id.BtnMol);
        pritch = (Button) findViewById(R.id.BtnPritch);
        mols.setBackground(getResources().getDrawable(R.drawable.choicewindow_mols));
        pritch.setBackground(getResources().getDrawable(R.drawable.choicewindow_pritchs));
        View.OnClickListener oclBtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.d("Debug","Кнопка "+ v.getId()+" Была нажата");
                Intent intent = new Intent(getApplicationContext(),WordActivity.class);
                switch (v.getId()){
                    case R.id.BtnMol:
                        intent.putExtra("TypeMol",true); //определяем содержимое WordActivity
                        startActivity(intent);
                        break;
                    case  R.id.BtnPritch:
                        intent.putExtra("TypeMol",false); //определяем содержимое WordActivity
                        startActivity(intent);
                        break;
                }
            }
        };
        mols.setOnClickListener(oclBtn);
        pritch.setOnClickListener(oclBtn);
        mols.setOnTouchListener(this);
        pritch.setOnTouchListener(this);


    }

    @Override
    protected void onResume() {
        mols.setBackground(getResources().getDrawable(R.drawable.choicewindow_mols));
        pritch.setBackground(getResources().getDrawable(R.drawable.choicewindow_pritchs));
        super.onResume();
    }

    @Override
    public boolean onTouch(View v, MotionEvent motionEvent) {
        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
            switch (v.getId()) {
                case R.id.BtnMol:
                    mols.setBackground(getResources().getDrawable(R.drawable.choicewindow_mols_pressed));
                    break;
                case R.id.BtnPritch:
                    pritch.setBackground(getResources().getDrawable(R.drawable.choicewindow_pritchs_pressed));
                    break;
            }
        }
        if  (motionEvent.getAction() == MotionEvent.ACTION_UP){
            switch (v.getId()) {
                case R.id.BtnMol:
                    mols.setBackground(getResources().getDrawable(R.drawable.choicewindow_mols));
                    break;
                case R.id.BtnPritch:
                    pritch.setBackground(getResources().getDrawable(R.drawable.choicewindow_pritchs));
                    break;
            }
        }

        return false;
    }
}
