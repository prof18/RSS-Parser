/*
*   Copyright 2019 Marco Gomiero
*
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
*
*/

package com.prof18.rssparser.rss

import com.prof18.rssparser.XmlParserTestExecutor
import com.prof18.rssparser.model.RawEnclosure
import com.prof18.rssparser.model.RssChannel
import com.prof18.rssparser.model.RssImage
import com.prof18.rssparser.model.RssItem
import com.prof18.rssparser.parseFeed
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class XmlParserImage2FeedTest : XmlParserTestExecutor() {

    private val expectedChannel = RssChannel(
        title = "F.C. Barcelona",
        link = "https://www.mundodeportivo.com/futbol/fc-barcelona",
        description = "F.C. Barcelona",
        image = RssImage(
            title = "F.C. Barcelona",
            url = "https://www.mundodeportivo.com/rsc/images/logo_MD_feed.png",
            link = "https://www.mundodeportivo.com/futbol/fc-barcelona",
            description = "Mundo Deportivo es tu diario deportivo On Line. Noticias de deporte, fútbol y del Barça"
        ),
        lastBuildDate = null,
        updatePeriod = null,
        items = listOf(
            RssItem(
                guid = "f5c42a9c-78d9-11e9-a24c-645e8f5d185b",
                title = "Martens: “Estoy feliz y quiero seguir en el Barça”",
                author = null,
                link = "https://www.mundodeportivo.com/futbol/fc-barcelona/20190517/462298326260/martens-estoy-feliz-y-quiero-seguir-en-el-barca.html",
                pubDate = "Fri, 17 May 2019 21:33:59 +0200",
                description = """El diario francés l’Equipe se hizo eco ayer de una noticia de impacto: el Olympique de Lyon desea fichar a la delantera azulgrana Lieke 
    Martens. La holandesa es una de las jugadoras más cotizadas del mundo desde que hace dos años recibió el...""",
                content = null,
                image = "https://www.mundodeportivo.com/ra/thumbnail/GODO/MD/p6/Barca/Imagenes/2019/05/17/Recortada/img_ppunti_20190517-210423_imagenes_md_propias_ppunti_190517fcbfem294_4_6_2298222649-kYlG-U4622983262609eF-980x554@MundoDeportivo-Web.jpg",
                audio = null,
                video = null,
                sourceName = null,
                sourceUrl = null,
                categories = listOf(
                    "F.C. Barcelona"
                ),
                itunesItemData = null,
                commentsUrl = null,
                youtubeItemData = null,
                rawEnclosure = RawEnclosure(
                    url = "https://www.mundodeportivo.com/ra/thumbnail/GODO/MD/p6/Barca/Imagenes/2019/05/17/Recortada/img_ppunti_20190517-210423_imagenes_md_propias_ppunti_190517fcbfem294_4_6_2298222649-kYlG-U4622983262609eF-980x554@MundoDeportivo-Web.jpg",
                    length = 228,
                    type = "image/jpeg",
                ),
            )
        ),
        itunesChannelData = null,
        youtubeChannelData = null,
    )

    @Test
    fun channelIsParsedCorrectly() = runTest {
        val channel = parseFeed("feed-test-image-2.xml")
        assertEquals(expectedChannel, channel)
    }
}
