package com.prof.rssparser.caching

import android.content.Context
import android.util.Log
import com.prof.rssparser.BuildConfig
import com.prof.rssparser.Channel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

class CacheManager(private val database: CacheDatabase,
                   private val cacheDurationMillis: Long) {

    // TODO: add method for flushing the cache

    // TODO: document & test
    suspend fun cacheFeed(url: String, channel: Channel) {
        withContext(Dispatchers.IO) {
            var objectStream: ObjectOutputStream? = null
            val outputStream = ByteArrayOutputStream()
            try {
                objectStream = ObjectOutputStream(outputStream)

                objectStream.writeObject(channel)
                objectStream.flush()

                val bytes = outputStream.toByteArray()
                val urlHash = url.hashCode()

                val cachedFeed = CachedFeed(
                        urlHash = urlHash,
                        byteArray = bytes,
                        cachedDate = System.currentTimeMillis(),
                        libraryVersion = BuildConfig.VERSION_CODE
                )

                database.cachedProjectsDao().insertFeed(cachedFeed)
                Log.d(TAG, "Saved the feed to cache")

            } catch (e: Exception) {
                Log.e(TAG, "Failed to cache the feed")
            } finally {
                outputStream.close()
                objectStream?.close()
            }
        }
    }

    // TODO: document & test
    suspend fun getCachedFeed(url: String): Channel? = withContext(Dispatchers.IO) {
        val urlHash = url.hashCode()

        database.cachedProjectsDao().getCachedProject(urlHash)?.let { cachedFeed ->
            // Just to avoid problems when the Channel object changes
            if (cachedFeed.libraryVersion != BuildConfig.VERSION_CODE) {
                // TODO: delete feed from the db
                return@withContext null
            }

            if (System.currentTimeMillis() - cachedFeed.cachedDate <= cacheDurationMillis) {
                val inputStream = ByteArrayInputStream(cachedFeed.byteArray)
                var objectInput: ObjectInputStream? = null
                try {
                    objectInput = ObjectInputStream(inputStream)
                    val channelFromCache = objectInput.readObject() as Channel
                    Log.d(TAG, "Feed restored from cache")
                    return@withContext channelFromCache
                } catch (e: Exception) {
                    Log.e(TAG, "Failed to load feed from cache")
                    return@withContext null
                }
            } else {
                // TODO: delete feed from the db
            }
            return@withContext null
        }
    }

    companion object {
        const val TAG = "RSSParser CacheManager"

        private var INSTANCE: CacheManager? = null
        private val lock = Any()

        fun getInstance(context: Context, cacheDurationMillis: Long): CacheManager {
            if (INSTANCE == null) {
                synchronized(lock) {
                    if (INSTANCE == null) {
                        INSTANCE = CacheManager(CacheDatabase.getInstance(context), cacheDurationMillis)
                    }
                    return INSTANCE as CacheManager
                }
            }
            return INSTANCE as CacheManager
        }
    }
}


/*


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
                    _categories = mutableListOf("News", "5G", "Japan")
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
                    _categories = mutableListOf("News", "Samsung", "Samsung Galaxy Fold")
            )
    )

    val channel = Channel(
            title = "Android Authority",
            link = "https://www.androidauthority.com",
            description = "Android News, Reviews, How To",
            image = null,
            lastBuildDate = "Wed, 15 May 2019 20:48:02 +0000",
            updatePeriod = "hourly",
            articles = articles
    )
    val url = "https://www.androidauthority.com/feed"

 */