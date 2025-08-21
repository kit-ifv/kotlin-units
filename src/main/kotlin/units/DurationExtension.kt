package units

import kotlin.time.Duration
import kotlin.time.DurationUnit

/**
 * In this package conversion to raw numbers are usually called 'in-X' ('inSeconds', 'inMiles'...). Since duration is a
 * native kotlin unit, this naming is sadly marked as shadowing a member. Which is true because inSeconds  is part of a
 * deprecated experimental API. Since I don't like the warning, the methods are now called 'asMinutes'
 * and if someone is really annoyed by this naming convention - just add "inSeconds, ..." here
 */

/**
 * for full support, like OutOfBoundsUnit-operations use `DurationWrapper` instead of Duration.
 */
inline val Duration.asMinutes: Double get() = toDouble(DurationUnit.MINUTES)
inline val Duration.asSeconds: Double get() = toDouble(DurationUnit.SECONDS)
inline val Duration.asHours: Double get() = toDouble(DurationUnit.HOURS)

/**
 * @return A DurationWrapper of this Duration.
 */
inline val Duration.wrap: DurationWrapper get() = DurationWrapper(this)

/**
 * Inconveniently Kotlin has a Duration implementation already. To ensure interoperability with the entire library,
 * we will use a wrapper wherever needed. Since we don't want to break the API, we keep using the kotlin Duration
 * as much as possible.
 */
class DurationWrapper(val duration: Duration): FlexibleUnit {
    inline val asMinutes: Double get() = duration.toDouble(DurationUnit.MINUTES)
    inline val asSeconds: Double get() = duration.toDouble(DurationUnit.SECONDS)
    inline val asHours: Double get() = duration.toDouble(DurationUnit.HOURS)

    //--- Define different operations below:
    operator fun times(other: DurationWrapper): SquareDuration = (this.asSeconds * other.asSeconds).square_seconds
    operator fun times(other: Duration): SquareDuration = (this.asSeconds * other.asSeconds).square_seconds
    operator fun div(frequency: Frequency): SquareDuration = (this.asSeconds / frequency.inHertz).square_seconds
    operator fun times(squareDuration: SquareDuration): CubicDuration = (this.asSeconds * squareDuration.inSquareSeconds).cubic_seconds
    override fun toOutOfBoundsUnit(): OutOfBoundsUnit {
        return OutOfBoundsUnit(
            asSeconds,
            PhysicsUnit(0,1,0))
    }
}

//--- Define different operations below:

operator fun Duration.times(other: Duration): SquareDuration = SquareDuration(this.asSeconds * other.asSeconds)
operator fun Duration.times(other: DurationWrapper): SquareDuration = (this.asSeconds * other.asSeconds).square_seconds
operator fun Duration.times(squareDuration: SquareDuration): CubicDuration = (this.asSeconds * squareDuration.inSquareSeconds).cubic_seconds
operator fun Duration.div(frequency: Frequency): SquareDuration = (this.asSeconds / frequency.inHertz).square_seconds