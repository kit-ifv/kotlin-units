package edu.kit.ifv.units.arrays

import edu.kit.ifv.units.Temperature
import edu.kit.ifv.units.kelvin

fun main() {
    val myArray: Array<Temperature> = arrayOf(5.kelvin)
    val myList: List<Temperature> = listOf(5.kelvin)
    println(myArray)
    val longArray: LongArray = longArrayOf(5L)
    val arrayOfLong: Array<Long> = arrayOf(5L)
    val tempArray = TemperatureArray(listOf(5.kelvin))
    val test = TemperatureArray(5)
    val test2 = TemperatureArray(5) { _ -> 5.kelvin }

    val elem = tempArray[0]
    println(elem)
}