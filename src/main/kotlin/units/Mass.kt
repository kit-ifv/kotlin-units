package units

import kotlin.math.absoluteValue

@JvmInline
value class Mass(val rawValue: Long): Comparable<Mass> {

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

    fun toLong(unit: MassUnit) = rawValue / unit.scale
    fun toDouble(unit: MassUnit) = rawValue.toDouble() / unit.scale
    //--- Define conversions to "naked" number representations here.

    val inKilograms: Double get() = toDouble(MassUnit.KILOGRAM)

    //--- Define different operations below:


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
    MICROGRAM(1L),
    MILLIGRAM(1000L),
    GRAM(1_000_000L),
    KILOGRAM(1_000_000_000L),
    TON(1_000_000_000_000L);
}
