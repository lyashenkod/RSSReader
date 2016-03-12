package com.rss.dima.rssreader.ui.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rss.dima.rssreader.R;
import com.rss.dima.rssreader.entity.RssItem;

import java.util.List;

/**
 * Created by Dima on 11.03.2016.
 */
public class RssItemAdapter extends ArrayAdapter<RssItem> {
    private Context context;

    public RssItemAdapter(Context context, int resourceId, List<RssItem> list) {
        super(context, resourceId, list);
        this.context = context;

    }

    @SuppressLint("DefaultLocale")
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        RssItem model = (RssItem) getItem(position);


        if (convertView == null) {

            LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

            convertView = mInflater.inflate(R.layout.list_item, null);

            holder = new ViewHolder();
            holder.txtTitle = (TextView) convertView.findViewById(R.id.rss_item_title);
            holder.txtData = (TextView) convertView.findViewById(R.id.rss_item_data);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.txtTitle.setText(model.getTitle());
        holder.txtData.setText(model.getPubDate());

        return convertView;
    }

    private class ViewHolder {
        TextView txtTitle;
        TextView txtData;
    }

}
