package com.cesoft.cesrssreader2.data.remote.entity

import androidx.annotation.Keep
import com.cesoft.cesrssreader2.data.entity.Item
import com.cesoft.cesrssreader2.data.remote.Util
import com.tickaroo.tikxml.annotation.*

@Keep
@Xml(name = "item")
data class ItemEntity(
    @field:PropertyElement
    var guid: String?,
    @field:PropertyElement
    var title: String?,
    @field:PropertyElement
    var author: String?,
    @field:PropertyElement
    var link: String?,
    @field:PropertyElement
    var pubDate: String?,
    @field:PropertyElement(writeAsCData=true)
    var description: String?,
    @field:PropertyElement(name="content:encoded", writeAsCData=true)
    var body: String?,
    @field:Path("media:content")
    @field:Attribute(name="url")
    var image: String?,
    //@field:PropertyElement(name = "category", writeAsCData=true)
    @field:Element(name = "category")
    var categories: MutableList<CategoryEntity?>?
) {
    constructor(): this("","","","","","","","", mutableListOf())
    private fun MutableList<CategoryEntity?>?.parse(): String {
        var res = ""
        this?.let {
            for(item in iterator())
                res += "${item?.category} "
            return res
        }
        return ""
    }
    fun parse(): Item {
        if(body.isNullOrEmpty()) {
            body = description
        }
        if(image.isNullOrEmpty()) {
            body?.let { image = Util.getImgSrcFromHtml(it) }
        }
        return Item(
            null,
            guid ?:"",
            title ?:"",
            author ?:"",
            link ?:"",
            pubDate ?:"",
            description ?:"",
            body ?:"",
            image ?:"",
            categories.parse()
        )
    }
}