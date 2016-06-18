# RSS Parser
[ ![Download](https://api.bintray.com/packages/prof18/maven/RSS-Parser/images/download.svg) ]
(https://bintray.com/prof18/maven/YoutubeParser/_latestVersion)
[![License](https://img.shields.io/badge/License-MIT-blue.svg?style=flat)](http://opensource.org/licenses/MIT) 
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

##How to
####Import:
The library is uploaded in jCenter, so you can easily add the dependency:
```Gradle
dependencies {
  compile 'com.prof.rssparser:rssparser:1.0'
}
```
####Use:
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
##Sample app
I wrote a simple app that shows articles from Android Central. If in the article's content there isn't a image, a placeholder
will be load. 

<img src="https://github.com/prof18/RSS-Parser/blob/master/Screen.png" width="30%" height="30%">

You can browse the code <a href="https://github.com/prof18/RSS-Parser/tree/master/app"> in this repo.</a> 
You can also download the <a href="https://github.com/prof18/RSS-Parser/blob/master/RSS%20Parser.apk"> apk file</a> to try it!

##License
```
The MIT License (MIT)

Copyright (c) 2016 Marco Gomiero

Permission is hereby granted, free of charge, to any person obtaining a copy 
of this software and associated documentation files (the "Software"), to deal 
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell 
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all 
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL 
THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE 
SOFTWARE. 
```
