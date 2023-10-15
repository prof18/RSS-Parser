# RSS Parser

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.prof18.rssparser/rssparser/badge.svg?style=plastic)](https://maven-badges.herokuapp.com/maven-central/com.prof18.rssparser/rssparser)
[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)

**RSS Parser** is a Kotlin Multiplatform library for parsing RSS and Atom feeds. It supports Android, iOS, and the JVM.

With **RSS Parser**, you can fetch plenty of useful information from any RSS channel, be it a blog, magazine, or even a podcast feed.

## Table of Contents

- [Installation](#installation)
- [Important Notices](#important-notices)
    - [Artifact change](#artifact-change)
    - [Breaking changes](#breaking-changes)
- [Available data](#available-data)
- [Usage](#usage)
    - [Creating a RssParser instance](#creating-a-rssparser-instance)
        - [Builder](#builder)
    - [RSS Parsing from URL](#rss-parsing-from-url)
    - [RSS Parsing from string](#rss-parsing-from-string)
- [Migration from version 5](#migration-from-version-5)
- [Sample project](#sample-project)
- [Changelog](#changelog)
- [License](#license)
- [Apps using RSS Parser](#apps-using-rss-parser)

## Installation

RSS Parser is currently published to Maven Central, so add it to your project's repositories.

```kotlin
repositories {
    mavenCentral()
    // ...
}
```

Then, add the dependency to your common source set's dependencies.

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
    // ...
    implementation("com.prof18.rssparser:rssparser:<latest-version>")
}
```

## Important Notices

### Artifact change

Since February 2022, the library artifacts have been moved to MavenCentral. The group ID changed from `com.prof.rssparser` to `com.prof18.rssparser`.
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

- Some class names and packages have been changed. Check out the [migration guide](#migration-from-version-5) for further info.
- Caching of feeds is not supported anymore. After some consideration, I decided that the aim of this library is not meant to provide a caching solution for RSS feeds, but just to do the parsing. The user of the library should implement any caching/storing solutions their project requires.
- Support for Java with the `OnTaskCompleted` and `onError` callbacks has been dropped.

[Here](https://github.com/prof18/RSS-Parser/blob/version-5/README.md) you can find the README for version 5 of RSSParser.

## Available data

The [`RssChannel` result object](https://github.com/prof18/RSS-Parser/blob/master/rssparser/src/commonMain/kotlin/com/prof18/rssparser/model/RssChannel.kt) provides the following data:

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

Items support the following attributes:

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

RssParser uses [Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) for all the asynchronous work.

### Creating an RssParser instance

An `RssParser` instance is the entry point of the library. 

It's possible to create an instance of `RssParser` directly in the common code, without having to pass any platform-specific dependencies. 

```kotlin
val rssParser: RssParser = RssParser()
```

#### Builder

An `RssParser` instance can also be built with a platform-specific `RssParserBuilder`. Some custom and optional fields can be provided to the builder.

**On Android and the JVM**, a custom `OkHttpClient` instance and a custom `Charset` can be provided. If an `OkHttpClient` instance is not provided, the library will create one. If no `Charset` is provided, it will automatically be inferred from the feed XML.

```kotlin
val builder = RssParserBuilder(
    callFactory = OkHttpClient(),
    charset = Charsets.UTF_8,
)
val rssParser = builder.build() 
```

**On iOS**, a custom `NSURLSession` instance can be provided. If an `NSURLSession` instance is not provided, the library will use the shared `NSURLSession`. 

```kotlin
val builder = RssParserBuilder(
    nsUrlSession = NSURLSession(),
)
val rssParser = builder.build() 
```

### RSS Parsing from URL
To parse an RSS feed from a URL, the suspending `getRssChannel` function can be used.

```kotlin
val rssChannel: RssChannel = rssParser.getRssChannel("https://www.rssfeed.com")
```

### RSS Parsing from string

To parse an RSS feed from an XML string, the suspending `parse` function can be used

```kotlin
val xmlString: String = "xml-string"
val rssChannel: RssChannel = rssParser.parse(xmlString)
```

## Sample projects

The repository contains two samples projects: a [multiplatform](/sample/) and an [Android](/sampleandroid/) project to showcase the usage in a multiplatform app and an Android one.

## Migration from version 5

Version 6 of the library introduced the following breaking changes:

- The main package name has been changed from `com.prof.rssparser` to `com.prof18.rssparser`;
- `com.prof.rssparser.Parser` has been moved and renamed to `com.prof18.rssparser.RssParser`;
- `com.prof.rssparser.Parser.Builder` has been moved and renamed to `com.prof18.rssparser.RssParserBuilder`;
- `com.prof.rssparser.Channel` has been moved and renamed to `com.prof18.rssparser.model.RssChannel`;
- `com.prof.rssparser.Article` has been moved and renamed to `com.prof18.rssparser.model.RssItem`;
- `com.prof.rssparser.HTTPException` has been moved and renamed to `com.prof18.rssparser.exception.HttpException`;
- `com.prof.rssparser.Image` has been moved and renamed to `com.prof18.rssparser.RssImage`;
- `com.prof.rssparser.ItunesOwner` has been moved to `com.prof18.rssparser.model.ItunesOwner`;
- `com.prof.rssparser.ItunesArticleData` has been moved and renamed to `com.prof18.rssparser.model.ItunesItemData`;
- `com.prof.rssparser.ItunesChannelData` has been moved to `com.prof18.rssparser.model.ItunesChannelData`;
- `getChannel()` has been renamed to `getRssChannel()`;
- `cancel()` is not available anymore, the cancellation can be handled by the caller.

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
    * [ReLabs](https://github.com/theimpulson/ReLabs)

</details>

