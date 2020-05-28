package com.cesoft.cesrssreader2.ui.feedlist

import android.annotation.SuppressLint
import android.text.method.LinkMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cesoft.cesrssreader2.R
import com.cesoft.cesrssreader2.data.entity.Feed
import kotlinx.android.synthetic.main.item_feedlist.view.*
import java.text.SimpleDateFormat
import java.util.*

class FeedlistAdapter(val feeds: MutableList<Feed>)
    : RecyclerView.Adapter<FeedlistAdapter.ViewHolder>() {

    companion object {
        private val TAG: String = FeedlistAdapter::class.simpleName!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_feedlist, parent, false))

    override fun getItemCount() = feeds.size

    override fun onBindViewHolder(holder: FeedlistAdapter.ViewHolder, position: Int) = holder.bind(feeds[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetJavaScriptEnabled")
        fun bind(feed: Feed) {

            var pubDateString = feed.pubDate

            try {
                val sourceDateString = feed.pubDate
                val sourceSdf = SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH)
                if (sourceDateString != null) {
                    val date = sourceSdf.parse(sourceDateString)
                    if (date != null) {
                        val sdf = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
                        pubDateString = sdf.format(date)
                    }
                }
            }
            catch(e: Exception) {
                Log.e(TAG, "ViewHolder:bind:e:",e)
            }


            Glide.with(itemView).load(feed.image).into(itemView.image);
            //Picasso.get().load(article.image).placeholder(R.drawable.placeholder).into(itemView.image)

            itemView.title.text = feed.title
            itemView.pubDate.text = pubDateString
            itemView.categories.text = feed.categories

            itemView.setOnClickListener {
                //show article content inside a dialog
                val articleView = WebView(itemView.context)

                articleView.settings.loadWithOverviewMode = true
                articleView.settings.javaScriptEnabled = true
                articleView.isHorizontalScrollBarEnabled = false
                articleView.webChromeClient = WebChromeClient()
                articleView.loadDataWithBaseURL(null,
                    "<style>img{display: inline; height: auto; max-width: 100%;} " +
                        "</style>\n" + "<style>iframe{ height: auto; width: auto;}" + "</style>\n"
                            + feed.content, null, "utf-8", null)

                val alertDialog = androidx.appcompat.app.AlertDialog.Builder(itemView.context).create()
                alertDialog.setTitle(feed.title)
                alertDialog.setView(articleView)
                alertDialog.setButton(androidx.appcompat.app.AlertDialog.BUTTON_NEUTRAL, "OK") { dialog, _ -> dialog.dismiss() }
                alertDialog.show()

                (alertDialog.findViewById<View>(android.R.id.message) as TextView).movementMethod = LinkMovementMethod.getInstance()
            }
        }
    }
}