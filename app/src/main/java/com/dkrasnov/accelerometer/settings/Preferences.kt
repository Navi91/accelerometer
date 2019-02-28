package com.dkrasnov.accelerometer.settings

import android.content.Context
import android.content.SharedPreferences

class Preferences(private val context: Context) {

    fun getPreferences(): SharedPreferences {
        return context.getSharedPreferences("siemens_preferences", Context.MODE_PRIVATE)
    }

    fun getEditor(): SharedPreferences.Editor {
        return getPreferences().edit()
    }
}