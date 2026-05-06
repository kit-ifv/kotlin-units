@file:Suppress("unused")
package edu.kit.ifv.units.arrays

import edu.kit.ifv.units.Frequency

/**
* This class is a typed array for Frequency. At runtime, it is converted to a regular java array
* of the backing type double (double[]).
*/
@JvmInline
value class FrequencyArray internal constructor(private val rawValues: DoubleArray) {
     
    // Developer-Note: This class was generated automatically and likely will again in the future, you might 
    // want to edit the code-generation instead of this class specifically. You can find the generating 
    // array.main-function in `src/test/edu/kit/ifv/units/arrays/ArrayGen.kt`.
    // The Gradle task `generateArrays` regenerates all Array classes.
    
    constructor(size: Int): this(DoubleArray(size))
    
    constructor(size: Int, init: (index: Int) -> Frequency): 
        this(DoubleArray(size) { index -> init(index).rawValue })
        
    constructor(src: Array<Frequency>): this(src.map { it.rawValue }.toDoubleArray())
    
    constructor(src: Collection<Frequency>): this(src.map { it.rawValue }.toDoubleArray())

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

    operator fun get(index: Int) = Frequency(rawValues[index])

    operator fun set(index: Int, value: Frequency) {
        rawValues[index] = value.rawValue
    }

    fun mean() = Frequency(rawValues.sum() / rawValues.size)

    fun sum() = Frequency(rawValues.sum())

    fun iterator(): FrequencyIterator = FrequencyIterator(rawValues.iterator())

    /**
    * @returns true if all elements of this array match the given predicate.
    */
    fun all(predicate: (Frequency) -> Boolean): Boolean 
        = rawValues.all { predicate(Frequency(it)) }

    /**
    * @returns true if this array contains at least one element.
    */
    fun any(): Boolean = rawValues.any()

    /**
    * @returns true if at least one element of this array matches the given predicate.
    */
    fun any(predicate: (Frequency) -> Boolean): Boolean  = rawValues.any { predicate(Frequency(it)) }

    /**
    * @returns true if this array has no elements.
    */
    fun none(): Boolean = rawValues.none()

    /**
    * @returns true if no element of this array matches the given predicate.
    */
    fun none(predicate: (Frequency) -> Boolean): Boolean  = rawValues.none { predicate(Frequency(it)) }

    fun <K, V> associate(transform: (Frequency) -> Pair<K, V>): Map<K, V> 
        = rawValues.associate { transform(Frequency(it)) }

    fun <K> associateBy(keySelector: (Frequency) -> K): Map<K, Frequency> 
        = associate { keySelector(it) to it }

    fun <K, V> associateBy(keySelector: (Frequency) -> K, valueTransform: (Frequency) -> V): Map<K, V> 
        = associate { keySelector(it) to valueTransform(it)}

    fun <V> associateWith(valueSelector: (Frequency) -> V): Map<Frequency, V> 
        = associate {it to valueSelector(it)}

    fun average(): Frequency
        = Frequency(rawValues.average().toDouble())

    /**
    * Returns the largest element.
    */
    fun max(): Frequency
        = Frequency(rawValues.max())

    /**
    * Returns the smallest element.
    */
    fun min(): Frequency
        = Frequency(rawValues.min())

    /**
    * Returns a list containing only elements matching the given predicate.
    */
    fun filter(predicate: (Frequency) -> Boolean): List<Frequency>
        = buildList { 
            for (element in rawValues) {
                val converted = Frequency(element)
                if (predicate(converted)) add(converted)
            }
         }

    /**
     * Returns a list containing the results of applying the given transform function to each element in the original array.
     */
     fun <R> map(transform: (Frequency) -> R): List<R> =
        rawValues.map { transform(Frequency(it)) }

    companion object {
        @JvmInline
        value class FrequencyIterator internal constructor(val iterator: DoubleIterator): Iterator<Frequency> {
            override fun next(): Frequency = Frequency(iterator.next())
            override fun hasNext(): Boolean = iterator.hasNext()
        }
    }
}


fun Collection<Frequency>.toFrequencyArray() = FrequencyArray(this)

fun Array<Frequency>.toFrequencyArray() = FrequencyArray(this)