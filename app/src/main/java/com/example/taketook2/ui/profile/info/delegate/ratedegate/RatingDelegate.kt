package com.example.taketook2.ui.profile.info.delegate.ratedegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.emoji.fragments.delegateItem.DelegateItem
import com.example.taketook2.databinding.StarsItemBinding
import com.example.taketook2.ui.delegate.AdapterDelegate

class RatingDelegate : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(StarsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DelegateItem, position: Int) {
        (holder as ViewHolder).bind(item.content() as RatingModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is RatingDelegateItem

    class ViewHolder(private val binding: StarsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: RatingModel) {
            // binding.ratingBar.rating = model.starsCount
        }
    }
}
