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

import com.prof.rssparser.Article;
import com.prof.rssparser.OnTaskCompleted;
import com.prof.rssparser.Parser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MainViewModel extends ViewModel {

    private MutableLiveData<List<Article>> articleListLive = null;
    private String urlString = "https://www.androidauthority.com/feed";

    private MutableLiveData<String> snackbar = new MutableLiveData<>();

    public MutableLiveData<List<Article>> getArticleList() {
        if (articleListLive == null) {
            articleListLive = new MutableLiveData<>();
        }
        return articleListLive;
    }

    private void setArticleList(List<Article> articleList) {
        this.articleListLive.postValue(articleList);
    }

    public LiveData<String> getSnackbar() {
        return snackbar;
    }

    public void onSnackbarShowed() {
        snackbar.setValue(null);
    }

    public void fetchFeed() {

        Executors.newSingleThreadExecutor().submit(new Runnable() {
            @Override
            public void run() {
                Parser parser = new Parser();
                parser.onFinish(new OnTaskCompleted() {

                    //what to do when the parsing is done
                    @Override
                    public void onTaskCompleted(List<Article> list) {
                        setArticleList(list);
                    }

                    //what to do in case of error
                    @Override
                    public void onError(Exception e) {
                        setArticleList(new ArrayList<Article>());
                        e.printStackTrace();
                        snackbar.postValue("An error has occurred. Please try again");
                    }
                });
                parser.execute(urlString);
            }
        });
    }
}
