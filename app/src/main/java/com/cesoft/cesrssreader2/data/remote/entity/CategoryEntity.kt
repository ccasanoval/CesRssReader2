package com.cesoft.cesrssreader2.data.remote.entity

import androidx.annotation.Keep
import com.tickaroo.tikxml.annotation.PropertyElement
import com.tickaroo.tikxml.annotation.TextContent
import com.tickaroo.tikxml.annotation.Xml

@Keep
@Xml(name = "category")
data class CategoryEntity(
    @field:TextContent
    var category: String?
) {
    constructor(): this("")
}