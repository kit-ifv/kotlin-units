@file:Suppress("unused")
package edu.kit.ifv.units.arrays

import edu.kit.ifv.units.Temperature

/**
* This class is a typed array for Temperature. At runtime, it is converted to a regular java array
* of the backing type long (long[]).
*/
@JvmInline
value class TemperatureArray internal constructor(private val rawValues: LongArray) {
     
    // Developer-Note: This class was generated automatically and likely will again in the future, you might 
    // want to edit the code-generation instead of this class specifically. You can find the generating 
    // array.main-function in `src/test/edu/kit/ifv/units/arrays/ArrayGen.kt`.
    // The Gradle task `generateArrays` regenerates all Array classes.
    
    constructor(size: Int): this(LongArray(size))
    
    constructor(size: Int, init: (index: Int) -> Temperature): 
        this(LongArray(size) { index -> init(index).rawValue })
        
    constructor(src: Array<Temperature>): this(src.map { it.rawValue }.toLongArray())
    
    constructor(src: Collection<Temperature>): this(src.map { it.rawValue }.toLongArray())

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

    operator fun get(index: Int) = Temperature(rawValues[index])

    operator fun set(index: Int, value: Temperature) {
        rawValues[index] = value.rawValue
    }

    fun mean() = Temperature(rawValues.sum() / rawValues.size)

    fun iterator(): TemperatureIterator = TemperatureIterator(rawValues.iterator())

    /**
    * @returns true if all elements of this array match the given predicate.
    */
    fun all(predicate: (Temperature) -> Boolean): Boolean 
        = rawValues.all { predicate(Temperature(it)) }

    /**
    * @returns true if this array contains at least one element.
    */
    fun any(): Boolean = rawValues.any()

    /**
    * @returns true if at least one element of this array matches the given predicate.
    */
    fun any(predicate: (Temperature) -> Boolean): Boolean  = rawValues.any { predicate(Temperature(it)) }

    /**
    * @returns true if this array has no elements.
    */
    fun none(): Boolean = rawValues.none()

    /**
    * @returns true if no element of this array matches the given predicate.
    */
    fun none(predicate: (Temperature) -> Boolean): Boolean  = rawValues.none { predicate(Temperature(it)) }

    fun <K, V> associate(transform: (Temperature) -> Pair<K, V>): Map<K, V> 
        = rawValues.associate { transform(Temperature(it)) }

    fun <K> associateBy(keySelector: (Temperature) -> K): Map<K, Temperature> 
        = associate { keySelector(it) to it }

    fun <K, V> associateBy(keySelector: (Temperature) -> K, valueTransform: (Temperature) -> V): Map<K, V> 
        = associate { keySelector(it) to valueTransform(it)}

    fun <V> associateWith(valueSelector: (Temperature) -> V): Map<Temperature, V> 
        = associate {it to valueSelector(it)}

    fun average(): Temperature
        = Temperature(rawValues.average().toLong())

    /**
    * Returns the largest element.
    */
    fun max(): Temperature
        = Temperature(rawValues.max())

    /**
    * Returns the smallest element.
    */
    fun min(): Temperature
        = Temperature(rawValues.min())

    /**
    * Returns a list containing only elements matching the given predicate.
    */
    fun filter(predicate: (Temperature) -> Boolean): List<Temperature>
        = buildList { 
            for (element in rawValues) {
                val converted = Temperature(element)
                if (predicate(converted)) add(converted)
            }
         }

    /**
     * Returns a list containing the results of applying the given transform function to each element in the original array.
     */
     fun <R> map(transform: (Temperature) -> R): List<R> =
        rawValues.map { transform(Temperature(it)) }

    companion object {
        @JvmInline
        value class TemperatureIterator internal constructor(val iterator: LongIterator): Iterator<Temperature> {
            override fun next(): Temperature = Temperature(iterator.next())
            override fun hasNext(): Boolean = iterator.hasNext()
        }
    }
}


fun Collection<Temperature>.toTemperatureArray() = TemperatureArray(this)

fun Array<Temperature>.toTemperatureArray() = TemperatureArray(this)