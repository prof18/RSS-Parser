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

package com.prof18.rssparser.atom

import com.prof18.rssparser.BaseXmlParserTest

class XmlParserAtomFeedCreatedUpdated : BaseXmlParserTest(
    feedPath = "atom-feed-created-updated.xml",
    channelTitle = "tonsky.me",
    channelLink = "https://tonsky.me/",
    channelLastBuildDate = "2023-12-22T18:46:17Z",
    channelDescription = "Nikita Prokopov’s blog",
    articleTitle = "The Absolute Minimum Every Software Developer Must Know About Unicode in 2023 (Still No Excuses!)",
    articlePubDate = "2023-10-02T00:00:00Z",
    articleLink = "https://tonsky.me/blog/unicode/",
    articleImage = "https://tonsky.me/blog/unicode/utf8_trend@2x.png",
    articleAuthor = "Nikita Prokopov",
    articleGuid = "https://tonsky.me/blog/unicode/",
    articleDescription = "Modern extension to classic 2003 article by Joel Spolsky",
    articleContent = "<p><em>Translations: <a href=\"https://www.outofpluto.com/blog/nikita-prokopov-must-read-article-about-utf-8/\">French</a> <a href=\"https://blog.xinshijiededa.men/unicode/\">Chinese</a></em></p>\n" +
            "<p>Twenty years ago, <a href=\"https://www.joelonsoftware.com/2003/10/08/the-absolute-minimum-every-software-developer-absolutely-positively-must-know-about-unicode-and-character-sets-no-excuses/\">Joel Spolsky wrote</a>:</p>\n" +
            "<blockquote>\n" +
            "  <p>There Ain’t No Such Thing As Plain Text.</p>\n" +
            "  <p>It does not make sense to have a string without knowing what encoding it uses. You can no longer stick your head in the sand and pretend that “plain” text is ASCII.</p>\n" +
            "</blockquote>\n" +
            "<p>A lot has changed in 20 years. In 2003, the main question was: what encoding is this?</p>\n" +
            "<p>In 2023, it’s no longer a question: with a 98% probability, it’s UTF-8. Finally! We can stick our heads in the sand again!</p>\n" +
            "<figure>\n" +
            "<img src=\"https://tonsky.me/blog/unicode/utf8_trend@2x.png\"></figure>\n" +
            "<p>The question now becomes: how do we use UTF-8 correctly? Let’s see!</p>\n" +
            "<h1 id=\"what-is-unicode\">What is Unicode?</h1>\n" +
            "<p>Unicode is a standard that aims to unify all human languages, both past and present, and make them work with computers.</p>\n" +
            "<p>In practice, Unicode is a table that assigns unique numbers to different characters. </p>\n" +
            "<p>For example:</p>\n" +
            "<ul>\n" +
            "  <li>The Latin letter <code>A</code> is assigned the number <code>65</code>.</li>\n" +
            "  <li>The Arabic Letter Seen <code>س</code> is <code>1587</code>.</li>\n" +
            "  <li>The Katakana Letter Tu <code>ツ</code> is <code>12484</code></li>\n" +
            "  <li>The Musical Symbol G Clef <code>\uD834\uDD1E</code> is <code>119070</code>.</li>\n" +
            "  <li><code class=\"emoji\">\uD83D\uDCA9</code> is <code>128169</code>.</li>\n" +
            "</ul>\n" +
            "<p>Unicode refers to these numbers as <em>code points</em>.</p>\n" +
            "<p>Since everybody in the world agrees on which numbers correspond to which characters, and we all agree to use Unicode, we can read each other’s texts.</p>\n" +
            "<p class=\"loud\">Unicode == character ⟷ code point.</p>\n" +
            "<h1 id=\"how-big-is-unicode\">How big is Unicode?</h1>\n" +
            "<p>Currently, the largest defined code point is 0x10FFFF. That gives us a space of about 1.1 million code points.</p>\n" +
            "<p>About 170,000, or 15%, are currently defined. An additional 11% are reserved for private use. The rest, about 800,000 code points, are not allocated at the moment. They could become characters in the future.</p>\n" +
            "<p>Here’s roughly how it looks:</p>\n" +
            "<figure>\n" +
            "<img src=\"https://tonsky.me/blog/unicode/overview@2x.png\"></figure>\n" +
            "<p>Large square == plane == 65,536 characters. Small one == 256 characters. The entire ASCII is half of a small red square in the top left corner.</p>\n" +
            "<h1 id=\"whats-private-use\">What’s Private Use?</h1>\n" +
            "<p>These are code points reserved for app developers and will never be defined by Unicode itself.</p>\n" +
            "<p>For example, there’s no place for the Apple logo in Unicode, so Apple puts it at <code>U+F8FF</code> which is within the Private Use block. In any other font, it’ll render as missing glyph <code>\uDBC2\uDCFA</code>, but in fonts that ship with macOS, you’ll see <img class=\"inline\" src=\"https://tonsky.me/blog/unicode/apple-logo@2x.png\">.</p>\n" +
            "<p>The Private Use Area is mostly used by icon fonts:</p>\n" +
            "<figure>\n" +
            "<img src=\"https://tonsky.me/blog/unicode/nerd_font@2x.png\"><figcaption>Isn’t it a beauty? It’s all text!</figcaption></figure>\n" +
            "<h1 id=\"what-does-u1f4a9-mean\">What does <code>U+1F4A9</code> mean?</h1>\n" +
            "<p>It’s a convention for how to write code point values. The prefix <code>U+</code> means, well, Unicode, and <code>1F4A9</code> is a code point number in hexadecimal.</p>\n" +
            "<p>Oh, and <code>U+1F4A9</code> specifically is <code class=\"emoji\">\uD83D\uDCA9</code>.</p>\n" +
            "<h1 id=\"whats-utf-8-then\">What’s UTF-8 then?</h1>\n" +
            "<p>UTF-8 is an encoding. Encoding is how we store code points in memory.</p>\n" +
            "<p>The simplest possible encoding for Unicode is UTF-32. It simply stores code points as 32-bit integers. So <code>U+1F4A9</code> becomes <code>00 01 F4 A9</code>, taking up four bytes. Any other code point in UTF-32 will also occupy four bytes. Since the highest defined code point is <code>U+10FFFF</code>, any code point is guaranteed to fit.</p>\n" +
            "<p>UTF-16 and UTF-8 are less straightforward, but the ultimate goal is the same: to take a code point and encode it as bytes.</p>\n" +
            "<p>Encoding is what you’ll actually deal with as a programmer.</p>\n" +
            "<h1 id=\"how-many-bytes-are-in-utf-8\">How many bytes are in UTF-8?</h1>\n" +
            "<p>UTF-8 is a variable-length encoding. A code point might be encoded as a sequence of one to four bytes.</p>\n" +
            "<p>This is how it works:</p>\n" +
            "<table>\n" +
            "  <thead>\n" +
            "    <tr>\n" +
            "      <th>Code point</th>\n" +
            "      <th>Byte 1</th>\n" +
            "      <th>Byte 2</th>\n" +
            "      <th>Byte 3</th>\n" +
            "      <th>Byte 4</th>\n" +
            "    </tr>\n" +
            "  </thead>\n" +
            "  <tbody>\n" +
            "    <tr>\n" +
            "      <td>U+<code>0000</code>..<code>007F</code></td>\n" +
            "      <td><code>0xxxxxxx</code></td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "      <td>U+<code>0080</code>..<code>07FF</code></td>\n" +
            "      <td><code>110xxxxx</code></td>\n" +
            "      <td><code>10xxxxxx</code></td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "      <td>U+<code>0800</code>..<code>FFFF</code></td>\n" +
            "      <td><code>1110xxxx</code></td>\n" +
            "      <td><code>10xxxxxx</code></td>\n" +
            "      <td><code>10xxxxxx</code></td>\n" +
            "    </tr>\n" +
            "    <tr>\n" +
            "      <td>U+<code>10000</code>..<code>10FFFF</code></td>\n" +
            "      <td><code>11110xxx</code></td>\n" +
            "      <td><code>10xxxxxx</code></td>\n" +
            "      <td><code>10xxxxxx</code></td>\n" +
            "      <td><code>10xxxxxx</code></td>\n" +
            "    </tr>\n" +
            "  </tbody>\n" +
            "</table><p>If you combine this with the Unicode table, you’ll see that English is encoded with 1 byte, Cyrillic, Latin European languages, Hebrew and Arabic need 2, and Chinese, Japanese, Korean, other Asian languages, and Emoji need 3 or 4.</p>\n" +
            "<p>A few important points here:</p>\n" +
            "<p>First, UTF-8 is byte-compatible with ASCII. The code points 0..127, the former ASCII, are encoded with one byte, and it’s the same exact byte. <code>U+0041</code> (<code>A</code>, Latin Capital Letter A) is just <code>41</code>, one byte.</p>\n" +
            "<p>Any pure ASCII text is also a valid UTF-8 text, and any UTF-8 text that only uses codepoints 0..127 can be read as ASCII directly.</p>\n" +
            "<p>Second, UTF-8 is space-efficient for basic Latin. That was one of its main selling points over UTF-16. It might not be fair for texts all over the world, but for technical strings like HTML tags or JSON keys, it makes sense.</p>\n" +
            "<p>On average, UTF-8 tends to be a pretty good deal, even for non-English computers. And for English, there’s no comparison.</p>\n" +
            "<p>Third, UTF-8 has error detection and recovery built-in. The first byte’s prefix always looks different from bytes 2-4. This way you can always tell if you are looking at a complete and valid sequence of UTF-8 bytes or if something is missing (for example, you jumped it the middle of the sequence). Then you can correct that by moving forward or backward until you find the beginning of the correct sequence.</p>\n" +
            "<p>And a couple of important consequences:</p>\n" +
            "<ul>\n" +
            "  <li>You CAN’T determine the length of the string by counting bytes.</li>\n" +
            "  <li>You CAN’T randomly jump into the middle of the string and start reading.</li>\n" +
            "  <li>You CAN’T get a substring by cutting at arbitrary byte offsets. You might cut off part of the character.</li>\n" +
            "</ul>\n" +
            "<p>Those who do will eventually meet this bad boy: �</p>\n" +
            "<h1 id=\"whats-\">What’s �?</h1>\n" +
            "<p><code>U+FFFD</code>, the Replacement Character, is simply another code point in the Unicode table. Apps and libraries can use it when they detect Unicode errors.</p>\n" +
            "<p>If you cut half of the code point off, there’s not much left to do with the other half, except displaying an error. That’s when � is used.</p>\n" +
            "<pre><code>var bytes = &quot;Аналитика&quot;.getBytes(&quot;UTF-8&quot;);\n" +
            "var partial = Arrays.copyOfRange(bytes, 0, 11);\n" +
            "new String(partial, &quot;UTF-8&quot;); // =&gt; &quot;Анал�&quot;</code></pre>\n" +
            "<h1 id=\"wouldnt-utf-32-be-easier-for-everything\">Wouldn’t UTF-32 be easier for everything?</h1>\n" +
            "<p>NO.</p>\n" +
            "<p>UTF-32 is great for operating on code points. Indeed, if every code point is always 4 bytes, then <code>strlen(s) == sizeof(s) / 4</code>, <code>substring(0, 3) == bytes[0, 12]</code>, etc.</p>\n" +
            "<p>The problem is, you don’t want to operate on code points. A code point is not a unit of writing; one code point is not always a single character. What you should be iterating on is called “<strong>extended grapheme clusters</strong>”, or graphemes for short.</p>\n" +
            "<p>A grapheme is a minimally distinctive unit of writing in the context of a particular writing system. <code>ö</code> is one grapheme. <code>é</code> is one too. And <code>각</code>. Basically, grapheme is what the user thinks of as a single character.</p>\n" +
            "<p>The problem is, in Unicode, some graphemes are encoded with multiple code points!</p>\n" +
            "<figure>\n" +
            "<img src=\"https://tonsky.me/blog/unicode/graphemes@2x.png\"></figure>\n" +
            "<p>For example, <code>é</code> (a single grapheme) is encoded in Unicode as <code>e</code> (U+0065 Latin Small Letter E) + <code>´</code> (U+0301 Combining Acute Accent). Two code points!</p>\n" +
            "<p>It can also be more than two:</p>\n" +
            "<ul>\n" +
            "  <li><code class=\"emoji\">☹\uFE0F</code> is <code>U+2639</code> + <code>U+FE0F</code></li>\n" +
            "  <li><code class=\"emoji\">\uD83D\uDC68\u200D\uD83C\uDFED</code> is <code>U+1F468</code> + <code>U+200D</code> + <code>U+1F3ED</code></li>\n" +
            "  <li><code class=\"emoji\">\uD83D\uDEB5\uD83C\uDFFB\u200D♀\uFE0F</code> is <code>U+1F6B5</code> + <code>U+1F3FB</code> + <code>U+200D</code> + <code>U+2640</code> + <code>U+FE0F</code></li>\n" +
            "  <li><code>y̖̠͍̘͇͗̏̽̎͞</code> is <code>U+0079</code> + <code>U+0316</code> + <code>U+0320</code> + <code>U+034D</code> + <code>U+0318</code> + <code>U+0347</code> + <code>U+0357</code> + <code>U+030F</code> + <code>U+033D</code> + <code>U+030E</code> + <code>U+035E</code></li>\n" +
            "</ul>\n" +
            "<p>There’s no limit, as far as I know.</p>\n" +
            "<p>Remember, we are talking about code points here. Even in the widest encoding, UTF-32, <code class=\"emoji\">\uD83D\uDC68\u200D\uD83C\uDFED</code> will still take three 4-byte units to encode. And it still needs to be treated as a single character.</p>\n" +
            "<p>If the analogy helps, we can think of the Unicode itself (without any encodings) as being variable-length.</p>\n" +
            "<p class=\"loud\">An Extended Grapheme Cluster is a sequence of one or more Unicode code points that must be treated as a single, unbreakable character.</p>\n" +
            "<p>Therefore, we get all the problems we have with variable-length encodings, but now on code point level: you can’t take only a part of the sequence, it always should be selected, copied, edited, or deleted as a whole.</p>\n" +
            "<p>Failure to respect grapheme clusters leads to bugs like this:</p>\n" +
            "<figure>\n" +
            "<img src=\"https://tonsky.me/blog/unicode/error1.png\"></figure>\n" +
            "<p>or this:</p>\n" +
            "<figure>\n" +
            "  <video autoplay=\"\" muted=\"\" loop=\"\" preload=\"auto\" playsinline=\"\" controls=\"\">\n" +
            "    <source src=\"https://tonsky.me/blog/unicode/intellij@2x.mp4\" type=\"video/mp4\">\n" +
            "  </video>\n" +
            "<figcaption>Just to be clear: this is NOT correct behavior</figcaption></figure>\n" +
            "<p>Using UTF-32 instead of UTF-8 will not make your life any easier in regards to extended grapheme clusters. And extended grapheme clusters is what you should care about.</p>\n" +
            "<p class=\"loud\">Code points — \uD83E\uDD71. Graphemes — \uD83D\uDE0D</p>\n" +
            "<h1 id=\"is-unicode-hard-only-because-of-emojis\">Is Unicode hard only because of emojis?</h1>\n" +
            "<p>Not really. Extended Grapheme Clusters are also used for alive, actively used languages. For example:</p>\n" +
            "<ul>\n" +
            "  <li><code>ö</code> (German) is a single character, but multiple code points (<code>U+006F U+0308</code>).</li>\n" +
            "  <li><code>ą́</code> (Lithuanian) is <code>U+00E1 U+0328</code>.</li>\n" +
            "  <li><code>각</code> (Korean) is <code>U+1100 U+1161 U+11A8</code>.</li>\n" +
            "</ul>\n" +
            "<p>So no, it’s not just about emojis.</p>\n" +
            "<h1 id=\"whats-length\">What’s &quot;\uD83E\uDD26\uD83C\uDFFC\u200D♂\uFE0F&quot;.length?</h1>\n" +
            "<p>The question is inspired by <a href=\"https://hsivonen.fi/string-length/\">this brilliant article</a>.</p>\n" +
            "<p>Different programming languages will happily give you different answers.</p>\n" +
            "<p>Python 3:</p>\n" +
            "<pre><code>&gt;&gt;&gt; len(&quot;\uD83E\uDD26\uD83C\uDFFC\u200D♂\uFE0F&quot;)\n" +
            "5</code></pre>\n" +
            "<p>JavaScript / Java / C#:</p>\n" +
            "<pre><code>&gt;&gt; &quot;\uD83E\uDD26\uD83C\uDFFC\u200D♂\uFE0F&quot;.length\n" +
            "7</code></pre>\n" +
            "<p>Rust:</p>\n" +
            "<pre><code>println!(&quot;{}&quot;, &quot;\uD83E\uDD26\uD83C\uDFFC\u200D♂\uFE0F&quot;.len());\n" +
            "// =&gt; 17</code></pre>\n" +
            "<p>As you can guess, different languages use different internal string representations (UTF-32, UTF-16, UTF-8) and report length in whatever units they store characters in (ints, shorts, bytes).</p>\n" +
            "<p>BUT! If you ask any normal person, one that isn’t burdened with computer internals, they’ll give you a straight answer: 1. The length of <code class=\"emoji\">\uD83E\uDD26\uD83C\uDFFC\u200D♂\uFE0F</code> string is 1.</p>\n" +
            "<p>That’s what extended grapheme clusters are all about: what <em>humans</em> perceive as a single character. And in this case, <code class=\"emoji\">\uD83E\uDD26\uD83C\uDFFC\u200D♂\uFE0F</code> is undoubtedly a single character.</p>\n" +
            "<p>The fact that <code class=\"emoji\">\uD83E\uDD26\uD83C\uDFFC\u200D♂\uFE0F</code> consists of 5 code points (<code>U+1F926 U+1F3FB U+200D U+2642 U+FE0F</code>) is mere implementation detail. It should not be broken apart, it should not be counted as multiple characters, the text cursor should not be positioned inside it, it shouldn’t be partially selected, etc.</p>\n" +
            "<p>For all intents and purposes, this is an atomic unit of text. Internally, it could be encoded whatever, but for user-facing API, it should be treated as a whole.</p>\n" +
            "<p>The only two modern languages that get it right are Swift:</p>\n" +
            "<pre><code>print(&quot;\uD83E\uDD26\uD83C\uDFFC\u200D♂\uFE0F&quot;.count)\n" +
            "// =&gt; 1</code></pre>\n" +
            "<p>and Elixir:</p>\n" +
            "<pre><code>String.length(&quot;\uD83E\uDD26\uD83C\uDFFC\u200D♂\uFE0F&quot;)\n" +
            "// =&gt; 1</code></pre>\n" +
            "<p>Basically, there are two layers:</p>\n" +
            "<ol start=\"1\">\n" +
            "  <li>Internal, computer-oriented. How to copy strings, send them over the network, store on disk, etc. This is where you need encodings like UTF-8. Swift uses UTF-8 internally, but it might as well be UTF-16 or UTF-32. What&#x27;s important is that you only use it to copy strings as a whole and never to analyze their content.</li>\n" +
            "  <li>External, human-facing API. Character count in UI. Taking first 10 characters to generate preview. Searching in text. Methods like <code>.count</code> or <code>.substring</code>. Swift gives you <em>a view</em> that pretends the string is a sequence of grapheme clusters. And that view behaves like any human would expect: it gives you 1 for <code>&quot;\uD83E\uDD26\uD83C\uDFFC\u200D♂\uFE0F&quot;.count</code>.</li>\n" +
            "</ol>\n" +
            "<p>I hope more languages adopt this design soon.</p>\n" +
            "<p>Question to the reader: what to you think <code>&quot;ẇ͓̞͒͟͡ǫ̠̠̉̏͠͡ͅr̬̺͚̍͛̔͒͢d̠͎̗̳͇͆̋̊͂͐&quot;.length</code> should be?</p>\n" +
            "<h1 id=\"how-do-i-detect-extended-grapheme-clusters-then\">How do I detect extended grapheme clusters then?</h1>\n" +
            "<p>Unfortunately, most languages choose the easy way out and let you iterate through strings with 1-2-4-byte chunks, but not with grapheme clusters.</p>\n" +
            "<p>It makes no sense and has no semantics, but since it’s the default, programmers don’t think twice, and we see corrupted strings as the result:</p>\n" +
            "<figure>\n" +
            "<img src=\"https://tonsky.me/blog/unicode/stdlib@2x.png\"></figure>\n" +
            "<p>“I know, I’ll use a library to do strlen()!” — nobody, ever.</p>\n" +
            "<p>But that’s exactly what you should be doing! Use a proper Unicode library! Yes, for basic stuff like <code>strlen</code> or <code>indexOf</code> or <code>substring</code>!</p>\n" +
            "<p>For example:</p>\n" +
            "<ol start=\"1\">\n" +
            "  <li>C/C++/Java: use <a href=\"https://github.com/unicode-org/icu\">ICU</a>. It’s a library from Unicode itself that encodes all the rules about text segmentation.</li>\n" +
            "  <li>C#: use <code>TextElementEnumerator</code>, which is kept up to date with Unicode as far as I can tell.</li>\n" +
            "  <li>Swift: just stdlib. Swift does the right thing by default.</li>\n" +
            "  <li>UPD: Erlang/Elixir seem to be doing the right thing, too.</li>\n" +
            "  <li>For other languages, there’s probably a library or binding for ICU.</li>\n" +
            "  <li>Roll your own. Unicode <a href=\"https://www.unicode.org/reports/tr29/#Grapheme_Cluster_Boundaries\">publishes</a> rules and tables in a machine-readable format, and all the libraries above are based on them.</li>\n" +
            "</ol>\n" +
            "<p>But whatever you choose, make sure it’s on the recent enough version of Unicode (15.1 at the moment of writing), because the definition of graphemes changes from version to version. For example, Java’s <code>java.text.BreakIterator</code> is a no-go: it’s based on a very old version of Unicode and not updated.</p>\n" +
            "<p class=\"loud\">Use a library</p>\n" +
            "<p>IMO, the whole situation is a shame. Unicode should be in the stdlib of every language by default. It’s the lingua franca of the internet! It’s not even new: we’ve been living with Unicode for 20 years now.</p>\n" +
            "<h1 id=\"wait-rules-are-changing\">Wait, rules are changing?</h1>\n" +
            "<p>Yes! Ain’t it cool?</p>\n" +
            "<p>(I know, it ain’t)</p>\n" +
            "<p>Starting roughly in 2014, Unicode has been releasing a major revision of their standard every year. This is where you get your new emojis from — Android and iOS updates in the Fall usually include the newest Unicode standard among other things.</p>\n" +
            "<figure>\n" +
            "<img src=\"https://tonsky.me/blog/unicode/versions@2x.png\"></figure>\n" +
            "<p>What’s sad for us is that the rules defining grapheme clusters change every year as well. What is considered a sequence of two or three separate code points today might become a grapheme cluster tomorrow! There’s no way to know! Or prepare!</p>\n" +
            "<p>Even worse, different versions of your own app might be running on different Unicode standards and report different string lengths!</p>\n" +
            "<p>But that’s the reality we live in. You don’t really have a choice here. You can’t ignore Unicode or Unicode updates if you want to stay relevant and provide a decent user experience. So, buckle up, embrace, and update.</p>\n" +
            "<p class=\"loud\">Update yearly</p>\n" +
            "<h1 id=\"why-is-a----\">Why is &quot;Å&quot; !== &quot;Å&quot; !== &quot;Å&quot;?</h1>\n" +
            "<figure>\n" +
            "<img src=\"https://tonsky.me/blog/unicode/spider_men@2x.jpg\"></figure>\n" +
            "<p>Copy any of these to your JavaScript console:</p>\n" +
            "<pre><code>&quot;Å&quot; === &quot;Å&quot;\n" +
            "&quot;Å&quot; === &quot;Å&quot;\n" +
            "&quot;Å&quot; === &quot;Å&quot;</code></pre>\n" +
            "<p>What do you get? False? You should get false, and it’s not a mistake.</p>\n" +
            "<p>Remember earlier when I said that <code>ö</code> is two code points, <code>U+006F U+0308</code>? Basically, Unicode offers more than one way to write characters like <code>ö</code> or <code>Å</code>. You can:</p>\n" +
            "<ol start=\"1\">\n" +
            "  <li>Compose <code>Å</code> from normal Latin <code>A</code> + a combining character,</li>\n" +
            "  <li>OR there’s also a pre-composed code point <code>U+00C5</code> that does that for you.</li>\n" +
            "</ol>\n" +
            "<p>They will look the same (<code>Å</code> vs <code>Å</code>), they should work the same, and for all intents and purposes, they are considered exactly the same. The only difference is the byte representation.</p>\n" +
            "<p>That’s why we need normalization. There are four forms:</p>\n" +
            "<p><strong>NFD</strong> tries to explode everything to the smallest possible pieces, and also sorts pieces in a canonical order if there is more than one.</p>\n" +
            "<p><strong>NFC</strong>, on the other hand, tries to combine everything into pre-composed form if one exists.</p>\n" +
            "<figure>\n" +
            "<img src=\"https://tonsky.me/blog/unicode/normalization@2x.png\"></figure>\n" +
            "<p>For some characters there are also multiple versions of them in Unicode. For example, there’s <code>U+00C5 Latin Capital Letter A with Ring Above</code>, but there’s also <code>U+212B Angstrom Sign</code> which looks the same. </p>\n" +
            "<p>These are also replaced during normalization:</p>\n" +
            "<figure>\n" +
            "<img src=\"https://tonsky.me/blog/unicode/normalization_clones@2x.png\"></figure>\n" +
            "<p>NFD and NFC are called “canonical normalization”. Another two forms are “compatibility normalization”:</p>\n" +
            "<p><strong>NFKD</strong> tries to explode everything and replaces visual variants with default ones.</p>\n" +
            "<p><strong>NFKC</strong> tries to combine everything while replacing visual variants with default ones.</p>\n" +
            "<figure>\n" +
            "<img src=\"https://tonsky.me/blog/unicode/normalization_compat@2x.png\"></figure>\n" +
            "<p>Visual variants are separate Unicode code points that represent the same character but are supposed to render differently. Like, <code>①</code> or <code>⁹</code> or <code>\uD835\uDD4F</code>. We want to be able to find both <code>&quot;x&quot;</code> and <code>&quot;2&quot;</code> in a string like <code>&quot;\uD835\uDD4F²&quot;</code>, don’t we?</p>\n" +
            "<figure>\n" +
            "<img src=\"https://tonsky.me/blog/unicode/x_variants@2x.png\"><figcaption>All of these have their own code points, but they are also all Xs</figcaption></figure>\n" +
            "<p>Why does the <code>ﬁ</code> ligature even have its own code point? No idea. A lot can happen in a million characters.</p>\n" +
            "<p class=\"loud\">Before comparing strings or searching for a substring, normalize!</p>\n" +
            "<h1 id=\"unicode-is-locale-dependent\">Unicode is locale-dependent</h1>\n" +
            "<p>The Russian name Nikolay is written like this:</p>\n" +
            "<figure>\n" +
            "<img src=\"https://tonsky.me/blog/unicode/nikolay_ru.png\"></figure>\n" +
            "<p>and encoded in Unicode as <code>U+041D 0438 043A 043E 043B 0430 0439</code>.</p>\n" +
            "<p>The Bulgarian name Nikolay is written:</p>\n" +
            "<figure>\n" +
            "<img src=\"https://tonsky.me/blog/unicode/nikolay_bg.png\"></figure>\n" +
            "<p>and encoded in Unicode as <code>U+041D 0438 043A 043E 043B 0430 0439</code>. Exactly the same!</p>\n" +
            "<p>Wait a second! How does the computer know when to render Bulgarian-style glyphs and when to use Russian ones?</p>\n" +
            "<p>Short answer: it doesn’t. Unfortunately, Unicode is not a perfect system, and it has many shortcomings. Among them is assigning the same code point to glyphs that are supposed to look differently, like Cyrillic Lowercase K and Bulgarian Lowercase K (both are <code>U+043A</code>).</p>\n" +
            "<p>From what I understand, Asian people <a href=\"https://en.wikipedia.org/wiki/Han_unification\">get it much worse</a>: many Chinese, Japanese, and Korean logograms that are written very differently get assigned the same code point:</p>\n" +
            "<figure>\n" +
            "<img src=\"https://tonsky.me/blog/unicode/han.png\"><figcaption>U+8FD4 in different locales</figcaption></figure>\n" +
            "<p>Unicode motivation is to save code points space (my guess). Information on how to render is supposed to be transferred outside of the string, as locale/language metadata.</p>\n" +
            "<p>Unfortunately, it fails the original goal of Unicode:</p>\n" +
            "<blockquote>\n" +
            "  <p>[...] no escape sequence or control code is required to specify any character in any language.</p>\n" +
            "</blockquote>\n" +
            "<p>In practice, dependency on locale brings a lot of problems:</p>\n" +
            "<ol start=\"1\">\n" +
            "  <li>Being metadata, locale often gets lost.</li>\n" +
            "  <li>People are not limited to a single locale. For example, I can read and write English (USA), English (UK), German, and Russian. Which locale should I set my computer to?</li>\n" +
            "  <li>It’s hard to mix and match. Like Russian names in Bulgarian text or vice versa. Why not? It’s the internet, people from all cultures hang out here.</li>\n" +
            "  <li>There’s no place to specify the locale. Even making the two screenshots above was non-trivial because in most software, there’s no dropdown or text input to change locale.</li>\n" +
            "  <li>When needed, it had to be guessed. For example, Twitter tries to guess the locale from the text of the tweet itself (because where else could it get it from?) and sometimes gets it wrong:</li>\n" +
            "</ol>\n" +
            "<figure>\n" +
            "<a href=\"https://twitter.com/nikitonsky/status/1171115067112398849\"><img src=\"https://tonsky.me/blog/unicode/twitter_locale.jpg\"></a></figure>\n" +
            "<h1 id=\"why-does-stringtolowercase-accepts-locale-as-an-argument\">Why does <code>String::toLowerCase()</code> accepts Locale as an argument?</h1>\n" +
            "<p>Another unfortunate example of locale dependence is the Unicode handling of dotless <code>i</code> in the Turkish language.</p>\n" +
            "<p>Unlike English, Turks have two <code>I</code> variants: dotted and dotless. Unicode decided to reuse <code>I</code> and <code>i</code> from ASCII and only add two new code points: <code>İ</code> and <code>ı</code>.</p>\n" +
            "<p>Unfortunately, that made <code>toLowerCase</code>/<code>toUpperCase</code> behave differently on the same input:</p>\n" +
            "<pre><code>var en_US = Locale.of(&quot;en&quot;, &quot;US&quot;);\n" +
            "var tr = Locale.of(&quot;tr&quot;);\n" +
            "\n" +
            "&quot;I&quot;.toLowerCase(en_US); // =&gt; &quot;i&quot;\n" +
            "&quot;I&quot;.toLowerCase(tr);    // =&gt; &quot;ı&quot;\n" +
            "\n" +
            "&quot;i&quot;.toUpperCase(en_US); // =&gt; &quot;I&quot;\n" +
            "&quot;i&quot;.toUpperCase(tr);    // =&gt; &quot;İ&quot;</code></pre>\n" +
            "<p>So no, you can’t convert string to lowercase without knowing what language that string is written in.</p>\n" +
            "<h1 id=\"i-live-in-the-usuk-should-i-even-care\">I live in the US/UK, should I even care?</h1>\n" +
            "<figure>\n" +
            "<img src=\"https://tonsky.me/blog/unicode/english@2x.png\"></figure>\n" +
            "<ul>\n" +
            "  <li>quotation marks <code>“</code> <code>”</code> <code>‘</code> <code>’</code>, </li>\n" +
            "  <li>apostrophe <code>’</code>,</li>\n" +
            "  <li>dashes <code>–</code> <code>—</code>,</li>\n" +
            "  <li>different variations of spaces (figure, hair, non-breaking),</li>\n" +
            "  <li>bullets <code>•</code> <code>■</code> <code>☞</code>,</li>\n" +
            "  <li>currency symbols other than the <code>\$</code> (kind of tells you who invented computers, doesn’t it?): <code>€</code> <code>¢</code> <code>£</code>,</li>\n" +
            "  <li>mathematical signs—plus <code>+</code> and equals <code>=</code> are part of ASCII, but minus <code>−</code> and multiply <code>×</code> are not <nobr>¯\\_(ツ)_/¯</nobr>,</li>\n" +
            "  <li>various other signs <code>©</code> <code>™</code> <code>¶</code> <code>†</code> <code>§</code>.</li>\n" +
            "</ul>\n" +
            "<p>Hell, you can’t even spell <code>café</code>, <code>piñata</code>, or <code>naïve</code> without Unicode. So yes, we are all in it together, even Americans.</p>\n" +
            "<p>Touché.</p>\n" +
            "<h1 id=\"what-are-surrogate-pairs\">What are surrogate pairs?</h1>\n" +
            "<p>That goes back to Unicode v1. The first version of Unicode was supposed to be fixed-width. A 16-bit fixed width, to be exact:</p>\n" +
            "<figure>\n" +
            "<img src=\"https://tonsky.me/blog/unicode/unicode1@2x.png\"><figcaption>Version 1.0 of the Unicode Standard, October 1991</figcaption></figure>\n" +
            "<p>They believed 65,536 characters would be enough for all human languages. They were almost right!</p>\n" +
            "<p>When they realized they needed more code points, UCS-2 (an original version of UTF-16 without surrogates) was already used in many systems. 16 bit, fixed-width, it only gives you 65,536 characters. What can you do?</p>\n" +
            "<p>Unicode decided to allocate some of these 65,536 characters to encode higher code points, essentially converting fixed-width UCS-2 into variable-width UTF-16.</p>\n" +
            "<p>A surrogate pair is two UTF-16 units used to encode a single Unicode code point. For example, <code>D83D DCA9</code> (two 16-bit units) encodes <em>one</em> code point, <code>U+1F4A9</code>.</p>\n" +
            "<p>The top 6 bits in surrogate pairs are used for the mask, leaving 2×10 free bits to spare:</p>\n" +
            "<pre><code>   High Surrogate          Low Surrogate\n" +
            "        D800        ++          DC00\n" +
            "1101 10?? ???? ???? ++ 1101 11?? ???? ????</code></pre>\n" +
            "<p>Technically, both halves of the surrogate pair can be seen as Unicode code points, too. In practice, the whole range from <code>U+D800</code> to <code>U+DFFF</code> is allocated as “for surrogate pairs only”. Code points from there are not even considered valid in any other encodings.</p>\n" +
            "<figure>\n" +
            "<img src=\"https://tonsky.me/blog/unicode/bmp@2x.png\"><figcaption>This space on a very crammed Basic Multilingual Plane will never be used for anything good ever again</figcaption></figure>\n" +
            "<h1 id=\"is-utf-16-still-alive\">Is UTF-16 still alive?</h1>\n" +
            "<p>Yes!</p>\n" +
            "<p>The promise of a fixed-width encoding that covers all human languages was so compelling that many systems were eager to adopt it. Among them were Microsoft Windows, Objective-C, Java, JavaScript, .NET, Python 2, QT, SMS, and CD-ROM!</p>\n" +
            "<p>Since then, Python has moved on, CD-ROM has become obsolete, but the rest is stuck with UTF-16 or even UCS-2. So UTF-16 lives there as in-memory representation.</p>\n" +
            "<p>In practical terms today, UTF-16 has roughly the same usability as UTF-8. It’s also variable-length; counting UTF-16 units is as useless as counting bytes or code points, grapheme clusters are still a pain, etc. The only difference is memory requirements.</p>\n" +
            "<p>The only downside of UTF-16 is that everything else is UTF-8, so it requires conversion every time a string is read from the network or from disk.</p>\n" +
            "<p>Also, fun fact: the number of planes Unicode has (17) is defined by how much you can express with surrogate pairs in UTF-16.</p>\n" +
            "<h1 id=\"conclusion\">Conclusion</h1>\n" +
            "<p>To sum it up:</p>\n" +
            "<ul>\n" +
            "  <li>Unicode has won.</li>\n" +
            "  <li>UTF-8 is the most popular encoding for data in transfer and at rest.</li>\n" +
            "  <li>UTF-16 is still sometimes used as an in-memory representation.</li>\n" +
            "  <li>The two most important views for strings are bytes (allocate memory/copy/encode/decode) and extended grapheme clusters (all semantic operations).</li>\n" +
            "  <li>Using code points for iterating over a string is wrong. They are not the basic unit of writing. One grapheme could consist of multiple code points.</li>\n" +
            "  <li>To detect grapheme boundaries, you need Unicode tables.</li>\n" +
            "  <li>Use a Unicode library for everything Unicode, even boring stuff like <code>strlen</code>, <code>indexOf</code> and <code>substring</code>.</li>\n" +
            "  <li>Unicode updates every year, and rules sometimes change.</li>\n" +
            "  <li>Unicode strings need to be normalized before they can be compared.</li>\n" +
            "  <li>Unicode depends on locale for some operations and for rendering.</li>\n" +
            "  <li>All this is important even for pure English text.</li>\n" +
            "</ul>\n" +
            "<p>Overall, yes, Unicode is not perfect, but the fact that</p>\n" +
            "<ol start=\"1\">\n" +
            "  <li>an encoding exists that covers all possible languages at once,</li>\n" +
            "  <li>the entire world agrees to use it,</li>\n" +
            "  <li>we can completely forget about encodings and conversions and all that stuff</li>\n" +
            "</ol>\n" +
            "<p>is a miracle. Send this to your fellow programmers so they can learn about it, too.</p>\n" +
            "<p class=\"loud\">There’s such a thing as plain text, and it’s encoded with UTF-8.</p>\n" +
            "<p>Thanks Lev Walkin and my patrons for reading early drafts of this article.</p>",
)
