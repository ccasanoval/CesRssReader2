package com.cesoft.cesrssreader2.ui

import android.text.Html
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class UtilTest {

    @Test
    fun testA() {
        val eltextoquemeinteresa = "Este es el texto que me interesa"
        val text = "<p><img src='asklfsdlafjsdl.jpg'></p>\n<p>$eltextoquemeinteresa</p>"

        val a = Html.fromHtml(text.trim(), Html.FROM_HTML_MODE_COMPACT)
        for(span in a) {
            System.err.println("-----------------------------------------${span.toByte()}:$span")
        }


        val parsed = Html.fromHtml(text.trim(), Html.FROM_HTML_MODE_COMPACT).toString().substring(1).trim()

        Assert.assertEquals(eltextoquemeinteresa, parsed)

    }

}