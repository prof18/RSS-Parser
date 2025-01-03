package com.prof18.rssparser.internal

internal sealed class RdfKeyword(val value: String) {
    data object Rdf : RdfKeyword("rdf:RDF")
    data object Title : RdfKeyword("title")
    data object Description : RdfKeyword("description")
    data object Link : RdfKeyword("link")
    data object DcDate : RdfKeyword("dc:date")
    data object Channel : RdfKeyword("channel") {
        data object Image : RdfKeyword("image") {
            data object Resource : RdfKeyword("rdf:resource")
        }

        data object UpdatePeriod : RdfKeyword("sy:updatePeriod")
    }

    data object Item : RdfKeyword("item") {
        data object DcCreator : RdfKeyword("dc:creator")
        data object DcSubject : RdfKeyword("dc:subject")
    }
}
