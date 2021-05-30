package com.romix.videoplayer.models


data class Video(
    val imageUrl: String,
    val videoId: Int,
    val videoLink: String,
    val size: Int,
    val name: String,
    val duration: Int,
    val width: Int,
    val height: Int,
    val quality: String
)
