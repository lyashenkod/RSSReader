package com.rss.dima.rssreader.entity;

/**
 * Created by Dima on 11.03.2016.
 */
public class RssItem {

    private int id;
    private String rssReaderUrl;
    private String title;
    private String link;
    private String description;
    private String fulltext;
    private String pubDate;

    public RssItem() {
    }

    public RssItem(int id, String rssReaderUrl, String title, String link, String description, String fulltext, String pubDate) {
        this.id = id;
        this.rssReaderUrl = rssReaderUrl;
        this.title = title;
        this.link = link;
        this.description = description;
        this.fulltext = fulltext;
        this.pubDate = pubDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRssReaderUrl() {
        return rssReaderUrl;
    }

    public void setRssReaderUrl(String rssReaderUrl) {
        this.rssReaderUrl = rssReaderUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFulltext() {
        return fulltext;
    }

    public void setFulltext(String fulltext) {
        this.fulltext = fulltext;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "RssItem{" +
                "id=" + id +
                ", rssReaderUrl='" + rssReaderUrl + '\'' +
                ", title='" + title + '\'' +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", fulltext='" + fulltext + '\'' +
                ", pubDate='" + pubDate + '\'' +
                '}';
    }
}
