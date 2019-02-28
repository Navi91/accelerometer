package com.dkrasnov.accelerometer.accelerationdispatcher.data

import android.content.Context
import com.dkrasnov.accelerometer.settings.ModeSettings
import com.dkrasnov.accelerometer.utils.safeDispose
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.BehaviorSubject
import java.lang.IllegalStateException

class Accelerometer(private val context: Context, private val modeSettings: ModeSettings) {

    private var session: Session? = null
    private var sessionDisposable: Disposable? = null
    private val eventBehaviorSubject = BehaviorSubject.create<Event>()

    init {
        modeSettings.getModeObservalbe().subscribe {
            session?.setMode(it)
        }
    }

    fun start() : Boolean {
        if (session != null) return false

        session = Session(context, modeSettings.getMode())
        session?.start()

        session?.getEventObservable()?.subscribe {
            eventBehaviorSubject.onNext(it)
        }

        return true
    }

    fun stopAndSave() {
        session?.stop()
        session?.save()
        session = null
    }

    fun destroy() {
        session?.stop()
        session?.destroy()
        session = null
    }

    fun getEventObservable(): Observable<Event> = eventBehaviorSubject
}