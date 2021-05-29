package com.romix.videoplayer.repository

import com.romix.videoplayer.App
import com.romix.videoplayer.retrofit.RetrofitServices
import com.romix.videoplayer.retrofit.Video
import com.romix.videoplayer.retrofit.VimeoService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import retrofit2.Response

class Repository(private val app: App) {
    private val vimeoService: VimeoService by lazy { RetrofitServices.vimeoService }

    fun getVideos() {
        vimeoService.getVideos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<List<Video>>() {
                override fun onSuccess(response: List<Video>) {

                }

                override fun onError(e: Throwable) { }
            })
    }

    fun getVideo(videoId: String) {
        vimeoService.getVideo(videoId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<Video>() {
                override fun onSuccess(response: Video) {

                }

                override fun onError(e: Throwable) { }
            })
    }
}