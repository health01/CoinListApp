package com.example.coinlistapp.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

interface CoinDestination {
    val route: String
}

object Home : CoinDestination {
    override val route = "home"
}

object CoinDetail : CoinDestination {
    override val route = "coin_detail"
    const val coinIdArg = "coin_id"
    val routeWithArgs = "$route/{$coinIdArg}"
    val arguments = listOf(navArgument(coinIdArg) { type = NavType.StringType }
    )
}