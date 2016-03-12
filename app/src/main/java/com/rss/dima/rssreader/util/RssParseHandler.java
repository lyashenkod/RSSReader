package com.rss.dima.rssreader.util;

import com.rss.dima.rssreader.entity.RssItem;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dima on 11.03.2016.
 */
public class RssParseHandler extends DefaultHandler {

    private final String PUB_DATE = "pubDate";
    private final String DESCRIPTION = "description";
    private final String LINK = "link";
    private final String TITLE = "title";
    private final String ITEM = "item";
    private final String FULLTEXT = "fulltext";

    private List<RssItem> rssItemList;
    private RssItem currentItem;
    private boolean parsingTitle;
    private boolean parsingLink;
    private boolean parsingDescription;
    private boolean parsingFulltext;
    private boolean parsingPubDate;


    public RssParseHandler() {
        rssItemList = new ArrayList<RssItem>();
    }

    public ArrayList<RssItem> getItems() {
        return (ArrayList<RssItem>) rssItemList;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (ITEM.equals(qName)) {
            currentItem = new RssItem();
        } else if (TITLE.equals(qName)) {
            parsingTitle = true;
        } else if (LINK.equals(qName)) {
            parsingLink = true;
        } else if (DESCRIPTION.equals(qName)) {
            parsingDescription = true;
        } else if (FULLTEXT.equals(qName)) {
            parsingFulltext = true;
        } else if (PUB_DATE.equals(qName)) {
            parsingPubDate = true;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        if (ITEM.equals(qName)) {
            rssItemList.add(currentItem);
            currentItem = null;
        } else if (TITLE.equals(qName)) {
            parsingTitle = false;
        } else if (LINK.equals(qName)) {
            parsingLink = false;
        } else if (DESCRIPTION.equals(qName)) {
            parsingDescription = false;
        } else if (FULLTEXT.equals(qName)) {
            parsingFulltext = false;
        } else if (PUB_DATE.equals(qName)) {
            parsingPubDate = false;
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (parsingTitle) {
            if (currentItem != null)
                currentItem.setTitle(new String(ch, start, length));
        } else if (parsingLink) {
            if (currentItem != null) {
                currentItem.setLink(new String(ch, start, length));
                parsingLink = false;
            }
        } else if (parsingDescription) {
            if (currentItem != null) {
                currentItem.setDescription(new String(ch, start, length));
                parsingDescription = false;
            }
        } else if (parsingFulltext) {
            if (currentItem != null) {
                currentItem.setFulltext(new String(ch, start, length));
                parsingFulltext = false;
            }
        } else if (parsingPubDate) {
            if (currentItem != null) {
                currentItem.setPubDate(new String(ch, start, length));
                parsingPubDate = false;
            }
        }
    }

}
