package com.samnoedel.librss.parsers;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Parse a section of an XML document and return a domain object
 * @param <T> The type of object to be parsed from the XML document
 */
public interface IXmlTagParser<T> {
    /**
     * Get the tag name that triggers this parser
     * @return The tag name
     */
    public String getStartTag();

    /**
     * Retrieve the element parsed from the XML tag.
     * @return The parsed element, or null if no element has been parsed
     */
    public T getParsedElement();

    /**
     * Execute the logic required to parse the domain object. It is guaranteed that
     * XmlPullParser.require(XmlPullParser.START_TAG, ...) will match the tag returned by
     * getStartTag(). It is the implementer's responsibility to advance the XmlPullParser to the
     * element following the close tag of the item to be parsed.
     *
     * @return A parsed domain object
     */
    public T parse(XmlPullParser parser) throws IOException, XmlPullParserException;
}
