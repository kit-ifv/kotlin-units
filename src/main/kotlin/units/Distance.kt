package units

import kotlin.math.absoluteValue
import kotlin.math.roundToLong
import kotlin.time.Duration

@JvmInline
value class Distance internal constructor(val rawValue: Long) : Comparable<Distance> {
    internal constructor(doubleValue: Double): this(doubleValue.toLong())
    internal constructor(floatValue: Float): this(floatValue.toLong())

    operator fun unaryMinus(): Distance = Distance(-rawValue)
    operator fun plus(other: Distance) = Distance(rawValue + other.rawValue)
    operator fun minus(other: Distance) = Distance(rawValue - other.rawValue)
    operator fun times(scalar: Double) = Distance((rawValue * scalar).toLong())
    operator fun times(scalar: Float) = Distance((rawValue * scalar).toLong())
    operator fun times(scalar: Int) = Distance((rawValue * scalar))
    operator fun times(scalar: Long) = Distance((rawValue * scalar))

    operator fun div(scalar: Double): Distance = Distance((rawValue / scalar).toLong())
    operator fun div(scalar: Float): Distance = Distance((rawValue / scalar).toLong())
    operator fun div(scalar: Int): Distance = Distance((rawValue / scalar))
    operator fun div(scalar: Long): Distance = Distance((rawValue / scalar))

    operator fun rangeTo(other: Distance): ClosedDistanceRange = ClosedDistanceRange(this, other)

    operator fun rangeUntil(other: Distance) = OpenDistanceRange(this, other)

    operator fun rem(other: Distance): Distance = Distance((rawValue % other.rawValue))
    override fun compareTo(other: Distance): Int = rawValue.compareTo(other.rawValue)

    fun toLong(unit: DistanceUnit) = rawValue / unit.scale
    fun toDouble(unit: DistanceUnit) = rawValue.toDouble() / unit.scale
    //--- Define conversions to "naked" number representations here.

    inline val inWholeMillimeters: Long get() = (rawValue / MILLIMETERS)
    inline val inWholeCentimeters: Long get() = (rawValue / CENTIMETERS)
    inline val inWholeMeters: Long get() =      (rawValue / METERS)
    inline val inWholeKilometers: Long get() =  (rawValue / KILOMETERS)

    inline val inMeters: Double get() = rawValue.toDouble() / METERS
    inline val inKilometers: Double get() = rawValue.toDouble() / KILOMETERS

    //--- Define different operations below:
    operator fun times(distance: Distance): Area {
        return Area(inMeters * distance.inMeters)
    }
    operator fun div(distance: Distance): Double {
        return rawValue.toDouble() / distance.rawValue
    }
    operator fun div(duration: Duration): Speed {
        return Speed(inMeters / duration.asSeconds)
    }

    companion object {
        const val MICROMETERS = 1L
        const val MILLIMETERS = 1000L
        const val CENTIMETERS = 10_000L
        const val INCH = 25_400L
        const val DECIMETERS = 100_000L
        const val YARD = 914_400L
        const val METERS = 1_000_000L
        const val KILOMETERS = 1_000_000_000L
        const val MILE = 1_609_340_000L
        const val SEA_MILE = 1_852_000_000L
    }
}

class ClosedDistanceRange(override val start: Distance, override val endInclusive: Distance) : ClosedRange<Distance> {
    override fun contains(value: Distance): Boolean {
        return value.rawValue in start.rawValue..endInclusive.rawValue
    }
}

class OpenDistanceRange(override val start: Distance, override val endExclusive: Distance) : OpenEndRange<Distance> {
    override fun contains(value: Distance): Boolean {
        return value.rawValue in start.rawValue..<endExclusive.rawValue
    }
}


fun Long.toDistance(unit: DistanceUnit): Distance {
    return Distance(this * unit.scale)
}

fun Double.toDistance(unit: DistanceUnit): Distance {
    return Distance((this * unit.scale).toLong())
}

fun Int.toDistance(unit: DistanceUnit): Distance {
    return Distance((this * unit.scale))
}

fun Float.toDistance(unit: DistanceUnit): Distance {
    return Distance((this * unit.scale).toLong())
}


fun <T> Iterable<T>.sumOf(selector: (T) -> Distance): Distance {
    var sum = 0.meters
    for (element in this) {
        sum += selector(element)
    }
    return sum
}

fun Iterable<Distance>.min() = minBy { it }
fun Iterable<Distance>.max() = maxBy { it }
fun Iterable<Distance>.average(): Distance {
    var sum = 0.meters
    var count = 0
    for (element in this) {
        sum += element
        count++
    }
    return sum / count
}

fun abs(element: Distance) = Distance(element.rawValue.absoluteValue)
@Deprecated("Enum scale values should not be used, rather they should be defined as Unit.companion.ConstVals")
enum class DistanceUnit(val scale: Long, val symbol: String) {
    MICROMETERS(Distance.MICROMETERS, "µm"),
    MILLIMETERS(Distance.MILLIMETERS, "mm"),
    CENTIMETERS(Distance.CENTIMETERS, "cm"),
    INCH(Distance.INCH, "\""),
    DECIMETERS(Distance.DECIMETERS, "dm"),
    YARD(Distance.YARD, "yd"),
    METERS(Distance.METERS, "m"),
    KILOMETERS(Distance.KILOMETERS, "km"),
    MILE(Distance.MILE, "mi"),
    SEA_MILE(Distance.SEA_MILE, "nmi");
}