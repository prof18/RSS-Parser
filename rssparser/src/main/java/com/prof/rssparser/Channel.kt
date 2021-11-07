package com.prof.rssparser

import java.io.Serializable

data class Channel(
    val title: String?,
    val link: String?,
    val description: String?,
    val image: Image?,
    val lastBuildDate: String?,
    val updatePeriod: String?,
    val articles: List<Article>
) : Serializable {

    internal data class Builder(
        private var title: String? = null,
        private var link: String? = null,
        private var description: String? = null,
        private var image: Image? = null,
        private var lastBuildDate: String? = null,
        private var updatePeriod: String? = null,
        private var articles: MutableList<Article> = mutableListOf()
    ) {
        fun title(title: String?) = apply { this.title = title }
        fun link(link: String?) = apply { this.link = link }
        fun description(description: String?) = apply { this.description = description }
        fun image(image: Image) = apply { this.image = image }
        fun lastBuildDate(lastBuildDate: String?) = apply { this.lastBuildDate = lastBuildDate }
        fun updatePeriod(updatePeriod: String?) = apply { this.updatePeriod = updatePeriod }
        fun addArticle(article: Article) = apply { this.articles.add(article) }
        fun build() = Channel(
            title,
            link,
            description,
            image,
            lastBuildDate,
            updatePeriod,
            articles
        )
    }
}