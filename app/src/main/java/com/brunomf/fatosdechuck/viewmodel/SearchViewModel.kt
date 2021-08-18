package com.brunomf.fatosdechuck.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.brunomf.fatosdechuck.R
import com.brunomf.fatosdechuck.constants.Constants
import com.brunomf.fatosdechuck.service.repository.ChuckNorrisRepository
import com.brunomf.fatosdechuck.service.utils.FactsResult.*
import com.brunomf.fatosdechuck.service.utils.Validator.validateSearchText
import com.brunomf.fatosdechuck.service.utils.onApiError
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repository: ChuckNorrisRepository
) : ViewModel() {

    val validatorMsgLiveData: MutableLiveData<Pair<Boolean, Int?>> = MutableLiveData()

    val listCategoryLiveData: MutableLiveData<List<String>> = MutableLiveData()

    val errorListMsgLiveData: MutableLiveData<Pair<Int, Int?>> = MutableLiveData()

    fun validate(search: String) {
        validatorMsgLiveData.value = validateSearchText(search)
    }

    fun getListCategories() {
        viewModelScope.launch {
            when (val result = repository.getCategoriesList()) {
                is Success -> {
                    listCategoryLiveData.postValue(result.successData)
                    errorListMsgLiveData.postValue(Pair(Constants.SUCCESS, null))
                }
                is ApiError -> {
                    errorListMsgLiveData.postValue(onApiError(result.statusCode))
                }
                is ConnectionError -> {
                    errorListMsgLiveData.postValue(
                        Pair(
                            Constants.RESULT_ERROR,
                            R.string.error_lost_connection
                        )
                    )
                }
                is ServerError -> {
                    errorListMsgLiveData.postValue(
                        Pair(
                            Constants.RESULT_ERROR,
                            R.string.error_server
                        )
                    )
                }
            }
        }
    }
}
