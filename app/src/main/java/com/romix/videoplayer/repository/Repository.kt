package com.romix.videoplayer.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.romix.videoplayer.App
import com.romix.videoplayer.models.Video
import com.romix.videoplayer.models.VideoListMapper
import com.romix.videoplayer.models.VideoMapper
import com.romix.videoplayer.retrofit.RetrofitServices
import com.romix.videoplayer.retrofit.VimeoService
import com.romix.videoplayer.retrofit.dto.VideoDTO
import com.romix.videoplayer.retrofit.dto.VideoPlaylistPage
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class Repository(private val app: App) {
    private val vimeoService: VimeoService by lazy { RetrofitServices.vimeoService }

    fun getVideos(): LiveData<List<Video>> {
        val videos = MutableLiveData<List<Video>>()
        vimeoService.getVideos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<VideoPlaylistPage>() {
                override fun onSuccess(response: VideoPlaylistPage) {
                    videos.value = VideoListMapper(VideoMapper()).map(response.videos)
                }

                override fun onError(e: Throwable) { }
            })
        return videos
    }

    fun getVideo(videoId: String) {
        val video = MutableLiveData<Video>()
        vimeoService.getVideo(videoId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<VideoDTO>() {
                override fun onSuccess(response: VideoDTO) {
                    video.value = VideoMapper().map(response)
                }

                override fun onError(e: Throwable) { }
            })
    }
}