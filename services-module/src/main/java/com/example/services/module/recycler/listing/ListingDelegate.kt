package com.example.services.module.recycler.listing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.recycler_utils.AdapterDelegate
import com.example.recycler_utils.DelegateItem
import com.example.services.module.R
import com.example.services.module.databinding.ListingItemBinding

/**
 * @author y.gladkikh
 */
class ListingDelegate : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(ListingItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DelegateItem, position: Int) {
        (holder as ViewHolder).bind(item.content() as ListingModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is ListingDelegateItem

    class ViewHolder(private val binding: ListingItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: ListingModel) {
            with(binding) {
                title.text = model.title
                price.text = model.price.toString()
                location.text = model.city

                Glide.with(itemView.context)
                    .load(model.iconLink)
                    .error(R.drawable.sell_cell_system_v1) //TODO error image
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(binding.icon)

                usingAutomatSystem.visibility = if (model.usingAutomatSystem) View.VISIBLE else View.GONE
            }
        }
    }
}
