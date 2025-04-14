# 📦 Units Kotlin Package

A Kotlin software package that introduces **type-safe representations for physical units** by wrapping primitive numeric types like `Long` and `Double`. This approach eliminates the need for manual conversion and mitigates rounding errors for most fundamental unit types.

---

## 🧠 Overview

This package allows developers to perform operations on physical units (e.g., distance, mass, temperature) using intuitive and type-safe syntax. Internally, these unit types are backed by primitive types for performance and compatibility.

- **Simple units** like `Distance`, `Mass`, and `Temperature` are backed by `Long`.
- **Complex units** like `Area` and `Energy` are backed by `Double`.

> ⚠️ Multiplying simple units can quickly exceed `Long`'s limits. For such scenarios, precision is traded off by using `Double`.

---

## 📐 Base Units & Scaling

All simple units use **micro-scale representations** to maximize precision:

| Unit        | Base Value | Scale                      |
|-------------|------------|----------------------------|
| Distance    | 1L         | = 1 micrometer             |
| Mass        | 1L         | = 1 microgram              |
| Temperature | 1L         | = 1 microkelvin            |

This ensures that most basic units remain integer-representable, minimizing floating-point rounding errors.

---

## 🔁 Supported Operations

Each unit type supports the following:

- **Arithmetic operators**:
    - `+`, `-`, `*` (by scalar), `/` (by scalar), `rem`
- **Range operations**:
    - `rangeTo`, `rangeUntil`
- **Utility functions**:
    - `abs(unit: UnitType)`
    - Kotlin extensions: `.sumOf()`, `.min()`, `.max()`, `.average()`

### 🔢 Efficient Range Support

All units offer specialized implementations of `ClosedRange` and `OpenEndRange` tailored to their respective types.  
This design avoids boxing overhead that would occur with generic `ClosedRange<T>` use. For example, the `Distance` type defines `rangeTo` and `rangeUntil` operators that return concrete range classes (`ClosedDistanceRange`, `OpenDistanceRange`), allowing highly efficient `in` checks on the raw numeric value at the JVM level.

#### Example: Distance Range Implementation

```kotlin
operator fun Distance.rangeTo(other: Distance): ClosedDistanceRange =
  ClosedDistanceRange(this, other)

operator fun Distance.rangeUntil(other: Distance): OpenDistanceRange =
  OpenDistanceRange(this, other)

class ClosedDistanceRange(
  override val start: Distance,
  override val endInclusive: Distance
) : ClosedRange<Distance> {
  override fun contains(value: Distance): Boolean {
    return value.raw in start.raw..endInclusive.raw
  }
}

class OpenDistanceRange(
  override val start: Distance,
  override val endExclusive: Distance
) : OpenEndRange<Distance> {
  override fun contains(value: Distance): Boolean {
    return value.raw in start.raw..<endExclusive.raw
  }
}
```

## 🔧 Extension Properties

Each unit provides intuitive extensions for conversion and computation:

### 📏 Distance

| Property                | Type    | Description                                |
|-------------------------|---------|--------------------------------------------|
| `inWholeMillimeters`    | `Long`  | Rounded using `roundToLong()`              |
| `inWholeCentimeters`    | `Long`  | Rounded using `roundToLong()`              |
| `inWholeMeters`         | `Long`  | Rounded using `roundToLong()`              |
| `inWholeKilometers`     | `Long`  | Rounded using `roundToLong()`              |
| `inMeters`              | `Double`| Direct double conversion                   |
| `inKilometers`          | `Double`| Direct double conversion                   |

### ⚖️ Mass

| Property     | Type     | Description                                  |
|--------------|----------|----------------------------------------------|
| `inKilograms`| `Double` | Returns the raw kilogram representation      |

### 🌡️ Temperature

- Temperature is internally stored in microkelvin (`Long`).
- No direct conversion properties are currently available.

### 📐 Area

- No direct number conversion properties.

### 💶 Currency

| Property     | Type     | Description       |
|--------------|----------|-------------------|
| `inEuros`    | `Double` | Returns a double  |

### ⚡ Efficiency

- No numeric conversion properties.

---

## 🏗️ Construction Extensions

Units can be constructed directly from numeric primitives using intuitive Kotlin extensions.

```kotlin
val d = 5.kilometers
val m = 200.grams
val temp = 25.celsius
val price = 10.`€`
```
```mermaid
graph TD 

    Distance["Distance (m)"]
  Area["Area (m²)"]
  Volume["Volume (m³)"]



  Frequency["Frequency (s⁻¹)"]
  Speed["Speed (ms⁻¹)"]


  Energy["Energy(kgm²s⁻²)"]
  Power["Power(kgm²s⁻³)"]
  Temperature
  Efficiency["Newton (kgms⁻²) = Efficiency"]
  Mass["Mass (kg)"]
  Acceleration["Acceleration (ms⁻²)"]
    Duration["Duration (s)"]
      Currency


 Distance --> |"m"| Area
 Distance --> |"s⁻¹"| Speed
 Area --> |"m⁻¹"| Distance
  Area --> |"m"| Volume
 Volume --> |"m⁻¹"| Area
 Energy --> |"m⁻¹"| Efficiency
 Frequency --> |"m"| Speed
 Power --> |"s"| Energy
 Energy --> |"s⁻¹"| Power
 Speed --> |"s"| Distance
Speed --> |"s⁻¹"| Acceleration
Acceleration --> |"s"| Speed
Energy --> |"(Newton)⁻¹"| Distance


