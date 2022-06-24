package com.mercadolibre.app.ui.screens.listresult

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mercadolibre.app.ui.events.MainScreenEvents
import com.mercadolibre.app.ui.events.ResultScreenEvents
import com.mercadolibre.app.ui.events.ResultState
import com.mercadolibre.app.ui.screens.error.ErrorScreen
import com.mercadolibre.app.ui.screens.main.HeaderMain
import com.mercadolibre.app.ui.theme.GeneralBackGround
import com.mercadolibre.app.ui.utils.AnimateContent
import com.mercadolibre.app.ui.utils.LoaderSearch
import com.mercadolibre.app.viewmodel.RootViewModel

@ExperimentalFoundationApi
@Composable
fun ResultScreen(
    viewModel: RootViewModel,
    queryType: String,
    query: String,
    queryTitle: String,
    onEvent: (action: ResultScreenEvents) -> Unit
) {
    val state by viewModel.stateResult

    if (state.query != "" && state.query != query && !state.loading) {
        LaunchedEffect(key1 = true) {
            viewModel.clearSearch()
        }
    } else {
        if (state.data.isEmpty() && !state.endReached && !state.loading) {
            viewModel.getSearchProduct(query, queryType)
        }
    }

    Scaffold(
        topBar = {
            HeaderMain(
                modifier = Modifier.fillMaxWidth(),
                onEvent = {
                    if (it is MainScreenEvents.GotoBack) {
                        onEvent.invoke(ResultScreenEvents.GoBack)
                    }
                    if (it is MainScreenEvents.GotoSearch) {
                        onEvent.invoke(ResultScreenEvents.GotoSearch)
                    }
                },
                resultQuery = queryTitle,
                showMessage = false
            )
        }
    ) {

        BodyResult(
            state = state,
            viewModel = viewModel,
            queryType = queryType,
            query = query,
            onEvent = onEvent
        )
    }
}

@Composable
fun BodyResult(
    state: ResultState,
    viewModel: RootViewModel,
    queryType: String,
    query: String,
    onEvent: (action: ResultScreenEvents) -> Unit
) {
    LazyColumn(
        contentPadding = PaddingValues(
            start = 16.dp,
            end = 16.dp,
            top = 16.dp,
            bottom = 32.dp
        ),
        modifier = Modifier
            .background(color = GeneralBackGround)
            .fillMaxSize()
    ) {
        items(state.data.size) { index ->
            val data = state.data[index]

            if (index >= state.data.size - 1 && !state.endReached && !state.loading) {
                viewModel.getSearchProduct(query, queryType)
            }
            ResultItem(result = data,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                ) {
                    onEvent.invoke(
                        ResultScreenEvents.GotoDetail(
                            idProduct = data.id.orEmpty(),
                            titleProduct = data.title.orEmpty(),
                        )
                    )
                }
            )
        }
        if (state.loading) {
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp),
                    horizontalArrangement = Arrangement.Center
                ) {
                    LoaderSearch(modifier = Modifier.size(100.dp, 16.dp))
                }
            }
        }
    }

    AnimateContent(visible = state.error != null && !state.loading) {
        ErrorScreen(
            message = state.error.orEmpty(),
            modifier = Modifier.fillMaxWidth()
        )
    }
}