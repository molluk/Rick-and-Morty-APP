package com.molluk

import android.app.Application
import android.content.SharedPreferences
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MollukApplication : Application(){

    @Inject
    lateinit var shared: SharedPreferences

    companion object {
        lateinit var instance: MollukApplication
            private set
    }

}