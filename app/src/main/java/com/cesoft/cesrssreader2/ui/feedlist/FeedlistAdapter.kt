package com.cesoft.cesrssreader2.ui.feedlist

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cesoft.cesrssreader2.R
import com.cesoft.cesrssreader2.data.entity.Item
import kotlinx.android.synthetic.main.item_feedlist.view.*
import java.util.*
import com.cesoft.cesrssreader2.ui.*//Extension functions


class FeedlistAdapter(val items: MutableList<Item>, val callback: OnClickListener)
    : RecyclerView.Adapter<FeedlistAdapter.ViewHolder>(), Filterable {

    companion object {
        private val TAG: String = FeedlistAdapter::class.simpleName!!
    }

    interface OnClickListener {
        fun onItemClicked(item: Item)
    }

    private var listFull: List<Item>? = null
    init {
        listFull = ArrayList(items)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_feedlist, parent, false))

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: FeedlistAdapter.ViewHolder, position: Int) = holder.bind(items[position])

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetJavaScriptEnabled")
        fun bind(item: Item) {

            val img = item.image ?:""
            if(img.isNotEmpty()) {
                itemView.image.visibility = View.VISIBLE
                Glide.with(itemView).load(img).into(itemView.image)
                //Picasso.get().load(article.image).placeholder(R.drawable.placeholder).into(itemView.image)
            }
            else {
                itemView.image.visibility = View.GONE
            }

            val title = item.title.toHtml()

            itemView.title.text = title
            itemView.categories.text = item.categories
            itemView.description.text = item.description.toHtml()
            item.pubDate?.let {
                itemView.pubDate.text = item.pubDate.toDate()
            } ?: run{
                itemView.pubDate.text =""
            }

            itemView.setOnClickListener {
                callback.onItemClicked(item)
                /**
                //show article content inside a dialog
                val articleView = WebView(itemView.context)

                articleView.settings.loadWithOverviewMode = true
                articleView.settings.javaScriptEnabled = true
                articleView.isHorizontalScrollBarEnabled = false
                articleView.webChromeClient = WebChromeClient()
                articleView.loadDataWithBaseURL(
                    item.link,
                    "<style>img{display: inline; height: auto; max-width: 100%;}</style>" +
                            "<style>iframe{ height: auto; width: auto;}</style>" +
                            item.content,
                    null,
                    "utf-8",
                    null)

                val alertDialog = AlertDialog.Builder(itemView.context).create()
                alertDialog.setTitle(title)
                alertDialog.setView(articleView)
                alertDialog.setButton(
                    AlertDialog.BUTTON_NEUTRAL,
                    itemView.context.getString(R.string.close_button)
                ) { dialog, _ -> dialog.dismiss() }
                alertDialog.show()

                (alertDialog.findViewById<View>(android.R.id.message) as TextView).movementMethod = LinkMovementMethod.getInstance()
                */
            }
        }
    }

    /// Implemets Filterable
    override fun getFilter(): Filter = filter
    private var filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = ArrayList<Item>()

            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(listFull as Iterable<Item>)
            } else {
                val filterPattern = constraint.toString().toLowerCase(Locale.ROOT).trim { it <= ' ' }
                for (item in listFull!!) {
                    if (item.title.toLowerCase(Locale.ROOT).contains(filterPattern)) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            items.clear()
            val res = results.values
            if(res is List<*> && res.isNotEmpty() && res[0] is Item) {
                items.addAll(res as List<Item>)
            }
            notifyDataSetChanged()
        }
    }
}