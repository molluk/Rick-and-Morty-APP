package com.molluk.ui.home.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.molluk.BR
import com.molluk.data.character.models.CharacterResult
import com.molluk.data.episode.models.EpisodeResult
import com.molluk.data.location.models.LocationResult
import com.molluk.data.status.Resource
import com.molluk.databinding.FragmentCategoriesBinding
import com.molluk.ui.base.BaseFragment
import com.molluk.ui.base.Event
import com.molluk.ui.base.list.setDivider
import com.molluk.ui.base.list.setNoBottomDivider
import com.molluk.ui.base.list.toBaseList
import com.molluk.ui.home.categories.character.CharacterViewModel
import com.molluk.ui.home.categories.episode.EpisodeViewModel
import com.molluk.ui.home.categories.location.LocationViewModel
import com.resource.fadeVisibility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : BaseFragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private val args: CategoriesFragmentArgs by navArgs()
    private val characterViewModel: CharacterViewModel by viewModels()
    private val locationViewModel: LocationViewModel by viewModels()
    private val episodeViewModel: EpisodeViewModel by viewModels()
    private val clickerViewModel: ClickerViewModel by viewModels()
    private lateinit var adapter: CategoriesAdapter

    private var nextPage = 1
    private var lastPage = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        nextPage = 1
        lastPage = -1

        binding = FragmentCategoriesBinding.inflate(layoutInflater, container, false)
        binding.setVariable(BR.type, args.categories)
        setToolbar(binding.toolbar)

        initViews()
        setApiObservers()

        return binding.root
    }

    private fun initViews() {
        with(binding) {
            adapter = CategoriesAdapter(args.categories, clickerViewModel)
            recycler.adapter = adapter
            recycler.setNoBottomDivider(com.resource.R.drawable.shape_separator)
            nestedScroll.setOnScrollChangeListener { view, i, i2, i3, i4 ->
                if (view.canScrollVertically(-1)) {
                    if (binding.separatorTop.visibility == View.GONE) {
                        binding.separatorTop.apply {
                            binding.separatorTop.fadeVisibility(View.VISIBLE)
                        }
                        recolorAppbar(binding.appbar, true)
                    }
                } else {
                    if (binding.separatorTop.visibility == View.VISIBLE) {
                        binding.separatorTop.apply {
                            binding.separatorTop.fadeVisibility(View.GONE)
                        }
                        recolorAppbar(binding.appbar, false)
                    }
                }
                if (!view.canScrollVertically(1)) {
                    if (nextPage != lastPage) {
                        lastPage = nextPage
                        when (args.categories) {
                            Categories.Character.name -> {
                                characterViewModel.getAllCharacterInPage(nextPage)
                            }
                            Categories.Location.name -> {
                                locationViewModel.getAllLocationInPage(nextPage)
                            }
                            else -> {
                                episodeViewModel.getAllEpisodeInPage(nextPage)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun setApiObservers() {
        when (args.categories) {
            Categories.Character.name -> {
                characterViewModel.getAllCharacterInPage(nextPage)
                characterViewModel.allCharacterInPageResponse.observe(viewLifecycleOwner) { event ->
                    event.getContentIfNotHandledOrReturnNull()?.let {
                        when (it.status) {
                            Resource.Status.SUCCESS -> {
                                if (it.response?.results != null) {
                                    val data = it.response.results.toBaseList()
                                    if (nextPage == 1) {
                                        adapter.stockData(data)
                                    } else {
                                        adapter.addData(data.toMutableList())
                                    }
                                    nextPage++
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
            }
            Categories.Location.name -> {
                locationViewModel.getAllLocationInPage(nextPage)
                locationViewModel.allLocationInPageResponse.observe(viewLifecycleOwner) { event ->
                    event.getContentIfNotHandledOrReturnNull()?.let {
                        when (it.status) {
                            Resource.Status.SUCCESS -> {
                                if (it.response?.results != null) {
                                    val data = it.response.results.toBaseList()
                                    if (nextPage == 1) {
                                        adapter.stockData(data)
                                    } else {
                                        adapter.addData(data.toMutableList())
                                    }
                                    nextPage++
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
            }
            Categories.Episode.name -> {
                episodeViewModel.getAllEpisodeInPage(nextPage)
                episodeViewModel.allEpisodeInPageResponse.observe(viewLifecycleOwner) { event ->
                    event.getContentIfNotHandledOrReturnNull()?.let {
                        when (it.status) {
                            Resource.Status.SUCCESS -> {
                                if (it.response?.results != null) {
                                    val data = it.response.results.toBaseList()
                                    if (nextPage == 1) {
                                        adapter.stockData(data)
                                    } else {
                                        adapter.addData(data.toMutableList())
                                    }
                                    nextPage++
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
            }
        }
        clickerViewModel.clickElement.observe(viewLifecycleOwner) { event ->
            event.getContentIfNotHandledOrReturnNull().let { data ->
                if (data != null) {
                    when (data) {
                        is CharacterResult -> {
                            CategoriesFragmentDirections.actionCategoriesFragmentToCharacterFragment(
                                data
                            )

                        }
                        is LocationResult -> {
                            CategoriesFragmentDirections.actionCategoriesFragmentToLocationFragment(
                                data
                            )
                        }
                        is EpisodeResult -> {
                            CategoriesFragmentDirections.actionCategoriesFragmentToEpisodeFragment(
                                data
                            )
                        }
                        else -> {
                            null
                        }
                    }?.let {
                        saveNavigate(it)
                    }
                }
            }
        }
    }
}