package com.mercadolibre.app.ui.screens.main

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mercadolibre.app.R
import com.mercadolibre.app.ui.events.MainScreenEvents
import com.mercadolibre.app.ui.theme.*

@Composable
fun HeaderMain(
    showMessage: Boolean = true,
    showSearchBar: Boolean = true,
    resultQuery: String,
    onEvent: (action: MainScreenEvents) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        elevation = AppBarDefaults.TopAppBarElevation,
        color = Yellow100,
        shape = RoundedCornerShape(
            topStart = 0.dp,
            topEnd = 0.dp,
            bottomEnd = 8.dp,
            bottomStart = 8.dp
        )
    ) {
        Column(
            modifier = modifier
                .background(Yellow100)
                .wrapContentHeight()
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 24.dp,
                    bottom = 0.dp
                )
                .fillMaxWidth()
        ) {

            if (!showMessage) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    IconButton(
                        modifier = Modifier
                            .align(Alignment.CenterVertically),
                        onClick = {
                            onEvent.invoke(MainScreenEvents.GotoBack)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back",
                            tint = GeneralBlack
                        )
                    }

                    Text(
                        text = resultQuery,
                        style = proximeNovaBold,
                        color = GeneralBlack,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Start,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .fillMaxWidth(fraction = 1f)
                            .align(Alignment.CenterVertically),
                    )
                }
            }

            if (showSearchBar) {
                Row(modifier = Modifier
                    .background(
                        color = GeneralWhite,
                        shape = ShapesRounded.small
                    )
                    .padding(6.dp)
                    .clickable(enabled = true) {
                        onEvent.invoke(MainScreenEvents.GotoSearch)
                    }
                    .wrapContentHeight(Alignment.CenterVertically)
                    .fillMaxWidth()

                ) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Back",
                        tint = GeneralBlack,
                        modifier = Modifier.alpha(ContentAlpha.disabled)
                    )
                    Text(
                        modifier = Modifier
                            .alpha(ContentAlpha.disabled)
                            .fillMaxWidth(fraction = 1f)
                            .align(Alignment.CenterVertically),
                        text = stringResource(R.string.main_header_search_message),
                        maxLines = 1,
                        style = proximeNovaRegular,
                        fontSize = 14.sp,
                        overflow = TextOverflow.Ellipsis,
                        color = GeneralBlack
                    )
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            ) {
                if (showMessage) {
                    Text(
                        text = stringResource(R.string.main_header_message),
                        style = proximeNovaSemiBold,
                        color = GeneralBlack,
                        fontSize = 14.sp,
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                    Text(
                        text = resultQuery,
                        style = proximeNovaBold,
                        color = GeneralBlack,
                        fontSize = 16.sp,
                        textAlign = TextAlign.Start
                    )
                    Spacer(modifier = Modifier.size(8.dp))
                }
            }
        }
    }
}


@Preview(name = "Header Main")
@Composable
fun HeaderMainPreview() {
    HeaderMain(
        showMessage = true,
        resultQuery = "Categorias",
        onEvent = {},
    )
}

@Preview(name = "Header Main hide message")
@Composable
fun HeaderMainHideMessagePreview() {
    HeaderMain(
        showMessage = false,
        resultQuery = "Categorias",
        onEvent = {},
    )
}

@Preview(name = "Header Main hide message and searchbar")
@Composable
fun HeaderMainHideAllPreview() {
    HeaderMain(
        showSearchBar = false,
        showMessage = false,
        resultQuery = "Categorias",
        onEvent = {},
    )
}

