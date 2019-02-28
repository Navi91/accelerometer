package com.dkrasnov.accelerometer.accelerationdispatcher.data

enum class Mode(val value: Int) {
    STAND(0), LAY(1);

    companion object {

        fun fromValue(value: Int): Mode {
            for (mode in values()) {
                if (mode.value == value) return mode
            }

            return STAND
        }
    }
}