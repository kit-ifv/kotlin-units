package units

import kotlin.experimental.ExperimentalTypeInference
import kotlin.math.absoluteValue
import kotlin.time.Duration


@JvmInline
value class Speed internal constructor(val rawValue: Double): Comparable<Speed> {

    operator fun unaryMinus(): Speed = Speed(-rawValue)
    operator fun plus(other: Speed) = Speed(rawValue + other.rawValue)
    operator fun minus(other: Speed) = Speed(rawValue - other.rawValue)
    operator fun times(scalar: Double) = Speed(rawValue * scalar)
    operator fun times(scalar: Float) = Speed(rawValue * scalar)
    operator fun times(scalar: Int) = Speed((rawValue * scalar))
    operator fun times(scalar: Long)  = Speed((rawValue * scalar))

    operator fun div(scalar: Double): Speed = Speed(rawValue / scalar)
    operator fun div(scalar: Float): Speed = Speed(rawValue / scalar)
    operator fun div(scalar: Int): Speed =  Speed((rawValue / scalar))
    operator fun div(scalar: Long): Speed = Speed((rawValue / scalar))

    operator fun rangeTo(other: Speed): ClosedSpeedRange = ClosedSpeedRange(this, other)

    operator fun rangeUntil(other: Speed) = OpenSpeedRange(this, other)

    operator fun rem(other: Speed): Speed = Speed((rawValue % other.rawValue))
    override fun compareTo(other: Speed): Int = rawValue.compareTo(other.rawValue)

    @Deprecated("Conversions via .toNumber(unit) should no longer be used, if you require a type add it to the library ",
        ReplaceWith("use Unit.as/inXXX for direct conversion")
    )
    fun toInt(unit: SpeedUnit): Int = (rawValue / unit.scale).toInt()
    @Deprecated("Conversions via .toNumber(unit) should no longer be used, if you require a type add it to the library ",
        ReplaceWith("use Unit.as/inXXX for direct conversion")
    )
    fun toLong(unit: SpeedUnit): Long = (rawValue / unit.scale).toLong()
    @Deprecated("Conversions via .toNumber(unit) should no longer be used, if you require a type add it to the library ",
        ReplaceWith("use Unit.as/inXXX for direct conversion")
    )
    fun toDouble(unit: SpeedUnit): Double = rawValue / unit.scale
    //--- Define conversions to "naked" number representations here.


    //--- Define different operations below:
    operator fun div(other: Speed): Double = rawValue / other.rawValue
    operator fun times(duration: Duration) = Distance(rawValue * Distance.METERS * duration.asSeconds)
    operator fun div(duration: Duration): Acceleration = Acceleration(rawValue / duration.asSeconds)
    companion object {

        val MAX = Speed(Double.MAX_VALUE)
        val ZERO = Speed(.0)

        const val METER_PER_SECOND = 1.0
        const val KILOMETER_PER_HOUR = 0.2777777777777778
        const val MILES_PER_HOUR = 0.44704
        const val KNOTS = 0.514444

        fun parse(text: String): Speed {
            val index = text.indexOfFirst { it !in '0'..'9' && it !in setOf('.')}
            val numericComponent = text.substring(0, index).toDouble()
            val unitComponent = SpeedUnit.parseUnit(text.substring(index))

            return numericComponent.toSpeed(unitComponent)
        }
    }

    /**
     * This is likely not what you want to do. This is a function for internal use.
     */
    fun toOutOfBoundsUnit(): OutOfBoundsUnit {
        return OutOfBoundsUnit(rawValue, PhysicsUnit(1, 1, 0))
    }
}

class ClosedSpeedRange(override val start: Speed, override val endInclusive: Speed): ClosedRange<Speed> {
    override fun contains(value: Speed): Boolean {
        return value.rawValue in start.rawValue..endInclusive.rawValue
    }
}

class OpenSpeedRange(override val start: Speed, override val endExclusive: Speed): OpenEndRange<Speed> {
    override fun contains(value: Speed): Boolean {
        return value.rawValue in start.rawValue..<endExclusive.rawValue
    }
}


fun Long.toSpeed(unit: SpeedUnit): Speed {
    return Speed(this * unit.scale)
}
fun Double.toSpeed(unit: SpeedUnit): Speed {
    return Speed(this * unit.scale)
}
fun Int.toSpeed(unit: SpeedUnit): Speed {
    return Speed(this * unit.scale)
}
fun Float.toSpeed(unit: SpeedUnit): Speed {
    return Speed(this * unit.scale)
}




@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
@JvmName("sumOfSpeed")
fun <T> Iterable<T>.sumOf(selector: (T) -> Speed): Speed {
    var sum = 0.toSpeed(SpeedUnit.METER_PER_SECOND)
    for (element in this) {
        sum += selector(element)
    }
    return sum
}
fun Iterable<Speed>.min() = minBy { it }
fun Iterable<Speed>.max() = maxBy { it }
fun Iterable<Speed>.average(): Speed {
    var sum = 0.toSpeed(SpeedUnit.METER_PER_SECOND)
    var count = 0
    for(element in this) {
        sum += element
        count++
    }
    return sum / count
}
fun abs(element: Speed) = Speed(element.rawValue.absoluteValue)
@Deprecated("Enum scale values should not be used, rather they should be defined as Unit.companion.ConstVals")
enum class SpeedUnit(val scale: Double, val symbol: String) {
    METER_PER_SECOND(Speed.METER_PER_SECOND, "m/s"),
    KILOMETER_PER_HOUR(Speed.KILOMETER_PER_HOUR, "km/h"),
    MILES_PER_HOUR(Speed.MILES_PER_HOUR, "mph"),
    KNOTS(Speed.KNOTS, "knot");
    companion object {
        fun parseUnit(text: String): SpeedUnit = entries.first {it.symbol == text}
    }

}