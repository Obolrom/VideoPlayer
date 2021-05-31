package com.romix.videoplayer.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.romix.videoplayer.models.Video
import com.romix.videoplayer.repository.Repository

class VideoViewModel(private val repository: Repository): ViewModel() {

    val videos: LiveData<List<Video>> = repository.videoList

//    fixme this is not correct, because we should return domain model from repository
//    val getAll = repository.getAll()

    fun getVideo(videoId: String): LiveData<Video> {
        return repository.getVideo(videoId)
    }
}

class VideoViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VideoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VideoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}