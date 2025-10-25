package com.prof18.rssparser.rss

import com.prof18.rssparser.internal.RssKeyword
import com.prof18.rssparser.internal.RssKeyword.Companion.isValid
import kotlin.test.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RssKeywordTest {
    @Test
    fun `isValid returns true if it is a valid atom keyword`() {
        val entries = RssKeyword.entries
        for (entry in entries) {
            assertTrue(isValid(entry.value))
        }
    }

    @Test
    fun `isValid returns false if it not a valid rdf keyword`() {
        assertFalse(isValid("NOT_VALID_RDF_KEYWORD"))
    }
}
