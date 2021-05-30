package com.romix.videoplayer.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.romix.videoplayer.App
import com.romix.videoplayer.models.Video
import com.romix.videoplayer.models.VideoListMapper
import com.romix.videoplayer.models.VideoMapperVideoDtoToVideo
import com.romix.videoplayer.models.VideoMapperVideoToVideoEntity
import com.romix.videoplayer.retrofit.RetrofitServices
import com.romix.videoplayer.retrofit.VimeoService
import com.romix.videoplayer.retrofit.dto.VideoDTO
import com.romix.videoplayer.retrofit.dto.VideoPlaylistPage
import com.romix.videoplayer.room.VideoDatabase
import com.romix.videoplayer.room.VideoEntity
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class Repository(private val app: App) {
    private val appScope = app.applicationScope
    private val vimeoService: VimeoService by lazy { RetrofitServices.vimeoService }
    private val database: VideoDatabase by lazy { VideoDatabase.getDatabase(app.baseContext, appScope) }

    // add mapping
//    val allVideos: Flow<List<Video>> = database.videoDao().getAllVideos()

    fun getVideos(): LiveData<List<Video>> {
        val videos = MutableLiveData<List<Video>>()
        vimeoService.getVideos()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<VideoPlaylistPage>() {
                override fun onSuccess(response: VideoPlaylistPage) {
                    val models = VideoListMapper(VideoMapperVideoDtoToVideo()).map(response.videos)
                    videos.value = models
                    val videoEntities = VideoListMapper(VideoMapperVideoToVideoEntity())
                        .map(models)
                    appScope.launch { insertVideos(videoEntities) }
                }

                override fun onError(e: Throwable) { }
            })
        return videos
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
    suspend fun insertVideos(videos: List<VideoEntity>) =
        database.videoDao().insert(videos)
}