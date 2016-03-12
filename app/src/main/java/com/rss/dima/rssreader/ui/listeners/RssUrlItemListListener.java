package com.rss.dima.rssreader.ui.listeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.rss.dima.rssreader.entity.RssUrl;
import com.rss.dima.rssreader.ui.activity.RssItemListActivity;

import java.util.List;

/**
 * Created by Dima on 12.03.2016.
 */
public class RssUrlItemListListener implements AdapterView.OnItemClickListener {
    public static String RssUrlId = "RssUrlId";
    private List<RssUrl> listItems;
    private Activity activity;

    public RssUrlItemListListener(List<RssUrl> aListItems, Activity anActivity) {
        listItems = aListItems;
        activity = anActivity;
    }

    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
        Intent intent = new Intent(activity, RssItemListActivity.class);
        intent.putExtra(RssUrlId, listItems.get(pos).getId().toString());
        activity.startActivity(intent);
    }
}