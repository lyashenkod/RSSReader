package com.rss.dima.rssreader.database;

import android.content.Context;

import com.rss.dima.rssreader.entity.RssUrl;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;

/**
 * Created by Dima on 11.03.2016.
 */
public class RssUrlJSONSerializer {
    private Context context;
    private String filename;

    public RssUrlJSONSerializer(Context eContext, String eFilename) {
        context = eContext;
        filename = eFilename;
    }

    public void saveRssUrl(ArrayList<RssUrl> rssUrls)
            throws JSONException, IOException {
        JSONArray array = new JSONArray();
        for (RssUrl rssUrl : rssUrls)
            array.put(rssUrl.toJSON());

        Writer writer = null;
        try {
            OutputStream out = context
                    .openFileOutput(filename, Context.MODE_PRIVATE);
            writer = new OutputStreamWriter(out);
            writer.write(array.toString());
        } finally {
            if (writer != null)
                writer.close();
        }
    }


    public ArrayList<RssUrl> loadRssUrl() throws IOException, JSONException {
        ArrayList<RssUrl> rssUrls = new ArrayList<RssUrl>();
        BufferedReader reader = null;
        try {
            InputStream in = context.openFileInput(filename);
            reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonString = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }
            JSONArray array = (JSONArray) new JSONTokener(jsonString.toString())
                    .nextValue();
            for (int i = 0; i < array.length(); i++) {
                rssUrls.add(new RssUrl(array.getJSONObject(i)));
            }
        } catch (FileNotFoundException e) {
        } finally {
            if (reader != null)
                reader.close();
        }
        return rssUrls;
    }

}
