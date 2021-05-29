package com.romix.videoplayer.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.romix.videoplayer.repository.Repository

class VideoViewModel(private val repository: Repository): ViewModel() {

    fun getVideos() {
        repository.getVideos()
    }

    fun getVideo(videoId: String) {
        repository.getVideo(videoId)
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