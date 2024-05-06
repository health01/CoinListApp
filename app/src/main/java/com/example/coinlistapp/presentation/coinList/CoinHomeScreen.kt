@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.coinlistapp.presentation.coinList

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.coinlistapp.R
import com.example.coinlistapp.data.dto.Coin

@Composable
fun CoinHomeScreen(
    state: CoinListState,
    onRefreshRequested: () -> Unit,
    onNavigationRequested: (itemId: String) -> Unit
) {
    val listItemVisibleAnimateState = remember { MutableTransitionState(false) }

    Scaffold(
        topBar = { CategoriesAppBar() },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                Button(
                    onClick = {
                        onRefreshRequested()
                        listItemVisibleAnimateState.targetState = false
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text("Refresh Coins")
                }

                CoinList(
                    state = state,
                    visibleState = listItemVisibleAnimateState,
                    onNavigationRequested = onNavigationRequested
                )
                Spacer(modifier = Modifier.weight(1f))

            }
        }
    )
}

@Composable
private fun CategoriesAppBar() {
    TopAppBar(
        title = { Text(stringResource(id = R.string.app_name)) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onSecondary
        )
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CoinList(
    state: CoinListState,
    visibleState: MutableTransitionState<Boolean>,
    onNavigationRequested: (itemId: String) -> Unit
) {
    visibleState.targetState = true

    if (state.isLoading) {
        CircularProgressIndicator(
            Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.Center)
        )
    } else if (state.error != null) {
        Text("Error: ${state.error}")
    } else {

        LazyColumn {
            items(state.coins.size) { index ->

                AnimatedVisibility(
                    visibleState = visibleState,
                    enter = fadeIn(
                        animationSpec = tween(durationMillis = 5000)
                    )
                ) {

                    CoinItem(
                        coin = state.coins[index],
                        onClick = { onNavigationRequested(state.coins[index].id) },
                        Modifier.animateItemPlacement()
                    )
                    if (index < state.coins.size - 1) { // Avoid adding a divider after the last item
                        Divider()
                    }
                }
            }
        }
    }
}


@Composable
fun CoinItem(coin: Coin, onClick: () -> Unit, modifier: Modifier) {
    Text(
        style = MaterialTheme.typography.titleMedium,
        text = coin.name,
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp)
            .background(color = Color.LightGray),
        textAlign = TextAlign.Center,

        color = Color.Black
    )
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    CoinHomeScreen(
        state = CoinListState(
            coins = listOf(
                Coin(
                    id = "bitcoin",
                    name = "Bitcoin",
                    symbol = "BTC",
                    rank = 1,
                    isNew = false,
                    isActive = true,
                    type = "Cryptocurrency"

                ), Coin(
                    id = "22",
                    name = "Bitcoin",
                    symbol = "BTC",
                    rank = 1,
                    isNew = false,
                    isActive = true,
                    type = "Cryptocurrency"

                )
            )
        ),
        onRefreshRequested = { },
        onNavigationRequested = { })
}
