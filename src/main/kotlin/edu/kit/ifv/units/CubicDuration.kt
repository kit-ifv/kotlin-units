@file:Suppress("unused")
package edu.kit.ifv.units

import kotlin.experimental.ExperimentalTypeInference
import kotlin.math.absoluteValue
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * The rawValue is in cubic seconds.
 */
@JvmInline
value class CubicDuration internal constructor(val rawValue: Double): Comparable<CubicDuration>, FlexibleUnit {

    operator fun unaryMinus(): CubicDuration = CubicDuration(-rawValue)
    operator fun plus(other: CubicDuration) = CubicDuration(rawValue + other.rawValue)
    operator fun minus(other: CubicDuration) = CubicDuration(rawValue - other.rawValue)
    operator fun times(scalar: Double) = CubicDuration(rawValue * scalar)
    operator fun times(scalar: Float) = CubicDuration(rawValue * scalar)
    operator fun times(scalar: Int) = CubicDuration((rawValue * scalar))
    operator fun times(scalar: Long)  = CubicDuration((rawValue * scalar))

    operator fun div(scalar: Double): CubicDuration = CubicDuration(rawValue / scalar)
    operator fun div(scalar: Float): CubicDuration = CubicDuration(rawValue / scalar)
    operator fun div(scalar: Int): CubicDuration =  CubicDuration((rawValue / scalar))
    operator fun div(scalar: Long): CubicDuration = CubicDuration((rawValue / scalar))


    operator fun rem(other: CubicDuration): CubicDuration = CubicDuration((rawValue % other.rawValue))
    override fun compareTo(other: CubicDuration): Int = rawValue.compareTo(other.rawValue)

    //--- Define conversions to "naked" number representations here.

    inline val inCubicSeconds: Double get() = rawValue/CUBIC_SECONDS
    inline val inCubicMinutes: Double get() = rawValue/CUBIC_MINUTES
    inline val  inCubicHours: Double get() = rawValue/CUBIC_HOURS

    //--- Define different operations below:
    operator fun times(frequency: Frequency): SquareDuration = (inCubicSeconds  * frequency.inHertz).squareSeconds
    operator fun times(duration: Duration) = (inCubicSeconds  * duration.asSeconds).seconds

    operator fun div(other: CubicDuration): Double = inCubicSeconds / other.rawValue
    operator fun div(duration: Duration): SquareDuration = (inCubicSeconds  / duration.asSeconds).squareSeconds
    operator fun div(squareDuration: SquareDuration): Duration
        = (inCubicSeconds  / squareDuration.inSquareSeconds).seconds


    override fun toOutOfBoundsUnit(): OutOfBoundsUnit {
        return OutOfBoundsUnit(
            inCubicSeconds, PhysicsUnit(0,3,0))
    }

    companion object {

        val MAX = CubicDuration(Double.MAX_VALUE)
        val ZERO = CubicDuration(.0)

        const val CUBIC_SECONDS = 1.0
        const val CUBIC_MINUTES = 60*60*60.0
        const val CUBIC_HOURS = 3600*3600*3600.0
    }
}

fun Long.toCubicDuration(unit: CubicDurationUnit): CubicDuration {
    return CubicDuration((this * unit.scale).toDouble())
}
fun Double.toCubicDuration(unit: CubicDurationUnit): CubicDuration {
    return CubicDuration(this * unit.scale)
}
fun Int.toCubicDuration(unit: CubicDurationUnit): CubicDuration {
    return CubicDuration((this * unit.scale).toDouble())
}
fun Float.toCubicDuration(unit: CubicDurationUnit): CubicDuration {
    return CubicDuration((this * unit.scale).toDouble())
}

enum class CubicDurationUnit(val scale: Double) {
    CUBIC_SECONDS(CubicDuration.CUBIC_SECONDS),
    CUBIC_MINUTES(CubicDuration.CUBIC_MINUTES),
    CUBIC_HOURS(CubicDuration.CUBIC_HOURS),
}

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
@JvmName("sumOfCubicDuration")
fun <T> Iterable<T>.sumOf(selector: (T) -> CubicDuration): CubicDuration {
    var sum = 0.cubicSeconds
    for (element in this) {
        sum += selector(element)
    }
    return sum
}
fun Iterable<CubicDuration>.min() = minBy { it }
fun Iterable<CubicDuration>.max() = maxBy { it }
fun Iterable<CubicDuration>.average(): CubicDuration {
    var sum = 0.cubicSeconds
    var count = 0
    for(element in this) {
        sum += element
        count++
    }
    return sum / count
}

fun abs(element: CubicDuration) = CubicDuration(element.rawValue.absoluteValue)

fun min(a: CubicDuration, b: CubicDuration): CubicDuration {
    if (a < b) return a
    return b
}

fun max(a: CubicDuration, b: CubicDuration): CubicDuration {
    if (a > b) return a
    return b
}

fun CubicDuration.coerceIn(min: CubicDuration, max: CubicDuration): CubicDuration {
    if(this < min) return min
    if(this > max) return max
    return this
}

fun CubicDuration.coerceAtLeast(min: CubicDuration): CubicDuration {
    return  max(this, min)
}

fun CubicDuration.coerceAtMost(max: CubicDuration): CubicDuration {
    return min(this, max)
}