package com.prof18.rssparser.model

public data class RssChannel(
    val title: String?,
    val link: String?,
    val description: String?,
    val image: RssImage?,
    val lastBuildDate: String?,
    val updatePeriod: String?,
    val items: List<RssItem>,
    val itunesChannelData: ItunesChannelData?,
    val youtubeChannelData: YoutubeChannelData?,
) {
    internal data class Builder(
        private var title: String? = null,
        private var link: String? = null,
        private var description: String? = null,
        private var image: RssImage? = null,
        private var lastBuildDate: String? = null,
        private var updatePeriod: String? = null,
        private val items: MutableList<RssItem> = mutableListOf(),
        private var itunesChannelData: ItunesChannelData? = null,
        private var youtubeChannelData: YoutubeChannelData? = null,
    ) {
        fun title(title: String?) = apply { this.title = title }
        fun link(link: String?) = apply { this.link = link }
        fun description(description: String?) = apply { this.description = description }
        fun image(image: RssImage?) = apply { this.image = image }
        fun lastBuildDate(lastBuildDate: String?) = apply { this.lastBuildDate = lastBuildDate }
        fun updatePeriod(updatePeriod: String?) = apply { this.updatePeriod = updatePeriod }
        fun addItem(item: RssItem) = apply { this.items.add(item) }
        fun itunesChannelData(itunesChannelData: ItunesChannelData?) =
            apply { this.itunesChannelData = itunesChannelData }
        fun youtubeChannelData(youtubeChannelData: YoutubeChannelData?) =
            apply { this.youtubeChannelData = youtubeChannelData }

        fun build() = RssChannel(
            title = title,
            link = link,
            description = description,
            image = image,
            lastBuildDate = lastBuildDate,
            updatePeriod = updatePeriod,
            items = items,
            itunesChannelData = itunesChannelData,
            youtubeChannelData = youtubeChannelData,
        )
    }
}
