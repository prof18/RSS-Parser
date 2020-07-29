# RSS Parser

[![Download](https://api.bintray.com/packages/prof18/maven/RSS-Parser/images/download.svg)](https://bintray.com/prof18/maven/YoutubeParser/_latestVersion)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)

**RSS Parser** is an Android library to parse any RSS feed. With **RSS Parser**, you can fetch plenty of useful information from any RSS channel, be it a blog, magazine, or even a podcast feed. 

As of April 2020, it's been downloaded more than 30,000 times from 125 different countries (among them, North Korea, Madagascar and even Panama 🌴).

## ⚠️ Important Notice

Versions 1.4 and 1.4.1 have been deleted due to a critical dependency error. Please forgive me and update to the latest version available.

## Table of Contents

* [Available data](#available-data)
* [Features](#features)
  * [Caching](#caching)
  * [RSS Parsing from string](rss-parsing-from-string)
* [Installation](#installation)
* [Usage](#usage)
  * [Kotlin](#kotlin)
  * [Java (deprecated)](#java)
* [Sample app](#sample-app)
* [Changelog](#changelog)
* [License](#license)
* [Apps using RSS Parser](#app-using-rss-parser)

## Available data

The RSS channel resulting object provides you with the following data:

- Title
- Description
- Link
- Articles
- Image (Title, Url and Link)
- Last build data
- Update period

Articles, on the other hand, support the following attributes:

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

## Features

### Caching

The library provides a caching layer to avoid wasting data with very long feeds. All you need to do is provide an expiration date. For more details, give a look to the [Usage section](#usage)

⚠️ For the time being caching is only supported if you are using Kotlin.

### RSS Parsing from string

The library provides a way of simply parsing raw data into POJOs.

## Installation

The library is provided by jCenter, so you can easily add its dependency and be good to go.

```Gradle
dependencies {
  implementation 'com.prof.rssparser:rssparser:2.1.1'
}
```

## Usage

### Kotlin

First of all, you have to create a `Parser` object with the Parser Builder. In the builder you can provided some custom and optional fields:

- A custom `OkHttpClient`; if not provided, the library will create one for you.
- A custom Charset; if not provided, the default one is `UTF_8`.
- The Android Context, mandatory to make the caching layer work.
- The cache expiration date in milliseconds, mandatory to make the caching layer work.

If you don't provide the Android Context and the cache expiration date, the library will continue working but without the caching feature.

```Kotlin
val parser = Parser.Builder()
                .context(this)
                .charset(Charset.forName("ISO-8859-7"))
                .cacheExpirationMillis(24L * 60L * 60L * 100L) // one day
                .build()
```

The library uses the Kotlin Coroutines to retrieve, parse and cache the feed. So to start the process, you need to [launch](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines/launch.html) the coroutine.

```Kotlin

//url of RSS feed
private val url = "https://www.androidauthority.com/feed"

viewModelScope.launch {
    try {
        val channel = parser.getChannel(url)
        // Do something with your data
    } catch (e: Exception) {
        e.printStackTrace()
        // Handle the exception
    }
}
```

For flushing the cache:

```Kotlin
parser.flushCache(url)
```

For simply parsing raw data:
- A `suspend` function `Parser#parser(rawRssFeed)` which returns the channel.  
- A java compatible function `Parser.parser(rawRssFeed, OnTaskCompleted)` which calls the listener once done.

```kotlin
// parser without any network or caching configuration
val parser = Parser.Builder().build()

viewModelScope.launch {
    try {
        val channel = parser.parse(raw)
        // Do something with your data
    } catch (e: Exception) {
        e.printStackTrace()
        // Handle the exception
    }
}
```

You can give a look to the full Kotlin sample by clicking [here](https://github.com/prof18/RSS-Parser/tree/master/samplekotlin)

### Java

Starting from the version 2.x, the library has been completely rewritten using Kotlin and the coroutines. However, the compatibility with Java has been preserved and the same code of the versions 1.x can still be used.

⚠️ For the time being, the caching is not supported if you are using Java.

If you are still using Java, this is the interface to use the library:

```Java
import com.prof.rssparser.Article;
import com.prof.rssparser.OnTaskCompleted;
import com.prof.rssparser.Parser;

Parser parser = new Parser.Builder()
        .charset(Charset.forName("ISO-8859-7"))
        // .cacheExpirationMillis() and .context() not called because on Java side, caching is NOT supported
        .build();
        
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

#### Version 1.4.4 and below

For older versions of the library, the interface is marginally different:

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

## Apps using RSS Parser
If you are using RSS Parser in your app and would like to be listed here, please open a pull request!

<details>
  <summary>List of Apps using RSS Parser</summary>

* [MarioDiscepolo.com](https://play.google.com/store/apps/details?id=com.prof.mariodiscepolo&hl=it)

</details>
