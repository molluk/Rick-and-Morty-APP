package com.molluk.ui.home.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.molluk.BR
import com.molluk.data.character.models.CharacterResult
import com.molluk.databinding.ItemInListBinding
import com.molluk.ui.base.list.BaseAdapter
import com.molluk.ui.base.list.BaseListItem
import com.molluk.ui.base.list.FillAction

class ItemInListAdapter(
    private val clickerViewModel: ClickerViewModel
) : BaseAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemInListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemInListViewHolder(binding, clickerViewModel)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FillAction) {
            holder.fill(data[position])
        }
    }
}

private class ItemInListViewHolder(
    private val binding: ItemInListBinding,
    private val clickerViewModel: ClickerViewModel
) : RecyclerView.ViewHolder(binding.root), FillAction {

    override fun fill(item: BaseListItem) {
        if (item.data is String) {
            binding.setVariable(BR.itemLink, item.data)
            binding.parent.setOnClickListener {
                clickerViewModel.clickElement(item.data)
            }
        } else if (item.data is CharacterResult) {
            binding.setVariable(BR.image, item.data.image)
            binding.setVariable(BR.itemLink, item.data.name)
            binding.parent.setOnClickListener {
                clickerViewModel.clickElement(item.data)
            }
        }
    }
}