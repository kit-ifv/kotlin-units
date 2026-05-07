@file:Suppress("unused")
package edu.kit.ifv.units.arrays

import edu.kit.ifv.units.Degrees

/**
* This class is a typed array for Degrees. At runtime, it is converted to a regular java array
* of the backing type double (double[]).
*/
@JvmInline
value class DegreesArray internal constructor(private val rawValues: DoubleArray) {
     
    // Developer-Note: This class was generated automatically and likely will again in the future, you might 
    // want to edit the code-generation instead of this class specifically. You can find the generating 
    // array.main-function in `src/test/edu/kit/ifv/units/arrays/ArrayGen.kt`.
    // The Gradle task `generateArrays` regenerates all Array classes.
    
    constructor(size: Int): this(DoubleArray(size))
    
    constructor(size: Int, init: (index: Int) -> Degrees): 
        this(DoubleArray(size) { index -> init(index).rawValue })
        
    constructor(src: Array<Degrees>): this(src.map { it.rawValue }.toDoubleArray())
    
    constructor(src: Collection<Degrees>): this(src.map { it.rawValue }.toDoubleArray())

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

    operator fun get(index: Int) = Degrees(rawValues[index])

    operator fun set(index: Int, value: Degrees) {
        rawValues[index] = value.rawValue
    }

    fun mean() = Degrees(rawValues.sumOf { it } / rawValues.size)

    fun sum() = Degrees(rawValues.sumOf { it })

    fun iterator(): DegreesIterator = DegreesIterator(rawValues.iterator())

    /**
    * @returns true if all elements of this array match the given predicate.
    */
    fun all(predicate: (Degrees) -> Boolean): Boolean 
        = rawValues.all { predicate(Degrees(it)) }

    /**
    * @returns true if this array contains at least one element.
    */
    fun any(): Boolean = rawValues.any()

    /**
    * @returns true if at least one element of this array matches the given predicate.
    */
    fun any(predicate: (Degrees) -> Boolean): Boolean  = rawValues.any { predicate(Degrees(it)) }

    /**
    * @returns true if this array has no elements.
    */
    fun none(): Boolean = rawValues.none()

    /**
    * @returns true if no element of this array matches the given predicate.
    */
    fun none(predicate: (Degrees) -> Boolean): Boolean  = rawValues.none { predicate(Degrees(it)) }

    fun <K, V> associate(transform: (Degrees) -> Pair<K, V>): Map<K, V> 
        = rawValues.associate { transform(Degrees(it)) }

    fun <K> associateBy(keySelector: (Degrees) -> K): Map<K, Degrees> 
        = associate { keySelector(it) to it }

    fun <K, V> associateBy(keySelector: (Degrees) -> K, valueTransform: (Degrees) -> V): Map<K, V> 
        = associate { keySelector(it) to valueTransform(it)}

    fun <V> associateWith(valueSelector: (Degrees) -> V): Map<Degrees, V> 
        = associate {it to valueSelector(it)}

    fun average(): Degrees = mean()

    /**
    * Returns the largest element.
    */
    fun max(): Degrees
        = Degrees(rawValues.max())

    /**
    * Returns the smallest element.
    */
    fun min(): Degrees
        = Degrees(rawValues.min())

    /**
    * Returns a list containing only elements matching the given predicate.
    */
    fun filter(predicate: (Degrees) -> Boolean): List<Degrees>
        = buildList { 
            for (element in rawValues) {
                val converted = Degrees(element)
                if (predicate(converted)) add(converted)
            }
         }

    /**
     * Returns a list containing the results of applying the given transform function to each element in the original array.
     */
     fun <R> map(transform: (Degrees) -> R): List<R> =
        rawValues.map { transform(Degrees(it)) }

    companion object {
        @JvmInline
        value class DegreesIterator internal constructor(val iterator: DoubleIterator): Iterator<Degrees> {
            override fun next(): Degrees = Degrees(iterator.next())
            override fun hasNext(): Boolean = iterator.hasNext()
        }
    }
}


fun Collection<Degrees>.toDegreesArray() = DegreesArray(this)

fun Array<Degrees>.toDegreesArray() = DegreesArray(this)