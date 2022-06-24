package com.mercadolibre.app.ui.screens.detail

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.calculateCurrentOffsetForPage
import com.mercadolibre.app.R
import com.mercadolibre.app.models.detail.Attributes
import com.mercadolibre.app.ui.events.ResultProductState
import com.mercadolibre.app.ui.screens.error.ErrorScreen
import com.mercadolibre.app.ui.screens.main.HeaderMain
import com.mercadolibre.app.ui.theme.*
import com.mercadolibre.app.ui.utils.AnimateContent
import com.mercadolibre.app.ui.utils.LoaderSearch
import com.mercadolibre.app.utils.UtilFormat
import com.mercadolibre.app.utils.UtilFormat.decode
import com.mercadolibre.app.viewmodel.RootViewModel
import kotlin.math.absoluteValue

@Composable
fun DetailScreen(
    viewModel: RootViewModel,
    productTitle: String,
    onBack: () -> Unit
) {
    val state by viewModel.stateResultProduct
    Scaffold(
        topBar = {
            HeaderMain(
                modifier = Modifier.fillMaxWidth(),
                onEvent = {
                    onBack.invoke()
                },
                resultQuery = productTitle.decode(),
                showSearchBar = false,
                showMessage = false
            )
        }
    ) {
        if (state.loading) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(24.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                LoaderSearch(modifier = Modifier.size(100.dp, 16.dp))
            }
        }

        AnimateContent(visible = state.error.isNullOrEmpty() && !state.loading) {
            contentDetail(state)
        }

        AnimateContent(visible = state.error != null && !state.loading) {
            ErrorScreen(
                message = state.error.orEmpty(),
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalComposeUiApi::class)
@Composable
fun contentDetail(state: ResultProductState) {
    val scrollState = rememberScrollState()
    val itemCondition = remember {
        mutableStateOf(state.data.attributes.find { it.id == "ITEM_CONDITION" }?.valueName)
    }

    val isShowModal = rememberSaveable { mutableStateOf(false) }

    if (isShowModal.value) {
        AttributesDialog(state.data.attributes) { isShowModal.value = false }
    }

    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .fillMaxWidth()
            .background(GeneralBackGround)
    ) {

        if (itemCondition.value != null) {
            Text(
                text = itemCondition.value.orEmpty(),
                style = proximeNovaRegular,
                color = Blue100,
                fontSize = 16.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(
                        top = 24.dp,
                        start = 24.dp,
                        end = 24.dp,
                    )
                    .background(color = GeneralWhite, shape = ShapesRounded.small)
                    .border(BorderStroke(0.5.dp, Blue100))
                    .padding(4.dp)
            )
        }

        Text(
            text = state.data.title.orEmpty(),
            style = proximeNovaSemiBold,
            color = GeneralBlack,
            fontSize = 16.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 16.dp,
                    start = 24.dp,
                    end = 24.dp,
                    bottom = 16.dp
                )
        )

        HorizontalPager(
            count = state.data.pictures.size,
            reverseLayout = false,
        ) { page ->
            Card(
                backgroundColor = GeneralWhite,
                modifier = Modifier
                    .background(GeneralWhite)
                    .graphicsLayer {
                        val pageOffset = calculateCurrentOffsetForPage(page).absoluteValue
                        lerp(
                            start = 0.85f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        ).also { scale ->
                            scaleX = scale
                            scaleY = scale
                        }
                        alpha = lerp(
                            start = 0.5f,
                            stop = 1f,
                            fraction = 1f - pageOffset.coerceIn(0f, 1f)
                        )
                    }
            ) {
                Image(
                    painter = rememberAsyncImagePainter(
                        model = state.data.pictures[page].secureUrl,
                        contentScale = ContentScale.Fit,
                        error = painterResource(id = R.drawable.ic_image_no_found),
                        placeholder = painterResource(id = R.drawable.ic_image_no_found)
                    ),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                )
            }
        }

        val promotionPrices = state.prices?.prices?.find { it.type == "promotion" }
        val offPrice =
            (((promotionPrices?.amount ?: 0) * 100) / (promotionPrices?.regularAmount ?: 1))
        var priceStr = UtilFormat.moneyFormat(state.data.price ?: 0)
        val offPriceStr = "${100 - offPrice}% OFF"

        if (promotionPrices != null && offPrice > 0) {
            priceStr += " $offPriceStr"
            Text(
                text = UtilFormat.moneyFormat(promotionPrices.regularAmount ?: 0),
                style = proximeNovaLight.copy(
                    textDecoration = TextDecoration.LineThrough
                ),
                color = GrayTwo,
                fontSize = 20.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = 16.dp,
                        start = 24.dp,
                        end = 24.dp,
                    )
            )
        }

        Text(
            text = buildAnnotatedString {
                append(priceStr)
                if (offPrice > 0) {
                    addStyle(
                        style = SpanStyle(
                            color = GreenOne,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 26.sp
                        ),
                        start = priceStr.indexOf(offPriceStr),
                        end = priceStr.indexOf(offPriceStr) + offPriceStr.length,
                    )
                }
            },
            style = proximeNovaSemiBold,
            color = GeneralBlack,
            fontSize = 32.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 8.dp)
        )

        Text(
            text = stringResource(id = R.string.product_description),
            style = proximeNovaSemiBold,
            color = GeneralBlack,
            fontSize = 20.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 8.dp,
                    start = 24.dp,
                    end = 24.dp,
                    bottom = 16.dp
                )
        )

        Text(
            text = state.description.plainText.orEmpty(),
            style = proximeNovaRegular,
            color = GeneralBlack,
            fontSize = 16.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    bottom = 16.dp
                )
        )

        Text(
            text = stringResource(id = R.string.product_characteristics),
            style = proximeNovaSemiBold.copy(
                textDecoration = TextDecoration.Underline
            ),
            color = Blue100,
            fontSize = 20.sp,
            textAlign = TextAlign.Start,
            modifier = Modifier
                .clickable {
                    isShowModal.value = !isShowModal.value
                }
                .background(color = GeneralWhite)
                .fillMaxWidth()
                .padding(
                    top = 16.dp,
                    start = 24.dp,
                    end = 24.dp,
                    bottom = 16.dp
                )
        )
    }
}

