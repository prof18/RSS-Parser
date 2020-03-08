# RSS Parser
[![Download](https://api.bintray.com/packages/prof18/maven/RSS-Parser/images/download.svg)](https://bintray.com/prof18/maven/YoutubeParser/_latestVersion)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)

## Important Notice
Versions 1.4 and 1.4.1 have been deleted due to a critical dependency error. Please forgive me and update to the latest version available.

## About

This is an Android library to parse a RSS Feed. You can retrieve the following information about an RSS channel:

- Title
- Description
- Link
- Articles
- Image (Title, Url and Link)
- Last build data
- Update period

Articles can have following attributes:

- Title
- Author
- Description
- Content
- Main Image
- Link
- Publication Date
- Categories
- Audio
- Source (name and link)  


**Disclaimer**: This library has been built starting from RSS feed generated from a Wordpress Site. Of course it's compatible with RSS feed generated from other tipe of sites; [Here](https://www.androidauthority.com/feed/) you can find an example of RSS feed.

## How to
#### Import:
The library is uploaded in jCenter, so you can easily add the dependency:
```Gradle
dependencies {
  implementation 'com.prof.rssparser:rssparser:2.1.1'
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

Please use the issues tracker only to report issues. If you have any kind of question you can ask it on [the blog post that I wrote](https://www.marcogomiero.com/posts/2017/rss-parser-library/)

## Changelog

From version 1.4.4 and above, the changelog is available in the [release section.](https://github.com/prof18/RSS-Parser/releases)

- 14 December 2017 - Little fixes on Error Management - Version 1.3.1
- 13 December 2017 - Improved Error Management - Version 1.3
- 10 December 2017 - Added support for the categories - Version 1.2
- 12 August 2017 - Fixed the Library Manifest and updated the dependencies - Version 1.1
- 18 June 2016 - First release  - Version 1.0

## License
```
   Copyright 2016-2020 Marco Gomiero

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
