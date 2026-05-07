@file:Suppress("unused")
package edu.kit.ifv.units.arrays

import edu.kit.ifv.units.Force

/**
* This class is a typed array for Force. At runtime, it is converted to a regular java array
* of the backing type double (double[]).
*/
@JvmInline
value class ForceArray internal constructor(private val rawValues: DoubleArray) {
     
    // Developer-Note: This class was generated automatically and likely will again in the future, you might 
    // want to edit the code-generation instead of this class specifically. You can find the generating 
    // array.main-function in `src/test/edu/kit/ifv/units/arrays/ArrayGen.kt`.
    // The Gradle task `generateArrays` regenerates all Array classes.
    
    constructor(size: Int): this(DoubleArray(size))
    
    constructor(size: Int, init: (index: Int) -> Force): 
        this(DoubleArray(size) { index -> init(index).rawValue })
        
    constructor(src: Array<Force>): this(src.map { it.rawValue }.toDoubleArray())
    
    constructor(src: Collection<Force>): this(src.map { it.rawValue }.toDoubleArray())

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

    operator fun get(index: Int) = Force(rawValues[index])

    operator fun set(index: Int, value: Force) {
        rawValues[index] = value.rawValue
    }

    fun mean() = Force(rawValues.sumOf { it } / rawValues.size)

    fun sum() = Force(rawValues.sumOf { it })

    fun iterator(): ForceIterator = ForceIterator(rawValues.iterator())

    /**
    * @returns true if all elements of this array match the given predicate.
    */
    fun all(predicate: (Force) -> Boolean): Boolean 
        = rawValues.all { predicate(Force(it)) }

    /**
    * @returns true if this array contains at least one element.
    */
    fun any(): Boolean = rawValues.any()

    /**
    * @returns true if at least one element of this array matches the given predicate.
    */
    fun any(predicate: (Force) -> Boolean): Boolean  = rawValues.any { predicate(Force(it)) }

    /**
    * @returns true if this array has no elements.
    */
    fun none(): Boolean = rawValues.none()

    /**
    * @returns true if no element of this array matches the given predicate.
    */
    fun none(predicate: (Force) -> Boolean): Boolean  = rawValues.none { predicate(Force(it)) }

    fun <K, V> associate(transform: (Force) -> Pair<K, V>): Map<K, V> 
        = rawValues.associate { transform(Force(it)) }

    fun <K> associateBy(keySelector: (Force) -> K): Map<K, Force> 
        = associate { keySelector(it) to it }

    fun <K, V> associateBy(keySelector: (Force) -> K, valueTransform: (Force) -> V): Map<K, V> 
        = associate { keySelector(it) to valueTransform(it)}

    fun <V> associateWith(valueSelector: (Force) -> V): Map<Force, V> 
        = associate {it to valueSelector(it)}

    fun average(): Force = mean()

    /**
    * Returns the largest element.
    */
    fun max(): Force
        = Force(rawValues.max())

    /**
    * Returns the smallest element.
    */
    fun min(): Force
        = Force(rawValues.min())

    /**
    * Returns a list containing only elements matching the given predicate.
    */
    fun filter(predicate: (Force) -> Boolean): List<Force>
        = buildList { 
            for (element in rawValues) {
                val converted = Force(element)
                if (predicate(converted)) add(converted)
            }
         }

    /**
     * Returns a list containing the results of applying the given transform function to each element in the original array.
     */
     fun <R> map(transform: (Force) -> R): List<R> =
        rawValues.map { transform(Force(it)) }

    companion object {
        @JvmInline
        value class ForceIterator internal constructor(val iterator: DoubleIterator): Iterator<Force> {
            override fun next(): Force = Force(iterator.next())
            override fun hasNext(): Boolean = iterator.hasNext()
        }
    }
}


fun Collection<Force>.toForceArray() = ForceArray(this)

fun Array<Force>.toForceArray() = ForceArray(this)