package com.rss.dima.rssreader.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;

import com.rss.dima.rssreader.R;
import com.rss.dima.rssreader.entity.RssItem;
import com.rss.dima.rssreader.entity.RssUrl;
import com.rss.dima.rssreader.ui.listeners.RssItemListListener;
import com.rss.dima.rssreader.util.RssReaderHelper;

public class DescriptionRssItemActivity extends AppCompatActivity {

    private int defaultValue;
    private int itemId;
    private RssItem rssItem;

    private WebView textTitle;
    private WebView textDesc;
    private WebView textFulltext;
    private Button toLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description_rss_item);

        defaultValue = 0;
        itemId = getIntent().getIntExtra(RssItemListListener.RssId, defaultValue);
        rssItem = new RssReaderHelper().getItemToIdDb(DescriptionRssItemActivity.this, itemId);

        textTitle = (WebView) findViewById(R.id.desc_text_title);
        textDesc = (WebView) findViewById(R.id.desc_text_description);
        textFulltext = (WebView) findViewById(R.id.desc_text_fulltext);
        toLink = (Button) findViewById(R.id.desc_but_link);


        textTitle.loadDataWithBaseURL(null, rssItem.getTitle(), "text/html", "utf-8", null);
        textDesc.loadDataWithBaseURL(null, rssItem.getDescription(), "text/html", "utf-8", null);
        textFulltext.loadDataWithBaseURL(null, rssItem.getFulltext(), "text/html", "utf-8", null);

        toLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent linkIntent = new Intent(Intent.ACTION_VIEW);
                linkIntent.setData(Uri.parse(rssItem.getLink()));
                startActivity(linkIntent);
            }
        });

    }
}
