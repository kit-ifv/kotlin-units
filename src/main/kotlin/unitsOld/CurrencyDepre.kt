package unitsOld

@JvmInline
value class CurrencyDepre(override val rawValue: Double) : FloatUnit<CurrencyUnit>, ScalarUnit<CurrencyUnit> {
    override val type: Map<Class<out NumericUnit<*>>, Int>
        get() = signature

    override fun times(scalar: Double): CurrencyDepre {
        return CurrencyDepre(rawValue * scalar)
    }

    override fun div(scalar: Double): CurrencyDepre {
        return CurrencyDepre(rawValue * scalar)
    }

    companion object {
        val signature: Map<Class<out NumericUnit<*>>, Int> = mapOf(CurrencyDepre::class.java to 1)

    }

    override fun plus(other: FloatUnit<CurrencyUnit>): FloatUnit<CurrencyUnit> {
        return CurrencyDepre(this.rawValue + other.rawValue)
    }

    override fun minus(other: FloatUnit<CurrencyUnit>): FloatUnit<CurrencyUnit> {
        return this + (-other)
    }

    override fun unaryMinus(): FloatUnit<CurrencyUnit> {
        return CurrencyDepre(-rawValue)
    }


}

fun Int.toCurrency(units: CurrencyUnit): CurrencyDepre {
    return CurrencyDepre(this * units.scale)
}


fun Long.toCurrency(units: CurrencyUnit): CurrencyDepre {
    return CurrencyDepre(this * units.scale)
}

fun Double.toCurrency(units: CurrencyUnit): CurrencyDepre {
    return CurrencyDepre(this * units.scale)
}
operator fun Number.times(element: CurrencyDepre): CurrencyDepre {
    return element * this.toDouble()
}
inline val Number.euros: CurrencyDepre
    get() = this.toDouble().toCurrency(CurrencyUnit.EUROS)


enum class CurrencyUnit(override val scale: Double) : FloatUnitScale {
    EUROS(1.0)
}
