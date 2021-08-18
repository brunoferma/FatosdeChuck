package com.brunomf.fatosdechuck.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.verify
import com.brunomf.fatosdechuck.R
import com.brunomf.fatosdechuck.service.model.Fact
import com.brunomf.fatosdechuck.service.utils.FactsResult
import com.brunomf.fatosdechuck.test_utils.CoroutineTestRule
import com.brunomf.fatosdechuck.test_utils.MockRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Mock
    private lateinit var searchResultLiveDataObserver: Observer<List<Fact>?>

    @Mock
    private lateinit var viewFlipperLiveDataObserver: Observer<Pair<Int, Int?>>

    private lateinit var viewModel: MainViewModel

    private lateinit var facts: List<Fact>


    @Before
    fun setUp() {
        facts = listOf(
            Fact(
                categories = arrayListOf("food"),
                created_at = "2020-01-05 13:42:18.823766",
                icon_url = "https://assets.chucknorris.host/img/avatar/chuck-norris.png",
                id = "MjtEesffSd6AH3Pxbw7_lg",
                updated_at = "2020-01-05 13:42:18.823766",
                url = "https://api.chucknorris.io/jokes/MjtEesffSd6AH3Pxbw7_lg",
                value = "When Chuck Norris played Chopped from Food Network, he finished " +
                        "his food in 1 millisecond, and instantly wins every dish. You " +
                        "didn't see him play because the episode is secret.",
            )
        )
    }

    @Test
    fun `Should set searchResultLiveData, when viewModel getByFreeSearch, get Success`() {
        //Arrange
        val resultSuccess = MockRepository(FactsResult.Success(facts))
        viewModel = MainViewModel(resultSuccess)
        viewModel.searchResultLiveData.observeForever(searchResultLiveDataObserver)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        //act
        viewModel.getByFreeSearch(query = "food")

        //Assert
        verify(searchResultLiveDataObserver).onChanged(facts)
        verify(viewFlipperLiveDataObserver).onChanged(Pair(2, null))
    }

    @Test
    fun `Should set viewFlipperLiveData, when viewModel getByFreeSearch get error 404`() {
        val mockRepository = MockRepository(FactsResult.ApiError(404))
        viewModel = MainViewModel(mockRepository)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        viewModel.getByFreeSearch(query = "fo")

        verify(viewFlipperLiveDataObserver).onChanged(Pair(3, R.string.error_404))
    }

    @Test
    fun `Should set viewFlipperLiveData, when viewModel getByFreeSearch get error 400`() {
        val mockRepository = MockRepository(FactsResult.ApiError(400))
        viewModel = MainViewModel(mockRepository)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        viewModel.getByFreeSearch(query = "fo")

        verify(viewFlipperLiveDataObserver).onChanged(Pair(3, R.string.error_400))
    }

    @Test
    fun `Should set viewFlipperLiveData, when viewModel getByFreeSearch get error 500`() {
        val mockRepository = MockRepository(FactsResult.ApiError(500))
        viewModel = MainViewModel(mockRepository)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        viewModel.getByFreeSearch(query = "fo")

        verify(viewFlipperLiveDataObserver).onChanged(Pair(3, R.string.error_generic))
    }

    @Test
    fun `Should set viewFlipperLiveData, when viewModel getByFreeSearch get ServerError`() {
        val mockRepository = MockRepository(FactsResult.ServerError)
        viewModel = MainViewModel(mockRepository)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        viewModel.getByFreeSearch(query = "fo")

        verify(viewFlipperLiveDataObserver).onChanged(Pair(3, R.string.error_server))
    }

    @Test
    fun `Should set searchResultLiveData, when viewModel getByRandom, get Success`() {
        val resultSuccess = MockRepository(FactsResult.Success(facts))
        viewModel = MainViewModel(resultSuccess)
        viewModel.searchResultLiveData.observeForever(searchResultLiveDataObserver)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        viewModel.getByRandom()

        verify(searchResultLiveDataObserver).onChanged(facts)
        verify(viewFlipperLiveDataObserver).onChanged(Pair(2, null))
    }

    @Test
    fun `Should set viewFlipperLiveData, when viewModel getByRandom get error 404`() {
        val mockRepository = MockRepository(FactsResult.ApiError(404))
        viewModel = MainViewModel(mockRepository)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        viewModel.getByRandom()

        verify(viewFlipperLiveDataObserver).onChanged(Pair(3, R.string.error_404))
    }

    @Test
    fun `Should set viewFlipperLiveData, when viewModel getByRandom get error 400`() {
        val mockRepository = MockRepository(FactsResult.ApiError(400))
        viewModel = MainViewModel(mockRepository)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        viewModel.getByRandom()

        verify(viewFlipperLiveDataObserver).onChanged(Pair(3, R.string.error_400))
    }

    @Test
    fun `Should set viewFlipperLiveData, when viewModel getByRandom get error 500`() {
        val mockRepository = MockRepository(FactsResult.ApiError(500))
        viewModel = MainViewModel(mockRepository)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        viewModel.getByRandom()

        verify(viewFlipperLiveDataObserver).onChanged(Pair(3, R.string.error_generic))
    }

    @Test
    fun `Should set viewFlipperLiveData, when viewModel getByRandom get ServerError`() {
        val mockRepository = MockRepository(FactsResult.ServerError)
        viewModel = MainViewModel(mockRepository)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        viewModel.getByRandom()

        verify(viewFlipperLiveDataObserver).onChanged(Pair(3, R.string.error_server))
    }

    @Test
    fun `Should set searchResultLiveData, when viewModel getByCategory, get Success`() {
        val resultSuccess = MockRepository(FactsResult.Success(facts))
        viewModel = MainViewModel(resultSuccess)
        viewModel.searchResultLiveData.observeForever(searchResultLiveDataObserver)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        viewModel.getByCategory("food")

        verify(searchResultLiveDataObserver).onChanged(facts)
        verify(viewFlipperLiveDataObserver).onChanged(Pair(2, null))
    }

    @Test
    fun `Should set viewFlipperLiveData, when viewModel getByCategory get error 404`() {
        val mockRepository = MockRepository(FactsResult.ApiError(404))
        viewModel = MainViewModel(mockRepository)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        viewModel.getByCategory("food")

        verify(viewFlipperLiveDataObserver).onChanged(Pair(3, R.string.error_404))
    }

    @Test
    fun `Should set viewFlipperLiveData, when viewModel getByCategory get error 400`() {
        val mockRepository = MockRepository(FactsResult.ApiError(400))
        viewModel = MainViewModel(mockRepository)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        viewModel.getByCategory("food")

        verify(viewFlipperLiveDataObserver).onChanged(Pair(3, R.string.error_400))
    }

    @Test
    fun `Should set viewFlipperLiveData, when viewModel getByCategory get error 500`() {
        val mockRepository = MockRepository(FactsResult.ApiError(500))
        viewModel = MainViewModel(mockRepository)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        viewModel.getByCategory("food")

        verify(viewFlipperLiveDataObserver).onChanged(Pair(3, R.string.error_generic))
    }


    @Test
    fun `Should set viewFlipperLiveData, when viewModel getByCategory get ServerError`() {
        val mockRepository = MockRepository(FactsResult.ServerError)
        viewModel = MainViewModel(mockRepository)
        viewModel.viewFlipperLiveData.observeForever(viewFlipperLiveDataObserver)

        viewModel.getByCategory("food")

        verify(viewFlipperLiveDataObserver).onChanged(Pair(3, R.string.error_server))
    }
}



