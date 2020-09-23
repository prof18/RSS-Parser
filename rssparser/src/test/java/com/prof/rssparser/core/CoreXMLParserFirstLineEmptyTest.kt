/*
*   Copyright 2020 Marco Gomiero
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

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.P])
class CoreXMLParserFirstLineEmptyTest {
    private lateinit var articleList: MutableList<Article>
    private lateinit var article: Article
    private val feedPath = "/feed-test-first-line-blank.xml"
    private lateinit var channel: Channel

    @Before
    fun setUp() {
        val inputStream = javaClass.getResourceAsStream(feedPath)!!
        val feed = inputStream.bufferedReader().use { it.readText() }
        channel = CoreXMLParser.parseXML(feed)
        articleList = channel.articles
        article = articleList[0]
    }

    @Test
    fun channelTitle_isCorrect() {
        assertEquals(channel.title, "Inteligência artificial – Portal CNJ")
    }

    @Test
    fun channelDesc_isCorrect() {
        assertEquals(channel.description, "Conselho Nacional de Justiça")
    }

    @Test
    fun channelLink_isCorrect() {
        assertEquals(channel.link, "https://www.cnj.jus.br")
    }

    @Test
    fun channelImage_isNull() {
        assertEquals(channel.image!!.url, "https://www.cnj.jus.br/wp-content/uploads/2020/06/Favicons-Portal-CNJ-1-36x36.jpg")
    }

    @Test
    fun channelLastBuild_isCorrect() {
        assertEquals(channel.lastBuildDate, "Fri, 07 Aug 2020 14:03:42 +0000")
    }

    @Test
    fun channelUpdatePeriod_isCorrect() {
        assertEquals(channel.updatePeriod, "hourly")
    }

    @Test
    @Throws
    fun size_isCorrect() {
        assertEquals(articleList.size, 9)
    }

    @Test
    @Throws
    fun title_isCorrect() {
        assertEquals(article.title, "TJ é premiado em evento nacional sobre inovação e direito para o ecossistema")
    }

    @Test
    @Throws
    fun author_isCorrect() {
        assertEquals(article.author, "Marcio Gonçalves")
    }

    @Test
    @Throws
    fun link_isCorrect() {
        assertEquals(article.link, "https://www.cnj.jus.br/tj-e-premiado-em-evento-nacional-sobre-inovacao-e-direito-para-o-ecossistema/?utm_source=rss&utm_medium=rss&utm_campaign=tj-e-premiado-em-evento-nacional-sobre-inovacao-e-direito-para-o-ecossistema")
    }

    @Test
    @Throws
    fun pubDate_isCorrect() {
        assertEquals(article.pubDate, "Thu, 18 Jun 2020 22:37:47 +0000")
    }

    @Test
    @Throws
    fun description_isCorrect() {
        assertEquals(article.description, "<p>O Tribunal de Justiça de Mato Grosso (TJMT) recebeu o prêmio destaque do Expojud online, Congresso de Inovação, Tecnologia e Direito para o Ecossistema da Justiça realizado de dois a quatro de junho. O Judiciário estadual foi condecorado na categoria Maior Participação de Instituição de Justiça. “Esse reconhecimento mostra que estamos no melhor caminho e [&#8230;]</p>\n" +
                "<p>The post <a rel=\"nofollow\" href=\"https://www.cnj.jus.br/tj-e-premiado-em-evento-nacional-sobre-inovacao-e-direito-para-o-ecossistema/\">TJ é premiado em evento nacional sobre inovação e direito para o ecossistema</a> appeared first on <a rel=\"nofollow\" href=\"https://www.cnj.jus.br\">Portal CNJ</a>.</p>")
    }

    @Test
    @Throws
    fun content_isCorrect() {
        assertEquals(article.content, "<p>O Tribunal de Justiça de Mato Grosso (TJMT) recebeu o prêmio destaque do Expojud online, Congresso de Inovação, Tecnologia e Direito para o Ecossistema da Justiça realizado de dois a quatro de junho. O Judiciário estadual foi condecorado na categoria Maior Participação de Instituição de Justiça. “Esse reconhecimento mostra que estamos no melhor caminho e certifica o trabalho que vem sendo desenvolvido no nosso tribunal. Tudo isso é fruto dos investimentos e do planejamento estratégico traçado de forma contundente na administração do desembargador Carlos Alberto Alves da Rocha”, frisa Luiz Octávio Oliveira Sabóia Ribeiro, juiz auxiliar da Presidência do TJMT.</p>\n" +
                "<p>O evento, 100% virtual, transmitido via YouTube, reuniu mais de cinco mil magistrados, servidores, profissionais de Tecnologia da Informação e operadores do Direito das instituições do sistema judicial de todos os estados. Durante os três dias, sempre no período matutino, os participantes tiveram a oportunidade de fazer interações com os palestrantes e visitas aos stands virtuais de instituições do Judiciário e das empresas apoiadoras do congresso. E o TJMT estava entre as 23 instituições que apresentaram inovações tecnológicas a serviço da qualidade da prestação jurisdicional.</p>\n" +
                "<p>Foram promovidas mais de 50 palestras, distribuídas entre dois palcos virtuais, com especialistas de diversos setores que falaram sobre inovação, tecnologia, casos de sucesso e empreendedorismo dentro do sistema judicial. Os temas foram aplicados à realidade atual do Judiciário, como os cenários pós-pandemia e o impacto na Justiça, os efeitos da pandemia nas atividades dos magistrados, Covid-19 e os impactos dessas complexidades na revolução tecnológica nos tribunais superiores. Também foi debatida a importância do alinhamento da estratégia digital com a estratégia de negócio nos tribunais.</p>\n" +
                "<p>Na programação, os conteúdos de tecnologia proporcionaram uma imersão nas soluções que estão transformando os tribunais de Justiça, entre os temas centrais estavam inteligência artificial (IA), plataformas de colaboração em vídeo, machine learning, realidade virtual, blockchain e as soluções que envolvem todas as esferas da Lei Geral de Proteção de Dados Pessoais.</p>\n" +
                "<p>E como inteligência artificial figurava entre os assuntos de maior centralidade do seminário, o juiz federal Pedro Felipe Santos abordou como a pandemia do novo coronavírus possibilitou a aceleração da implementação de novas ferramentas digitais na prestação jurisdicional, além de como essa aceleração provocou transformações que influenciaram os serviços que hoje são prestados à sociedade.</p>\n" +
                "<p>No entanto, o magistrado federal demonstrou preocupação com essa aceleração e ressaltou “a necessidade da observação das medidas éticas e cautelares no desenvolvimento da inteligência artificial no Judiciário brasileiro”. Segundo Pedro Felipe, essa experiência pode e deve ser positiva, justamente porque todos estão empenhados em desenvolver e trocar experiências acerca do tema, uma vez que, na avaliação dele, a inteligência artificial multiplica os cérebros pensando a Justiça brasileira.</p>\n" +
                "<p>O juiz Pedro Felipe também se mostrou otimista em relação ao desenvolvimento de IA pelo Poder Judiciário brasileiro, e reiterou a necessidade de observação de questões éticas e afirmou ser fundamental a criação de um marco regulatório para utilização desse tipo de tecnologia.</p>\n" +
                "<p>Na visão do ativista em inovação e curador do congresso, advogado Ademir Piccoli, o evento superou todas as expectativas. Nos cálculos dele, alguns painéis contaram com até 1.400 pessoas conectadas simultaneamente. “Foi surpreendente, e o Tribunal de Justiça de Mato Grosso está de parabéns. Mostrou que está atento às inovações, e essa estratégia é muito importante. Além disso, foi um dos quatro tribunais que participou com o maior número de inscritos”, sinalizou Piccoli.</p>\n" +
                "<p style=\"text-align: right;\"><em>Fonte: <a href=\"http://www.tjmt.jus.br/noticias/59610#.XuvrKmhKjIU\" target=\"_blank\" rel=\"noopener noreferrer\">TJMT</a></em></p>\n" +
                "<p>The post <a rel=\"nofollow\" href=\"https://www.cnj.jus.br/tj-e-premiado-em-evento-nacional-sobre-inovacao-e-direito-para-o-ecossistema/\">TJ é premiado em evento nacional sobre inovação e direito para o ecossistema</a> appeared first on <a rel=\"nofollow\" href=\"https://www.cnj.jus.br\">Portal CNJ</a>.</p>")
    }

    @Test
    @Throws
    fun image_isCorrect() {
        assertNull(article.image)
    }

    @Test
    @Throws
    fun categories_isCorrect() {
        assertEquals(article.categories, mutableListOf(
                "Notícias do Judiciário",
                "Agência CNJ de Notícias",
                "tecnologia e modernização",
                "TJMT",
                "tecnologia da informação",
                "Inteligência artificial",
                "coronavirus",
        ))
    }

    @Test
    @Throws
    fun guid_isCorrect() {
        assertEquals(article.guid, "https://www.cnj.jus.br/?p=109151")
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