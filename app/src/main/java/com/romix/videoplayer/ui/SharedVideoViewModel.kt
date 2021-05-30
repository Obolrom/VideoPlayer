package com.romix.videoplayer.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.romix.videoplayer.models.Video

class SharedVideoViewModel: ViewModel() {
    private val _video = MutableLiveData<Video>()

    val currentVideo: LiveData<Video> = _video

    fun changeCurrentVideo(video: Video) {
        _video.value = video
    }
}