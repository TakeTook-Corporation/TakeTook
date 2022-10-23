package com.example.profile.module.info.delegate.ratedegate

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.profile.module.databinding.StarsItemBinding
import com.example.recycler_utils.AdapterDelegate
import com.example.recycler_utils.DelegateItem

/**
 * @author y.gladkikh
 */
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
