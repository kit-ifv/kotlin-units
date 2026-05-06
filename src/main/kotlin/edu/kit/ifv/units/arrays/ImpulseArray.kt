@file:Suppress("unused")
package edu.kit.ifv.units.arrays

import edu.kit.ifv.units.Impulse

/**
* This class is a typed array for Impulse. At runtime, it is converted to a regular java array
* of the backing type double (double[]).
*/
@JvmInline
value class ImpulseArray internal constructor(private val rawValues: DoubleArray) {
     
    // Developer-Note: This class was generated automatically and likely will again in the future, you might 
    // want to edit the code-generation instead of this class specifically. You can find the generating 
    // array.main-function in `src/test/edu/kit/ifv/units/arrays/ArrayGen.kt`.
    // The Gradle task `generateArrays` regenerates all Array classes.
    
    constructor(size: Int): this(DoubleArray(size))
    
    constructor(size: Int, init: (index: Int) -> Impulse): 
        this(DoubleArray(size) { index -> init(index).rawValue })
        
    constructor(src: Array<Impulse>): this(src.map { it.rawValue }.toDoubleArray())
    
    constructor(src: Collection<Impulse>): this(src.map { it.rawValue }.toDoubleArray())

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

    operator fun get(index: Int) = Impulse(rawValues[index])

    operator fun set(index: Int, value: Impulse) {
        rawValues[index] = value.rawValue
    }

    fun mean() = Impulse(rawValues.sum() / rawValues.size)

    fun sum() = Impulse(rawValues.sum())

    fun iterator(): ImpulseIterator = ImpulseIterator(rawValues.iterator())

    /**
    * @returns true if all elements of this array match the given predicate.
    */
    fun all(predicate: (Impulse) -> Boolean): Boolean 
        = rawValues.all { predicate(Impulse(it)) }

    /**
    * @returns true if this array contains at least one element.
    */
    fun any(): Boolean = rawValues.any()

    /**
    * @returns true if at least one element of this array matches the given predicate.
    */
    fun any(predicate: (Impulse) -> Boolean): Boolean  = rawValues.any { predicate(Impulse(it)) }

    /**
    * @returns true if this array has no elements.
    */
    fun none(): Boolean = rawValues.none()

    /**
    * @returns true if no element of this array matches the given predicate.
    */
    fun none(predicate: (Impulse) -> Boolean): Boolean  = rawValues.none { predicate(Impulse(it)) }

    fun <K, V> associate(transform: (Impulse) -> Pair<K, V>): Map<K, V> 
        = rawValues.associate { transform(Impulse(it)) }

    fun <K> associateBy(keySelector: (Impulse) -> K): Map<K, Impulse> 
        = associate { keySelector(it) to it }

    fun <K, V> associateBy(keySelector: (Impulse) -> K, valueTransform: (Impulse) -> V): Map<K, V> 
        = associate { keySelector(it) to valueTransform(it)}

    fun <V> associateWith(valueSelector: (Impulse) -> V): Map<Impulse, V> 
        = associate {it to valueSelector(it)}

    fun average(): Impulse
        = Impulse(rawValues.average().toDouble())

    /**
    * Returns the largest element.
    */
    fun max(): Impulse
        = Impulse(rawValues.max())

    /**
    * Returns the smallest element.
    */
    fun min(): Impulse
        = Impulse(rawValues.min())

    /**
    * Returns a list containing only elements matching the given predicate.
    */
    fun filter(predicate: (Impulse) -> Boolean): List<Impulse>
        = buildList { 
            for (element in rawValues) {
                val converted = Impulse(element)
                if (predicate(converted)) add(converted)
            }
         }

    /**
     * Returns a list containing the results of applying the given transform function to each element in the original array.
     */
     fun <R> map(transform: (Impulse) -> R): List<R> =
        rawValues.map { transform(Impulse(it)) }

    companion object {
        @JvmInline
        value class ImpulseIterator internal constructor(val iterator: DoubleIterator): Iterator<Impulse> {
            override fun next(): Impulse = Impulse(iterator.next())
            override fun hasNext(): Boolean = iterator.hasNext()
        }
    }
}


fun Collection<Impulse>.toImpulseArray() = ImpulseArray(this)

fun Array<Impulse>.toImpulseArray() = ImpulseArray(this)