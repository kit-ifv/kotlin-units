@file:Suppress("unused")
package edu.kit.ifv.units.arrays

import edu.kit.ifv.units.Power

/**
* This class is a typed array for Power. At runtime, it is converted to a regular java array
* of the backing type double (double[]).
*/
@JvmInline
value class PowerArray internal constructor(private val rawValues: DoubleArray) {
     
    // Developer-Note: This class was generated automatically and likely will again in the future, you might 
    // want to edit the code-generation instead of this class specifically. You can find the generating 
    // array.main-function in `src/test/edu/kit/ifv/units/arrays/ArrayGen.kt`.
    // The Gradle task `generateArrays` regenerates all Array classes.
    
    constructor(size: Int): this(DoubleArray(size))
    
    constructor(size: Int, init: (index: Int) -> Power): 
        this(DoubleArray(size) { index -> init(index).rawValue })
        
    constructor(src: Array<Power>): this(src.map { it.rawValue }.toDoubleArray())
    
    constructor(src: Collection<Power>): this(src.map { it.rawValue }.toDoubleArray())

    /**
    * How many entries the array has.
    */
    val size: Int get() = rawValues.size

    /**
    * IntRange of all valid indices of this array.
    */
    val indices: IntRange get() = rawValues.indices

    /**
    * Last valid index of this array.
    */
    val lastIndex: Int get() = rawValues.lastIndex

    operator fun get(index: Int) = Power(rawValues[index])

    operator fun set(index: Int, value: Power) {
        rawValues[index] = value.rawValue
    }

    fun mean() = Power(rawValues.sumOf { it } / rawValues.size)

    fun sum() = Power(rawValues.sumOf { it })

    fun iterator(): PowerIterator = PowerIterator(rawValues.iterator())

    /**
    * @returns true if all elements of this array match the given predicate.
    */
    fun all(predicate: (Power) -> Boolean): Boolean 
        = rawValues.all { predicate(Power(it)) }

    /**
    * @returns true if this array contains at least one element.
    */
    fun any(): Boolean = rawValues.any()

    /**
    * @returns true if at least one element of this array matches the given predicate.
    */
    fun any(predicate: (Power) -> Boolean): Boolean  = rawValues.any { predicate(Power(it)) }

    /**
    * @returns true if this array has no elements.
    */
    fun none(): Boolean = rawValues.none()

    /**
    * @returns true if no element of this array matches the given predicate.
    */
    fun none(predicate: (Power) -> Boolean): Boolean  = rawValues.none { predicate(Power(it)) }

    fun <K, V> associate(transform: (Power) -> Pair<K, V>): Map<K, V> 
        = rawValues.associate { transform(Power(it)) }

    fun <K> associateBy(keySelector: (Power) -> K): Map<K, Power> 
        = associate { keySelector(it) to it }

    fun <K, V> associateBy(keySelector: (Power) -> K, valueTransform: (Power) -> V): Map<K, V> 
        = associate { keySelector(it) to valueTransform(it)}

    fun <V> associateWith(valueSelector: (Power) -> V): Map<Power, V> 
        = associate {it to valueSelector(it)}

    fun average(): Power = mean()

    /**
    * Returns the largest element.
    */
    fun max(): Power
        = Power(rawValues.max())

    /**
    * Returns the smallest element.
    */
    fun min(): Power
        = Power(rawValues.min())

    /**
    * Returns a list containing only elements matching the given predicate.
    */
    fun filter(predicate: (Power) -> Boolean): List<Power>
        = buildList { 
            for (element in rawValues) {
                val converted = Power(element)
                if (predicate(converted)) add(converted)
            }
         }

    /**
     * Returns a list containing the results of applying the given transform function to each element in the original array.
     */
     fun <R> map(transform: (Power) -> R): List<R> =
        rawValues.map { transform(Power(it)) }

    companion object {
        @JvmInline
        value class PowerIterator internal constructor(val iterator: DoubleIterator): Iterator<Power> {
            override fun next(): Power = Power(iterator.next())
            override fun hasNext(): Boolean = iterator.hasNext()
        }
    }
}


fun Collection<Power>.toPowerArray() = PowerArray(this)

fun Array<Power>.toPowerArray() = PowerArray(this)