package com.romix.videoplayer.retrofit

import retrofit2.Retrofit

object RetrofitServices {
    private const val BASE_VIDEO_SERVICE_URL = ""

    val videoService: VideoService
        get() = RetrofitClient
            .getVideoClient(BASE_VIDEO_SERVICE_URL)
            .create(VideoService::class.java)
}