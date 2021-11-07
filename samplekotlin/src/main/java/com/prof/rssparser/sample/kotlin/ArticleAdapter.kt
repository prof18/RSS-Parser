/*
 *   Copyright 2016 Marco Gomiero
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

package com.prof.rssparser.sample.kotlin

import android.annotation.SuppressLint
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.prof.rssparser.Article
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class ArticleAdapter(private var articles: List<Article>) :
    RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row, parent, false))

    override fun getItemCount() = articles.size

    override fun onBindViewHolder(holder: ArticleAdapter.ViewHolder, position: Int) =
        holder.bind(articles[position])

    fun clearArticles() {
        articles = emptyList()
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title = itemView.findViewById<TextView>(R.id.title)
        private val pubDate = itemView.findViewById<TextView>(R.id.pubDate)
        private val categoriesText = itemView.findViewById<TextView>(R.id.categories)
        private val image = itemView.findViewById<ImageView>(R.id.image)

        @SuppressLint("SetJavaScriptEnabled")
        fun bind(article: Article) {

            var pubDateString = article.pubDate

            try {
                val sourceDateString = article.pubDate
                val sourceSdf = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
                if (sourceDateString != null) {
                    val date = sourceSdf.parse(sourceDateString)
                    if (date != null) {
                        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                        pubDateString = sdf.format(date)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            title.text = article.title

            Picasso.get()
                .load(article.image)
                .placeholder(R.drawable.placeholder)
                .into(image)

            pubDate.text = pubDateString

            var categories = ""
            for (i in 0 until article.categories.size) {
                categories = if (i == article.categories.size - 1) {
                    categories + article.categories[i]
                } else {
                    categories + article.categories[i] + ", "
                }
            }

            categoriesText.text = categories

            itemView.setOnClickListener {
                //show article content inside a dialog
                val articleView = WebView(itemView.context)

                articleView.settings.loadWithOverviewMode = true
                articleView.settings.javaScriptEnabled = true
                articleView.isHorizontalScrollBarEnabled = false
                articleView.webChromeClient = WebChromeClient()
                articleView.loadDataWithBaseURL(
                    null,
                    "<style>img{display: inline; height: auto; max-width: 100%;} " +

                            "</style>\n" + "<style>iframe{ height: auto; width: auto;}" + "</style>\n" + article.content,
                    null,
                    "utf-8",
                    null
                )

                val alertDialog =
                    androidx.appcompat.app.AlertDialog.Builder(itemView.context).create()
                alertDialog.setTitle(article.title)
                alertDialog.setView(articleView)
                alertDialog.setButton(
                    androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "OK"
                ) { dialog, _ -> dialog.dismiss() }
                alertDialog.show()

                (alertDialog.findViewById<View>(android.R.id.message) as TextView).movementMethod =
                    LinkMovementMethod.getInstance()
            }
        }
    }
}