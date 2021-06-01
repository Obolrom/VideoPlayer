package com.romix.videoplayer.ui

import java.io.Serializable

data class VideoState(
    var playWhenReady: Boolean = true,
    var currentWindow: Int = 0,
    var playbackPosition: Long = 0
) : Serializable
