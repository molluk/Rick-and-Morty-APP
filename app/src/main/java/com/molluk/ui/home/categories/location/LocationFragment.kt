package com.molluk.ui.home.categories.location

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.molluk.BR
import com.molluk.data.character.models.CharacterResult
import com.molluk.data.status.Resource
import com.molluk.databinding.FragmentLocationBinding
import com.molluk.ui.base.BaseFragment
import com.molluk.ui.base.list.BaseListItem
import com.molluk.ui.base.list.setNoBottomDivider
import com.molluk.ui.home.categories.ClickerViewModel
import com.molluk.ui.home.categories.ItemInListAdapter
import com.molluk.ui.home.categories.character.CharacterViewModel
import com.resource.customRotate
import com.resource.fadeVisibility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LocationFragment() : BaseFragment() {

    private lateinit var binding: FragmentLocationBinding
    private val args: LocationFragmentArgs by navArgs()
    private val locationViewModel: LocationViewModel by viewModels()
    private val characterViewModel: CharacterViewModel by viewModels()
    private val clickerViewModel: ClickerViewModel by viewModels()
    private lateinit var residentAdapter: ItemInListAdapter
    private var characterList = mutableListOf<CharacterResult>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationBinding.inflate(inflater, container, false)
        setToolbar(binding.toolbar)
        setShowDividerScrollListener(
            scroll = binding.nestedScroll,
            appbar = binding.appbar,
            separatorTop = binding.separatorTop,
            isTrim = true
        )

        initViews()
        setApiObservers()

        return binding.root
    }

    private fun initViews() {
        with(binding) {
            residentAdapter = ItemInListAdapter(clickerViewModel)
            recyclerLinks.adapter = residentAdapter
            recyclerLinks.setNoBottomDivider(com.resource.R.drawable.shape_separator)
            if (args.data != null) {
                args.data?.let { data ->
                    setVariable(BR.data, args.data)
                    val strBuilder = StringBuilder()
                    for (el in data.residents.withIndex()) {
                        strBuilder.append(
                            el.value.substring(
                                el.value.lastIndexOf("/") + 1,
                                el.value.length
                            ) + ","
                        )
                    }
                    characterViewModel.getCharactersList(strBuilder.toString())
                }
            } else {
                args.locationLink?.let { link ->
                    locationViewModel.location(link)
                }
            }
            charactersTitle.setOnClickListener {
                when (binding.recyclerLinks.visibility) {
                    View.VISIBLE -> {
                        binding.recyclerLinks.fadeVisibility(View.GONE)
                        binding.showMoreCharacters.customRotate(180f, 360f, 250, true)
                    }
                    View.GONE -> {
                        if (binding.data?.residents != null) {
                            binding.recyclerLinks.fadeVisibility(View.VISIBLE)
                            binding.showMoreCharacters.customRotate(0f, 180f, 250, true)
                        }
                    }
                }
            }
        }
    }

    private fun setApiObservers() {
        locationViewModel.locationResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandledOrReturnNull()?.let {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        if (it.response != null) {
                            val data = it.response
                            binding.setVariable(BR.data, data)
                            val strBuilder = StringBuilder()
                            for (el in data.residents.withIndex()) {
                                strBuilder.append(
                                    el.value.substring(
                                        el.value.lastIndexOf("/") + 1,
                                        el.value.length
                                    ) + ","
                                )
                            }
                            characterViewModel.getCharactersList(strBuilder.toString())
                        }
                    }
                    Resource.Status.LOADING -> {
                        showErrorSnackBar(Resource.Status.LOADING.name)
                    }
                    Resource.Status.ERROR -> {
                        showErrorSnackBar(Resource.Status.ERROR.name)
                    }
                }
            }
        }
        characterViewModel.charactersList.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandledOrReturnNull()?.let {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        if (it.response != null) {
                            val data = it.response
                            characterList.addAll(data)
                            for (el in data) {
                                residentAdapter.addItem(BaseListItem(el))
                            }
                            residentAdapter.notifyDataSetChanged()
                            binding.shimmer.stopShimmer()
                            binding.shimmer.hideShimmer()
                            binding.charactersTitle.isClickable = true
                        }
                    }
                    Resource.Status.LOADING -> {
                        binding.charactersTitle.isClickable = false
                    }
                    Resource.Status.ERROR -> {
                        binding.shimmer.stopShimmer()
                        binding.shimmer.hideShimmer()
                        binding.residentsCard.visibility = View.GONE
                    }
                }
            }
        }
        clickerViewModel.clickElement.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandledOrReturnNull()?.let {
                if (it is String) {
                    characterList.forEach { character ->
                        if (character.name?.lowercase() == it.lowercase()) {
                            val action = LocationFragmentDirections.globalGoToCharacterFragment(data = character)
                            saveNavigate(action)
                        }
                    }
                } else if (it is CharacterResult) {
                    val action = LocationFragmentDirections.globalGoToCharacterFragment(data = it)
                    saveNavigate(action)
                }
            }
        }
    }
}