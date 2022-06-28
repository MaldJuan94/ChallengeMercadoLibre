package com.mercadolibre.app.viewmodel

import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.mercadolibre.app.RetryRule
import com.mercadolibre.app.models.categories.Categories
import com.mercadolibre.app.models.description.ProductDescription
import com.mercadolibre.app.models.detail.DetailResponse
import com.mercadolibre.app.models.search.Results
import com.mercadolibre.app.models.search.SearchProductsResponse
import com.mercadolibre.app.network.ApiService
import com.mercadolibre.app.repository.RecipeRepository
import com.mercadolibre.app.repository.RecipeRepositoryImpl
import com.mercadolibre.app.ui.events.MainState
import com.mercadolibre.app.usescases.*
import com.mercadolibre.app.utils.Network
import com.mercadolibre.app.utils.SearchType
import com.mercadolibre.app.utils.SharedPreferenceUtils
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.setMain
import okhttp3.ResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.internal.util.reflection.FieldSetter
import retrofit2.Response

class RootViewModelTest {

    @get:Rule
    val retryRule = RetryRule()

    @ExperimentalCoroutinesApi
    private val testDispatcher = UnconfinedTestDispatcher()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private var viewModel: RootViewModel? = null
    private var mockApiService: ApiService = mockk(relaxUnitFun = true)
    private var mockSharedPreferenceUtils: SharedPreferenceUtils = mockk(relaxUnitFun = true)
    private var mockRepository: RecipeRepository = RecipeRepositoryImpl(mockApiService)
    private var mockApplication: Application = mockk(relaxUnitFun = true)
    private val getSearchProductUseCase: GetSearchProductUseCase =
        GetSearchProductUseCase(mockRepository, mockApplication)
    private val getSearchProductByCategoryUseCase: GetSearchProductByCategoryUseCase =
        GetSearchProductByCategoryUseCase(mockRepository, mockApplication)
    private val getProductUseCase: GetProductUseCase =
        GetProductUseCase(mockRepository, mockApplication)
    private val getCategoriesUseCase: GetCategoriesUseCase =
        GetCategoriesUseCase(mockRepository, mockApplication)
    private val getProductDescriptionUseCase: GetProductDescriptionUseCase =
        GetProductDescriptionUseCase(mockRepository, mockApplication)
    private val saveKeyWordInSearchHistoryUseCase: SaveKeyWordInSearchHistoryUseCase =
        SaveKeyWordInSearchHistoryUseCase(mockSharedPreferenceUtils, mockApplication)
    private val getLastSearchedItemsUseCase: GetLastSearchedItemsUseCase =
        GetLastSearchedItemsUseCase(mockSharedPreferenceUtils, mockApplication)

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

    @Before
    fun resetStatus() {
        viewModel?.clearSearch()
    }

    @Test
    fun `test the loading status of searches`() {
        //Execute test
        viewModel?.onLoadUpdatedSearch(loading = false, type = "other")
        viewModel?.onLoadUpdatedSearch(loading = false, type = "byTerms")
        viewModel?.onLoadUpdatedSearch(loading = false, type = "byCategory")

        //Execute assert
        Assert.assertFalse(
            "stateResult loading field must be false",
            viewModel?.stateResult?.value?.loading ?: true
        )

        Assert.assertEquals(
            "stateResult loading field must be false",
            SearchType.BY_CATEGORY,
            viewModel?.stateResult?.value?.type,
        )
    }

    @Test
    fun `test successful search by category`() {
        runBlocking {
            mockkObject(Network)
            every { Network.checkNetworkStateWithNetworkCapabilities(viewModel!!.context) } returns true

            val response = SearchProductsResponse(siteId = "MCO", results = arrayListOf(Results()))

            coEvery { mockApiService.searchProductsByCategory(any(), any(), any()) } returns
                    Response.success(response)

            //Execute test
            viewModel?.callSearchProductPager(
                query = "Test",
                type = SearchType.BY_CATEGORY.type
            )

            //Execute assert

            Assert.assertEquals(
                "result size field must be 1",
                1,
                viewModel?.stateResult?.value?.data?.size
            )

            Assert.assertFalse(
                "loading field must be false",
                viewModel?.stateResult?.value?.loading ?: true
            )
        }
    }

