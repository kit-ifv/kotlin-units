package edu.kit.ifv.units.arrays

import edu.kit.ifv.units.Temperature

@JvmInline
value class SampleTemperatureArray internal constructor(private val rawValues: LongArray) {
    constructor(size: Int): this(LongArray(size))
    constructor(size: Int, init: (index: Int) -> Temperature): this(LongArray(size) { index -> init(index).rawValue })
    constructor(src: Array<Temperature>): this(src.map { it.rawValue }.toLongArray())
    constructor(src: Collection<Temperature>): this(src.map { it.rawValue }.toLongArray())

    val size: Int get() = rawValues.size
    operator fun get(index: Int) = Temperature(rawValues[index])

    operator fun set(index: Int, value: Temperature) {
        rawValues[index] = value.rawValue
    }

    fun mean() = Temperature(rawValues.sum() / rawValues.size)

    fun sum() = Temperature(rawValues.sum())

    fun iterator(): TemperatureIterator = TemperatureIterator(rawValues)

    fun test() {
        val iterator = iterator()
        while (iterator.hasNext()) {
            val next = iterator.next()
            println(next)
        }
    }


    companion object {
        class TemperatureIterator(rawValues: LongArray) : Iterator<Temperature> {
            val internalIterator = rawValues.iterator()
            override fun next(): Temperature = Temperature(internalIterator.next())
            override fun hasNext(): Boolean = internalIterator.hasNext()
        }
    }
}