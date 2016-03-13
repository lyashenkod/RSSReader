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

    private static RssUrlReaderHelper rssUrlHelper;
    private Context context;
    private ArrayList<RssUrl> rssUrls;
    private RssUrlJSONSerializer mSerializer;


    public RssUrlReaderHelper(Context eContext) {
        context = eContext;
        mSerializer = new RssUrlJSONSerializer(context, FILENAME);
        rssUrls = new ArrayList<RssUrl>();
        try {
            rssUrls = mSerializer.loadRssUrl();
        } catch (Exception e) {
            rssUrls = new ArrayList<RssUrl>();
            Log.e(TAG, "Error loading rssUrl: ", e);
        }

    }

    public static RssUrlReaderHelper get(Context c) {
        if (rssUrlHelper == null) {
            rssUrlHelper = new RssUrlReaderHelper(c.getApplicationContext());
        }
        return rssUrlHelper;
    }


    public ArrayList<RssUrl> getRssUrls() {
        return rssUrls;
    }

    public RssUrl getRssUrl(UUID id) {
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
