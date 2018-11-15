package com.name.rolo;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

public class NewsDescrActivity extends Activity {

    TextView tvDescr;
    TextView tvName;
    TextView tvDate;
    //void Back(View v){
    //    Intent intent = new Intent(this,NewsActivity.class);
    //    startActivity(intent);
    //}
    //void DrawableView(View v){
        //Intent intent2 = new Intent(this,DrawableClass.class);
        //intent2.putExtra("id",getIntent().getIntExtra("id",0));
        //startActivity(intent2);
    //}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_descr_layout);
        tvName = (TextView) findViewById(R.id.NameTxtDescr);
        tvName.setTypeface(Typeface.createFromAsset(getAssets(), MainActivity.MAIN_FONT_ADRESS));
        tvDescr = (TextView) findViewById(R.id.DescrTxtDescr);
        tvDescr.setTypeface(Typeface.createFromAsset(getAssets(), MainActivity.MOLS_FONT_ADRESS));
        tvDate = (TextView) findViewById(R.id.TxtDateatDescr);
        WebView ivImage = (WebView) findViewById(R.id.ImageDescrNews);
        int id = getIntent().getIntExtra("id", 0);


        tvName.setText(NewsActivity.ARTitles.get(id));
        tvDescr.setText(NewsActivity.ARDescrs.get(id));
        tvDate.setText(NewsActivity.ARDates.get(id));
        String html = "<html><body><img src=\"" + NewsActivity.ARURLs.get(id) + "\" width=\"100%\" height=\"100%\"/></body></html>";
        ivImage.loadData(html, "text/html", null); //иначе неправильно масштабируется
    }
}
