@file:Suppress("unused")
package edu.kit.ifv.units

import kotlin.experimental.ExperimentalTypeInference
import kotlin.math.absoluteValue
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * The rawValue is in square seconds.
 */
@JvmInline
value class SquareDuration internal constructor(val rawValue: Double): Comparable<SquareDuration>, FlexibleUnit {

    operator fun unaryMinus(): SquareDuration = SquareDuration(-rawValue)
    operator fun plus(other: SquareDuration) = SquareDuration(rawValue + other.rawValue)
    operator fun minus(other: SquareDuration) = SquareDuration(rawValue - other.rawValue)
    operator fun times(scalar: Double) = SquareDuration(rawValue * scalar)
    operator fun times(scalar: Float) = SquareDuration(rawValue * scalar)
    operator fun times(scalar: Int) = SquareDuration((rawValue * scalar))
    operator fun times(scalar: Long)  = SquareDuration((rawValue * scalar))

    operator fun div(scalar: Double): SquareDuration = SquareDuration(rawValue / scalar)
    operator fun div(scalar: Float): SquareDuration = SquareDuration(rawValue / scalar)
    operator fun div(scalar: Int): SquareDuration =  SquareDuration((rawValue / scalar))
    operator fun div(scalar: Long): SquareDuration = SquareDuration((rawValue / scalar))

    operator fun rangeTo(other: SquareDuration): ClosedSquareDurationRange = ClosedSquareDurationRange(this, other)

    operator fun rangeUntil(other: SquareDuration) = OpenSquareDurationRange(this, other)

    operator fun rem(other: SquareDuration): SquareDuration = SquareDuration((rawValue % other.rawValue))
    override fun compareTo(other: SquareDuration): Int = rawValue.compareTo(other.rawValue)

    //--- Define conversions to "naked" number representations here.

    fun toInt(unit: SquareDurationUnit): Int = (rawValue / unit.scale).toInt()
    fun toLong(unit: SquareDurationUnit): Long = (rawValue / unit.scale).toLong()
    fun toDouble(unit: SquareDurationUnit): Double = rawValue / unit.scale

    inline val inSquareSeconds: Double get() = rawValue/SQUARE_SECONDS
    inline val inSquareMinutes: Double get() = rawValue/SQUARE_MINUTES
    inline val inSquareHours: Double get() = rawValue/SQUARE_HOURS

    //--- Define different operations below:
    operator fun times(duration: Duration) = (rawValue * duration.asSeconds).cubicSeconds
    operator fun times(frequency: Frequency): Duration = (inSquareSeconds * frequency.inHertz).seconds
    operator fun times(acceleration: Acceleration): Distance
        = (inSquareSeconds * acceleration.inMetersPerSecondsSquared).meters


    operator fun div(duration: Duration): Duration = (rawValue / duration.asSeconds).seconds
    operator fun div(other: SquareDuration): Double = rawValue / other.rawValue
    operator fun div(cubicDuration: CubicDuration): Frequency
        = (inSquareSeconds / cubicDuration.inCubicSeconds).hertz
    operator fun div(frequency: Frequency): CubicDuration
            = (inSquareSeconds / frequency.inHertz).cubicSeconds


    override fun toOutOfBoundsUnit(): OutOfBoundsUnit {
        return OutOfBoundsUnit(inSquareSeconds,
            PhysicsUnit(0,2,0))
    }

    companion object {

        val MAX = SquareDuration(Double.MAX_VALUE)
        val ZERO = SquareDuration(.0)

        const val SQUARE_SECONDS = 1.0
        const val SQUARE_MINUTES = 60*60
        const val SQUARE_HOURS = 3600*3600
    }
}

class ClosedSquareDurationRange(override val start: SquareDuration, override val endInclusive: SquareDuration): ClosedRange<SquareDuration> {
    override fun contains(value: SquareDuration): Boolean {
        return value.rawValue in start.rawValue..endInclusive.rawValue
    }
}

class OpenSquareDurationRange(override val start: SquareDuration, override val endExclusive: SquareDuration): OpenEndRange<SquareDuration> {
    override fun contains(value: SquareDuration): Boolean {
        return value.rawValue in start.rawValue..<endExclusive.rawValue
    }
}

enum class SquareDurationUnit(val scale: Double) {
    SQUARE_SECONDS(SquareDuration.SQUARE_SECONDS),
    SQUARE_MINUTES(SquareDuration.SQUARE_MINUTES.toDouble()),
    SQUARE_HOURS(SquareDuration.SQUARE_HOURS.toDouble()),
}

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
@JvmName("sumOfSquareDuration")
fun <T> Iterable<T>.sumOf(selector: (T) -> SquareDuration): SquareDuration {
    var sum = 0.0
    for (element in this) {
        sum += selector(element).rawValue
    }
    return SquareDuration(sum)
}
fun Iterable<SquareDuration>.min() = minBy { it }
fun Iterable<SquareDuration>.max() = maxBy { it }
fun Iterable<SquareDuration>.average(): SquareDuration {
    var sum = 0.0
    var count = 0
    for(element in this) {
        sum += element.rawValue
        count++
    }
    return SquareDuration(sum / count)
}


fun abs(element: SquareDuration) = SquareDuration(element.rawValue.absoluteValue)

fun min(a: SquareDuration, b: SquareDuration): SquareDuration {
    if (a < b) return a
    return b
}

fun max(a: SquareDuration, b: SquareDuration): SquareDuration {
    if (a > b) return a
    return b
}

fun SquareDuration.coerceIn(min: SquareDuration, max: SquareDuration): SquareDuration {
    if(this < min) return min
    if(this > max) return max
    return this
}

fun SquareDuration.coerceAtLeast(min: SquareDuration): SquareDuration {
    return  max(this, min)
}

fun SquareDuration.coerceAtMost(max: SquareDuration): SquareDuration {
    return min(this, max)
}