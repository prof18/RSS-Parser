package com.prof.rssparser.utils

internal sealed class AtomKeyword(val value: String) {
    object Atom : AtomKeyword("feed")
    object Title : AtomKeyword("title")
    object Link : AtomKeyword("link") {
        object Href : AtomKeyword("href")
        object Rel : AtomKeyword("rel")
        object Edit : AtomKeyword("edit")
    }
    object Subtitle : AtomKeyword("subtitle")

    object Entry {
        object Item : AtomKeyword("entry")
        object Guid : AtomKeyword("id")
        object Content : AtomKeyword("content")
        object PubDate : AtomKeyword("updated")
        object Category : AtomKeyword("category")
        object Description : AtomKeyword("summary")
        object Author : AtomKeyword("name")
        object Email : AtomKeyword("email")
    }
}