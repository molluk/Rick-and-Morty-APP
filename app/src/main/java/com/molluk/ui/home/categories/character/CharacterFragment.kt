package com.molluk.ui.home.categories.character

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.graphics.ColorUtils
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.molluk.ui.base.BaseFragment
import androidx.navigation.fragment.navArgs
import com.molluk.BR
import com.molluk.data.episode.models.EpisodeResult
import com.molluk.data.location.models.LocationResult
import com.molluk.data.status.Resource
import com.molluk.databinding.FragmentCharacterBinding
import com.molluk.ui.base.list.BaseListItem
import com.molluk.ui.base.list.setNoBottomDivider
import com.molluk.ui.home.categories.ClickerViewModel
import com.molluk.ui.home.categories.ItemInListAdapter
import com.molluk.ui.home.categories.episode.EpisodeViewModel
import com.molluk.ui.home.categories.location.LocationViewModel
import com.molluk.utils.ui.scrollToTop
import com.resource.customRotate
import com.resource.fadeVisibility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CharacterFragment : BaseFragment() {
    private lateinit var binding: FragmentCharacterBinding

    private val args: CharacterFragmentArgs by navArgs()
    private val characterViewModel: CharacterViewModel by viewModels()
    private val locationViewModel: LocationViewModel by viewModels()
    private val episodeViewModel: EpisodeViewModel by viewModels()
    private val clickerViewModel: ClickerViewModel by viewModels()
    private var episodeList = mutableListOf<EpisodeResult>()

    private lateinit var episodeAdapter: ItemInListAdapter
    private lateinit var origin: LocationResult
    private lateinit var location: LocationResult

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterBinding.inflate(inflater, container, false)
        setShowDividerScrollListener(
            scroll = binding.nestedScroll,
            separatorTop = binding.separatorTop
        )

        initViews()
        setApiObservers()

        return binding.root
    }

    private fun initViews() {
        with(binding) {
            episodeAdapter = ItemInListAdapter(clickerViewModel)
            if (args.data != null) {
                args.data?.let { data ->
                    binding.setVariable(BR.data, data)
                    val strBuilder = StringBuilder()
                    if (data.episode != null) {
                        for (el in data.episode.withIndex()) {
                            strBuilder.append(
                                el.value.substring(
                                    el.value.lastIndexOf("/") + 1,
                                    el.value.length
                                ) + ","
                            )
                        }
                        episodeViewModel.getEpisodeList(strBuilder.toString())
                        data.origin?.url?.let {
                            val url = it.substring(it.lastIndexOf("/") + 1, it.length)
                            locationViewModel.getLocationList(url)
                        }
                        data.location?.url?.let {
                            val url = it.substring(it.lastIndexOf("/") + 1, it.length)
                            locationViewModel.getLocationList(url)
                        }
                    }
                }
            } else {
                args.characterLink?.let { link ->
                    characterViewModel.getCharactersLink(link)
                }
            }
            binding.recyclerLinks.adapter = episodeAdapter
            binding.recyclerLinks.setNoBottomDivider(com.resource.R.drawable.shape_separator)

            motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
                override fun onTransitionStarted(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int
                ) {
                }

                override fun onTransitionChange(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int,
                    progress: Float
                ) {
                    share.setBackgroundColor(Color.TRANSPARENT)
                    val alpha = bgTop.alpha * 255
                    val color = ColorUtils.setAlphaComponent(
                        requireContext().getColor(com.resource.R.color.color_background_tile),
                        alpha.toInt()
                    )
                    requireActivity().window.statusBarColor = color
                }

                override fun onTransitionCompleted(
                    motionLayout: MotionLayout?,
                    currentId: Int
                ) {
                    share.setBackgroundColor(Color.TRANSPARENT)
                    binding.bgTop.visibility = View.INVISIBLE
                    val alpha = bgTop.alpha * 255
                    val color = ColorUtils.setAlphaComponent(
                        requireContext().getColor(com.resource.R.color.color_background_tile),
                        alpha.toInt()
                    )
                    requireActivity().window.statusBarColor = color
                }

                override fun onTransitionTrigger(
                    motionLayout: MotionLayout?,
                    triggerId: Int,
                    positive: Boolean,
                    progress: Float
                ) {
                }
            })
            back.setOnClickListener {
                findNavController().popBackStack()
            }
            share.setOnClickListener {
                binding.data?.let { data ->
                    val intent = Intent()
                    intent.action = Intent.ACTION_SEND
                    intent.putExtra(
                        Intent.EXTRA_TEXT,
                        data.url
                    )
                    intent.type = "text/plain"
                    startActivity(
                        Intent.createChooser(
                            intent,
                            requireContext().getString(com.resource.R.string.button_share) + data.name
                        )
                    )
                }
            }
            toolbar.setOnClickListener {
                nestedScroll.scrollToTop()
                motionLayout.transitionToStart()
            }
            characterOrigin.setOnClickListener {
                if (this@CharacterFragment::origin.isInitialized) {
                    val action = CharacterFragmentDirections.globalGoToLocationFragment(
                        origin,
                        null
                    )
                    saveNavigate(action)
                } else {
                    binding.data?.origin?.url?.let { url ->
                        if (url.isNotEmpty()) {
                            val action = CharacterFragmentDirections.globalGoToLocationFragment(
                                null,
                                url
                            )
                            saveNavigate(action)
                        }
                    }
                }
            }
            characterLocation.setOnClickListener {
                if (this@CharacterFragment::location.isInitialized) {
                    val action = CharacterFragmentDirections.globalGoToLocationFragment(
                        location,
                        null
                    )
                    saveNavigate(action)
                } else {
                    binding.data?.location?.url?.let { url ->
                        if (url.isNotEmpty()) {
                            val action = CharacterFragmentDirections.globalGoToLocationFragment(
                                null,
                                url
                            )
                            saveNavigate(action)
                        }
                    }
                }
            }

            episodeTitle.setOnClickListener {
                when (binding.recyclerLinks.visibility) {
                    View.VISIBLE -> {
                        binding.recyclerLinks.fadeVisibility(View.GONE)
                        binding.showMoreEpisode.customRotate(180f, 360f, 250, true)
                    }
                    View.GONE -> {
                        if (binding.data?.episode != null) {
                            binding.recyclerLinks.fadeVisibility(View.VISIBLE)
                            binding.showMoreEpisode.customRotate(0f, 180f, 250, true)
                        }
                    }
                }
            }
        }
    }

    private fun setApiObservers() {
        characterViewModel.charactersList.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandledOrReturnNull()?.let {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        if (it.response != null) {
                            val data = it.response
                            binding.setVariable(BR.data, data)
                            data.first().origin?.url?.let { str ->
                                val url = str.substring(str.lastIndexOf("/") + 1, str.length)
                                locationViewModel.getLocationList(url)
                            }
                            data.first().url?.let { str ->
                                val url = str.substring(str.lastIndexOf("/") + 1, str.length)
                                locationViewModel.getLocationList(url)
                            }
                        }
                    }
                    Resource.Status.LOADING -> {
                        showErrorSnackBar(Resource.Status.LOADING.name)
                    }
                    Resource.Status.ERROR -> {}
                }
            }
        }
        episodeViewModel.episodeResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandledOrReturnNull()?.let {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        if (it.response != null) {
                            val data = it.response
                            episodeList.addAll(data)
                            for (el in data) {
                                episodeAdapter.addItem(BaseListItem(el.name))
                            }
                            episodeAdapter.notifyDataSetChanged()
                            binding.shimmer.stopShimmer()
                            binding.shimmer.hideShimmer()
                            binding.episodeTitle.isClickable = true
                        }
                    }
                    Resource.Status.LOADING -> {
                        binding.episodeTitle.isClickable = false
                    }
                    Resource.Status.ERROR -> {
                        binding.shimmer.stopShimmer()
                        binding.shimmer.hideShimmer()
                        binding.episodeTitle.visibility = View.GONE
                    }
                }
            }
        }
        locationViewModel.locationResponse.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandledOrReturnNull()?.let {
                when (it.status) {
                    Resource.Status.SUCCESS -> {
                        if (it.response != null) {
                            val data = it.response
                            if (this::origin.isInitialized && this::location.isInitialized){
                                binding.shimmerOrigin.stopShimmer()
                                binding.shimmerOrigin.hideShimmer()
                                binding.characterOrigin.isClickable = true
                                binding.shimmerLocation.stopShimmer()
                                binding.shimmerLocation.hideShimmer()
                                binding.characterLocation.isClickable = true
                            } else if (!this::origin.isInitialized) {
                                origin = data
                                binding.shimmerOrigin.stopShimmer()
                                binding.shimmerOrigin.hideShimmer()
                                binding.characterOrigin.isClickable = true
                                if (!this::location.isInitialized) {
                                    binding.data?.location?.url?.let { str ->
                                        val url =
                                            str.substring(str.lastIndexOf("/") + 1, str.length)
                                        locationViewModel.getLocationList(url)
                                    }
                                }
                            } else if (!this::location.isInitialized) {
                                location = data
                                binding.shimmerLocation.stopShimmer()
                                binding.shimmerLocation.hideShimmer()
                                binding.characterLocation.isClickable = true
                                if (!this::origin.isInitialized) {
                                    binding.data?.origin?.url?.let { str ->
                                        val url =
                                            str.substring(str.lastIndexOf("/") + 1, str.length)
                                        locationViewModel.getLocationList(url)
                                    }
                                }
                            }

                        }
                    }
                    Resource.Status.LOADING -> {
                        if (!this::origin.isInitialized) {
                            binding.characterOrigin.isClickable = false
                        }
                        if (!this::location.isInitialized) {
                            binding.characterLocation.isClickable = false
                        }
                    }
                    Resource.Status.ERROR -> {
                        binding.characterOrigin.isClickable = true
                        binding.characterLocation.isClickable = true
                    }
                }
            }
        }
        clickerViewModel.clickElement.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandledOrReturnNull()?.let {
                if (it is String) {
                    episodeList.forEach { episode ->
                        if (episode.name != null && episode.name.lowercase() == it.lowercase()) {
                            val action =
                                CharacterFragmentDirections.globalGoToEpisodeFragment(
                                    episode,
                                    null
                                )
                            saveNavigate(action)
                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        getMainActivity().changeNavigationBarColor(com.resource.R.color.color_background_tile)
    }

    override fun onResume() {
        super.onResume()
        binding.nestedScroll.scrollToTop()
        binding.motionLayout.transitionToStart()
    }

    override fun onStop() {
        getMainActivity().changeNavigationBarColor(com.resource.R.color.color_background_default)
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        requireActivity().window.statusBarColor = Color.TRANSPARENT
    }

    override fun onPause() {
        super.onPause()
        requireActivity().window.statusBarColor = Color.TRANSPARENT
    }
}