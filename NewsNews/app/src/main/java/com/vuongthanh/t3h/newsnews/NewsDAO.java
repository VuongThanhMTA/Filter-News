package com.vuongthanh.t3h.newsnews;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class NewsDAO extends SQLiteOpenHelper {
    public NewsDAO(Context context) {
        super(context, NewsField.DB_NAME, null, NewsField.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table " + NewsField.TB_NAME + "(" +
                NewsField._ID + " INTEGER primary key autoincrement," +
                NewsField._TITLE + " text," +
                NewsField._LINK + " text," +
                NewsField._DESCRIPTION + " text," +
                NewsField._DATE + " text," +
                NewsField._IMAGE + " text," +
                NewsField._TYPE + " text" +
                ")";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<News> getData(String type) {
        ArrayList<News> arrNews = new ArrayList<>();
        SQLiteDatabase database = getReadableDatabase();// chi doc
        Cursor cursor = database.query(NewsField.TB_NAME, null, null, null, null, null, null);

        int indexId = cursor.getColumnIndex(NewsField._ID);
        int indexTitle = cursor.getColumnIndex(NewsField._TITLE);
        int indexLink = cursor.getColumnIndex(NewsField._LINK);
        int indexDes = cursor.getColumnIndex(NewsField._DESCRIPTION);
        int indexImg = cursor.getColumnIndex(NewsField._IMAGE);
        int indexDate = cursor.getColumnIndex(NewsField._DATE);
        int indexType = cursor.getColumnIndex(NewsField._TYPE);

        cursor.moveToFirst();// dong dau tien
        while (cursor.isAfterLast() == false) { // kiem tra dong cuoi chua
            String type1 = cursor.getString(indexType);
            if(type1.equals(type)) {

                int id = cursor.getInt(indexId);
                String title = cursor.getString(indexTitle);
                String link = cursor.getString(indexLink);
                String des = cursor.getString(indexDes);
                String date = cursor.getString(indexDate);
                String img = cursor.getString(indexImg);
                News news = new News(id, title, des, img, date, link);
                arrNews.add(news);
            }
            cursor.moveToNext();
        }
        database.close();
        return arrNews;
    }

    public long insertNews(News news,String type) {
        SQLiteDatabase database = getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NewsField._TITLE, news.getTitle());
        values.put(NewsField._LINK, news.getLink());
        values.put(NewsField._DESCRIPTION, news.getDescription());
        values.put(NewsField._IMAGE, news.getImage());
        values.put(NewsField._DATE, news.getPubDate());
        values.put(NewsField._TYPE,type);

        long id = database.insert(NewsField.TB_NAME, null, values);
        // id xac dinh ban ghi
        database.close();
        return id;
    }

    public int deleteNews(News news) {
        SQLiteDatabase database = getWritableDatabase();

        String selction = NewsField._ID + " = ?";
        String[] selctionAgr = {String.valueOf(news.getId())};
        // section se gan voi sectionAgr theo thu tu
        // section = student.getID()
        int rows = database.delete(NewsField.TB_NAME, selction, selctionAgr);

        database.close();

        return rows;// so dong bi xoa
    }

}
