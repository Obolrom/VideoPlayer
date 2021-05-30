package com.romix.videoplayer.retrofit.dto

import com.google.gson.annotations.SerializedName

data class PictureSize(
    @SerializedName("width")
    val width: Int,

    @SerializedName("height")
    val height: Int,

    @SerializedName("link")
    val imageUrl: String,

    @SerializedName("link_with_play_button")
    val imageUrlWithButton: String
)
