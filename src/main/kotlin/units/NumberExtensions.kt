package units

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


// --- Efficiency
operator fun Int.times(newton: Newton): Newton = newton * this
operator fun Long.times(newton: Newton): Newton = newton * this
operator fun Float.times(newton: Newton): Newton = newton * this
operator fun Double.times(newton: Newton): Newton = newton * this

val Int.newton     get()= Newton(this.toDouble())
val Long.newton    get()= Newton(this.toDouble())
val Float.newton   get()= Newton(this.toDouble())
val Double.newton  get()= Newton(this)

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

val Int.grams get()= this.toMass(MassUnit.GRAM)
val Long.grams get()= this.toMass(MassUnit.GRAM)
val Float.grams get()= this.toMass(MassUnit.GRAM)
val Double.grams get()= this.toMass(MassUnit.GRAM)

val Int.kilograms get()= this.toMass(MassUnit.KILOGRAM)
val Long.kilograms get()= this.toMass(MassUnit.KILOGRAM)
val Float.kilograms get()= this.toMass(MassUnit.KILOGRAM)
val Double.kilograms get()= this.toMass(MassUnit.KILOGRAM)

// --- Power
operator fun Int.times(power: Power): Power = power * this
operator fun Long.times(power: Power): Power = power * this
operator fun Float.times(power: Power): Power = power * this
operator fun Double.times(power: Power): Power = power * this

val Int.watts get()= this.toPower(PowerUnit.WATTS)
val Long.watts get()= this.toPower(PowerUnit.WATTS)
val Float.watts get()= this.toPower(PowerUnit.WATTS)
val Double.watts get()= this.toPower(PowerUnit.WATTS)

// --- Speed
operator fun Int.times(speed: Speed): Speed = speed * this
operator fun Long.times(speed: Speed): Speed = speed * this
operator fun Float.times(speed: Speed): Speed = speed * this
operator fun Double.times(speed: Speed): Speed = speed * this

val Int.kmh get()= Speed(this * Speed.KILOMETER_PER_HOUR)
val Long.kmh get()= Speed(this * Speed.KILOMETER_PER_HOUR)
val Float.kmh get()= Speed(this * Speed.KILOMETER_PER_HOUR)
val Double.kmh get()= Speed(this * Speed.KILOMETER_PER_HOUR)

// --- Impulse
operator fun Int.times(impulse: Impulse): Impulse = impulse * this
operator fun Long.times(impulse: Impulse): Impulse = impulse * this
operator fun Float.times(impulse: Impulse): Impulse = impulse * this
operator fun Double.times(impulse: Impulse): Impulse = impulse * this

val Int.Ns get()= Impulse(this * Impulse.Newton_Seconds)
val Long.Ns get()= Impulse(this * Impulse.Newton_Seconds)
val Float.Ns get()= Impulse(this * Impulse.Newton_Seconds)
val Double.Ns get()= Impulse(this * Impulse.Newton_Seconds)


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

val Int.squareMeters: Area get() = Area(this * Area.SQUARE_METERS)
val Long.squareMeters: Area get() = Area(this * Area.SQUARE_METERS)
val Float.squareMeters: Area get() = Area(this * Area.SQUARE_METERS)
val Double.squareMeters: Area get() = Area(this * Area.SQUARE_METERS)