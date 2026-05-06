package edu.kit.ifv.units.arrays

import edu.kit.ifv.units.joule
import edu.kit.ifv.units.kilowatthours
import kotlin.system.measureNanoTime
import kotlin.time.Duration.Companion.nanoseconds


fun main() {
    val size = 50000000
    val list = buildList {
        for (i in 0 until size) {
            add(i.kilowatthours)
        }
    }
    val boxArray = list.toTypedArray()
    val nonBox = boxArray.toEnergyArray()

    var res1 = 0.joule
    val boxTime = measureNanoTime {
        res1 = (boxArray.sumOf { it.rawValue }).joule
    }
    var res2 = 0.joule
    val nonBoxTime = measureNanoTime {
        res2 = nonBox.sum()
    }

    var resList = 0.joule
    val listTime = measureNanoTime {
        resList = (list.sumOf { it.rawValue }).joule
    }
    println("Result is box: $res1")
    println("Result non-box: $res2")
    println("Result list: $resList")
    println("Result is equal: ${res1 == res2 && res1 == resList}")
    println("Box took ${boxTime.nanoseconds.inWholeMilliseconds}ms")
    println("Non-Box took ${nonBoxTime.nanoseconds.inWholeMilliseconds}ms")
    println("List took ${listTime.nanoseconds.inWholeMilliseconds}ms")
}
