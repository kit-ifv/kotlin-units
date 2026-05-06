@file:Suppress("unused")
package edu.kit.ifv.units.arrays

import edu.kit.ifv.units.joule
import kotlin.random.Random
import kotlin.system.measureNanoTime
import kotlin.time.Duration.Companion.nanoseconds


fun main() {
    println("Benchmarking sum:")

    println("testing size 50")
    benchmarkSum(50)

    println("\n\ntesting size 500")
    benchmarkSum(500)
    println("\n\ntesting size 5000")
    benchmarkSum(5000)
    println("\n\ntesting size 50000")
    benchmarkSum(50000)
    println("\n\ntesting size 500000")
    benchmarkSum(500000)
    println("\n\ntesting size 5000000")
    benchmarkSum(5000000)
    println("\n\ntesting size 50000000")
    benchmarkSum(50000000)
}


fun benchmarkArray() {
    val size = 50000000
    val list = buildList {
        repeat(size) {
            add(Random.nextDouble().joule)
        }
    }
    val boxArray = list.toTypedArray()
    val nonBox = boxArray.toEnergyArray()

    var resBox = 0.joule
    val boxTime = measureNanoTime {
        resBox = (boxArray.sumOf { it.inJoule }).joule / boxArray.size
    }
    var resNonBox = 0.joule
    val nonBoxTime = measureNanoTime {
        resNonBox = nonBox.average()
    }

    var resList = 0.joule
    val listTime = measureNanoTime {
        resList = (list.sumOf { it.inJoule }).joule / list.size
    }
    println("Result Array<Energy>: $resBox")
    println("Result EnergyArray: $resNonBox")
    println("Result List<Energy>: $resList")
    println("Result is equal: ${resBox == resNonBox && resBox == resList}")
    require(resBox == resNonBox && resBox == resList)
    println("Array<Energy> took        ${boxTime.nanoseconds.inWholeNanoseconds}ns")
    println("EnergyArray took       ${nonBoxTime.nanoseconds.inWholeNanoseconds}ns")
    println("List<Energy> took        ${listTime.nanoseconds.inWholeNanoseconds}ns")
}

fun benchmarkSum(size: Int = 50) {
    val list = buildList {
        repeat(size) {
            add(Random.nextDouble().joule)
        }
    }
    val boxArray = list.toTypedArray()
    val nonBox = boxArray.toEnergyArray()

    var resBox = 0.joule
    val boxTime = measureNanoTime {
        resBox = (boxArray.sumOf { it.inJoule }).joule
    }
    var resNonBox = 0.joule
    val nonBoxTime = measureNanoTime {
        resNonBox = nonBox.sum()
    }

    var resList = 0.joule
    val listTime = measureNanoTime {
        resList = (list.sumOf { it.inJoule }).joule
    }
    println("Result Array<Energy>: $resBox")
    println("Result EnergyArray:   $resNonBox")
    println("Result List<Energy>:  $resList")
    println("Result is equal: ${resBox == resNonBox && resBox == resList}")
    require(resBox == resNonBox && resBox == resList)
    println("Array<Energy> took        ${boxTime.nanoseconds.inWholeNanoseconds}ns")
    println("EnergyArray took          ${nonBoxTime.nanoseconds.inWholeNanoseconds}ns")
    println("List<Energy> took         ${listTime.nanoseconds.inWholeNanoseconds}ns")
}