package com.prof18.rssparser.rss

import com.prof18.rssparser.XmlParserTestExecutor
import com.prof18.rssparser.model.ItunesChannelData
import com.prof18.rssparser.model.ItunesItemData
import com.prof18.rssparser.model.RawEnclosure
import com.prof18.rssparser.model.RssChannel
import com.prof18.rssparser.model.RssImage
import com.prof18.rssparser.model.RssItem
import com.prof18.rssparser.model.YoutubeChannelData
import com.prof18.rssparser.model.YoutubeItemData
import com.prof18.rssparser.parseFeed
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class XmlParserFeedThumbTest : XmlParserTestExecutor() {

    private val expectedChannel = RssChannel(
        title = "HDblog.it",
        link = "https://www.hdblog.it",
        description = "HDblog.it",
        image = RssImage(
            title = "HDblog.it",
            url = "https://www.hdblog.it/new_files/templates/theme_darklight/img/logos_wt/logohd.png",
            link = "https://www.hdblog.it",
            description = null
        ),
        lastBuildDate = "Sat, 02 Sep 2023 11:13:55 +0200",
        updatePeriod = "hourly",
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
                title = "Fairphone 5 è ufficiale: equo, modulare e aggiornato fino al 2031 | Anteprima IFA",
                author = "HDblog.it",
                link = "https://www.hdblog.it/smartphone/articoli/n573063/fairphone-5-ufficiale-caratteristiche-prezzo/",
                pubDate = "Sat, 02 Sep 2023 11:05:00 +0200",
                description = "Due fotocamere da 50MP sul retro, 5 anni di garanzia e 10 moduli sostituibili.",
                content = "<a href=\"https://hd2.tudocdn.net/1113460?w=660\"><img src=\"https://hd2.tudocdn.net/1113460?w=660\" width=\"100%\" border=\"0\"/></a><div class=\"textblock\"><p><a href=\"https://www.hdblog.it/fairphone-5/\"><strong>Fairphone 5</strong></a> &egrave; ufficiale, <a href=\"https://www.hdblog.it/smartphone/articoli/n573026/fairphone-5-caratteristiche-immagini-prezzo-quando/\">le indiscrezioni di ieri</a> erano dunque corrette: rispetto <a href=\"https://www.hdblog.it/schede-tecniche/fairphone-4_i5838/\">a Fairphone 4 </a>sono stati fatti molti passi avanti, sia nella qualit&agrave; del prodotto, sia nelle caratteristiche tecniche. Resta una soluzione profondamente <strong>modulare</strong> - anzi, ancor pi&ugrave; modulare se confrontata con la generazione precedente - e dunque facilmente aggiornabile e riparabile.</p>\n" +
                    "\n" +
                    "<p>Lo smartphone &egrave; destinato ad avere un <strong>ciclo di vita lungo</strong>:</p>\n" +
                    "\n" +
                    "<ul>\n" +
                    "\t<li><strong>i moduli sostituibili sono 10 </strong>e non pi&ugrave; 8 (tra cui batteria, slot SIM)</li>\n" +
                    "\t<li><strong>garantiti 5 major update</strong>, supporto software fino al 2031 (ma Fairphone parla anche di un obiettivo 2033)</li>\n" +
                    "</ul>\n" +
                    "\n" +
                    "<p>La lunga vita di Fairphone 5 &egrave; strettamente legata alla scelta fatta dall&#39;azienda olandese per il processore, la soluzione &quot;industriale&quot; di Qualcomm <strong>QCM6490</strong> octa-core. Le prestazioni dovrebbero corrispondere a quelle di <strong>Snapdragon 778G</strong>.<hr/><div class=\"box_highlight_footer\" style=\"margin-top: 6px;\"><div style=\"padding-bottom: 12px;\">Piccolo e super potente? <a href=\"https://www.hdblog.it/prezzi/Apple-iPhone-14-Pro_i5631/\" title=\"Guarda tutte le offerte per Apple iPhone 14 Pro\" style=\"font-weight: 400;\">Apple iPhone 14 Pro</a>, compralo al miglior prezzo da <a href=\"/clickgo/?id=5631&amp;pid=dreyeb125820940913&amp;nid=869291037&amp;area=4&amp;prz=989&amp;hash=f3c5846469fbdf3cfaca2857fdf2231e\" target=\"_blank\" rel=\"nofollow\" style=\"font-weight: 400;\">eBay a <b>989 euro</b></a>. </div></div><h2><a href=\"https://www.hdblog.it/smartphone/articoli/n573063/fairphone-5-ufficiale-caratteristiche-prezzo/\">CLICCA QUI PER CONTINUARE A LEGGERE</a></h2>",
                image = "https://hd2.tudocdn.net/1113460?w=180&h=180&ext=file.jpg",
                categories = listOf("Android", "Fairphone"),
                guid = "http://www.hdblog.it/?p=573063",
                audio = null,
                video = null,
                sourceName = null,
                sourceUrl = null,
                commentsUrl = "https://www.hdblog.it/smartphone/articoli/n573063/fairphone-5-ufficiale-caratteristiche-prezzo/#comments",
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
                    url = null,
                    length = null,
                    type = null,
                ),
            )
        )
    )

    @Test
    fun testXmlParserFeedThumb() = runTest {
        val feedChannel = parseFeed("feed-test-thumb.xml")
        assertEquals(expectedChannel, feedChannel)
    }
}
