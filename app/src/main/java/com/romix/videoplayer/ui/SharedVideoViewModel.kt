package com.romix.videoplayer.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.romix.videoplayer.models.Video

class SharedVideoViewModel: ViewModel() {
    private val _video = MutableLiveData<Video>()
    private val _videoList = MutableLiveData<List<Video>>()

    val currentVideo: LiveData<Video> = _video

    val playlist: LiveData<List<Video>> = _videoList

    fun changeCurrentVideo(video: Video) {
        _video.value = video
    }

    fun updatePlaylist(list: List<Video>) {
        _videoList.value = list
    }
}