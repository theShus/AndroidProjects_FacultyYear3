package com.example.rmaproject2.data.models.note

import java.time.LocalDate
import java.time.ZoneId
import java.util.*

class StatisticsHolder(
    private val days: Array<Int>,
    private val sizes: Array<Int>
) {
    private var dateNow: Date = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())

    init {
        chechForDateChange()
    }

    fun fillWithExistingData(notes: List<Note>) {//notes reversed, 1st is newest
        notes.forEach {
            when (daysBetween(it.creationDate, dateNow)) {
                0 -> days[4] += 1
                1 -> days[3] += 1
                2 -> days[2] += 1
                3 -> days[1] += 1
                4 -> days[0] += 1
            }
        }

        sizes[0] = days[0] * 100
        sizes[1] = days[1] * 100
        sizes[2] = days[2] * 100
        sizes[3] = days[3] * 100
        sizes[4] = days[4] * 100

        cutGraphSizes()
    }

    private fun daysBetween(d1: Date, d2: Date): Int {
        return ((d2.time - d1.time) / (1000 * 60 * 60 * 24)).toInt()
    }

    fun newDayDataShift() {
        days[0] = days[1]
        days[1] = days[2]
        days[2] = days[3]
        days[3] = days[4]
        days[4] = 0
    }

    fun addToCurrentDay() {
        days[4] += 1
        sizes[4] += 100

        cutGraphSizes()
    }

    fun cutGraphSizes() {
        while (sizes[0] > 600 || sizes[1] > 600 || sizes[2] > 600 || sizes[3] > 600 || sizes[4] > 600) {
            sizes[0] /= 2
            sizes[1] /= 2
            sizes[2] /= 2
            sizes[3] /= 2
            sizes[4] /= 2
        }
    }

    fun getDayGraphSize(day: Int): Int {
        return when (day) {
            1 -> sizes[0]
            2 -> sizes[1]
            3 -> sizes[2]
            4 -> sizes[3]
            5 -> sizes[4]
            else -> 0
        }
    }

    private fun chechForDateChange(){
        var dateRightNow: Date = dateNow
        Thread(
            Runnable {
                while(dateRightNow == dateNow){
                    dateRightNow =  Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())
                    Thread.sleep(900000) //svakih 15 min proveravamo, todo ovo bi inace radilo svaki sekund, ali nije potrebno sada
                }
                dateNow =  Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant())//promenio se dan
                println("PROMENIO SE DANNN")
                newDayDataShift()//pomeramo sve dane za jedan nazad
            }
        ).start()
    }

}