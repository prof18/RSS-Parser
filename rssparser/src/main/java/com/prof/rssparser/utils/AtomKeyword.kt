package com.prof.rssparser.utils

internal sealed class AtomKeyword(val value: String) {
    object Atom : AtomKeyword("feed")
    object Title : AtomKeyword("title")
    object Icon: AtomKeyword("icon")
    object Link : AtomKeyword("link") {
        object Href : AtomKeyword("href")
        object Rel : AtomKeyword("rel")
        object Edit : AtomKeyword("edit")
    }
    object Subtitle : AtomKeyword("subtitle")
    object Updated: AtomKeyword("updated")
    object Entry {
        object Item : AtomKeyword("entry")
        object Guid : AtomKeyword("id")
        object Content : AtomKeyword("content")
        object Published : AtomKeyword("published")
        object Category : AtomKeyword("category")
        object Term : AtomKeyword("term")
        object Description : AtomKeyword("summary")
        object Author : AtomKeyword("name")
        object Email : AtomKeyword("email")
    }
}