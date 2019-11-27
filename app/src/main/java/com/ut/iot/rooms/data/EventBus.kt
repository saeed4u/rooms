package com.ut.iot.rooms.data

import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject


/**
 * Created by Saeed on 2019-10-19.
 */
object EventBus {
    private val publisher = PublishSubject.create<Any>()

    fun <T> listen(event: Class<T>): Observable<T> = publisher.ofType(event)

    fun publish(event: Any?) {
        event?.let { publisher.onNext(it) }
    }
}