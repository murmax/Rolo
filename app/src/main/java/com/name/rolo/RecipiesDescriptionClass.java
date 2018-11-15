package com.name.rolo;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class RecipiesDescriptionClass extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recipies_descr_layout);

        int RecipePost = getIntent().getIntExtra("RP", -1);
        int ID = getIntent().getIntExtra("ID", -1);
        Log.d("LOG", "RP at Descr: "+String.valueOf(RecipePost));
        Log.d("LOG", "ID: "+String.valueOf(ID));

        ImageView imageView = (ImageView) findViewById(R.id.ImagePost);
        TextView name = (TextView) findViewById(R.id.NamePost);
        TextView descr = (TextView) findViewById(R.id.DescrPost);


        //imageView.setBackground(getResources().getDrawable(getResources().getIdentifier("recipie" + ID +"_"+RecipePost, "drawable", getPackageName())));
        final String aqw = getResources().getStringArray(getResources().getIdentifier("Recipies"+RecipePost, "array", getPackageName()))[ID];

        try {
            name.setText(aqw.substring(1, aqw.indexOf('~')));
        } catch (Exception e) {
            Log.d("LOG", "Error at recepie text1");
            name.setVisibility(View.GONE);
        }
        try {
            imageView.setBackground(getResources().getDrawable(getResources().getIdentifier("recipies" + RecipePost +"_"+ ID, "drawable", getPackageName())));
        } catch (Exception e) {
            imageView.setVisibility(View.GONE);
        }
        try {
            descr.setText("\nЭтот рецепт относится к посту:\n "+getResources().getStringArray(R.array.PostsNames)[RecipePost]+"\n\n"+aqw.substring(aqw.indexOf('~')+1));
            //descr.setText(Html.fromHtml(getString(R.string.coloredText)));
        } catch (Exception e) {
            Log.d("LOG", "Error at recepie text2");
            descr.setText(aqw);
        }



    }
}
