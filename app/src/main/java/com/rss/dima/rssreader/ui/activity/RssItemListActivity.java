package com.rss.dima.rssreader.ui.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.rss.dima.rssreader.R;
import com.rss.dima.rssreader.entity.RssItem;
import com.rss.dima.rssreader.ui.adapters.RssItemAdapter;
import com.rss.dima.rssreader.ui.listeners.RssItemListListener;
import com.rss.dima.rssreader.ui.listeners.RssUrlItemListListener;
import com.rss.dima.rssreader.util.RssReaderHelper;
import com.rss.dima.rssreader.util.RssUrlReaderHelper;

import java.util.List;
import java.util.UUID;

public class RssItemListActivity extends AppCompatActivity {
    private String TAG = "RssReader";
    private ListView rssListView;
    private ProgressDialog progressDialog;
    private String urlLink;
    RssItemAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String txtName = getIntent().getStringExtra(RssUrlItemListListener.RssUrlId);
        UUID rssUrlId = UUID.fromString(txtName);

        ActionBar actionBar = getSupportActionBar();
        String title = new RssUrlReaderHelper(RssItemListActivity.this).getRssUrlToId(rssUrlId).getDescription();
        actionBar.setTitle(title);


        rssListView = (ListView) findViewById(R.id.listMainView);

        urlLink = new RssUrlReaderHelper(RssItemListActivity.this).getRssUrlToId(rssUrlId).getRssReaderUrl();

        GetRSSDataTask task = new GetRSSDataTask();
        task.execute(urlLink);




    }

    private class GetRSSDataTask extends AsyncTask<String, Void, List<RssItem>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(RssItemListActivity.this);
            progressDialog.setTitle(getFormatResource(R.string.loading_news));
            progressDialog.setMessage(getFormatResource(R.string.loading));
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }

        @Override
        protected List<RssItem> doInBackground(String... urls) {
            try {
                RssReaderHelper rssReader = new RssReaderHelper(urls[0]);
                rssReader.addLinkItemsDb(RssItemListActivity.this);
                return rssReader.getItems();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<RssItem> result) {
            List<RssItem> rssItems = new RssReaderHelper().getItemsUtlDb(RssItemListActivity.this, urlLink);
            adapter = new RssItemAdapter(RssItemListActivity.this, R.layout.list_item, rssItems);
            rssListView.setAdapter(adapter);
            rssListView.setOnItemClickListener(new RssItemListListener(rssItems, RssItemListActivity.this));
            progressDialog.dismiss();
        }
    }



    private String getFormatResource(int id) {
        return String.format(getResources().getString(id));
    }


}
