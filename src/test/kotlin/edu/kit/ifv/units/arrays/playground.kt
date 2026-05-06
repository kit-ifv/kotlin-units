package edu.kit.ifv.units.arrays

import edu.kit.ifv.units.joule
import kotlin.random.Random
import kotlin.system.measureNanoTime
import kotlin.time.Duration.Companion.nanoseconds


fun main() {
    val size = 100000
    val list = buildList {
        repeat(size) {
            add(Random.nextDouble().joule)
        }
    }
    val boxArray = list.toTypedArray()
    val nonBox = boxArray.toEnergyArray()

    var resBox = 0.joule
    val boxTime = measureNanoTime {
        resBox = (boxArray.sumOf { it.rawValue }).joule / boxArray.size
    }
    var resNonBox = 0.joule
    val nonBoxTime = measureNanoTime {
        resNonBox = nonBox.average()
    }

    var resList = 0.joule
    val listTime = measureNanoTime {
        resList = (list.sumOf { it.rawValue }).joule / list.size
    }
    println("Result is box: $resBox")
    println("Result non-box: $resNonBox")
    println("Result list: $resList")
    println("Result is equal: ${resBox == resNonBox && resBox == resList}")
    println("Box took        ${boxTime.nanoseconds.inWholeNanoseconds}ns")
    println("Non-Box took    ${nonBoxTime.nanoseconds.inWholeNanoseconds}ns")
    println("List took       ${listTime.nanoseconds.inWholeNanoseconds}ns")
}
