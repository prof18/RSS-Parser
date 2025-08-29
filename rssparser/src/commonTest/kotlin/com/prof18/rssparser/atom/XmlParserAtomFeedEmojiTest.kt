package com.prof18.rssparser.atom

import com.prof18.rssparser.XmlParserTestExecutor
import com.prof18.rssparser.model.RssChannel
import com.prof18.rssparser.model.RssItem
import com.prof18.rssparser.parseFeed
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class XmlParserAtomFeedEmojiTest : XmlParserTestExecutor() {

    val expectedChannel = RssChannel(
        title = "Arch Linux Forums / Multimedia and Games",
        link = "https://bbs.archlinux.org/index.php",
        description = null,
        image = null,
        lastBuildDate = "2025-08-29T17:29:55Z",
        updatePeriod = null,
        itunesChannelData = null,
        youtubeChannelData = null,
        items = listOf(
            RssItem(
                guid = "https://bbs.archlinux.org/viewtopic.php?id=307811&action=new",
                title = "[Solved] WINE does not start at all",
                author = "nephtalima",
                link = "https://bbs.archlinux.org/viewtopic.php?id=307811&action=new",
                pubDate = "2025-08-24T23:41:54Z",
                description = "<div class=\"quotebox\"><cite>seth wrote:</cite><blockquote><div><p>It is also an awesome way to trash/invalidate/outdate your local database, so don&#039;t.<br />Also please avoid bloating the thread w/ pointless full quotes (which is about me having to scroll more <img src=\"https://bbs.archlinux.org/img/smilies/tongue.png\" width=\"15\" height=\"15\" alt=\"tongue\" />)</p></div></blockquote></div><p>Ah I see. Thanks for the info!~</p><p>Anyways, back to the topic at hand:</p><div class=\"quotebox\"><cite>seth wrote:</cite><blockquote><div><p>1. is this still wine-stable or actully wine from the repos</p></div></blockquote></div><p>This is still wine-stable. I didn&#039;t want to use the one from the repos (iirc, that is wine-staging) mainly because I didn&#039;t want to completely bork the existing configuration (as I am not the most experienced with WINE). This is the latest version of it as of yesterday.</p><div class=\"quotebox\"><cite>seth wrote:</cite><blockquote><div><p>2. which version of medibang paint pro is this?</p></div></blockquote></div><p>I don&#039;t know. I installed this version on my Windows installation back when I used to daily drive Windows, and I do not know how I can check it when it doesn&#039;t run. AFAIK, this exact same version is the one on the laptop, so I would take a wager that something minor changed from WINE 9 to WINE 10.</p><p>I was busy today but only got back now. I didn&#039;t mention for brevity but I also tried LTSpice XVII and it didn&#039;t work due to similar error yesterday when I first started this thread. Now I have tried to run LTSpice and it works without issue (haven&#039;t tested fully yet, but it launches and that&#039;s more than it was doing yesterday).</p><p>I&#039;ve played around with LTSpice now, and no major issues to report back. Since LTSpice has loaded, I now deduce that now it is an issue with MediBangPaintPro, so I will install a newer version and report back.</p><p>I think this issue is with MediBangPaintPro itself, as I tried installing 29.1, but WINE still freaks out on launching it... I will mark this as solved since this isn&#039;t a WINE issue anymore.</p>",
                content = null,
                image = null,
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
        )
    )

    @Test
    fun channelIsParsedCorrectlyWithSmiliesFiltered() = runTest {
        val channel = parseFeed("atom-feed-emoji.xml")
        assertEquals(expectedChannel, channel)
    }
}