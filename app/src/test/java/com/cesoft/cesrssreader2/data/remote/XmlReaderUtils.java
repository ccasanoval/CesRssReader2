package com.cesoft.cesrssreader2.data.remote;

import com.tickaroo.tikxml.XmlReader;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import okio.Buffer;
import okio.BufferedSource;
import okio.Okio;
import org.junit.*;

/**
 * @author Hannes Dorfmann
 */
public class XmlReaderUtils {

    private XmlReaderUtils() {

    }

    public static XmlReader readerFrom(String xml) {
        return XmlReader.of(new Buffer().writeUtf8(xml));
    }

    public static XmlReader readerFromFile(String filePath) throws IOException {
        return XmlReader.of(Okio.buffer(Okio.source(new File(getResourcePath(filePath)))));
    }

    public static BufferedSource sourceForFile(String filePath) throws IOException {
        return Okio.buffer(Okio.source(new File(getResourcePath(filePath))));
    }

    /**
     * Get the resource path
     */
    private static String getResourcePath(String resPath) {
        URL resource = XmlReaderUtils.class.getClassLoader().getResource(resPath);
        Assert.assertNotNull("Could not open the resource " + resPath, resource);
        return resource.getFile();
    }

    /**
     * Converts the buffers content to a String
     * @param buffer
     * @return
     */
    public static String bufferToString(Buffer buffer) {
        return buffer.readString(Charset.defaultCharset());
    }
}
