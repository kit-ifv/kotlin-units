@file:Suppress("unused")
package edu.kit.ifv.units

import kotlin.experimental.ExperimentalTypeInference
import kotlin.math.absoluteValue
import kotlin.time.Duration

@JvmInline
value class Acceleration(val rawValue: Double): Comparable<Acceleration>, FlexibleUnit {
    operator fun unaryMinus(): Acceleration = Acceleration(-rawValue)
    operator fun plus(other: Acceleration) = Acceleration(rawValue + other.rawValue)
    operator fun minus(other: Acceleration) = Acceleration(rawValue - other.rawValue)
    operator fun times(scalar: Double) = Acceleration(rawValue * scalar)
    operator fun times(scalar: Float) = Acceleration(rawValue * scalar)
    operator fun times(scalar: Int) = Acceleration((rawValue * scalar))
    operator fun times(scalar: Long)  = Acceleration((rawValue * scalar))

    operator fun div(scalar: Double): Acceleration = Acceleration(rawValue / scalar)
    operator fun div(scalar: Float): Acceleration = Acceleration(rawValue / scalar)
    operator fun div(scalar: Int): Acceleration =  Acceleration((rawValue / scalar))
    operator fun div(scalar: Long): Acceleration = Acceleration((rawValue / scalar))

    operator fun rangeTo(other: Acceleration): ClosedAccelerationRange = ClosedAccelerationRange(this, other)

    operator fun rangeUntil(other: Acceleration) = OpenAccelerationRange(this, other)

    operator fun rem(other: Acceleration): Acceleration = Acceleration((rawValue % other.rawValue))
    override fun compareTo(other: Acceleration): Int = rawValue.compareTo(other.rawValue)

    //--- Define conversions to "naked" number representations here.

    fun toInt(unit: AccelerationUnit): Int = (rawValue / unit.scale).toInt()
    fun toLong(unit: AccelerationUnit): Long = (rawValue / unit.scale).toLong()
    fun toDouble(unit: AccelerationUnit): Double = rawValue / unit.scale

    inline val inMetersPerSecondsSquared: Double get() = rawValue / METER_PER_SECOND_SQUARED
    inline val inEarthGravityGs: Double get() = rawValue / GRAVITY_EARTH

    //--- Define different operations below:
    operator fun div(other: Acceleration): Double = rawValue / other.rawValue
    operator fun times(duration: Duration) = Speed(rawValue * duration.asSeconds)
    operator fun times(mass: Mass): Force = (inMetersPerSecondsSquared * mass.inKilograms).newton
    operator fun times(impulse: Impulse): Power
        = (inMetersPerSecondsSquared * impulse.inNewtonSeconds).watts
    operator fun times(squareDuration: SquareDuration): Distance
        = (inMetersPerSecondsSquared * squareDuration.inSquareSeconds).meters
    operator fun div(frequency: Frequency): Speed
        = (this.inMetersPerSecondsSquared * frequency.inHertz).metersPerSecond
    operator fun div(speed: Speed): Frequency
        = (this.inMetersPerSecondsSquared * speed.inMetersPerSecond).hertz

    override fun toOutOfBoundsUnit(): OutOfBoundsUnit {
        return OutOfBoundsUnit(inMetersPerSecondsSquared, PhysicsUnit(1,-2,0))
    }


    companion object {

        val MAX = Acceleration(Double.MAX_VALUE)
        val ZERO = Acceleration(.0)
        const val METER_PER_SECOND_SQUARED = 1.0
        const val GRAVITY_EARTH = 9.81
    }
}

enum class AccelerationUnit(val scale: Double)  {
    METER_PER_SECOND_SQUARED(Acceleration.METER_PER_SECOND_SQUARED),
    GRAVITY_EARTH(Acceleration.GRAVITY_EARTH),
}

class ClosedAccelerationRange(override val start: Acceleration, override val endInclusive: Acceleration): ClosedRange<Acceleration> {
    override fun contains(value: Acceleration): Boolean {
        return value.rawValue in start.rawValue..endInclusive.rawValue
    }
}

class OpenAccelerationRange(override val start: Acceleration, override val endExclusive: Acceleration): OpenEndRange<Acceleration> {
    override fun contains(value: Acceleration): Boolean {
        return value.rawValue in start.rawValue..<endExclusive.rawValue
    }
}

fun Long.toAcceleration(): Acceleration {
    return Acceleration(this.toDouble())
}
fun Double.toAcceleration(): Acceleration {
    return Acceleration(this)
}
fun Int.toAcceleration(): Acceleration {
    return Acceleration(this.toDouble())
}
fun Float.toAcceleration(): Acceleration {
    return Acceleration(this.toDouble())
}

@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
@JvmName("sumOfAcceleration")
fun <T> Iterable<T>.sumOf(selector: (T) -> Acceleration): Acceleration {
    var sum = 0.metersPerSecondSquared
    for (element in this) {
        sum += selector(element)
    }
    return sum
}
fun Iterable<Acceleration>.min() = minBy { it }
fun Iterable<Acceleration>.max() = maxBy { it }
fun Iterable<Acceleration>.average(): Acceleration {
    var sum = 0.metersPerSecondSquared
    var count = 0
    for(element in this) {
        sum += element
        count++
    }
    return sum / count
}

fun abs(element: Acceleration) = Acceleration(element.rawValue.absoluteValue)


fun min(a: Acceleration, b: Acceleration): Acceleration {
    if (a < b) return a
    return b
}

fun max(a: Acceleration, b: Acceleration): Acceleration {
    if (a > b) return a
    return b
}

fun Acceleration.coerceIn(min: Acceleration, max: Acceleration): Acceleration {
    if(this < min) return min
    if(this > max) return max
    return this
}

fun Acceleration.coerceAtLeast(min: Acceleration): Acceleration {
    return  max(this, min)
}

fun Acceleration.coerceAtMost(max: Acceleration): Acceleration {
    return min(this, max)
}