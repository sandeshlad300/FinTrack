package com.sandesh.fintrack.core.model

data class IntroModel (
    val id: Int,
    val title: String,
    val subtitle: String,
    val gradientColors: List<Long>,
    val lottieAsset: String
    )