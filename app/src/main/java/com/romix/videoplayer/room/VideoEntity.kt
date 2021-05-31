package com.romix.videoplayer.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "video")
data class VideoEntity(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val videoId: Int,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "image_url")
    val imageUrl: String,

    @ColumnInfo(name = "video_link")
    val videoLink: String,

    @ColumnInfo(name = "quality")
    val quality: String,

    @ColumnInfo(name = "duration")
    val duration: Int,

    @ColumnInfo(name = "size")
    val size: Int,

    @ColumnInfo(name = "width")
    val width: Int,

    @ColumnInfo(name = "height")
    val height: Int
)