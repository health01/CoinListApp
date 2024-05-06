package com.example.coinlistapp.data.dto

import com.google.gson.annotations.SerializedName

data class Coin(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("symbol")
    val symbol: String,
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("is_new")
    val isNew: Boolean,
    @SerializedName("is_active")
    val isActive: Boolean,
    @SerializedName("type")
    val type: String
)