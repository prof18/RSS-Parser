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

package com.prof18.rssparser.sampleandroid

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.snackbar.Snackbar
import com.prof18.rssparser.RssParser
import com.prof18.rssparser.build
import com.prof18.rssparser.sample.kotlin.R
import com.prof18.rssparser.sampleandroid.util.AlertDialogHelper

class MainActivity : AppCompatActivity() {

    private lateinit var adapter: ArticleAdapter
    private lateinit var parser: RssParser

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        StrictMode.setVmPolicy(
            VmPolicy.Builder()
                .detectLeakedClosableObjects()
                .penaltyLog()
                .build()
        )

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val swipeLayout = findViewById<SwipeRefreshLayout>(R.id.swipe_layout)
        val rootLayout = findViewById<RelativeLayout>(R.id.root_layout)

        setSupportActionBar(toolbar)

        // TODO
        parser = RssParser/*.Builder()
            .context(this)
            // If you want to provide a custom charset (the default is utf-8):
            // .charset(Charset.forName("ISO-8859-7"))
            .cacheExpirationMillis(24L * 60L * 60L * 100L) // one day
 */           .build()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.setHasFixedSize(true)

        viewModel.rssChannel.observe(this, { channel ->
            if (channel != null) {
                if (channel.title != null) {
                    title = channel.title
                }
                adapter = ArticleAdapter(channel.items)
                recyclerView.adapter = adapter
                adapter.notifyDataSetChanged()
                progressBar.visibility = View.GONE
                swipeLayout.isRefreshing = false
            }

        })

        viewModel.snackbar.observe(this, { value ->
            value?.let {
                Snackbar.make(rootLayout, value, Snackbar.LENGTH_LONG).show()
                viewModel.onSnackbarShowed()
            }
        })

        swipeLayout.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark)
        swipeLayout.canChildScrollUp()
        swipeLayout.setOnRefreshListener {
            adapter.clearArticles()
            swipeLayout.isRefreshing = true
            viewModel.fetchFeed(parser)
        }

        if (!isOnline()) {
            val builder = AlertDialog.Builder(this)
            builder.setMessage(R.string.alert_message)
                .setTitle(R.string.alert_title)
                .setCancelable(false)
                .setPositiveButton(
                    R.string.alert_positive
                ) { _, _ -> finish() }

            val alert = builder.create()
            alert.show()
        } else if (isOnline()) {
            viewModel.fetchFeed(parser)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id = item.itemId

        if (id == R.id.action_settings) {
            val alertDialog = AlertDialog.Builder(this@MainActivity).create()
            alertDialog.setTitle(R.string.app_name)
            alertDialog.setMessage(Html.fromHtml(this@MainActivity.getString(R.string.info_text) +
                " <a href='http://github.com/prof18/RSS-Parser'>GitHub.</a>" +
                this@MainActivity.getString(R.string.author)))
            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK"
            ) { dialog, _ -> dialog.dismiss() }
            alertDialog.show()

            (alertDialog.findViewById<View>(android.R.id.message) as TextView).movementMethod = LinkMovementMethod.getInstance()
        } else if (id == R.id.action_raw_parser) {
            AlertDialogHelper(
                title = R.string.alert_raw_parser_title,
                positiveButton = R.string.alert_raw_parser_positive,
                negativeButton = R.string.alert_raw_parser_negative
            ).buildInputDialog(this) { url ->
                viewModel.fetchForUrlAndParseRawData(url)
            }.show()
        }

        return super.onOptionsItemSelected(item)
    }

    @Suppress("DEPRECATION")
    fun isOnline(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            return when {
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } else {
            val activeNetworkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }
}


