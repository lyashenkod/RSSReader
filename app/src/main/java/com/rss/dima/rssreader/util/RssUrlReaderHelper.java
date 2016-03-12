package com.rss.dima.rssreader.util;

import android.content.Context;
import android.util.Log;

import com.rss.dima.rssreader.database.RssUrlJSONSerializer;
import com.rss.dima.rssreader.entity.RssUrl;

import java.util.ArrayList;
import java.util.UUID;

/**
 * Created by Dima on 11.03.2016.
 */
public class RssUrlReaderHelper {
    private static final String TAG = "RssUrlLab";
    private static final String FILENAME = "rssUrl.json";

    private static RssUrlReaderHelper rssUrlLab;
    private Context mAppContext;
    private ArrayList<RssUrl> rssUrls;
    private RssUrlJSONSerializer mSerializer;


    public RssUrlReaderHelper(Context appContext) {
        mAppContext = appContext;
        mSerializer = new RssUrlJSONSerializer(mAppContext, FILENAME);
        rssUrls = new ArrayList<RssUrl>();
        try {
            rssUrls = mSerializer.loadRssUrl();
        } catch (Exception e) {
            rssUrls = new ArrayList<RssUrl>();
            Log.e(TAG, "Error loading rssUrl: ", e);
        }

    }

    public static RssUrlReaderHelper get(Context c) {
        if (rssUrlLab == null) {
            rssUrlLab = new RssUrlReaderHelper(c.getApplicationContext());
        }
        return rssUrlLab;
    }


    public ArrayList<RssUrl> getRssUrlsList() {
        return rssUrls;
    }

    public RssUrl getRssUrlToId(UUID id) {
        for (RssUrl url : rssUrls) {
            if (url.getId().equals(id))
                return url;
        }
        return null;
    }

    public boolean saveRssUrl() {
        try {
            mSerializer.saveRssUrl(rssUrls);
            Log.d(TAG, "rssUrls saved to file");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "Error saving rssUrls: ", e);
            return false;
        }
    }

    public void deleteRssUrl(RssUrl url) {
        rssUrls.remove(url);
        saveRssUrl();
    }

    public void addRssUrl(RssUrl url) {
        rssUrls.add(url);
    }

}
