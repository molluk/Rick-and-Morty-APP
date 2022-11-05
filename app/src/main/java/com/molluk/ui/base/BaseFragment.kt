package com.molluk.ui.base

import android.animation.ValueAnimator
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavDirections
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.R
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.snackbar.Snackbar
import com.molluk.ui.home.categories.character.CharacterViewModel
import com.molluk.ui.main.MainActivity
import com.resource.fadeVisibility

open class BaseFragment : Fragment() {

    private var startAppbarColor: Int = 0
    private var endAppbarColor: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startAppbarColor = resources.getColor(
            com.resource.R.color.color_background_default,
            requireContext().theme
        )
        endAppbarColor = resources.getColor(
            com.resource.R.color.color_background_tile,
            requireContext().theme
        )
    }

    fun Fragment.getMainActivity(): MainActivity {
        return requireActivity() as MainActivity
    }

    fun Fragment.getAppCompatActivity(): AppCompatActivity {
        return requireActivity() as AppCompatActivity
    }

    fun startNewNavigation(destinationId: Int) {
        val mainGraph =
            findNavController().navInflater.inflate(com.molluk.R.navigation.main_navigation_graph)
        mainGraph.setStartDestination(destinationId)
        findNavController().graph = mainGraph
    }

    fun saveNavigate(destinationId: Int) {
        try {
            findNavController().navigate(destinationId)
        } catch (ex: Exception) {
            Log.e("NAVIGATION", ex.stackTraceToString())
        }
    }

    fun saveNavigate(navDirections: NavDirections) {
        try {
            findNavController().navigate(navDirections)
        } catch (ex: Exception) {
            Log.e("NAVIGATION", ex.stackTraceToString())
        }
    }

    fun Fragment.addOnBackPressedCallback(
        enabled: Boolean = true,
        onBackPressed: OnBackPressedCallback.() -> Unit
    ): OnBackPressedCallback {
        return object : OnBackPressedCallback(enabled) {
            override fun handleOnBackPressed() = onBackPressed()
        }.also { requireActivity().onBackPressedDispatcher.addCallback(this, it) }
    }

    fun Fragment.showErrorSnackBar(errorText: String) {
        val snack = Snackbar.make(this.requireView(), errorText, Snackbar.LENGTH_LONG)
            .setTextMaxLines(5)
            .setBackgroundTint(
                resources.getColor(
                    com.resource.R.color.color_background_default,
                    requireContext().theme
                )
            )

        val view = snack.view
        val snackText: TextView =
            view.findViewById(R.id.snackbar_text)
        snackText.textAlignment = View.TEXT_ALIGNMENT_CENTER
        snackText.setTextColor(Color.RED)
        snack.show()
    }

    fun setToolbar(toolbar: MaterialToolbar) {
        toolbar.setNavigationOnClickListener { view ->
            view.findNavController().popBackStack()
        }
    }

    fun setShowDividerScrollListener(
        scroll: View,
        separatorTop: View? = null,
        separatorBottom: View? = null,
        isTrim: Boolean = false,
        appbar: AppBarLayout? = null
    ) {
        scroll.setOnScrollChangeListener { _, _, _, _, _ ->
            if (scroll.canScrollVertically(-1)) {
                if (separatorTop != null && separatorTop.visibility == View.GONE) {
                    separatorTop.apply {
                        separatorTop.fadeVisibility(View.VISIBLE)
                    }
                    if (isTrim) {
                        if (appbar != null) {
                            recolorAppbar(appbar, true)
                        }
                    }
                }
            } else {
                if (separatorTop != null && separatorTop.visibility == View.VISIBLE) {
                    separatorTop.apply {
                        separatorTop.fadeVisibility(View.GONE)
                    }
                    if (isTrim) {
                        if (appbar != null) {
                            recolorAppbar(appbar, false)
                        }
                    }
                }
            }
            if (scroll.canScrollVertically(1)) {
                if (separatorBottom != null && separatorBottom.visibility == View.GONE) {
                    separatorBottom.apply {
                        separatorBottom.fadeVisibility(View.VISIBLE)
                    }
                }
            } else {
                if (separatorBottom != null && separatorBottom.visibility == View.VISIBLE) {
                    separatorBottom.apply {
                        separatorBottom.fadeVisibility(View.GONE)
                    }
                }
            }
        }
    }

    fun recolorAppbar(appbar: AppBarLayout, isRecolor: Boolean) {
        if (isRecolor) {
            val valueAnimator = ValueAnimator.ofArgb(
                startAppbarColor,
                endAppbarColor
            )
            valueAnimator.duration = 300
            valueAnimator.interpolator = LinearInterpolator()
            valueAnimator.addUpdateListener { anim ->
                appbar.setBackgroundColor(
                    anim.animatedValue as Int
                )
            }
            valueAnimator.start()
        } else {
            val valueAnimator = ValueAnimator.ofArgb(
                endAppbarColor,
                startAppbarColor
            )
            valueAnimator.duration = 300
            valueAnimator.interpolator = LinearInterpolator()
            valueAnimator.addUpdateListener { anim ->
                appbar.setBackgroundColor(
                    anim.animatedValue as Int
                )
            }
            valueAnimator.start()
        }
    }
}