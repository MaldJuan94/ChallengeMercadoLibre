package com.mercadolibre.app.ui.screens.search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mercadolibre.app.R
import com.mercadolibre.app.ui.events.ScreenEvents
import com.mercadolibre.app.ui.theme.*
import com.mercadolibre.app.viewmodel.RootViewModel

@ExperimentalFoundationApi
@Composable
fun SearchScreen(
    viewModel: RootViewModel,
    onEvent: (action: ScreenEvents) -> Unit
) {
    val searchTextState by viewModel.searchTextState
    val listSearch = remember { mutableStateOf(viewModel.obtainLastSearchedItems()) }

    Scaffold(
        topBar = {
            MainAppBar(
                searchTextState = searchTextState,
                onTextChange = {
                    viewModel.updateSearchTextState(newValue = it)
                },
                onCloseClicked = {
                    onEvent.invoke(ScreenEvents.GoBack)
                },
                onSearchClicked = {
                    onEvent.invoke(ScreenEvents.GoToResult(it))
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .background(color = GeneralBackGround)
                .fillMaxSize()
        ) {
            items(listSearch.value.size) { index ->
                val data = listSearch.value[index]
                SearchItemHistory(data, onEvent)
            }
        }
    }
}

@Composable
fun MainAppBar(
    searchTextState: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    SearchAppBar(
        text = searchTextState,
        onTextChange = onTextChange,
        onCloseClicked = onCloseClicked,
        onSearchClicked = onSearchClicked
    )
}


@Composable
fun SearchAppBar(
    text: String,
    onTextChange: (String) -> Unit,
    onCloseClicked: () -> Unit,
    onSearchClicked: (String) -> Unit,
) {
    val focusRequester = remember { FocusRequester() }

    LaunchedEffect(key1 = "onCreateView") {
        focusRequester.requestFocus()
    }

    val mCustomTextSelectionColors = TextSelectionColors(
        handleColor = Blue100,
        backgroundColor = Blue100.copy(
            alpha = 0.5f
        )
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = GeneralWhite
    ) {
        CompositionLocalProvider(LocalTextSelectionColors provides mCustomTextSelectionColors) {
            TextField(modifier = Modifier
                .focusRequester(focusRequester)
                .fillMaxWidth(),
                value = text,
                onValueChange = {
                    onTextChange(it)
                },
                placeholder = {
                    Text(
                        modifier = Modifier
                            .alpha(ContentAlpha.medium),
                        text = stringResource(id = R.string.main_header_search_message),
                        color = GeneralBlack,
                    )
                },
                textStyle = AppTypography.subtitle1,
                singleLine = true,
                leadingIcon = {
                    IconButton(
                        modifier = Modifier
                            .alpha(ContentAlpha.medium),
                        onClick = {
                            onCloseClicked()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back Icon",
                            tint = GeneralBlack
                        )
                    }
                },
                trailingIcon = {
                    IconButton(
                        onClick = {
                            if (text.isNotEmpty()) {
                                onTextChange("")
                            } else {
                                onCloseClicked()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Icon",
                            tint = GeneralBlack
                        )
                    }
                },
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        if (text.isNotEmpty()) {
                            onSearchClicked(text)
                        }
                    }
                ),
                colors = TextFieldDefaults.textFieldColors(
                    textColor = GeneralBlack,
                    backgroundColor = Color.Transparent,
                    cursorColor = GeneralBlack.copy(alpha = ContentAlpha.medium)
                ))
        }
    }
}

@Composable
private fun SearchItemHistory(term: String, onEvent: (action: ScreenEvents) -> Unit) {
    Row(
        modifier = Modifier
            .clickable {
                onEvent(ScreenEvents.GoToResult(term))
            }
            .fillMaxWidth()
            .background(GeneralWhite)
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_clock),
            tint = GrayTwo,
            contentDescription = null
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(weight = 3f, fill = false)
                    .padding(start = 16.dp)
            ) {

                Text(
                    text = term,
                    style = proximeNovaRegular,
                    color = GeneralBlack,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            IconButton(
                modifier = Modifier
                    .weight(weight = 1f, fill = false),
                onClick = {
                    onEvent(ScreenEvents.AutoCompleteSearch(term))
                }
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(id = R.drawable.ic_ico_arrow_up_left),
                    tint = GrayTwo,
                    contentDescription = "Autocomplete",
                )
            }
        }
    }
}

@Composable
@Preview
fun SearchAppBarPreview() {
    SearchAppBar(
        text = "Some random text",
        onTextChange = {},
        onCloseClicked = {},
        onSearchClicked = {}
    )
}