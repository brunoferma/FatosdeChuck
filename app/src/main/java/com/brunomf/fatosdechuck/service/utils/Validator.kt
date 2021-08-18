package com.brunomf.fatosdechuck.service.utils

import com.brunomf.fatosdechuck.R

object Validator {

    fun validateSearchText(search: String): Pair<Boolean, Int?> {
        when {
            search.isEmpty() -> {
                return Pair(false, R.string.info_you_need_search)
            }
            search.length < 3 -> {
                return Pair(false, R.string.info_min_search_characters)
            }
            search.length in 3..119 && !haveSymbols(search) -> {
                return Pair(true, R.string.info_yeah_its_a_search)
            }
            search.length > 120 -> {
                return Pair(false, R.string.info_max_search_characters)
            }
            else -> {
                return if (haveSymbols(search)) {
                    Pair(false, R.string.info_no_symbols)
                } else {
                    Pair(false, null)
                }
            }
        }
    }

    private fun haveSymbols(search: String): Boolean {
        return search.filterNot { it.isLetterOrDigit() || it.isWhitespace() }.count() > 0
    }
}
