@file:Suppress("unused")
package edu.kit.ifv.units.arrays

import edu.kit.ifv.units.euros
import edu.kit.ifv.units.joule
import kotlin.random.Random
import kotlin.random.nextInt
import kotlin.system.measureNanoTime
import kotlin.time.Duration.Companion.nanoseconds


fun main() {
    benchmarkAccess(50, 50)
    benchmarkAccess(50, 5000)
    benchmarkAccess(50, 5000000)
    benchmarkAccess(50000, 50)
    benchmarkAccess(50000, 5000)
    benchmarkAccess(50000, 5000000)
    benchmarkAccess(50000000, 50)
    benchmarkAccess(50000000, 5000)
    benchmarkAccess(50000000, 5000000)
}

fun benchmarkSumSteps() {
    println("Benchmarking sum:")
    println("\ntesting size 10")
    benchmarkSum(10)
    println("\n\ntesting size 50")
    benchmarkSum(50)
    println("\n\ntesting size 100")
    benchmarkSum(100)
    println("\n\ntesting size 500")
    benchmarkSum(500)
    println("\n\ntesting size 5000")
    benchmarkSum(5000)
    println("\n\ntesting size 50000")
    benchmarkSum(50000)
    println("\n\ntesting size 500000")
    benchmarkSum(500000)
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

fun benchmarkAccess(size: Int, numAccesses: Int) {
    val randomAccessIndex = buildList {
        repeat(numAccesses) {
            add(Random.nextInt(0..< size))
        }
    }

    val list = buildList {
        repeat(size) {
            add(Random.nextDouble().euros)
        }
    }
    val boxArray = list.toTypedArray()
    val nonBox = boxArray.toCurrencyArray()

    val listAccess = measureNanoTime {
        for (i in randomAccessIndex) {
            list[i]
        }
    }

    val genericArrayAccess = measureNanoTime {
        for (i in randomAccessIndex) {
            boxArray[i]
        }
    }

    val typeArrayAccess = measureNanoTime {
        for (i in randomAccessIndex) {
            nonBox[i]
        }
    }

    println("Testing random  access time for arrays of size $size and $numAccesses accesses")
    println("List took          $listAccess ns (poor list)")
    println("Generic Array took $genericArrayAccess ns")
    println("TypeArray took     $typeArrayAccess ns\n")
}