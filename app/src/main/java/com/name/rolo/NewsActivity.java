package com.name.rolo;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.name.rolo.util.DBHelper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class NewsActivity extends Activity{
    static public ArrayList<String> ARURLs  = new ArrayList<>();
    static public ArrayList<String> ARHrefs  = new ArrayList<>();
    static public ArrayList<String> ARDates = new ArrayList<>();
    static public ArrayList<String> ARTitles = new ArrayList<>();
    static public ArrayList<String> ARDescrs = new ArrayList<>();
    ContentValues values;
    SQLiteDatabase mSqLiteDatabase;
    SQLiteDatabase sdb;
    ProgressBar prbar;

        View.OnClickListener oclBtnRefresh = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARURLs  = new ArrayList<>();
                ARHrefs  = new ArrayList<>();
                ARDates = new ArrayList<>();
                ARTitles = new ArrayList<>();
                ARDescrs = new ArrayList<>();
                startActivity(new Intent(getApplicationContext(),NewsActivity.class));
                finish();
            }
        };


    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);
        if (getActionBar()!=null)  getActionBar().setDisplayHomeAsUpEnabled(true);
        findViewById(R.id.BtnNewsRefresh).setOnClickListener(oclBtnRefresh);

        DBHelper mDatabaseHelper = new DBHelper(this);
        prbar = (ProgressBar) findViewById(R.id.ProgressBar);

        if (ARTitles.isEmpty()) {
            sdb = mDatabaseHelper.getReadableDatabase();
            mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();
            values = new ContentValues();
            ParseALL parseALL = new ParseALL();
            parseALL.execute();
        }
        else {
            LinearLayout linLayout = (LinearLayout) findViewById(R.id.linlayout);
            LayoutInflater ltInflater = getLayoutInflater();
            View item;
            TextView tvNews;
            TextView tvDate;
            WebView webPic;
            Button btn;

            View.OnClickListener oclBtn = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(),NewsDescrActivity.class);
                    intent.putExtra("id",v.getId()-100);
                    startActivity(intent);
                }
            };
            for (int cnt = 0;cnt<ARTitles.size();cnt++){
                item = ltInflater.inflate(R.layout.item_news, linLayout, false);
                tvNews = (TextView) item.findViewById(R.id.textNews);
                tvDate = (TextView) item.findViewById(R.id.TxtDate);
                webPic = (WebView) item.findViewById(R.id.imageViewItemNews);
                btn = (Button) item.findViewById(R.id.NewsElemButton);
                tvNews.setText(ARTitles.get(cnt));
                tvDate.setText(ARDates.get(cnt));
                try {
                    String html = "<html><body><img src=\"" + ARURLs.get(cnt) + "\" width=\"100%\" height=\"100%\"/></body></html>";
                    webPic.loadData(html, "text/html", null); //иначе неправильно масштабируется
                }
                catch (Exception e){
                    Log.e("LOG",e.toString());
                }
                btn.setId(cnt + 100);//по этому значению будем определять, какая кнопка нажата(+100 - чтобы не было конфликтов)
                item.getLayoutParams().width = FrameLayout.LayoutParams.MATCH_PARENT;
                linLayout.addView(item);
                btn.setOnClickListener(oclBtn); //при нажатии любой кнопки перехождим к oclBtn
            }
        }
    }




    public class ParseALL extends AsyncTask<Void,Void,Void>{


        LinearLayout linLayout = (LinearLayout) findViewById(R.id.linlayout);
        LayoutInflater ltInflater = getLayoutInflater();
        View item;
        TextView tvNews;
        TextView tvDate;
        WebView webPic;
        int cnt=0;
        Button btn;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            prbar.setVisibility(View.VISIBLE);
        }


        View.OnClickListener oclBtn = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),NewsDescrActivity.class);
                intent.putExtra("id",v.getId()-100);
                startActivity(intent);
            }
        };
        @Override
        protected Void doInBackground(Void... params) {
            try {
                boolean x=true;

                Document document;
                Elements elementsTitles=null;
                Elements elementsHrefs=null;
                Elements elementsImgs=null;
                Elements elementsTimes=null;
                Elements elementsDates=null;
                String string;
                int end;
                int begin;
                try{
                    document = Jsoup.connect("http://ria.ru/religion/all.html").get();
                    elementsTitles = document.select(".b-list__item-title");
                    elementsImgs = document.select(".b-list__item-img-ind");
                    elementsTimes = document.select(".b-list__item-time");
                    elementsDates = document.select(".b-list__item-date");
                    elementsHrefs = document.select(".b-list__item");
                }
                catch (UnknownHostException e){
                    x=false;
                }

                Element Title;
                Element Href;
                Element IMG;
                Element Time;
                Element Date;
                String result;
                String[] titles;
                Cursor cursor = mSqLiteDatabase.query(DBHelper.DATABASE_TABLE, new String[]{DBHelper.TITLE_COLUMN},
                        null, null,
                        null, null, null);
                int length = cursor.getCount();
                titles=new String[length];
                if (cursor.moveToFirst()){
                    for (int i=0;i<length;i++)
                    {
                        titles[i] = cursor.getString(cursor.getColumnIndex(DBHelper.TITLE_COLUMN));
                        cursor.moveToNext();
                    }
                }
                cursor.close();

                for (int i=0;i<titles.length;i++)
                    Log.d("LOG","FIRST: "+titles[i]);
                    for (int i = 0; i< (elementsTitles != null ? elementsTitles.size() : 0); i++) {
                        //Log.d("LOG",elementsTitles.get(i).toString());
                        //Log.d("LOG",String.valueOf(i));
                        if (!x) break;
                        Title = elementsTitles.get(i);
                        Href = elementsHrefs.get(i);
                        IMG = elementsImgs.get(i);
                        Time = elementsTimes.get(i);
                        Date = elementsDates.get(i);
                        for (String title : titles)
                            if (title.equals(Title.text()))
                                x = false;
                        if (x) {
                           // Log.d("LOG","I= "+String.valueOf(i));

                            ARTitles.add(Title.text());
                            values.put(DBHelper.TITLE_COLUMN,Title.text());

                            ARHrefs.add(Href.select("a[href]").first().attr("abs:href"));
                            values.put(DBHelper.HREFS_COLUMN,Href.select("a[href]").first().attr("abs:href"));

                            String a = IMG.toString();
                            begin = 45;
                            for (end = 46; a.charAt(end) != '\"'; end++) ;
                            a = a.substring(begin, end + 1);
                            ARURLs.add(a);
                            values.put(DBHelper.URL_COLUMN,a);

                            string = Time.text() + " " + Date.text();
                            if (!string.equals("")) {
                                ARDates.add(string);
                                values.put(DBHelper.DATES_COLUMN,string);
                            }

                            result = " ";
                            try {
                                document = Jsoup.connect(ARHrefs.get(i)).get();
                                Element element = document.select(".b-article__main").first();
                                Elements elements;
                                if (element != null) {
                                    elements = element.select("p");
                                    for (Element element1 : elements) {
                                        if (element1.text() != null && !element1.text().equals(""))
                                            result = result + element1.text() + "\n\n   ";
                                    }
                                }
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            ARDescrs.add(result);
                            values.put(DBHelper.TEXT_COLUMN,result);
                            if (values.size()>0) {
                                Log.d("LOG","Putting: "+ values.get(DBHelper.TITLE_COLUMN).toString());
                                mSqLiteDatabase.insert(DBHelper.DATABASE_TABLE, null, values);
                            }
                            publishProgress();
                        }
                    }

                cursor = mSqLiteDatabase.query(DBHelper.DATABASE_TABLE, new String[]{DBHelper.TITLE_COLUMN,DBHelper.HREFS_COLUMN,DBHelper.DATES_COLUMN,DBHelper.TEXT_COLUMN,DBHelper.URL_COLUMN},
                        null, null,
                        null, null, null);
                final int[] indexes = new int[5];
                indexes[0]=cursor.getColumnIndex(DBHelper.TITLE_COLUMN);
                indexes[1]=cursor.getColumnIndex(DBHelper.HREFS_COLUMN);
                indexes[2]=cursor.getColumnIndex(DBHelper.TEXT_COLUMN);
                indexes[3]=cursor.getColumnIndex(DBHelper.DATES_COLUMN);
                indexes[4]=cursor.getColumnIndex(DBHelper.URL_COLUMN);
                cursor.moveToFirst();
                Log.d("LOG","Columns: "+ String.valueOf(cursor.getColumnCount()));
                Log.d("LOG","Count: "+ String.valueOf(cursor.getCount()));

                for (int i = 0; (i<length)&&(i<30); i++)
                {
                    try {
                        // Log.d("LOG",cursor.getString(indexes[0]));
                        ARTitles.add(cursor.getString(indexes[0]));
                        ARHrefs.add(cursor.getString(indexes[1]));
                        ARDescrs.add(cursor.getString(indexes[2]));
                        ARDates.add(cursor.getString(indexes[3]));
                        ARURLs.add(cursor.getString(indexes[4]));
                        cursor.moveToNext();
                        publishProgress();
                    }
                    catch (CursorIndexOutOfBoundsException e){
                        break;
                    }
                }
                cursor.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            sdb.close();
            mSqLiteDatabase.close();
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... Vagues) {
            super.onProgressUpdate(Vagues);
            try {
                item = ltInflater.inflate(R.layout.item_news, linLayout, false);
                tvNews = (TextView) item.findViewById(R.id.textNews);
                tvDate = (TextView) item.findViewById(R.id.TxtDate);
                webPic = (WebView) item.findViewById(R.id.imageViewItemNews);
                btn = (Button) item.findViewById(R.id.NewsElemButton);
                tvNews.setText(ARTitles.get(cnt));
                tvDate.setText(ARDates.get(cnt));
                try {
                    String html = "<html><body><img src=\"" + ARURLs.get(cnt) + "\" width=\"100%\" height=\"100%\"/></body></html>";
                    webPic.loadData(html, "text/html", null); //иначе неправильно масштабируется
                }
                catch (Exception e){
                    Log.d("ERR","e");
                }
                btn.setId(cnt + 100);//по этому значению будем определять, какая кнопка нажата(+100 - чтобы не было конфликтов)
                item.getLayoutParams().width = FrameLayout.LayoutParams.MATCH_PARENT;
                linLayout.addView(item);
                btn.setOnClickListener(oclBtn); //при нажатии любой кнопки перехождим к oclBtn
            }
            catch (Exception e){
                Log.e("ERR",e.toString());
            }
            cnt++;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            prbar.setVisibility(View.GONE);
        }
    }

}
