package com.mercadolibre.app.ui.screens.main

import android.content.res.Configuration
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.mercadolibre.app.R
import com.mercadolibre.app.ui.events.MainScreenEvents
import com.mercadolibre.app.ui.events.MainState
import com.mercadolibre.app.ui.screens.error.ErrorScreen
import com.mercadolibre.app.ui.theme.*
import com.mercadolibre.app.ui.utils.AnimateContent
import com.mercadolibre.app.viewmodel.RootViewModel
import soup.compose.material.motion.MaterialMotion
import soup.compose.material.motion.materialSharedAxisZ

@ExperimentalFoundationApi
@Composable
fun MainScreen(
    viewModel: RootViewModel,
    onEvent: (action: MainScreenEvents) -> Unit
) {
    val mainState by viewModel.categoriesState
    LaunchedEffect(key1 = true) {
        viewModel.getCategories()
    }
    BobyMain(
        mainState,
        onEvent
    )
}

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun BobyMain(
    state: MainState,
    onEvent: (action: MainScreenEvents) -> Unit
) {
    var numberByRow by remember { mutableStateOf(3) }
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))

    val configuration = LocalConfiguration.current
    numberByRow = when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            4
        }
        else -> {
            3
        }
    }

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = {
            HeaderMain(
                modifier = Modifier.fillMaxWidth(),
                onEvent = onEvent,
                resultQuery = stringResource(R.string.main_categories_popular)
            )
        },
        drawerElevation = 8.dp
    ) {
        ConstraintLayout(
            modifier = Modifier
                .background(color = GeneralBackGround)
                .fillMaxSize()
        ) {
            val (content) = createRefs()
            MaterialMotion(
                modifier = Modifier.constrainAs(content) {
                    top.linkTo(parent.top, margin = 0.dp)
                    start.linkTo(parent.start, margin = 0.dp)
                    end.linkTo(parent.end, margin = 0.dp)
                    width = Dimension.fillToConstraints
                },
                targetState = state,
                motionSpec = {
                    materialSharedAxisZ(durationMillis = 500)
                },
                pop = state is MainState.SuccessGetCategories
            ) { newScreen ->

                when (newScreen) {
                    is MainState.Loading -> {
                        Log.e("Scren", "Loading")
                        val (shimmerContent) = createRefs()
                        Column(modifier = Modifier
                            .constrainAs(shimmerContent) {
                                top.linkTo(parent.top, margin = 16.dp)
                                start.linkTo(parent.start, margin = 16.dp)
                                end.linkTo(parent.end, margin = 16.dp)
                                width = Dimension.fillToConstraints
                            }
                        ) {
                            AnimatedShimmerCategories(numberByRow)
                        }
                    }
                    is MainState.SuccessGetCategories -> {
                        Log.e("Scren", "SuccessGetCategories")
                        val (shimmerContent) = createRefs()
                        Column(modifier = Modifier
                            .constrainAs(shimmerContent) {
                                top.linkTo(parent.top, margin = 2.dp)
                                start.linkTo(parent.start, margin = 2.dp)
                                end.linkTo(parent.end, margin = 2.dp)
                                width = Dimension.fillToConstraints
                                height = Dimension.fillToConstraints
                            }
                        ) {
                            Categories(newScreen.categories, numberByRow, onEvent)
                        }
                    }
                    is MainState.ErrorNetwork -> {
                        val (empty) = createRefs()
                        AnimateContent(visible = true, modifier = Modifier
                            .constrainAs(empty) {
                                top.linkTo(parent.top, margin = 2.dp)
                                start.linkTo(parent.start, margin = 2.dp)
                                end.linkTo(parent.end, margin = 2.dp)
                                width = Dimension.fillToConstraints
                                height = Dimension.fillToConstraints
                            }) {
                            ErrorScreen(
                                message = stringResource(id = R.string.error_network),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                    is MainState.Error -> {
                        val (empty) = createRefs()
                        AnimateContent(visible = true, modifier = Modifier
                            .constrainAs(empty) {
                                top.linkTo(parent.top, margin = 2.dp)
                                start.linkTo(parent.start, margin = 2.dp)
                                end.linkTo(parent.end, margin = 2.dp)
                                width = Dimension.fillToConstraints
                            }) {
                            ErrorScreen(
                                message = stringResource(id = R.string.error_general),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }
    }
}


