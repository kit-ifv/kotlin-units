@file:Suppress("unused")
package edu.kit.ifv.units

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
class DurationWrapper(val duration: Duration): FlexibleUnit, Comparable<DurationWrapper> {
    inline val asMinutes: Double get() = duration.toDouble(DurationUnit.MINUTES)
    inline val asSeconds: Double get() = duration.toDouble(DurationUnit.SECONDS)
    inline val asHours: Double get() = duration.toDouble(DurationUnit.HOURS)

    //--- Define different operations below:
    operator fun times(other: Duration): SquareDuration = SquareDuration(this.asSeconds * other.asSeconds)
    operator fun times(other: DurationWrapper): SquareDuration = (this.asSeconds * other.asSeconds).squareSeconds
    operator fun times(squareDuration: SquareDuration): CubicDuration
            = (this.asSeconds * squareDuration.inSquareSeconds).cubicSeconds
    operator fun times(acceleration: Acceleration): Speed
            = (this.asSeconds * acceleration.inMetersPerSecondsSquared).metersPerSecond
    operator fun times(power: Power): Energy
            = (this.asSeconds * power.inWatts).joule
    operator fun times(force: Force): Impulse
            = (this.asSeconds * force.inNewton).newtonSeconds
    operator fun times(speed: Speed): Distance
            = (this.asSeconds * speed.inMetersPerSecond).meters

    operator fun div(frequency: Frequency): SquareDuration = (this.asSeconds / frequency.inHertz).squareSeconds
    operator fun div(squareDuration: SquareDuration): Frequency
            = (this.asSeconds / squareDuration.inSquareSeconds).hertz

    override fun toOutOfBoundsUnit(): OutOfBoundsUnit {
        return OutOfBoundsUnit(
            asSeconds,
            PhysicsUnit(0,1,0))
    }

    override fun compareTo(other: DurationWrapper): Int {
        return this.duration.compareTo(other.duration)
    }

    operator fun DurationWrapper.rangeTo(other: DurationWrapper): ClosedDurationWrapperRange = ClosedDurationWrapperRange(this, other)

    operator fun DurationWrapper.rangeUntil(other: DurationWrapper) = OpenDurationWrapperRange(this, other)
}

class ClosedDurationWrapperRange(override val start: DurationWrapper, override val endInclusive: DurationWrapper): ClosedRange<DurationWrapper> {
    override fun contains(value: DurationWrapper): Boolean {
        return value.asSeconds in start.asSeconds..endInclusive.asSeconds
    }
}

class OpenDurationWrapperRange(override val start: DurationWrapper, override val endExclusive: DurationWrapper): OpenEndRange<DurationWrapper> {
    override fun contains(value: DurationWrapper): Boolean {
        return value.asSeconds in start.asSeconds..<endExclusive.asSeconds
    }
}


//--- Define different operations below:

operator fun Duration.times(other: Duration): SquareDuration = SquareDuration(this.asSeconds * other.asSeconds)
operator fun Duration.times(other: DurationWrapper): SquareDuration = (this.asSeconds * other.asSeconds).squareSeconds
operator fun Duration.times(squareDuration: SquareDuration): CubicDuration
    = (this.asSeconds * squareDuration.inSquareSeconds).cubicSeconds
operator fun Duration.times(acceleration: Acceleration): Speed
    = (this.asSeconds * acceleration.inMetersPerSecondsSquared).metersPerSecond
operator fun Duration.times(power: Power): Energy
    = (this.asSeconds * power.inWatts).joule
operator fun Duration.times(force: Force): Impulse
    = (this.asSeconds * force.inNewton).newtonSeconds
operator fun Duration.times(speed: Speed): Distance
    = (this.asSeconds * speed.inMetersPerSecond).meters

operator fun Duration.div(frequency: Frequency): SquareDuration = (this.asSeconds / frequency.inHertz).squareSeconds
operator fun Duration.div(squareDuration: SquareDuration): Frequency
= (this.asSeconds / squareDuration.inSquareSeconds).hertz


// This is a bit messy...

fun abs(element: Duration) = element.absoluteValue

fun min(a: Duration, b: Duration): Duration {
    if (a < b) return a
    return b
}

fun max(a: Duration, b: Duration): Duration {
    if (a > b) return a
    return b
}

fun Duration.coerceIn(min: Duration, max: Duration): Duration {
    if(this < min) return min
    if(this > max) return max
    return this
}

fun Duration.coerceAtLeast(min: Duration): Duration {
    return  max(this, min)
}

fun Duration.coerceAtMost(max: Duration): Duration {
    return min(this, max)
}

fun min(a: DurationWrapper, b: DurationWrapper): DurationWrapper {
    if (a.duration < b.duration) return a
    return b
}

fun max(a: DurationWrapper, b: DurationWrapper): DurationWrapper {
    if (a > b) return a
    return b
}

fun DurationWrapper.coerceIn(min: DurationWrapper, max: DurationWrapper): DurationWrapper {
    if(this < min) return min
    if(this > max) return max
    return this
}

fun DurationWrapper.coerceAtLeast(min: DurationWrapper): DurationWrapper {
    return  max(this, min)
}

fun DurationWrapper.coerceAtMost(max: DurationWrapper): DurationWrapper {
    return min(this, max)
}

fun abs(element: DurationWrapper) = DurationWrapper(element.duration.absoluteValue)


operator fun Duration.rangeTo(other: Duration): ClosedDurationRange = ClosedDurationRange(this, other)

operator fun Duration.rangeUntil(other: Duration) = OpenDurationRange(this, other)

class ClosedDurationRange(override val start: Duration, override val endInclusive: Duration): ClosedRange<Duration> {
    override fun contains(value: Duration): Boolean {
        return value.asSeconds in start.asSeconds..endInclusive.asSeconds
    }
}

class OpenDurationRange(override val start: Duration, override val endExclusive: Duration): OpenEndRange<Duration> {
    override fun contains(value: Duration): Boolean {
        return value.asSeconds in start.asSeconds..<endExclusive.asSeconds
    }
}