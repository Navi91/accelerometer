package com.dkrasnov.accelerometer.settings

import android.content.SharedPreferences
import com.dkrasnov.accelerometer.accelerationdispatcher.data.Mode
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class ModeSettings(private val preferences: Preferences) : SharedPreferences.OnSharedPreferenceChangeListener {

    companion object {

        private const val MODE_PREF = "mode_pref"
    }

    private var mode: Mode = getModeFromPreferences()
    private val modeBehaviorSubject = BehaviorSubject.create<Mode>()

    init {
        modeBehaviorSubject.onNext(mode)

        preferences.getPreferences().registerOnSharedPreferenceChangeListener(this)
    }

    fun getMode() = mode

    fun changeMode() {
        if (mode == Mode.STAND) {
            setMode(Mode.LAY)
        } else {
            setMode(Mode.STAND)
        }
    }

    private fun setMode(mode: Mode) {
        setModePreferences(mode)
    }

    fun getModeObservalbe(): Observable<Mode> = modeBehaviorSubject

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (key == MODE_PREF) {
            mode = getModeFromPreferences()
            modeBehaviorSubject.onNext(mode)
        }
    }

    private fun setModePreferences(mode: Mode) {
        preferences.getEditor().putInt(MODE_PREF, mode.value).apply()
    }

    private fun getModeFromPreferences(): Mode {
        val value = preferences.getPreferences().getInt(MODE_PREF, Mode.STAND.value)

        return Mode.fromValue(value)
    }
}