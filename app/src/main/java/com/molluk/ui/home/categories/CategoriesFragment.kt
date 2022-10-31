package com.molluk.ui.home.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.molluk.BR
import com.molluk.data.status.Resource
import com.molluk.databinding.FragmentCategoriesBinding
import com.molluk.ui.base.BaseFragment
import com.molluk.ui.base.list.setDivider
import com.molluk.ui.base.list.setNoBottomDivider
import com.molluk.ui.base.list.toBaseList
import com.molluk.ui.home.categories.character.CharacterViewModel
import com.resource.fadeVisibility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CategoriesFragment : BaseFragment() {

    private lateinit var binding: FragmentCategoriesBinding
    private val args: CategoriesFragmentArgs by navArgs()
    private val characterViewModel: CharacterViewModel by viewModels()
    private lateinit var adapter: CategoriesAdapter
    private var nextPage = 1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCategoriesBinding.inflate(layoutInflater, container, false)
        binding.setVariable(BR.type, args.categories)
        setToolbar(binding.toolbar)

        initViews()

        setApiObservers()

        return binding.root
    }

    private fun initViews() {
        with(binding) {
            adapter = CategoriesAdapter(args.categories)
            recycler.adapter = adapter
            recycler.setDivider()

            nestedScroll.setOnScrollChangeListener { scroll, _, _, _, _ ->
                if (scroll.canScrollVertically(-1)) {
                    if (topDivider.visibility == View.GONE) {
                        topDivider.apply {
                            topDivider.fadeVisibility(View.VISIBLE)
                        }
                    }
                } else {
                    if (topDivider.visibility == View.VISIBLE) {
                        topDivider.apply {
                            topDivider.fadeVisibility(View.GONE)
                        }
                    }
                }
                if (!scroll.canScrollVertically(1))
                    characterViewModel.getAllCharacterInPage(nextPage)
            }
        }
    }

    private fun setApiObservers() {
        when (args.categories) {
            Categories.Character.name -> {
                characterViewModel.getAllCharacterInPage(nextPage)
                characterViewModel.allCharacterInPageResponse.observe(viewLifecycleOwner) {
                    when (it.status) {
                        Resource.Status.SUCCESS -> {
                            if (it.response != null) {
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
            Categories.Location.name -> {

            }
            Categories.Episode.name -> {

            }
        }
    }
}