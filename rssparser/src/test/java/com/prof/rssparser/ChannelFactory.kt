package com.prof.rssparser

import okhttp3.OkHttpClient
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody

object ChannelFactory {

    fun getOneDayCacheDuration(): Long = 24L * 60L * 60L * 100L

    fun getOkHttpClientForTesting(response: String): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                return@addInterceptor Response.Builder()
                    .protocol(Protocol.HTTP_2)
                    .code(200)
                    .message("")
                    .request(chain.request())
                    .body(response.toResponseBody(null))
                    .build()
            }
            .build()
    }

    fun getErrorOkHttpClientForTesting(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                return@addInterceptor Response.Builder()
                    .protocol(Protocol.HTTP_2)
                    .code(404)
                    .message("")
                    .request(chain.request())
                    .build()
            }
            .build()
    }

    fun getLink(): String = "https://www.androidauthority.com/feed"

    fun getChannel(): Channel {
        val articles = mutableListOf(
            Article(
                guid = "https://www.androidauthority.com/?p=986823",
                title = "Japan’s plans for 5G include 10 billion 14-digit phone numbers",
                author = "Williams Pelegrin",
                link = "https://www.androidauthority.com/japan-phone-numbers-14-digits-5g-986823/",
                pubDate = "Wed, 15 May 2019 20:48:02 +0000",
                description = "Japan expects a huge surge of 5G-enabled devices to hit the market in the future.",
                content = "<p><a href=\"https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g-.jpg\" rel=\"lightbox[986823]\"><img class=\"aligncenter size-large wp-image-959882\" src=\"https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g--840x473.jpg\" alt=\"\" width=\"840\" height=\"473\" srcset=\"https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g--840x472.jpg 840w, https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g--300x170.jpg 300w, https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g--768x432.jpg 768w, https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g--16x9.jpg 16w, https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g--32x18.jpg 32w, https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g--28x16.jpg 28w, https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g--56x32.jpg 56w, https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g--64x36.jpg 64w, https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g--712x400.jpg 712w, https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g--1000x563.jpg 1000w, https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g--1200x675.jpg 1200w, https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g--792x446.jpg 792w, https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g--1280x720.jpg 1280w, https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g--1340x754.jpg 1340w, https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g--770x433.jpg 770w, https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g--356x200.jpg 356w\" sizes=\"(max-width: 840px) 100vw, 840px\" /></a></p>\n" +
                          "<p>With <a href=\"https://www.androidauthority.com/what-is-5g-explained-944868/\">5G</a> promising to bring billions of devices into the fold, Japan hopes to meet that demand by announcing plans to create 10 billion 14-digit phone numbers. As <a href=\"https://www.japantimes.co.jp/news/2019/05/15/national/japan-plans-create-10-billion-14-digit-phone-numbers-5g-era-approaching/#.XNxWwo7Yo3F\"><em>The Japan Times</em></a> reported today, all the new numbers will start with the code &#8220;020.&#8221;</p>\n" +
                          "<p>The plan is to introduce the new numbers by the end of 2021 at the latest. Three of Japan&#8217;s largest mobile operators — <a href=\"https://fave.co/2HxymX8\">NTT Docomo</a>, <a href=\"https://fave.co/2HlI5AQ\">KDDI</a>, and <a href=\"https://fave.co/2VCVCwI\">SoftBank</a> — agreed to the idea and will update their systems to accommodate the 10 billion new numbers. Japan&#8217;s communications ministry plans to reward carriers for ahead-of-schedule support.</p>\n" +
                          "<p>Japan expects to run out of 11-digit numbers as early as fiscal 2022, which starts April 1 of that year for the Japanese government.</p>\n" +
                          "<div class=\"aa_srma_container shortcodes_wrapper right\"><div class=\"shortcodes-header\"><div class=\"shortcodes-title\">Editor's Pick</div></div><div class=\"related_articles_wrapper\"><div class=\"related_article_item\"><a class=\"overlay-link\" href=\"https://www.androidauthority.com/5g-device-price-959959/\" title=\"Here’s what every 5G phone will cost you\"></a><h4 class=\"ra-title\">Here’s what every 5G phone will cost you</h4><div class=\"ra-excerpt\">\n" +
                          "\n" +
                          "If you want to be one of the first consumers to get your hands on a 5G phone, you’ll have to pay up. A lot of manufacturers including Samsung, LG, and Xiaomi have announced 5G …</div></div></div></div>\n" +
                          "<p>The 14 billion new numbers sound excessive, but keep in mind that <a href=\"https://www.androidauthority.com/what-is-the-internet-of-things-592491/\">Internet of Things</a> (IoT) devices have been using 11-digit numbers that start with &#8220;020&#8221; since January 2017. Also, carriers have already taken 32.6 million of the 11-digit 020 numbers that were allocated two years ago.</p>\n" +
                          "<p>14-digit numbers could prove troublesome for the masses, since there will be more numbers to remember. That issue could be exacerbated among Japan&#8217;s aging population. It&#8217;s estimated that people older than 60 <a href=\"http://www.stat.go.jp/english/data/nenkan/1431-02.htm\">make up 33 percent</a> of the Japanese population.</p>\n" +
                          "<p>Even so, we could see similar announcements in other countries that plan to deploy 5G networks and are running low on allocated phone numbers. It&#8217;s not as much of an issue in the U.S., where a <a href=\"http://www.nationalnanpa.com/pdf/NRUF/April_2014_NANP_Exhaust_Analysis.pdf\">North American Numbering Plan exhaust analysis</a> estimates that the country&#8217;s current numbering system is sufficient until 2044.</p>\n" +
                          "<p style=\"text-align: center;\"><strong>NEXT:</strong> <a href=\"https://www.androidauthority.com/vodafone-5g-launch-986496/\">Vodafone issues 5G launch details: Here&#8217;s what you need to know</a></p>\n" +
                          "<div class=\"container-fluid builder-html\"><div class=\"container-inner\"><div class=\"affiliate-text\"><i>Affiliate disclosure: We may receive compensation in connection with your purchase of products via links on this page. The compensation received will never influence the content, topics or posts made in this blog. See our <a href=\"https://www.androidauthority.com/external-links/\">disclosure policy</a> for more details.</i></div></div></div>",
                image = "https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g--500x260.jpg",
                audio = null,
                video = null,
                sourceName = null,
                sourceUrl = null,
                categories = mutableListOf("News", "5G", "Japan"),
                itunesArticleData = null
            ),
            Article(
                guid = "https://www.androidauthority.com/?p=986861",
                title = "Report details just what Samsung is doing to update the Galaxy Fold",
                author = "C. Scott Brown",
                link = "https://www.androidauthority.com/samsung-galaxy-fold-updates-986861/",
                pubDate = "Wed, 15 May 2019 20:10:12 +0000",
                description = "Overall, many of the changes are expected, but it's nice to hear that Samsung could be implementing them.",
                content = "<p><img class=\"aligncenter size-large wp-image-977086\" src=\"https://cdn57.androidauthority.net/wp-content/uploads/2019/04/Samsung-Galaxy-Fold-tablet-mode-on-chair-840x473.jpg\" alt=\"Samsung Galaxy Fold tablet mode on chair\" width=\"840\" height=\"473\" srcset=\"https://cdn57.androidauthority.net/wp-content/uploads/2019/04/Samsung-Galaxy-Fold-tablet-mode-on-chair-840x472.jpg 840w, https://cdn57.androidauthority.net/wp-content/uploads/2019/04/Samsung-Galaxy-Fold-tablet-mode-on-chair-300x170.jpg 300w, https://cdn57.androidauthority.net/wp-content/uploads/2019/04/Samsung-Galaxy-Fold-tablet-mode-on-chair-768x432.jpg 768w, https://cdn57.androidauthority.net/wp-content/uploads/2019/04/Samsung-Galaxy-Fold-tablet-mode-on-chair-16x9.jpg 16w, https://cdn57.androidauthority.net/wp-content/uploads/2019/04/Samsung-Galaxy-Fold-tablet-mode-on-chair-32x18.jpg 32w, https://cdn57.androidauthority.net/wp-content/uploads/2019/04/Samsung-Galaxy-Fold-tablet-mode-on-chair-28x16.jpg 28w, https://cdn57.androidauthority.net/wp-content/uploads/2019/04/Samsung-Galaxy-Fold-tablet-mode-on-chair-56x32.jpg 56w, https://cdn57.androidauthority.net/wp-content/uploads/2019/04/Samsung-Galaxy-Fold-tablet-mode-on-chair-64x36.jpg 64w, https://cdn57.androidauthority.net/wp-content/uploads/2019/04/Samsung-Galaxy-Fold-tablet-mode-on-chair-712x400.jpg 712w, https://cdn57.androidauthority.net/wp-content/uploads/2019/04/Samsung-Galaxy-Fold-tablet-mode-on-chair-1000x563.jpg 1000w, https://cdn57.androidauthority.net/wp-content/uploads/2019/04/Samsung-Galaxy-Fold-tablet-mode-on-chair-1200x675.jpg 1200w, https://cdn57.androidauthority.net/wp-content/uploads/2019/04/Samsung-Galaxy-Fold-tablet-mode-on-chair-792x446.jpg 792w, https://cdn57.androidauthority.net/wp-content/uploads/2019/04/Samsung-Galaxy-Fold-tablet-mode-on-chair-1280x720.jpg 1280w, https://cdn57.androidauthority.net/wp-content/uploads/2019/04/Samsung-Galaxy-Fold-tablet-mode-on-chair-1340x754.jpg 1340w, https://cdn57.androidauthority.net/wp-content/uploads/2019/04/Samsung-Galaxy-Fold-tablet-mode-on-chair-770x433.jpg 770w, https://cdn57.androidauthority.net/wp-content/uploads/2019/04/Samsung-Galaxy-Fold-tablet-mode-on-chair-355x200.jpg 355w\" sizes=\"(max-width: 840px) 100vw, 840px\" /></p>\n" +
                          "<p>After early review units <a href=\"https://www.androidauthority.com/samsung-galaxy-fold-issues-display-problems-977233/\">started breaking</a>, Samsung postponed the release of its first foldable device, the <a href=\"https://www.androidauthority.com/samsung-galaxy-fold-review-977100/\">Samsung Galaxy Fold</a>, and promised to work on addressing the breakage issues. Now we have a report out of South Korea from <a href=\"https://www.yna.co.kr/view/AKR20190515026700017\"><em>Yonhap News</em></a> (via <a href=\"https://www.sammobile.com/2019/05/15/improvements-samsung-made-galaxy-fold/\"><em>SamMobile</em></a>) that gives us our first glimpse into what Samsung could be doing to address these problems.</p>\n" +
                          "<p>The two biggest issues with the Samsung Galaxy Fold were the protective film covering the inner screen and the gap between the display and the device&#8217;s folding hinge. Reviewers mistakenly pulled off the protective layer which caused some devices to malfunction, and the hinge gap allows for dust and other particles to contaminate the very sensitive OLED display.</p>\n" +
                          "<p>For the protective layer problem, Samsung is reportedly integrating the edges of the film into the body of the device itself. If true, this would make it more difficult for consumers to accidentally pull the protective layer off.</p>\n" +
                          "<div class=\"aa_srma_container shortcodes_wrapper right\"><div class=\"shortcodes-header\"><div class=\"shortcodes-title\">Editor's Pick</div></div><div class=\"related_articles_wrapper\"><div class=\"related_article_item\"><a class=\"overlay-link\" href=\"https://www.androidauthority.com/samsung-galaxy-fold-release-decision-984575/\" title=\"Samsung CEO: Galaxy Fold release decision coming &#8216;in a couple of days&#8217;\"></a><h4 class=\"ra-title\">Samsung CEO: Galaxy Fold release decision coming &#8216;in a couple of days&#8217;</h4><div class=\"ra-excerpt\">\n" +
                          "\n" +
                          "According to The Korea Herald, the new United States release date for the Samsung Galaxy Fold will be determined in the next few days.\n" +
                          "\n" +
                          "This information comes directly from Samsung CEO Koh Dong-jin, more commonly known as …</div></div></div></div>\n" +
                          "<p>As far as the hinge goes, the report merely suggests Samsung is working to reduce the size of the gap. This could mean that Samsung is eliminating the gap, or it could mean that it will simply be smaller and thus less prone to collecting debris.</p>\n" +
                          "<p>Finally, there will also likely be more warnings and safety statements with the Samsung Galaxy Fold to educate consumers on how this new type of device needs to be handled.</p>\n" +
                          "<p>We&#8217;ve reached out to <a href=\"https://www.androidauthority.com/best-samsung-phones-771831/\">Samsung</a> about confirming or denying these reports and will update the article if we hear back.</p>\n" +
                          "<p>In the meantime, what do you think about this potential progress? Would these fixes make you more comfortable with buying a Fold? Or has the damage already been done? Let us know in the comments.</p>\n" +
                          "<p style=\"text-align: center;\"><strong>NEXT: <a href=\"https://www.androidauthority.com/samsung-galaxy-home-release-date-985015/\">Samsung Galaxy Home confirmed to launch by the end of June 2019</a></strong></p>",
                image = "https://cdn57.androidauthority.net/wp-content/uploads/2019/04/Samsung-Galaxy-Fold-tablet-mode-on-chair-500x260.jpg",
                audio = null,
                video = null,
                sourceName = null,
                sourceUrl = null,
                categories = mutableListOf("News", "Samsung", "Samsung Galaxy Fold"),
                itunesArticleData = null
            )
        )

        return Channel(
            title = "Android Authority",
            link = "https://www.androidauthority.com",
            description = "Android News, Reviews, How To",
            image = null,
            lastBuildDate = "Wed, 15 May 2019 20:48:02 +0000",
            updatePeriod = "hourly",
            articles = articles,
            itunesChannelData = null
        )
    }

    fun getFeedString(): String {
        return """
            <?xml version="1.0" encoding="UTF-8"?>
            <rss xmlns:atom="http://www.w3.org/2005/Atom" xmlns:content="http://purl.org/rss/1.0/modules/content/"
                xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:media="https://search.yahoo.com/mrss/"
                xmlns:slash="http://purl.org/rss/1.0/modules/slash/" xmlns:sy="http://purl.org/rss/1.0/modules/syndication/"
                xmlns:webfeeds="http://webfeeds.org/rss/1.0"
                xmlns:wfw="http://wellformedweb.org/CommentAPI/" version="2.0">

                <channel>
                    <title>Android Authority</title>
                    <atom:link href="https://www.androidauthority.com/feed/" rel="self"
                        type="application/rss+xml" />
                    <link>https://www.androidauthority.com</link>
                    <description>Android News, Reviews, How To</description>
                    <lastBuildDate>
                        Wed, 15 May 2019 20:48:02 +0000
                    </lastBuildDate>
                    <language>en-US</language>
                    <sy:updatePeriod>
                        hourly
                    </sy:updatePeriod>
                    <sy:updateFrequency>
                        1
                    </sy:updateFrequency>
                    <generator>https://wordpress.org/?v=5.1.1</generator>
                    <webfeeds:accentColor>54cc39</webfeeds:accentColor>
                    <webfeeds:related layout="card" target="browser" />
                    <webfeeds:analytics engine="GoogleAnalytics" id="UA-20765087-1" />
                    <webfeeds:icon>
                        https://www.androidauthority.com/wp-content/uploads/feed/aa-mascot-192x192.png?v=1.0.2
                    </webfeeds:icon>
                    <webfeeds:logo>
                        https://www.androidauthority.com/wp-content/uploads/feed/aa_icon_feed.svg?v=1.0.2
                    </webfeeds:logo>
                    <webfeeds:cover
                        image="https://www.androidauthority.com/wp-content/uploads/feed/aboutus_top_imagev2.jpg?v=1.0.2" />
                    <item>
                        <title>Japan&#8217;s plans for 5G include 10 billion 14-digit phone numbers</title>
                        <link>https://www.androidauthority.com/japan-phone-numbers-14-digits-5g-986823/</link>
                        <comments>
                            https://www.androidauthority.com/japan-phone-numbers-14-digits-5g-986823/#respond
                        </comments>
                        <pubDate>Wed, 15 May 2019 20:48:02 +0000</pubDate>
                        <dc:creator><![CDATA[Williams Pelegrin]]></dc:creator>
                        <category><![CDATA[News]]></category>
                        <category><![CDATA[5G]]></category>
                        <category><![CDATA[Japan]]></category>

                        <guid isPermaLink="false">https://www.androidauthority.com/?p=986823</guid>
                        <description>
                            <![CDATA[Japan expects a huge surge of 5G-enabled devices to hit the market in the future.]]></description>
                        <media:content name="AndroidAuthority"
                            medium="image"
                            url="https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g--500x260.jpg" />
                        <content:encoded><![CDATA[<p><a href="https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g-.jpg" rel="lightbox[986823]"><img class="aligncenter size-large wp-image-959882" src="https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g--840x473.jpg" alt="" width="840" height="473" srcset="https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g--840x472.jpg 840w, https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g--300x170.jpg 300w, https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g--768x432.jpg 768w, https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g--16x9.jpg 16w, https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g--32x18.jpg 32w, https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g--28x16.jpg 28w, https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g--56x32.jpg 56w, https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g--64x36.jpg 64w, https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g--712x400.jpg 712w, https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g--1000x563.jpg 1000w, https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g--1200x675.jpg 1200w, https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g--792x446.jpg 792w, https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g--1280x720.jpg 1280w, https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g--1340x754.jpg 1340w, https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g--770x433.jpg 770w, https://cdn57.androidauthority.net/wp-content/uploads/2019/02/Whats-next-with-5g--356x200.jpg 356w" sizes="(max-width: 840px) 100vw, 840px" /></a></p>
            <p>With <a href="https://www.androidauthority.com/what-is-5g-explained-944868/">5G</a> promising to bring billions of devices into the fold, Japan hopes to meet that demand by announcing plans to create 10 billion 14-digit phone numbers. As <a href="https://www.japantimes.co.jp/news/2019/05/15/national/japan-plans-create-10-billion-14-digit-phone-numbers-5g-era-approaching/#.XNxWwo7Yo3F"><em>The Japan Times</em></a> reported today, all the new numbers will start with the code &#8220;020.&#8221;</p>
            <p>The plan is to introduce the new numbers by the end of 2021 at the latest. Three of Japan&#8217;s largest mobile operators — <a href="https://fave.co/2HxymX8">NTT Docomo</a>, <a href="https://fave.co/2HlI5AQ">KDDI</a>, and <a href="https://fave.co/2VCVCwI">SoftBank</a> — agreed to the idea and will update their systems to accommodate the 10 billion new numbers. Japan&#8217;s communications ministry plans to reward carriers for ahead-of-schedule support.</p>
            <p>Japan expects to run out of 11-digit numbers as early as fiscal 2022, which starts April 1 of that year for the Japanese government.</p>
            <div class="aa_srma_container shortcodes_wrapper right"><div class="shortcodes-header"><div class="shortcodes-title">Editor's Pick</div></div><div class="related_articles_wrapper"><div class="related_article_item"><a class="overlay-link" href="https://www.androidauthority.com/5g-device-price-959959/" title="Here’s what every 5G phone will cost you"></a><h4 class="ra-title">Here’s what every 5G phone will cost you</h4><div class="ra-excerpt">

            If you want to be one of the first consumers to get your hands on a 5G phone, you’ll have to pay up. A lot of manufacturers including Samsung, LG, and Xiaomi have announced 5G …</div></div></div></div>
            <p>The 14 billion new numbers sound excessive, but keep in mind that <a href="https://www.androidauthority.com/what-is-the-internet-of-things-592491/">Internet of Things</a> (IoT) devices have been using 11-digit numbers that start with &#8220;020&#8221; since January 2017. Also, carriers have already taken 32.6 million of the 11-digit 020 numbers that were allocated two years ago.</p>
            <p>14-digit numbers could prove troublesome for the masses, since there will be more numbers to remember. That issue could be exacerbated among Japan&#8217;s aging population. It&#8217;s estimated that people older than 60 <a href="http://www.stat.go.jp/english/data/nenkan/1431-02.htm">make up 33 percent</a> of the Japanese population.</p>
            <p>Even so, we could see similar announcements in other countries that plan to deploy 5G networks and are running low on allocated phone numbers. It&#8217;s not as much of an issue in the U.S., where a <a href="http://www.nationalnanpa.com/pdf/NRUF/April_2014_NANP_Exhaust_Analysis.pdf">North American Numbering Plan exhaust analysis</a> estimates that the country&#8217;s current numbering system is sufficient until 2044.</p>
            <p style="text-align: center;"><strong>NEXT:</strong> <a href="https://www.androidauthority.com/vodafone-5g-launch-986496/">Vodafone issues 5G launch details: Here&#8217;s what you need to know</a></p>
            <div class="container-fluid builder-html"><div class="container-inner"><div class="affiliate-text"><i>Affiliate disclosure: We may receive compensation in connection with your purchase of products via links on this page. The compensation received will never influence the content, topics or posts made in this blog. See our <a href="https://www.androidauthority.com/external-links/">disclosure policy</a> for more details.</i></div></div></div>]]></content:encoded>
                        <wfw:commentRss>
                            https://www.androidauthority.com/japan-phone-numbers-14-digits-5g-986823/feed/
                        </wfw:commentRss>
                        <slash:comments>0</slash:comments>
                    </item>
                    <item>
                        <title>Report details just what Samsung is doing to update the Galaxy Fold</title>
                        <link>https://www.androidauthority.com/samsung-galaxy-fold-updates-986861/</link>
                        <comments>https://www.androidauthority.com/samsung-galaxy-fold-updates-986861/#respond
                        </comments>
                        <pubDate>Wed, 15 May 2019 20:10:12 +0000</pubDate>
                        <dc:creator><![CDATA[C. Scott Brown]]></dc:creator>
                        <category><![CDATA[News]]></category>
                        <category><![CDATA[Samsung]]></category>
                        <category><![CDATA[Samsung Galaxy Fold]]></category>

                        <guid isPermaLink="false">https://www.androidauthority.com/?p=986861</guid>
                        <description>
                            <![CDATA[Overall, many of the changes are expected, but it's nice to hear that Samsung could be implementing them.]]></description>
                        <media:content name="AndroidAuthority"
                            medium="image"
                            url="https://cdn57.androidauthority.net/wp-content/uploads/2019/04/Samsung-Galaxy-Fold-tablet-mode-on-chair-500x260.jpg" />
                        <content:encoded><![CDATA[<p><img class="aligncenter size-large wp-image-977086" src="https://cdn57.androidauthority.net/wp-content/uploads/2019/04/Samsung-Galaxy-Fold-tablet-mode-on-chair-840x473.jpg" alt="Samsung Galaxy Fold tablet mode on chair" width="840" height="473" srcset="https://cdn57.androidauthority.net/wp-content/uploads/2019/04/Samsung-Galaxy-Fold-tablet-mode-on-chair-840x472.jpg 840w, https://cdn57.androidauthority.net/wp-content/uploads/2019/04/Samsung-Galaxy-Fold-tablet-mode-on-chair-300x170.jpg 300w, https://cdn57.androidauthority.net/wp-content/uploads/2019/04/Samsung-Galaxy-Fold-tablet-mode-on-chair-768x432.jpg 768w, https://cdn57.androidauthority.net/wp-content/uploads/2019/04/Samsung-Galaxy-Fold-tablet-mode-on-chair-16x9.jpg 16w, https://cdn57.androidauthority.net/wp-content/uploads/2019/04/Samsung-Galaxy-Fold-tablet-mode-on-chair-32x18.jpg 32w, https://cdn57.androidauthority.net/wp-content/uploads/2019/04/Samsung-Galaxy-Fold-tablet-mode-on-chair-28x16.jpg 28w, https://cdn57.androidauthority.net/wp-content/uploads/2019/04/Samsung-Galaxy-Fold-tablet-mode-on-chair-56x32.jpg 56w, https://cdn57.androidauthority.net/wp-content/uploads/2019/04/Samsung-Galaxy-Fold-tablet-mode-on-chair-64x36.jpg 64w, https://cdn57.androidauthority.net/wp-content/uploads/2019/04/Samsung-Galaxy-Fold-tablet-mode-on-chair-712x400.jpg 712w, https://cdn57.androidauthority.net/wp-content/uploads/2019/04/Samsung-Galaxy-Fold-tablet-mode-on-chair-1000x563.jpg 1000w, https://cdn57.androidauthority.net/wp-content/uploads/2019/04/Samsung-Galaxy-Fold-tablet-mode-on-chair-1200x675.jpg 1200w, https://cdn57.androidauthority.net/wp-content/uploads/2019/04/Samsung-Galaxy-Fold-tablet-mode-on-chair-792x446.jpg 792w, https://cdn57.androidauthority.net/wp-content/uploads/2019/04/Samsung-Galaxy-Fold-tablet-mode-on-chair-1280x720.jpg 1280w, https://cdn57.androidauthority.net/wp-content/uploads/2019/04/Samsung-Galaxy-Fold-tablet-mode-on-chair-1340x754.jpg 1340w, https://cdn57.androidauthority.net/wp-content/uploads/2019/04/Samsung-Galaxy-Fold-tablet-mode-on-chair-770x433.jpg 770w, https://cdn57.androidauthority.net/wp-content/uploads/2019/04/Samsung-Galaxy-Fold-tablet-mode-on-chair-355x200.jpg 355w" sizes="(max-width: 840px) 100vw, 840px" /></p>
            <p>After early review units <a href="https://www.androidauthority.com/samsung-galaxy-fold-issues-display-problems-977233/">started breaking</a>, Samsung postponed the release of its first foldable device, the <a href="https://www.androidauthority.com/samsung-galaxy-fold-review-977100/">Samsung Galaxy Fold</a>, and promised to work on addressing the breakage issues. Now we have a report out of South Korea from <a href="https://www.yna.co.kr/view/AKR20190515026700017"><em>Yonhap News</em></a> (via <a href="https://www.sammobile.com/2019/05/15/improvements-samsung-made-galaxy-fold/"><em>SamMobile</em></a>) that gives us our first glimpse into what Samsung could be doing to address these problems.</p>
            <p>The two biggest issues with the Samsung Galaxy Fold were the protective film covering the inner screen and the gap between the display and the device&#8217;s folding hinge. Reviewers mistakenly pulled off the protective layer which caused some devices to malfunction, and the hinge gap allows for dust and other particles to contaminate the very sensitive OLED display.</p>
            <p>For the protective layer problem, Samsung is reportedly integrating the edges of the film into the body of the device itself. If true, this would make it more difficult for consumers to accidentally pull the protective layer off.</p>
            <div class="aa_srma_container shortcodes_wrapper right"><div class="shortcodes-header"><div class="shortcodes-title">Editor's Pick</div></div><div class="related_articles_wrapper"><div class="related_article_item"><a class="overlay-link" href="https://www.androidauthority.com/samsung-galaxy-fold-release-decision-984575/" title="Samsung CEO: Galaxy Fold release decision coming &#8216;in a couple of days&#8217;"></a><h4 class="ra-title">Samsung CEO: Galaxy Fold release decision coming &#8216;in a couple of days&#8217;</h4><div class="ra-excerpt">

            According to The Korea Herald, the new United States release date for the Samsung Galaxy Fold will be determined in the next few days.

            This information comes directly from Samsung CEO Koh Dong-jin, more commonly known as …</div></div></div></div>
            <p>As far as the hinge goes, the report merely suggests Samsung is working to reduce the size of the gap. This could mean that Samsung is eliminating the gap, or it could mean that it will simply be smaller and thus less prone to collecting debris.</p>
            <p>Finally, there will also likely be more warnings and safety statements with the Samsung Galaxy Fold to educate consumers on how this new type of device needs to be handled.</p>
            <p>We&#8217;ve reached out to <a href="https://www.androidauthority.com/best-samsung-phones-771831/">Samsung</a> about confirming or denying these reports and will update the article if we hear back.</p>
            <p>In the meantime, what do you think about this potential progress? Would these fixes make you more comfortable with buying a Fold? Or has the damage already been done? Let us know in the comments.</p>
            <p style="text-align: center;"><strong>NEXT: <a href="https://www.androidauthority.com/samsung-galaxy-home-release-date-985015/">Samsung Galaxy Home confirmed to launch by the end of June 2019</a></strong></p>
            ]]></content:encoded>
                        <wfw:commentRss>
                            https://www.androidauthority.com/samsung-galaxy-fold-updates-986861/feed/
                        </wfw:commentRss>
                        <slash:comments>0</slash:comments>
                    </item>
                    <item>
                        <title>Get certified as a white hat hacker for ${'$'}29 today</title>
                        <link>https://www.androidauthority.com/white-hat-hacker-certification-deal-984343/
                        </link>
                        <pubDate>Wed, 15 May 2019 19:29:50 +0000</pubDate>
                        <dc:creator><![CDATA[AA Picks]]></dc:creator>
                        <category><![CDATA[Deals]]></category>
                        <category><![CDATA[News]]></category>
                        <category><![CDATA[hacking]]></category>

                        <guid isPermaLink="false">https://www.androidauthority.com/?p=984343</guid>
                        <description>
                            <![CDATA[This is your chance to fight the increasing cyber threat as a certified white hat hacker. ]]></description>
                        <media:content name="AndroidAuthority"
                            medium="image"
                            url="https://cdn57.androidauthority.net/wp-content/uploads/2019/02/YfClsvq-500x260.jpg" />
                        <content:encoded><![CDATA[<p><a href="http://andauth.co/CompleteWhiteHat"><img class="aligncenter wp-image-883757 size-large" src="https://cdn57.androidauthority.net/wp-content/uploads/2018/07/Hacker-2-840x500.jpg" alt="White Hat Hacker Bundle" width="840" height="500" srcset="https://cdn57.androidauthority.net/wp-content/uploads/2018/07/Hacker-2-840x500.jpg 840w, https://cdn57.androidauthority.net/wp-content/uploads/2018/07/Hacker-2-300x178.jpg 300w, https://cdn57.androidauthority.net/wp-content/uploads/2018/07/Hacker-2-768x457.jpg 768w, https://cdn57.androidauthority.net/wp-content/uploads/2018/07/Hacker-2-16x10.jpg 16w, https://cdn57.androidauthority.net/wp-content/uploads/2018/07/Hacker-2-32x19.jpg 32w, https://cdn57.androidauthority.net/wp-content/uploads/2018/07/Hacker-2-28x17.jpg 28w, https://cdn57.androidauthority.net/wp-content/uploads/2018/07/Hacker-2-56x33.jpg 56w, https://cdn57.androidauthority.net/wp-content/uploads/2018/07/Hacker-2-64x38.jpg 64w, https://cdn57.androidauthority.net/wp-content/uploads/2018/07/Hacker-2-336x200.jpg 336w, https://cdn57.androidauthority.net/wp-content/uploads/2018/07/Hacker-2.jpg 953w" sizes="(max-width: 840px) 100vw, 840px" /></a></p>
            <p>To <strong>make money from hacking</strong> you don’t need to plug gadgets into bank ATMs like a young John Connor. You can keep it legal and still get well paid as a <a href="http://andauth.co/CompleteWhiteHat">white hat hacker</a>.</p>
            <p>Big corporations have your data and their reputations to protect. That’s why companies such as <a href="https://www.androidauthority.com/facebook-privacy-settings-848172/" target="_blank" rel="noopener noreferrer">Facebook</a> payout millions of dollars as part of their bug bounty programs. A<strong> typical payment</strong> for finding one security flaw is <strong>around ${'$'}30,000</strong>.</p>
            <p>Big money is on offer. To get in on the action, you can get <strong>extensive online training </strong>as a <strong>white hat hacker</strong> for <a href="http://andauth.co/CompleteWhiteHat">only ${'$'}29</a>.</p>
            <p>The <a href="http://andauth.co/CompleteWhiteHat">Complete White Hat Hacker Certification Bundle</a> is an <strong>eight-piece learning kit</strong> into the art of ethical hacking. It covers everything from the<strong> latest software</strong> used by hackers, to the <strong>coding</strong> you need, and the most <strong>common hacking techniques</strong>. Once you know how to think like a hacker, you’ll have the skills to beat them.</p>
            <p><a href="http://andauth.co/CompleteWhiteHat"><img class="aligncenter wp-image-915805 size-large" src="https://cdn57.androidauthority.net/wp-content/uploads/2018/10/White-Hat-Hacker-Bundle-840x399.jpg" alt="White Hat Hacker Bundle" width="840" height="399" srcset="https://cdn57.androidauthority.net/wp-content/uploads/2018/10/White-Hat-Hacker-Bundle-840x400.jpg 840w, https://cdn57.androidauthority.net/wp-content/uploads/2018/10/White-Hat-Hacker-Bundle-300x143.jpg 300w, https://cdn57.androidauthority.net/wp-content/uploads/2018/10/White-Hat-Hacker-Bundle-768x365.jpg 768w, https://cdn57.androidauthority.net/wp-content/uploads/2018/10/White-Hat-Hacker-Bundle-16x8.jpg 16w, https://cdn57.androidauthority.net/wp-content/uploads/2018/10/White-Hat-Hacker-Bundle-32x15.jpg 32w, https://cdn57.androidauthority.net/wp-content/uploads/2018/10/White-Hat-Hacker-Bundle-28x13.jpg 28w, https://cdn57.androidauthority.net/wp-content/uploads/2018/10/White-Hat-Hacker-Bundle-56x27.jpg 56w, https://cdn57.androidauthority.net/wp-content/uploads/2018/10/White-Hat-Hacker-Bundle-64x30.jpg 64w, https://cdn57.androidauthority.net/wp-content/uploads/2018/10/White-Hat-Hacker-Bundle-421x200.jpg 421w, https://cdn57.androidauthority.net/wp-content/uploads/2018/10/White-Hat-Hacker-Bundle.jpg 842w" sizes="(max-width: 840px) 100vw, 840px" /></a></p>
            <p>Despite the low price, you even get a<strong> free voucher for the ISA CISS examination</strong> upon completion of the <a href="http://andauth.co/CompleteWhiteHat">Hacking In Practice: Certified Ethical Hacking Mega Course</a>. This <strong>certification</strong> will shine on your resume if you decide to apply for any lucrative cybersecurity roles.</p>
            <h2>The White Hat Hacker Bundle:</h2>
            <ul>
            <li><a href="http://andauth.co/CompleteWhiteHat">Hacking &amp; Penetration Testing With Metasploit</a> <strong>&#8212; ${'$'}99.99 value</strong></li>
            <li><a href="http://andauth.co/CompleteWhiteHat">Hacking Wireless Networks: Theory &amp; Practice</a> <strong>&#8212; ${'$'}99.99 value</strong></li>
            <li><a href="http://andauth.co/CompleteWhiteHat">Rootkits &amp; Stealth Apps: Creating &amp; Revealing 2.0</a><strong> &#8212; ${'$'}99.99 value</strong></li>
            <li><a href="http://andauth.co/CompleteWhiteHat">Website Hacking In Practice: Hands-On</a> <strong>&#8212; ${'$'}99.99 value</strong></li>
            <li><a href="http://andauth.co/CompleteWhiteHat">IT Surveillance &amp; Computer Forensics From Scratch</a><strong> &#8212; ${'$'}99.99 value</strong></li>
            <li><a href="http://andauth.co/CompleteWhiteHat">Computer &amp; Network Hacking Mastery: Practical Techniques</a> <strong>&#8212; ${'$'}99.99 value</strong></li>
            <li><a href="http://andauth.co/CompleteWhiteHat">Hacking In Practice: Certified Ethical Hacking Mega Course</a> <strong>&#8212; ${'$'}99.99 value</strong></li>
            <li><a href="http://andauth.co/CompleteWhiteHat">Kali Linux Tutorial For Beginners</a> <strong>&#8212; ${'$'}194.99 value</strong></li>
            </ul>
            <p>Follow the links for more info on each of the courses in this package. Over 1,000 people have already enrolled. If you want to join them and get a <strong>96 percent discount</strong> at the same time, make sure you <strong>sign up before the deal expires</strong>.</p>
            <p>The button below takes you there.</p>
            <div class="aa_custom_button_wrapp center"><a class="aa_button cbs_button add-active fasc-alignment-center center" style="background-color: #0077db;" target="_blank" rel="nofollow noopener noreferrer" href="http://andauth.co/CompleteWhiteHat">Start learning to hack now!</a></div>
            <p><em>The AAPicks team writes about things we think you’ll like, and we may see a share of revenue from any purchases made through affiliate links. To see all our hottest deals, head over to <a href="http://andauth.co/AAPicksHub">the AAPICKS HUB</a>. </em></p>
            ]]></content:encoded>
                    </item>
                </channel>
            </rss>
        """.trimIndent()
    }
}
