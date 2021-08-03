package com.marslan.memogame.data

data class Card(
    val id: Int,
    val imageID: Int,
    var isActive: Boolean = false,
    var isFind: Boolean = false
)
