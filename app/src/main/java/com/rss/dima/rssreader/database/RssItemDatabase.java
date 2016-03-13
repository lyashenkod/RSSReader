package com.rss.dima.rssreader.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.rss.dima.rssreader.entity.RssItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dima on 11.03.2016.
 */
public class RssItemDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "rssManager";
    private static final String TABLE_RSS_ITEMS = "RssItems";
    private static final String ID = "id";
    private static final String READER_URL = "rssReaderUrl";
    private static final String TITLE = "title";
    private static final String LINK = "link";
    private static final String DESCRIPTION = "description";
    private static final String FULLTEXT = "fulltext";
    private static final String PUBDATE = "pubDate";
    private static final String EMRTY = null;

    public RssItemDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_RSS_ITEMS_TABLE = "CREATE TABLE " + TABLE_RSS_ITEMS + "("
                + ID + " INTEGER PRIMARY KEY," + READER_URL + " TEXT,"
                + TITLE + " TEXT," + LINK + " TEXT," + DESCRIPTION + " TEXT," + FULLTEXT + " TEXT," + PUBDATE + " TEXT" + ");";
        db.execSQL(CREATE_RSS_ITEMS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RSS_ITEMS);
        onCreate(db);
    }

    public void addRssItem(RssItem rssItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        if (rssItem.getRssReaderUrl() != null) {
            values.put(READER_URL, rssItem.getRssReaderUrl());
        } else
            values.put(READER_URL, EMRTY);
        if (rssItem.getTitle() != null) {
            values.put(TITLE, rssItem.getTitle());
        } else
            values.put(TITLE, EMRTY);

        if (rssItem.getLink() != null) {
            values.put(LINK, rssItem.getLink());
        } else {
            values.put(LINK, EMRTY);
        }
        if (rssItem.getDescription() != null) {
            values.put(DESCRIPTION, rssItem.getDescription());
        } else {
            values.put(DESCRIPTION, EMRTY);
        }
        if (rssItem.getFulltext() != null) {
            values.put(FULLTEXT, rssItem.getFulltext());
        } else {
            values.put(FULLTEXT, EMRTY);
        }
        if (rssItem.getPubDate() != null) {
            values.put(PUBDATE, rssItem.getPubDate());
        } else {
            values.put(PUBDATE, EMRTY);
        }
        db.insert(TABLE_RSS_ITEMS, null, values);
        db.close();
    }

    public List<RssItem> getRssItems(String link) {
        List<RssItem> rssItemList = new ArrayList<RssItem>();
        String selectQuery = "SELECT  * FROM " + TABLE_RSS_ITEMS + " WHERE " + READER_URL + " LIKE " + "\'" + link + "\'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                RssItem rssItem = new RssItem();
                rssItem.setId(Integer.parseInt(cursor.getString(0)));
                rssItem.setRssReaderUrl(cursor.getString(1));
                rssItem.setTitle(cursor.getString(2));
                rssItem.setLink(cursor.getString(3));
                rssItem.setDescription(cursor.getString(4));
                rssItem.setFulltext(cursor.getString(5));
                rssItem.setPubDate(cursor.getString(6));
                rssItemList.add(rssItem);
            } while (cursor.moveToNext());
        }
        return rssItemList;
    }

    public List<RssItem> getRssItems() {
        List<RssItem> rssItemList = new ArrayList<RssItem>();
        String selectQuery = "SELECT  * FROM " + TABLE_RSS_ITEMS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                RssItem rssItem = new RssItem();
                rssItem.setId(Integer.parseInt(cursor.getString(0)));
                rssItem.setRssReaderUrl(cursor.getString(1));
                rssItem.setTitle(cursor.getString(2));
                rssItem.setLink(cursor.getString(3));
                rssItem.setDescription(cursor.getString(4));
                rssItem.setFulltext(cursor.getString(5));
                rssItem.setPubDate(cursor.getString(6));
                rssItemList.add(rssItem);
            } while (cursor.moveToNext());
        }
        return rssItemList;
    }

    public void deleteRssItem(RssItem rssItem) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RSS_ITEMS, ID + " = ?", new String[]{String.valueOf(rssItem.getId())});
        db.close();
    }


    public void deleteRssItem(String link) {
        String selectQuery = "SELECT  * FROM " + TABLE_RSS_ITEMS + " WHERE " + READER_URL + " LIKE " + "\'" + link + "\'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                RssItem rssItem = new RssItem();
                rssItem.setId(Integer.parseInt(cursor.getString(0)));
                rssItem.setRssReaderUrl(cursor.getString(1));
                rssItem.setTitle(cursor.getString(2));
                rssItem.setLink(cursor.getString(3));
                rssItem.setDescription(cursor.getString(4));
                rssItem.setFulltext(cursor.getString(5));
                rssItem.setPubDate(cursor.getString(6));
                deleteRssItem(rssItem);
            } while (cursor.moveToNext());
        }
    }


    public RssItem getRssItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_RSS_ITEMS, new String[]{ID,
                        READER_URL, TITLE, LINK, DESCRIPTION, FULLTEXT, PUBDATE}, ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        RssItem rssItem = new RssItem(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
        return rssItem;
    }

    public RssItem getLastRssItem() {
        String selectQuery = "SELECT  * FROM " + TABLE_RSS_ITEMS + " ORDER BY " + ID + " DESC LIMIT 1";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        RssItem rssItem = new RssItem(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
        return rssItem;
    }

    public RssItem getFirstRssItem(String link) {
        String selectQuery = "SELECT  * FROM " + TABLE_RSS_ITEMS + " WHERE " + READER_URL + " LIKE " + "\'" + link + "\'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        RssItem rssItem = new RssItem(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6));
        return rssItem;

    }

    public void deleteAllItems() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RSS_ITEMS, null, null);
        db.close();
    }
}