package com.dkrasnov.accelerometer.accelerationdispatcher.data

sealed class Event(val session: Session)
class TickEvent(session: Session, val tick: AccelerometerTick): Event(session)
class ErrorEvent(session: Session, val message: String): Event(session)
class SaveEvent(session: Session) : Event(session)
class SaveResultEvent(session: Session, val success: Boolean, val e: Throwable?): Event(session)