package com.romix.videoplayer.models


data class Video(
    val imageUrl: String,
    val video_id: Int,
    val video_link: String,
    val size: Int,
    val name: String,
    val duration: Int,
    val width: Int,
    val height: Int,
    val quality: String
)