    @Test
    fun `test successful search with empty list by category`() {

        runBlocking {
            val response = SearchProductsResponse(siteId = "MCO", results = arrayListOf())
            mockkObject(Network)
            every { Network.checkNetworkStateWithNetworkCapabilities(viewModel!!.context) } returns true

            coEvery { mockApiService.searchProductsByCategory(any(), any(), any()) } returns
                    Response.success(response)

            //Execute test
            viewModel?.callSearchProductPager(
                query = "Test",
                type = SearchType.BY_CATEGORY.type
            )

            //Execute assert

            Assert.assertEquals(
                "result size field must be 0",
                0,
                viewModel?.stateResult?.value?.data?.size
            )

            Assert.assertFalse(
                "loading field must be false",
                viewModel?.stateResult?.value?.loading ?: true
            )
        }
    }

    @Test
    fun `test successful search by term`() {
        runBlocking {
            mockkObject(Network)
            every { Network.checkNetworkStateWithNetworkCapabilities(viewModel!!.context) } returns true
            every { mockSharedPreferenceUtils.getLastSearchedList(viewModel!!.context) } returns arrayListOf()
            every {
                mockSharedPreferenceUtils.saveLastSearchedList(
                    viewModel!!.context,
                    any()
                )
            } returns Unit

            val response = SearchProductsResponse(siteId = "MCO", results = arrayListOf(Results()))
            coEvery { mockApiService.searchProduct(any(), any(), any()) } returns
                    Response.success(response)

            //Execute test
            viewModel?.callSearchProductPager(
                query = "Test",
                type = SearchType.BY_TERMS.type
            )
            viewModel?.clearSearch()
            viewModel?.callSearchProductPager(
                query = "Test",
                type = "Test type"
            )

            //Execute assert

            Assert.assertEquals(
                "result size field must be 1",
                1,
                viewModel?.stateResult?.value?.data?.size
            )

            Assert.assertFalse(
                "loading field must be false",
                viewModel?.stateResult?.value?.loading ?: true
            )
        }
    }

    @Test
    fun `test successful search by term with search history`() {
        runBlocking {
            mockkObject(Network)
            every { Network.checkNetworkStateWithNetworkCapabilities(viewModel!!.context) } returns true
            every { mockSharedPreferenceUtils.getLastSearchedList(viewModel!!.context) } returns arrayListOf(
                "TEST",
                "TEST"
            )
            every {
                mockSharedPreferenceUtils.saveLastSearchedList(
                    viewModel!!.context,
                    any()
                )
            } returns Unit

            val response = SearchProductsResponse(siteId = "MCO", results = arrayListOf(Results()))
            coEvery { mockApiService.searchProduct(any(), any(), any()) } returns
                    Response.success(response)

            //Execute test
            viewModel?.callSearchProductPager(
                query = "Test",
                type = SearchType.BY_TERMS.type
            )
            viewModel?.clearSearch()
            viewModel?.callSearchProductPager(
                query = "Test",
                type = "Test type"
            )

            //Execute assert

            Assert.assertEquals(
                "result size field must be 1",
                1,
                viewModel?.stateResult?.value?.data?.size
            )

            Assert.assertFalse(
                "loading field must be false",
                viewModel?.stateResult?.value?.loading ?: true
            )
        }
    }

    @Test
    fun `test get product successfully`() {
        runBlocking {
            mockkObject(Network)
            every { Network.checkNetworkStateWithNetworkCapabilities(viewModel!!.context) } returns true

            val responseProductList = SearchProductsResponse(
                siteId = "MCO",
                results = arrayListOf(Results(id = "MCO000000"))
            )
            val responseGetProduct = DetailResponse(siteId = "MCO", id = "MCO000000")
            val responseGetProductDescription = ProductDescription(text = "text")

            coEvery { mockApiService.getProduct(any()) } returns Response.success(responseGetProduct)

            coEvery { mockApiService.getProductDescription(any()) } returns
                    Response.success(responseGetProductDescription)

            coEvery { mockApiService.searchProductsByCategory(any(), any(), any()) } returns
                    Response.success(responseProductList)

            //Execute test
            viewModel?.callSearchProductPager(
                query = "Test",
                type = SearchType.BY_CATEGORY.type
            )
            viewModel?.clearDetail()
            viewModel?.getDataProduct(
                id = "MCO000000"
            )
            //Execute assert

            Assert.assertEquals(
                "product id size field must be 1",
                "MCO000000",
                viewModel?.stateResultProduct?.value?.data?.id
            )

            Assert.assertFalse(
                "stateResultProduct loading field must be false",
                viewModel?.stateResultProduct?.value?.loading ?: true
            )

            Assert.assertNotNull(
                "stateResultProduct prices field must be not null",
                viewModel?.stateResultProduct?.value?.prices
            )
        }
    }

