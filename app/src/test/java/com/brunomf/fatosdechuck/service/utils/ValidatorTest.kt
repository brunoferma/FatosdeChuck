package com.brunomf.fatosdechuck.service.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.pliniodev.chucknorrisfacts.R
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class ValidatorTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Test
    fun whenIsEmpty() {
        val search = ""
        val result = Validator.validateSearchText(search)

        val resultSucess = Pair(false, R.string.info_you_need_search)

        assertEquals(resultSucess, result)
    }

    @Test
    fun whenSmallerThenThree() {
        val search = "fo"
        val result = Validator.validateSearchText(search)

        val resultSucess = Pair(false, R.string.info_min_search_characters)

        assertEquals(resultSucess, result)
    }

    @Test
    fun whenStringCorrectSize() {
        val search = "food"
        val result = Validator.validateSearchText(search)

        val resultSucess = Pair(true, R.string.info_yeah_its_a_search)

        assertEquals(resultSucess, result)
    }

    @Test
    fun whenSearchStringIsReallyBig() {
        val search = "When Chuck Norris played Chopped from Food Network, he finished " +
                "his food in 1 millisecond, and instantly wins every dish. You " +
                "didn't see him play because the episode is secret."
        val result = Validator.validateSearchText(search)

        val resultSucess = Pair(false, R.string.info_max_search_characters)

        assertEquals(resultSucess, result)
    }

    @Test
    fun whenHaveSymbols() {
        val search = "[]/~.,"
        val result = Validator.validateSearchText(search)

        val resultSucess = Pair(false, R.string.info_no_symbols)

        assertEquals(resultSucess, result)
    }
}
