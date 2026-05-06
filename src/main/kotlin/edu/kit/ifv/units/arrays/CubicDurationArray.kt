@file:Suppress("unused")
package edu.kit.ifv.units.arrays

import edu.kit.ifv.units.CubicDuration

/**
* This class is a typed array for CubicDuration. At runtime, it is converted to a regular java array
* of the backing type double (double[]).
*/
@JvmInline
value class CubicDurationArray internal constructor(private val rawValues: DoubleArray) {
     
    // Developer-Note: This class was generated automatically and likely will again in the future, you might 
    // want to edit the code-generation instead of this class specifically. You can find the generating 
    // array.main-function in `src/test/edu/kit/ifv/units/arrays/ArrayGen.kt`.
    // The Gradle task `generateArrays` regenerates all Array classes.
    
    constructor(size: Int): this(DoubleArray(size))
    
    constructor(size: Int, init: (index: Int) -> CubicDuration): 
        this(DoubleArray(size) { index -> init(index).rawValue })
        
    constructor(src: Array<CubicDuration>): this(src.map { it.rawValue }.toDoubleArray())
    
    constructor(src: Collection<CubicDuration>): this(src.map { it.rawValue }.toDoubleArray())

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

    operator fun get(index: Int) = CubicDuration(rawValues[index])

    operator fun set(index: Int, value: CubicDuration) {
        rawValues[index] = value.rawValue
    }

    fun mean() = CubicDuration(rawValues.sum() / rawValues.size)

    fun sum() = CubicDuration(rawValues.sum())

    fun iterator(): CubicDurationIterator = CubicDurationIterator(rawValues.iterator())

    /**
    * @returns true if all elements of this array match the given predicate.
    */
    fun all(predicate: (CubicDuration) -> Boolean): Boolean 
        = rawValues.all { predicate(CubicDuration(it)) }

    /**
    * @returns true if this array contains at least one element.
    */
    fun any(): Boolean = rawValues.any()

    /**
    * @returns true if at least one element of this array matches the given predicate.
    */
    fun any(predicate: (CubicDuration) -> Boolean): Boolean  = rawValues.any { predicate(CubicDuration(it)) }

    /**
    * @returns true if this array has no elements.
    */
    fun none(): Boolean = rawValues.none()

    /**
    * @returns true if no element of this array matches the given predicate.
    */
    fun none(predicate: (CubicDuration) -> Boolean): Boolean  = rawValues.none { predicate(CubicDuration(it)) }

    fun <K, V> associate(transform: (CubicDuration) -> Pair<K, V>): Map<K, V> 
        = rawValues.associate { transform(CubicDuration(it)) }

    fun <K> associateBy(keySelector: (CubicDuration) -> K): Map<K, CubicDuration> 
        = associate { keySelector(it) to it }

    fun <K, V> associateBy(keySelector: (CubicDuration) -> K, valueTransform: (CubicDuration) -> V): Map<K, V> 
        = associate { keySelector(it) to valueTransform(it)}

    fun <V> associateWith(valueSelector: (CubicDuration) -> V): Map<CubicDuration, V> 
        = associate {it to valueSelector(it)}

    fun average(): CubicDuration
        = CubicDuration(rawValues.average().toDouble())

    /**
    * Returns the largest element.
    */
    fun max(): CubicDuration
        = CubicDuration(rawValues.max())

    /**
    * Returns the smallest element.
    */
    fun min(): CubicDuration
        = CubicDuration(rawValues.min())

    /**
    * Returns a list containing only elements matching the given predicate.
    */
    fun filter(predicate: (CubicDuration) -> Boolean): List<CubicDuration>
        = buildList { 
            for (element in rawValues) {
                val converted = CubicDuration(element)
                if (predicate(converted)) add(converted)
            }
         }

    /**
     * Returns a list containing the results of applying the given transform function to each element in the original array.
     */
     fun <R> map(transform: (CubicDuration) -> R): List<R> =
        rawValues.map { transform(CubicDuration(it)) }

    companion object {
        @JvmInline
        value class CubicDurationIterator internal constructor(val iterator: DoubleIterator): Iterator<CubicDuration> {
            override fun next(): CubicDuration = CubicDuration(iterator.next())
            override fun hasNext(): Boolean = iterator.hasNext()
        }
    }
}


fun Collection<CubicDuration>.toCubicDurationArray() = CubicDurationArray(this)

fun Array<CubicDuration>.toCubicDurationArray() = CubicDurationArray(this)