package com.example.module_common.utils.html

import android.text.Editable
import android.text.Html
import android.util.Log
import androidx.collection.ArrayMap
import androidx.collection.ArraySet
import org.xml.sax.*

class CustomHtmlTagHandler : Html.TagHandler, ContentHandler {

    companion object {
        private const val TAG = "CustomHtmlTagHandler"
        const val CUSTOM_TAG = "custom"
        private val ORIGIN_TAGS = ArraySet(listOf(
                "br", "p", "ul", "li", "div", "span", "strong", "b", "em", "cite", "dfn", "i",
                "big", "small", "font", "blockquote", "tt", "a", "u", "del", "s", "strike",
                "sup", "sub", "h1", "h2", "h3", "h4", "h5", "h6", "img"
        ))
    }

    private var originXmlReader: XMLReader? = null
    private var originContentHandler: ContentHandler? = null
    private var originEditable: Editable? = null
    private var count = 0

    private val tagMaps = ArrayMap<String, BaseHtmlTag?>()
    fun registerTag(tag: String, htmlTag: BaseHtmlTag?) {
        tagMaps[tag.toLowerCase()] = htmlTag
    }

    fun removeTag(tag: String): BaseHtmlTag? {
        return if (tagMaps.containsKey(tag.toLowerCase())) {
            tagMaps.remove(tag.toLowerCase())
        } else null
    }

    override fun handleTag(opening: Boolean, tag: String, output: Editable, xmlReader: XMLReader) {
        if (opening) {
            startHandleTag(tag.toLowerCase(), output, xmlReader)
        } else {
            endHandleTag(tag.toLowerCase(), output, xmlReader)
        }
    }

    private fun startHandleTag(tag: String, output: Editable, xmlReader: XMLReader) {
        when (tag) {
            CUSTOM_TAG -> {
                if (originContentHandler == null) {
                    originContentHandler = xmlReader.contentHandler
                    originXmlReader = xmlReader
                    xmlReader.contentHandler = this
                    originEditable = output
                }
                count++
            }
            else -> {
            }
        }
    }

    private fun endHandleTag(tag: String, output: Editable, xmlReader: XMLReader) {
        when (tag) {
            CUSTOM_TAG -> {
                count--
                if (count == 0) {
                    for (key in tagMaps.keys) {
                        tagMaps[key]!!.finishHandleTag(originEditable)
                    }
                    originXmlReader!!.contentHandler = originContentHandler
                    originXmlReader = null
                    originContentHandler = null
                    originEditable = null
                }
            }
            else -> {
            }
        }
    }

    @Throws(SAXException::class)
    override fun startElement(uri: String, localName: String, qName: String, atts: Attributes) {
        //忽略大小写
        val low = localName.toLowerCase()
        when {
            low.equals(CUSTOM_TAG, ignoreCase = true) -> {
                handleTag(true, low, originEditable!!, originXmlReader!!)
            }
            canHandleTag(low) -> {
                tagMaps[low]!!.startHandleTag(originEditable, atts)
            }
            ORIGIN_TAGS.contains(low) -> {
                originContentHandler!!.startElement(uri, low, qName, atts)
            }
            else -> {
                Log.e(TAG, "startElement: <$low>标签不可被解析")
            }
        }
    }

    @Throws(SAXException::class)
    override fun endElement(uri: String, localName: String, qName: String) {
        //忽略大小写
        val low = localName.toLowerCase()
        when {
            low.equals(CUSTOM_TAG, ignoreCase = true) -> {
                handleTag(false, low, originEditable!!, originXmlReader!!)
            }
            canHandleTag(low) -> {
                tagMaps[low]!!.endHandleTag(originEditable)
            }
            ORIGIN_TAGS.contains(low) -> {
                originContentHandler!!.endElement(uri, low, qName)
            }
            else -> {
                Log.e(TAG, "endElement: </$low>标签不可被解析")
            }
        }
    }

    fun canHandleTag(tagName: String?): Boolean {
        if (!tagMaps.containsKey(tagName)) {
            return false
        }
        val baseHtmlTag = tagMaps[tagName]
        return baseHtmlTag != null
    }

    @Throws(SAXException::class)
    override fun characters(ch: CharArray, start: Int, length: Int) {
        originContentHandler!!.characters(ch, start, length)
    }

    @Throws(SAXException::class)
    override fun ignorableWhitespace(ch: CharArray, start: Int, length: Int) {
        originContentHandler!!.ignorableWhitespace(ch, start, length)
    }

    @Throws(SAXException::class)
    override fun processingInstruction(target: String, data: String) {
        originContentHandler!!.processingInstruction(target, data)
    }

    @Throws(SAXException::class)
    override fun skippedEntity(name: String) {
        originContentHandler!!.skippedEntity(name)
    }

    override fun setDocumentLocator(locator: Locator) {
        originContentHandler!!.setDocumentLocator(locator)
    }

    @Throws(SAXException::class)
    override fun startDocument() {
        originContentHandler!!.startDocument()
    }

    @Throws(SAXException::class)
    override fun endDocument() {
        originContentHandler!!.endDocument()
    }

    @Throws(SAXException::class)
    override fun startPrefixMapping(prefix: String, uri: String) {
        originContentHandler!!.startPrefixMapping(prefix, uri)
    }

    @Throws(SAXException::class)
    override fun endPrefixMapping(prefix: String) {
        originContentHandler!!.endPrefixMapping(prefix)
    }


}