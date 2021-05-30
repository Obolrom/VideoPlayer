package com.romix.videoplayer.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.util.Util
import com.romix.videoplayer.databinding.FragmentVideoPlayerBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class VideoPlayerFragment : Fragment() {

    private lateinit var sharedVideoViewModel: SharedVideoViewModel
    private var _binding: FragmentVideoPlayerBinding? = null
    private lateinit var playerView: PlayerView
    private var player: ExoPlayer? = null

    // FIXME: 30.05.21 move it to the class, maybe cache it
    private var playWhenReady = true
    private var currentWindow = 0
    private var playbackPosition: Long = 0

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPlayer()

        sharedVideoViewModel.currentVideo.observe(viewLifecycleOwner, {
            player?.setMediaItem(MediaItem.Builder()
                .setUri(it.videoLink)
                .build())
            startPlay()
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
    }

    private fun startPlay() {
        with(player!!) {
            playWhenReady = playWhenReady
            seekTo(currentWindow, playbackPosition)
            prepare()
        }
    }

    private fun releasePlayer() {
        player?.let {
            playbackPosition = it.currentPosition
            currentWindow = it.currentWindowIndex
            playWhenReady = it.playWhenReady
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
}