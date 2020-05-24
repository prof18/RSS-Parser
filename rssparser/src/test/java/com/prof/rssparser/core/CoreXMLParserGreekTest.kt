package com.prof.rssparser.core

import android.os.Build
import com.prof.rssparser.Article
import com.prof.rssparser.Channel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.nio.charset.Charset

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class CoreXMLParserGreekTest {
    private lateinit var articleList: MutableList<Article>
    private lateinit var article: Article
    private val feedPath = "/feed-test-greek.xml"
    private lateinit var channel: Channel

    @Before
    fun setUp() {
        val inputStream = javaClass.getResourceAsStream(feedPath)!!
        val feed = inputStream.bufferedReader(Charset.forName("ISO-8859-7")).use { it.readText() }
        channel = CoreXMLParser.parseXML(feed)
        articleList = channel.articles
        article = articleList[0]
    }

    @Test
    fun channelTitle_isCorrect() {
        assertEquals(channel.title, "Liberal - ΕΠΙΚΑΙΡΟΤΗΤΑ")
    }

    @Test
    fun channelDesc_isCorrect() {
        assertEquals(channel.description, "Ενημέρωση με άποψη για την οικονομία, την πολιτική και τις διεθνείς σχέσεις.")
    }

    @Test
    fun channelLink_isCorrect() {
        assertEquals(channel.link, "https://www.liberal.gr/news")
    }

    @Test
    fun channelImage_isNull() {
        assertNull(channel.image)
    }

    @Test
    fun channelLastBuild_isCorrect() {
        assertNull(channel.lastBuildDate)
    }

    @Test
    fun channelUpdatePeriod_isCorrect() {
        assertNull(channel.updatePeriod)
    }

    @Test
    @Throws
    fun size_isCorrect() {
        assertEquals(articleList.size, 2)
    }


    @Test
    @Throws
    fun title_isCorrect() {
        assertEquals(article.title, "«Πορτραίτα καραντίνας» για έμπνευση από τη συλλογή Φέλιου")
    }

    @Test
    @Throws
    fun author_isCorrect() {
        assertEquals(article.author, null)
    }

    @Test
    @Throws
    fun link_isCorrect() {
        assertEquals(article.link, "https://www.liberal.gr/news/portraita%2Dkarantinas%2Dgia%2Dempneusi%2Dapo%2Dti%2Dsullogi%2Dfeliou/298923")
    }

    @Test
    @Throws
    fun pubDate_isCorrect() {
        assertEquals(article.pubDate, "Fri, 24 Apr 2020 22:03:00 +0200")
    }

    @Test
    @Throws
    fun description_isPresent() {
        assertTrue(!article.description.isNullOrEmpty())
    }

    @Test
    @Throws
    fun description_isCorrect() {
        assertEquals(article.description, """
Η συνθήκη του αναγκαστικού εγκλεισμού οδήγησε τα μεγάλα μουσεία διεθνώς να βρουν νέους, εναλλακτικούς τρόπους επικοινωνίας με το κοινό. Το διαδίκτυο και τα social media πρόσφεραν το απαραίτητο έδαφος και οι απανταχού φιλότεχνοι ανταποκρίθηκαν αμέσως. Μονάχα το Prado, από τις 12 Μαρτίου που έκλεισε, είχε 2.000.000 επισκέπτες μέσα από ένα ευρύ πρόγραμμα ψηφιακών προβολών και δράσεων που ανέπτυξε. Μία από τις πιο ευφάνταστες ξεκίνησε από το Rijksmuseum και τον λογαριασμό του στο Instagram με την ονομασία &laquo;Between Art and Quarantine&raquo; (Μεταξύ Τέχνης και Καραντίνας). Εκεί, καλούνται οι διαδικτυακοί φίλοι να δημιουργήσουν σέλφις, εμπνευσμένες από έργα τέχνης, με αποτέλεσμα σπουδαίοι ζωγράφοι να &laquo;χρησιμοποιηθούν&raquo; ξανά για να συνθέσουν τη ζωή στις μέρες της καραντίνας.

                Εδώ σε μας, την σκυτάλη από τα ξένα μουσεία πήρε ο διακεκριμένος νομικός Σωτήρης Φέλιος που θέλησε να δώσει χρώμα ελληνικό στην ευφάνταστη πρωτοβουλία. Γνωστός για την εξωστρέφεια με την οποία διαχειρίζεται την συλλογή του με έργα εγχώριας τέχνης, ο Φέλιος προχώρησε ένα βήμα παραπέρα, αφήνοντας πίσω μεγάλα κρατικά ιδρύματα. Δημιούργησε στη νέα ιστοσελίδα του (felioscollection.gr) ένα σύγχρονο ψηφιακό περιβάλλον, παρουσιάζοντας όχι μόνο φωτογραφίες έργων, αλλά δίνοντας &laquo;βήμα&raquo; στους ίδιους τους δημιουργούς. Με μια σειρά από video, οι σύγχρονοι καλλιτέχνες συστήνονται στο κοινό, φιλοτεχνώντας το &laquo;πορτρέτο&raquo; τους. Μιλούν για τη δουλειά τους, τον τρόπο που ζωγραφίζουν κι αυτό που, ίσως, ονειρεύονται ν&rsquo; αφήσουν στην ιστορία της ελληνικής τέχνης. Είναι μια καταγραφή, όχι απλά χρήσιμη για τον υποψιασμένο θεατή, αλλά για το ευρύ κοινό που ενδιαφέρεται να δει το πρόσωπο του δημιουργού πίσω από το έργο.



                Τις αυτοπροσωπογραφίες αυτές, μαζί με τα δεκάδες πορτρέτα που ανήκουν στη συλλογή &ndash; πολλά από αυτά έχουν ήδη καταγραφεί ως μεγάλα έργα της ελληνικής ζωγραφικής - επαναφέρει ο συλλέκτης και οι συνεργάτες του με τη δραστηριότητα &quot;Quarantine Portraits&quot;. Ο φιλότεχνος θεατής καλείται να εμπνευστεί από τη συλλογή και να ζωγραφίσει το δικό του πορτρέτο, μοιραζόμενος το αποτέλεσμα μέσα από το Facebook&nbsp; και το&nbsp; Instagram στα tags #quarantineportraits και @felioscollection.

                6 Βήματα

                Εκτός από το κέφι, το πρώτο που χρειάζεται είναι η έμπνευσή σας. Και σίγουρα, ρίχνοντας μια ματιά στη πολυάριθμη όσο και ποιοτική συλλογή των έργων, δεν θα δυσκολευτείτε να επιλέξετε ένα από τα πορτρέτα. Μπορείτε να πειραματιστείτε με γεωμετρικά σχήματα και χρώματα, με χρωματιστά φόντα, διαφορετικές γραμμές και μοτίβα, καθώς και με απλά περιγράμματα ή πλούσια χρώματα, αν είστε αρχάριος, ή επιχειρείτε να φιλοτεχνήσετε το πορτρέτο μαζί με το πιτσιρίκι σας.

                Το δεύτερο είναι η επιλογή των υλικών: πάρτε μολύβια, κραγιόνια, μαρκαδόρους, οτιδήποτε έρχεται βολικό και πλησιάζει στο αποτέλεσμα που θέλετε. Πολλοί αγαπημένοι δημιουργοί, όπως ο Τ. Μαντζαβίνος και ο Μ. Μπιτσάκης κάνουν αριστουργήματα απλώς με ένα στιλό bic. Κατόπιν, διαλέξτε τα χρώματά σας όπου μπορείτε να &laquo;πειράξετε&raquo; το έργο που σας αρέσει, βάζοντας αυτό που πιστεύετε ότι σας ταιριάζει (και γιατί όχι, θα έπρεπε να έχει επιλέξει ο δημιουργός).



                Πάρτε ένα αγαπημένο πρόσωπο και κοιτάξτε το, όπως δεν το έχετε ξανακάνει. Μπορεί να &laquo;κλέψετε&raquo; και λίγο, κοιτώντας μια φωτογραφία (κανείς δεν πρόκειται να το μάθει). Αν δεν έχετε μοντέλο στη διάθεσή σας, φέρτε έναν καθρέφτη ή τη θύμηση ενός αγαπημένου προσώπου. Το έκτο και τελευταίο βήμα είναι το πιο αποφασιστικό: μην σας αποθαρρύνει το αποτέλεσμα, αλλά σκεφτείτε ότι υπογράψατε ένα έργο τέχνης που &laquo;ανήκει&raquo; σε μία από τις πιο σημαντικές συλλογές. Μοιραστείτε το έργο και δείτε το online στους λογαριασμούς #quarantineportraits και @felioscollection.

                Περισσότερα από χίλια ζωγραφικά έργα,&nbsp; γλυπτά, ανάγλυφα,&nbsp; κατασκευές,&nbsp; χαρακτικά,&nbsp; φωτογραφίες, ψηφιακές εκτυπώσεις και σχέδια των Χρόνη Μπότσογλου, Εδουάρδου Σακαγιάν, Γιώργου Ρόρρη, Χρήστου Μποκόρου, Στέφανου Δασκαλάκη, Τάσου Μισούρα, Μαρίας Φιλοπούλου, Καλλιόπης Ασαργιωτάκη, Κωνσταντίνου Παπαμιχαλόπουλου και τόσων ακόμη κορυφαίων μας περιμένουν να τα ανακαλύψουμε διαδικτυακά, να μας εμπνεύσουν και, γιατί όχι, να αναμετρηθούμε μαζί τους.
        """.trimIndent())
    }

    @Test
    @Throws
    fun content_isCorrect() {
        assertNull(article.content)
    }

    @Test
    @Throws
    fun image_isCorrect() {
        assertEquals(article.image, "http://www.liberal.gr/photos/646464564565.jpg")
    }

    @Test
    @Throws
    fun categories_isCorrect() {
        assertTrue(article.categories.isEmpty())
    }

    @Test
    @Throws
    fun guid_isCorrect() {
        assertEquals(article.guid, "https://www.liberal.gr/news/portraita%2Dkarantinas%2Dgia%2Dempneusi%2Dapo%2Dti%2Dsullogi%2Dfeliou/298923")
    }

    @Test
    @Throws
    fun audio_iCorrect() {
        assertNull(article.audio)
    }

    @Test
    @Throws
    fun sourceName_iCorrect() {
        assertNull(article.sourceName)
    }

    @Test
    @Throws
    fun sourceUrl_iCorrect() {
        assertNull(article.sourceUrl)
    }

    @Test
    @Throws
    fun video_isCorrect() {
        assertNull(article.video)
    }
}