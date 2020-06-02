package com.cesoft.cesrssreader2.ui.feeditem

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cesoft.cesrssreader2.data.entity.Item

class FeeditemViewModel : ViewModel() {

    private val _item = MutableLiveData<Item>()
    val item: LiveData<Item> = _item


}