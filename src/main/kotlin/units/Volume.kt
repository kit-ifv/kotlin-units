package units

import kotlin.math.absoluteValue


@JvmInline
value class Volume(val rawValue: Double): Comparable<Volume> {

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

    fun toLong(unit: VolumeUnit) = rawValue / unit.scale
    fun toDouble(unit: VolumeUnit) = rawValue / unit.scale
    //--- Define conversions to "naked" number representations here.
    val inLiter: Double get()= rawValue / LITER

    //--- Define different operations below:
    val benzene: Energy get() = this.inLiter.toEnergy(EnergyUnit.BENZENE_EQUIVALENT)

    operator fun div(other: Volume): Double = rawValue / other.rawValue

    companion object {
        const val CUBIC_METER = 1.0
        const val LITER = 0.001

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
@Deprecated("Enum scale values should not be used, rather they should be defined as Unit.companion.ConstVals")
enum class VolumeUnit(val scale: Double)  {
    CUBIC_METER(Volume.CUBIC_METER),
    LITER(Volume.LITER),

}