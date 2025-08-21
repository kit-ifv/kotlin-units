package units


import kotlin.experimental.ExperimentalTypeInference
import kotlin.math.absoluteValue
import kotlin.time.Duration

@JvmInline
value class Power internal constructor(val rawValue: Double): Comparable<Power> {

    operator fun unaryMinus(): Power = Power(-rawValue)
    operator fun plus(other: Power) = Power(rawValue + other.rawValue)
    operator fun minus(other: Power) = Power(rawValue - other.rawValue)
    operator fun times(scalar: Double) = Power(rawValue * scalar)
    operator fun times(scalar: Float) = Power(rawValue * scalar)
    operator fun times(scalar: Int) = Power((rawValue * scalar))
    operator fun times(scalar: Long)  = Power((rawValue * scalar))

    operator fun div(scalar: Double): Power = Power(rawValue / scalar)
    operator fun div(scalar: Float): Power = Power(rawValue / scalar)
    operator fun div(scalar: Int): Power =  Power((rawValue / scalar))
    operator fun div(scalar: Long): Power = Power((rawValue / scalar))

    operator fun rangeTo(other: Power): ClosedPowerRange = ClosedPowerRange(this, other)

    operator fun rangeUntil(other: Power) = OpenPowerRange(this, other)

    operator fun rem(other: Power): Power = Power((rawValue % other.rawValue))
    override fun compareTo(other: Power): Int = rawValue.compareTo(other.rawValue)

    @Deprecated("Conversions via .toNumber(unit) should no longer be used, if you require a type add it to the library ",
        ReplaceWith("use Unit.as/inXXX for direct conversion")
    )
    fun toInt(unit: PowerUnit): Int = (rawValue / unit.scale).toInt()
    @Deprecated("Conversions via .toNumber(unit) should no longer be used, if you require a type add it to the library ",
        ReplaceWith("use Unit.as/inXXX for direct conversion")
    )
    fun toLong(unit: PowerUnit): Long = (rawValue / unit.scale).toLong()
    @Deprecated("Conversions via .toNumber(unit) should no longer be used, if you require a type add it to the library ",
        ReplaceWith("use Unit.as/inXXX for direct conversion")
    )
    fun toDouble(unit: PowerUnit): Double = rawValue / unit.scale
    //--- Define conversions to "naked" number representations here.

    inline val inWatts: Double get() = rawValue / WATTS

    //--- Define different operations below:

    operator fun div(other: Power): Double = rawValue / other.rawValue
    operator fun times(duration: Duration): Energy = Energy(rawValue * duration.asSeconds)
    operator fun div(energy: Energy): Frequency = (inWatts / energy.inJoule).hertz

    companion object {
        val MAX = Power(Double.MAX_VALUE)
        val ZERO = Power(.0)
        const val WATTS = 1
    }
}

class ClosedPowerRange(override val start: Power, override val endInclusive: Power): ClosedRange<Power> {
    override fun contains(value: Power): Boolean {
        return value.rawValue in start.rawValue..endInclusive.rawValue
    }
}

class OpenPowerRange(override val start: Power, override val endExclusive: Power): OpenEndRange<Power> {
    override fun contains(value: Power): Boolean {
        return value.rawValue in start.rawValue..<endExclusive.rawValue
    }
}



fun Long.toPower(unit: PowerUnit): Power {
    return Power(this * unit.scale)
}
fun Double.toPower(unit: PowerUnit): Power {
    return Power(this * unit.scale)
}
fun Int.toPower(unit: PowerUnit): Power {
    return Power(this * unit.scale)
}
fun Float.toPower(unit: PowerUnit): Power {
    return Power(this * unit.scale)
}




@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
@JvmName("sumOfPower")
fun <T> Iterable<T>.sumOf(selector: (T) -> Power): Power {
    var sum = 0.toPower(PowerUnit.WATTS)
    for (element in this) {
        sum += selector(element)
    }
    return sum
}
fun Iterable<Power>.min() = minBy { it }
fun Iterable<Power>.max() = maxBy { it }
fun Iterable<Power>.average(): Power {
    var sum = 0.toPower(PowerUnit.WATTS)
    var count = 0
    for(element in this) {
        sum += element
        count++
    }
    return sum / count
}
fun abs(element: Power) = Power(element.rawValue.absoluteValue)

@Deprecated("Enum scale values should not be used, rather they should be defined as Unit.companion.ConstVals")
enum class PowerUnit(val scale: Double) {
    WATTS(1.0),
    KILOWATTS(1000.0)
}