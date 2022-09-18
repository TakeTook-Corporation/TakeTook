package com.example.taketook2.ui.profile.info.delegate.characteristic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.emoji.fragments.delegateItem.DelegateItem
import com.example.taketook2.databinding.CommonCharacteristicsItemBinding
import com.example.taketook2.ui.delegate.AdapterDelegate

/*
 * @author y.gladkikh
 */
class UserCharacteristicsDelegate : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(CommonCharacteristicsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DelegateItem, position: Int) {
        (holder as ViewHolder).bind(item.content() as UserCharacteristicsModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is UserCharacteristicsDelegateItem

    class ViewHolder(private val binding: CommonCharacteristicsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: UserCharacteristicsModel) {
            binding.titleText.text = model.title
            binding.bodyText.text = model.description
        }
    }
}
