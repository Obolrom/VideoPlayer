package com.romix.videoplayer.retrofit.dto

import com.google.gson.annotations.SerializedName

data class Picture(
    @SerializedName("uri")
    val uri: String,

    @SerializedName("sizes")
    val pictures: List<PictureSize>
)
