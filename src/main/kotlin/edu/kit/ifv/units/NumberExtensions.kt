@file:Suppress("unused")
package edu.kit.ifv.units

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

val Int.Hz get()= Frequency(this * Frequency.HERTZ)
val Long.Hz get() = Frequency(this * Frequency.HERTZ)
val Float.Hz get() = Frequency(this * Frequency.HERTZ)
val Double.Hz get() = Frequency(this * Frequency.HERTZ)

val Int.hertz get()= Frequency(this * Frequency.HERTZ)
val Long.hertz get() = Frequency(this * Frequency.HERTZ)
val Float.hertz get() = Frequency(this * Frequency.HERTZ)
val Double.hertz get() = Frequency(this * Frequency.HERTZ)

// --- Area
operator fun Int.times(area: Area): Area = area * this
operator fun Long.times(area: Area): Area = area * this
operator fun Float.times(area: Area): Area = area * this
operator fun Double.times(area: Area): Area = area * this

val Int.squareMeters get()= Area(this * Area.SQUARE_METERS)
val Long.squareMeters get() = Area(this * Area.SQUARE_METERS)
val Float.squareMeters get() = Area(this * Area.SQUARE_METERS)
val Double.squareMeters get() = Area(this * Area.SQUARE_METERS)

// --- Currency
operator fun Int.times(currency: Currency): Currency = currency * this
operator fun Long.times(currency: Currency): Currency = currency * this
operator fun Float.times(currency: Currency): Currency = currency * this
operator fun Double.times(currency: Currency): Currency = currency * this

val Int.`€` get()= Currency(this * Currency.EUROS)
val Long.`€` get()= Currency(this * Currency.EUROS)
val Float.`€` get()= Currency(this * Currency.EUROS)
val Double.`€` get()= Currency(this * Currency.EUROS)

val Int.euros get()= Currency(this * Currency.EUROS)
val Long.euros get()= Currency(this * Currency.EUROS)
val Float.euros get()= Currency(this * Currency.EUROS)
val Double.euros get()= Currency(this * Currency.EUROS)

// --- Distance
operator fun Int.times(distance: Distance): Distance = distance * this
operator fun Long.times(distance: Distance): Distance = distance * this
operator fun Float.times(distance: Distance): Distance = distance * this
operator fun Double.times(distance: Distance): Distance = distance * this

val Int.meters get()= Distance(this * Distance.METERS)
val Long.meters get() = Distance(this * Distance.METERS)
val Float.meters get() = Distance(this * Distance.METERS)
val Double.meters get() = Distance(this * Distance.METERS)

val Int.kilometers get()=    Distance(this * Distance.KILOMETERS)
val Long.kilometers get()=   Distance(this * Distance.KILOMETERS)
val Float.kilometers get()=  Distance(this * Distance.KILOMETERS)
val Double.kilometers get()= Distance(this * Distance.KILOMETERS)


// --- Efficiency/Force
operator fun Int.times(force: Force): Force = force * this
operator fun Long.times(force: Force): Force = force * this
operator fun Float.times(force: Force): Force = force * this
operator fun Double.times(force: Force): Force = force * this

val Int.newton     get()= Force(this.toDouble())
val Long.newton    get()= Force(this.toDouble())
val Float.newton   get()= Force(this.toDouble())
val Double.newton  get()= Force(this)

// --- Energy
operator fun Int.times(energy: Energy): Energy = energy * this
operator fun Long.times(energy: Energy): Energy = energy * this
operator fun Float.times(energy: Energy): Energy = energy * this
operator fun Double.times(energy: Energy): Energy = energy * this

val Int.joule     get()= Energy(this * Energy.JOULE)
val Long.joule    get()= Energy(this * Energy.JOULE)
val Float.joule   get()= Energy(this * Energy.JOULE)
val Double.joule  get()= Energy(this * Energy.JOULE)

val Int.kilowatthours     get()= Energy(this * Energy.KILOWATTHOUR)
val Long.kilowatthours    get()= Energy(this * Energy.KILOWATTHOUR)
val Float.kilowatthours   get()= Energy(this * Energy.KILOWATTHOUR)
val Double.kilowatthours  get()= Energy(this * Energy.KILOWATTHOUR)

val Int.litersBenzene     get()= Energy(this * Energy.BENZENE_EQUIVALENT)
val Long.litersBenzene    get()= Energy(this * Energy.BENZENE_EQUIVALENT)
val Float.litersBenzene   get()= Energy(this * Energy.BENZENE_EQUIVALENT)
val Double.litersBenzene  get()= Energy(this * Energy.BENZENE_EQUIVALENT)

// --- Mass
operator fun Int.times(mass: Mass): Mass = mass * this
operator fun Long.times(mass: Mass): Mass = mass * this
operator fun Float.times(mass: Mass): Mass = mass * this
operator fun Double.times(mass: Mass): Mass = mass * this

val Int.grams get()= Mass(this * Mass.GRAM)
val Long.grams get()= Mass(this * Mass.GRAM)
val Float.grams get()= Mass((this * Mass.GRAM).toLong())
val Double.grams get()= Mass((this * Mass.GRAM).toLong())

val Int.kilograms get()= Mass(this * Mass.KILOGRAM)
val Long.kilograms get()= Mass(this * Mass.KILOGRAM)
val Float.kilograms get()= Mass((this * Mass.KILOGRAM).toLong())
val Double.kilograms get()= Mass((this * Mass.KILOGRAM).toLong())

