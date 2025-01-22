package units

@JvmInline
value class Currency(override val rawValue: Double) : FloatUnit<CurrencyUnit>, ScalarUnit<CurrencyUnit> {
    override val type: Map<Class<out NumericUnit<*>>, Int>
        get() = signature

    override fun times(scalar: Double): Currency {
        return Currency(rawValue * scalar)
    }

    override fun div(scalar: Double): Currency {
        return Currency(rawValue * scalar)
    }

    companion object {
        val signature: Map<Class<out NumericUnit<*>>, Int> = mapOf(Currency::class.java to 1)

    }

    override fun plus(other: FloatUnit<CurrencyUnit>): FloatUnit<CurrencyUnit> {
        return Currency(this.rawValue + other.rawValue)
    }

    override fun minus(other: FloatUnit<CurrencyUnit>): FloatUnit<CurrencyUnit> {
        return this + (-other)
    }

    override fun unaryMinus(): FloatUnit<CurrencyUnit> {
        return Currency(-rawValue)
    }


}

fun Int.toCurrency(units: CurrencyUnit): Currency {
    return Currency(this * units.scale)
}


fun Long.toCurrency(units: CurrencyUnit): Currency {
    return Currency(this * units.scale)
}

fun Double.toCurrency(units: CurrencyUnit): Currency {
    return Currency(this * units.scale)
}
operator fun Number.times(element: Currency): Currency {
    return element * this.toDouble()
}
inline val Number.euros: Currency
    get() = this.toDouble().toCurrency(CurrencyUnit.EUROS)


enum class CurrencyUnit(override val scale: Double) : FloatUnitScale {
    EUROS(1.0)
}
