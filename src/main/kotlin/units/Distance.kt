package units

import kotlin.math.absoluteValue

@JvmInline
value class Distance(val rawValue: Long): Comparable<Distance> {

    operator fun unaryMinus(): Distance = Distance(-rawValue)
    operator fun plus(other: Distance) = Distance(rawValue + other.rawValue)
    operator fun minus(other: Distance) = Distance(rawValue - other.rawValue)
    operator fun times(scalar: Double) = Distance((rawValue * scalar).toLong())
    operator fun times(scalar: Float) = Distance((rawValue * scalar).toLong())
    operator fun times(scalar: Int) = Distance((rawValue * scalar))
    operator fun times(scalar: Long)  = Distance((rawValue * scalar))

    operator fun div(scalar: Double): Distance = Distance((rawValue / scalar).toLong())
    operator fun div(scalar: Float): Distance = Distance((rawValue / scalar).toLong())
    operator fun div(scalar: Int): Distance =  Distance((rawValue / scalar))
    operator fun div(scalar: Long): Distance = Distance((rawValue / scalar))

    operator fun rangeTo(other: Distance): ClosedDistanceRange = ClosedDistanceRange(this, other)

    operator fun rangeUntil(other: Distance) =OpenDistanceRange(this, other)

    operator fun rem(other: Distance): Distance = Distance((rawValue % other.rawValue))
    override fun compareTo(other: Distance): Int = rawValue.compareTo(other.rawValue)

    fun toLong(unit: DistanceUnit) = rawValue / unit.scale
    fun toDouble(unit: DistanceUnit) = rawValue.toDouble() / unit.scale
    //--- Define conversions to "naked" number representations here.

    val inWholeMillimeters: Long get() = toLong(DistanceUnit.MILLIMETERS)
    val inWholeCentimeters: Long get() = toLong(DistanceUnit.CENTIMETERS)
    val inWholeMeters: Long get() = toLong(DistanceUnit.METERS)
    val inWholeKilometers: Long get() = toLong(DistanceUnit.KILOMETERS)

    val inMeters: Double get() = toDouble(DistanceUnit.METERS)
    val inKilometers: Double get() = toDouble(DistanceUnit.KILOMETERS)

    //--- Define different operations below:
    

}

class ClosedDistanceRange(override val start: Distance, override val endInclusive: Distance): ClosedRange<Distance> {
    override fun contains(value: Distance): Boolean {
        return value.rawValue in start.rawValue..endInclusive.rawValue
    }
}

class OpenDistanceRange(override val start: Distance, override val endExclusive: Distance): OpenEndRange<Distance> {
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
    for(element in this) {
        sum += element
        count++
    }
    return sum / count
}
fun abs(element: Distance) = Distance(element.rawValue.absoluteValue)

enum class DistanceUnit(val scale: Long, val symbol: String) {
    MICROMETERS(1L, "µm"),
    MILLIMETERS(1000L, "mm"),
    CENTIMETERS(10_000L, "cm"),
    INCH(25_400L, "\""),
    DECIMETERS(100_000L, "dm"),
    YARD(914_400L, "yd"),
    METERS(1_000_000L, "m"),
    KILOMETERS(1_000_000_000L, "km"),
    MILE(1_609_340_000L, "mi"),
    SEA_MILE(1_852_000_000L, "nmi");
}