    @Test
    fun `test get categories successfully`() {
        runBlocking {
            mockkObject(Network)
            every { Network.checkNetworkStateWithNetworkCapabilities(viewModel!!.context) } returns true

            val responseCategories = arrayListOf(
                Categories(id = "MCO0001", name = "TEST01"),
                Categories(id = "MCO0002", name = "TEST02")
            )
            coEvery { mockApiService.getCategories() } returns Response.success(responseCategories)
            //Execute test
            viewModel?.getCategories()
            //Execute assert
            Assert.assertTrue(
                "categoriesState must be SuccessGetCategories",
                (viewModel?.categoriesState?.value is MainState.SuccessGetCategories)
            )
        }
    }

    @Test
    fun `test get categories without internet connection`() {
        val categoriesState: MutableState<MainState> = mutableStateOf(value = MainState.Loading)

        FieldSetter.setField(
            viewModel,
            viewModel!!::class.java.getDeclaredField("_categoriesState"),
            categoriesState
        )

        runBlocking {
            mockkObject(Network)
            every { Network.checkNetworkStateWithNetworkCapabilities(viewModel!!.context) } returns false

            val responseCategories = arrayListOf(
                Categories(id = "MCO0001", name = "TEST01"),
                Categories(id = "MCO0002", name = "TEST02")
            )
            coEvery { mockApiService.getCategories() } returns Response.success(responseCategories)
            //Execute test
            viewModel?.getCategories()
            //Execute assert
            Assert.assertTrue(
                "categoriesState must be ErrorNetwork",
                (viewModel?.categoriesState?.value !is MainState.ErrorNetwork)
            )
        }
    }

    @Test
    fun `test get categories with service error`() {
        runBlocking {

            val categoriesState: MutableState<MainState> = mutableStateOf(value = MainState.Loading)
            val bodyErrorServices: ResponseBody = mockk(relaxUnitFun = true)

            FieldSetter.setField(
                viewModel,
                viewModel!!::class.java.getDeclaredField("_categoriesState"),
                categoriesState
            )

            mockkObject(Network)
            every { Network.checkNetworkStateWithNetworkCapabilities(viewModel!!.context) } returns true

            every { bodyErrorServices.contentType() } returns null
            every { bodyErrorServices.contentLength() } returns 0

            coEvery { mockApiService.getCategories() } returns Response.error(
                404,
                bodyErrorServices
            )
            //Execute test
            viewModel?.getCategories()
            print(viewModel?.categoriesState?.value)
            //Execute assert
            Assert.assertTrue(
                "categoriesState must be ErrorNetwork",
                (viewModel?.categoriesState?.value !is MainState.Error)
            )
        }
    }

    @Test
    fun `test get search result by category without internet connection`() {
        runBlocking {
            mockkObject(Network)
            every { Network.checkNetworkStateWithNetworkCapabilities(viewModel!!.context) } returns false

            //Execute test
            viewModel?.callSearchProductPager(
                query = "Test",
                type = SearchType.BY_CATEGORY.type
            )
            //Execute assert
            Assert.assertFalse(
                "stateResult loading must be false",
                viewModel?.stateResult?.value?.loading ?: true
            )
            Assert.assertTrue(
                "stateResult endReached must be true",
                viewModel?.stateResult?.value?.endReached ?: false
            )

            Assert.assertEquals(
                "stateResult error must be default",
                "default",
                viewModel?.stateResult?.value?.error
            )
        }
    }

    @Test
    fun `test get search result by category with service error`() {
        runBlocking {
            val bodyErrorServices: ResponseBody = mockk(relaxUnitFun = true)

            mockkObject(Network)
            every { Network.checkNetworkStateWithNetworkCapabilities(viewModel!!.context) } returns true

            every { bodyErrorServices.contentType() } returns null
            every { bodyErrorServices.contentLength() } returns 0

            coEvery {
                mockApiService.searchProductsByCategory(
                    any(),
                    any(),
                    any()
                )
            } returns Response.error(
                404,
                bodyErrorServices
            )
            //Execute test
            viewModel?.callSearchProductPager(
                query = "Test",
                type = SearchType.BY_CATEGORY.type
            )
            //Execute assert
            Assert.assertFalse(
                "stateResult loading must be false",
                viewModel?.stateResult?.value?.loading ?: true
            )
            Assert.assertTrue(
                "stateResult endReached must be true",
                viewModel?.stateResult?.value?.endReached ?: false
            )

            Assert.assertTrue(
                "stateResult error must be not empty",
                viewModel?.stateResult?.value?.error?.isNotEmpty() ?: false
            )
        }
    }

