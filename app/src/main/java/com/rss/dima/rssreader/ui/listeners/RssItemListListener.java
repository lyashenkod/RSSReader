package com.rss.dima.rssreader.ui.listeners;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;

import com.rss.dima.rssreader.entity.RssItem;
import com.rss.dima.rssreader.ui.activity.DescriptionRssItemActivity;

import java.util.List;

/**
 * Created by Dima on 11.03.2016.
 */
public class RssItemListListener implements AdapterView.OnItemClickListener {
    public static String RssId = "RssId";
    private List<RssItem> listItems;
    private Activity activity;

    public RssItemListListener(List<RssItem> aListItems, Activity anActivity) {
        listItems = aListItems;
        activity = anActivity;
    }

    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
        Intent intent = new Intent(activity, DescriptionRssItemActivity.class);
        intent.putExtra(RssId, listItems.get(pos).getId());
        activity.startActivity(intent);
    }

}
