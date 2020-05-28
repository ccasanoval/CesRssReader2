package com.cesoft.cesrssreader2.data.remote

import com.cesoft.cesrssreader2.data.remote.entity.RssEntity
import com.tickaroo.tikxml.TikXml
import okio.Buffer
import okio.BufferedSource
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4


//https://medium.com/@hanru.yeh/unit-test-retrofit-and-mockwebserver-a3e4e81fd2a2

@RunWith(JUnit4::class)
class RemoteTest {

    private lateinit var rssParser: TikXml

    @Before
    fun init() {
        rssParser = TikXml.Builder()
            //.exceptionOnUnreadXml(false)
            //.addTypeConverter<Any>(String::class.java, HtmlEscapeStringConverter())
            .build()
    }

    @Test
    fun remoteTestA() {
//        val xml = "<element a==\"qwe\"></element>"
//        val reader: XmlReader = readerFrom(xml)
//        try {
//            reader.beginElement()
//            reader.nextElementName()
//            reader.nextAttributeName()
//            reader.nextAttributeValue()
//        } finally {
//            reader.close()
//        }

        val bufferedSource: BufferedSource = Buffer().writeUtf8(rss)
        val rssEntity = rssParser.read<RssEntity>(bufferedSource, RssEntity::class.java)
        Assert.assertEquals("2.0", rssEntity.version)
        Assert.assertEquals("Android Authority", rssEntity.channel.title)
        Assert.assertEquals("https://www.androidauthority.com", rssEntity.channel.link)
        Assert.assertEquals("Android News, Reviews, How To", rssEntity.channel.description)
        Assert.assertEquals("Wed, 27 May 2020 15:01:12 +0000", rssEntity.channel.lastBuildDate)

        Assert.assertEquals(1, rssEntity.channel.items?.size)
        Assert.assertEquals("https://www.androidauthority.com/?p=1047299", rssEntity.channel.items?.get(0)?.guid)
        Assert.assertEquals("https://www.androidauthority.com/google-pixel-4a-1047299/", rssEntity.channel.items?.get(0)?.link)
        Assert.assertEquals("Wed, 27 May 2020 15:00:03 +0000", rssEntity.channel.items?.get(0)?.pubDate)
        Assert.assertEquals("Google Pixel 4a: Everything we know so far (Updated: May 27)", rssEntity.channel.items?.get(0)?.title)
        Assert.assertEquals("https://cdn57.androidauthority.net/wp-content/uploads/2020/04/google-pixel-4a-techdroider-facebook-resize-500x260.jpg", rssEntity.channel.items?.get(0)?.image)

        Assert.assertEquals(5, rssEntity.channel.items?.get(0)?.categories?.size)
        Assert.assertEquals("Features", rssEntity.channel.items?.get(0)?.categories?.get(0)?.category)
        Assert.assertEquals("Google Pixel 4a", rssEntity.channel.items?.get(0)?.categories?.get(4)?.category)
    }

