package com.rss.dima.rssreader.entity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by Dima on 11.03.2016.
 */
public class RssUrl {
    private static final String JSON_ID = "id";
    private static final String JSON_RSSURL = "rssReaderUrl";
    private static final String JSON_DESCRIPTION = "description";

    private UUID Id;
    private String rssReaderUrl;
    private String description;

    public RssUrl() {
        Id = UUID.randomUUID();
    }

    public UUID getId() {
        return Id;
    }

    public void setId(UUID Id) {
        this.Id = Id;
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

    public JSONObject toJSON() throws JSONException {
        JSONObject json = new JSONObject();
        json.put(JSON_ID, Id.toString());
        json.put(JSON_RSSURL, rssReaderUrl.toString());
        json.put(JSON_DESCRIPTION, description.toString());
        return json;
    }

    public RssUrl(JSONObject json) throws JSONException {
        Id = UUID.fromString(json.getString(JSON_ID));
        rssReaderUrl = json.getString(JSON_RSSURL);
        description = json.getString(JSON_DESCRIPTION);
    }

    @Override
    public String toString() {
        return "RssUrl{" +
                "Id=" + Id +
                ", rssReaderUrl='" + rssReaderUrl + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
