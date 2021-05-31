package com.romix.videoplayer.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.romix.videoplayer.App
import com.romix.videoplayer.R
import com.romix.videoplayer.models.Video
import com.romix.videoplayer.models.VideoListMapper
import com.romix.videoplayer.models.VideoMapperVideoEntityToVideo
import com.squareup.picasso.Picasso

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class VideoListFragment : Fragment(), VideoAdapter.OnVideoClickListener {

    private lateinit var sharedVideoViewModel: SharedVideoViewModel
    private val videoViewModel: VideoViewModel by viewModels {
        VideoViewModelFactory((activity?.application as App).repository)
    }
    private lateinit var videoRV: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        sharedVideoViewModel = ViewModelProvider(requireActivity())
            .get(SharedVideoViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_video_list, container, false)
        with(root) {
            videoRV = findViewById(R.id.video_list_rv)
        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val videoAdapter = VideoAdapter(this, Picasso.get())
        with(videoRV) {
            setHasFixedSize(true)
            adapter = videoAdapter
            layoutManager = LinearLayoutManager(context)
        }

        videoViewModel.videos.observe(viewLifecycleOwner, { videos ->
            videoAdapter.submitList(videos)
            videoAdapter.notifyDataSetChanged()
        })
    }

    override fun onVideoClick(video: Video) {
        sharedVideoViewModel.changeCurrentVideo(video)
        findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
    }
}