# RSS Parser
[![Download](https://api.bintray.com/packages/prof18/maven/RSS-Parser/images/download.svg)](https://bintray.com/prof18/maven/YoutubeParser/_latestVersion)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)

## Important Notice
Versions 1.4 and 1.4.1 have been deleted due to a critical dependency error. Please forgive me and update to the latest version available.

## About

This is an Android library to parse a RSS Feed. You can retrieve the following information about channel:
<ul>
<li> Title
<li> Description
<li> Link
<li> Articles
</ul>

Articles can have following attributes:
<ul>
<li> Title
<li> Author
<li> Description
<li> Content
<li> Main Image
<li> Link
<li> Publication Date
<li> Categories
</ul>

**Disclaimer**: This library has been built starting from RSS feed generated from a Wordpress Site. Of course it's compatible with RSS feed generated from other tipe of sites; [Here](https://www.androidauthority.com/feed/) you can find an example of RSS feed.

## How to
#### Import:
The library is uploaded in jCenter, so you can easily add the dependency:
```Gradle
dependencies {
  compile 'com.prof.rssparser:rssparser:2.0.4'
}
```
#### Use:

Starting from the version 2.x, the library has been completely rewritten using Kotlin and the coroutines. However, The compatibility with Java has been maintained and the same code of the versions 1.x can be used.  

If you are using Kotlin you need to [launch](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/launch.html) the coroutine that retrieves the articles. 

```Kotlin
import com.prof.rssparser.Article
import com.prof.rssparser.Parser

//url of RSS feed
private val url = "https://www.androidauthority.com/feed"

coroutineScope.launch(Dispatchers.Main) {
    try {
        val parser = Parser()
        val articleList = parser.getChannel(url)
        // Show the channel data
    } catch (e: Exception) {
        // Handle the exception
    }
}
```
You can give a look to the full kotlin sample by clicking [here](https://github.com/prof18/RSS-Parser/tree/master/samplekotlin)

If you are still using Java, the code is very similar to the older versions of the library:

```Java
import com.prof.rssparser.Article;
import com.prof.rssparser.OnTaskCompleted;
import com.prof.rssparser.Parser;

Parser parser = new Parser();
parser.onFinish(new OnTaskCompleted() {

    //what to do when the parsing is done
    @Override
    public void onTaskCompleted(Channel channel) {
        // Use the channel info
    }

    //what to do in case of error
    @Override
    public void onError(Exception e) {
        // Handle the exception
    }
});
parser.execute(urlString);
```
The full Java sample is available [here](https://github.com/prof18/RSS-Parser/tree/master/samplejava)

##### Version 1.4.4 and below:


```Java
import com.prof.rssparser.Article;
import com.prof.rssparser.Parser;

//url of RSS feed
String urlString = "http://www.androidcentral.com/feed";
Parser parser = new Parser();
parser.onFinish(new Parser.OnTaskCompleted() {
    
    @Override
    public void onTaskCompleted(Channel channel) {
      //what to do when the parsing is done
    }

    @Override
    public void onError() {
      //what to do in case of error
    }
});
parser.execute(urlString);
```
## Sample app
I wrote a simple app that shows articles from Android Authority. If in the article's content there isn't an image, a placeholder will be loaded. 

<img src="https://github.com/prof18/RSS-Parser/blob/master/Screen.png" width="30%" height="30%">

The sample is written both in Kotlin and Java. You can browse the Kotlin code [here](https://github.com/prof18/RSS-Parser/tree/master/samplekotlin) and the Java code [here](https://github.com/prof18/RSS-Parser/tree/master/samplejava)
You can also download the <a href="https://github.com/prof18/RSS-Parser/blob/master/RSS%20Parser.apk"> apk file</a> to try it!

Please use the issues tracker only to report issues. If you have any kind of question you can ask it on [the blog post that I wrote](https://medium.com/@marcogomiero/how-to-easily-handle-rss-feeds-on-android-with-rss-parser-8acc98e8926f)

## Changelog
- 28 September 2019 - Add the possibility to provide a custom OkHttpClient (Issue #43). Add handling of encoding (Issue #45). Add the possibility to stop the fetching and parsing process (Issue #30). Migration to Gradle 5
- 18 May 2019 - Fix parsing image url from enclosure tag (PR #35). Support parsing time tag (PR #34). Add parsing of guid (Issue #31) - Version 2.0.4
- 24 January 2019 - Now the date of an article is saved as String, to extend the compatibility with different formats - Version 2.0.3
- 11 January 2019 - Moved the Java parsing on a background thread - Version 2.0.2
- 2 January 2019 - Fixed an error on Date parsing - Version 2.0.1
- 22 December 2018 - Rewrote library with Kotlin - Version 2.0.0
- 8 December 2018 - Include thrown exception in onError() callback (PR #22) - Version 1.4.5
- 7 September 2018 - Added more sources for the featured image. Removed unused resources and improved the parsing of the image. Fixed dependency errors. - Version 1.4.4
- 14 December 2017 - Little fixes on Error Management - Version 1.3.1
- 13 December 2017 - Improved Error Management - Version 1.3
- 10 December 2017 - Added support for the categories - Version 1.2
- 12 August 2017 - Fixed the Library Manifest and updated the dependencies - Version 1.1
- 18 June 2016 - First release  - Version 1.0

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
