package edu.kit.ifv.units

import kotlin.experimental.ExperimentalTypeInference
import kotlin.math.absoluteValue
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds


@JvmInline
value class Energy internal constructor(val rawValue: Double): Comparable<Energy>, FlexibleUnit {

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

    fun toInt(unit: EnergyUnit): Int = (rawValue / unit.scale).toInt()
    fun toLong(unit: EnergyUnit): Long = (rawValue / unit.scale).toLong()
    fun toDouble(unit: EnergyUnit): Double = rawValue / unit.scale

    //--- Define conversions to "naked" number representations here.

    inline val inJoule: Double get() = rawValue / JOULE
    inline val inKiloJoule: Double get() = rawValue / KILOJOULE
    inline val inKiloWattHours: Double get() = rawValue / KILOWATTHOUR

    //--- Define different operations below:
    operator fun times(frequency: Frequency): Power = (inJoule * frequency.inHertz).watts

    operator fun div(other: Energy): Double = rawValue / other.rawValue
    operator fun div(distance: Distance): Force = Force(rawValue / distance.inMeters)
    operator fun div(speed: Speed): Impulse = (inJoule / speed.inMetersPerSecond).newton_seconds
    operator fun div(duration: Duration): Power = Power(rawValue / duration.asSeconds)
    operator fun div(force: Force): Distance = (rawValue / force.rawValue).meters
    operator fun div(impulse: Impulse): Speed = (inJoule / impulse.inNewtonSeconds).meters_per_second
    operator fun div(power: Power): Duration = (inJoule / power.inWatts).seconds

    override fun toOutOfBoundsUnit(): OutOfBoundsUnit {
        return OutOfBoundsUnit(inJoule, PhysicsUnit(2,-2,1))
    }

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

enum class EnergyUnit(val scale: Double) {
    JOULE(Energy.JOULE),
    KILOJOULE(Energy.KILOJOULE),
    KILOWATTHOUR(Energy.KILOWATTHOUR),
    BENZENE_EQUIVALENT(Energy.BENZENE_EQUIVALENT);

}

fun min(a: Energy, b: Energy): Energy {
    if (a < b) return a
    return b
}

fun max(a: Energy, b: Energy): Energy {
    if (a > b) return a
    return b
}

fun Energy.coerceIn(min: Energy, max: Energy): Energy {
    if(this < min) return min
    if(this > max) return max
    return this
}

fun Energy.coerceAtLeast(min: Energy): Energy {
    return  max(this, min)
}

fun Energy.coerceAtMost(max: Energy): Energy {
    return min(this, max)
}