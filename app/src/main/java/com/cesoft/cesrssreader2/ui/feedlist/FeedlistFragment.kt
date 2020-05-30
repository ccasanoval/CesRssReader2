package com.cesoft.cesrssreader2.ui.feedlist

import android.os.Bundle
import android.view.*
import android.view.KeyEvent.ACTION_DOWN
import android.view.KeyEvent.KEYCODE_ENTER
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.feedlist_fragment.*
import org.koin.core.KoinComponent
import com.cesoft.cesrssreader2.R
import com.cesoft.cesrssreader2.ui.hideKeyboard//Extension functions


////////////////////////////////////////////////////////////////////////////////////////////////////
//
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

		setHasOptionsMenu(true)

//		searchItems.addTextChangedListener(object : TextWatcher {
//			override fun afterTextChanged(s: Editable?) {
//			}
//
//			override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//			}
//
//			override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//				nameFromDb(s.toString())
//			}
//		})

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
				adapter = FeedlistAdapter(channel.items)
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
			adapter.items.clear()
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

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		super.onCreateOptionsMenu(menu, inflater)
		inflater.inflate(R.menu.main_menu, menu)
		//val searchManager = context?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
		(menu.findItem(R.id.menu_search)?.actionView as SearchView).apply {
			//Log.e(TAG, "onCreateOptionsMenu---------------------------------------------------------------------")
			//setSearchableInfo(searchManager.getSearchableInfo(ComponentName(context, FeedlistFragment::class.java)))
			setOnQueryTextListener(object : SearchView.OnQueryTextListener {
				override fun onQueryTextSubmit(query: String?): Boolean {
					//Log.e(TAG, "onCreateOptionsMenu-onQueryTextSubmit--------------------------------------------------------------------")
					return false
				}
				override fun onQueryTextChange(newText: String?): Boolean {
					//Log.e(TAG, "onCreateOptionsMenu-onQueryTextChange--------------------------------------------------------------------")
					adapter.filter.filter(newText)
					return true
				}
			})
		}
	}

}
