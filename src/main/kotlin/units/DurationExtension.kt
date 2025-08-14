package units

import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.DurationUnit

/**
 * In this package conversion to raw numbers are usually called inSeconds  ('in-X'). Since duration is a native kotlin
 * unit, this naming is sadly marked as shadowing a member. Which is true because inSeconds  is part of a deprecated
 * experimental API. Since I don't like the warning, the methods are now called 'asMinutes'
 * and if someone is really annoyed by this naming convention - just add "inSeconds, ..." here
 */

inline val Duration.asMinutes: Double get() = this.toDouble(DurationUnit.MINUTES)
inline val Duration.asSeconds: Double get() = this.toDouble(DurationUnit.SECONDS)
inline val Duration.asHours: Double get() = this.toDouble(DurationUnit.HOURS)

operator fun Duration.times(other: Duration): SquareDuration = (this.asSeconds * other.asSeconds).squareSeconds