// --- Power
operator fun Int.times(power: Power): Power = power * this
operator fun Long.times(power: Power): Power = power * this
operator fun Float.times(power: Power): Power = power * this
operator fun Double.times(power: Power): Power = power * this

val Int.watts get()= Power((this * Power.WATTS).toDouble())
val Long.watts get()= Power((this * Power.WATTS).toDouble())
val Float.watts get()= Power((this * Power.WATTS).toDouble())
val Double.watts get()= Power((this * Power.WATTS))

// --- Speed
operator fun Int.times(speed: Speed): Speed = speed * this
operator fun Long.times(speed: Speed): Speed = speed * this
operator fun Float.times(speed: Speed): Speed = speed * this
operator fun Double.times(speed: Speed): Speed = speed * this

val Int.kmh get()= Speed(this * Speed.KILOMETER_PER_HOUR)
val Long.kmh get()= Speed(this * Speed.KILOMETER_PER_HOUR)
val Float.kmh get()= Speed(this * Speed.KILOMETER_PER_HOUR)
val Double.kmh get()= Speed(this * Speed.KILOMETER_PER_HOUR)

val Int.metersPerSecond get()= Speed(this * Speed.METER_PER_SECOND)
val Long.metersPerSecond get()= Speed(this * Speed.METER_PER_SECOND)
val Float.metersPerSecond get()= Speed(this * Speed.METER_PER_SECOND)
val Double.metersPerSecond get()= Speed(this * Speed.METER_PER_SECOND)

// --- Acceleration
operator fun Int.times(acceleration: Acceleration): Acceleration = acceleration * this
operator fun Long.times(acceleration: Acceleration): Acceleration = acceleration * this
operator fun Float.times(acceleration: Acceleration): Acceleration = acceleration * this
operator fun Double.times(acceleration: Acceleration): Acceleration = acceleration * this

val Int.metersPerSecondSquared get()= Acceleration(this * Acceleration.METER_PER_SECOND_SQUARED)
val Long.metersPerSecondSquared get()= Acceleration(this * Acceleration.METER_PER_SECOND_SQUARED)
val Float.metersPerSecondSquared get()= Acceleration(this * Acceleration.METER_PER_SECOND_SQUARED)
val Double.metersPerSecondSquared get()= Acceleration(this * Acceleration.METER_PER_SECOND_SQUARED)

// --- Impulse
operator fun Int.times(impulse: Impulse): Impulse = impulse * this
operator fun Long.times(impulse: Impulse): Impulse = impulse * this
operator fun Float.times(impulse: Impulse): Impulse = impulse * this
operator fun Double.times(impulse: Impulse): Impulse = impulse * this

val Int.newtonSeconds get()= Impulse(this * Impulse.Newton_Seconds)
val Long.newtonSeconds get()= Impulse(this * Impulse.Newton_Seconds)
val Float.newtonSeconds get()= Impulse(this * Impulse.Newton_Seconds)
val Double.newtonSeconds get()= Impulse(this * Impulse.Newton_Seconds)


// --- Temperature
val Int.celsius get()= this.toTemperature(TemperatureUnit.CELSIUS)
val Long.celsius get()= this.toTemperature(TemperatureUnit.CELSIUS)
val Float.celsius get()= this.toTemperature(TemperatureUnit.CELSIUS)
val Double.celsius get()= this.toTemperature(TemperatureUnit.CELSIUS)

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

// -- SquareDuration
operator fun Int.times(squareDuration: SquareDuration): SquareDuration = squareDuration * this
operator fun Long.times(squareDuration: SquareDuration): SquareDuration = squareDuration * this
operator fun Float.times(squareDuration: SquareDuration): SquareDuration = squareDuration * this
operator fun Double.times(squareDuration: SquareDuration): SquareDuration = squareDuration * this

val Int.squareSeconds: SquareDuration get() = SquareDuration(this * SquareDuration.SQUARE_SECONDS)
val Long.squareSeconds: SquareDuration get() = SquareDuration(this * SquareDuration.SQUARE_SECONDS)
val Float.squareSeconds: SquareDuration get() = SquareDuration(this * SquareDuration.SQUARE_SECONDS)
val Double.squareSeconds: SquareDuration get() = SquareDuration(this * SquareDuration.SQUARE_SECONDS)

// -- Cubic Duration
operator fun Int.times(cubicDuration: CubicDuration): CubicDuration = cubicDuration * this
operator fun Long.times(cubicDuration: CubicDuration): CubicDuration = cubicDuration * this
operator fun Float.times(cubicDuration: CubicDuration): CubicDuration = cubicDuration * this
operator fun Double.times(cubicDuration: CubicDuration): CubicDuration = cubicDuration * this

val Int.cubicSeconds: CubicDuration get() = CubicDuration(this * CubicDuration.CUBIC_SECONDS)
val Long.cubicSeconds: CubicDuration get() = CubicDuration(this * CubicDuration.CUBIC_SECONDS)
val Float.cubicSeconds: CubicDuration get() = CubicDuration(this * CubicDuration.CUBIC_SECONDS)
val Double.cubicSeconds: CubicDuration get() = CubicDuration(this * CubicDuration.CUBIC_SECONDS)
