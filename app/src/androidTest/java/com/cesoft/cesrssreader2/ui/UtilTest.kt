package com.cesoft.cesrssreader2.ui

import com.cesoft.cesrssreader2.ui.*

import android.text.Html
import androidx.core.text.parseAsHtml
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class UtilTest {

    private val htmlParsed = "Este es el texto que me interesa"
    private val html = "<p><img src='primera_imagen.jpg'>\n" +
                        "<div><br/></div></p>\n" +
                        "<p>$htmlParsed</p>" +
                        "<p><img src='segunda_imagen.jpg'>\n" +
                        "</body>"

    private val dateParsed = "05 agosto 2020"
    private val dates = listOf(
        "Mon, 5 Aug 2020 16:30:13 +0000",
        "Mon, 5 Aug 2020 16:30 +0000",
        "Monday, 05 August 2020 16:30:13 +0000",
        "Monday, 05 August 2020 16:30 +0000",
        "Mon 05 Aug 2020 16:30 +0000",
        "Monday 5 Aug 2020 16:30:13 +0000"
    )

    @Test
    fun testA() {
//        val a = Html.fromHtml(text.trim(), Html.FROM_HTML_MODE_COMPACT)
//        for(span in a) {
//            System.err.println("-----------------------------------------${span.toByte()}:$span")
//        }
        val res1 = Html.fromHtml(html.trim(), Html.FROM_HTML_MODE_COMPACT).toString().replace(0xFFFC.toChar(), ' ').trim()
        Assert.assertEquals(htmlParsed, res1)
        val res2 = html.parseAsHtml().toString().replace(0xFFFC.toChar(), ' ').trim()
        Assert.assertEquals(htmlParsed, res2)
    }

    @Test
    fun testB() {
        val res = html.toHtml()
        println("*${res}*")
        Assert.assertEquals(htmlParsed, res)
    }

    @Test
    fun testC() {
        for(date in dates) {
            val res = date.toDate()
            println("$date = $res")
            Assert.assertEquals(dateParsed, res)
        }
    }
}