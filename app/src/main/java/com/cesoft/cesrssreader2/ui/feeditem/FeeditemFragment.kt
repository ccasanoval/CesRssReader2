package com.cesoft.cesrssreader2.ui.feeditem

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.cesoft.cesrssreader2.R
import com.cesoft.cesrssreader2.data.entity.Item
import com.cesoft.cesrssreader2.ui.toHtml
import kotlinx.android.synthetic.main.fragment_feeditem.*
import org.koin.core.KoinComponent


////////////////////////////////////////////////////////////////////////////////////////////////////
//
class FeeditemFragment : Fragment(), KoinComponent {

    //private val viewModel: FeeditemViewModel by viewModels()
    private var item: Item? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_feeditem, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        item = arguments?.get(Item.TAG) as Item
    }

    override fun onResume() {
        super.onResume()
        iniFields()
    }

    private fun iniFields() {
        item?.let { item ->
            (activity as AppCompatActivity).supportActionBar?.title = item.title.toHtml()

            title.text = item.title.toHtml()

            content.settings.loadWithOverviewMode = true
            content.settings.javaScriptEnabled = false
            content.isHorizontalScrollBarEnabled = false
            content.webChromeClient = WebChromeClient()
            content.webViewClient = WebViewClient()
            val style = "<style>img { display:inline; height:auto; max-width:100%; }</style>" +
                    "<style>video { display:inline; height:auto; max-width:100%; }</style>"+
                    "<style>iframe { height:auto; width:auto; max-width:100%; }</style>" +
                    "<style>div { height:auto; width:auto; max-width:100%; }</style>"
            content.loadDataWithBaseURL(
                item.link,
                style + item.content,
                null,
                "utf-8",
                null)

        }
    }
}