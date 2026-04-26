package edu.kit.ifv.units.arrays

import edu.kit.ifv.units.Temperature

@JvmInline
value class TemperatureArray(private val rawValues: LongArray) {
    private constructor(): this(emptyList<Temperature>())
    constructor(src: Collection<Temperature>): this(src.map { it.rawValue }.toLongArray())

    constructor(size: Int): this(LongArray(size))
    constructor(size: Int, init: (index: Int) -> Temperature): this(LongArray(size) { index -> init(index).rawValue })
    constructor(src: Array<Temperature>): this(src.map { it.rawValue }.toLongArray())

    operator fun get(index: Int) = Temperature(rawValues[index])

    fun mean() = Temperature(rawValues.sum() / rawValues.size)
}
