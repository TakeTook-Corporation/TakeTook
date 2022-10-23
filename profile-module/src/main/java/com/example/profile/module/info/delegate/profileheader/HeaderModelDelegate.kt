package com.example.profile.module.info.delegate.profileheader

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.profile.module.R
import com.example.profile.module.databinding.ProfileHeaderItemBinding
import com.example.recycler_utils.AdapterDelegate
import com.example.recycler_utils.DelegateItem

/**
 * @author y.gladkikh
 */
class HeaderModelDelegate : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(ProfileHeaderItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DelegateItem, position: Int) {
        (holder as ViewHolder).bind(item.content() as HeaderModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is HeaderModelDelegateItem

    class ViewHolder(private val binding: ProfileHeaderItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: HeaderModel) {

            Glide.with(itemView.context)
                .load(model.iconUrl)
                .error(R.drawable.spcx) //TODO normal errors
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(binding.roundedImageView)

            binding.nameField.text = model.userName
        }
    }
}
