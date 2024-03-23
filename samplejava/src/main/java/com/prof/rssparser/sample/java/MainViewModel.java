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

package com.prof.rssparser.sample.java;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.prof18.rssparser.RssParser;
import com.prof18.rssparser.RssParserBuilder;
import com.prof18.rssparser.model.RssChannel;

public class MainViewModel extends ViewModel {

    private MutableLiveData<RssChannel> articleListLive = null;
    private String urlString = "https://www.androidauthority.com/feed";

    private MutableLiveData<String> snackbar = new MutableLiveData<>();

    public MutableLiveData<RssChannel> getChannel() {
        if (articleListLive == null) {
            articleListLive = new MutableLiveData<>();
        }
        return articleListLive;
    }

    private void setChannel(RssChannel channel) {
        this.articleListLive.postValue(channel);
    }

    public LiveData<String> getSnackbar() {
        return snackbar;
    }

    public void onSnackbarShowed() {
        snackbar.setValue(null);
    }

    public void fetchFeed() {

        RssParser parser = new RssParserBuilder().build();

        try {
            RssChannel channel = CoroutineBridgeKt.parseFeed(parser, urlString).get();
            setChannel(channel);
        } catch (Exception e) {
            e.printStackTrace();
            snackbar.postValue("An error has occurred. Please try again");
        }
    }
}
