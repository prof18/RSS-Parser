package com.prof18.rssparser.rss

import com.prof18.rssparser.XmlParserTestExecutor
import com.prof18.rssparser.model.ItunesChannelData
import com.prof18.rssparser.model.ItunesItemData
import com.prof18.rssparser.model.RawEnclosure
import com.prof18.rssparser.model.RssChannel
import com.prof18.rssparser.model.RssItem
import com.prof18.rssparser.model.YoutubeChannelData
import com.prof18.rssparser.model.YoutubeItemData
import com.prof18.rssparser.parseFeed
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class XmlParserMultipleImageAndVideo : XmlParserTestExecutor() {

    private val expectedChannel = RssChannel(
        title = "Motor.ru",
        link = "https://motor.ru",
        description = "Автомобильное издание Lenta.ru",
        image = null,
        lastBuildDate = null,
        updatePeriod = null,
        itunesChannelData = ItunesChannelData(
            author = null,
            categories = listOf(),
            duration = null,
            explicit = null,
            image = null,
            keywords = listOf(),
            newsFeedUrl = null,
            owner = null,
            subtitle = null,
            summary = null,
            type = null,
        ),
        youtubeChannelData = YoutubeChannelData(channelId = null),
        items = listOf(
            RssItem(
                guid = null,
                title = "Kia сомневается в перспективах Stinger второго поколения",
                author = null,
                link = "https://motor.ru/news/kia-stinger-future-25-04-2020.htm",
                pubDate = "Sat, 25 Apr 2020 10:00:00 +0300",
                description = "Планируемый рестайлинг Kia Stinger окажется весьма масштабным, но появление второго поколения модели находится под большим вопросом.",
                content = "<p>Планируемый рестайлинг Kia Stinger окажется весьма масштабным, но появление второго поколения модели находится под большим вопросом.</p><div data-block=\"feed\" data-layout=\"horizontal\" data-title=\"По теме\"><div data-block=\"feed-item\" data-href=\"https://motor.ru/news/stingervsix-15-04-2020.htm\" data-title=\"Kia Stinger GT станет мощнее\" data-thumb=\"https://motor.ru/thumb/320x180/imgs/2020/04/15/11/3867723/b27756146cb608dee1f325126e7e637d75691104.jpg\" data-thumb-position=\"top\" data-thumb-ratio=\"16x9\"></div></div><p>Stinger позиционировался производителем как гораздо более эмоциональная модель, чем остальные Kia — именно это и должно было привлечь новых для марки покупателей, однако на деле что-то пошло не так. Как <a href=\"https://thekoreancarblog.com/2020/04/20/2nd-gen-kia-stinger-could-get-the-axe/\">сообщает</a>издание Korean Car Blog, продажи лифтбека оказались ниже ожидаемых: к примеру, в прошлом году популярность Stinger на глобальном рынке упала на 18%, а за первый квартал нынешнего года продажи снизились ещё на 21%. Наибольший упадок пришёлся на март, когда лифтбек продавался на 40% хуже по сравнению с мартом 2019 года.</p><iframe src=\"https://motor.media.eagleplatform.com/index/player?player=new&record_id=1410861\" width=\"640\" height=\"480\"></iframe><p>Конечно, отчасти в этом виновата и пандемия, испортившая продажи автомобилей по всему миру, но суровые рыночные реалии таковы, что от убыточных моделей рано или поздно приходиться избавляться. Известно, что модель скоро переживёт рестайлинг, который должен получиться весьма существенным, но при этом Kia сомневается в перспективах <a href=\"https://motor.ru/news/stinger-gt-night-sky-29-02-2020.htm\">Stinger</a>второго поколения. По словам инсайдеров, знакомых с ситуацией, корейцы до сих пор не приняли решения о его судьбе, однако руководство Kia всерьёз подумывает снять лифтбек с производства и не тратить деньги на разработку преемника.</p><a href=\"https://motor.ru/selector/carstobeat.htm\">5 конкурентов мощнейшей модели Kia</a>",
                image = "https://motor.ru/imgs/2020/04/24/22/3883005/7e8815499c86633261719c7154c9fee6acb7d47b.jpg",
                audio = null,
                video = "https://motor.media.eagleplatform.com/index/player?record_id=1410861",
                sourceName = null,
                sourceUrl = null,
                categories = listOf("Новости"),
                commentsUrl = null,
                itunesItemData = ItunesItemData(
                    author = null,
                    duration = null,
                    episode = null,
                    episodeType = null,
                    explicit = null,
                    image = null,
                    keywords = listOf(),
                    subtitle = null,
                    summary = null,
                    season = null,
                ),
                youtubeItemData = YoutubeItemData(
                    videoId = null,
                    title = null,
                    videoUrl = null,
                    thumbnailUrl = null,
                    description = null,
                    viewsCount = null,
                    likesCount = null,
                ),
                rawEnclosure = RawEnclosure(
                    url = "https://motor.media.eagleplatform.com/index/player?record_id=1410861",
                    length = null,
                    type = "video/mp4",
                ),
            )
        )
    )

    @Test
    fun channelIsParsedCorrectly() = runTest {
        val channel = parseFeed("feed-test-multiple-image-and-video.xml")
        assertEquals(expectedChannel, channel)
    }
}
