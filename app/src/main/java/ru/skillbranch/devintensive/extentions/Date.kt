package ru.skillbranch.devintensive.extentions

import java.text.SimpleDateFormat
import java.util.*

const val SECOND = 1000L
const val MINUTE = 60 * SECOND
const val HOUR = 60 * MINUTE
const val DAY = 24 * HOUR

fun Date.format(pattern:String = "HH:mm:ss dd:MM:yy"):String {
    val dateFormat = SimpleDateFormat(pattern, Locale("ru"))
    return dateFormat.format(this)
}

fun Date.add(value: Int, units: TimeUnits = TimeUnits.SECOND) : Date{
    var time = this.time

    time += when(units){
        TimeUnits.SECOND-> value * SECOND
        TimeUnits.MINUTE-> value * MINUTE
        TimeUnits.HOUR-> value * HOUR
        TimeUnits.DAY-> value * DAY
    }

    this.time = time

    return this
}

enum class TimeUnits {
    SECOND,
    MINUTE,
    HOUR,
    DAY;

    fun plural(value: Int): String {

        return when (this) {
            SECOND -> pluriaze(value, "секунду", "секунды", "секунд")
            MINUTE -> pluriaze(value, "минуту", "минуты", "минут")
            HOUR -> pluriaze(value, "час", "часа", "часов")
            DAY -> pluriaze(value, "день", "дня", "дней")
        }
    }

}

fun Date.humanizeDiff(date: Date = Date()): String {

    val diffSec = date.getTime() - this.time
    var humanReadableDiff = ""
    when(diffSec){
        in -999..999 -> humanReadableDiff = "только что"
        in 1000..44999 -> humanReadableDiff = "несколько секунд назад"
        in 45000..74999 -> humanReadableDiff = "минуту назад"
        in 75000..(45*MINUTE) -> humanReadableDiff = "${TimeUnits.MINUTE.plural((diffSec/MINUTE).toInt())} назад"
        in (45* MINUTE)..(75*MINUTE) -> humanReadableDiff = "час назад"
        in (75*MINUTE)..(22* HOUR) -> humanReadableDiff = "${TimeUnits.HOUR.plural((diffSec/ HOUR).toInt())} назад"
        in (22* HOUR)..(26* HOUR) -> humanReadableDiff = "день назад"
        in (26* HOUR)..(360* DAY ) -> humanReadableDiff = "${TimeUnits.DAY.plural((diffSec/ DAY).toInt())} назад"
        else -> humanReadableDiff = "более года назад"
    }

    return humanReadableDiff
}