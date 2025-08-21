package units

import kotlin.math.absoluteValue
import kotlin.time.Duration


typealias Efficiency = Newton
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
 * @see Newton for an alias representing the same concept of force per unit distance.
 */
@JvmInline
value class Newton internal constructor(val rawValue: Double): Comparable<Newton> {


    operator fun times(scalar: Double) = Newton(rawValue * scalar)
    operator fun times(scalar: Float) = Newton(rawValue * scalar)
    operator fun times(scalar: Int) = Newton((rawValue * scalar))
    operator fun times(scalar: Long)  = Newton((rawValue * scalar))

    operator fun div(scalar: Double): Newton = Newton(rawValue / scalar)
    operator fun div(scalar: Float): Newton = Newton(rawValue / scalar)
    operator fun div(scalar: Int): Newton =  Newton((rawValue / scalar))
    operator fun div(scalar: Long): Newton = Newton((rawValue / scalar))

    operator fun rangeTo(other: Newton): ClosedEfficiencyRange = ClosedEfficiencyRange(this, other)

    operator fun rangeUntil(other: Newton) = OpenEfficiencyRange(this, other)
    override fun compareTo(other: Newton): Int = rawValue.compareTo(other.rawValue)


    //--- Define conversions to "naked" number representations here.

    inline val inNewton: Double get() = rawValue

    //--- Define different operations below:
    operator fun div(other: Newton): Double = rawValue / other.rawValue
    operator fun times(distance: Distance): Energy = (inNewton * distance.inMeters).joule
    operator fun times(duration: Duration): Impulse = (inNewton * duration.asSeconds).newton_seconds
    operator fun div(mass: Mass): Acceleration = (inNewton / mass.inKilograms).meters_per_second_squared
    operator fun div(acceleration: Acceleration): Mass = (inNewton / acceleration.inMetersPerSecondsSquared).kilograms
    companion object {
        val MAX = Newton(Double.MAX_VALUE)
        val ZERO = Newton(.0)
    }
}

class ClosedEfficiencyRange(override val start: Newton, override val endInclusive: Newton): ClosedRange<Newton> {
    override fun contains(value: Newton): Boolean {
        return value.rawValue in start.rawValue..endInclusive.rawValue
    }
}

class OpenEfficiencyRange(override val start: Newton, override val endExclusive: Newton): OpenEndRange<Newton> {
    override fun contains(value: Newton): Boolean {
        return value.rawValue in start.rawValue..<endExclusive.rawValue
    }
}

val Pair<Energy, Distance>.newton: Newton get() = first / second

fun abs(element: Newton) = Newton(element.rawValue.absoluteValue)
/**
 * I am unaware that there are scales for Efficiency in any meaningful way.
 */

@Deprecated("Enum scale values should not be used, rather they should be defined as Unit.companion.ConstVals")
enum class EfficiencyUnit(val scale: Double)  {
    DEFAULT(1.0),
}