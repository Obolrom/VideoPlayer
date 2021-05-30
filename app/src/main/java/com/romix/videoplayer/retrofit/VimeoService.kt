package com.romix.videoplayer.retrofit

import com.romix.videoplayer.retrofit.dto.VideoDTO
import com.romix.videoplayer.retrofit.dto.VideoPlaylistPage
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface VimeoService {
    @GET("me/videos")
    fun getVideos(): Single<VideoPlaylistPage>

    @GET("videos/{video_id}")
    fun getVideo(@Path("video_id") id: String): Single<VideoDTO>
}