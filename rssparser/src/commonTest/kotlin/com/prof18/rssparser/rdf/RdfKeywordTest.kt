package com.prof18.rssparser.rdf

import com.prof18.rssparser.internal.RdfKeyword
import com.prof18.rssparser.internal.RdfKeyword.Companion.isValid
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RdfKeywordTest {

    @Test
    fun `isValid returns true if it is a valid rdf keyword`() {
        val entries = RdfKeyword.entries
        for (entry in entries) {
            assertTrue(isValid(entry.value))
        }
    }

    @Test
    fun `isValid returns false if it not a valid rdf keyword`() {
        assertFalse(isValid("NOT_VALID_RDF_KEYWORD"))
    }
}
