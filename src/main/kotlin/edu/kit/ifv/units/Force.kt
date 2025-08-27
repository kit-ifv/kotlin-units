package edu.kit.ifv.units

import kotlin.math.absoluteValue
import kotlin.time.Duration


typealias Efficiency = Force
/**
 * A value class representing **Efficiency**, which can be used to model the relationship
 * between energy and distance in various contexts such as car fuel efficiency or mechanical
 * efficiency. The unit of **Efficiency** is typically **Joules per meter (J/m)**, which
 * corresponds to a force per unit distance.
 *
 * This class wraps a raw `Double` value representing the efficiency, and provides various
 * methods for comparing and working with it.
 *
 * @property rawValue The raw `Double` value representing the efficiency in **Joules per meter (J/m)**.
 *
 * @constructor Creates an instance of the `Efficiency` class with the provided raw value.
 *
 * @see Force for an alias representing the same concept of force per unit distance.
 */
@JvmInline
value class Force internal constructor(val rawValue: Double): Comparable<Force>, FlexibleUnit {


    operator fun times(scalar: Double) = Force(rawValue * scalar)
    operator fun times(scalar: Float) = Force(rawValue * scalar)
    operator fun times(scalar: Int) = Force((rawValue * scalar))
    operator fun times(scalar: Long)  = Force((rawValue * scalar))

    operator fun div(scalar: Double): Force = Force(rawValue / scalar)
    operator fun div(scalar: Float): Force = Force(rawValue / scalar)
    operator fun div(scalar: Int): Force =  Force((rawValue / scalar))
    operator fun div(scalar: Long): Force = Force((rawValue / scalar))

    operator fun rangeTo(other: Force): ClosedEfficiencyRange = ClosedEfficiencyRange(this, other)

    operator fun rangeUntil(other: Force) = OpenEfficiencyRange(this, other)
    override fun compareTo(other: Force): Int = rawValue.compareTo(other.rawValue)

    fun toInt(unit: EfficiencyUnit): Int = (rawValue / unit.scale).toInt()
    fun toLong(unit: EfficiencyUnit): Long = (rawValue / unit.scale).toLong()
    fun toDouble(unit: EfficiencyUnit): Double = rawValue / unit.scale

    //--- Define conversions to "naked" number representations here.

    inline val inNewton: Double get() = rawValue

    //--- Define different operations below:
    operator fun times(distance: Distance): Energy = (inNewton * distance.inMeters).joule
    operator fun times(duration: Duration): Impulse = (inNewton * duration.asSeconds).newtonSeconds
    operator fun times(speed: Speed): Power = (inNewton * speed.inMetersPerSecond).watts


    operator fun div(other: Force): Double = rawValue / other.rawValue
    operator fun div(mass: Mass): Acceleration = (inNewton / mass.inKilograms).metersPerSecondSquared
    operator fun div(acceleration: Acceleration): Mass = (inNewton / acceleration.inMetersPerSecondsSquared).kilograms
    operator fun div(frequency: Frequency): Impulse = (inNewton / frequency.inHertz).newtonSeconds
    operator fun div(impulse: Impulse): Frequency = (inNewton / impulse.inNewtonSeconds).hertz


    override fun toOutOfBoundsUnit(): OutOfBoundsUnit {
        return OutOfBoundsUnit(inNewton,
            PhysicsUnit(1,-2,1))
    }

    companion object {
        val MAX = Force(Double.MAX_VALUE)
        val ZERO = Force(.0)
    }
}

class ClosedEfficiencyRange(override val start: Force, override val endInclusive: Force): ClosedRange<Force> {
    override fun contains(value: Force): Boolean {
        return value.rawValue in start.rawValue..endInclusive.rawValue
    }
}

class OpenEfficiencyRange(override val start: Force, override val endExclusive: Force): OpenEndRange<Force> {
    override fun contains(value: Force): Boolean {
        return value.rawValue in start.rawValue..<endExclusive.rawValue
    }
}

val Pair<Energy, Distance>.newton: Force get() = first / second

fun abs(element: Force) = Force(element.rawValue.absoluteValue)
/**
 * I am unaware that there are scales for Efficiency in any meaningful way.
 */

enum class EfficiencyUnit(val scale: Double)  {
    DEFAULT(1.0),
}

fun min(a: Force, b: Force): Force {
    if (a < b) return a
    return b
}

fun max(a: Force, b: Force): Force {
    if (a > b) return a
    return b
}

fun Force.coerceIn(min: Force, max: Force): Force {
    if(this < min) return min
    if(this > max) return max
    return this
}

fun Force.coerceAtLeast(min: Force): Force {
    return  max(this, min)
}

fun Force.coerceAtMost(max: Force): Force {
    return min(this, max)
}