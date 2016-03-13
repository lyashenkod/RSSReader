package com.rss.dima.rssreader.util;

import android.content.Context;

import com.rss.dima.rssreader.database.RssItemDatabase;
import com.rss.dima.rssreader.entity.RssItem;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Dima on 11.03.2016.
 */
public class RssReaderHelper {


    private String rssUrl;

    public RssReaderHelper() {
    }

    public RssReaderHelper(String rssUrl) {
        this.rssUrl = rssUrl;
    }

    public ArrayList<RssItem> getItems() throws Exception {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        RssParseHandler handler = new RssParseHandler();
        saxParser.parse(rssUrl, handler);
        return handler.getItems();

    }


    public void addItemsDb(Context context) throws Exception {
        List<RssItem> list = getItems();
        RssItemDatabase db = new RssItemDatabase(context);
        if (getItems() != null) {
            db.deleteRssItem(rssUrl);
            for (int i = 0; i < list.size(); i++) {
                list.get(i).setRssReaderUrl(rssUrl);
                db.addRssItem(list.get(i));
            }

        }
    }

    public List<RssItem> getItemsDb(Context context, String urllLink) {
        RssItemDatabase db = new RssItemDatabase(context);
        return db.getRssItems(urllLink);

    }

    public RssItem getItemDb(Context context, Integer id) {
        RssItemDatabase db = new RssItemDatabase(context);
        return db.getRssItem(id);

    }

    public List<RssItem> getItemsDb(Context context) {
        RssItemDatabase db = new RssItemDatabase(context);
        return db.getRssItems();

    }

    private Date dateFormater(String stringDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.ENGLISH);
        try {
            return formatter.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

}
