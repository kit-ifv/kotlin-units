package units

import kotlin.time.Duration

@JvmInline
value class Acceleration(val rawValue: Double): Comparable<Acceleration>, FlexibleUnit {
    operator fun unaryMinus(): Acceleration = Acceleration(-rawValue)
    operator fun plus(other: Acceleration) = Acceleration(rawValue + other.rawValue)
    operator fun minus(other: Acceleration) = Acceleration(rawValue - other.rawValue)
    operator fun times(scalar: Double) = Acceleration(rawValue * scalar)
    operator fun times(scalar: Float) = Acceleration(rawValue * scalar)
    operator fun times(scalar: Int) = Acceleration((rawValue * scalar))
    operator fun times(scalar: Long)  = Acceleration((rawValue * scalar))

    operator fun div(scalar: Double): Acceleration = Acceleration(rawValue / scalar)
    operator fun div(scalar: Float): Acceleration = Acceleration(rawValue / scalar)
    operator fun div(scalar: Int): Acceleration =  Acceleration((rawValue / scalar))
    operator fun div(scalar: Long): Acceleration = Acceleration((rawValue / scalar))

    operator fun rangeTo(other: Acceleration): ClosedAccelerationRange = ClosedAccelerationRange(this, other)

    operator fun rangeUntil(other: Acceleration) = OpenAccelerationRange(this, other)

    operator fun rem(other: Acceleration): Acceleration = Acceleration((rawValue % other.rawValue))
    override fun compareTo(other: Acceleration): Int = rawValue.compareTo(other.rawValue)

    //--- Define conversions to "naked" number representations here.

    inline val inMetersPerSecondsSquared: Double get() = rawValue / METER_PER_SECOND_SQUARED
    inline val inEarthGravityGs: Double get() = rawValue / GRAVITY_EARTH

    //--- Define different operations below:
    operator fun div(other: Acceleration): Double = rawValue / other.rawValue
    operator fun times(duration: Duration) = Speed(rawValue * duration.asSeconds)
    operator fun times(mass: Mass): Newton = (inMetersPerSecondsSquared * mass.inKilograms).newton
    operator fun times(impulse: Impulse): Power
        = (inMetersPerSecondsSquared * impulse.inNewtonSeconds).watts
    operator fun times(squareDuration: SquareDuration): Distance
        = (inMetersPerSecondsSquared * squareDuration.inSquareSeconds).meters
    operator fun div(frequency: Frequency): Speed
        = (this.inMetersPerSecondsSquared * frequency.inHertz).meters_per_second
    operator fun div(speed: Speed): Frequency
        = (this.inMetersPerSecondsSquared * speed.inMetersPerSecond).hertz

    override fun toOutOfBoundsUnit(): OutOfBoundsUnit {
        return OutOfBoundsUnit(inMetersPerSecondsSquared, PhysicsUnit(1,-2,0))
    }


    companion object {

        val MAX = Acceleration(Double.MAX_VALUE)
        val ZERO = Acceleration(.0)
        const val METER_PER_SECOND_SQUARED = 1.0
        const val GRAVITY_EARTH = 9.81

    }
}

class ClosedAccelerationRange(override val start: Acceleration, override val endInclusive: Acceleration): ClosedRange<Acceleration> {
    override fun contains(value: Acceleration): Boolean {
        return value.rawValue in start.rawValue..endInclusive.rawValue
    }
}

class OpenAccelerationRange(override val start: Acceleration, override val endExclusive: Acceleration): OpenEndRange<Acceleration> {
    override fun contains(value: Acceleration): Boolean {
        return value.rawValue in start.rawValue..<endExclusive.rawValue
    }
}
