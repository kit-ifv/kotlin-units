package units

import kotlin.math.absoluteValue

@JvmInline
value class Efficiency(val rawValue: Double): Comparable<Efficiency> {


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
enum class EfficiencyUnit(val scale: Double)  {
    DEFAULT(1.0),
}