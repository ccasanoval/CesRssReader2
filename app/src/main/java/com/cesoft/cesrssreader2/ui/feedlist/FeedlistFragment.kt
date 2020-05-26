package com.cesoft.cesrssreader2.ui.feedlist

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.KeyEvent.ACTION_DOWN
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.cesoft.cesrssreader2.R
import com.cesoft.cesrssreader2.data.remote.Util
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.main_fragment.*
import org.koin.core.KoinComponent
import org.koin.core.inject


////////////////////////////////////////////////////////////////////////////////////////////////////
//
class FeedlistFragment : Fragment(), KoinComponent {

	companion object {
		fun newInstance() = FeedlistFragment()
		private val TAG: String = FeedlistFragment::class.simpleName!!
	}

	private val viewModel: FeedlistViewModel by viewModels()
	//private lateinit var viewModel: FeedlistViewModel = FeedlistViewModel()
	private lateinit var adapter: FeedlistAdapter

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
							  savedInstanceState: Bundle?): View {
		return inflater.inflate(R.layout.main_fragment, container, false)
	}

	override fun onActivityCreated(savedInstanceState: Bundle?) {
		super.onActivityCreated(savedInstanceState)

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
				hideKeyboard()
			}
		})

		feedList.layoutManager = LinearLayoutManager(requireContext())
		feedList.itemAnimator = DefaultItemAnimator()
		feedList.setHasFixedSize(true)

		viewModel.snackbar.observe(viewLifecycleOwner, Observer { value ->
			value?.let {
				Snackbar.make(root_layout, value, Snackbar.LENGTH_LONG).show()
				viewModel.onSnackbarShowed()
			}
		})

		swipe.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark)
		swipe.canChildScrollUp()
		swipe.setOnRefreshListener {
			adapter.feeds.clear()//TODO: es necesario mutable solo para esto?
			adapter.notifyDataSetChanged()
			swipe.isRefreshing = true
			viewModel.fetchFeed(feedUrl.text.toString())
			hideKeyboard()
		}

        viewModel.fetchFeed(feedUrl.text.toString())

		//TODO: no utilizar netUtil porque el repo hara eso...
		/*if( ! netUtil.isOnline()) {
			context?.let {
				val builder = AlertDialog.Builder(it)
				builder.setMessage(R.string.app_name)
					.setTitle(R.string.alert_message)
					.setCancelable(false)
					.setPositiveButton(
						R.string.alert_positive
					) { dialog, id -> activity?.finish() }
					.create()
					.show()
			}
		}
		else if(netUtil.isOnline()) {
			viewModel.fetchFeed(feedUrl.text.toString())
		}*/

//		feedUrl.doOnTextChanged { text, start, count, after ->
//			viewModel.fetchFeed(text.toString())
//		}
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
		hideKeyboard()
	}

	// Extensions
	private fun Fragment.hideKeyboard() {
		feedList.requestFocus()
		view?.let { activity?.hideKeyboard(it) }
	}
	private fun Activity.hideKeyboard() {
		hideKeyboard(currentFocus ?: View(this))
	}
	private fun Context.hideKeyboard(view: View) {
		val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
		inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
	}

}
