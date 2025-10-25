package com.prof18.rssparser.rdf

import com.prof18.rssparser.internal.RdfKeyword
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class RdfKeywordTest {

    @Test
    fun fromValue_shouldReturnCorrectKeyword_whenValidValueProvided() {
        // Test all enum values
        assertEquals(RdfKeyword.RDF, RdfKeyword.fromValue("rdf:RDF"))
        assertEquals(RdfKeyword.TITLE, RdfKeyword.fromValue("title"))
        assertEquals(RdfKeyword.DESCRIPTION, RdfKeyword.fromValue("description"))
        assertEquals(RdfKeyword.LINK, RdfKeyword.fromValue("link"))
        assertEquals(RdfKeyword.DC_DATE, RdfKeyword.fromValue("dc:date"))
        assertEquals(RdfKeyword.CHANNEL, RdfKeyword.fromValue("channel"))
        assertEquals(RdfKeyword.CHANNEL_IMAGE, RdfKeyword.fromValue("image"))
        assertEquals(RdfKeyword.CHANNEL_IMAGE_RESOURCE, RdfKeyword.fromValue("rdf:resource"))
        assertEquals(RdfKeyword.CHANNEL_UPDATE_PERIOD, RdfKeyword.fromValue("sy:updatePeriod"))
        assertEquals(RdfKeyword.ITEM, RdfKeyword.fromValue("item"))
        assertEquals(RdfKeyword.ITEM_DC_CREATOR, RdfKeyword.fromValue("dc:creator"))
        assertEquals(RdfKeyword.ITEM_DC_SUBJECT, RdfKeyword.fromValue("dc:subject"))
    }

    @Test
    fun fromValue_shouldBeCaseInsensitive() {
        // Test case insensitivity
        assertEquals(RdfKeyword.TITLE, RdfKeyword.fromValue("TITLE"))
        assertEquals(RdfKeyword.TITLE, RdfKeyword.fromValue("Title"))
        assertEquals(RdfKeyword.DESCRIPTION, RdfKeyword.fromValue("DESCRIPTION"))
        assertEquals(RdfKeyword.DC_DATE, RdfKeyword.fromValue("DC:DATE"))
        assertEquals(RdfKeyword.RDF, RdfKeyword.fromValue("RDF:RDF"))
    }

    @Test
    fun fromValue_shouldReturnNull_whenInvalidValueProvided() {
        assertNull(RdfKeyword.fromValue("invalid"))
        assertNull(RdfKeyword.fromValue("notAKeyword"))
        assertNull(RdfKeyword.fromValue(""))
        assertNull(RdfKeyword.fromValue("random"))
    }

    @Test
    fun isValid_shouldReturnTrue_whenValidValueProvided() {
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
        assertTrue(RdfKeyword.isValid("rdf:RDF"))
    }

    @Test
    fun isValid_shouldReturnFalse_whenInvalidValueProvided() {
        assertFalse(RdfKeyword.isValid("invalid"))
        assertFalse(RdfKeyword.isValid("notAKeyword"))
        assertFalse(RdfKeyword.isValid(""))
        assertFalse(RdfKeyword.isValid("random"))
    }

    @Test
    fun isValid_shouldBeCaseInsensitive() {
        assertTrue(RdfKeyword.isValid("TITLE"))
        assertTrue(RdfKeyword.isValid("Title"))
        assertTrue(RdfKeyword.isValid("DESCRIPTION"))
        assertTrue(RdfKeyword.isValid("DC:DATE"))
        assertTrue(RdfKeyword.isValid("RDF:RDF"))
    }

    @Test
    fun enumValues_shouldContainAllExpectedKeywords() {
        // Verify all expected values exist
        val values = RdfKeyword.entries
        assertEquals(12, values.size)

        // Verify specific keywords exist
        assertNotNull(values.find { it == RdfKeyword.RDF })
        assertNotNull(values.find { it == RdfKeyword.TITLE })
        assertNotNull(values.find { it == RdfKeyword.DESCRIPTION })
        assertNotNull(values.find { it == RdfKeyword.LINK })
        assertNotNull(values.find { it == RdfKeyword.DC_DATE })
        assertNotNull(values.find { it == RdfKeyword.CHANNEL })
        assertNotNull(values.find { it == RdfKeyword.CHANNEL_IMAGE })
        assertNotNull(values.find { it == RdfKeyword.CHANNEL_IMAGE_RESOURCE })
        assertNotNull(values.find { it == RdfKeyword.CHANNEL_UPDATE_PERIOD })
        assertNotNull(values.find { it == RdfKeyword.ITEM })
        assertNotNull(values.find { it == RdfKeyword.ITEM_DC_CREATOR })
        assertNotNull(values.find { it == RdfKeyword.ITEM_DC_SUBJECT })
    }

    @Test
    fun valueProperty_shouldReturnCorrectStringValue() {
        assertEquals("rdf:RDF", RdfKeyword.RDF.value)
        assertEquals("title", RdfKeyword.TITLE.value)
        assertEquals("description", RdfKeyword.DESCRIPTION.value)
        assertEquals("link", RdfKeyword.LINK.value)
        assertEquals("dc:date", RdfKeyword.DC_DATE.value)
        assertEquals("channel", RdfKeyword.CHANNEL.value)
        assertEquals("image", RdfKeyword.CHANNEL_IMAGE.value)
        assertEquals("rdf:resource", RdfKeyword.CHANNEL_IMAGE_RESOURCE.value)
        assertEquals("sy:updatePeriod", RdfKeyword.CHANNEL_UPDATE_PERIOD.value)
        assertEquals("item", RdfKeyword.ITEM.value)
        assertEquals("dc:creator", RdfKeyword.ITEM_DC_CREATOR.value)
        assertEquals("dc:subject", RdfKeyword.ITEM_DC_SUBJECT.value)
    }
}
