package com.prof18.rssparser.atom

import com.prof18.rssparser.XmlParserTestExecutor
import com.prof18.rssparser.model.RssChannel
import com.prof18.rssparser.model.RssImage
import com.prof18.rssparser.model.RssItem
import com.prof18.rssparser.parseFeed
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class XmlParserAtomRedditTest : XmlParserTestExecutor() {

    val expectedChannel = RssChannel(
        title = "Minecraft on reddit",
        link = "https://www.reddit.com/r/minecraft",
        description = "Minecraft community on Reddit",
        image = RssImage(
            title = null,
            url = "https://www.redditstatic.com/icon.png/",
            link = null,
            description = null,
        ),
        lastBuildDate = "2025-06-22T10:10:32+00:00",
        updatePeriod = null,
        itunesChannelData = null,
        youtubeChannelData = null,
        items = listOf(
            RssItem(
                guid = "https://www.redditstatic.com/icon.png/",
                title = "Vibrant Visuals - Troubleshooting",
                author = "/u/Mlakuss",
                link = "https://www.reddit.com/r/Minecraft/comments/1ldu6hr/vibrant_visuals_troubleshooting/",
                pubDate = "2025-06-17T18:02:33+00:00",
                description = null,
                content = "<table> <tr><td> <a href=\"https://www.reddit.com/r/Minecraft/comments/1ldu6hr/vibrant_visuals_troubleshooting/\"> <img src=\"https://external-preview.redd.it/bqEhLTi2rlzVDgMqeUu4xWmTgN16qIfQKQ3BsX5ozbQ.jpeg?width=640&amp;crop=smart&amp;auto=webp&amp;s=a8773a88f096deba46da1d25c1f80f0d5330bb04\" alt=\"Vibrant Visuals - Troubleshooting\" title=\"Vibrant Visuals - Troubleshooting\" /> </a> </td><td> <!-- SC_OFF --><div class=\"md\"><p>Hello,</p> <p>As we are seeing a huge number of identical threads, here are some answers to the question &quot;Why isn&#39;t Vibrant Visual working?&quot;</p> <ul> <li><strong>It&#39;s on Bedrock only.</strong></li> </ul> <p>If you are playing on computer, you may be using the Java version. Java does not have Vibrant Visuals, but will have it later.</p> <ul> <li><strong>It&#39;s only on some supported devices</strong></li> </ul> <p>Shaders require some specific hardware. The <a href=\"https://minecraft.wiki/w/Vibrant_Visuals#Availability\">list of supported devices can be found here</a>. In short, it is not on Nintendo Switch (1 &amp; 2), ChromeOS, Amazon Fire and old devices.</p> <p>For Windows, to know if your computer has DirectX 12 (required), <a href=\"https://support.microsoft.com/en-us/windows/which-version-of-directx-is-on-your-pc-3c688307-6c44-2ff5-9df7-d90d92bf5239\">see this</a>. It it does and still have troubles, <a href=\"https://www.reddit.com/r/Minecraft/comments/1ldu6hr/comment/myu323c/\">check this</a>.</p> <ul> <li><strong>I can&#39;t enable/disable Vibrant Visuals, option is grayed out</strong></li> </ul> <p>Try leaving your world and change the video settings from the main menu (not in world options). It&#39;s in the Video Settings</p> <p><a href=\"https://preview.redd.it/8bl85u4r7j7f1.png?width=3836&amp;format=png&amp;auto=webp&amp;s=86586f3583b123772e6c06fbedb066f57b461774\">Turn on \\&quot;Allow In-Game Graphics Mode Switching\\&quot; to be able to change this setting when you are playing in your world</a></p> <ul> <li><strong>It requires a supported resource pack (vanilla is supported)</strong></li> </ul> <p>If you have a texture pack, try turning it off. You may also need to disable some addons if they require a texture pack. Servers/Realms sometimes use resource pack too that can prevent Vibrant Visuals being turned on.</p> <ul> <li><strong>Improve Performance</strong></li> </ul> <p>Reducing the Volumetric Fog setting can significantly increase the performance if Vibrant Visuals are tanking your FPS too much.</p> <ul> <li>Getting more help</li> </ul> <p>If you need further help, make sure to indicate what device you are using (exact model of the phone, model of the graphic card, version of your graphic drivers, OS version...).</p> <p>The wiki also has a <a href=\"https://minecraft.wiki/w/Vibrant_Visuals#Issues\">list of known issues</a> related to Vibrant Visuals.</p> </div><!-- SC_ON --> &#32; submitted by &#32; <a href=\"https://www.reddit.com/user/Mlakuss\"> /u/Mlakuss </a> <br/> <span><a href=\"https://www.reddit.com/r/Minecraft/comments/1ldu6hr/vibrant_visuals_troubleshooting/\">[link]</a></span> &#32; <span><a href=\"https://www.reddit.com/r/Minecraft/comments/1ldu6hr/vibrant_visuals_troubleshooting/\">[comments]</a></span> </td></tr></table>",
                image = "https://external-preview.redd.it/bqEhLTi2rlzVDgMqeUu4xWmTgN16qIfQKQ3BsX5ozbQ.jpeg?width=640&crop=smart&auto=webp&s=a8773a88f096deba46da1d25c1f80f0d5330bb04",
                audio = null,
                video = null,
                sourceName = null,
                sourceUrl = null,
                categories = listOf("Minecraft"),
                commentsUrl = null,
                itunesItemData = null,
                youtubeItemData = null,
                rawEnclosure = null,
            )
        )
    )

    @Test
    fun channelIsParsedCorrectly() = runTest {
        val channel = parseFeed("atom-feed-reddit.xml")
        assertEquals(expectedChannel, channel)
    }
}
