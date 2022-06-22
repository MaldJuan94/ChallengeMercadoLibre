package com.mercadolibre.app.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mercadolibre.app.repository.RecipeRepository
import com.mercadolibre.app.ui.events.SearchWidgetState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RootViewModel @Inject constructor(
    app: Application,
    private val repository: RecipeRepository
) : AndroidViewModel(app) {
    var context = app

    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState

    private val _searchTextState: MutableState<String> =
        mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }

    fun getData() {
        viewModelScope.launch {
            newSearch()
        }
    }

    private suspend fun newSearch() {
        val result = repository.searchProduct(
            page = 1,
            query = "Motorola"
        )
        Log.e("Error", result.toString())

        getProduct(result.data?.results?.get(0)?.id.orEmpty())
        getProductDesc(result.data?.results?.get(0)?.id.orEmpty())
    }

    fun getProduct(id: String) {
        viewModelScope.launch {
            getProductSuspend(id)
        }
    }

    private suspend fun getProductSuspend(id: String) {
        val result = repository.getProduct(
            id = id
        )
        Log.e("PRODUCTO", result.errorMessage.orEmpty())
        Log.e("PRODUCTO", result.data?.thumbnail.orEmpty())
    }

    fun getProductDesc(id: String) {
        viewModelScope.launch {
            getProductDescSuspend(id)
        }
    }

    private suspend fun getProductDescSuspend(id: String) {
        val result = repository.getProductDescription(
            id = id
        )
        Log.e("PRODUCTO DEs", result.errorMessage.orEmpty())
        Log.e("PRODUCTO DEs", result.data?.plainText.orEmpty())
    }
}