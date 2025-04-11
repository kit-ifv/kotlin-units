package units

import kotlin.math.absoluteValue


typealias Newton = Efficiency
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
value class Efficiency internal constructor(val rawValue: Double): Comparable<Efficiency> {


    operator fun times(scalar: Double) = Efficiency(rawValue * scalar)
    operator fun times(scalar: Float) = Efficiency(rawValue * scalar)
    operator fun times(scalar: Int) = Efficiency((rawValue * scalar))
    operator fun times(scalar: Long)  = Efficiency((rawValue * scalar))

    operator fun div(scalar: Double): Efficiency = Efficiency(rawValue / scalar)
    operator fun div(scalar: Float): Efficiency = Efficiency(rawValue / scalar)
    operator fun div(scalar: Int): Efficiency =  Efficiency((rawValue / scalar))
    operator fun div(scalar: Long): Efficiency = Efficiency((rawValue / scalar))

    operator fun rangeTo(other: Efficiency): ClosedEfficiencyRange = ClosedEfficiencyRange(this, other)

    operator fun rangeUntil(other: Efficiency) = OpenEfficiencyRange(this, other)
    override fun compareTo(other: Efficiency): Int = rawValue.compareTo(other.rawValue)


    //--- Define conversions to "naked" number representations here.


    //--- Define different operations below:
    operator fun div(other: Efficiency): Double = rawValue / other.rawValue

}

class ClosedEfficiencyRange(override val start: Efficiency, override val endInclusive: Efficiency): ClosedRange<Efficiency> {
    override fun contains(value: Efficiency): Boolean {
        return value.rawValue in start.rawValue..endInclusive.rawValue
    }
}

class OpenEfficiencyRange(override val start: Efficiency, override val endExclusive: Efficiency): OpenEndRange<Efficiency> {
    override fun contains(value: Efficiency): Boolean {
        return value.rawValue in start.rawValue..<endExclusive.rawValue
    }
}

val Pair<Energy, Distance>.efficiency: Efficiency get() = first / second






fun abs(element: Efficiency) = Efficiency(element.rawValue.absoluteValue)
/**
 * I am unaware that there are scales for Efficiency in any meaningful way.
 */

@Deprecated("Enum scale values should not be used, rather they should be defined as Unit.companion.ConstVals")
enum class EfficiencyUnit(val scale: Double)  {
    DEFAULT(1.0),
}