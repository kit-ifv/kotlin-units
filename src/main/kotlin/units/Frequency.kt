package units

import kotlin.experimental.ExperimentalTypeInference
import kotlin.math.absoluteValue
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

@JvmInline
value class Frequency internal constructor(val rawValue: Double) : Comparable<Frequency>, FlexibleUnit {
    operator fun times(scalar: Double) = Frequency(rawValue * scalar)
    operator fun times(scalar: Float) = Frequency(rawValue * scalar)
    operator fun times(scalar: Int) = Frequency((rawValue * scalar))
    operator fun times(scalar: Long)  = Frequency((rawValue * scalar))

    operator fun div(scalar: Double): Frequency = Frequency(rawValue / scalar)
    operator fun div(scalar: Float): Frequency = Frequency(rawValue / scalar)
    operator fun div(scalar: Int): Frequency =  Frequency((rawValue / scalar))
    operator fun div(scalar: Long): Frequency = Frequency((rawValue / scalar))
    override fun compareTo(other: Frequency): Int = rawValue.compareTo(other.rawValue)

    //--- Define conversions to "naked" number representations here.

    inline val inHertz: Double get() = rawValue / HERTZ

    //--- Define different operations below:
    operator fun times(other: Distance): Speed = Speed(rawValue * other.inMeters)
    operator fun times(squareDuration: SquareDuration): Duration = (inHertz * squareDuration.inSquareSeconds).seconds
    operator fun times(cubicDuration: CubicDuration): SquareDuration
        = (inHertz * cubicDuration.inCubicSeconds).square_seconds
    operator fun times(energy: Energy): Power
        = (inHertz * energy.inJoule).watts
    operator fun times(impulse: Impulse): Newton
        = (inHertz * impulse.inNewtonSeconds).newton
    operator fun times(speed: Speed): Acceleration
        = (inHertz * speed.inMetersPerSecond).meters_per_second_squared

    operator fun div(other: Frequency): Double = rawValue / other.rawValue


    override fun toOutOfBoundsUnit(): OutOfBoundsUnit {
        return OutOfBoundsUnit(inHertz, PhysicsUnit(0,-1,0))
    }

    companion object {

        val MAX = Frequency(Double.MAX_VALUE)
        val ZERO = Frequency(.0)
        const val HERTZ = 1.0
    }
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
@OptIn(ExperimentalTypeInference::class)
@OverloadResolutionByLambdaReturnType
@JvmName("sumOfFrequency")
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
