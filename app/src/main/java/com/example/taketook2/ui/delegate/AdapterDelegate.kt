package com.example.taketook2.ui.delegate

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.emoji.fragments.delegateItem.DelegateItem

/**
 * @author y.gladkikh
 */
interface AdapterDelegate {
    fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder
    fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: DelegateItem, position: Int)
    fun isOfViewType(item: DelegateItem): Boolean
 }