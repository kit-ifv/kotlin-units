package units

import units.CubicDuration.Companion.CUBIC_MINUTES
import kotlin.experimental.ExperimentalTypeInference
import kotlin.math.absoluteValue

@JvmInline
value class Area internal constructor(val rawValue: Double): Comparable<Area> , FlexibleUnit {
    operator fun unaryMinus(): Area = Area(-rawValue)
    operator fun plus(other: Area) = Area(rawValue + other.rawValue)
    operator fun minus(other: Area) = Area(rawValue - other.rawValue)
    operator fun times(scalar: Double) = Area(rawValue * scalar)
    operator fun times(scalar: Float) = Area(rawValue * scalar)
    operator fun times(scalar: Int) = Area((rawValue * scalar))
    operator fun times(scalar: Long)  = Area((rawValue * scalar))

    operator fun div(scalar: Double): Area = Area(rawValue / scalar)
    operator fun div(scalar: Float): Area = Area(rawValue / scalar)
    operator fun div(scalar: Int): Area =  Area((rawValue / scalar))
    operator fun div(scalar: Long): Area = Area((rawValue / scalar))

    operator fun rangeTo(other: Area): ClosedAreaRange = ClosedAreaRange(this, other)

    operator fun rangeUntil(other: Area) = OpenAreaRange(this, other)

    operator fun rem(other: Area): Area = Area((rawValue % other.rawValue))
    override fun compareTo(other: Area): Int = rawValue.compareTo(other.rawValue)

    @Deprecated("Conversions via .toNumber(unit) should no longer be used, if you require a type add it to the library ",
        ReplaceWith("use Unit.as/inXXX for direct conversion")
    )
    fun toInt(unit: AreaUnit): Int = (rawValue / unit.scale).toInt()
    @Deprecated("Conversions via .toNumber(unit) should no longer be used, if you require a type add it to the library ",
        ReplaceWith("use Unit.as/inXXX for direct conversion")
    )
    fun toLong(unit: AreaUnit): Long = (rawValue / unit.scale).toLong()
    @Deprecated("Conversions via .toNumber(unit) should no longer be used, if you require a type add it to the library ",
        ReplaceWith("use Unit.as/inXXX for direct conversion")
    )
    fun toDouble(unit: AreaUnit):Double = rawValue / unit.scale

    //--- Define conversions to "naked" number representations here.

    inline val inSquareMeters: Double get() = rawValue / SQUARE_METERS
    inline val inSquareInch: Double get() = rawValue / SQUARE_INCH
    inline val inSquareKilometers: Double get() = rawValue / SQUARE_KILOMETERS

    //--- Define different operations below:
    operator fun div(area: Area): Double = rawValue / area.rawValue
    operator fun div(distance: Distance): Distance = Distance((rawValue / distance.inMeters) * Distance.METERS)
    operator fun times(distance: Distance): Volume = Volume(rawValue * distance.inMeters)

    override fun toOutOfBoundsUnit(): OutOfBoundsUnit {
        return OutOfBoundsUnit(inSquareMeters, PhysicsUnit(2, 0, 0))
    }

    companion object {

        val MAX = Area(Double.MAX_VALUE)
        val ZERO = Area(.0)
        const val SQUARE_METERS: Double = 1.0
        const val SQUARE_INCH: Double = 0.00064516
        const val SQUARE_KILOMETERS: Double = 1000.0 * 1000.0
    }
}

class ClosedAreaRange(override val start: Area, override val endInclusive: Area): ClosedRange<Area> {
    override fun contains(value: Area): Boolean {
        return value.rawValue in start.rawValue..endInclusive.rawValue
    }
}

class OpenAreaRange(override val start: Area, override val endExclusive: Area): OpenEndRange<Area> {
    override fun contains(value: Area): Boolean {
        return value.rawValue in start.rawValue..<endExclusive.rawValue
    }
}


fun Long.toArea(unit: AreaUnit): Area {
    return Area(this * unit.scale)
}
fun Double.toArea(unit: AreaUnit): Area {
    return Area(this * unit.scale)
}
fun Int.toArea(unit: AreaUnit): Area {
    return Area(this * unit.scale)
}
fun Float.toArea(unit: AreaUnit): Area {
    return Area(this * unit.scale)
}




@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
@JvmName("sumOfArea")
fun <T> Iterable<T>.sumOf(selector: (T) -> Area): Area {
    var sum = 0.toArea(AreaUnit.SQUARE_METERS)
    for (element in this) {
        sum += selector(element)
    }
    return sum
}
fun Iterable<Area>.min() = minBy { it }
fun Iterable<Area>.max() = maxBy { it }
fun Iterable<Area>.average(): Area {
    var sum = 0.toArea(AreaUnit.SQUARE_METERS)
    var count = 0
    for(element in this) {
        sum += element
        count++
    }
    return sum / count
}
fun abs(element: Area) = Area(element.rawValue.absoluteValue)
@Deprecated("Enum scale values should not be used, rather they should be defined as Unit.companion.ConstVals")
enum class AreaUnit(val scale: Double) {
    SQUARE_METERS(Area.SQUARE_METERS),
    SQUARE_INCH(Area.SQUARE_INCH),
    SQUARE_KILOMETERS(Area.SQUARE_KILOMETERS)
}