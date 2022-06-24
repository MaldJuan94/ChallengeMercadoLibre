package com.mercadolibre.app.ui.navigation

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mercadolibre.app.ui.events.MainScreenEvents
import com.mercadolibre.app.ui.events.ResultScreenEvents
import com.mercadolibre.app.ui.events.ScreenEvents
import com.mercadolibre.app.ui.screens.detail.DetailScreen
import com.mercadolibre.app.ui.screens.listresult.ResultScreen
import com.mercadolibre.app.ui.screens.main.MainScreen
import com.mercadolibre.app.ui.screens.search.SearchScreen
import com.mercadolibre.app.ui.screens.splash.SplashScreen
import com.mercadolibre.app.utils.SearchType
import com.mercadolibre.app.utils.UtilFormat.encode
import com.mercadolibre.app.viewmodel.RootViewModel

@ExperimentalFoundationApi
@Composable
fun Navigation(
    viewModel: RootViewModel
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = NavItem.SplashNavItem.route
    ) {
        composable(NavItem.SplashNavItem) {
            SplashScreen(
                onFinished = {
                    navController.navigate(NavItem.MainNavItem.route) {
                        popUpTo(route = NavItem.SplashNavItem.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }
        composable(NavItem.SearchNavItem) {
            SearchScreen(
                viewModel = viewModel,
                onEvent = {
                    when (it) {
                        is ScreenEvents.GoToResult -> {
                            viewModel.clearSearch()
                            navController.navigate(
                                NavItem.ResultNavItem.createRoute(
                                    searchQuery = it.query,
                                    searchQueryTitle = it.query,
                                    searchType = SearchType.BY_TERMS
                                )
                            ) {
                                popUpTo(route = NavItem.SearchNavItem.route) {
                                    inclusive = true
                                }
                            }
                        }
                        is ScreenEvents.GoBack -> {
                            navController.popBackStack()
                        }
                        is ScreenEvents.AutoCompleteSearch -> {
                            viewModel.setAutoCompleteTerms(it.query)
                        }
                    }
                }
            )
        }
        composable(NavItem.ResultNavItem) { backStackEntry ->
            ResultScreen(
                viewModel = viewModel,
                queryTitle = backStackEntry.findArg(NavArgs.SearchQueryTitle.key),
                query = backStackEntry.findArg(NavArgs.SearchQuery.key),
                queryType = backStackEntry.findArg(NavArgs.SearchType.key),
                onEvent = {
                    when (it) {
                        is ResultScreenEvents.GotoDetail -> {
                            viewModel.clearDetail()
                            viewModel.getDataProduct(it.idProduct)
                            navController.navigate(
                                NavItem.DetailNavItem.createRoute(
                                    productId = it.idProduct,
                                    productTitle = it.titleProduct.encode()
                                )
                            )
                        }
                        is ResultScreenEvents.GoBack -> {
                            navController.popBackStack()
                        }
                        is ResultScreenEvents.GotoSearch -> {
                            navController.navigate(NavItem.SearchNavItem.route)
                        }
                    }
                }
            )
        }

        composable(NavItem.MainNavItem) {
            MainScreen(
                viewModel = viewModel,
                onEvent = {
                    when (it) {
                        is MainScreenEvents.GotoSearch -> {
                            navController.navigate(NavItem.SearchNavItem.route)
                        }
                        is MainScreenEvents.GoToCategory -> {
                            viewModel.clearSearch()
                            navController.navigate(
                                NavItem.ResultNavItem.createRoute(
                                    searchQuery = it.category.id.orEmpty(),
                                    searchQueryTitle = it.category.name.orEmpty(),
                                    searchType = SearchType.BY_CATEGORY
                                )
                            )
                        }
                        is MainScreenEvents.GotoBack -> {
                            navController.popBackStack()
                        }
                    }
                }
            )
        }
        composable(NavItem.DetailNavItem) { backStackEntry ->
            DetailScreen(
                viewModel = viewModel,
                productTitle = backStackEntry.findArg(NavArgs.ProductTitle.key),
                onBack = { navController.popBackStack() }
            )
        }
    }
}

private fun NavGraphBuilder.composable(
    navItem: NavItem,
    content: @Composable (NavBackStackEntry) -> Unit
) {
    composable(
        route = navItem.route,
        arguments = navItem.args
    ) {
        content(it)
    }
}

private inline fun <reified T> NavBackStackEntry.findArg(key: String): T {
    val value = arguments?.get(key)
    requireNotNull(value)
    return value as T
}
