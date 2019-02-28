package com.dkrasnov.accelerometer.accelerationdispatcher.data

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

class Session(context: Context, private var mode: Mode) : SensorEventListener {

    private val data = SessionData()
    private val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val sensor = if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null) {
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    } else {
        null
    }
    private val eventBehaviorSubject = BehaviorSubject.create<Event>()

    fun start() {
        if (sensor == null) {
            eventBehaviorSubject.onNext(ErrorEvent("Акселерометр не найден"))
            return
        }

        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_FASTEST)
    }

    fun stop() {
        sensorManager.unregisterListener(this)
    }

    fun save() {

    }

    fun destroy() {
        sensorManager.unregisterListener(this)
    }

    fun setMode(mode: Mode) {
        this.mode = mode
    }

    fun getEventObservable(): Observable<Event> = eventBehaviorSubject

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) = Unit

    override fun onSensorChanged(event: SensorEvent) {
        val tick = AccelerometerTick.create(event.values[0], event.values[1], event.values[2], mode)
        data.writeTick(tick)
    }
}