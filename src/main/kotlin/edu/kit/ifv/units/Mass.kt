@file:Suppress("unused")
package edu.kit.ifv.units

import kotlin.experimental.ExperimentalTypeInference
import kotlin.math.absoluteValue

@JvmInline
value class Mass internal constructor(val rawValue: Long): Comparable<Mass> , FlexibleUnit {

    operator fun unaryMinus(): Mass = Mass(-rawValue)
    operator fun plus(other: Mass) = Mass(rawValue + other.rawValue)
    operator fun minus(other: Mass) = Mass(rawValue - other.rawValue)
    operator fun times(scalar: Double) = Mass((rawValue * scalar).toLong())
    operator fun times(scalar: Float) = Mass((rawValue * scalar).toLong())
    operator fun times(scalar: Int) = Mass((rawValue * scalar))
    operator fun times(scalar: Long)  = Mass((rawValue * scalar))

    operator fun div(scalar: Double): Mass = Mass((rawValue / scalar).toLong())
    operator fun div(scalar: Float): Mass = Mass((rawValue / scalar).toLong())
    operator fun div(scalar: Int): Mass =  Mass((rawValue / scalar))
    operator fun div(scalar: Long): Mass = Mass((rawValue / scalar))

    operator fun rangeTo(other: Mass): ClosedMassRange = ClosedMassRange(this, other)
    operator fun rangeUntil(other: Mass) =OpenMassRange(this, other)
    operator fun rem(other: Mass): Mass = Mass((rawValue % other.rawValue))
    override fun compareTo(other: Mass): Int = rawValue.compareTo(other.rawValue)

    //--- Define conversions to "naked" number representations here.

    fun toInt(unit: MassUnit): Int = (rawValue / unit.scale).toInt()
    fun toLong(unit: MassUnit): Long = rawValue / unit.scale
    fun toDouble(unit: MassUnit): Double = rawValue.toDouble() / unit.scale

    val inKilograms: Double get() = rawValue.toDouble() / KILOGRAM

    //--- Define different operations below:
    operator fun div(other: Mass): Double = rawValue.toDouble() / other.rawValue
    operator fun times(speed:Speed): Impulse = (inKilograms * speed.inMetersPerSecond).newtonSeconds
    operator fun times(acceleration: Acceleration): Force
        = (inKilograms * acceleration.inMetersPerSecondsSquared).newton

    override fun toOutOfBoundsUnit(): OutOfBoundsUnit {
        return OutOfBoundsUnit(inKilograms, PhysicsUnit(0,0,1))
    }

    companion object {

        val MAX = Mass(Long.MAX_VALUE)
        val ZERO = Mass(0L)
        const val MICROGRAM = 1L
        const val MILLIGRAM = 1000L
        const val GRAM = 1_000_000L
        const val KILOGRAM = 1_000_000_000L
        const val TON = 1_000_000_000_000L
    }
}

class ClosedMassRange(override val start: Mass, override val endInclusive: Mass): ClosedRange<Mass> {
    override fun contains(value: Mass): Boolean {
        return value.rawValue in start.rawValue..endInclusive.rawValue
    }
}

class OpenMassRange(override val start: Mass, override val endExclusive: Mass): OpenEndRange<Mass> {
    override fun contains(value: Mass): Boolean {
        return value.rawValue in start.rawValue..<endExclusive.rawValue
    }
}



fun Long.toMass(unit: MassUnit): Mass {
    return Mass(this * unit.scale)
}
fun Double.toMass(unit: MassUnit): Mass {
    return Mass((this * unit.scale).toLong())
}
fun Int.toMass(unit: MassUnit): Mass {
    return Mass((this * unit.scale))
}
fun Float.toMass(unit: MassUnit): Mass {
    return Mass((this * unit.scale).toLong())
}



@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
@JvmName("sumOfMass")
fun <T> Iterable<T>.sumOf(selector: (T) -> Mass): Mass {
    var sum = 0.grams
    for (element in this) {
        sum += selector(element)
    }
    return sum
}
fun Iterable<Mass>.min() = minBy { it }
fun Iterable<Mass>.max() = maxBy { it }
fun Iterable<Mass>.average(): Mass {
    var sum = 0.grams
    var count = 0
    for(element in this) {
        sum += element
        count++
    }
    return sum / count
}
fun abs(element: Mass) = Mass(element.rawValue.absoluteValue)

enum class MassUnit(val scale: Long) {
    MICROGRAM(Mass.MICROGRAM),
    MILLIGRAM(Mass.MILLIGRAM),
    GRAM(Mass.GRAM),
    KILOGRAM(Mass.KILOGRAM),
    TON(Mass.TON);
}

fun min(a: Mass, b: Mass): Mass {
    if (a < b) return a
    return b
}

fun max(a: Mass, b: Mass): Mass {
    if (a > b) return a
    return b
}

fun Mass.coerceIn(min: Mass, max: Mass): Mass {
    if(this < min) return min
    if(this > max) return max
    return this
}

fun Mass.coerceAtLeast(min: Mass): Mass {
    return  max(this, min)
}

fun Mass.coerceAtMost(max: Mass): Mass {
    return min(this, max)
}