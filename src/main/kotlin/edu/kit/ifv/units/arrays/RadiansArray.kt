@file:Suppress("unused")
package edu.kit.ifv.units.arrays

import edu.kit.ifv.units.Radians

/**
* This class is a typed array for Radians. At runtime, it is converted to a regular java array
* of the backing type double (double[]).
*/
@JvmInline
value class RadiansArray internal constructor(private val rawValues: DoubleArray) {
     
    // Developer-Note: This class was generated automatically and likely will again in the future, you might 
    // want to edit the code-generation instead of this class specifically. You can find the generating 
    // array.main-function in `src/test/edu/kit/ifv/units/arrays/ArrayGen.kt`.
    // The Gradle task `generateArrays` regenerates all Array classes.
    
    constructor(size: Int): this(DoubleArray(size))
    
    constructor(size: Int, init: (index: Int) -> Radians): 
        this(DoubleArray(size) { index -> init(index).rawValue })
        
    constructor(src: Array<Radians>): this(src.map { it.rawValue }.toDoubleArray())
    
    constructor(src: Collection<Radians>): this(src.map { it.rawValue }.toDoubleArray())

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

    operator fun get(index: Int) = Radians(rawValues[index])

    operator fun set(index: Int, value: Radians) {
        rawValues[index] = value.rawValue
    }

    fun mean() = Radians(rawValues.sumOf { it } / rawValues.size)

    fun sum() = Radians(rawValues.sumOf { it })

    fun iterator(): RadiansIterator = RadiansIterator(rawValues.iterator())

    /**
    * @returns true if all elements of this array match the given predicate.
    */
    fun all(predicate: (Radians) -> Boolean): Boolean 
        = rawValues.all { predicate(Radians(it)) }

    /**
    * @returns true if this array contains at least one element.
    */
    fun any(): Boolean = rawValues.any()

    /**
    * @returns true if at least one element of this array matches the given predicate.
    */
    fun any(predicate: (Radians) -> Boolean): Boolean  = rawValues.any { predicate(Radians(it)) }

    /**
    * @returns true if this array has no elements.
    */
    fun none(): Boolean = rawValues.none()

    /**
    * @returns true if no element of this array matches the given predicate.
    */
    fun none(predicate: (Radians) -> Boolean): Boolean  = rawValues.none { predicate(Radians(it)) }

    fun <K, V> associate(transform: (Radians) -> Pair<K, V>): Map<K, V> 
        = rawValues.associate { transform(Radians(it)) }

    fun <K> associateBy(keySelector: (Radians) -> K): Map<K, Radians> 
        = associate { keySelector(it) to it }

    fun <K, V> associateBy(keySelector: (Radians) -> K, valueTransform: (Radians) -> V): Map<K, V> 
        = associate { keySelector(it) to valueTransform(it)}

    fun <V> associateWith(valueSelector: (Radians) -> V): Map<Radians, V> 
        = associate {it to valueSelector(it)}

    fun average(): Radians = mean()

    /**
    * Returns the largest element.
    */
    fun max(): Radians
        = Radians(rawValues.max())

    /**
    * Returns the smallest element.
    */
    fun min(): Radians
        = Radians(rawValues.min())

    /**
    * Returns a list containing only elements matching the given predicate.
    */
    fun filter(predicate: (Radians) -> Boolean): List<Radians>
        = buildList { 
            for (element in rawValues) {
                val converted = Radians(element)
                if (predicate(converted)) add(converted)
            }
         }

    /**
     * Returns a list containing the results of applying the given transform function to each element in the original array.
     */
     fun <R> map(transform: (Radians) -> R): List<R> =
        rawValues.map { transform(Radians(it)) }

    companion object {
        @JvmInline
        value class RadiansIterator internal constructor(val iterator: DoubleIterator): Iterator<Radians> {
            override fun next(): Radians = Radians(iterator.next())
            override fun hasNext(): Boolean = iterator.hasNext()
        }
    }
}


fun Collection<Radians>.toRadiansArray() = RadiansArray(this)

fun Array<Radians>.toRadiansArray() = RadiansArray(this)