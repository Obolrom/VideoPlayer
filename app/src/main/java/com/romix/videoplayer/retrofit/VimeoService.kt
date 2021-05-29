package com.romix.videoplayer.retrofit

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface VimeoService {
    @GET("me/videos")
    fun getVideos(): Single<List<Video>>

    @GET("videos/{video_id}")
    fun getVideo(@Path("video_id") id: String): Single<Video>
}