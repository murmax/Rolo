package com.name.rolo;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


public class DescrActivity extends Activity {
    TextView tvDescr;
    TextView tvName;
    ImageView imageView;
    SharedPreferences sPref;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this,WordActivity.class);
                //intent.putExtra("folder",getIntent().getIntExtra("folder",1));
                intent.putExtra("TypeMol",getIntent().getBooleanExtra("TypeMol",true));
                if (getIntent().getStringExtra("string")!=null&&getIntent().getStringExtra("string").equals("NO"))
                    intent.putExtra("id",-1);
                intent.putExtra("position", getIntent().getIntExtra("position",0));
                startActivity(intent);

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.descr_layout);



        //String[] Names;// = getResources().getStringArray(R.array.Names);
        String[] Descrs;// = getResources().getStringArray(R.array.Descrs);
        tvName = (TextView) findViewById(R.id.NameTxtDescr);
        tvDescr = (TextView) findViewById(R.id.DescrTxtDescr);


        if (getIntent().getBooleanExtra("TypeMol",true)) {
            //Names = getResources().getStringArray(R.array.Mols);
            sPref = getSharedPreferences("MolsLikes",MODE_PRIVATE);
            if (sPref.getString("Main","").contains("/"+String.valueOf(getIntent().getIntExtra("id", 0)+1)+"/"))
                findViewById(R.id.BtnMolsLike).setBackgroundColor(Color.GRAY);


            Descrs = getResources().getStringArray(R.array.MolsDescrs);
            try {
                int a = getIntent().getIntExtra("id",1);
                tvName.setText(getIntent().getStringExtra("name"));
                tvDescr.setText(Descrs[a]);
            } catch (Exception e){
                //Log.e("Tag",e.toString());
            }
        }
        else {
            imageView = (ImageView) findViewById(R.id.ivDescrActivity);
            findViewById(R.id.BtnMolsLike).setVisibility(View.GONE);
            try {
                imageView.setImageDrawable(getResources().getDrawable(getResources().getIdentifier("pritch" + getIntent().getIntExtra("id-file", 0), "drawable", this.getPackageName())));
            }
            catch (Exception e){
                Log.e("LOG",e.toString());
            }
            int a = getIntent().getIntExtra("id-file", 0);
            tvName.setText(getResources().getStringArray(R.array.Pritchs)[a]);
            tvName.setTypeface(Typeface.createFromAsset(getAssets(), MainActivity.MAIN_FONT_ADRESS));
            tvDescr.setText(getResources().getStringArray(R.array.PritchDescrs)[a]);
            tvDescr.setTypeface(Typeface.createFromAsset(getAssets(), MainActivity.MOLS_FONT_ADRESS));
            sPref = getSharedPreferences("PritchsData3",MODE_PRIVATE);
            SharedPreferences.Editor ed = sPref.edit();
            if (!sPref.getString("Read","").contains("/"+String.valueOf(a)+"/")&&!sPref.getString("Read","").contains("!"+String.valueOf(a)+"/")) {
                if (!sPref.getString("Read", "").equals(""))
                    ed.putString("Read", sPref.getString("Read", "") + String.valueOf(a) + "/");
                else
                    ed.putString("Read", "!" + String.valueOf(a) + "/");
                ed.commit();
                //Toast.makeText(this, "Text saved", Toast.LENGTH_SHORT).show();
            }

        }

    }

}
