package com.prof18.rssparser.internal

internal enum class RdfKeyword(val value: String) {
    RDF("rdf:RDF"),
    TITLE("title"),
    DESCRIPTION("description"),
    LINK("link"),
    DC_DATE("dc:date"),
    CHANNEL("channel"),
    IMAGE("image"),
    RESOURCE("rdf:resource"),
    UPDATE_PERIOD("sy:updatePeriod"),
    ITEM("item"),
    DC_CREATOR("dc:creator"),
    DC_SUBJECT("dc:subject");

    companion object {
        /**
         * Validates and returns the RdfKeyword for the given string value.
         * @param value the string value to validate
         * @return the corresponding RdfKeyword if valid, null otherwise
         */
        fun fromValue(value: String): RdfKeyword? {
            return entries.firstOrNull { it.value.equals(value, ignoreCase = true) }
        }

        /**
         * Checks if the given string value is a valid RDF keyword.
         * @param value the string value to validate
         * @return true if valid, false otherwise
         */
        fun isValid(value: String): Boolean {
            return fromValue(value) != null
        }
    }
}
