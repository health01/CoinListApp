package com.example.coinlistapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.coinlistapp.presentation.coinDetail.CoinDetailScreen
import com.example.coinlistapp.presentation.coinDetail.CoinDetailViewModel
import com.example.coinlistapp.presentation.coinList.CoinHomeScreen
import com.example.coinlistapp.presentation.coinList.CoinListViewModel
import com.example.coinlistapp.ui.theme.CoinListAppTheme
import com.example.coinlistapp.util.CoinDetail
import com.example.coinlistapp.util.Home
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CoinListAppTheme {
                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    CoinNavHost(navController)
                }
            }
        }
    }

    @Composable
    fun CoinNavHost(
        navController: NavHostController,
        modifier: Modifier = Modifier
    ) {
        NavHost(
            navController = navController,
            startDestination = Home.route,
            modifier = modifier
        ) {
            composable(route = Home.route) {
                HomeDestination(navController)
            }

            composable(
                route = CoinDetail.routeWithArgs,
                arguments = CoinDetail.arguments
            ) {
                CoinDetailDestination()
            }
        }
    }


}

@Composable
private fun CoinDetailDestination() {
    val coinDetailViewModel: CoinDetailViewModel = hiltViewModel()
    val state by coinDetailViewModel.state.collectAsStateWithLifecycle()
    CoinDetailScreen(state = state)
}

@Composable
private fun HomeDestination(
    navController: NavHostController,
) {
    val coinListViewModel: CoinListViewModel = hiltViewModel()
    val state by coinListViewModel.state.collectAsState()

    CoinHomeScreen(
        state = state,
        onRefreshRequested = { coinListViewModel.getCoinList() },
        onNavigationRequested = { itemId ->
            Log.d("onNavigationRequested", itemId)
            navController.navigate("${CoinDetail.route}/$itemId")
        }
    )
}