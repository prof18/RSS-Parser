/*
 * The MIT License (MIT)
 *
 *    Copyright (c) 2016 Marco Gomiero
 *
 *    Permission is hereby granted, free of charge, to any person obtaining a copy
 *    of this software and associated documentation files (the "Software"), to deal
 *    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *    copies of the Software, and to permit persons to whom the Software is
 *    furnished to do so, subject to the following conditions:
 *
 *    The above copyright notice and this permission notice shall be included in all
 *    copies or substantial portions of the Software.
 *
 *    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 *    THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *    SOFTWARE.
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
    Article currentArticle;

    public XMLParser() {

        articles = new ArrayList<>();
        currentArticle = new Article();
    }

    public void parseXML (String xml) {

       try {

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

                   } else if (xmlPullParser.getName().equalsIgnoreCase("content:encoded")) {

                       if (insideItem) {

                           String htmlData = xmlPullParser.nextText();
                           Document doc = Jsoup.parse(htmlData);
                           try {

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

       } catch (Exception e) {

           e.printStackTrace();

       }
    }

    private void triggerObserver() {

        setChanged();
        notifyObservers(articles);
    }
}