package com.romix.videoplayer.ui

import android.annotation.SuppressLint
import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.util.MimeTypes
import com.google.android.exoplayer2.util.Util
import com.romix.videoplayer.databinding.FragmentVideoPlayerBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class VideoPlayerFragment : Fragment() {

    private lateinit var sharedVideoViewModel: SharedVideoViewModel
    private var _binding: FragmentVideoPlayerBinding? = null
    private val playbackStateListener: PlaybackStateListener = PlaybackStateListener()
    private lateinit var playerView: PlayerView
    private var player: ExoPlayer? = null

    private var videoState: VideoState = VideoState()

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedVideoViewModel = ViewModelProvider(requireActivity())
            .get(SharedVideoViewModel::class.java)
        _binding = FragmentVideoPlayerBinding.inflate(inflater, container, false)
        playerView = _binding?.videoPlayer!!

        return binding.root
    }

    private fun initPlaylist() {
        sharedVideoViewModel.playlist.observe(viewLifecycleOwner, {
            val mediaItems = mutableListOf<MediaItem>()
            it.forEach { video ->
                mediaItems.add(MediaItem.Builder()
                    .setUri(video.videoLink)
                    .setMediaId(video.videoId.toString())
                    .setMimeType(MimeTypes.APPLICATION_MP4)
                    .build())
            }
            player?.addMediaItems(mediaItems)
        })
    }

    private fun initPlayer() {
        if (player == null) {
            val trackSelector = DefaultTrackSelector(requireContext())
            trackSelector.setParameters(
                trackSelector
                    .buildUponParameters()
                    .setMaxVideoSizeSd())
            player = SimpleExoPlayer.Builder(requireContext())
                .setTrackSelector(trackSelector)
                .build()
        }

        playerView.player = player
        player?.addListener(playbackStateListener)
        initPlaylist()
        startPlay()
    }

    private fun startPlay() {
        with(player!!) {
            playWhenReady = playWhenReady
            seekTo(videoState.currentWindow, videoState.playbackPosition)
            prepare()
            play()
        }
    }

    private fun releasePlayer() {
        player?.let {
            videoState.playbackPosition = it.currentPosition
            videoState.currentWindow = it.currentWindowIndex
            videoState.playWhenReady = it.playWhenReady
            it.removeListener(playbackStateListener)
            it.release()
            player = null
        }
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT > 23) {
            initPlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT <= 23 || player == null) {
            initPlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT <= 23) {
            releasePlayer()
        }
    }

    override fun onStop() {
        super.onStop()
        if (Util.SDK_INT > 23) {
            releasePlayer()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private inner class PlaybackStateListener(): Player.Listener {

        override fun onPlaybackStateChanged(state: Int) {
            super.onPlaybackStateChanged(state)
            val stateString = when (state) {
                ExoPlayer.STATE_IDLE -> "ExoPlayer.STATE_IDLE      -"
                ExoPlayer.STATE_BUFFERING -> "ExoPlayer.STATE_BUFFERING -"
                ExoPlayer.STATE_READY -> "ExoPlayer.STATE_READY     -"
                ExoPlayer.STATE_ENDED -> "ExoPlayer.STATE_ENDED     -"
                else -> "UNKNOWN_STATE             -"
            }
            Log.d("playerState", "changed state to $stateString")
        }
    }
}