@ExperimentalComposeUiApi
@Composable
fun AttributesDialog(
    attributes: ArrayList<Attributes>,
    onClickClose: () -> Unit
) {
    Dialog(
        onDismissRequest = onClickClose,
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true,
            usePlatformDefaultWidth = false
        )
    ) {
        AttributesDialogBody(
            attributes = attributes,
        )
    }
}

@Composable
fun AttributesDialogBody(
    attributes: ArrayList<Attributes>,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .padding(24.dp, 10.dp, 24.dp, 10.dp)
            .fillMaxWidth(),
        elevation = 8.dp
    ) {
        Column(
            modifier
                .background(Color.White)
        ) {
            TableScreen(attributes)
        }
    }
}

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    isBold: Boolean = false
) {
    Text(
        text = text,
        style = if (isBold) proximeNovaBold else proximeNovaRegular,
        color = GeneralBlack,
        fontSize = 14.sp,
        textAlign = TextAlign.Start,
        modifier = Modifier
            .weight(weight)
            .padding(8.dp)
    )
}

@Composable
fun TableScreen(
    attributes: ArrayList<Attributes>
) {
    val column1Weight = .5f
    val column2Weight = .5f
    LazyColumn(
        Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(attributes.size) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(GeneralBackGround)
            ) {
                TableCell(text = attributes[it].name ?: "-", weight = column1Weight, isBold = true)
                TableCell(text = attributes[it].valueName ?: "-", weight = column2Weight)
            }
        }
    }
}
