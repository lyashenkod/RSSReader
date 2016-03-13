package com.rss.dima.rssreader.ui.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

import com.rss.dima.rssreader.R;
import com.rss.dima.rssreader.entity.RssItem;
import com.rss.dima.rssreader.ui.adapters.RssItemAdapter;
import com.rss.dima.rssreader.ui.listeners.RssItemListListener;
import com.rss.dima.rssreader.ui.listeners.RssUrlItemListListener;
import com.rss.dima.rssreader.util.RssReaderHelper;
import com.rss.dima.rssreader.util.RssUrlReaderHelper;

import java.util.List;
import java.util.UUID;

public class RssItemListActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {
    private String TAG = "RssReader";
    private ListView rssListView;
    private String urlLink;
    private SwipeRefreshLayout refreshLayout;
    private RssItemAdapter itemAdapter;
    private CountDownTimer refreshTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rss_item_list);

        String txtName = getIntent().getStringExtra(RssUrlItemListListener.RssUrlId);
        UUID rssUrlId = UUID.fromString(txtName);

        ActionBar actionBar = getSupportActionBar();
        String actionBarTitle = new RssUrlReaderHelper(RssItemListActivity.this).getRssUrl(rssUrlId).getDescription();
        actionBar.setTitle(actionBarTitle);

        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.list_refresh);
        refreshLayout.setOnRefreshListener(this);

        rssListView = (ListView) findViewById(R.id.rss_list_view);

        urlLink = new RssUrlReaderHelper(RssItemListActivity.this).getRssUrl(rssUrlId).getRssReaderUrl();

        onRefresh();

        onRefreshTimer();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        refreshTimer.cancel();
    }

    @Override
    public void onRefresh() {
        refreshLayout.setRefreshing(true);
        GetRSSDataTask task = new GetRSSDataTask();
        task.execute(urlLink);
        return;
    }

    private void onRefreshTimer() {
        int timerRefreshMs = 180000;
        refreshTimer = new CountDownTimer(timerRefreshMs, timerRefreshMs) {

            @Override
            public void onTick(long millisUntilFinished) {
            }

            @Override
            public void onFinish() {
                onRefresh();
                refreshTimer.start();
            }
        }.start();
    }

    private class GetRSSDataTask extends AsyncTask<String, Void, List<RssItem>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected List<RssItem> doInBackground(String... urls) {
            try {
                RssReaderHelper rssReader = new RssReaderHelper(urls[0]);
                rssReader.addItemsDb(RssItemListActivity.this);
                return rssReader.getItems();
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
                showExceptionMessage(e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(List<RssItem> result) {
            List<RssItem> rssItems = new RssReaderHelper().getItemsDb(RssItemListActivity.this, urlLink);
            itemAdapter = new RssItemAdapter(RssItemListActivity.this, R.layout.list_item, rssItems);
            rssListView.setAdapter(itemAdapter);
            rssListView.setOnItemClickListener(new RssItemListListener(rssItems, RssItemListActivity.this));
            refreshLayout.setRefreshing(false);
        }
    }


    private void showExceptionMessage(final Exception e) {
        String errorMessage = "java.io.IOException: Couldn't open";
        if (e.toString().startsWith(errorMessage)) {
            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    Toast errorToast = Toast.makeText(getApplicationContext(), getFormatResource(R.string.internet_eror),
                            Toast.LENGTH_SHORT);
                    errorToast.show();
                }
            });
        }
    }

    private String getFormatResource(int id) {
        return String.format(getResources().getString(id));
    }
}
