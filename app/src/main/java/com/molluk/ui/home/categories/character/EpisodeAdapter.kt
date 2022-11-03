package com.molluk.ui.home.categories.character

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.molluk.BR
import com.molluk.databinding.ItemEpisodeBinding
import com.molluk.ui.base.list.BaseAdapter
import com.molluk.ui.base.list.BaseListItem
import com.molluk.ui.base.list.FillAction
import com.molluk.ui.home.categories.ClickerViewModel

class EpisodeAdapter(
    private val clickerViewModel: ClickerViewModel
) : BaseAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding = ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EpisodeViewHolder(binding, clickerViewModel)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FillAction) {
            holder.fill(data[position])
        }
    }
}

private class EpisodeViewHolder(
    private val binding: ItemEpisodeBinding,
    private val clickerViewModel: ClickerViewModel
) : RecyclerView.ViewHolder(binding.root), FillAction {

    override fun fill(item: BaseListItem) {
        if (item.data is String) {
            binding.setVariable(BR.episodeLink, item.data)
            binding.episodeText.setOnClickListener {
                clickerViewModel.clickElement(item.data)
            }
        }
    }
}