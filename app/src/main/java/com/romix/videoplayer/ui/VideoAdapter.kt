package com.romix.videoplayer.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.AdapterListUpdateCallback
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.romix.videoplayer.R
import com.romix.videoplayer.models.Video
import com.squareup.picasso.Picasso

class VideoAdapter(private val callback: OnVideoClickListener,
                   private val picasso: Picasso) :
    ListAdapter<Video, VideoAdapter.ViewHolder>(VideoComparator()) {
    interface OnVideoClickListener {
        fun onVideoClick(video: Video)
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val videoPreview: ImageView = itemView.findViewById(R.id.video_preview)
        private val name: AppCompatTextView = itemView.findViewById(R.id.video_name)
        private val quality: AppCompatTextView = itemView.findViewById(R.id.highest_quality)
        private val duration: AppCompatTextView = itemView.findViewById(R.id.duration)

        fun bind(listItem: Video) {
            picasso.load(listItem.imageUrl)
                .into(videoPreview)
            name.text = listItem.name
            duration.text = listItem.duration.toString()
            quality.text = listItem.quality

            itemView.setOnClickListener { callback.onVideoClick(listItem) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_video, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItem = getItem(position)
        holder.bind(listItem)
    }

    class VideoComparator: DiffUtil.ItemCallback<Video>() {
        override fun areItemsTheSame(oldItem: Video, newItem: Video): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Video, newItem: Video): Boolean {
            return oldItem == newItem
        }
    }
}