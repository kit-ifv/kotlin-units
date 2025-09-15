# Units Kotlin Package

A Kotlin software package that introduces **type-safe representations for physical units** by wrapping primitive numeric types like `Long` and `Double`. This approach eliminates the need for manual conversion and mitigates rounding errors for most fundamental unit types.

---

## Overview

This package allows developers to perform operations on physical units (e.g., distance, mass, temperature) using intuitive and type-safe syntax. Internally, these unit types are backed by primitive types for performance and compatibility.

- **Simple units** like `Distance`, `Mass`, and `Temperature` are backed by `Long`.
- **Complex units** like `Area`, `Impulse`, `Power`, `Energy`... are backed by `Double`.

> ⚠️ Multiplying simple units can quickly exceed `Long`'s limits. For such scenarios, precision is traded off by using `Double`.

### Provided UnitTypes:
| Unit          | Backing Type          | unit                                                 | scale (unit of its rawvalue) |
|---------------|-----------------------|------------------------------------------------------|------------------------------|
| `Distance`      | `Long`                  | Meter                                                | micrometer                   |  
| `Area`          | `Double`                | Squaremeters                                         | Square meters                |
| `Volume` | `Double`                | Cubic meters                                         | Cubic meters                 |
| `Mass `         | `Long`                  | Gram                                                 | microgram                    |
| `Temperature`   | `Long`                  | Kelvin                                               | microkelvin                  |
| `Duration`      | is native Kotlin type | -                                                    | -                            |
| `DurationWrapper` | `Duration`            | (ensures interoperability with this library)         | -                            |
| `SquareDuration` | `Double`                | Seconds squared                                      | Seconds squared              | 
| `CubicDuration` | `Double`                | Seconds cubed                                        | Seconds cubed                |
| `Frequency` | `Double`                | Hertz                                                | Hertz                        |
| `Speed` | `Double`                | Meters per second                                    | Meters per second            |
| `Impulse` | `Double`                | Kilograms meters per second                          | Kilograms meters per second  |
| `Acceleration`  | `Double`                | Meters per second squared                            | Meters per second squared    |
| `Force` | `Double`                | Newton                                               | Newton                       |
| `Energy` | `Double`                | Joule                                                | Joule                        |
| `Power` | `Double`                | Watt                                                 | Watt                         |
| `Degrees` | `Double` | Degrees                                              | Degrees                      |
| `Radians` | `Double` | Radians                                              | Radians                      |
| `GPSCoordinate` | `Radians` | -                                                    | -                            |
| `OutOfBoundsUnit` | `Double` | (any unit made out of Meters, Seconds and Kilogram)  |                              |


## Base Units & Scaling

Simple units use **micro-scale representations** to maximize precision:

This ensures that most basic units remain integer-representable, minimizing floating-point rounding errors.

---

# Construction Of UnitTypes
UnitTypes can be constructed directly from `Int`, `Long`, `Float`, and `Double` using intuitive Kotlin extensions.

```kotlin
val d = 5.kilometers
val m = 200.grams
val temp = 25.celsius
val price = 10.`€`
val force = (30.09).newton
val area = 30.squareMeters
//...
```

or using the good old `number.toXYZ` functions.
```kotlin
val impulse = 4.5.toImpulse(ImpulseUnit.NEWTON_SECONDS)
val tons = (400.923).toMass(MassUnit.TON)
```

# Destruction of UnitTypes
UnitTypes can be converted to primitive numbers using `toInt(ReturnScale)`, `toDouble(ReturnScale)`, `toLong(ReturnScale)`

Or with self-descriptive conversions like `inKilograms`, `inEuros`, `inXYC` (or `asSeconds` for Durations because Durations are special)

---

# Supported Operations

Each unit type supports the following:

### Arithmetic operators
All types support basic operations like:
- `+` (same types), `-`(same types), `*` (by scalar), `/` (by scalar), `rem`
#### Multiplication Or Division By Another Type
Multiplication or division by another type is typesafe as long as both types result in a type defined in this library. 
For example 
```kotlin
assertIs<Newton>(1.metersPerSecondSquared * 1.kilograms)
```
Any other multiplication or division of two types will result in an `OutOfBoundsUnit` e.g.
  `assertIs<OutOfBoundsUnit>(1.joule * 1.joule)`.

> __OutOfBoundsUnit__ can also be used for calculations, but you loose type-safety at __compile time__ (which is the point of this library). 
> They will, however, prevent you from adding, say meters and euros, by throwing an exception at __runtime__.

### Range operations
- `rangeTo`, `rangeUntil`

These are defined for every type to avoid boxing, that would happen if we would rely on the generic
`rangeTo<T>` of Kotlin.
> `Duration` is an exception here, as Kotlin has a native Duration class, which we can't extend to always use our 
> `rangeTo` definition.

### Utility functions
- get the absolute value of a unit: `abs(unit)`
- compare units with: `min(a,b)`, `max(a,b)`
- Iterable<UnitType> extensions: `sumOf()`, `min()`, `max()`, `.average()`
- set limits with the following extensions:  `.coerceIn()`, `.coerceAtLeast()`, `.coerceAtMost()`'
