package com.example.bookrating.data

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.assertEquals

@RunWith(MockitoJUnitRunner::class)
class RatingsRepositoryTest {

    //SUT
    lateinit var ratingsRepository: RatingsRepository

    @Before
    fun setUp() {
        ratingsRepository = RatingsRepositoryImpl()
    }

    @Test
    fun testAddNonRatingForOneBook() {
        ratingsRepository.addRating("1", 0)

        assertEquals(null, ratingsRepository.getBookRating("1")?.getEverageRating(), "Everage rating should be not set")

    }

    @Test
    fun testAddRatingForOneBook() {
        ratingsRepository.addRating("1", 2)
        ratingsRepository.addRating("1", 2)


        assertEquals(2, ratingsRepository.getBookRating("1")?.getEverageRating(), "Everage rating should be 2")
    }

    @Test
    fun testAddRatingForMultipleBooks() {
        ratingsRepository.addRating("1", 2)
        ratingsRepository.addRating("1", 2)

        ratingsRepository.addRating("2", 2)
        ratingsRepository.addRating("2", 4)

        assertEquals(2, ratingsRepository.getBookRating("1")?.getEverageRating(), "Everage rating should be 2")
        assertEquals(3, ratingsRepository.getBookRating("2")?.getEverageRating(), "Everage rating should be 3")
    }

    @After
    fun tearDown() {
        ratingsRepository.clear()
    }

}