    @Test
    fun `test get search result by term without internet connection`() {
        runBlocking {
            mockkObject(Network)
            every { Network.checkNetworkStateWithNetworkCapabilities(viewModel!!.context) } returns false

            //Execute test
            viewModel?.callSearchProductPager(
                query = "Test",
                type = SearchType.BY_TERMS.type
            )
            //Execute assert
            Assert.assertFalse(
                "stateResult loading must be false",
                viewModel?.stateResult?.value?.loading ?: true
            )
            Assert.assertTrue(
                "stateResult endReached must be true",
                viewModel?.stateResult?.value?.endReached ?: false
            )

            Assert.assertEquals(
                "stateResult error must be default",
                "default",
                viewModel?.stateResult?.value?.error
            )
        }
    }

    @Test
    fun `test get search result by term with service error`() {
        runBlocking {
            val bodyErrorServices: ResponseBody = mockk(relaxUnitFun = true)

            mockkObject(Network)
            every { Network.checkNetworkStateWithNetworkCapabilities(viewModel!!.context) } returns true

            every { bodyErrorServices.contentType() } returns null
            every { bodyErrorServices.contentLength() } returns 0

            coEvery {
                mockApiService.searchProduct(
                    any(),
                    any(),
                    any()
                )
            } returns Response.error(
                404,
                bodyErrorServices
            )
            //Execute test
            viewModel?.callSearchProductPager(
                query = "Test",
                type = SearchType.BY_TERMS.type
            )
            //Execute assert
            Assert.assertFalse(
                "stateResult loading must be false",
                viewModel?.stateResult?.value?.loading ?: true
            )
            Assert.assertTrue(
                "stateResult endReached must be true",
                viewModel?.stateResult?.value?.endReached ?: false
            )

            Assert.assertTrue(
                "stateResult error must be not empty",
                viewModel?.stateResult?.value?.error?.isNotEmpty() ?: false
            )
        }
    }

    @Test
    fun `test get product detail without internet connection`() {
        runBlocking {
            mockkObject(Network)
            every { Network.checkNetworkStateWithNetworkCapabilities(viewModel!!.context) } returns false

            //Execute test
            viewModel?.getDataProduct("MCO0000")
            //Execute assert
            Assert.assertFalse(
                "stateResultProduct loading must be false",
                viewModel?.stateResultProduct?.value?.loading ?: true
            )

            Assert.assertEquals(
                "stateResult error must be default",
                "default",
                viewModel?.stateResultProduct?.value?.error
            )
        }
    }

    @Test
    fun `test get product detail with service error`() {
        runBlocking {
            val bodyErrorServices: ResponseBody = mockk(relaxUnitFun = true)

            mockkObject(Network)
            every { Network.checkNetworkStateWithNetworkCapabilities(viewModel!!.context) } returns true

            every { bodyErrorServices.contentType() } returns null
            every { bodyErrorServices.contentLength() } returns 0

            coEvery { mockApiService.getProduct("MCO0000") } returns Response.error(
                404,
                bodyErrorServices
            )

            coEvery { mockApiService.getProductDescription("MCO0000") } returns Response.error(
                404,
                bodyErrorServices
            )

            //Execute test
            viewModel?.callSearchProductPager(
                query = "Test",
                type = SearchType.BY_TERMS.type
            )
            //Execute assert
            Assert.assertFalse(
                "stateResult loading must be false",
                viewModel?.stateResult?.value?.loading ?: true
            )
            Assert.assertTrue(
                "stateResult endReached must be true",
                viewModel?.stateResult?.value?.endReached ?: false
            )

            Assert.assertTrue(
                "stateResult error must be not empty",
                viewModel?.stateResult?.value?.error?.isNotEmpty() ?: false
            )
        }
    }

    @Test
    fun `test update search text`() {
        //Execute test
        viewModel?.updateSearchTextState("other")
        viewModel?.setAutoCompleteTerms("other")

        //Execute assert
        Assert.assertEquals(
            "searchTextState field must be other",
            "other",
            viewModel?.searchTextState?.value
        )
    }

    @Test
    fun `test get search history`() {
        every { mockSharedPreferenceUtils.getLastSearchedList(viewModel!!.context) } returns arrayListOf()

        //Execute test
        val result = viewModel?.obtainLastSearchedItems()

        //Execute assert
        Assert.assertEquals(
            "lastSearchedItems size field must be 0",
            0,
            result?.size
        )
    }
}
