package com.dkrasnov.accelerometer.accelerationdispatcher.presentation

import com.arellomobile.mvp.MvpView
import com.dkrasnov.accelerometer.accelerationdispatcher.data.AccelerometerTick
import com.dkrasnov.accelerometer.accelerationdispatcher.data.Session

interface AccelerometerView: MvpView {

    fun setStandMode(stand: Boolean)

    fun showSaveProgess(session: Session)

    fun onSessionStarted()

    fun onSessionStopped()

    fun onSessionTick(session: Session, tick: AccelerometerTick)

    fun onSessionError(session: Session, message: String)

    fun onSessionResult(session: Session, success: Boolean, error: Throwable?)
}