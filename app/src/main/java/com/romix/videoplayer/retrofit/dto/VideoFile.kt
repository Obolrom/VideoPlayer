package com.romix.videoplayer.retrofit.dto

import com.google.gson.annotations.SerializedName

data class VideoFile(
    @SerializedName("quality")
    val quality: String,

    @SerializedName("width")
    val width: Int,

    @SerializedName("height")
    val height: Int,

    @SerializedName("size")
    val size: Int,

    @SerializedName("size_short")
    val size_name: String,

    @SerializedName("public_name")
    val name: String,

    @SerializedName("link")
    val urlMP4: String
)
