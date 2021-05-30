package com.romix.videoplayer.models

import com.romix.videoplayer.retrofit.dto.Picture
import com.romix.videoplayer.retrofit.dto.VideoDTO
import com.romix.videoplayer.retrofit.dto.VideoFile

interface Mapper<I, O> {
    fun map(input: I): O
}

interface ListMapper<I, O>: Mapper<List<I>, List<O>>

class VideoMapper: Mapper<VideoDTO, Video> {
    override fun map(input: VideoDTO): Video {
        val videoFile = getVideoFile(input)
        return Video(
            imageUrl    = getImageUrl(input.pictures),
            video_id    = getVideoId(input.uri),
            video_link  = videoFile.urlMP4,
            size        = videoFile.size,
            name        = input.name,
            duration    = input.duration,
            width       = input.width,
            height      = input.height,
            quality     = videoFile.quality
        )
    }

    private fun getImageUrl(pictureStorage: Picture): String {
        return pictureStorage.pictures[pictureStorage.pictures.size - 1].imageUrl
    }

    private fun getVideoId(uri: String): Int {
        return Integer.parseInt(uri.substring(8))
    }

    private fun getVideoFile(videoDTO: VideoDTO): VideoFile {
        return videoDTO.videoFiles[0]
    }
}

class VideoListMapper<I, O>(
    private val mapper: Mapper<I, O>
) : ListMapper<I, O> {
    override fun map(input: List<I>): List<O> {
        return input.map { mapper.map(it) }
    }
}

