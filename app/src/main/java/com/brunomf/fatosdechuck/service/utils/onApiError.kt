package com.brunomf.fatosdechuck.service.utils

import com.brunomf.fatosdechuck.R
import com.brunomf.fatosdechuck.constants.Constants


fun onApiError(result: Int): Pair<Int, Int> {
    return when (result) {
        400 -> {
            Pair(Constants.VIEW_FLIPPER_ERROR, R.string.error_400)
        }
        404 -> {
            Pair(Constants.VIEW_FLIPPER_ERROR, R.string.error_404)
        }
        else -> {
            Pair(Constants.VIEW_FLIPPER_ERROR, R.string.error_generic)
        }
    }
}
