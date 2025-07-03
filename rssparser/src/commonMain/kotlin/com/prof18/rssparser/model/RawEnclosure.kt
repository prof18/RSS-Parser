package com.prof18.rssparser.model

public data class RawEnclosure(
    val url: String?,
    val length: Long?,
    val type: String?,
) {
    internal data class Builder(
        private var url: String? = null,
        private var length: Long? = null,
        private var type: String? = null,
    ) {
        fun url(url: String?) = apply { this.url = url }
        fun length(length: Long?) = apply { this.length = length }
        fun type(type: String?) = apply { this.type = type }

        fun build(): RawEnclosure? {
            if (
                url.isNullOrBlank() &&
                length == null &&
                type.isNullOrBlank()
            ) {
                return null
            }
            return RawEnclosure(
                url = url,
                length = length,
                type = type
            )
        }
    }
}
