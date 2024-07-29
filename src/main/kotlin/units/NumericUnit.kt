package units

import kotlin.time.Duration

sealed interface NumericUnit<SCALE : NumericUnitScale> {
    val rawValue: Number
    val type: Map<Class<out NumericUnit<*>>, Int>
    fun toDouble(unit: SCALE): Double
    fun toLong(unit: SCALE): Long
    fun toInt(unit: SCALE): Int


    operator fun unaryMinus(): NumericUnit<SCALE>

    operator fun times(other: NumericUnit<*>): NumericUnit<*> {
        return HigherOrderUnit(this, other)
    }

    operator fun times(other: Duration): NumericUnit<*> {
        return HigherOrderUnit(listOf(this, other.fakeDuration))
    }


    operator fun div(other: Duration): NumericUnit<*> {
        return HigherOrderUnit(listOf(this), listOf(other.fakeDuration))
    }

    operator fun div(other: NumericUnit<SCALE>): Double {
        return rawValue.toDouble() / other.rawValue.toDouble()
    }

    operator fun div(other: NumericUnit<*>): NumericUnit<*> {
        return HigherOrderUnit(listOf(this), listOf(other))
    }
}

interface NumericUnitScale {
    val scale: Number
}
