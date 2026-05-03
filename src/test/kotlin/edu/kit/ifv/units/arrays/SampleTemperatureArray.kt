package edu.kit.ifv.units.arrays

import edu.kit.ifv.units.Temperature
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.nanoseconds

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

    fun iterator(): TemperatureIterator = TemperatureIterator(rawValues.iterator())

    companion object {
        @JvmInline
        value class TemperatureIterator internal constructor(val iterator: LongIterator): Iterator<Temperature> {
            override fun next(): Temperature = Temperature(iterator.next())
            override fun hasNext(): Boolean = iterator.hasNext()
        }
    }
}