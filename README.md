# RSS Parser
[![Download](https://api.bintray.com/packages/prof18/maven/RSS-Parser/images/download.svg)](https://bintray.com/prof18/maven/YoutubeParser/_latestVersion)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)

This is an Android library to parse a RSS Feed. You can retrive the following information about an article:
<ul>
<li> Title
<li> Author
<li> Description
<li> Content
<li> Main Image
<li> Link
<li> Publication Date
</ul>

## How to
#### Import:
The library is uploaded in jCenter, so you can easily add the dependency:
```Gradle
dependencies {
  compile 'com.prof.rssparser:rssparser:1.0'
}
```
#### Use:
```Java
import com.prof.rssparser.Article;
import com.prof.rssparser.Parser;

//url of RSS feed
String urlString = "http://www.androidcentral.com/feed";
Parser parser = new Parser();
parser.execute(urlString);
parser.onFinish(new Parser.OnTaskCompleted() {
    
    @Override
    public void onTaskCompleted(ArrayList<Article> list) {
      //what to do when the parsing is done
      //the Array List contains all article's data. For example you can use it for your adapter. 
    }

    @Override
    public void onError() {
      //what to do in case of error
    }
});
```
## Sample app
I wrote a simple app that shows articles from Android Central. If in the article's content there isn't a image, a placeholder
will be load. 

<img src="https://github.com/prof18/RSS-Parser/blob/master/Screen.png" width="30%" height="30%">

You can browse the code <a href="https://github.com/prof18/RSS-Parser/tree/master/app"> in this repo.</a> 
You can also download the <a href="https://github.com/prof18/RSS-Parser/blob/master/RSS%20Parser.apk"> apk file</a> to try it!

## License
```
   Copyright 2016 Marco Gomiero

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
