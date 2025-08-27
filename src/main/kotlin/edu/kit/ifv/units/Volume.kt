package edu.kit.ifv.units

import kotlin.experimental.ExperimentalTypeInference
import kotlin.math.absoluteValue


@JvmInline
value class Volume internal constructor(val rawValue: Double): Comparable<Volume>, FlexibleUnit {

    operator fun unaryMinus(): Volume = Volume(-rawValue)
    operator fun plus(other: Volume) = Volume(rawValue + other.rawValue)
    operator fun minus(other: Volume) = Volume(rawValue - other.rawValue)
    operator fun times(scalar: Double) = Volume(rawValue * scalar)
    operator fun times(scalar: Float) = Volume(rawValue * scalar)
    operator fun times(scalar: Int) = Volume((rawValue * scalar))
    operator fun times(scalar: Long)  = Volume((rawValue * scalar))

    operator fun div(scalar: Double): Volume = Volume(rawValue / scalar)
    operator fun div(scalar: Float): Volume = Volume(rawValue / scalar)
    operator fun div(scalar: Int): Volume =  Volume((rawValue / scalar))
    operator fun div(scalar: Long): Volume = Volume((rawValue / scalar))

    operator fun rangeTo(other: Volume): ClosedVolumeRange = ClosedVolumeRange(this, other)

    operator fun rangeUntil(other: Volume) = OpenVolumeRange(this, other)

    operator fun rem(other: Volume): Volume = Volume((rawValue % other.rawValue))
    override fun compareTo(other: Volume): Int = rawValue.compareTo(other.rawValue)

    fun toInt(unit: VolumeUnit): Int = (rawValue / unit.scale).toInt()
    fun toLong(unit: VolumeUnit): Long = (rawValue / unit.scale).toLong()
    fun toDouble(unit: VolumeUnit):Double = rawValue / unit.scale

    //--- Define conversions to "naked" number representations here.

    inline val inLiter: Double get()= rawValue / LITER
    inline val inCubicMeters: Double get() = rawValue / CUBIC_METER

    //--- Define different operations below:
    val benzene: Energy get() = this.inLiter.toEnergy(EnergyUnit.BENZENE_EQUIVALENT)


    operator fun div(other: Volume): Double = rawValue / other.rawValue
    operator fun div(distance: Distance): Area = Area(rawValue / distance.inMeters)
    operator fun div(area: Area): Distance = (inCubicMeters / area.inSquareMeters).meters


    override fun toOutOfBoundsUnit(): OutOfBoundsUnit {
        return OutOfBoundsUnit(inCubicMeters,
            PhysicsUnit(3,0,0))
    }

    companion object {
        const val CUBIC_METER = 1.0
        const val LITER = 0.001
        val MAX = Volume(Double.MAX_VALUE)
        val ZERO = Volume(.0)
        fun ofCube(a: Distance, b: Distance, c: Distance): Volume {
            return Volume(a.inMeters * b.inMeters * c.inMeters)
        }
    }
}

class ClosedVolumeRange(override val start: Volume, override val endInclusive: Volume): ClosedRange<Volume> {
    override fun contains(value: Volume): Boolean {
        return value.rawValue in start.rawValue..endInclusive.rawValue
    }
}

class OpenVolumeRange(override val start: Volume, override val endExclusive: Volume): OpenEndRange<Volume> {
    override fun contains(value: Volume): Boolean {
        return value.rawValue in start.rawValue..<endExclusive.rawValue
    }
}


fun Long.toVolume(unit: VolumeUnit): Volume {
    return Volume(this * unit.scale)
}
fun Double.toVolume(unit: VolumeUnit): Volume {
    return Volume(this * unit.scale)
}
fun Int.toVolume(unit: VolumeUnit): Volume {
    return Volume(this * unit.scale)
}
fun Float.toVolume(unit: VolumeUnit): Volume {
    return Volume(this * unit.scale)
}




@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
@JvmName("sumOfVolume")
fun <T> Iterable<T>.sumOf(selector: (T) -> Volume): Volume {
    var sum = 0.liters
    for (element in this) {
        sum += selector(element)
    }
    return sum
}
fun Iterable<Volume>.min() = minBy { it }
fun Iterable<Volume>.max() = maxBy { it }
fun Iterable<Volume>.average(): Volume {
    var sum = 0.liters
    var count = 0
    for(element in this) {
        sum += element
        count++
    }
    return sum / count
}

fun abs(element: Volume) = Volume(element.rawValue.absoluteValue)

enum class VolumeUnit(val scale: Double)  {
    CUBIC_METER(Volume.CUBIC_METER),
    LITER(Volume.LITER),

}

fun min(a: Volume, b: Volume): Volume {
    if (a < b) return a
    return b
}

fun max(a: Volume, b: Volume): Volume {
    if (a > b) return a
    return b
}

fun Volume.coerceIn(min: Volume, max: Volume): Volume {
    if(this < min) return min
    if(this > max) return max
    return this
}

fun Volume.coerceAtLeast(min: Volume): Volume {
    return  max(this, min)
}

fun Volume.coerceAtMost(max: Volume): Volume {
    return min(this, max)
}