package com.romix.videoplayer.repository

import com.romix.videoplayer.App
import com.romix.videoplayer.retrofit.RetrofitServices
import com.romix.videoplayer.retrofit.VideoService

class Repository(private val app: App) {
    private val videoService: VideoService by lazy { RetrofitServices.videoService }
}