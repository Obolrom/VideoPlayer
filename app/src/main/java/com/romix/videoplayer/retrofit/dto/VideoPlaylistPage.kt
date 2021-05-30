package com.romix.videoplayer.retrofit.dto

import com.google.gson.annotations.SerializedName

data class VideoPlaylistPage(
    @SerializedName("data")
    val videos: List<Video>
)
