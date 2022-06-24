package com.mercadolibre.app.ui.screens.main

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mercadolibre.app.models.categories.Categories
import com.mercadolibre.app.ui.events.MainScreenEvents
import com.mercadolibre.app.ui.theme.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Categories(
    response: ArrayList<Categories>,
    countByRow: Int,
    onEvent: (action: MainScreenEvents) -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        LazyVerticalGrid(
            cells = GridCells.Fixed(countByRow),
            state = rememberLazyListState(),
            contentPadding = PaddingValues(16.dp),
        ) {
            items(response.size) { index ->
                Card(
                    border = BorderStroke(0.5.dp, Blue100),
                    elevation = 2.dp,
                    backgroundColor = GeneralWhite,
                    shape = ShapesRounded.small,
                    modifier = Modifier
                        .clickable {
                            onEvent.invoke(MainScreenEvents.GoToCategory(response[index]))
                        }
                        .align(Alignment.CenterVertically)
                        .padding(5.dp)
                        .aspectRatio(1f)
                        .wrapContentSize(Alignment.Center)
                        .fillMaxSize()
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                    ) {
                        Text(
                            text = response[index].name.orEmpty(),
                            style = proximeNovaSemiBold,
                            color = Blue100,
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .padding(16.dp)
                                .align(Alignment.Center)
                        )
                    }
                }
            }
        }
    }
}


@Preview(name = "Login PCO")
@Composable
fun CategoriesPreview() {
    Categories(
        response = arrayListOf(Categories("test", "test")),
        countByRow = 3,
        onEvent = {},
    )
}
