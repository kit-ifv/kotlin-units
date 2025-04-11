package units

import kotlin.math.roundToLong
import kotlin.time.Duration

/**
 * In this file all the functions that extend upon the primitive numbers should be added.
 */

// --- Frequency
operator fun Int.times(frequency: Frequency): Frequency = frequency * this
operator fun Long.times(frequency: Frequency): Frequency = frequency * this
operator fun Float.times(frequency: Frequency): Frequency = frequency * this
operator fun Double.times(frequency: Frequency): Frequency = frequency * this

operator fun Int.div(duration: Duration) = Frequency(this / duration.asSeconds)
operator fun Long.div(duration: Duration) = Frequency(this / duration.asSeconds)
operator fun Float.div(duration: Duration) = Frequency(this / duration.asSeconds)
operator fun Double.div(duration: Duration) = Frequency(this / duration.asSeconds)

// --- Area
operator fun Int.times(area: Area): Area = area * this
operator fun Long.times(area: Area): Area = area * this
operator fun Float.times(area: Area): Area = area * this
operator fun Double.times(area: Area): Area = area * this

// --- Currency
operator fun Int.times(currency: Currency): Currency = currency * this
operator fun Long.times(currency: Currency): Currency = currency * this
operator fun Float.times(currency: Currency): Currency = currency * this
operator fun Double.times(currency: Currency): Currency = currency * this

inline val Int.`€` get()= Currency(this * Currency.EUROS)
inline val Long.`€` get()= Currency(this * Currency.EUROS)
inline val Float.`€` get()= Currency(this * Currency.EUROS)
inline val Double.`€` get()= Currency(this * Currency.EUROS)

inline val Int.euros get()= Currency(this * Currency.EUROS)
inline val Long.euros get()= Currency(this * Currency.EUROS)
inline val Float.euros get()= Currency(this * Currency.EUROS)
inline val Double.euros get()= Currency(this * Currency.EUROS)

// --- Distance
inline operator fun Int.times(distance: Distance): Distance = distance * this
inline operator fun Long.times(distance: Distance): Distance = distance * this
inline operator fun Float.times(distance: Distance): Distance = distance * this
inline operator fun Double.times(distance: Distance): Distance = distance * this

inline val Int.meters get()= Distance(this * Distance.METERS)
inline val Long.meters get() = Distance(this * Distance.METERS)
inline val Float.meters get() = Distance(this * Distance.METERS)
inline val Double.meters get() = Distance(this * Distance.METERS)

inline val Int.kilometers get()=    Distance(this * Distance.KILOMETERS)
inline val Long.kilometers get()=   Distance(this * Distance.KILOMETERS)
inline val Float.kilometers get()=  Distance(this * Distance.KILOMETERS)
inline val Double.kilometers get()= Distance(this * Distance.KILOMETERS)


// --- Efficiency
operator fun Int.times(efficiency: Efficiency): Efficiency = efficiency * this
operator fun Long.times(efficiency: Efficiency): Efficiency = efficiency * this
operator fun Float.times(efficiency: Efficiency): Efficiency = efficiency * this
operator fun Double.times(efficiency: Efficiency): Efficiency = efficiency * this

// --- Energy
operator fun Int.times(energy: Energy): Energy = energy * this
operator fun Long.times(energy: Energy): Energy = energy * this
operator fun Float.times(energy: Energy): Energy = energy * this
operator fun Double.times(energy: Energy): Energy = energy * this

inline val Int.joule     get()= Energy(this * Energy.JOULE)
inline val Long.joule    get()= Energy(this * Energy.JOULE)
inline val Float.joule   get()= Energy(this * Energy.JOULE)
inline val Double.joule  get()= Energy(this * Energy.JOULE)

inline val Int.kilowatthours     get()= Energy(this * Energy.KILOWATTHOUR)
inline val Long.kilowatthours    get()= Energy(this * Energy.KILOWATTHOUR)
inline val Float.kilowatthours   get()= Energy(this * Energy.KILOWATTHOUR)
inline val Double.kilowatthours  get()= Energy(this * Energy.KILOWATTHOUR)

