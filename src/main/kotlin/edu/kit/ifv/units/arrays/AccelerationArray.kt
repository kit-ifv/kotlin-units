@file:Suppress("unused")
package edu.kit.ifv.units.arrays

import edu.kit.ifv.units.Acceleration

/**
* This class is a typed array for Acceleration. At runtime, it is converted to a regular java array
* of the backing type double (double[]).
*/
@JvmInline
value class AccelerationArray internal constructor(private val rawValues: DoubleArray) {
     
    // Developer-Note: This class was generated automatically and likely will again in the future, you might 
    // want to edit the code-generation instead of this class specifically. You can find the generating 
    // array.main-function in `src/test/edu/kit/ifv/units/arrays/ArrayGen.kt`.
    // The Gradle task `generateArrays` regenerates all Array classes.
    
    constructor(size: Int): this(DoubleArray(size))
    
    constructor(size: Int, init: (index: Int) -> Acceleration): 
        this(DoubleArray(size) { index -> init(index).rawValue })
        
    constructor(src: Array<Acceleration>): this(src.map { it.rawValue }.toDoubleArray())
    
    constructor(src: Collection<Acceleration>): this(src.map { it.rawValue }.toDoubleArray())

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

    operator fun get(index: Int) = Acceleration(rawValues[index])

    operator fun set(index: Int, value: Acceleration) {
        rawValues[index] = value.rawValue
    }

    fun mean() = Acceleration(rawValues.sum() / rawValues.size)

    fun sum() = Acceleration(rawValues.sum())

    fun iterator(): AccelerationIterator = AccelerationIterator(rawValues.iterator())

    /**
    * @returns true if all elements of this array match the given predicate.
    */
    fun all(predicate: (Acceleration) -> Boolean): Boolean 
        = rawValues.all { predicate(Acceleration(it)) }

    /**
    * @returns true if this array contains at least one element.
    */
    fun any(): Boolean = rawValues.any()

    /**
    * @returns true if at least one element of this array matches the given predicate.
    */
    fun any(predicate: (Acceleration) -> Boolean): Boolean  = rawValues.any { predicate(Acceleration(it)) }

    /**
    * @returns true if this array has no elements.
    */
    fun none(): Boolean = rawValues.none()

    /**
    * @returns true if no element of this array matches the given predicate.
    */
    fun none(predicate: (Acceleration) -> Boolean): Boolean  = rawValues.none { predicate(Acceleration(it)) }

    fun <K, V> associate(transform: (Acceleration) -> Pair<K, V>): Map<K, V> 
        = rawValues.associate { transform(Acceleration(it)) }

    fun <K> associateBy(keySelector: (Acceleration) -> K): Map<K, Acceleration> 
        = associate { keySelector(it) to it }

    fun <K, V> associateBy(keySelector: (Acceleration) -> K, valueTransform: (Acceleration) -> V): Map<K, V> 
        = associate { keySelector(it) to valueTransform(it)}

    fun <V> associateWith(valueSelector: (Acceleration) -> V): Map<Acceleration, V> 
        = associate {it to valueSelector(it)}

    fun average(): Acceleration
        = Acceleration(rawValues.average().toDouble())

    /**
    * Returns the largest element.
    */
    fun max(): Acceleration
        = Acceleration(rawValues.max())

    /**
    * Returns the smallest element.
    */
    fun min(): Acceleration
        = Acceleration(rawValues.min())

    /**
    * Returns a list containing only elements matching the given predicate.
    */
    fun filter(predicate: (Acceleration) -> Boolean): List<Acceleration>
        = buildList { 
            for (element in rawValues) {
                val converted = Acceleration(element)
                if (predicate(converted)) add(converted)
            }
         }

    /**
     * Returns a list containing the results of applying the given transform function to each element in the original array.
     */
     fun <R> map(transform: (Acceleration) -> R): List<R> =
        rawValues.map { transform(Acceleration(it)) }

    companion object {
        @JvmInline
        value class AccelerationIterator internal constructor(val iterator: DoubleIterator): Iterator<Acceleration> {
            override fun next(): Acceleration = Acceleration(iterator.next())
            override fun hasNext(): Boolean = iterator.hasNext()
        }
    }
}


fun Collection<Acceleration>.toAccelerationArray() = AccelerationArray(this)

fun Array<Acceleration>.toAccelerationArray() = AccelerationArray(this)