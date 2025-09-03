# Units Kotlin Package

A Kotlin software package that introduces **type-safe representations for physical units** by wrapping primitive numeric types like `Long` and `Double`. This approach eliminates the need for manual conversion and mitigates rounding errors for most fundamental unit types.

---

## Overview

This package allows developers to perform operations on physical units (e.g., distance, mass, temperature) using intuitive and type-safe syntax. Internally, these unit types are backed by primitive types for performance and compatibility.

- **Simple units** like `Distance`, `Mass`, and `Temperature` are backed by `Long`.
- **Complex units** like `Area`, `Impulse`, `Power`, `Energy`... are backed by `Double`.

> ⚠️ Multiplying simple units can quickly exceed `Long`'s limits. For such scenarios, precision is traded off by using `Double`.

---

## Base Units & Scaling

All simple units use **micro-scale representations** to maximize precision:

| Unit        | Base Value | Scale                      |
|-------------|------------|----------------------------|
| Distance    | 1L         | = 1 micrometer             |
| Mass        | 1L         | = 1 microgram              |
| Temperature | 1L         | = 1 microkelvin            |

This ensures that most basic units remain integer-representable, minimizing floating-point rounding errors.

---

## Supported Operations

Each unit type supports the following:

- **Arithmetic operators**:
    - `+`, `-`, `*` (by scalar), `/` (by scalar), `rem` (remainder only defined for units with `Long` backing)
    - `*` (by other type), `/` (by other type) is defined. So `1.meters/1.seconds == 1.metersPerSecond`. If  is typesafe which results in a unit, defined in this
      library, is type safe. For example `assertIs<Newton>(1.meters_per_second_squared * 1.kilograms)`. Any other
      multiplication or division of two types will result in an `OutOfBoundsUnit` e.g.
      `assertIs<OutOfBoundsUnit>(1.joule * 1.joule)`.
- **Range operations**:
    - `rangeTo`, `rangeUntil`
- **Utility functions**:
    - `abs(unit: UnitType)`
    - Iterable<UnitType> extensions: `sumOf()`, `min()`, `max()`, `.average()`
    - Kotlin extensions:  `.coerceIn()`, `.coerceAtLeast()`, `.coerceAtMost()`'
    - 

### Efficient Range Support

All units offer specialized implementations of `ClosedRange` and `OpenEndRange` tailored to their respective types.  
Of course generic ranges also work, but they would use boxing, which is overhead we are trying to avoid.
(Duration is an exception, as Duration is a Kotlin Native Type, which doesn't )

## Conversion To Primitive Types
Types can be converted to primitive numbers using `toInt(ReturnScale)`, `toDouble(ReturnScale)`, `toLong(ReturnScale)`
Or with self-descriptive conversions like `inKilograms`, `inEuros`... (or `asSeconds` for Durations)

## Construction Of Types

Units can be constructed directly from `Int`, `Long`, `Float`, and `Double` using intuitive Kotlin extensions.

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
val impulse = 4.5.toImpulse(ImpulseUnit.Default)
val tons = 400.923.toMass(MassUnit.TON)
```
