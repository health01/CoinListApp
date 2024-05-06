package com.example.coinlistapp.repository.usecase

import com.example.coinlistapp.data.dto.Coin

fun List<Coin>.sortByName(): List<Coin> {
    return this.sortedBy { it.name }
}