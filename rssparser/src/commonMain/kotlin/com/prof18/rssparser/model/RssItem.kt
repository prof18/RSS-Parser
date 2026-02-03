/*
*   Copyright 2016 Marco Gomiero
*
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
*
*/

package com.prof18.rssparser.model

public data class RssItem(
    val guid: String?,
    val title: String?,
    val author: String?,
    val link: String?,
    val pubDate: String?,
    val description: String?,
    val content: String?,
    val image: String?,
    val audio: String?,
    val video: String?,
    val sourceName: String?,
    val sourceUrl: String?,
    val categories: List<String>,
    val itunesItemData: ItunesItemData?,
    val commentsUrl: String?,
    val youtubeItemData: YoutubeItemData?,
    val rawEnclosure: RawEnclosure?,
) {
    internal data class Builder(
        private var guid: String? = null,
        private var title: String? = null,
        private var author: String? = null,
        private var link: String? = null,
        private var pubDate: String? = null,
        private var description: String? = null,
        private var content: String? = null,
        private var image: String? = null,
        private var audio: String? = null,
        private var video: String? = null,
        private var sourceName: String? = null,
        private var sourceUrl: String? = null,
        private val categories: MutableList<String> = mutableListOf(),
        private var itunesItemData: ItunesItemData? = null,
        private var commentUrl: String? = null,
        private var youtubeItemData: YoutubeItemData? = null,
        private var rawEnclosure: RawEnclosure? = null,
    ) {
        private var linkPriority: Int = LINK_PRIORITY_NONE

        fun guid(guid: String?) = apply { this.guid = guid }
        fun title(title: String?) = apply { this.title = title }
        fun author(author: String?) = apply { this.author = author }

        fun link(link: String?, rel: String? = null) = apply {
            val candidate = link?.takeIf { it.isNotBlank() } ?: return@apply
            val priority = linkPriorityFor(rel)
            if (priority > linkPriority) {
                this.link = candidate
                linkPriority = priority
            }
        }
        fun pubDate(pubDate: String?) = apply {
            this.pubDate = pubDate
        }

        fun pubDateIfNull(pubDate: String?) = apply {
            if (this.pubDate == null) {
                this.pubDate = pubDate
            }
        }

        fun description(description: String?) = apply { this.description = description }
        fun content(content: String?) = apply { this.content = content }
        fun image(image: String?) = apply {
            if (this.image == null && image?.isNotEmpty() == true) {
                this.image = image
            }
        }

        fun audio(audio: String?) = apply { this.audio = audio }
        fun audioIfNull(audio: String?) = apply {
            if (this.audio == null) {
                this.audio = audio
            }
        }

        fun video(video: String?) = apply { this.video = video }
        fun videoIfNull(video: String?) = apply {
            if (this.video == null) {
                this.video = video
            }
        }

        fun sourceName(sourceName: String?) = apply { this.sourceName = sourceName }
        fun sourceUrl(sourceUrl: String?) = apply { this.sourceUrl = sourceUrl }
        fun addCategory(category: String?) = apply {
            if (category != null) {
                this.categories.add(category)
            }
        }

        fun itunesArticleData(itunesItemData: ItunesItemData?) =
            apply { this.itunesItemData = itunesItemData }

        fun commentUrl(url: String?) = apply { this.commentUrl = url }

        fun youtubeItemData(youtubeItemData: YoutubeItemData?) = apply { this.youtubeItemData = youtubeItemData }

        fun rawEnclosure(rawEnclosure: RawEnclosure?) = apply { this.rawEnclosure = rawEnclosure }

        fun build(): RssItem? {
            if (
                guid.isNullOrBlank() &&
                title.isNullOrBlank() &&
                author.isNullOrBlank() &&
                link.isNullOrBlank() &&
                pubDate.isNullOrBlank() &&
                description.isNullOrBlank() &&
                content.isNullOrBlank() &&
                image.isNullOrBlank() &&
                audio.isNullOrBlank() &&
                video.isNullOrBlank() &&
                sourceName.isNullOrBlank() &&
                sourceUrl.isNullOrBlank() &&
                categories.isEmpty() &&
                itunesItemData == null &&
                commentUrl.isNullOrBlank() &&
                youtubeItemData == null &&
                rawEnclosure == null
            ) {
                return null
            }

            return RssItem(
                guid = guid,
                title = title,
                author = author,
                link = link,
                pubDate = pubDate,
                description = description,
                content = content,
                image = image,
                audio = audio,
                video = video,
                sourceName = sourceName,
                sourceUrl = sourceUrl,
                categories = categories,
                itunesItemData = itunesItemData,
                commentsUrl = commentUrl,
                youtubeItemData = youtubeItemData,
                rawEnclosure = rawEnclosure,
            )
        }
    }

    private companion object {
        private const val LINK_PRIORITY_NONE = -1
        private const val LINK_PRIORITY_RELATED = 0
        private const val LINK_PRIORITY_OTHER = 1
        private const val LINK_PRIORITY_MISSING_REL = 2
        private const val LINK_PRIORITY_ALTERNATE = 3

        private fun linkPriorityFor(rel: String?): Int {
            val normalized = rel?.lowercase()
            return when {
                normalized == "alternate" -> LINK_PRIORITY_ALTERNATE
                normalized.isNullOrBlank() -> LINK_PRIORITY_MISSING_REL
                normalized == "related" -> LINK_PRIORITY_RELATED
                else -> LINK_PRIORITY_OTHER
            }
        }
    }
}
