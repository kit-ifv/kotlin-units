package units

import kotlin.math.absoluteValue

@JvmInline
value class Frequency(val rawValue: Double) : Comparable<Frequency> {
    operator fun times(scalar: Double) = Frequency(rawValue * scalar)
    operator fun times(scalar: Float) = Frequency(rawValue * scalar)
    operator fun times(scalar: Int) = Frequency((rawValue * scalar))
    operator fun times(scalar: Long)  = Frequency((rawValue * scalar))

    operator fun div(scalar: Double): Frequency = Frequency(rawValue / scalar)
    operator fun div(scalar: Float): Frequency = Frequency(rawValue / scalar)
    operator fun div(scalar: Int): Frequency =  Frequency((rawValue / scalar))
    operator fun div(scalar: Long): Frequency = Frequency((rawValue / scalar))
    override fun compareTo(other: Frequency): Int = rawValue.compareTo(other.rawValue)
}

class ClosedFrequencyRange(override val start: Frequency, override val endInclusive: Frequency): ClosedRange<Frequency> {
    override fun contains(value: Frequency): Boolean {
        return value.rawValue in start.rawValue..endInclusive.rawValue
    }
}

class OpenFrequencyRange(override val start: Frequency, override val endExclusive: Frequency): OpenEndRange<Frequency> {
    override fun contains(value: Frequency): Boolean {
        return value.rawValue in start.rawValue..<endExclusive.rawValue
    }
}





fun Long.toFrequency(unit: FrequencyUnit): Frequency {
    return Frequency(this * unit.scale)
}
fun Double.toFrequency(unit: FrequencyUnit): Frequency {
    return Frequency(this * unit.scale)
}
fun Int.toFrequency(unit: FrequencyUnit): Frequency {
    return Frequency(this * unit.scale)
}
fun Float.toFrequency(unit: FrequencyUnit): Frequency {
    return Frequency(this * unit.scale)
}
fun <T> Iterable<T>.sumOf(selector: (T) -> Frequency): Frequency {
    var sum = 0.0
    for (element in this) {
        sum += selector(element).rawValue
    }
    return Frequency(sum)
}
fun Iterable<Frequency>.min() = minBy { it }
fun Iterable<Frequency>.max() = maxBy { it }
fun Iterable<Frequency>.average(): Frequency {
    var sum = 0.0
    var count = 0
    for(element in this) {
        sum += element.rawValue
        count++
    }
    return Frequency(sum / count)
}
fun abs(element: Frequency) = Frequency(element.rawValue.absoluteValue)

enum class FrequencyUnit(val scale: Double) {
    HERTZ(1.0),

}