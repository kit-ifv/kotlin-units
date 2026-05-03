package edu.kit.ifv.units.arrays

import edu.kit.ifv.units.Temperature

/**
 * This class is a typed array for Temperature. At runtime, it is converted to a regular java array
 * of the backing type long (long[]).
 */
@JvmInline
value class SampleTemperatureArray internal constructor(private val rawValues: LongArray) {

    // Developer-Note: This class was generated automatically and likely will again in the future, you might
    // want to edit the code-generation instead of this class specifically. You can find the generating
    // main-function in `src/test/edu/kit/ifv/units/arrays/ArrayGen.kt`.
    // The Gradle task `generateArrays` regenerates all Array classes.

    constructor(size: Int): this(LongArray(size))

    constructor(size: Int, init: (index: Int) -> Temperature):
            this(LongArray(size) { index -> init(index).rawValue })

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


fun Collection<Temperature>.toTemperatureArray() = SampleTemperatureArray(this)

fun Array<Temperature>.toTemperatureArray() = SampleTemperatureArray(this)