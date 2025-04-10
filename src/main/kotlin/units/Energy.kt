package units

import kotlin.math.absoluteValue
import kotlin.time.Duration


@JvmInline
value class Energy(val rawValue: Double): Comparable<Energy> {

    operator fun unaryMinus(): Energy = Energy(-rawValue)
    operator fun plus(other: Energy) = Energy(rawValue + other.rawValue)
    operator fun minus(other: Energy) = Energy(rawValue - other.rawValue)
    operator fun times(scalar: Double) = Energy(rawValue * scalar)
    operator fun times(scalar: Float) = Energy(rawValue * scalar)
    operator fun times(scalar: Int) = Energy((rawValue * scalar))
    operator fun times(scalar: Long)  = Energy((rawValue * scalar))

    operator fun div(scalar: Double): Energy = Energy(rawValue / scalar)
    operator fun div(scalar: Float): Energy = Energy(rawValue / scalar)
    operator fun div(scalar: Int): Energy =  Energy((rawValue / scalar))
    operator fun div(scalar: Long): Energy = Energy((rawValue / scalar))

    operator fun rangeTo(other: Energy): ClosedEnergyRange = ClosedEnergyRange(this, other)

    operator fun rangeUntil(other: Energy) = OpenEnergyRange(this, other)

    operator fun rem(other: Energy): Energy = Energy((rawValue % other.rawValue))
    override fun compareTo(other: Energy): Int = rawValue.compareTo(other.rawValue)

    fun toLong(unit: EnergyUnit) = rawValue / unit.scale
    fun toDouble(unit: EnergyUnit) = rawValue / unit.scale
    //--- Define conversions to "naked" number representations here.


    //--- Define different operations below:

    operator fun div(distance: Distance): Efficiency = Efficiency(rawValue / distance.inMeters)


    companion object {

        fun of(mass: Mass, distance: Distance, duration: Duration): Energy {
            return Energy(
                mass.inKilograms
                        * distance.inMeters
                        * distance.inMeters
                        / duration.asSeconds
                        / duration.asSeconds
            )
        }
    }
}

class ClosedEnergyRange(override val start: Energy, override val endInclusive: Energy): ClosedRange<Energy> {
    override fun contains(value: Energy): Boolean {
        return value.rawValue in start.rawValue..endInclusive.rawValue
    }
}

class OpenEnergyRange(override val start: Energy, override val endExclusive: Energy): OpenEndRange<Energy> {
    override fun contains(value: Energy): Boolean {
        return value.rawValue in start.rawValue..<endExclusive.rawValue
    }
}



fun Long.toEnergy(unit: EnergyUnit): Energy {
    return Energy(this * unit.scale)
}
fun Double.toEnergy(unit: EnergyUnit): Energy {
    return Energy(this * unit.scale)
}
fun Int.toEnergy(unit: EnergyUnit): Energy {
    return Energy(this * unit.scale)
}
fun Float.toEnergy(unit: EnergyUnit): Energy {
    return Energy(this * unit.scale)
}




fun <T> Iterable<T>.sumOf(selector: (T) -> Energy): Energy {
    var sum = 0.toEnergy(EnergyUnit.JOULE)
    for (element in this) {
        sum += selector(element)
    }
    return sum
}
fun Iterable<Energy>.min() = minBy { it }
fun Iterable<Energy>.max() = maxBy { it }
fun Iterable<Energy>.average(): Energy {
    var sum = 0.toEnergy(EnergyUnit.JOULE)
    var count = 0
    for(element in this) {
        sum += element
        count++
    }
    return sum / count
}

fun abs(element: Energy) = Energy(element.rawValue.absoluteValue)
enum class EnergyUnit(val scale: Double) {
    JOULE(1.0),
    KILOJOULE(1000.0),
    KILOWATTHOUR(3_600_000.0),
    BENZENE_EQUIVALENT(32_000_000.0);

}
