package com.mercadolibre.app.ui.navigarion

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavItem(
    internal val baseRoute: String,
    private val navArgs: List<NavArgs> = emptyList()
) {
    object SplashNavItem : NavItem("splash")
    object MainNavItem : NavItem("main")
    object DetailNavItem : NavItem("detail", listOf(NavArgs.ProductId)) {
        fun createRoute(productId: String) = "$baseRoute/$productId"
    }

    val route = run {
        val argValues = navArgs.map { "{${it.key}}" }
        listOf(baseRoute)
            .plus(argValues)
            .joinToString("/")
    }

    val args = navArgs.map {
        navArgument(it.key) { type = it.navType }
    }
}

enum class NavArgs(val key: String, val navType: NavType<*>) {
    ProductId("productId", NavType.StringType)
}