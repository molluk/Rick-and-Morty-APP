package com.molluk.ui.home.categories

import androidx.recyclerview.widget.RecyclerView
import com.molluk.BR
import com.molluk.data.character.models.CharacterResponseData
import com.molluk.data.character.models.CharacterResult
import com.molluk.databinding.ItemCharacterBinding
import com.molluk.ui.base.list.BaseListItem
import com.molluk.ui.base.list.FillAction
import com.molluk.ui.home.categories.character.CharacterStatus
import com.resource.R

class CharacterViewHolder(
    private val binding: ItemCharacterBinding,
) :
    RecyclerView.ViewHolder(binding.root), FillAction {
    override fun fill(item: BaseListItem) {
        if (item.data is CharacterResult) {
            val data = item.data
            binding.setVariable(BR.data, data)
            binding.parent.setOnClickListener {

            }
            val context = binding.root.context
            binding.status.setTextColor(
                context.getColorStateList(
                    when (data.status) {
                        CharacterStatus.Alive.name -> {
                            R.color.color_green
                        }
                        CharacterStatus.Dead.name -> {
                            R.color.color_red
                        }
                        else -> {
                            R.color.color_yellow
                        }
                    }
                )
            )
        }
    }
}