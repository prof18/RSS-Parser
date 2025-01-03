package com.prof18.rssparser.model

public data class YoutubeChannelData(
    val channelId: String?,
) {
    internal data class Builder(
        private var channelId: String? = null,
    ) {
        fun channelId(channelId: String?) = apply { this.channelId = channelId }
        fun build() = YoutubeChannelData(
            channelId = channelId,
        )
    }
}

public data class YoutubeItemData(
    val videoId: String?,
    val title: String?,
    val videoUrl: String?,
    val thumbnailUrl: String?,
    val description: String?,
    val viewsCount: Int?,
    val likesCount: Int?,
) {
    internal data class Builder(
        private var videoId: String? = null,
        private var title: String? = null,
        private var videoUrl: String? = null,
        private var thumbnailUrl: String? = null,
        private var description: String? = null,
        private var viewsCount: Int? = null,
        private var likesCount: Int? = null,
    ) {
        fun videoId(videoId: String?) = apply { this.videoId = videoId }
        fun title(title: String?) = apply { this.title = title }
        fun videoUrl(videoUrl: String?) = apply { this.videoUrl = videoUrl }
        fun thumbnailUrl(thumbnailUrl: String?) = apply { this.thumbnailUrl = thumbnailUrl }
        fun description(description: String?) = apply { this.description = description }
        fun viewsCount(viewsCount: Int?) = apply { this.viewsCount = viewsCount }
        fun likesCount(likesCount: Int?) = apply { this.likesCount = likesCount }
        fun build() = YoutubeItemData(
            videoId = videoId,
            title = title,
            videoUrl = videoUrl,
            thumbnailUrl = thumbnailUrl,
            description = description,
            viewsCount = viewsCount,
            likesCount = likesCount,
        )
    }
}
