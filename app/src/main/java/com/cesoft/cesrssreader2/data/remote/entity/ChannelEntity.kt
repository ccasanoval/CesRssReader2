package com.cesoft.cesrssreader2.data.remote.entity

import androidx.annotation.Keep
import com.tickaroo.tikxml.annotation.Element
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.Xml

@Keep
@Xml(name = "channel")
data class ChannelEntity(
    @field:PropertyElement
    var title: String?,
    @field:PropertyElement
    var link: String?,
    @field:PropertyElement
    var description: String?,
    @field:PropertyElement
    var language: String?,
    @field:PropertyElement(name = "webfeeds:icon")
    var icon: String?,
    @field:PropertyElement(name = "webfeeds:logo")
    var image: String?,
    @field:PropertyElement//@(name = "lastBuildDate", converter = CesDateConverter::class.java)
    var lastBuildDate: String?,
    @field:PropertyElement(name = "sy:updatePeriod")//hourly
    var updatePeriod: String?,
    @field:PropertyElement(name = "sy:updateFrequency")//1
    var updateFrequency: String?,
    @field:Element(name = "item")
    var items: MutableList<ItemEntity?>?
) {
    constructor(): this("", "", "", "", "", "", "", "", "0", mutableListOf())
}