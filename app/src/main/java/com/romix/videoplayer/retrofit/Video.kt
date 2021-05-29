package com.romix.videoplayer.retrofit

import com.google.gson.annotations.SerializedName

data class Video(
    @SerializedName("link")
    val urlMP4: String
)