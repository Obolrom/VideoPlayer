package com.romix.videoplayer

import android.app.Application
import com.romix.videoplayer.repository.Repository

class App: Application() {
    val repository by lazy { Repository(this) }

    override fun onCreate() {
        super.onCreate()
    }
}