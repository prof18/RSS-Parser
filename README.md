# RSS Parser

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.prof18.rssparser/rssparser/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/com.prof18.rssparser/rssparser)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)

**RSS Parser** is a Kotlin Multiplatform library to parse any RSS or Atom feed. The supported platforms are Android, iOS and the JVM.

With **RSS Parser**, you can fetch plenty of useful information from any RSS channel, be it a blog, magazine, or even a podcast feed.

## Table of Contents

- [Installation](#installation)
- [Important Notices](#important-notices)
    - [Artifact change](#artifact-change)
    - [Breaking changes](#breaking-changes)
- [Available data](#available-data)
- [Usage](#usage)
    - [Creating a RssParser instance](#creating-a-rssparser-instance)
    - [RSS Parsing from URL](#rss-parsing-from-url)
    - [RSS Parsing from string](#rss-parsing-from-string)
- [Migration from version 5](#migration-from-version-5)
- [Sample app](#sample-app)
- [Changelog](#changelog)
- [License](#license)
- [Apps using RSS Parser](#app-using-rss-parser)

## Installation

RSSParser is currently published to Maven Central, so add that to repositories.

```kotlin
repositories {
    mavenCentral()
    // ...
}
```

Then, add the dependency to your common source-set dependencies

```kotlin
commonMain {
    dependencies {
        // ...
        implementation("com.prof18.rssparser:rssparser:<latest-version>")
    }
}
```

If you are in an Android-only project, simply add the dependency:

```kotlin
dependencies {
    implementation 'com.prof18.rssparser:rssparser:<latest-version>'
}
```

## Important Notices

### Artifact change

Since February 2022, the library artifacts have been moved to MavenCentral. The group id is changed from `com.prof.rssparser` to `com.prof18.rssparser`.
Be sure to add the gradle dependency to your root `build.gradle` file.
```
allprojects {
    repositories {
        mavenCentral()
    }
}
```

### Breaking changes

From version 6.0, RSSParser has become Multiplatform. This update brought some breaking changes:

- Some class names and packages have been changed: please check out the [migration guide](#migration-from-version-5) for further info;
- the caching of the feed is not supported anymore. After some thinking, I decided that the aim of this library is not to provide a caching solution for an RSS feed, but just do the parsing. Any required caching/storing solution needs to be implemented by the user of the library;
- The support for Java with the `OnTaskCompleted` and `onError` callbacks has been dropped.

[Here](https://github.com/prof18/RSS-Parser/blob/version-5/README.md) you can find the README for version 5 of RSSParser.

## Available data

The [RSS channel resulting object](https://github.com/prof18/RSS-Parser/blob/master/rssparser/src/commonMain/kotlin/com/prof18/rssparser/model/RssChannel.kt) provides you with the following data:

- Title
- Description
- Link
- Items
- Image (Title, Url and Link)
- Last build data
- Update period
- Itunes Data:
  - Author
  - Categories
  - Duration  
  - Explicit
  - Image
  - Keywords
  - News Feed URL
  - Owner  
  - Subtitle
  - Summary
  - Type

Items, on the other hand, support the following attributes:

- Title
- Author
- Description
- Content
- Image
- Link
- Publication Date
- Categories
- Audio
- Source (name and URL)
- GUID
- Video
- Comments URL
- Itunes Data:
  - Author
  - Duration
  - Episode
  - Episode Type
  - Explicit
  - Image
  - Keywords
  - Subtitle
  - Summary
  - Season

## Usage

RssParser is available on the Android, iOS and JVM platforms.

### Creating a RssParser instance

// TODO: create from common code?

### RSS Parsing from URL

### RSS Parsing from string

## Changelog

From version 1.4.4 and above, the changelog is available in the [release section.](https://github.com/prof18/RSS-Parser/releases)

- 14 December 2017 - Little fixes on Error Management - Version 1.3.1
- 13 December 2017 - Improved Error Management - Version 1.3
- 10 December 2017 - Added support for the categories - Version 1.2
- 12 August 2017 - Fixed the Library Manifest and updated the dependencies - Version 1.1
- 18 June 2016 - First release  - Version 1.0

## License

```
Copyright 2016-2023 Marco Gomiero

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

    * [FeedFlow](https://www.feedflow.com)

</details>





----



### RSS Parsing from string

The library provides a way of simply parsing raw data from a string.



## Usage

### Kotlin

First of all, you have to create a `Parser` object with the Parser Builder. In the builder you can provided some custom and optional fields:

- A custom `OkHttpClient`; if not provided, the library will create one for you.
- A custom Charset; if not provided, it will automatically inferred from the XML.
- The Android Context, mandatory to make the caching layer work.
- The cache expiration date in milliseconds, mandatory to make the caching layer work.

If you don't provide the Android Context and the cache expiration date, the library will continue working but without the caching feature.

```Kotlin
val parser = Parser.Builder()
                .context(this)
                .charset(Charset.forName("ISO-8859-7"))
                .cacheExpirationMillis(24L * 60L * 60L * 1000L) // one day
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
import com.prof18.rssparser.Article;
import com.prof18.rssparser.OnTaskCompleted;
import com.prof18.rssparser.Parser;

Parser parser=new Parser.Builder()
        .charset(Charset.forName("ISO-8859-7"))
        // .cacheExpirationMillis() and .context() not called because on Java side, caching is NOT supported
        .build();

        parser.onFinish(new OnTaskCompleted(){

//what to do when the parsing is done
@Override
public void onTaskCompleted(Channel channel){
        // Use the channel info
        }

//what to do in case of error
@Override
public void onError(Exception e){
        // Handle the exception
        }
        });
        parser.execute(urlString);
```

For parsing raw data there is a java compatible function which calls the listeners `OnTaskCompleted` and `onError` once done.

`Parser.parser(rawRssFeed, OnTaskCompleted)`


The full Java sample is available [here](https://github.com/prof18/RSS-Parser/tree/master/samplejava)

#### Version 1.4.4 and below

For older versions of the library, the interface is marginally different:

```Java
import com.prof18.rssparser.Article;
import com.prof18.rssparser.Parser;

//url of RSS feed
String urlString="http://www.androidcentral.com/feed";
        Parser parser=new Parser();
        parser.onFinish(new Parser.OnTaskCompleted(){

@Override
public void onTaskCompleted(Channel channel){
        //what to do when the parsing is done
        }

@Override
public void onError(){
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


-----

