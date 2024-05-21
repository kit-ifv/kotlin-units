package units


import kotlin.time.Duration
import kotlin.time.DurationUnit

class HigherOrderUnit(
    override val type: Map<Class<out NumericUnit<*>>, Int> = mapOf(),
    override val rawValue: Double = 1.0

) : NumericUnit<FakeScale>, ScalarUnit<FakeScale> {
    constructor(
        numerators: List<NumericUnit<*>> = listOf(),
        denominators: List<NumericUnit<*>> = listOf()
    ) : this(
        merge(numerators.groupingBy { Resolver.name(it) }.eachCount(),
            denominators.groupingBy { Resolver.name(it) }.eachCount().mapValues { (_, v) -> -v }),

        numerators.product { it.rawValue.toDouble() } / denominators.product { it.rawValue.toDouble() }
    )

    private val inverseUnits = type.mapValues { (_, value) -> -value }

    constructor(vararg units: NumericUnit<*>) : this(units.toList())


    override fun toDouble(unit: FakeScale): Double {
        return rawValue
    }

    override fun toLong(unit: FakeScale): Long {
        return rawValue.toLong()
    }

    override fun toInt(unit: FakeScale): Int {
        return rawValue.toInt()
    }


    override fun unaryMinus(): NumericUnit<FakeScale> {
        return this
    }



    override fun times(scalar: Double): HigherOrderUnit {
        return HigherOrderUnit(type, rawValue * scalar)
    }

    override fun times(scalar: Number): HigherOrderUnit {
        return this * scalar.toDouble()
    }


    override fun div(scalar: Number): HigherOrderUnit {
        return this / scalar.toDouble()
    }
    override fun div(scalar: Double): HigherOrderUnit {
        return HigherOrderUnit(type, rawValue / scalar)
    }



    override operator fun times(other: NumericUnit<*>): NumericUnit<*> {

        val map = type.toMutableMap()
        val target = map.getOrDefault(Resolver.name(other), 0) + 1
        map[Resolver.name(other)] = target
        return HigherOrderUnit(map, rawValue * other.rawValue.toDouble()).simplify()
    }

    operator fun times(other: HigherOrderUnit): NumericUnit<*> {
        val start = merge(type, other.type)

        return HigherOrderUnit(start, rawValue * other.rawValue).simplify()
    }

    override operator fun div(other: NumericUnit<*>): NumericUnit<*> {
        val map = type.toMutableMap()
        val targetList = map.getOrDefault(Resolver.name(other), 0) - 1

        return HigherOrderUnit(map, rawValue / other.rawValue.toDouble()).simplify()
    }

    operator fun div(other: HigherOrderUnit): NumericUnit<*> {
        val start = merge(type, other.inverseUnits)
        return HigherOrderUnit(start, rawValue / other.rawValue).simplify()
    }

    private fun simplify(): NumericUnit<*> {
        return when (this.type) {
            Area.signature -> Area(this.rawValue)
            Currency.signature -> Currency(this.rawValue)
            Distance.signature -> Distance(this.rawValue.toLong())
            Energy.signature -> Energy(this.rawValue)
            Mass.signature -> Mass(this.rawValue.toLong())
            Power.signature -> Power(this.rawValue)
            Temperature.signature -> Temperature(this.rawValue.toLong())

            else -> this


        }
    }


    fun resolve(units: List<NumericUnitScale>): Double {
        return 0.0
    }
//        val nominators = this.type.mapValues { (key, values) ->
//            values.map{value -> value / units.first{Resolver.name(it) == key}.scale.toDouble()} }
//
//        val denominators = this.type.mapValues { (key, values) ->
//            values.map{value -> value / units.first{Resolver.name(it) == key}.scale.toDouble()} }
//        return (nominators.values.flatten().fold(1.0) { acc, value -> acc * value} /
//        denominators.values.flatten().fold(1.0) { acc, value -> acc * value} )
//    }


}

class FakeScale(override val scale: Int = 1) : NumericUnitScale

fun <E> List<E>.product(converter: (E) -> Double): Double {
    return this.fold(1.0) { acc, e -> acc * converter(e) }
}

fun <K, V> Map<K, V>.dropValue(target: V): Map<K, V> {
    return this.filter { (_, v) -> v != target }
}

fun merge(
    map1: Map<Class<out NumericUnit<*>>, Int>,
    map2: Map<Class<out NumericUnit<*>>, Int>
): Map<Class<out NumericUnit<*>>, Int> {
    val newMap = map1.toMutableMap()
    map2.entries.forEach { (key, value) -> newMap.merge(key, value) { a, b -> a + b } }
    return newMap.filter { (_, value) -> value != 0 }
}

/**
 * Maybe this could be externally visible, but right now the logic is too botched to make public
 */
object Resolver {
    /**
     * This is hacky, the class does not need to be called XXX-Unit but could have an arbitrary name. Sealing will solve
     * this issue, but if at any point someone feels or sees the need to rewrite this -> Go ahead
     */
    fun name(scale: NumericUnitScale): Class<NumericUnitScale> {
        return scale.javaClass
    }

    fun name(unit: NumericUnit<*>): Class<NumericUnit<*>> {
        return unit.javaClass
    }

    fun makeNumeric(input: Any): NumericUnit<*> {
        return when (input) {
            is Duration -> input.toDouble(DurationUnit.SECONDS).toFakeDuration(DurationImitatorUnit.SECONDS)
            is NumericUnit<*> -> input
            else -> throw UnsupportedOperationException("Sorry, $input cannot be turned into a numeric unit")
        }
    }
}
