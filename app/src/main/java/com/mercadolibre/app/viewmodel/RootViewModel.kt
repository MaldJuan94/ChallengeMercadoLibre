package com.mercadolibre.app.viewmodel

import android.app.Application
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mercadolibre.app.R
import com.mercadolibre.app.models.categories.Categories
import com.mercadolibre.app.models.description.ProductDescription
import com.mercadolibre.app.models.detail.DetailResponse
import com.mercadolibre.app.models.error.CodeError
import com.mercadolibre.app.models.search.Paging
import com.mercadolibre.app.models.search.Results
import com.mercadolibre.app.ui.events.MainState
import com.mercadolibre.app.ui.events.ResultProductState
import com.mercadolibre.app.ui.events.ResultState
import com.mercadolibre.app.ui.utils.ComposePager
import com.mercadolibre.app.usescases.*
import com.mercadolibre.app.utils.Network
import com.mercadolibre.app.utils.NetworkUtilities
import com.mercadolibre.app.utils.SearchType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RootViewModel @Inject constructor(
    app: Application,
    private val dispatcher: CoroutineDispatcher,
    private val getSearchProductUseCase: GetSearchProductUseCase,
    private val getSearchProductByCategoryUseCase: GetSearchProductByCategoryUseCase,
    private val getProductUseCase: GetProductUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase,
    private val getProductDescriptionUseCase: GetProductDescriptionUseCase,
    private val saveKeyWordInSearchHistoryUseCase: SaveKeyWordInSearchHistoryUseCase,
    private val getLastSearchedItemsUseCase: GetLastSearchedItemsUseCase,
) : AndroidViewModel(app),
    GetSearchProductUseCase.GetSearchProductUseCaseListener,
    GetSearchProductByCategoryUseCase.GetSearchProductByCategoryUseCaseListener,
    GetProductUseCase.GetProductUseCaseListener,
    GetCategoriesUseCase.GetCategoriesCaseListener,
    GetProductDescriptionUseCase.GetProductDescriptionUseCaseListener {
    var context = app

    private val _categoriesState: MutableState<MainState> =
        mutableStateOf(value = MainState.Loading)
    val categoriesState: State<MainState> = _categoriesState

    private val _searchTextState: MutableState<String> =
        mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    private val _stateResult: MutableState<ResultState> =
        mutableStateOf(value = ResultState())
    val stateResult: State<ResultState> = _stateResult

    private val _stateResultProduct: MutableState<ResultProductState> =
        mutableStateOf(value = ResultProductState())
    val stateResultProduct: State<ResultProductState> = _stateResultProduct

    private val pagerCategories = ComposePager(
        initialKey = 0,
        onLoadUpdated = ::onLoadUpdatedSearch,
        onRequest = ::onRequestSearch,
        onGetNextKey = ::onGetNextKeySearch,
        onError = ::onErrorSearch,
        onSuccess = ::onSuccessSearch
    )

    fun onLoadUpdatedSearch(loading: Boolean, type: String) {
        val typeEnum = when (type) {
            SearchType.BY_CATEGORY.type -> SearchType.BY_CATEGORY
            SearchType.BY_TERMS.type -> SearchType.BY_TERMS
            else -> SearchType.BY_TERMS
        }
        _stateResult.value = _stateResult.value.copy(loading = loading, type = typeEnum)
    }

    private suspend fun onRequestSearch(
        key: Int,
        query: String,
        type: String
    ): Result<List<Results>> {
        val offset = key * 50
        if (offset < _stateResult.value.paging.total || _stateResult.value.paging.total == 0) {
            when (type) {
                SearchType.BY_CATEGORY.type -> {
                    return getSearchProductByCategory(
                        key = key,
                        query = query,
                        type = SearchType.BY_CATEGORY
                    )
                }
                SearchType.BY_TERMS.type -> {
                    return getSearchProduct(
                        key = key,
                        query = query,
                        type = SearchType.BY_TERMS
                    )
                }
                else -> {
                    return getSearchProduct(
                        query = query,
                        key = key,
                        type = SearchType.BY_TERMS
                    )
                }
            }
        } else {
            return Result.success(listOf())
        }
    }

    private fun onGetNextKeySearch(data: List<Results>): Int {
        return if (data.isEmpty()) {
            _stateResult.value.page
        } else {
            _stateResult.value.page + 1
        }
    }

    private fun onErrorSearch(cause: Throwable?) {
        _stateResult.value =
            _stateResult.value.copy(error = cause?.message, endReached = true, loading = false)
    }

    private fun onSuccessSearch(data: List<Results>, newKey: Int, query: String, type: String) {
        if (type == SearchType.BY_TERMS.type && data.isNotEmpty()) {
            saveKeywordSelectedSearch(query)
        }
        if (newKey == 1 && data.isEmpty()) {
            onFailureGetSearchProduct(
                CodeError(code = "", message = context.getString(R.string.search_empty))
            )
        } else {
            _stateResult.value = _stateResult.value.copy(
                data = _stateResult.value.data + data,
                endReached = data.isEmpty(),
                page = newKey
            )
        }
    }

    private suspend fun getSearchProductByCategory(
        key: Int,
        query: String,
        type: SearchType
    ): Result<List<Results>> {
        val response = getSearchProductByCategoryUseCase(
            limit = 50,
            offset = key * 50,
            category = query,
            listener = this@RootViewModel
        )
        _stateResult.value =
            _stateResult.value.copy(
                paging = response?.paging ?: Paging(),
                type = type,
                query = query
            )
        return Result.success(response?.results.orEmpty())
    }

    private suspend fun getSearchProduct(
        key: Int,
        query: String,
        type: SearchType
    ): Result<List<Results>> {
        val response = getSearchProductUseCase(
            limit = 50,
            offset = key * 50,
            query = query,
            listener = this@RootViewModel
        )
        _stateResult.value =
            _stateResult.value.copy(
                paging = response?.paging ?: Paging(),
                type = type,
                query = query
            )
        return Result.success(response?.results.orEmpty())
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }

    fun getDataProduct(id: String) {
        getProduct(id)
        getProductDescription(id)
        val prices = _stateResult.value.data.find { it.id == id }?.prices
        _stateResultProduct.value = _stateResultProduct.value.copy(prices = prices)
    }

    private fun getProduct(id: String) {
        viewModelScope.launch(dispatcher) {
            getProductUseCase(
                idProduct = id,
                listener = this@RootViewModel
            )
        }
    }

    private fun getProductDescription(id: String) {
        viewModelScope.launch(dispatcher) {
            getProductDescriptionUseCase(
                idProduct = id,
                listener = this@RootViewModel
            )
        }
    }

    fun clearSearch() {
        pagerCategories.onReset()
        _stateResult.value = ResultState()
        _searchTextState.value = ""
    }

    fun setAutoCompleteTerms(terms: String) {
        _searchTextState.value = terms
    }

    fun clearDetail() {
        _stateResultProduct.value = ResultProductState()
    }

    fun callSearchProductPager(query: String, type: String) {
        viewModelScope.launch(dispatcher) {
            pagerCategories.onLoadNextData(query, type)
        }
    }

    fun getCategories() {
        if (_categoriesState.value !is MainState.SuccessGetCategories) {
            _categoriesState.value = MainState.Loading
            viewModelScope.launch(dispatcher) {
                getCategoriesUseCase(
                    listener = this@RootViewModel
                )
            }
        }
    }

    private fun validateNetworkError(errors: CodeError): Boolean {
        return ((errors.code == NetworkUtilities.NOT_NETWORK_ERROR_CODE)
                || !Network.checkNetworkStateWithNetworkCapabilities(context))
    }

    private fun saveKeywordSelectedSearch(query: String) {
        saveKeyWordInSearchHistoryUseCase.invoke(query)
    }

    fun obtainLastSearchedItems(): ArrayList<String> {
        return getLastSearchedItemsUseCase.invoke()
    }

    override fun onFailureGetSearchProduct(errors: CodeError) {
        if (validateNetworkError(errors)) {
            _stateResult.value =
                _stateResult.value.copy(
                    error = context.getString(R.string.error_network),
                    endReached = true,
                    loading = false
                )
        } else {
            _stateResult.value =
                _stateResult.value.copy(error = errors.message, endReached = true, loading = false)

        }
    }

    override fun onFailureGetSearchProductByCategory(errors: CodeError) {
        onFailureGetSearchProduct(errors)
    }

    override fun onSuccessGetProduct(response: DetailResponse) {
        _stateResultProduct.value = _stateResultProduct.value.copy(loading = false, data = response)
    }

    override fun onFailureGetProduct(errors: CodeError) {
        if (validateNetworkError(errors)) {
            _stateResultProduct.value =
                _stateResultProduct.value.copy(
                    loading = false,
                    error = context.getString(R.string.error_network),
                )
        } else {
            _stateResultProduct.value =
                _stateResultProduct.value.copy(loading = false, error = errors.message)
        }
    }

    override fun onSuccessGetCategories(response: ArrayList<Categories>) {
        _categoriesState.value = MainState.SuccessGetCategories(response)
    }

    override fun onFailureGetCategories(errors: CodeError) {
        if (validateNetworkError(errors)) {
            _categoriesState.value = MainState.ErrorNetwork
        } else {
            _categoriesState.value = MainState.Error(errors.message.orEmpty())
        }
    }

    override fun onSuccessGetProductDescription(response: ProductDescription) {
        _stateResultProduct.value = _stateResultProduct.value.copy(description = response)
    }

    override fun onFailureGetProductDescription(errors: CodeError) {
        if (validateNetworkError(errors)) {
            _stateResultProduct.value =
                _stateResultProduct.value.copy(
                    loading = false,
                    error = context.getString(R.string.error_network),
                )
        }
    }
}