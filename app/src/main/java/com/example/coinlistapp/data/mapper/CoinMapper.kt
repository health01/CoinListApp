package com.example.coinlistapp.data.mapper

import com.example.coinlistapp.data.dto.Coin

fun List<Coin>.sortByName(): List<Coin> {
    return this.sortedBy { it.name }
}