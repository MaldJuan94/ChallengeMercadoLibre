package com.mercadolibre.app.ui.navigarion

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mercadolibre.app.ui.screens.detail.DetailScreen
import com.mercadolibre.app.ui.screens.main.MainScreen
import com.mercadolibre.app.ui.screens.splash.SplashScreen
import com.mercadolibre.app.ui.utils.DevicePosture
import com.mercadolibre.app.ui.viewmodel.RootViewModel
import kotlinx.coroutines.flow.StateFlow

@ExperimentalFoundationApi
@Composable
fun Navigation(
    viewModel: RootViewModel,
    devicePosture: StateFlow<DevicePosture>
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
        composable(NavItem.MainNavItem) {
            MainScreen(
                viewModel = viewModel,
                onNavigate = {
                    navController.navigate(NavItem.DetailNavItem.createRoute(it))
                }
            )
        }
        composable(NavItem.DetailNavItem) { backStackEntry ->
            DetailScreen(
                viewModel = viewModel,
                productId = backStackEntry.findArg(NavArgs.ProductId.key),
                onUpClick = { navController.popBackStack() }
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
