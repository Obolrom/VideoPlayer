package com.romix.videoplayer.retrofit

object RetrofitServices {
    private const val BASE_VIDEO_SERVICE_URL = "https://api.vimeo.com/"

    val vimeoService: VimeoService
        get() = RetrofitClient
            .getVideoClient(BASE_VIDEO_SERVICE_URL)
            .create(VimeoService::class.java)
}