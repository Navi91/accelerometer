package com.dkrasnov.accelerometer

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import com.dkrasnov.accelerometer.accelerationdispatcher.data.Accelerometer
import com.dkrasnov.accelerometer.settings.ModeSettings
import com.dkrasnov.accelerometer.settings.Preferences

class App: Application() {

    companion object {

        @SuppressLint("StaticFieldLeak")
        private lateinit var preferences: Preferences
        private lateinit var modeSettings: ModeSettings
        @SuppressLint("StaticFieldLeak")
        private lateinit var accelerometer: Accelerometer

        fun getPreferences() = preferences

        fun getModeSettings() = modeSettings

        fun getAccelerometer() = accelerometer
    }


    override fun onCreate() {
        super.onCreate()

        preferences = Preferences(this)
        modeSettings = ModeSettings(preferences)
        accelerometer = Accelerometer(this, modeSettings)
    }
}