package com.molluk.ui.home.categories.character

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.core.graphics.ColorUtils
import androidx.navigation.fragment.findNavController
import com.molluk.databinding.FragmentCharacterBinding
import com.molluk.ui.base.BaseFragment
import androidx.navigation.fragment.navArgs
import com.molluk.BR
import com.molluk.utils.ui.scrollToTop
import com.resource.customRotate
import com.resource.fadeVisibility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterFragment : BaseFragment() {
    private lateinit var binding: FragmentCharacterBinding
    private val args: CharacterFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterBinding.inflate(inflater, container, false)
        binding.setVariable(BR.data, args.data)

        initViews()

        return binding.root
    }

    private fun initViews() {
        with(binding) {
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
                    val alpha = bgTop.alpha * 255
                    val color = ColorUtils.setAlphaComponent(
                        requireContext().getColor(com.resource.R.color.color_background_tile),
                        alpha.toInt()
                    )

                    requireActivity().window.statusBarColor = color
                    binding.separatorTop.visibility = View.INVISIBLE
                }

                override fun onTransitionCompleted(
                    motionLayout: MotionLayout?,
                    currentId: Int
                ) {
                    share.setBackgroundColor(Color.TRANSPARENT)
                    binding.bgTop.visibility = View.INVISIBLE
                    binding.separatorTop.visibility = View.INVISIBLE
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
                val intent = Intent()
                intent.action = Intent.ACTION_SEND
                intent.putExtra(
                    Intent.EXTRA_TEXT,
                    args.data.url
                )
                intent.type = "text/plain"
                startActivity(
                    Intent.createChooser(
                        intent,
                        requireContext().getString(com.resource.R.string.button_share) + args.data.name
                    )
                )
            }
            origin.setOnClickListener {

            }
            location.setOnClickListener {

            }
            episodeTitle.setOnClickListener {
                when(binding.moreEpisode.visibility){
                    View.VISIBLE -> {
                        binding.moreEpisode.fadeVisibility(View.GONE)
                        binding.showMoreEpisode.customRotate(180f, 360f, 250, true)
                    }
                    View.GONE -> {
                        if (binding.data?.episode != null) {
                            binding.moreEpisode.fadeVisibility(View.VISIBLE)
                            binding.showMoreEpisode.customRotate(0f, 180f, 250, true)
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