package com.molluk.ui.main

import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.molluk.R
import com.molluk.databinding.ActivityMainBinding
import com.resource.slideVisibility
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var dispatchIsWork = true

    private lateinit var binding: ActivityMainBinding

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    private var isBottomNavigationVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        window.navigationBarColor = resources.getColor(com.resource.R.color.black, theme)
        navHostFragment =
            (supportFragmentManager.findFragmentById(R.id.navHostFragmentMain) as NavHostFragment)
        navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.homeFragment || destination.id == R.id.favouritesFragment || destination.id == R.id.settingsFragment) {
                isBottomNavigationVisible = true
                showBottomNavigation(true)
            }
        }

        binding.bottomNavigationView.setupWithNavController(navController)
    }

    private fun showBottomNavigation(isShow: Boolean) {
        binding.bottomNavigationView.post {
            if (isShow) {
                binding.bottomNavigationView.slideVisibility(View.VISIBLE)
            } else {
                binding.bottomNavigationView.slideVisibility(View.GONE)
            }
        }
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        if (dispatchIsWork) {
            if (event != null) {
                if (event.action == MotionEvent.ACTION_DOWN) {
                    val v: View? = currentFocus
                    if (v is EditText) {
                        val outRect = Rect()
                        v.getGlobalVisibleRect(outRect)
                        if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                            v.clearFocus()
                            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                        }
                    }
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
}