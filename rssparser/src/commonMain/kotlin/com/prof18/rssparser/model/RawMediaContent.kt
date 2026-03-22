package com.prof18.rssparser.model

public data class RawMediaContent(
    val url: String?,
    val type: String?,
    val medium: String?,
) {
    internal data class Builder(
        private var url: String? = null,
        private var type: String? = null,
        private var medium: String? = null,
    ) {
        fun url(url: String?) = apply { this.url = url }
        fun type(type: String?) = apply { this.type = type }
        fun medium(medium: String?) = apply { this.medium = medium }

        fun build(): RawMediaContent? {
            if (
                url.isNullOrBlank() &&
                type.isNullOrBlank() &&
                medium.isNullOrBlank()
            ) {
                return null
            }
            return RawMediaContent(
                url = url,
                type = type,
                medium = medium,
            )
        }
    }
}
