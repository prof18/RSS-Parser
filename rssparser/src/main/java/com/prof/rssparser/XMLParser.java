/*
*   Copyright 2016 Marco Gomiero
*
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
*
*/

package com.prof.rssparser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.Observable;

/**
 * Created by Marco Gomiero on 12/02/2015.
 */

public class XMLParser extends Observable {

    private ArrayList<Article> articles;
    private Article currentArticle;

    public XMLParser() {
        articles = new ArrayList<>();
        currentArticle = new Article();
    }

    public void parseXML(String xml) throws XmlPullParserException, IOException {

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();

        factory.setNamespaceAware(false);
        XmlPullParser xmlPullParser = factory.newPullParser();

        xmlPullParser.setInput(new StringReader(xml));
        boolean insideItem = false;
        int eventType = xmlPullParser.getEventType();

        while (eventType != XmlPullParser.END_DOCUMENT) {

            if (eventType == XmlPullParser.START_TAG) {

                if (xmlPullParser.getName().equalsIgnoreCase("item")) {

                    insideItem = true;

                } else if (xmlPullParser.getName().equalsIgnoreCase("title")) {

                    if (insideItem) {
                        String title = xmlPullParser.nextText();
                        currentArticle.setTitle(title);
                    }

                } else if (xmlPullParser.getName().equalsIgnoreCase("link")) {

                    if (insideItem) {
                        String link = xmlPullParser.nextText();
                        currentArticle.setLink(link);
                    }

                } else if (xmlPullParser.getName().equalsIgnoreCase("dc:creator")) {

                    if (insideItem) {
                        String author = xmlPullParser.nextText();
                        currentArticle.setAuthor(author);
                    }

                } else if (xmlPullParser.getName().equalsIgnoreCase("category")) {

                    if (insideItem) {
                        String category = xmlPullParser.nextText();
                        currentArticle.addCategory(category);
                    }

                } else if (xmlPullParser.getName().equalsIgnoreCase("content:encoded")) {

                    if (insideItem) {
                        String htmlData = xmlPullParser.nextText();
                        Document doc = Jsoup.parse(htmlData);
                        try {
                            //choose the first image found in the article
                            String pic = doc.select("img").first().attr("abs:src");
                            currentArticle.setImage(pic);
                        } catch (NullPointerException e) {
                            currentArticle.setImage(null);
                        }
                        currentArticle.setContent(htmlData);
                    }

                } else if (xmlPullParser.getName().equalsIgnoreCase("description")) {

                    if (insideItem) {
                        String description = xmlPullParser.nextText();
                        currentArticle.setDescription(description);
                    }

                } else if (xmlPullParser.getName().equalsIgnoreCase("pubDate")) {
                    @SuppressWarnings("deprecation")
                    Date pubDate = new Date(xmlPullParser.nextText());
                    currentArticle.setPubDate(pubDate);
                }

            } else if (eventType == XmlPullParser.END_TAG && xmlPullParser.getName().equalsIgnoreCase("item")) {
                insideItem = false;
                articles.add(currentArticle);
                currentArticle = new Article();
            }
            eventType = xmlPullParser.next();
        }
        triggerObserver();
    }


    private void triggerObserver() {
        setChanged();
        notifyObservers(articles);
    }
}