    private val rss = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><rss version=\"2.0\"\n" +
            "\txmlns:content=\"http://purl.org/rss/1.0/modules/content/\"\n" +
            "\txmlns:wfw=\"http://wellformedweb.org/CommentAPI/\"\n" +
            "\txmlns:dc=\"http://purl.org/dc/elements/1.1/\"\n" +
            "\txmlns:atom=\"http://www.w3.org/2005/Atom\"\n" +
            "\txmlns:sy=\"http://purl.org/rss/1.0/modules/syndication/\"\n" +
            "\txmlns:slash=\"http://purl.org/rss/1.0/modules/slash/\"\n" +
            "\txmlns:media=\"https://search.yahoo.com/mrss/\"\n" +
            "\txmlns:webfeeds=\"http://webfeeds.org/rss/1.0\" >\n" +
            "\n" +
            "<channel>\n" +
            "\t<title>Android Authority</title>\n" +
            "\t<atom:link href=\"https://www.androidauthority.com/feed/\" rel=\"self\" type=\"application/rss+xml\" />\n" +
            "\t<link>https://www.androidauthority.com</link>\n" +
            "\t<description>Android News, Reviews, How To</description>\n" +
            "\t<lastBuildDate>Wed, 27 May 2020 15:01:12 +0000</lastBuildDate>\n" +
            "\t<language>en-US</language>\n" +
            "\t<sy:updatePeriod>\n" +
            "\thourly\t</sy:updatePeriod>\n" +
            "\t<sy:updateFrequency>\n" +
            "\t1\t</sy:updateFrequency>\n" +
            "\t<generator>https://wordpress.org/?v=5.2.6</generator>\n" +
            "<webfeeds:accentColor>00D49F</webfeeds:accentColor>\n" +
            "\t<webfeeds:related layout=\"card\" target=\"browser\" />\n" +
            "\t<webfeeds:analytics id=\"UA-20765087-1\" engine=\"GoogleAnalytics\" />\n" +
            "\t<webfeeds:icon>https://www.androidauthority.com/wp-content/uploads/feed/aa-mascot-192x192.png?v=1.0.2</webfeeds:icon>\n" +
            "\t<webfeeds:logo>https://www.androidauthority.com/wp-content/uploads/feed/aa_icon_feed.svg?v=1.0.2</webfeeds:logo>\n" +
            "\t<webfeeds:cover image=\"https://www.androidauthority.com/wp-content/uploads/feed/aboutus_top_imagev2.jpg?v=1.0.2\" />\t<item>\n" +
            "\t\t<title>Google Pixel 4a: Everything we know so far (Updated: May 27)</title>\n" +
            "\t\t<link>https://www.androidauthority.com/google-pixel-4a-1047299/</link>\n" +
            "\t\t\t\t<comments>https://www.androidauthority.com/google-pixel-4a-1047299/#respond</comments>\n" +
            "\t\t\t\t<pubDate>Wed, 27 May 2020 15:00:03 +0000</pubDate>\n" +
            "\t\t<dc:creator><![CDATA[Hadlee Simons]]></dc:creator>\n" +
            "\t\t\t\t<category><![CDATA[Features]]></category>\n" +
            "\t\t<category><![CDATA[Google]]></category>\n" +
            "\t\t<category><![CDATA[Google Pixel 3a]]></category>\n" +
            "\t\t<category><![CDATA[Google Pixel 4]]></category>\n" +
            "\t\t<category><![CDATA[Google Pixel 4a]]></category>\n" +
            "\n" +
            "\t\t<guid isPermaLink=\"false\">https://www.androidauthority.com/?p=1047299</guid>\n" +
            "\t\t\t\t<description><![CDATA[Update: More info on Pixel 4a pricing, release date, and the possible dropping of a legacy Pixel feature.]]></description>\n" +
            "\t\t\t<media:content name=\"AndroidAuthority\" url=\"https://cdn57.androidauthority.net/wp-content/uploads/2020/04/google-pixel-4a-techdroider-facebook-resize-500x260.jpg\" medium=\"image\" />" +
            "\n" +
            "\n" +
            "\t\t\t\t\t<content:encoded><![CDATA[<html><body><div class=\"video-container\">\n" +
            "<div class=\"youtube-player\"><iframe style=\"position: absolute;\" width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/lB2vilahuVA?autoplay=0&amp;autohide=2border=0&amp;wmode=opaque&amp;enablejsapi=1rel=0&amp;controls=1&amp;showinfo=1\" allowfullscreen frameborder=\"0\"></iframe></div>\n" +
            "</div>\n" +
            "<p><strong>Update, May 27, 2020 (11:00 AM ET):</strong> We have updated this Google Pixel 4a rumor hub with rumors that the launch has been delayed (again) and that it could ditch a legacy feature of the Pixel line. Also, we have a really solid rumor related to the price of the device.</p>\n" +
            "<p>See below for all the latest!</p>\n" +
            "<hr>\n" +
            "<p><strong>Original article, February 1, 2020 (6:00 PM ET):</strong> Without a doubt, the <a href=\"https://www.androidauthority.com/google-pixel-3a-review-986460/\">Google Pixel 3a</a> and <a href=\"https://www.androidauthority.com/google-pixel-3a-xl-review-982622/\">Pixel 3a XL</a> were some of the most exciting devices launched last year. For under \$400 you got most of what made the <a href=\"https://www.androidauthority.com/google-pixel-3-xl-review-911391/\">Google Pixel 3</a> great, including that amazing camera. Now it’s 2020, and all eyes are on the Google Pixel 4a.</p>\n" +
            "<p>Ostensibly, the Pixel 4a will attempt to replicate what the 3a series did: slash out the expensive hardware aspects of the Google Pixel 4 and <a href=\"https://www.androidauthority.com/google-pixel-4-xl-review-1040903/\">Pixel 4 XL</a> while retaining the super-fast Android updates, rear camera experience, and Pixel-exclusive software features.</p>\n" +
            "<div class=\"aa_srma_container shortcodes_wrapper\"><div class=\"related_articles_wrapper\"><div class=\"related_article_item\"><a class=\"overlay-link\" href=\"https://www.androidauthority.com/google-pixel-4-xl-revisited-1114275/\" title=\"Google Pixel 4 XL revisited: The good and bad after six months\"></a><div class=\"ra-image\" style=\"background-image: url(https://cdn57.androidauthority.net/wp-content/uploads/2020/05/google-pixel-4-xl-revisited-display.jpg)\"><img class=\"hidden\" src=\"https://cdn57.androidauthority.net/wp-content/uploads/2020/05/google-pixel-4-xl-revisited-display.jpg\" alt=\"related article\"></div><div><div class=\"shortcodes-header\"><div class=\"shortcodes-title\">Editor's Pick</div></div><h4 class=\"ra-title\">Google Pixel 4 XL revisited: The good and bad after six months</h4><div class=\"ra-excerpt\">\n" +
            "\n" +
            "I think it’s fair to say the Google Pixel 4 has been a divisive phone.\n" +
            "\n" +
            "In our review of the Pixel 4 and its larger twin, the Pixel 4 XL, we talked about the “untapped potential” …</div></div></div></div></div>\n" +
            "<p>That’s a tall order, but Google seems up for the task. Below, you’ll find everything we know so far about the Google Pixel 4a. Be sure to bookmark this page as we’ll update it often as new rumors come to light.</p>\n" +
            "<h2>Google Pixel 4a: Name and release date</h2>\n" +
            "<p><img class=\"aligncenter size-large wp-image-1048499 noname aa-img\" title=\"google pixel 4 vs pixel 3 vs pixel 3a 11\" src=\"https://cdn57.androidauthority.net/wp-content/uploads/2019/10/google-pixel-4-vs-pixel-3-vs-pixel-3a-11-1200x675.jpg\" alt=\"google pixel 4 vs pixel 3 vs pixel 3a 11\" width=\"1200\" height=\"675\" srcset=\"https://cdn57.androidauthority.net/wp-content/uploads/2019/10/google-pixel-4-vs-pixel-3-vs-pixel-3a-11-1200x675.jpg 1200w, https://cdn57.androidauthority.net/wp-content/uploads/2019/10/google-pixel-4-vs-pixel-3-vs-pixel-3a-11-300x170.jpg 300w, https://cdn57.androidauthority.net/wp-content/uploads/2019/10/google-pixel-4-vs-pixel-3-vs-pixel-3a-11-768x432.jpg 768w, https://cdn57.androidauthority.net/wp-content/uploads/2019/10/google-pixel-4-vs-pixel-3-vs-pixel-3a-11-16x9.jpg 16w, https://cdn57.androidauthority.net/wp-content/uploads/2019/10/google-pixel-4-vs-pixel-3-vs-pixel-3a-11-32x18.jpg 32w, https://cdn57.androidauthority.net/wp-content/uploads/2019/10/google-pixel-4-vs-pixel-3-vs-pixel-3a-11-28x16.jpg 28w, https://cdn57.androidauthority.net/wp-content/uploads/2019/10/google-pixel-4-vs-pixel-3-vs-pixel-3a-11-56x31.jpg 56w, https://cdn57.androidauthority.net/wp-content/uploads/2019/10/google-pixel-4-vs-pixel-3-vs-pixel-3a-11-64x36.jpg 64w, https://cdn57.androidauthority.net/wp-content/uploads/2019/10/google-pixel-4-vs-pixel-3-vs-pixel-3a-11-712x400.jpg 712w, https://cdn57.androidauthority.net/wp-content/uploads/2019/10/google-pixel-4-vs-pixel-3-vs-pixel-3a-11-1000x562.jpg 1000w, https://cdn57.androidauthority.net/wp-content/uploads/2019/10/google-pixel-4-vs-pixel-3-vs-pixel-3a-11-792x446.jpg 792w, https://cdn57.androidauthority.net/wp-content/uploads/2019/10/google-pixel-4-vs-pixel-3-vs-pixel-3a-11-1280x720.jpg 1280w, https://cdn57.androidauthority.net/wp-content/uploads/2019/10/google-pixel-4-vs-pixel-3-vs-pixel-3a-11-840x472.jpg 840w, https://cdn57.androidauthority.net/wp-content/uploads/2019/10/google-pixel-4-vs-pixel-3-vs-pixel-3a-11-1340x754.jpg 1340w, https://cdn57.androidauthority.net/wp-content/uploads/2019/10/google-pixel-4-vs-pixel-3-vs-pixel-3a-11-770x433.jpg 770w, https://cdn57.androidauthority.net/wp-content/uploads/2019/10/google-pixel-4-vs-pixel-3-vs-pixel-3a-11-356x200.jpg 356w, https://cdn57.androidauthority.net/wp-content/uploads/2019/10/google-pixel-4-vs-pixel-3-vs-pixel-3a-11-675x380.jpg 675w\" sizes=\"(max-width: 1200px) 100vw, 1200px\" data-attachment-id=\"1048499\" data-test-source_title=\"\" data-test-credit=\"\"></p>\n" +
            "<p>The Google Pixel series has stuck to the same naming convention since its inception. With that in mind (along with the leaks we’ve already seen), there’s little doubt as to the name of the upcoming mid-range entry in the series. Outside of some crazy situation arising, it’s a safe bet that we’ll see the Google Pixel 4a launch as Google’s next mid-ranger.</p>\n" +
            "<div class=\"aa_srma_container shortcodes_wrapper\"><div class=\"related_articles_wrapper\"><div class=\"related_article_item\"><a class=\"overlay-link\" href=\"https://www.androidauthority.com/oneplus-8-pro-1043096/\" title=\"OnePlus 8 buyer&#8217;s guide: Everything you need to know\"></a><div class=\"ra-image\" style=\"background-image: url(https://cdn57.androidauthority.net/wp-content/uploads/2020/04/OnePlus-8-and-8-Pro-in-front-of-box.jpg)\"><img class=\"hidden\" src=\"https://cdn57.androidauthority.net/wp-content/uploads/2020/04/OnePlus-8-and-8-Pro-in-front-of-box.jpg\" alt=\"related article\"></div><div><div class=\"shortcodes-header\"><div class=\"shortcodes-title\">Editor's Pick</div></div><h4 class=\"ra-title\">OnePlus 8 buyer&#8217;s guide: Everything you need to know</h4><div class=\"ra-excerpt\">OnePlus has been steadily climbing the ranks of the best smartphone makers. The OnePlus 8 series is the company’s latest release and, without doubt, it’s the best one yet.\n" +
            "\n" +
            "In this OnePlus 8 buyer's guide, we …</div></div></div></div></div>\n" +
            "<p>Unfortunately, sources close to <em>Android Authority</em> have confirmed there will be no Pixel 4a XL this year. This might be disappointing for some fans of the Pixel 3a XL, but this will likely reduce production costs for Google and help keep the Pixel 4a at a reasonable price.</p>\n" +
            "<p>In early April, a few pictures of some <a href=\"https://www.androidauthority.com/google-pixel-4a-design-1104694/\">Pixel 4a packages</a> surfaced online, which appear to confirm the name. The images look pretty legit, but we should still take them with a grain of salt.</p>\n" +
            "<blockquote class=\"twitter-tweet tw-align-center\">\n" +
            "<p dir=\"ltr\" lang=\"en\">Google Pixel 4a is On It’s Way… <a href=\"https://t.co/rJclXg1Yqo\" rel=\"nofollow\">pic.twitter.com/rJclXg1Yqo</a></p>\n" +
            "<p>— TechDroider (@techdroider) <a href=\"https://twitter.com/techdroider/status/1248141966145011713?ref_src=twsrc%5Etfw\" rel=\"nofollow\">April 9, 2020</a></p></blockquote>\n" +
            "<p><script async=\"\" src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script></p>\n" +
            "<p>The name might be solidified, but when exactly will the Google Pixel 4a land? Google launched the Pixel 3a series at <a href=\"https://www.androidauthority.com/google-io-2019-most-important-announcements-983718/\">Google I/O 2019</a>, so it seemed reasonable to assume at first that it would launch the follow-up around the same time. Unfortunately, Google I/O 2020 was <a href=\"https://www.androidauthority.com/google-i-o-2020-canceled-1089330/\">canceled due to the COVID-19 outbreak</a>. We expected Google to go ahead with an online-only launch of the new phone on or around the same time anyway, but that didn’t come to be.</p>\n" +
            "<p>Eventually, <a href=\"https://www.androidauthority.com/google-pixel-4a-launch-1117609/\">evidence popped up</a> that suggested Google moved the general sale date to June 5, 2020. This led us to believe the Pixel 4a would land at the confirmed <a href=\"https://www.androidauthority.com/android-11-beta-launch-show-1115873/\">Android 11 event</a> on June 3.</p>\n" +
            "<div class=\"aa_srma_container shortcodes_wrapper\"><div class=\"related_articles_wrapper\"><div class=\"related_article_item\"><a class=\"overlay-link\" href=\"https://www.androidauthority.com/google-pixel-biggest-problem-1119218/\" title=\"The only reason the Pixel line isn&#8217;t Android&#8217;s crown jewel is Google\"></a><div class=\"ra-image\" style=\"background-image: url(https://cdn57.androidauthority.net/wp-content/uploads/2019/10/google-pixel-4-vs-pixel-3-vs-pixel-3a-6.jpg)\"><img class=\"hidden\" src=\"https://cdn57.androidauthority.net/wp-content/uploads/2019/10/google-pixel-4-vs-pixel-3-vs-pixel-3a-6.jpg\" alt=\"related article\"></div><div><div class=\"shortcodes-header\"><div class=\"shortcodes-title\">Editor's Pick</div></div><h4 class=\"ra-title\">The only reason the Pixel line isn&#8217;t Android&#8217;s crown jewel is Google</h4><div class=\"ra-excerpt\">\n" +
            "\n" +
            "\n" +
            "\n" +
            "Yesterday, some rare behind-the-scenes news broke related to Google and its line of Pixel phones. In a nutshell, some of the leads at Google expressed disappointment regarding the design of the Pixel 4 and Pixel …</div></div></div></div></div>\n" +
            "<p>However, <strong>another</strong> rumor popped up that suggests Google moved the launch date <a href=\"https://www.androidauthority.com/google-pixel-4a-launch-date-1121544/\">even further back</a>. Now, we expect the device to land on July 13.</p>\n" +
            "<p>Essentially, we are going to keep our fingers crossed that the device lands in early June at the Android 11 event but keep our expectations low enough that it won’t be too upsetting if the launch actually happens in July.</p>\n" +
            "<h2>Google Pixel 4a: Design</h2>\n" +
            "<p><img class=\"aligncenter size-large wp-image-1049615 noname aa-img\" title=\"Pixel 3a XL product shot\" src=\"https://cdn57.androidauthority.net/wp-content/uploads/2019/11/Pixel-3a-XL-product-shot-1200x675.jpg\" alt=\"Pixel 3a XL product shot\" width=\"1200\" height=\"675\" srcset=\"https://cdn57.androidauthority.net/wp-content/uploads/2019/11/Pixel-3a-XL-product-shot-1200x675.jpg 1200w, https://cdn57.androidauthority.net/wp-content/uploads/2019/11/Pixel-3a-XL-product-shot-300x170.jpg 300w, https://cdn57.androidauthority.net/wp-content/uploads/2019/11/Pixel-3a-XL-product-shot-768x432.jpg 768w, https://cdn57.androidauthority.net/wp-content/uploads/2019/11/Pixel-3a-XL-product-shot-16x9.jpg 16w, https://cdn57.androidauthority.net/wp-content/uploads/2019/11/Pixel-3a-XL-product-shot-32x18.jpg 32w, https://cdn57.androidauthority.net/wp-content/uploads/2019/11/Pixel-3a-XL-product-shot-28x16.jpg 28w, https://cdn57.androidauthority.net/wp-content/uploads/2019/11/Pixel-3a-XL-product-shot-56x32.jpg 56w, https://cdn57.androidauthority.net/wp-content/uploads/2019/11/Pixel-3a-XL-product-shot-64x36.jpg 64w, https://cdn57.androidauthority.net/wp-content/uploads/2019/11/Pixel-3a-XL-product-shot-712x400.jpg 712w, https://cdn57.androidauthority.net/wp-content/uploads/2019/11/Pixel-3a-XL-product-shot-1000x563.jpg 1000w, https://cdn57.androidauthority.net/wp-content/uploads/2019/11/Pixel-3a-XL-product-shot-792x446.jpg 792w, https://cdn57.androidauthority.net/wp-content/uploads/2019/11/Pixel-3a-XL-product-shot-1280x720.jpg 1280w, https://cdn57.androidauthority.net/wp-content/uploads/2019/11/Pixel-3a-XL-product-shot-840x472.jpg 840w, https://cdn57.androidauthority.net/wp-content/uploads/2019/11/Pixel-3a-XL-product-shot-1340x754.jpg 1340w, https://cdn57.androidauthority.net/wp-content/uploads/2019/11/Pixel-3a-XL-product-shot-770x433.jpg 770w, https://cdn57.androidauthority.net/wp-content/uploads/2019/11/Pixel-3a-XL-product-shot-356x200.jpg 356w, https://cdn57.androidauthority.net/wp-content/uploads/2019/11/Pixel-3a-XL-product-shot-675x380.jpg 675w\" sizes=\"(max-width: 1200px) 100vw, 1200px\" data-attachment-id=\"1049615\" data-test-source_title=\"\" data-test-credit=\"\"></p>\n" +
            "<p>As has become the norm, we already have a decent idea of what the Google Pixel 4a will look like <a href=\"https://www.androidauthority.com/this-is-the-google-pixel-4a-punch-hole-display-headphone-jack-and-a-single-camera-1069957/\">thanks to serial leaker @OnLeaks</a>. The renders below, based on CAD files supplied to factories that actually create the devices, tell us the most basic information about the Pixel 4a with relative certainty.</p>\n" +
            "<p>Check out the renders for yourself below:</p>\n" +
            "\n" +
            "<a title=\"google pixel 4a leak renders 8\"  href='https://www.androidauthority.com/this-is-the-google-pixel-4a-punch-hole-display-headphone-jack-and-a-single-camera-1069957/google-pixel-4a-leak-renders-8/'><img width=\"1068\" height=\"600\" src=\"https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-8.jpg\" class=\"attachment-large size-large\" alt=\"google pixel 4a leak renders 8\" srcset=\"https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-8.jpg 1068w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-8-300x170.jpg 300w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-8-768x431.jpg 768w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-8-16x9.jpg 16w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-8-32x18.jpg 32w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-8-28x16.jpg 28w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-8-56x31.jpg 56w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-8-64x36.jpg 64w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-8-712x400.jpg 712w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-8-1000x562.jpg 1000w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-8-792x446.jpg 792w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-8-840x472.jpg 840w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-8-770x433.jpg 770w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-8-356x200.jpg 356w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-8-675x379.jpg 675w\" sizes=\"(max-width: 1068px) 100vw, 1068px\" /></a>\n" +
            "<a title=\"google pixel 4a leak renders 7\"  href='https://www.androidauthority.com/this-is-the-google-pixel-4a-punch-hole-display-headphone-jack-and-a-single-camera-1069957/google-pixel-4a-leak-renders-7/'><img width=\"1068\" height=\"600\" src=\"https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-7.jpg\" class=\"attachment-large size-large\" alt=\"google pixel 4a leak renders 7\" srcset=\"https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-7.jpg 1068w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-7-300x170.jpg 300w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-7-768x431.jpg 768w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-7-16x9.jpg 16w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-7-32x18.jpg 32w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-7-28x16.jpg 28w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-7-56x31.jpg 56w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-7-64x36.jpg 64w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-7-712x400.jpg 712w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-7-1000x562.jpg 1000w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-7-792x446.jpg 792w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-7-840x472.jpg 840w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-7-770x433.jpg 770w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-7-356x200.jpg 356w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-7-675x379.jpg 675w\" sizes=\"(max-width: 1068px) 100vw, 1068px\" /></a>\n" +
            "<a title=\"google pixel 4a leak renders 6\"  href='https://www.androidauthority.com/this-is-the-google-pixel-4a-punch-hole-display-headphone-jack-and-a-single-camera-1069957/google-pixel-4a-leak-renders-6/'><img width=\"1068\" height=\"600\" src=\"https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-6.jpg\" class=\"attachment-large size-large\" alt=\"google pixel 4a leak renders 6\" srcset=\"https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-6.jpg 1068w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-6-300x170.jpg 300w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-6-768x431.jpg 768w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-6-16x9.jpg 16w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-6-32x18.jpg 32w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-6-28x16.jpg 28w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-6-56x31.jpg 56w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-6-64x36.jpg 64w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-6-712x400.jpg 712w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-6-1000x562.jpg 1000w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-6-792x446.jpg 792w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-6-840x472.jpg 840w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-6-770x433.jpg 770w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-6-356x200.jpg 356w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-6-675x379.jpg 675w\" sizes=\"(max-width: 1068px) 100vw, 1068px\" /></a>\n" +
            "<a title=\"google pixel 4a leak renders 5\"  href='https://www.androidauthority.com/this-is-the-google-pixel-4a-punch-hole-display-headphone-jack-and-a-single-camera-1069957/google-pixel-4a-leak-renders-5/'><img width=\"1068\" height=\"601\" src=\"https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-5.jpg\" class=\"attachment-large size-large\" alt=\"google pixel 4a leak renders 5\" srcset=\"https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-5.jpg 1068w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-5-300x170.jpg 300w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-5-768x432.jpg 768w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-5-16x9.jpg 16w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-5-32x18.jpg 32w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-5-28x16.jpg 28w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-5-56x32.jpg 56w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-5-64x36.jpg 64w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-5-712x400.jpg 712w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-5-1000x563.jpg 1000w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-5-792x446.jpg 792w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-5-840x472.jpg 840w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-5-770x433.jpg 770w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-5-355x200.jpg 355w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-5-675x380.jpg 675w\" sizes=\"(max-width: 1068px) 100vw, 1068px\" /></a>\n" +
            "<a title=\"google pixel 4a leak renders 4\"  href='https://www.androidauthority.com/this-is-the-google-pixel-4a-punch-hole-display-headphone-jack-and-a-single-camera-1069957/google-pixel-4a-leak-renders-4/'><img width=\"1068\" height=\"601\" src=\"https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-4.jpg\" class=\"attachment-large size-large\" alt=\"google pixel 4a leak renders 4\" srcset=\"https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-4.jpg 1068w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-4-300x170.jpg 300w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-4-768x432.jpg 768w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-4-16x9.jpg 16w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-4-32x18.jpg 32w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-4-28x16.jpg 28w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-4-56x32.jpg 56w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-4-64x36.jpg 64w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-4-712x400.jpg 712w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-4-1000x563.jpg 1000w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-4-792x446.jpg 792w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-4-840x472.jpg 840w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-4-770x433.jpg 770w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-4-355x200.jpg 355w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-4-675x380.jpg 675w\" sizes=\"(max-width: 1068px) 100vw, 1068px\" /></a>\n" +
            "<a title=\"google pixel 4a leak renders 3\"  href='https://www.androidauthority.com/this-is-the-google-pixel-4a-punch-hole-display-headphone-jack-and-a-single-camera-1069957/google-pixel-4a-leak-renders-3/'><img width=\"1068\" height=\"601\" src=\"https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-3.jpg\" class=\"attachment-large size-large\" alt=\"google pixel 4a leak renders 3\" srcset=\"https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-3.jpg 1068w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-3-300x170.jpg 300w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-3-768x432.jpg 768w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-3-16x9.jpg 16w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-3-32x18.jpg 32w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-3-28x16.jpg 28w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-3-56x32.jpg 56w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-3-64x36.jpg 64w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-3-712x400.jpg 712w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-3-1000x563.jpg 1000w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-3-792x446.jpg 792w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-3-840x472.jpg 840w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-3-770x433.jpg 770w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-3-355x200.jpg 355w, https://cdn57.androidauthority.net/wp-content/uploads/2019/12/google-pixel-4a-leak-renders-3-675x380.jpg 675w\" sizes=\"(max-width: 1068px) 100vw, 1068px\" /></a>\n" +
            "\n" +
            "<p>As one would expect, the phone looks like a pared-down Google Pixel 4, which makes perfect sense. The square-shaped camera bump on the back of the device is a dead-ringer for the one on the Pixel 4 series, albeit with only one lens. The white colorway, Google “G” logo, and orange-colored power button all carry over from the Pixel 4.</p>\n" +
            "<p>However, there are some differences here. The matte-black ring that envelops the Pixel 4 body is absent, the selfie camera is housed in a display cutout, there’s a headphone jack present, and there’s a rear-mounted fingerprint sensor here, too. The fingerprint sensor and display cutout make sense, though, as it would hardly be cost-effective to transfer <a href=\"https://www.androidauthority.com/pixel-4-face-unlock-problem-1073653/\">all the face unlock tech</a> from the Pixel 4 series to the Pixel 4a.</p>\n" +
            "<p>Interestingly, <a href=\"https://9to5google.com/2020/04/09/exclusive-google-pixel-4a-details-specs/\" rel=\"nofollow\"><em>9to5Google</em></a> later reported that the Google Pixel 4a might not come in the white and orange colorway depicted above. The outlet claims it will come in at least two color variants: the usual Just Black and a new Barely Blue. <em>9to5Google</em> notes that depending on how “barely” blue the new blue color is, it could replace the white variant we see in the renders above.</p>\n" +
            "<p style=\"text-align: center;\"><strong>Related: </strong><a href=\"https://www.androidauthority.com/pixel-4-face-unlock-problem-1073653/\">Having trouble with face unlock on your Google Pixel 4? You’re not alone.</a></p>\n" +
            "<p>A couple of months after those renders surfaced online, <a href=\"https://www.androidauthority.com/pixel-4a-leaked-images-1091008/\">alleged photos</a> of the smartphone matching them also surfaced online. The device in these photos appears to sport a black chassis with a white lock button instead of the white and orange combo seen in the previous renders. It also comes in what appears to be an official Google Pixel fabric phone case. If this is true, this fabric cover is unlike any we’ve seen from Google before.</p>\n" +
            "</body></html>" +
            "]]></content:encoded>"+

