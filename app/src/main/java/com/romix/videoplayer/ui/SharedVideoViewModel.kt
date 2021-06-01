package com.romix.videoplayer.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.romix.videoplayer.models.Video

class SharedVideoViewModel: ViewModel() {
    private val _video = MutableLiveData<Video>()
    private val _videoList = MutableLiveData<List<Video>>()
    private val _videoIndex = MutableLiveData<Int>()

    val currentVideo: LiveData<Video> = _video

    val playlist: LiveData<List<Video>> = _videoList

    val videoIndex: LiveData<Int> = _videoIndex

    fun changeCurrentVideo(video: Video) {
        _video.value = video
    }

    fun updatePlaylist(list: List<Video>) {
        _videoList.value = list
    }

    fun updateVideoIndex(index: Int) {
        _videoIndex.value = index
    }
}