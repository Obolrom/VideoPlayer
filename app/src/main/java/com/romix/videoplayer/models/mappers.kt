package com.romix.videoplayer.models

import com.romix.videoplayer.retrofit.dto.Picture
import com.romix.videoplayer.retrofit.dto.VideoDTO
import com.romix.videoplayer.retrofit.dto.VideoFile
import com.romix.videoplayer.room.VideoEntity

interface Mapper<I, O> {
    fun map(input: I): O
}

interface ListMapper<I, O>: Mapper<List<I>, List<O>>

class VideoMapperVideoDtoToVideo: Mapper<VideoDTO, Video> {
    override fun map(input: VideoDTO): Video {
        val videoFile = getVideoFile(input)
        return Video(
            imageUrl    = getImageUrl(input.pictures),
            videoId    = getVideoId(input.uri),
            videoLink  = videoFile.urlMP4,
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

class VideoMapperVideoToVideoEntity: Mapper<Video, VideoEntity> {
    override fun map(input: Video): VideoEntity {
        return VideoEntity(
            videoId  = input.videoId,
            name      = input.name,
            imageUrl  = input.imageUrl,
            videoLink = input.videoLink,
            quality   = input.quality,
            duration  = input.duration,
            size      = input.size,
            width     = input.width,
            height    = input.height
        )
    }
}

class VideoMapperVideoEntityToVideo: Mapper<VideoEntity, Video> {
    override fun map(input: VideoEntity): Video {
        return Video(
            imageUrl  = input.imageUrl,
            videoLink = input.videoLink,
            videoId   = input.videoId,
            size      = input.size,
            name      = input.name,
            duration  = input.duration,
            width     = input.width,
            height    = input.height,
            quality   = input.quality
        )
    }
}

class VideoListMapper<I, O>(
    private val mapper: Mapper<I, O>
) : ListMapper<I, O> {
    override fun map(input: List<I>): List<O> {
        return input.map { mapper.map(it) }
    }
}

