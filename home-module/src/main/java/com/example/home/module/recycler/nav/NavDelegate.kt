package com.example.home.module.recycler.nav

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.home.module.R
import com.example.home.module.databinding.NavCardItemBinding
import com.example.recycler_utils.AdapterDelegate
import com.example.recycler_utils.DelegateItem

/**
 * @author y.gladkikh
 */
class NavDelegate : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(NavCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DelegateItem, position: Int) {
        (holder as ViewHolder).bind(item.content() as NavModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is NavDelegateItem

    class ViewHolder(private val binding: NavCardItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: NavModel) {
            binding.image.setOnClickListener {
                
            }
        }
    }
}
