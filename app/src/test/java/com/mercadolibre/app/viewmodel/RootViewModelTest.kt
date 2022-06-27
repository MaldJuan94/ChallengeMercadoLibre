package com.mercadolibre.app.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.mercadolibre.app.RetryRule
import com.mercadolibre.app.models.search.Results
import com.mercadolibre.app.models.search.SearchProductsResponse
import com.mercadolibre.app.usescases.*
import com.mercadolibre.app.utils.SearchType
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

/*
Para fines de prueba se crea dos casos de pruebas unitarias
representativas para los tipos de métodos implementados
 */
class RootViewModelTest {

    @get:Rule
    val retryRule = RetryRule()

    @ExperimentalCoroutinesApi
    private val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private var viewModel: RootViewModel? = null

    private var mockApplication: Application = mockk(relaxUnitFun = true)
    private val getSearchProductUseCase: GetSearchProductUseCase = mockk(relaxUnitFun = true)
    private val getSearchProductByCategoryUseCase: GetSearchProductByCategoryUseCase =
        mockk(relaxUnitFun = true)
    private val getProductUseCase: GetProductUseCase = mockk(relaxUnitFun = true)
    private val getCategoriesUseCase: GetCategoriesUseCase = mockk(relaxUnitFun = true)
    private val getProductDescriptionUseCase: GetProductDescriptionUseCase =
        mockk(relaxUnitFun = true)
    private val saveKeyWordInSearchHistoryUseCase: SaveKeyWordInSearchHistoryUseCase =
        mockk(relaxUnitFun = true)
    private val getLastSearchedItemsUseCase: GetLastSearchedItemsUseCase =
        mockk(relaxUnitFun = true)


    @ObsoleteCoroutinesApi
    @ExperimentalCoroutinesApi
    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        MockKAnnotations.init(this, relaxUnitFun = true)

        val anyText = "default"
        every { mockApplication.getString(any()) } returns anyText

        viewModel = RootViewModel(
            app = mockApplication,
            dispatcher = testDispatcher,
            getSearchProductUseCase = getSearchProductUseCase,
            getSearchProductByCategoryUseCase = getSearchProductByCategoryUseCase,
            getProductUseCase = getProductUseCase,
            getCategoriesUseCase = getCategoriesUseCase,
            getProductDescriptionUseCase = getProductDescriptionUseCase,
            saveKeyWordInSearchHistoryUseCase = saveKeyWordInSearchHistoryUseCase,
            getLastSearchedItemsUseCase = getLastSearchedItemsUseCase
        )
    }

    @Test
    fun `test the call onLoadUpdatedSearch function with all parameters`() {
        //Execute test
        viewModel?.onLoadUpdatedSearch(loading = false, type = "other")
        viewModel?.onLoadUpdatedSearch(loading = false, type = "byTerms")
        viewModel?.onLoadUpdatedSearch(loading = false, type = "byCategory")

        //Execute assert
        Assert.assertFalse(
            "stateResult loading field must be false",
            viewModel?.stateResult?.value?.loading?: true
        )

        Assert.assertEquals(
            "stateResult loading field must be false",
            SearchType.BY_CATEGORY,
            viewModel?.stateResult?.value?.type,
        )
    }

    @Test
    fun `test the call onRequestSearch function with all parameters`() {
        runBlocking {
            val response = SearchProductsResponse(siteId = "MCO", results = arrayListOf(Results()))
            coEvery {
                getSearchProductByCategoryUseCase(
                    any(),
                    any(),
                    any(),
                    any()
                )
            } returns response

            //Execute test
            val result = viewModel?.callSearchProductPager(
                query = "Test",
                type = SearchType.BY_CATEGORY.type
            )

            //Execute assert

            Assert.assertEquals(
                "result size field must be 1",
                1,
                viewModel?.stateResult?.value?.data?.size
            )
        }
    }
}