            /*"<content:encoded><![CDATA[<html><body><div class=\"video-container\">\n" +
            "<div class=\"youtube-player\"><iframe style=\"position: absolute;\" width=\"100%\" height=\"100%\" src=\"https://www.youtube.com/embed/lB2vilahuVA?autoplay=0&amp;autohide=2border=0&amp;wmode=opaque&amp;enablejsapi=1rel=0&amp;controls=1&amp;showinfo=1\" allowfullscreen frameborder=\"0\"></iframe></div>\n" +
            "</div>\n" +
            "<p><strong>Update, May 27, 2020 (11:00 AM ET):</strong> We have updated this Google Pixel 4a rumor hub with rumors that the launch has been delayed (again) and that it could ditch a legacy feature of the Pixel line. Also, we have a really solid rumor related to the price of the device.</p>\n" +
            "<p>See below for all the latest!</p>" +
            "</body></html>]]></content:encoded>\n" +*/



            "\t\t\t\t\t\t\t<wfw:commentRss>https://www.androidauthority.com/samsung-galaxy-s20-sales-s10-1123336/feed/</wfw:commentRss>\n" +
            "\t\t<slash:comments>0</slash:comments>\n" +
            "\t\t\t\t\t\t\t</item>\n" +
            "\t</channel>\n" +
            "</rss>"


/*
    @Mock
    private lateinit var context: Application
    private lateinit var util: Util
    private lateinit var serv: FeedService
    private lateinit var channel: com.prof.rssparser.Channel

    private val title: String? = "channel_title"
    private val link: String? = "channel_link"
    private val description: String? = "channel_description"
    private val image: com.prof.rssparser.Image? = com.prof.rssparser.Image("img_title","img_url", "img_link", "img_description")
    private val articles: MutableList<com.prof.rssparser.Article> = MutableList(3) { i ->
        com.prof.rssparser.Article(
            "guid$i",
            "title$i",
            "author$i",
            "link$i",
            "pubDate$i",
            "description$i",
            "content$i",
            "image$i",
            MutableList(3) { j -> "category $i $j"})
    }

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        setupContext()
        util = Util(context)
        serv = FeedService(util)
        channel = com.prof.rssparser.Channel(title, link, description, image, articles)
    }

    private fun setupContext() {
        Mockito.`when`<Context>(context.applicationContext).thenReturn(context)
        //Mockito.`when`(context.resources).thenReturn(Mockito.mock(Resources::class.java))
        //Mockito.`when`(context.resources.getStringArray(R.array.leagues_id)).thenReturn(testArrayLeagueId)
    }

    @After
    fun end() {

    }

    @Test
    fun testA() {
        val channelParsed = util.parse(channel)
        Assert.assertTrue(channelParsed is com.cesoft.cesrssreader2.data.entity.Channel)
        Assert.assertNotEquals("Objects are equal", channel, channelParsed)

        Assert.assertEquals("link is different", channel.link, channelParsed.link)
        Assert.assertEquals("title is different", channel.title, channelParsed.title)
        Assert.assertEquals("description is different", channel.description, channelParsed.description)
        Assert.assertEquals("Objects are equal", channel.image?.toString(), channelParsed.image)

        Assert.assertEquals("# of Feeds are different", channel.articles.size, channelParsed.feeds.size)
        for(i in 0 until channel.articles.size) {
            Assert.assertEquals("$i:Feed:author are different", channel.articles[i].author, channelParsed.feeds[i].author)
            Assert.assertEquals("$i:Feed:content are different", channel.articles[i].content, channelParsed.feeds[i].content)
            Assert.assertEquals("$i:Feed:description are different", channel.articles[i].description, channelParsed.feeds[i].description)
            Assert.assertEquals("$i:Feed:guid are different", channel.articles[i].guid, channelParsed.feeds[i].guid)
            Assert.assertEquals("$i:Feed:title are different", channel.articles[i].title, channelParsed.feeds[i].title)
            Assert.assertEquals("$i:Feed:image are different", channel.articles[i].image, channelParsed.feeds[i].image)
            Assert.assertEquals("$i:Feed:link are different", channel.articles[i].link, channelParsed.feeds[i].link)
            Assert.assertEquals("$i:Feed:pubDate are different", channel.articles[i].pubDate, channelParsed.feeds[i].pubDate)
            var categories = ""
            for(category in channel.articles[i].categories) categories += "$category "
            Assert.assertEquals("$i:Feed:categories are different", categories, channelParsed.feeds[i].categories)
        }

    }*/
}