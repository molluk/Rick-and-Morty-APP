package com.molluk.ui.base.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

open class BaseAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var data: MutableList<BaseListItem> = mutableListOf()

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FillAction) {
            holder.fill(data[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemView = inflater.inflate(android.R.layout.test_list_item, parent, false)
        return BaseVH(itemView)
    }

    override fun getItemViewType(position: Int): Int {
        return data[position].viewType
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun stockData(list: List<BaseListItem>) {
        data.clear()
        data.addAll(list)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearData() {
        data.clear()
        notifyDataSetChanged()
    }

    fun addData(list: MutableList<BaseListItem>) {
        val oldSize = itemCount
        data.addAll(list)
        notifyItemRangeInserted(oldSize, itemCount)
    }

    fun addItem(item: BaseListItem) {
        data.add(item)
        notifyItemInserted(data.lastIndex)
    }

    fun addItemTo(item: BaseListItem, index: Int) {
        if (data.size == index || data.size > index && data[index] != item) {
            data.add(index, item)
            notifyItemInserted(index)
        }
    }

    fun addItemInsteadOf(item: BaseListItem, index: Int) {
        data[index] = item
        notifyItemChanged(index)
    }

    fun removeItem(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }
}