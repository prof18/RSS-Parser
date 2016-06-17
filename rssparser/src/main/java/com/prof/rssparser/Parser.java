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

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by marco on 6/17/16.
 */
public class Parser extends AsyncTask<String, Void, String> implements Observer{

    XMLParser xmlParser;
    static ArrayList<Article> articles = new ArrayList<>();

    private OnTaskCompleted onComplete;

    public Parser() {

        xmlParser = new XMLParser();
        xmlParser.addObserver(this);
    }

    public interface OnTaskCompleted{
        void onTaskCompleted(ArrayList<Article> list);
        void onError();
    }

    public void onFinish (OnTaskCompleted onComplete ) {
        this.onComplete = onComplete;
    }

    @Override
    protected String doInBackground(String... ulr) {

        Response response = null;
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(ulr[0])
                .build();

        try {

            response = client.newCall(request).execute();
            return response.body().string();

        } catch (IOException e) {

            e.printStackTrace();
            onComplete.onError();

        }

        return null;
    }

    @Override
    protected void onPostExecute(String result) {

        try {

            xmlParser.parseXML(result);
            Log.i("RSS Parser ", "RSS parsed correctly!");

        } catch (Exception e) {

            e.printStackTrace();
            onComplete.onError();

        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void update(Observable observable, Object data) {

        articles = (ArrayList<Article>) data;
        onComplete.onTaskCompleted(articles);

    }

}
