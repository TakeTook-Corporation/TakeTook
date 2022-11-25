package com.example.home.module.recycler.nav

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.home.module.databinding.NewNavCardItemBinding
import com.example.recycler_utils.AdapterDelegate
import com.example.recycler_utils.DelegateItem

/**
 * @author y.gladkikh
 */
class NavDelegate : AdapterDelegate {

    override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder =
        ViewHolder(NewNavCardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        item: DelegateItem,
        position: Int
    ) {
        (holder as ViewHolder).bind(item.content() as NavModel)
    }

    override fun isOfViewType(item: DelegateItem): Boolean = item is NavDelegateItem

    class ViewHolder(private val binding: NewNavCardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: NavModel) {
            with(binding) {
                icon.setImageResource(model.iconId)
                icon.setOnClickListener {

                }
            }
        }
    }
}