inline val Int.litersBenzene     get()= Energy(this * Energy.BENZENE_EQUIVALENT)
inline val Long.litersBenzene    get()= Energy(this * Energy.BENZENE_EQUIVALENT)
inline val Float.litersBenzene   get()= Energy(this * Energy.BENZENE_EQUIVALENT)
inline val Double.litersBenzene  get()= Energy(this * Energy.BENZENE_EQUIVALENT)

// --- Mass
operator fun Int.times(mass: Mass): Mass = mass * this
operator fun Long.times(mass: Mass): Mass = mass * this
operator fun Float.times(mass: Mass): Mass = mass * this
operator fun Double.times(mass: Mass): Mass = mass * this

inline val Int.grams get()= this.toMass(MassUnit.GRAM)
inline val Long.grams get()= this.toMass(MassUnit.GRAM)
inline val Float.grams get()= this.toMass(MassUnit.GRAM)
inline val Double.grams get()= this.toMass(MassUnit.GRAM)

inline val Int.kilograms get()= this.toMass(MassUnit.KILOGRAM)
inline val Long.kilograms get()= this.toMass(MassUnit.KILOGRAM)
inline val Float.kilograms get()= this.toMass(MassUnit.KILOGRAM)
inline val Double.kilograms get()= this.toMass(MassUnit.KILOGRAM)

// --- Power
operator fun Int.times(power: Power): Power = power * this
operator fun Long.times(power: Power): Power = power * this
operator fun Float.times(power: Power): Power = power * this
operator fun Double.times(power: Power): Power = power * this

inline val Int.watts get()= this.toPower(PowerUnit.WATTS)
inline val Long.watts get()= this.toPower(PowerUnit.WATTS)
inline val Float.watts get()= this.toPower(PowerUnit.WATTS)
inline val Double.watts get()= this.toPower(PowerUnit.WATTS)

// --- Speed
operator fun Int.times(speed: Speed): Speed = speed * this
operator fun Long.times(speed: Speed): Speed = speed * this
operator fun Float.times(speed: Speed): Speed = speed * this
operator fun Double.times(speed: Speed): Speed = speed * this

inline val Int.kmh get()= Speed(this * Speed.KILOMETER_PER_HOUR)
inline val Long.kmh get()= Speed(this * Speed.KILOMETER_PER_HOUR)
inline val Float.kmh get()= Speed(this * Speed.KILOMETER_PER_HOUR)
inline val Double.kmh get()= Speed(this * Speed.KILOMETER_PER_HOUR)

// --- Temperature
inline val Int.celsius get()= this.toTemperature(TemperatureUnit.CELSIUS)
inline val Long.celsius get()= this.toTemperature(TemperatureUnit.CELSIUS)
inline val Float.celsius get()= this.toTemperature(TemperatureUnit.CELSIUS)
inline val Double.celsius get()= this.toTemperature(TemperatureUnit.CELSIUS)

// --- Volume
operator fun Int.times(volume: Volume): Volume = volume * this
operator fun Long.times(volume: Volume): Volume = volume * this
operator fun Float.times(volume: Volume): Volume = volume * this
operator fun Double.times(volume: Volume): Volume = volume * this

val Int.liters: Volume get() = Volume(this * Volume.LITER)
val Long.liters: Volume get() = Volume(this * Volume.LITER)
val Float.liters: Volume get() = Volume(this * Volume.LITER)
val Double.liters: Volume get() = Volume(this * Volume.LITER)

val Int.cubicMeters: Volume get() = Volume(this * Volume.CUBIC_METER)
val Long.cubicMeters: Volume get() = Volume(this * Volume.CUBIC_METER)
val Float.cubicMeters: Volume get() = Volume(this * Volume.CUBIC_METER)
val Double.cubicMeters: Volume get() = Volume(this * Volume.CUBIC_METER)
