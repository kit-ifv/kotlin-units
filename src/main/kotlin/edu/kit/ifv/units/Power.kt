@file:Suppress("unused")
package edu.kit.ifv.units


import kotlin.experimental.ExperimentalTypeInference
import kotlin.math.absoluteValue
import kotlin.time.Duration

@JvmInline
value class Power internal constructor(val rawValue: Double): Comparable<Power>, FlexibleUnit {

    operator fun unaryMinus(): Power = Power(-rawValue)
    operator fun plus(other: Power) = Power(rawValue + other.rawValue)
    operator fun minus(other: Power) = Power(rawValue - other.rawValue)
    operator fun times(scalar: Double) = Power(rawValue * scalar)
    operator fun times(scalar: Float) = Power(rawValue * scalar)
    operator fun times(scalar: Int) = Power((rawValue * scalar))
    operator fun times(scalar: Long)  = Power((rawValue * scalar))

    operator fun div(scalar: Double): Power = Power(rawValue / scalar)
    operator fun div(scalar: Float): Power = Power(rawValue / scalar)
    operator fun div(scalar: Int): Power =  Power((rawValue / scalar))
    operator fun div(scalar: Long): Power = Power((rawValue / scalar))

    operator fun rangeTo(other: Power): ClosedPowerRange = ClosedPowerRange(this, other)

    operator fun rangeUntil(other: Power) = OpenPowerRange(this, other)

    operator fun rem(other: Power): Power = Power((rawValue % other.rawValue))
    override fun compareTo(other: Power): Int = rawValue.compareTo(other.rawValue)

    fun toInt(unit: PowerUnit): Int = (rawValue / unit.scale).toInt()
    fun toLong(unit: PowerUnit): Long = (rawValue / unit.scale).toLong()
    fun toDouble(unit: PowerUnit): Double = rawValue / unit.scale
    //--- Define conversions to "naked" number representations here.

    inline val inWatts: Double get() = rawValue / WATTS

    //--- Define different operations below:
    operator fun times(duration: Duration): Energy = Energy(rawValue * duration.asSeconds)


    operator fun div(other: Power): Double = rawValue / other.rawValue
    operator fun div(energy: Energy): Frequency = (inWatts / energy.inJoule).hertz
    operator fun div(acceleration: Acceleration): Impulse
        = (inWatts / acceleration.inMetersPerSecondsSquared).newtonSeconds
    operator fun div(frequency: Frequency): Energy
        = (inWatts / frequency.inHertz).joule
    operator fun div(impulse: Impulse): Acceleration
        = (inWatts / impulse.inNewtonSeconds).metersPerSecondSquared
    operator fun div(force: Force): Speed
            = (inWatts / force.inNewton).metersPerSecond
    operator fun div(speed: Speed): Force
            = (inWatts / speed.inMetersPerSecond).newton


    override fun toOutOfBoundsUnit(): OutOfBoundsUnit {
        return OutOfBoundsUnit(inWatts,
            PhysicsUnit(2,-3,1))
    }

    companion object {
        val MAX = Power(Double.MAX_VALUE)
        val ZERO = Power(.0)
        const val WATTS = 1
    }
}

class ClosedPowerRange(override val start: Power, override val endInclusive: Power): ClosedRange<Power> {
    override fun contains(value: Power): Boolean {
        return value.rawValue in start.rawValue..endInclusive.rawValue
    }
}

class OpenPowerRange(override val start: Power, override val endExclusive: Power): OpenEndRange<Power> {
    override fun contains(value: Power): Boolean {
        return value.rawValue in start.rawValue..<endExclusive.rawValue
    }
}



fun Long.toPower(unit: PowerUnit): Power {
    return Power(this * unit.scale)
}
fun Double.toPower(unit: PowerUnit): Power {
    return Power(this * unit.scale)
}
fun Int.toPower(unit: PowerUnit): Power {
    return Power(this * unit.scale)
}
fun Float.toPower(unit: PowerUnit): Power {
    return Power(this * unit.scale)
}




@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
@JvmName("sumOfPower")
fun <T> Iterable<T>.sumOf(selector: (T) -> Power): Power {
    var sum = 0.toPower(PowerUnit.WATTS)
    for (element in this) {
        sum += selector(element)
    }
    return sum
}
fun Iterable<Power>.min() = minBy { it }
fun Iterable<Power>.max() = maxBy { it }
fun Iterable<Power>.average(): Power {
    var sum = 0.toPower(PowerUnit.WATTS)
    var count = 0
    for(element in this) {
        sum += element
        count++
    }
    return sum / count
}
fun abs(element: Power) = Power(element.rawValue.absoluteValue)

enum class PowerUnit(val scale: Double) {
    WATTS(1.0),
    KILOWATTS(1000.0)
}

fun min(a: Power, b: Power): Power {
    if (a < b) return a
    return b
}

fun max(a: Power, b: Power): Power {
    if (a > b) return a
    return b
}

fun Power.coerceIn(min: Power, max: Power): Power {
    if(this < min) return min
    if(this > max) return max
    return this
}

fun Power.coerceAtLeast(min: Power): Power {
    return  max(this, min)
}

fun Power.coerceAtMost(max: Power): Power {
    return min(this, max)
}