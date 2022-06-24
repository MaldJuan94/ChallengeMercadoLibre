package com.mercadolibre.app.ui.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.mercadolibre.app.utils.SearchType

sealed class NavItem(
    internal val baseRoute: String,
    private val navArgs: List<NavArgs> = emptyList()
) {
    object SplashNavItem : NavItem("splash")
    object MainNavItem : NavItem("main")
    object ResultNavItem : NavItem(
        "searchType",
        listOf(NavArgs.SearchType, NavArgs.SearchQuery, NavArgs.SearchQueryTitle)
    ) {
        fun createRoute(searchType: SearchType, searchQuery: String, searchQueryTitle: String) =
            "$baseRoute/${searchType.type}/$searchQuery/$searchQueryTitle"
    }

    object SearchNavItem : NavItem("search")
    object DetailNavItem : NavItem("detail", listOf(NavArgs.ProductId, NavArgs.ProductTitle)) {
        fun createRoute(productId: String, productTitle: String) =
            "$baseRoute/$productId/$productTitle"
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
    ProductId("productId", NavType.StringType),
    ProductTitle("productTitle", NavType.StringType),
    SearchType("searchType", NavType.StringType),
    SearchQuery("searchQuery", NavType.StringType),
    SearchQueryTitle("searchQueryTitle", NavType.StringType)
}