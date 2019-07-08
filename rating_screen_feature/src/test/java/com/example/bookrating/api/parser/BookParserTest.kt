package com.example.bookrating.api.parser

import com.example.bookrating.api.feed.BookFeed
import com.example.bookrating.api.feed.BooksFeed
import com.example.bookrating.model.Book
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class BookParserTest {

    //SUT
    private lateinit var parser: BooksParser

    @Before
    fun setUp() {
        parser = BooksParser()
    }

    @Test
    fun testEmptyFeedParsing() {
        val result = parser.parse(BooksFeed())

        assertEquals(0, result.size, "Empty list for empty feed")
    }

    @Test
    fun testSingleItemFeedParsing() {
        val result = parser.parse(getFeedWithOneItem())

        assertEquals(1, result.size, "List with one item for one item feed")

        assertEquals(getBookWithRating(getBookFeedItem(1)), result[0], "")
    }

    @Test
    fun testMultipleItemFeedParsing() {
        val result = parser.parse(getFeedWithMultipleItems())

        assertEquals(3, result.size, "List with one item for one item feed")

        assertEquals(getBookWithRating(getBookFeedItem(1)), result[0], "")
        assertEquals(getBookWithRating(getBookFeedItem(2)), result[1], "")
        assertEquals(getBookWithRating(getBookFeedItem(3)), result[2], "")
    }

    private fun getFeedWithOneItem() = BooksFeed(listOf(getBookFeedItem(1)))

    private fun getFeedWithMultipleItems() = BooksFeed(listOf(getBookFeedItem(1), getBookFeedItem(2), getBookFeedItem(3)))

    private fun getBookFeedItem(id: Int): BookFeed = BookFeed(id.toString(), "title$id", "image$id")

    private fun getBookWithRating(bookFeedItem: BookFeed): Book {
        return Book(bookFeedItem.id, bookFeedItem.title, bookFeedItem.image)
    }
}