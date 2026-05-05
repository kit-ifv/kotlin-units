package edu.kit.ifv.units.arrays

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
    val first = arrayNotBox[0]
    val second = arrayNotBox[1]
    val sum = arrayNotBox.sum()
    val test = IntArray(3).sum()
    println(sum)
    require(first + second == sum)
    assert(first + second == sum)
    assert(arrayNotBox.mean() == (4.minutes + 5.nanoseconds)/ 2)
}
