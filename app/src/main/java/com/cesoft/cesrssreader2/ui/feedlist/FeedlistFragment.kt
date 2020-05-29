package com.cesoft.cesrssreader2.ui.feedlist

import com.cesoft.cesrssreader2.ui.hideKeyboard//Extension functions

import android.os.Bundle
import android.util.Log
import android.view.KeyEvent.ACTION_DOWN
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.cesoft.cesrssreader2.R
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.feedlist_fragment.*
import org.koin.core.KoinComponent

////////////////////////////////////////////////////////////////////////////////////////////////////
// TODO: autocomplete edit text with url saved on local db
class FeedlistFragment : Fragment(), KoinComponent {

	companion object {
		fun newInstance() = FeedlistFragment()
		private val TAG: String = FeedlistFragment::class.simpleName!!
	}

	private val viewModel: FeedlistViewModel by viewModels()
	private lateinit var adapter: FeedlistAdapter

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
							  savedInstanceState: Bundle?): View {
		return inflater.inflate(R.layout.feedlist_fragment, container, false)
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)

		val feedUrlAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
			requireContext(),
			android.R.layout.simple_dropdown_item_1line)
		feedUrl.setAdapter(feedUrlAdapter)
		viewModel.rssUrlList.observe(viewLifecycleOwner, Observer { rssUrlList ->
			feedUrlAdapter.clear()
			feedUrlAdapter.addAll(rssUrlList)
		})

		viewModel.feedlist.observe(viewLifecycleOwner, Observer { channel ->
			if(channel != null) {
				if(channel.title != null) {
					activity?.title = channel.title
				}
				adapter = FeedlistAdapter(channel.feeds)
				feedList.adapter = adapter
				adapter.notifyDataSetChanged()
				progressBar.visibility = View.GONE
				swipe.isRefreshing = false
				hideKb()
			}
		})

		feedList.layoutManager = LinearLayoutManager(requireContext())
		feedList.itemAnimator = DefaultItemAnimator()
		feedList.setHasFixedSize(true)

		viewModel.snackbar.observe(viewLifecycleOwner, Observer { value ->
			value?.let {
				if(value is Int )
					Snackbar.make(root_layout, value, Snackbar.LENGTH_LONG).show()
				else if(value is String)
					Snackbar.make(root_layout, value, Snackbar.LENGTH_LONG).show()
				viewModel.onSnackbarShowed()
			}
		})

		swipe.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark)
		swipe.canChildScrollUp()
		swipe.setOnRefreshListener {
			adapter.feeds.clear()
			adapter.notifyDataSetChanged()
			swipe.isRefreshing = true
			viewModel.fetchFeed(feedUrl.text.toString())
			hideKb()
		}

        viewModel.fetchFeed(feedUrl.text.toString())

		feedUrl.setOnItemClickListener() { adapter, view, i, j ->
			viewModel.fetchFeed(feedUrl.text.toString())
		}
		feedUrl.setOnKeyListener { view, code, keyEvent ->
			if(code == KEYCODE_ENTER && keyEvent.action == ACTION_DOWN) {
				viewModel.fetchFeed(feedUrl.text.toString())
				true
			}
			else
				false
		}
	}

	override fun onResume() {
		super.onResume()
		hideKb()
	}

	private fun hideKb() {
		hideKeyboard()
		feedList.requestFocus()
	}
}
