package com.example.bookrating.rating

import io.reactivex.Observable
import io.reactivex.Scheduler
import java.util.concurrent.TimeUnit
import kotlin.random.Random

class NumberGenerator(val scheduler: Scheduler) {

    fun getNextNumber(): Observable<Int> {
        return Observable.interval(3000, TimeUnit.MILLISECONDS, scheduler)
            .map { Random.nextInt(5) }

//        return Observable.just(true)
//            .map {
//                Random.nextInt(5)
//            }.repeatWhen {
//                it.delay(3, TimeUnit.SECONDS)
//            }
//            .observeOn(scheduler)
    }
}