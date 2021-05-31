package com.romix.videoplayer.repository

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import com.romix.videoplayer.App
import com.romix.videoplayer.models.*
import com.romix.videoplayer.retrofit.RetrofitServices
import com.romix.videoplayer.retrofit.VimeoService
import com.romix.videoplayer.retrofit.dto.VideoDTO
import com.romix.videoplayer.retrofit.dto.VideoPlaylistPage
import com.romix.videoplayer.room.VideoDatabase
import com.romix.videoplayer.room.VideoEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class Repository(private val app: App) {
    private val appScope = app.applicationScope
    private val vimeoService: VimeoService by lazy { RetrofitServices.vimeoService }
    private val database: VideoDatabase by lazy { VideoDatabase.getDatabase(app.baseContext, appScope) }

    val videoList: Flow<List<Video>> = loadVideos()

    private fun loadVideos(): Flow<List<Video>> {
        loadVideosFromService()
        return getVideosFromDb()
    }

    private fun loadVideosFromService() {
        vimeoService.getVideos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<VideoPlaylistPage>() {
                override fun onSuccess(response: VideoPlaylistPage) {
                    val models = VideoListMapper(VideoMapperVideoDtoToVideo()).map(response.videos)
                    appScope.launch { insertVideos(models) }
                }

                override fun onError(e: Throwable) { }
            })
    }

    fun getVideo(videoId: String): LiveData<Video> {
        val video = MutableLiveData<Video>()
        vimeoService.getVideo(videoId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<VideoDTO>() {
                override fun onSuccess(response: VideoDTO) {
                    video.value = VideoMapperVideoDtoToVideo().map(response)
                }

                override fun onError(e: Throwable) { }
            })
        return video
    }

    @WorkerThread
    suspend fun insertVideos(videos: List<Video>) {
        val entities = VideoListMapper(VideoMapperVideoToVideoEntity())
            .map(videos)
        database.videoDao().insert(entities)
    }

    private fun getVideosFromDb(): Flow<List<Video>> {
        val stateFlow = MutableStateFlow(listOf<Video>())
        appScope.launch(Dispatchers.Unconfined) {
            try {
                database.videoDao().getAllVideos().collect {
                    stateFlow.value = VideoListMapper(VideoMapperVideoEntityToVideo()).map(it)
                }
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
        return stateFlow
    }
}