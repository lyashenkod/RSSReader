package com.rss.dima.rssreader.ui.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.rss.dima.rssreader.R;
import com.rss.dima.rssreader.entity.RssUrl;

import java.util.List;

/**
 * Created by Dima on 12.03.2016.
 */
public class RssUrlAdapter extends ArrayAdapter<RssUrl> {
    private Context context;

    public RssUrlAdapter(Context context, int resourceId, List<RssUrl> list) {
        super(context, resourceId, list);
        this.context = context;

    }


    @SuppressLint("DefaultLocale")
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        RssUrl model = (RssUrl) getItem(position);


        if (convertView == null) {

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = mInflater.inflate(R.layout.list_item, null);

            holder = new ViewHolder();
            holder.txtDesc = (TextView) convertView.findViewById(R.id.rss_item_title);
            holder.txtRssUrl = (TextView) convertView.findViewById(R.id.rss_item_data);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txtRssUrl.setText(model.getRssReaderUrl());
        holder.txtDesc.setText(model.getDescription());

        return convertView;
    }

    private class ViewHolder {
        TextView txtRssUrl;
        TextView txtDesc;
    }

}