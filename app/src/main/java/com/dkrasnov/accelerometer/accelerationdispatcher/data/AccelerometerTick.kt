package com.dkrasnov.accelerometer.accelerationdispatcher.data

data class AccelerometerTick(val x: Float, val y: Float, val z: Float, val timestamp: Long, val mode: Mode) {

    companion object {

        fun create(x: Float, y: Float, z: Float, mode: Mode) =
            AccelerometerTick(x, y, z, System.currentTimeMillis(), mode)
    }

    fun getStringPresentation() : String {
        return "$x $y $z $timestamp ${mode.value}"
    }
}
