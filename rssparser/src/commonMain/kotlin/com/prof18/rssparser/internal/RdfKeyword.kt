package com.prof18.rssparser.internal

internal enum class RdfKeyword(val value: String) {
    // Root
    RDF("rdf:RDF"),

    // Shared between Channel and Item
    TITLE("title"),
    DESCRIPTION("description"),
    LINK("link"),
    DC_DATE("dc:date"),

    // Channel
    CHANNEL("channel"),
    CHANNEL_IMAGE("image"),
    CHANNEL_IMAGE_RESOURCE("rdf:resource"),
    CHANNEL_UPDATE_PERIOD("sy:updatePeriod"),

    // Item
    ITEM("item"),
    ITEM_DC_CREATOR("dc:creator"),
    ITEM_DC_SUBJECT("dc:subject");

    internal companion object {
        private val valueMap: Map<String, RdfKeyword> = entries.associateBy { it.value.lowercase() }

        fun isValid(value: String): Boolean {
            return value.lowercase() in valueMap
        }
    }
}
