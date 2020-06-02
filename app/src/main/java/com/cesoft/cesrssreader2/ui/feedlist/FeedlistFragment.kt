package com.cesoft.cesrssreader2.ui.feedlist

import android.os.Bundle
import android.view.*
import android.view.KeyEvent.ACTION_DOWN
import android.view.KeyEvent.KEYCODE_ENTER
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_feedlist.*
import org.koin.core.KoinComponent
import com.cesoft.cesrssreader2.R
import com.cesoft.cesrssreader2.data.entity.Channel
import com.cesoft.cesrssreader2.data.entity.Item
import com.cesoft.cesrssreader2.ui.hideKeyboard//Extension functions


////////////////////////////////////////////////////////////////////////////////////////////////////
//
class FeedlistFragment : Fragment(), KoinComponent, FeedlistAdapter.OnClickListener {

	companion object {
		val TAG: String = FeedlistFragment::class.simpleName!!
	}

	private val viewModel: FeedlistViewModel by viewModels()
	private lateinit var adapter: FeedlistAdapter

	override fun onCreateView(
			inflater: LayoutInflater,
			container: ViewGroup?,
			savedInstanceState: Bundle?): View {
		return inflater.inflate(R.layout.fragment_feedlist, container, false)
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)
		setHasOptionsMenu(true)

		/// Recyclerview
		feedList.layoutManager = LinearLayoutManager(requireContext())
		feedList.itemAnimator = DefaultItemAnimator()
		feedList.setHasFixedSize(true)

		/// On Messages
		viewModel.snackbar.observe(viewLifecycleOwner, Observer { value ->
			value?.let {
				if(value is Int )
					Snackbar.make(root_layout, value, Snackbar.LENGTH_LONG).show()
				else if(value is String)
					Snackbar.make(root_layout, value, Snackbar.LENGTH_LONG).show()
				viewModel.onSnackbarShowed()
			}
		})

		/// Update
		swipe.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark)
		swipe.canChildScrollUp()
		swipe.setOnRefreshListener {
			update()
		}
		feedUrl.setOnItemClickListener() { adapter, view, i, j ->
			update()
		}
		feedUrl.setOnKeyListener { view, code, keyEvent ->
			if(code == KEYCODE_ENTER && keyEvent.action == ACTION_DOWN) {
				update()
				true
			}
			else
				false
		}

        /// On Updated
        viewModel.channel.observe(viewLifecycleOwner, Observer { channel ->
            onUpdated(channel)
        })

        /// Rss Url
        val rssUrlAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_dropdown_item_1line)
        feedUrl.setAdapter(rssUrlAdapter)
        viewModel.rssUrlList.observe(viewLifecycleOwner, Observer { rssUrlList ->
            rssUrlAdapter.clear()
            rssUrlAdapter.addAll(rssUrlList)
        })
        viewModel.rssUrl.observe(viewLifecycleOwner, Observer { url ->
			feedUrl.setText(url)
        })
	}

	private fun update() {
		adapter.items.clear()
		adapter.notifyDataSetChanged()
		swipe.isRefreshing = true
		viewModel.fetchFeed(feedUrl.text.toString())
		hideKb()
	}

    private fun onUpdated(channel: Channel?) {
        if(channel != null) {
            if(channel.title != null) {
                activity?.title = channel.title
            }
            adapter = FeedlistAdapter(channel.items, this)
            feedList.adapter = adapter
            adapter.notifyDataSetChanged()
            progressBar.visibility = View.GONE
            swipe.isRefreshing = false
            hideKb()
        }
    }

	override fun onResume() {
		super.onResume()
		//DEL requireActivity().actionBar?.setDisplayHomeAsUpEnabled(false)
		(requireActivity() as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
		hideKb()
	}

	private fun hideKb() {
		hideKeyboard()
		feedList.requestFocus()
	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		super.onCreateOptionsMenu(menu, inflater)
		inflater.inflate(R.menu.feedlist, menu)
		(menu.findItem(R.id.menu_search)?.actionView as SearchView).apply {
			setOnQueryTextListener(object : SearchView.OnQueryTextListener {
				override fun onQueryTextSubmit(query: String?): Boolean {
					return false
				}
				override fun onQueryTextChange(newText: String?): Boolean {
					adapter.filter.filter(newText)
					return true
				}
			})
		}
	}

	/// Implements FeedlistAdapter.OnClickListener
	override fun onItemClicked(item: Item) {
		//Log.e(TAG, "onItemClicked-------------------------------------------------------------"+item.title)
		//val bundle = bundleOf("id" to item)
		//findNavController().navigate(R.id.nav_feeditem, bundle)
		//(activity as MainActivity?)?.navigateToFeedItem(item)

		val bundle = bundleOf(Item.TAG to item)
		//val bundle = Bundle().apply { putParcelable(Item.TAG, item) }
		findNavController().navigate(R.id.nav_feeditem, bundle)
	}
}
