@file:Suppress("unused")
package edu.kit.ifv.units.arrays

import edu.kit.ifv.units.Distance

/**
* This class is a typed array for Distance. At runtime, it is converted to a regular java array
* of the backing type long (long[]).
*/
@JvmInline
value class DistanceArray internal constructor(private val rawValues: LongArray) {
     
    // Developer-Note: This class was generated automatically and likely will again in the future, you might 
    // want to edit the code-generation instead of this class specifically. You can find the generating 
    // array.main-function in `src/test/edu/kit/ifv/units/arrays/ArrayGen.kt`.
    // The Gradle task `generateArrays` regenerates all Array classes.
    
    constructor(size: Int): this(LongArray(size))
    
    constructor(size: Int, init: (index: Int) -> Distance): 
        this(LongArray(size) { index -> init(index).rawValue })
        
    constructor(src: Array<Distance>): this(src.map { it.rawValue }.toLongArray())
    
    constructor(src: Collection<Distance>): this(src.map { it.rawValue }.toLongArray())

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

    operator fun get(index: Int) = Distance(rawValues[index])

    operator fun set(index: Int, value: Distance) {
        rawValues[index] = value.rawValue
    }

    fun mean() = Distance(rawValues.sumOf { it } / rawValues.size)

    fun sum() = Distance(rawValues.sumOf { it })

    fun iterator(): DistanceIterator = DistanceIterator(rawValues.iterator())

    /**
    * @returns true if all elements of this array match the given predicate.
    */
    fun all(predicate: (Distance) -> Boolean): Boolean 
        = rawValues.all { predicate(Distance(it)) }

    /**
    * @returns true if this array contains at least one element.
    */
    fun any(): Boolean = rawValues.any()

    /**
    * @returns true if at least one element of this array matches the given predicate.
    */
    fun any(predicate: (Distance) -> Boolean): Boolean  = rawValues.any { predicate(Distance(it)) }

    /**
    * @returns true if this array has no elements.
    */
    fun none(): Boolean = rawValues.none()

    /**
    * @returns true if no element of this array matches the given predicate.
    */
    fun none(predicate: (Distance) -> Boolean): Boolean  = rawValues.none { predicate(Distance(it)) }

    fun <K, V> associate(transform: (Distance) -> Pair<K, V>): Map<K, V> 
        = rawValues.associate { transform(Distance(it)) }

    fun <K> associateBy(keySelector: (Distance) -> K): Map<K, Distance> 
        = associate { keySelector(it) to it }

    fun <K, V> associateBy(keySelector: (Distance) -> K, valueTransform: (Distance) -> V): Map<K, V> 
        = associate { keySelector(it) to valueTransform(it)}

    fun <V> associateWith(valueSelector: (Distance) -> V): Map<Distance, V> 
        = associate {it to valueSelector(it)}

    fun average(): Distance = mean()

    /**
    * Returns the largest element.
    */
    fun max(): Distance
        = Distance(rawValues.max())

    /**
    * Returns the smallest element.
    */
    fun min(): Distance
        = Distance(rawValues.min())

    /**
    * Returns a list containing only elements matching the given predicate.
    */
    fun filter(predicate: (Distance) -> Boolean): List<Distance>
        = buildList { 
            for (element in rawValues) {
                val converted = Distance(element)
                if (predicate(converted)) add(converted)
            }
         }

    /**
     * Returns a list containing the results of applying the given transform function to each element in the original array.
     */
     fun <R> map(transform: (Distance) -> R): List<R> =
        rawValues.map { transform(Distance(it)) }

    companion object {
        @JvmInline
        value class DistanceIterator internal constructor(val iterator: LongIterator): Iterator<Distance> {
            override fun next(): Distance = Distance(iterator.next())
            override fun hasNext(): Boolean = iterator.hasNext()
        }
    }
}


fun Collection<Distance>.toDistanceArray() = DistanceArray(this)

fun Array<Distance>.toDistanceArray() = DistanceArray(this)