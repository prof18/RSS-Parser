package com.prof18.rssparser.atom

import com.prof18.rssparser.XmlParserTestExecutor
import com.prof18.rssparser.model.RssChannel
import com.prof18.rssparser.model.RssItem
import com.prof18.rssparser.parseFeed
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class XmlParserAtomIdeaRicirTest : XmlParserTestExecutor() {

    private val expectedChannel = RssChannel(
        title = "IDEA - scintille informatiche",
        link = "https://idea.ricir.net/",
        description = "Un piccolo blog dove condivido qualche IDEA   per facilitare il mio, e vostro, uso delle tecnologie informatiche, e magari (se avrò tempo) qualche spunto di riflessione.",
        image = null,
        lastBuildDate = "2025-12-31T18:28:09+01:00",
        updatePeriod = null,
        itunesChannelData = null,
        youtubeChannelData = null,
        items = listOf(
            RssItem(
                guid = "https://idea.ricir.net/Ricevere-notifiche-per-nuovi-articoli",
                title = "Avvisi automatici: scopri i nuovi articoli in tempo reale",
                author = "Riccardo",
                link = "https://idea.ricir.net/Ricevere-notifiche-per-nuovi-articoli/",
                pubDate = "2025-12-31T00:00:00+01:00",
                description = "Dopo tanti mesi di pausa, torno ad aggiornare il sito con un articolo dedicato proprio all’uscita di nuovi contenuti. Ti spiego qui sotto come puoi restare aggiornato su nuovi articoli dei siti che ti interessano, usando una semplice app, e - solo per il mio sito - un canale Telegram. Per il mio sito Come avrai visto, non ti chiedo l’indirizzo e-mail per avvisarti dei nuovi articoli: desidero però che tu possa seguire le nuove uscite con facilità, e per questo ti propongo due soluzioni semplici. Prima soluzione - canale La soluzione più veloce, se hai un utente su Telegram, è iscriverti al mio canale. Basta che clicchi qua a fianco: Canale Telegram Il mio canale si aggiungerà ai canali che segui. E riceverai una notifica di Telegram quando pubblicherò un articolo. Seconda soluzione - App FeedFlow La seconda soluzione è l’app FeedFlow. La trovi su Google Play (se invece hai un iPhone, su Apple Store) cercando “Feedflow”: Una volta installata, torna qui e clicca qui a fianco Clicca a lungo (su cellulare) Segui poi questi passaggi sulla App (vedi figura sotto): clicca Condividi Link clicca su FeedFlow (può essere che devi scorrere a destra per trovarlo) ti arriva un breve messaggio: Feed IDEA - scintille informatiche aggiunto → Fatto! Ma cosa vuol dire Feed? Un Feed non è altro che un collegamento alla lista degli articoli di un sito.",
                content = "content",
                image = "https://idea.ricir.net/assets/ComeSeguireAggiornamenti-social.png",
                audio = null,
                video = null,
                sourceName = null,
                sourceUrl = null,
                categories = emptyList(),
                commentsUrl = null,
                itunesItemData = null,
                youtubeItemData = null,
                rawEnclosure = null,
            )
        ),
    )

    @Test
    fun channelIsParsedCorrectly() = runTest {
        val channel = parseFeed("atom-feed-idea-ricir.xml")
        assertEquals(expectedChannel, channel)
    }
}
