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

import com.prof.rssparser.Article;
import com.prof.rssparser.Channel;
import com.prof.rssparser.OnTaskCompleted;
import com.prof.rssparser.Parser;

import java.util.ArrayList;

public class MainViewModel extends ViewModel {

    private MutableLiveData<Channel> articleListLive = null;
    private String urlString = "https://www.androidauthority.com/feed";

    private MutableLiveData<String> snackbar = new MutableLiveData<>();

    public MutableLiveData<Channel> getChannel() {
        if (articleListLive == null) {
            articleListLive = new MutableLiveData<>();
        }
        return articleListLive;
    }

    private void setChannel(Channel channel) {
        this.articleListLive.postValue(channel);
    }

    public LiveData<String> getSnackbar() {
        return snackbar;
    }

    public void onSnackbarShowed() {
        snackbar.setValue(null);
    }

    public void fetchFeed() {

        Parser parser = new Parser();
        parser.onFinish(new OnTaskCompleted() {

            //what to do when the parsing is done
            @Override
            public void onTaskCompleted(Channel channel) {
                setChannel(channel);
            }

            //what to do in case of error
            @Override
            public void onError(Exception e) {
                setChannel(new Channel(null, null, null, null, null, null, new ArrayList<Article>()));
                e.printStackTrace();
                snackbar.postValue("An error has occurred. Please try again");
            }
        });
        parser.execute(urlString);
        parser.cancel();
    }
}
