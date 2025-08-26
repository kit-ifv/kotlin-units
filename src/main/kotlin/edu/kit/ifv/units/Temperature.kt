package edu.kit.ifv.units

import kotlin.math.absoluteValue

@JvmInline
value class Temperature internal constructor(val rawValue: Long) : Comparable<Temperature> {

    operator fun plus(other: Temperature) = Temperature(rawValue + other.rawValue)
    operator fun minus(other: Temperature) = Temperature(rawValue - other.rawValue)

    operator fun rangeTo(other: Temperature): ClosedTemperatureRange = ClosedTemperatureRange(this, other)

    operator fun rangeUntil(other: Temperature) = OpenTemperatureRange(this, other)
    @Deprecated("Conversions via .toNumber(unit) should no longer be used, if you require a type add it to the library ",
        ReplaceWith("use Unit.as/inXXX for direct conversion")
    )
    fun toInt(unit: TemperatureUnit): Int = (rawValue / unit.scale).toInt()
    @Deprecated("Conversions via .toNumber(unit) should no longer be used, if you require a type add it to the library ",
        ReplaceWith("use Unit.as/inXXX for direct conversion")
    )
    fun toLong(unit: TemperatureUnit): Long = rawValue / unit.scale
    @Deprecated("Conversions via .toNumber(unit) should no longer be used, if you require a type add it to the library ",
        ReplaceWith("use Unit.as/inXXX for direct conversion")
    )
    fun toDouble(unit: TemperatureUnit):Double = rawValue.toDouble() / unit.scale
    override fun compareTo(other: Temperature): Int {
        return rawValue.compareTo(other.rawValue)
    }

    //--- Define conversions to "naked" number representations here.


    //--- Define different operations below:


    companion object {
        val ABSOLUTE_ZERO = Temperature(0)
        val ZERO = ABSOLUTE_ZERO
        val MAX = Temperature(Long.MAX_VALUE)
    }

}

class ClosedTemperatureRange(override val start: Temperature, override val endInclusive: Temperature) :
    ClosedRange<Temperature> {
    override fun contains(value: Temperature): Boolean {
        return value.rawValue in start.rawValue..endInclusive.rawValue
    }
}

class OpenTemperatureRange(override val start: Temperature, override val endExclusive: Temperature) :
    OpenEndRange<Temperature> {
    override fun contains(value: Temperature): Boolean {
        return value.rawValue in start.rawValue..<endExclusive.rawValue
    }
}

fun Long.toTemperature(unit: TemperatureUnit): Temperature {
    return Temperature(this * unit.scale + unit.offset)
}

fun Double.toTemperature(unit: TemperatureUnit): Temperature {
    return Temperature((this * unit.scale).toLong() + unit.offset)
}

fun Int.toTemperature(unit: TemperatureUnit): Temperature {
    return Temperature((this * unit.scale) + unit.offset)
}

fun Float.toTemperature(unit: TemperatureUnit): Temperature {
    return Temperature((this * unit.scale).toLong() + unit.offset)
}



fun Iterable<Temperature>.min() = minBy { it }
fun Iterable<Temperature>.max() = maxBy { it }
fun Iterable<Temperature>.average(): Temperature {
    var sum = 0L
    var count = 0
    for(element in this) {
        sum += element.rawValue
        count++
    }
    return Temperature(sum / count)
}


fun abs(element: Temperature) = Temperature(element.rawValue.absoluteValue)
@Deprecated("Enum scale values should not be used, rather they should be defined as Unit.companion.ConstVals")
enum class TemperatureUnit(val scale: Long, val offset: Long) {

    KELVIN(1_000_000L, 0),
    FAHRENHEIT(555_556L, 255_372_222L),
    CELSIUS(1_000_000L, 273_150_000L);


}