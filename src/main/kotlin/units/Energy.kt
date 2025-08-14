package units

import kotlin.experimental.ExperimentalTypeInference
import kotlin.math.absoluteValue
import kotlin.time.Duration


@JvmInline
value class Energy internal constructor(val rawValue: Double): Comparable<Energy> {

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

    @Deprecated("Conversions via .toNumber(unit) should no longer be used, if you require a type add it to the library ",
        ReplaceWith("use Unit.as/inXXX for direct conversion")
    )
    fun toInt(unit: EnergyUnit): Int = (rawValue / unit.scale).toInt()
    @Deprecated("Conversions via .toNumber(unit) should no longer be used, if you require a type add it to the library ",
        ReplaceWith("use Unit.as/inXXX for direct conversion")
    )
    fun toLong(unit: EnergyUnit): Long = (rawValue / unit.scale).toLong()
    @Deprecated("Conversions via .toNumber(unit) should no longer be used, if you require a type add it to the library ",
        ReplaceWith("use Unit.as/inXXX for direct conversion")
    )
    fun toDouble(unit: EnergyUnit): Double = rawValue / unit.scale
    //--- Define conversions to "naked" number representations here.

    inline val inJoule: Double get() = rawValue / JOULE
    inline val inKiloJoule: Double get() = rawValue / KILOJOULE
    inline val inKiloWattHours: Double get() = rawValue / KILOWATTHOUR

    //--- Define different operations below:
    operator fun div(other: Energy): Double = rawValue / other.rawValue
    operator fun div(distance: Distance): Newton = Newton(rawValue / distance.inMeters)
    operator fun div(duration: Duration): Power = Power(rawValue / duration.asSeconds)

    operator fun div(newton: Newton): Distance = (rawValue / newton.rawValue).meters

    companion object {
        val MAX = Energy(Double.MAX_VALUE)
        val ZERO = Energy(.0)

        const val JOULE: Double = 1.0
        const val KILOJOULE: Double = 1000.0
        const val KILOWATTHOUR: Double = 3_600_000.0
        const val BENZENE_EQUIVALENT: Double = 32_000_000.0


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



@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
@JvmName("sumOfDistance")
fun <T> Iterable<T>.sumOf(selector: (T) -> Energy): Energy {
    var sum = 0.joule
    for (element in this) {
        sum += selector(element)
    }
    return sum
}
fun Iterable<Energy>.min() = minBy { it }
fun Iterable<Energy>.max() = maxBy { it }
fun Iterable<Energy>.average(): Energy {
    var sum = 0.joule
    var count = 0
    for(element in this) {
        sum += element
        count++
    }
    return sum / count
}

fun abs(element: Energy) = Energy(element.rawValue.absoluteValue)

@Deprecated("Enum scale values should not be used, rather they should be defined as Unit.companion.ConstVals")
enum class EnergyUnit(val scale: Double) {
    JOULE(Energy.JOULE),
    KILOJOULE(Energy.KILOJOULE),
    KILOWATTHOUR(Energy.KILOWATTHOUR),
    BENZENE_EQUIVALENT(Energy.BENZENE_EQUIVALENT);

}
