package com.prof18.rssparser.rss

import com.prof18.rssparser.BaseXmlParserTest
import com.prof18.rssparser.model.RssImage

class XmlParserDcDate : BaseXmlParserTest(
    feedPath = "feed-dcdate.xml",
    channelTitle = "Reporterre, le média de l'écologie - Indépendant et en accès libre",
    channelLink = "https://reporterre.net/",
    channelDescription = """
Reporterre est un média indépendant qui publie chaque jour des articles, enquêtes et reportages sur l"écologie. En accès libre, sans publicité, à but non lucratif, Reporterre est financé par les dons de ses lectrices et lecteurs. *****
            Reporterre, environnement, écologie, climat, biodiversité, luttes, alternatives
            *****
            À propos de Reporterre
            Reporterre est un média indépendant dédié à l'écologie sous toutes ses formes. Le journal est géré par une association d'intérêt général à but non lucratif, et n'a donc pas d'actionnaire. Il emploie une équipe de journalistes professionnels, et de nombreux contributeurs. Le journal est en accès libre, sans publicité, et financé à 98% par les dons de ses lecteurs.
            En savoir plus
            *****
            Rédaction
            Reporterre 16 bd Jules Ferry 75011 Paris
            *****
            Contacter Reporterre
            Une question ? Consultez la FAQ
            Les livres Reporterre / Seuil RGPD
            Recrutements
            *****
            CLIQUER ICI
            Tel : 01 88 33 57 55
            Adresse :
            . Rédaction : Reporterre 16 bd Jules Ferry, 75011 Paris.
            . L'association : La Pile 16 bd Jules Ferry, 75011 Paris.
            *****
            21 journalistes permanents
            *****
            Plus de 100 000 visiteurs uniques
            tous les jours
            *****
            14 000 donateurs
            réguliers
            *****
            Un budget de
            200 000 € par mois
            *****
            5 nouvelles publications par jour
            *****
            Reporterre 16 bd Jules Ferry 75011 Paris
            planete@reporterre.net
    """.trimIndent(),
    channelLastBuildDate = "Wed, 26 Feb 2025 18:45:21 +0100",
    channelImage = RssImage(
        title = "Reporterre, le média de l'écologie - Indépendant et en accès libre",
        url = "https://reporterre.net/squelettes/images/logofeed.png",
        link = "https://reporterre.net/",
        description = null,
    ),
    articleTitle = "Europe : vers un « affaiblissement historique » des normes écologiques ?",
    articleLink = "https://reporterre.net/Europe-vers-un-affaiblissement-historique-des-normes-ecologiques",
    articlePubDate = "2025-02-26T16:42:45Z",
    articleDescription = """<img src='https://reporterre.net/local/cache-vignettes/L700xH467/european_union_flag__4768764591_2_-cc8c4.jpg?1740588172' class='spip_logo' width='700' height='467' alt="" />

                <p>Le 26 février, la Commission européenne a présenté un projet de directive «<small class="fine d-inline"> </small>Omnibus<small class="fine d-inline"> </small>», un ensemble de nouvelles règles destinées à alléger les contraintes administratives pesant sur les entreprises. Sous couvert de leur «<small class="fine d-inline"> </small>simplifier<small class="fine d-inline"> </small>» la vie, «<small class="fine d-inline"> </small>la Commission propose de démanteler nombre d'obligations en matière de durabilité et de protection des droits humains<small class="fine d-inline"> </small>», s'indignent plusieurs <span class="caps">ONG</span> et syndicats dans un communiqué. Ils craignent un «<small class="fine d-inline"> </small>affaiblissement historique des normes (…)</p>

                <a href="https://reporterre.net/Europe-vers-un-affaiblissement-historique-des-normes-ecologiques?utm_source=RSS&utm_medium=RSS">Lire la suite</a>


                -
                <a href="https://reporterre.net/Breves-20" rel="directory">En bref</a>


                /
                <a href=" https://reporterre.net/Europe" rel="tag" >Europe</a>""",
    articleImage = "https://reporterre.net/local/cache-vignettes/L700xH467/european_union_flag__4768764591_2_-cc8c4.jpg?1740588172",
    articleGuid = "https://reporterre.net/Europe-vers-un-affaiblissement-historique-des-normes-ecologiques"
)
