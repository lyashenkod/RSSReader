package com.rss.dima.rssreader.ui.activity;

import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.rss.dima.rssreader.R;
import com.rss.dima.rssreader.ui.adapters.RssUrlAdapter;
import com.rss.dima.rssreader.ui.listeners.RssUrlItemListListener;
import com.rss.dima.rssreader.util.RssUrlReaderHelper;
import com.rss.dima.rssreader.entity.RssUrl;

import java.util.ArrayList;

public class UrlListActivity extends ActionBarActivity {

    private ArrayList<RssUrl> rssUrls;
    private ListView listView;
    private RssUrlAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_url);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.rss_linc);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.url_list_view);
        registerForContextMenu(listView);
        listView.setEmptyView(findViewById(R.id.empty_viev));

        rssUrls = RssUrlReaderHelper.get(UrlListActivity.this).getRssUrlsList();
        adapter = new RssUrlAdapter(UrlListActivity.this, R.layout.list_item, rssUrls);
        listView.setOnItemClickListener(new RssUrlItemListListener(rssUrls, UrlListActivity.this));
        listView.setAdapter(adapter);

        //    refreshList();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddUrlDialog();
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        UrlListActivity.this.getMenuInflater().inflate(R.menu.del_list_item, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int position = info.position;
        RssUrl rssUrl = adapter.getItem(position);

        switch (item.getItemId()) {
            case R.id.menu_item_delete:
                RssUrlReaderHelper.get(UrlListActivity.this).deleteRssUrl(rssUrl);
                adapter.notifyDataSetChanged();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    private void showAddUrlDialog() {
        final Dialog rssDial = new Dialog(UrlListActivity.this, R.style.Base_Theme_AppCompat_Dialog);
        rssDial.setContentView(R.layout.dialog_add_rss_url);
        final TextView rssUrlTitle = (TextView) rssDial.findViewById(R.id.rss_url);
        final TextView rssDesc = (TextView) rssDial.findViewById(R.id.rss_desc);
        Button rssAdd = (Button) rssDial.findViewById(R.id.but_add);
        rssAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RssUrl rssUrl = new RssUrl();
                rssUrl.setRssReaderUrl(rssUrlTitle.getText().toString());
                rssUrl.setDescription(rssDesc.getText().toString());
                if (!rssUrl.getDescription().isEmpty() && !rssUrl.getRssReaderUrl().isEmpty()) {
                    RssUrlReaderHelper.get(UrlListActivity.this).addRssUrl(rssUrl);
                    RssUrlReaderHelper.get(UrlListActivity.this).saveRssUrl();
                    rssDial.dismiss();
                    refreshList();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), getFormatResource(R.string.add_fields), Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });
        rssDial.show();
    }


    private void refreshList() {
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);
    }

    private String getFormatResource(int id) {
        return String.format(getResources().getString(id));
    }
}
