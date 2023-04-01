package com.prof.rssparser

internal class ChannelFactory {
    val channelBuilder = Channel.Builder()
    val channelImageBuilder = Image.Builder()
    var articleBuilder = Article.Builder()
    val itunesChannelBuilder = ItunesChannelData.Builder()
    var itunesArticleBuilder = ItunesArticleData.Builder()
    var itunesOwnerBuilder = ItunesOwner.Builder()

    // This image url is extracted from the content and the description of the rss item.
    // It's a fallback just in case there aren't any images in the enclosure tag.
    private var imageUrlFromContent: String? = null

    fun buildArticle() {
        articleBuilder.image(imageUrlFromContent)
        articleBuilder.itunesArticleData(itunesArticleBuilder.build())
        channelBuilder.addArticle(articleBuilder.build())
        // Reset temp data
        imageUrlFromContent = null
        articleBuilder = Article.Builder()
        itunesArticleBuilder = ItunesArticleData.Builder()
    }

    fun buildItunesOwner() {
        itunesChannelBuilder.owner(itunesOwnerBuilder.build())
        itunesOwnerBuilder = ItunesOwner.Builder()
    }

    /**
     * Finds the first img tag and get the src as featured image
     *
     * @param content The content in which to search for the tag
     * @return The url, if there is one
     */
    fun setImageFromContent(content: String?) {
        println(">>>> Trying to get image from content")

        val contentSplit = content
            ?.split("</a>", "/>", "\">", "srcset=", "alt=")
            ?.filter { it.contains("src=") }

        println(">>>> Content Split: ${contentSplit?.size}")
        contentSplit?.forEach {
            println("++ Item")
            println(it)
        }

        val urlRegex = Regex(pattern = "src\\s*=\\s*([\"'])(.+?)([\"'])")

        contentSplit
            ?.firstOrNull()
            ?.let{ urlRegex.find(it) }
            ?.let {
                it.groupValues.getOrNull(2)?.trim()?.let { imgUrl ->
                    imageUrlFromContent = imgUrl
                    println("Final image: $imageUrlFromContent")
                }
            }

//        contentSplit
//            ?.mapNotNull {
//                val urlRegex = Regex(pattern = "src\\s*=\\s*([\"'])(.+?)([\"'])")
//                println(">>> GRoup values")
//                println(">>> Finding stuff on: $it")
//                try {
//                    urlRegex.find(it)?.groupValues?.forEach {
//                        println(">>> GRoup value: $it")
//                    }
//                    urlRegex.find(it)?.groupValues?.getOrNull(2)?.trim()?.let { imgUrl ->
//                        imageUrlFromContent = imgUrl
//                    }
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                    println(">>> Skipping because of exception")
//                }
//            }

    }

    fun setChannelItunesKeywords(keywords: String?) {
        val keywordList = extractItunesKeywords(keywords)
        if (keywordList.isNotEmpty()) {
            itunesChannelBuilder.keywords(keywordList)
        }
    }

    fun setArticleItunesKeywords(keywords: String?) {
        val keywordList = extractItunesKeywords(keywords)
        if (keywordList.isNotEmpty()) {
            itunesArticleBuilder.keywords(keywordList)
        }
    }

    private fun extractItunesKeywords(keywords: String?): List<String> =
        keywords?.split(",")?.mapNotNull {
            it.ifEmpty {
                null
            }
        } ?: emptyList()


    fun build(): Channel {
        val channelImage = channelImageBuilder.build()
        if (channelImage.isNotEmpty()) {
            channelBuilder.image(channelImage)
        }
        channelBuilder.itunesChannelData(itunesChannelBuilder.build())
        return channelBuilder.build()
    }
}
