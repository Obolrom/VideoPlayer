package com.romix.videoplayer.retrofit.dto

import com.google.gson.annotations.SerializedName

data class VideoDTO(
    @SerializedName("uri")
    val uri: String,

    @SerializedName("name")
    val name: String,

    @SerializedName("duration")
    val duration: Int,

    @SerializedName("width")
    val width: Int,

    @SerializedName("height")
    val height: Int,

    @SerializedName("pictures")
    val pictures: Picture,

    @SerializedName("files")
    val videoFiles: List<VideoFile>
)