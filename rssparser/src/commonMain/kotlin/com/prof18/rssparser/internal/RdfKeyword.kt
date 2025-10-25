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

    companion object {
        // Pre-computed map for O(1) lookup performance
        private val valueMap: Map<String, RdfKeyword> = entries.associateBy { it.value.lowercase() }

        /**
         * Validates and returns the RdfKeyword for the given string value.
         * @param value the string value to validate
         * @return the corresponding RdfKeyword if valid, null otherwise
         */
        fun fromValue(value: String): RdfKeyword? {
            return valueMap[value.lowercase()]
        }

        /**
         * Checks if the given string value is a valid RDF keyword.
         * @param value the string value to validate
         * @return true if valid, false otherwise
         */
        fun isValid(value: String): Boolean {
            return value.lowercase() in valueMap
        }
    }
}
