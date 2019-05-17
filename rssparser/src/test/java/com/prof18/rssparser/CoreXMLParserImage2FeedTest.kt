package com.prof18.rssparser

import com.prof.rssparser.Article
import com.prof.rssparser.core.CoreXMLParser
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CoreXMLParserImage2FeedTest {
    private lateinit var articleList: MutableList<Article>
    private lateinit var article: Article
    private val feedPath = "/feed-test-image-2.xml"

    @Before
    fun setUp() {
        val inputStream = javaClass.getResourceAsStream(feedPath)!!
        val feed = inputStream.bufferedReader().use { it.readText() }
        articleList = CoreXMLParser.parseXML(feed)
        article = articleList[0]
    }

    @Test
    @Throws
    fun size_isCorrect() {
        assertEquals(articleList.size, 20)
    }


    @Test
    @Throws
    fun title_isCorrect() {
        assertEquals(article.title, "Martens: “Estoy feliz y quiero seguir en el Barça”")
    }

    @Test
    @Throws
    fun author_isCorrect() {
        assertEquals(article.author, null)
    }

    @Test
    @Throws
    fun link_isCorrect() {
        assertEquals(article.link, "https://www.mundodeportivo.com/futbol/fc-barcelona/20190517/462298326260/martens-estoy-feliz-y-quiero-seguir-en-el-barca.html")
    }

    @Test
    @Throws
    fun pubDate_isCorrect() {
        assertEquals(article.pubDate, "Fri, 17 May 2019 21:33:59 +0200")
    }

    @Test
    @Throws
    fun description_isCorrect() {
        assertEquals(article.description, "El diario francés l’Equipe se hizo eco ayer de una noticia de impacto: el Olympique de Lyon desea fichar a la delantera azulgrana Lieke Martens. La holandesa es una de las jugadoras más cotizadas del mundo desde que hace dos años recibió el...")
    }

    @Test
    @Throws
    fun content_isCorrect() {
        assertEquals(article.content, null)
    }

    @Test
    @Throws
    fun image_isCorrect() {
        assertEquals(article.image, "https://www.mundodeportivo.com/ra/thumbnail/GODO/MD/p6/Barca/Imagenes/2019/05/17/Recortada/img_ppunti_20190517-210423_imagenes_md_propias_ppunti_190517fcbfem294_4_6_2298222649-kYlG-U4622983262609eF-980x554@MundoDeportivo-Web.jpg")
    }

    @Test
    @Throws
    fun categories_isCorrect() {
        assertEquals(article.categories, mutableListOf(
                "F.C. Barcelona"
        ))
    }
}