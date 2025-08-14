package units

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds

/**
 * The rawValue is in cubic seconds.
 */
@JvmInline
value class CubicDuration internal constructor(val rawValue: Double): Comparable<CubicDuration> {

    operator fun unaryMinus(): CubicDuration = CubicDuration(-rawValue)
    operator fun plus(other: CubicDuration) = CubicDuration(rawValue + other.rawValue)
    operator fun minus(other: CubicDuration) = CubicDuration(rawValue - other.rawValue)
    operator fun times(scalar: Double) = CubicDuration(rawValue * scalar)
    operator fun times(scalar: Float) = CubicDuration(rawValue * scalar)
    operator fun times(scalar: Int) = CubicDuration((rawValue * scalar))
    operator fun times(scalar: Long)  = CubicDuration((rawValue * scalar))

    operator fun div(scalar: Double): CubicDuration = CubicDuration(rawValue / scalar)
    operator fun div(scalar: Float): CubicDuration = CubicDuration(rawValue / scalar)
    operator fun div(scalar: Int): CubicDuration =  CubicDuration((rawValue / scalar))
    operator fun div(scalar: Long): CubicDuration = CubicDuration((rawValue / scalar))


    operator fun rem(other: CubicDuration): CubicDuration = CubicDuration((rawValue % other.rawValue))
    override fun compareTo(other: CubicDuration): Int = rawValue.compareTo(other.rawValue)

    //--- Define conversions to "naked" number representations here.

    /**
     * amount of cubic seconds
     */
    fun toDouble(): Double = rawValue

    //--- Define different operations below:
    operator fun div(other: CubicDuration): Double = rawValue / other.rawValue
    operator fun times(duration: Duration) = (rawValue * duration.asSeconds).seconds
    operator fun div(duration: Duration): SquareDuration = (rawValue / duration.asSeconds).squareSeconds

    companion object {

        val MAX = CubicDuration(Double.MAX_VALUE)
        val ZERO = CubicDuration(.0)

        const val CUBIC_SECONDS = 1.0
        const val CUBIC_MINUTES = 60*60*60
        const val CUBIC_HOURS = 3600*3600*3600
    }
}