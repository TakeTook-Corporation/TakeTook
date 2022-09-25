package com.example.taketook2.ui.home.recycler.stories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.recycler_utils.DelegateItem
import com.example.taketook2.R
import com.example.taketook2.databinding.StroriesCardItemBinding
import com.example.recycler_utils.AdapterDelegate

/**
 * @author y.gladkikh
 */
class StoryDelegate : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(StroriesCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DelegateItem, position: Int) {
        (holder as ViewHolder).bind(item.content() as StoryModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is StoryDelegateItem

    class ViewHolder(private val binding: StroriesCardItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: StoryModel) {
            Glide.with(itemView.context)
                .load(model.iconUrl)
                .error(R.drawable.spcx)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.image)
        }
    }
}
