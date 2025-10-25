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
         * Validates if the given string is a valid RDF keyword.
         * @param keyword The keyword string to validate
         * @return true if the keyword is valid, false otherwise
         */
        fun isValid(keyword: String): Boolean {
            return entries.any { it.value.equals(keyword, ignoreCase = true) }
        }

        /**
         * Finds the RdfKeyword enum entry for the given keyword string.
         * @param keyword The keyword string to find
         * @return The matching RdfKeyword or null if not found
         */
        fun fromValue(keyword: String): RdfKeyword? {
            return entries.find { it.value.equals(keyword, ignoreCase = true) }
        }
    }
}
