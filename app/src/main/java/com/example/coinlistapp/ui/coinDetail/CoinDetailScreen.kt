package com.example.coinlistapp.ui.coinDetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.coinlistapp.R
import com.example.coinlistapp.data.dto.CoinDetail

@Composable
fun CoinDetailScreen(state: CoinDetailState) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        if (state.isLoading) {
            CircularProgressIndicator(
                Modifier
                    .fillMaxSize()
                    .wrapContentSize(Alignment.Center)
            )
        } else if (state.error != null) {
            Text("${stringResource(id = R.string.coin_detail_id)} ${state.error}")
        } else if (state.coin != null) {
            CoinDetailContent(coinDetail = state.coin)
        } else {
            Text(stringResource(id = R.string.empty_data_error))
        }
    }
}

@Composable
fun CoinDetailContent(coinDetail: CoinDetail) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = coinDetail.name, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "${stringResource(id = R.string.coin_detail_id)} ${coinDetail.id}",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "${stringResource(id = R.string.coin_detail_symbol)} ${coinDetail.symbol}",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "${stringResource(id = R.string.coin_detail_hash_algorithm)} ${coinDetail.hashAlgorithm}",
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "${stringResource(id = R.string.coin_detail_description)} ${coinDetail.description}",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}