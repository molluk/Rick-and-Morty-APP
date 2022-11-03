package com.molluk.ui.home.categories

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.molluk.databinding.ItemCharacterBinding
import com.molluk.ui.base.list.BaseAdapter
import com.molluk.ui.base.list.FillAction
import com.molluk.ui.home.categories.view_holders.CharacterViewHolder

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
                super.onCreateViewHolder(parent, viewType)
            }
            else -> {
                super.onCreateViewHolder(parent, viewType)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FillAction) {
            holder.fill(data[position])
        }
    }
}