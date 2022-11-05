package com.molluk.ui.home.categories.episode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.molluk.BR
import com.molluk.data.character.models.CharacterResult
import com.molluk.data.status.Resource
import com.molluk.databinding.FragmentEpisodeBinding
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
class EpisodeFragment() : BaseFragment() {

    private lateinit var binding: FragmentEpisodeBinding
    private val args: EpisodeFragmentArgs by navArgs()
    private val episodeViewModel: EpisodeViewModel by viewModels()
    private val characterViewModel: CharacterViewModel by viewModels()
    private val clickerViewModel: ClickerViewModel by viewModels()
    private lateinit var episodeAdapter: ItemInListAdapter
    private var characterList = mutableListOf<CharacterResult>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodeBinding.inflate(inflater, container, false)
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
            episodeAdapter = ItemInListAdapter(clickerViewModel)
            recyclerLinks.adapter = episodeAdapter
            recyclerLinks.setNoBottomDivider(com.resource.R.drawable.shape_separator)
            if (args.data != null) {
                args.data?.let { data ->
                    setVariable(BR.data, args.data)
                    if (!data.characters.isNullOrEmpty()) {
                        val strBuilder = StringBuilder()
                        for (el in data.characters.withIndex()) {
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
            } else {
                args.episodeLink?.let { link ->
                    episodeViewModel.episode(link)
                }
            }
            charactersTitle.setOnClickListener {
                when (binding.recyclerLinks.visibility) {
                    View.VISIBLE -> {
                        binding.recyclerLinks.fadeVisibility(View.GONE)
                        binding.showMoreCharacters.customRotate(180f, 360f, 250, true)
                    }
                    View.GONE -> {
                        if (binding.data?.characters != null) {
                            binding.recyclerLinks.fadeVisibility(View.VISIBLE)
                            binding.showMoreCharacters.customRotate(0f, 180f, 250, true)
                        }
                    }
                }
            }
        }
    }

    private fun setApiObservers() {
        episodeViewModel.episodeResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandledOrReturnNull()?.let {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        if (it.response != null) {
                            val data = it.response
                            binding.setVariable(BR.data, data)
                            if (!data.first().characters.isNullOrEmpty()) {
                                val strBuilder = StringBuilder()
                                for (el in data.first().characters!!.withIndex()) {
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
                                episodeAdapter.addItem(BaseListItem(el))
                            }
                            episodeAdapter.notifyDataSetChanged()
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
                            val action =
                                EpisodeFragmentDirections.globalGoToCharacterFragment(data = character)
                            saveNavigate(action)
                        }
                    }
                } else if (it is CharacterResult) {
                    val action = EpisodeFragmentDirections.globalGoToCharacterFragment(data = it)
                    saveNavigate(action)
                }
            }
        }
    }
}