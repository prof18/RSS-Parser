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


import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import android.text.Html
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast

import com.prof.rssparser.Article
import com.prof.rssparser.Parser
import kotlinx.android.synthetic.main.content_main.*

import java.util.ArrayList

class MainActivity : AppCompatActivity() {

    //    private var mRecyclerView: RecyclerView? = null
//    private var mAdapter: ArticleAdapter? = null
//    private var mSwipeRefreshLayout: SwipeRefreshLayout? = null
//    private var progressBar: ProgressBar? = null
    private val urlString = "https://www.androidauthority.com/feed"

    private lateinit var mAdapter: ArticleAdapter

    val isNetworkAvailable: Boolean
        get() {
            val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        list.layoutManager = LinearLayoutManager(this)
        list.itemAnimator = DefaultItemAnimator()
        list.setHasFixedSize(true)


        container.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark)
        container.canChildScrollUp()
        container.setOnRefreshListener {
            mAdapter.notifyDataSetChanged()
            container.isRefreshing = true
            loadFeed()
        }

        if (!isNetworkAvailable) {

            val builder = AlertDialog.Builder(this)
            builder.setMessage(R.string.alert_message)
                    .setTitle(R.string.alert_title)
                    .setCancelable(false)
                    .setPositiveButton(R.string.alert_positive
                    ) { dialog, id -> finish() }

            val alert = builder.create()
            alert.show()

        } else if (isNetworkAvailable) {
            loadFeed()
        }
    }

    fun loadFeed() {

        if (!container.isRefreshing)
            progressBar!!.visibility = View.VISIBLE

        val parser = Parser()
        parser.execute(urlString)
        parser.onFinish(object : Parser.OnTaskCompleted {
            //what to do when the parsing is done
            override fun onTaskCompleted(articleList: ArrayList<Article>) {
                //list is an Array List with all article's information
                //set the adapter to recycler view
                mAdapter = ArticleAdapter(articleList)
                list.adapter = mAdapter
                progressBar!!.visibility = View.GONE
                container.isRefreshing = false

            }

            //what to do in case of error
            override fun onError(exception: Exception) {

                runOnUiThread {
                    progressBar!!.visibility = View.GONE
                    container.isRefreshing = false
                    Toast.makeText(this@MainActivity, "Unable to load data.",
                            Toast.LENGTH_LONG).show()
                    Log.i("Unable to load ", "articles")
                }
            }
        })
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        if (id == R.id.action_settings) {
            val alertDialog = androidx.appcompat.app.AlertDialog.Builder(this@MainActivity).create()
            alertDialog.setTitle(R.string.app_name)
            alertDialog.setMessage(Html.fromHtml(this@MainActivity.getString(R.string.info_text) +
                    " <a href='http://github.com/prof18/RSS-Parser'>GitHub.</a>" +
                    this@MainActivity.getString(R.string.author)))
            alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "OK"
            ) { dialog, which -> dialog.dismiss() }
            alertDialog.show()

            (alertDialog.findViewById<View>(android.R.id.message) as TextView).movementMethod = LinkMovementMethod.getInstance()
        }

        return super.onOptionsItemSelected(item)
    }
}


