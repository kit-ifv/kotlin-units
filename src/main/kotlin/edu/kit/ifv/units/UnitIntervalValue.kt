@file:Suppress("unused")
package edu.kit.ifv.units

/**
 * A value class representing a value in bounds of the unit interval [0, 1]
 */
@JvmInline
value class UnitIntervalValue(private val share: Double) {
    fun toDouble(): Double {
        return this.share
    }

    @Suppress("MagicNumber")
    fun toPercentile(): Double {
        return this.share * 100
    }

    companion object {
        val MIDDLE = UnitIntervalValue(0.5)
    }
}


fun Number.share(): UnitIntervalValue {
    val temp = this.toDouble()
    require(temp in 0.0..1.0)
    return UnitIntervalValue(temp)
}

fun String.share(): UnitIntervalValue {
    return toDouble().share()
}
