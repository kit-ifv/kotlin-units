package edu.kit.ifv.units.arrays

import edu.kit.ifv.units.degrees
import edu.kit.ifv.units.kelvin
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.nanoseconds


fun main() {
    val arrayNotBox = DurationArray(2) { i ->
        when (i) {
            1 -> 4.minutes
            2 -> 5.nanoseconds
            else -> 0.minutes
        }
    }
    for (i in 0..100000) {
        val temperatureArray = listOf(i.minutes, 23.nanoseconds).toDurationArray()

        print(temperatureArray.average())
        print(temperatureArray.mean())
        require(temperatureArray.mean() == temperatureArray.average()) {"Failed for $i"}
    }
}
