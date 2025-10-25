package com.prof18.rssparser.internal

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class RdfKeywordTest {

    @Test
    fun testValidKeywords() {
        // Test all valid RDF keywords
        assertTrue(RdfKeyword.isValid("rdf:RDF"))
        assertTrue(RdfKeyword.isValid("title"))
        assertTrue(RdfKeyword.isValid("description"))
        assertTrue(RdfKeyword.isValid("link"))
        assertTrue(RdfKeyword.isValid("dc:date"))
        assertTrue(RdfKeyword.isValid("channel"))
        assertTrue(RdfKeyword.isValid("image"))
        assertTrue(RdfKeyword.isValid("rdf:resource"))
        assertTrue(RdfKeyword.isValid("sy:updatePeriod"))
        assertTrue(RdfKeyword.isValid("item"))
        assertTrue(RdfKeyword.isValid("dc:creator"))
        assertTrue(RdfKeyword.isValid("dc:subject"))
    }

    @Test
    fun testValidKeywordsCaseInsensitive() {
        // Test case insensitive validation
        assertTrue(RdfKeyword.isValid("TITLE"))
        assertTrue(RdfKeyword.isValid("Title"))
        assertTrue(RdfKeyword.isValid("TiTlE"))
        assertTrue(RdfKeyword.isValid("CHANNEL"))
        assertTrue(RdfKeyword.isValid("Channel"))
        assertTrue(RdfKeyword.isValid("DC:DATE"))
        assertTrue(RdfKeyword.isValid("dc:DATE"))
    }

    @Test
    fun testInvalidKeywords() {
        // Test invalid keywords
        assertFalse(RdfKeyword.isValid("invalid"))
        assertFalse(RdfKeyword.isValid("notakeyword"))
        assertFalse(RdfKeyword.isValid(""))
        assertFalse(RdfKeyword.isValid("rss:channel"))
        assertFalse(RdfKeyword.isValid("atom:entry"))
    }

    @Test
    fun testFromValueValidKeywords() {
        // Test fromValue method with valid keywords
        assertEquals(RdfKeyword.RDF, RdfKeyword.fromValue("rdf:RDF"))
        assertEquals(RdfKeyword.TITLE, RdfKeyword.fromValue("title"))
        assertEquals(RdfKeyword.DESCRIPTION, RdfKeyword.fromValue("description"))
        assertEquals(RdfKeyword.LINK, RdfKeyword.fromValue("link"))
        assertEquals(RdfKeyword.DC_DATE, RdfKeyword.fromValue("dc:date"))
        assertEquals(RdfKeyword.CHANNEL, RdfKeyword.fromValue("channel"))
        assertEquals(RdfKeyword.IMAGE, RdfKeyword.fromValue("image"))
        assertEquals(RdfKeyword.RESOURCE, RdfKeyword.fromValue("rdf:resource"))
        assertEquals(RdfKeyword.UPDATE_PERIOD, RdfKeyword.fromValue("sy:updatePeriod"))
        assertEquals(RdfKeyword.ITEM, RdfKeyword.fromValue("item"))
        assertEquals(RdfKeyword.DC_CREATOR, RdfKeyword.fromValue("dc:creator"))
        assertEquals(RdfKeyword.DC_SUBJECT, RdfKeyword.fromValue("dc:subject"))
    }

    @Test
    fun testFromValueCaseInsensitive() {
        // Test fromValue with case insensitive matching
        assertEquals(RdfKeyword.TITLE, RdfKeyword.fromValue("TITLE"))
        assertEquals(RdfKeyword.TITLE, RdfKeyword.fromValue("Title"))
        assertEquals(RdfKeyword.CHANNEL, RdfKeyword.fromValue("CHANNEL"))
        assertEquals(RdfKeyword.DC_DATE, RdfKeyword.fromValue("DC:DATE"))
    }

    @Test
    fun testFromValueInvalidKeywords() {
        // Test fromValue method with invalid keywords
        assertNull(RdfKeyword.fromValue("invalid"))
        assertNull(RdfKeyword.fromValue("notakeyword"))
        assertNull(RdfKeyword.fromValue(""))
        assertNull(RdfKeyword.fromValue("rss:channel"))
    }

    @Test
    fun testEnumValues() {
        // Test that all enum values have correct string values
        assertEquals("rdf:RDF", RdfKeyword.RDF.value)
        assertEquals("title", RdfKeyword.TITLE.value)
        assertEquals("description", RdfKeyword.DESCRIPTION.value)
        assertEquals("link", RdfKeyword.LINK.value)
        assertEquals("dc:date", RdfKeyword.DC_DATE.value)
        assertEquals("channel", RdfKeyword.CHANNEL.value)
        assertEquals("image", RdfKeyword.IMAGE.value)
        assertEquals("rdf:resource", RdfKeyword.RESOURCE.value)
        assertEquals("sy:updatePeriod", RdfKeyword.UPDATE_PERIOD.value)
        assertEquals("item", RdfKeyword.ITEM.value)
        assertEquals("dc:creator", RdfKeyword.DC_CREATOR.value)
        assertEquals("dc:subject", RdfKeyword.DC_SUBJECT.value)
    }

    @Test
    fun testEnumEntriesCount() {
        // Test that all expected keywords are present
        assertEquals(12, RdfKeyword.entries.size)
    }

    @Test
    fun testAllEnumValuesAreValid() {
        // Test that all enum values validate correctly
        RdfKeyword.entries.forEach { keyword ->
            assertTrue(RdfKeyword.isValid(keyword.value))
            assertNotNull(RdfKeyword.fromValue(keyword.value))
        }
    }
}
