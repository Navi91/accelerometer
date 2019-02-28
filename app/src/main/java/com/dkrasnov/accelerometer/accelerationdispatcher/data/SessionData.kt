package com.dkrasnov.accelerometer.accelerationdispatcher.data

class SessionData {

    val time: Long = System.currentTimeMillis()
    private var data = ""

    fun writeTick(tick: AccelerometerTick) {
        data += if (data.isEmpty()) {
            tick.getStringPresentation()
        } else {
            "\n${tick.getStringPresentation()}"
        }
    }

    fun getData() = data
}