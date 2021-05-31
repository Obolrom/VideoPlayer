package com.romix.videoplayer

import android.app.Application
import com.romix.videoplayer.repository.Repository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class App: Application() {
    val applicationScope: CoroutineScope = CoroutineScope(SupervisorJob())
    val repository by lazy { Repository(this) }

    override fun onCreate() {
        super.onCreate()
    }
}