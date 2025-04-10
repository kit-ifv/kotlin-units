package units

import kotlin.time.Duration
import kotlin.time.DurationUnit

/**
 * inSeconds and the other functions are marked as shadowing a member. Which is true because inSeconds
 * is part of a deprecated experimental API. Since I don' like the warning the methods are now calles asMinutes
 * and if someone is really annoyed by this naming convention - just add "inSeconds, ..." here
 */

inline val Duration.asMinutes: Double get() = this.toDouble(DurationUnit.MINUTES)
inline val Duration.asSeconds: Double get() = this.toDouble(DurationUnit.SECONDS)
inline val Duration.asHours: Double get() = this.toDouble(DurationUnit.HOURS)


