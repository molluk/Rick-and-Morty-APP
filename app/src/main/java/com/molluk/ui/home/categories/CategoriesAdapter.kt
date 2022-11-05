package com.molluk.ui.home.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.molluk.databinding.ItemCharacterBinding
import com.molluk.databinding.ItemEpisodeBinding
import com.molluk.databinding.ItemLocationBinding
import com.molluk.ui.base.list.BaseAdapter
import com.molluk.ui.base.list.FillAction
import com.molluk.ui.home.categories.view_holders.CharacterViewHolder
import com.molluk.ui.home.categories.view_holders.EpisodeViewHolder
import com.molluk.ui.home.categories.view_holders.LocationViewHolder

class CategoriesAdapter(
    private val type: String,
    private val clickerViewModel: ClickerViewModel
) :
    BaseAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (type) {
            Categories.Character.name -> {
                val binding = ItemCharacterBinding.inflate(inflater, parent, false)
                CharacterViewHolder(binding, clickerViewModel)
            }
            Categories.Location.name -> {
                val binding = ItemLocationBinding.inflate(inflater, parent, false)
                LocationViewHolder(binding, clickerViewModel)
            }
            else -> {
                val binding = ItemEpisodeBinding.inflate(inflater, parent, false)
                EpisodeViewHolder(binding, clickerViewModel)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FillAction) {
            holder.fill(data[position])
        }
    }
}