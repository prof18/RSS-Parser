package com.prof.rssparser.utils

internal sealed class AtomKeyword(val value: String) {
    object ATOM : AtomKeyword("feed")
    object Title : AtomKeyword("title")
    object Link : AtomKeyword("link") {
        object HREF : AtomKeyword("href")
        object REL : AtomKeyword("rel")
        object TYPE : AtomKeyword("type")
    }
    object SUBTITLE : AtomKeyword("subtitle")

    object Entry {
        object Item : AtomKeyword("entry")
        object GUID : AtomKeyword("id")
        object Content : AtomKeyword("content")
        object PubDate : AtomKeyword("updated")
        object Category : AtomKeyword("category")
        object Description : AtomKeyword("summary")
        object Author : AtomKeyword("name")
        object Email : AtomKeyword("email")
    }
}