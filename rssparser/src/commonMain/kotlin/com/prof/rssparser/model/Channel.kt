package com.prof.rssparser.model

// TODO: how to handle serialization?
data class Channel(
    val title: String?,
    val link: String?,
    val description: String?,
    val image: Image?,
    val lastBuildDate: String?,
    val updatePeriod: String?,
    val articles: List<Article>,
    val itunesChannelData: ItunesChannelData?
) {

    internal data class Builder(
        private var title: String? = null,
        private var link: String? = null,
        private var description: String? = null,
        private var image: Image? = null,
        private var lastBuildDate: String? = null,
        private var updatePeriod: String? = null,
        private val articles: MutableList<Article> = mutableListOf(),
        private var itunesChannelData: ItunesChannelData? = null
    ) {
        fun title(title: String?) = apply { this.title = title }
        fun link(link: String?) = apply { this.link = link }
        fun description(description: String?) = apply { this.description = description }
        fun image(image: Image) = apply { this.image = image }
        fun lastBuildDate(lastBuildDate: String?) = apply { this.lastBuildDate = lastBuildDate }
        fun updatePeriod(updatePeriod: String?) = apply { this.updatePeriod = updatePeriod }
        fun addArticle(article: Article) = apply { this.articles.add(article) }
        fun itunesChannelData(itunesChannelData: ItunesChannelData?) =
            apply { this.itunesChannelData = itunesChannelData }

        fun build() = Channel(
            title,
            link,
            description,
            image,
            lastBuildDate,
            updatePeriod,
            articles,
            itunesChannelData
        )
    }
}