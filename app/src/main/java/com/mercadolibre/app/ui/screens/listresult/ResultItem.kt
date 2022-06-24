package com.mercadolibre.app.ui.screens.listresult

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import coil.compose.rememberAsyncImagePainter
import com.mercadolibre.app.models.search.Results
import com.mercadolibre.app.R
import com.mercadolibre.app.ui.theme.*
import com.mercadolibre.app.utils.UtilFormat

@Composable
fun ResultItem(result: Results, modifier: Modifier = Modifier) {

    Card(
        border = BorderStroke(0.5.dp, GrayOne),
        elevation = 1.dp,
        backgroundColor = GeneralWhite,
        modifier = modifier
            .padding(5.dp)
            .wrapContentSize(Alignment.Center)
            .fillMaxWidth()
    ) {

        ConstraintLayout(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .background(GeneralWhite)
                .fillMaxWidth()
        ) {
            val (icon, data) = createRefs()

            Image(
                painter = rememberAsyncImagePainter(
                    model = result.thumbnail,
                    contentScale = ContentScale.Fit,
                    error = painterResource(id = R.drawable.ic_image_no_found),
                    placeholder = painterResource(id = R.drawable.ic_image_no_found)
                ),
                contentDescription = null,
                contentScale = ContentScale.FillWidth,
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .constrainAs(icon) {
                        top.linkTo(parent.top, margin = 16.dp)
                        start.linkTo(parent.start, margin = 16.dp)
                        bottom.linkTo(parent.bottom, margin = 16.dp)
                    }
            )


            Column(modifier = Modifier.constrainAs(data) {
                top.linkTo(icon.top)
                start.linkTo(icon.end, margin = 8.dp)
                end.linkTo(parent.end, margin = 16.dp)
                bottom.linkTo(icon.bottom)
                width = Dimension.fillToConstraints
            }) {
                Text(
                    text = result.title.orEmpty(),
                    style = proximeNovaRegular,
                    color = GeneralBlack,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )

                val promotionPrices = result.prices?.prices?.find { it.type == "promotion" }
                val offPrice =
                    (((promotionPrices?.amount ?: 0) * 100) / (promotionPrices?.regularAmount ?: 1))
                var priceStr = UtilFormat.moneyFormat(result.price ?: 0)
                val offPriceStr = "${100 - offPrice}% OFF"

                if (promotionPrices != null && offPrice > 0) {
                    priceStr += " $offPriceStr"
                    Text(
                        text = UtilFormat.moneyFormat(promotionPrices.regularAmount ?: 0),
                        style = proximeNovaLight.copy(
                            textDecoration = TextDecoration.LineThrough
                        ),
                        color = GrayTwo,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Start,
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Text(
                    text = buildAnnotatedString {
                        append(priceStr)
                        if (offPrice > 0) {
                            addStyle(
                                style = SpanStyle(
                                    color = GreenOne,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                ),
                                start = priceStr.indexOf(offPriceStr),
                                end = priceStr.indexOf(offPriceStr) + offPriceStr.length,
                            )
                        }
                    },
                    style = proximeNovaSemiBold,
                    color = GeneralBlack,
                    fontSize = 20.sp,
                    textAlign = TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
