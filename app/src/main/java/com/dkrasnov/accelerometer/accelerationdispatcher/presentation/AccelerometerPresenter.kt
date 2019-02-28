package com.dkrasnov.accelerometer.accelerationdispatcher.presentation

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.dkrasnov.accelerometer.App
import com.dkrasnov.accelerometer.accelerationdispatcher.data.*
import com.dkrasnov.accelerometer.utils.observeMain
import io.reactivex.disposables.CompositeDisposable

@InjectViewState
class AccelerometerPresenter : MvpPresenter<AccelerometerView>() {

    private val modeSettings = App.getModeSettings()
    private val accelerometer = App.getAccelerometer()
    private val compositeDisposable = CompositeDisposable()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        compositeDisposable.add(modeSettings.getModeObservalbe().observeMain().subscribe {
            viewState.setStandMode(it == Mode.STAND)
        })

        compositeDisposable.add(accelerometer.getEventObservable().subscribe { event ->
            when (event) {
                is TickEvent -> viewState.onSessionTick(event.session, event.tick)
                is SaveEvent -> viewState.showSaveProgess(event.session)
                is ErrorEvent -> viewState.onSessionError(event.session, event.message)
                is SaveResultEvent -> viewState.onSessionResult(event.session, event.success, event.e)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.clear()
        accelerometer.destroy()
    }

    fun changeMode() {
        modeSettings.changeMode()
    }

    fun start() {
        if (accelerometer.start()) {
            viewState.onSessionStarted()
        }
    }

    fun stop() {
        accelerometer.stopAndSave()
        viewState.onSessionStopped()
    }
}