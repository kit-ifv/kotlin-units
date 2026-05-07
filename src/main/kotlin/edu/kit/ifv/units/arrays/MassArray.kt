@file:Suppress("unused")
package edu.kit.ifv.units.arrays

import edu.kit.ifv.units.Mass

/**
* This class is a typed array for Mass. At runtime, it is converted to a regular java array
* of the backing type long (long[]).
*/
@JvmInline
value class MassArray internal constructor(private val rawValues: LongArray) {
     
    // Developer-Note: This class was generated automatically and likely will again in the future, you might 
    // want to edit the code-generation instead of this class specifically. You can find the generating 
    // array.main-function in `src/test/edu/kit/ifv/units/arrays/ArrayGen.kt`.
    // The Gradle task `generateArrays` regenerates all Array classes.
    
    constructor(size: Int): this(LongArray(size))
    
    constructor(size: Int, init: (index: Int) -> Mass): 
        this(LongArray(size) { index -> init(index).rawValue })
        
    constructor(src: Array<Mass>): this(src.map { it.rawValue }.toLongArray())
    
    constructor(src: Collection<Mass>): this(src.map { it.rawValue }.toLongArray())

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

    operator fun get(index: Int) = Mass(rawValues[index])

    operator fun set(index: Int, value: Mass) {
        rawValues[index] = value.rawValue
    }

    fun mean() = Mass(rawValues.sumOf { it } / rawValues.size)

    fun sum() = Mass(rawValues.sumOf { it })

    fun iterator(): MassIterator = MassIterator(rawValues.iterator())

    /**
    * @returns true if all elements of this array match the given predicate.
    */
    fun all(predicate: (Mass) -> Boolean): Boolean 
        = rawValues.all { predicate(Mass(it)) }

    /**
    * @returns true if this array contains at least one element.
    */
    fun any(): Boolean = rawValues.any()

    /**
    * @returns true if at least one element of this array matches the given predicate.
    */
    fun any(predicate: (Mass) -> Boolean): Boolean  = rawValues.any { predicate(Mass(it)) }

    /**
    * @returns true if this array has no elements.
    */
    fun none(): Boolean = rawValues.none()

    /**
    * @returns true if no element of this array matches the given predicate.
    */
    fun none(predicate: (Mass) -> Boolean): Boolean  = rawValues.none { predicate(Mass(it)) }

    fun <K, V> associate(transform: (Mass) -> Pair<K, V>): Map<K, V> 
        = rawValues.associate { transform(Mass(it)) }

    fun <K> associateBy(keySelector: (Mass) -> K): Map<K, Mass> 
        = associate { keySelector(it) to it }

    fun <K, V> associateBy(keySelector: (Mass) -> K, valueTransform: (Mass) -> V): Map<K, V> 
        = associate { keySelector(it) to valueTransform(it)}

    fun <V> associateWith(valueSelector: (Mass) -> V): Map<Mass, V> 
        = associate {it to valueSelector(it)}

    fun average(): Mass = mean()

    /**
    * Returns the largest element.
    */
    fun max(): Mass
        = Mass(rawValues.max())

    /**
    * Returns the smallest element.
    */
    fun min(): Mass
        = Mass(rawValues.min())

    /**
    * Returns a list containing only elements matching the given predicate.
    */
    fun filter(predicate: (Mass) -> Boolean): List<Mass>
        = buildList { 
            for (element in rawValues) {
                val converted = Mass(element)
                if (predicate(converted)) add(converted)
            }
         }

    /**
     * Returns a list containing the results of applying the given transform function to each element in the original array.
     */
     fun <R> map(transform: (Mass) -> R): List<R> =
        rawValues.map { transform(Mass(it)) }

    companion object {
        @JvmInline
        value class MassIterator internal constructor(val iterator: LongIterator): Iterator<Mass> {
            override fun next(): Mass = Mass(iterator.next())
            override fun hasNext(): Boolean = iterator.hasNext()
        }
    }
}


fun Collection<Mass>.toMassArray() = MassArray(this)

fun Array<Mass>.toMassArray() = MassArray(this)