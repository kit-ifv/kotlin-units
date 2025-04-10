package unitsOld

import kotlin.math.roundToInt
import kotlin.math.roundToLong


/**
 * Some units are not reasonably representable with an Integer or Long variable, since floating point arithmetics
 * support calculations with infinities no special shenanigans are needed to handle overflows
 */
sealed interface FloatUnit<SCALE : FloatUnitScale> : NumericUnit<SCALE>, Comparable<FloatUnit<SCALE>> {
    override val rawValue: Double

    override fun toDouble(unit: SCALE): Double {
        return rawValue / unit.scale
    }

    override fun toLong(unit: SCALE): Long {
        return (rawValue / unit.scale).roundToLong()
    }

    override fun toInt(unit: SCALE): Int {
        return (rawValue / unit.scale).roundToInt()
    }

    operator fun plus(other: FloatUnit<SCALE>): FloatUnit<SCALE>

    operator fun minus(other: FloatUnit<SCALE>): FloatUnit<SCALE>

    override fun unaryMinus(): FloatUnit<SCALE>

    override fun compareTo(other: FloatUnit<SCALE>): Int {
        return rawValue.compareTo(other.rawValue)
    }

}

interface FloatUnitScale : NumericUnitScale {
    override val scale: Double
}
