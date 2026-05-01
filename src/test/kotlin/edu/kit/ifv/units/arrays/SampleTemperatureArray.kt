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

}