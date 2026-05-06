@file:Suppress("unused")
package edu.kit.ifv.units.arrays

import edu.kit.ifv.units.SquareDuration

/**
* This class is a typed array for SquareDuration. At runtime, it is converted to a regular java array
* of the backing type double (double[]).
*/
@JvmInline
value class SquareDurationArray internal constructor(private val rawValues: DoubleArray) {
     
    // Developer-Note: This class was generated automatically and likely will again in the future, you might 
    // want to edit the code-generation instead of this class specifically. You can find the generating 
    // array.main-function in `src/test/edu/kit/ifv/units/arrays/ArrayGen.kt`.
    // The Gradle task `generateArrays` regenerates all Array classes.
    
    constructor(size: Int): this(DoubleArray(size))
    
    constructor(size: Int, init: (index: Int) -> SquareDuration): 
        this(DoubleArray(size) { index -> init(index).rawValue })
        
    constructor(src: Array<SquareDuration>): this(src.map { it.rawValue }.toDoubleArray())
    
    constructor(src: Collection<SquareDuration>): this(src.map { it.rawValue }.toDoubleArray())

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

    operator fun get(index: Int) = SquareDuration(rawValues[index])

    operator fun set(index: Int, value: SquareDuration) {
        rawValues[index] = value.rawValue
    }

    fun mean() = SquareDuration(rawValues.sum() / rawValues.size)

    fun sum() = SquareDuration(rawValues.sum())

    fun iterator(): SquareDurationIterator = SquareDurationIterator(rawValues.iterator())

    /**
    * @returns true if all elements of this array match the given predicate.
    */
    fun all(predicate: (SquareDuration) -> Boolean): Boolean 
        = rawValues.all { predicate(SquareDuration(it)) }

    /**
    * @returns true if this array contains at least one element.
    */
    fun any(): Boolean = rawValues.any()

    /**
    * @returns true if at least one element of this array matches the given predicate.
    */
    fun any(predicate: (SquareDuration) -> Boolean): Boolean  = rawValues.any { predicate(SquareDuration(it)) }

    /**
    * @returns true if this array has no elements.
    */
    fun none(): Boolean = rawValues.none()

    /**
    * @returns true if no element of this array matches the given predicate.
    */
    fun none(predicate: (SquareDuration) -> Boolean): Boolean  = rawValues.none { predicate(SquareDuration(it)) }

    fun <K, V> associate(transform: (SquareDuration) -> Pair<K, V>): Map<K, V> 
        = rawValues.associate { transform(SquareDuration(it)) }

    fun <K> associateBy(keySelector: (SquareDuration) -> K): Map<K, SquareDuration> 
        = associate { keySelector(it) to it }

    fun <K, V> associateBy(keySelector: (SquareDuration) -> K, valueTransform: (SquareDuration) -> V): Map<K, V> 
        = associate { keySelector(it) to valueTransform(it)}

    fun <V> associateWith(valueSelector: (SquareDuration) -> V): Map<SquareDuration, V> 
        = associate {it to valueSelector(it)}

    fun average(): SquareDuration
        = SquareDuration(rawValues.average().toDouble())

    /**
    * Returns the largest element.
    */
    fun max(): SquareDuration
        = SquareDuration(rawValues.max())

    /**
    * Returns the smallest element.
    */
    fun min(): SquareDuration
        = SquareDuration(rawValues.min())

    /**
    * Returns a list containing only elements matching the given predicate.
    */
    fun filter(predicate: (SquareDuration) -> Boolean): List<SquareDuration>
        = buildList { 
            for (element in rawValues) {
                val converted = SquareDuration(element)
                if (predicate(converted)) add(converted)
            }
         }

    /**
     * Returns a list containing the results of applying the given transform function to each element in the original array.
     */
     fun <R> map(transform: (SquareDuration) -> R): List<R> =
        rawValues.map { transform(SquareDuration(it)) }

    companion object {
        @JvmInline
        value class SquareDurationIterator internal constructor(val iterator: DoubleIterator): Iterator<SquareDuration> {
            override fun next(): SquareDuration = SquareDuration(iterator.next())
            override fun hasNext(): Boolean = iterator.hasNext()
        }
    }
}


fun Collection<SquareDuration>.toSquareDurationArray() = SquareDurationArray(this)

fun Array<SquareDuration>.toSquareDurationArray() = SquareDurationArray(this)