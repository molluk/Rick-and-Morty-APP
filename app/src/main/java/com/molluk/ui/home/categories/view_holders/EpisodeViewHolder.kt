package com.molluk.ui.home.categories.view_holders

import androidx.recyclerview.widget.RecyclerView
import com.molluk.BR
import com.molluk.data.episode.models.EpisodeResult
import com.molluk.databinding.ItemEpisodeBinding
import com.molluk.ui.base.list.BaseListItem
import com.molluk.ui.base.list.FillAction
import com.molluk.ui.home.categories.ClickerViewModel

class EpisodeViewHolder (
    private val binding: ItemEpisodeBinding,
    private val clickerViewModel: ClickerViewModel
) : RecyclerView.ViewHolder(binding.root), FillAction {
    override fun fill(item: BaseListItem) {
        if (item.data is EpisodeResult) {
            val data = item.data
            binding.setVariable(BR.data, data)
            binding.parent.setOnClickListener {
                clickerViewModel.clickElement(data)
            }
        }
    }
}