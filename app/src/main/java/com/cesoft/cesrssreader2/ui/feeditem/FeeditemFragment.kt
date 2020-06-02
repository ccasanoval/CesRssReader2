package com.cesoft.cesrssreader2.ui.feeditem

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.cesoft.cesrssreader2.R
import com.cesoft.cesrssreader2.data.entity.Item
import com.cesoft.cesrssreader2.ui.toHtml
import kotlinx.android.synthetic.main.fragment_feeditem.*
import kotlinx.android.synthetic.main.item_feedlist.view.*
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
        Log.e("FeeditemFragment", "onActivityCreated-----------*****************************************------" + item?.title)
    }

    override fun onResume() {
        super.onResume()
        iniFields()
    }

    private fun iniFields() {
        item?.let { item ->
            requireActivity().title = item.title
            requireActivity().actionBar?.title = item.title

            collapsing_toolbar.title = item.title//.substring(0, 50)
            Glide.with(image).load(item.image).into(image.image)
            title.text = item.title.toHtml()
            author.text = item.author.toHtml()
            body.text = item.content.toHtml()
        }
    }
}