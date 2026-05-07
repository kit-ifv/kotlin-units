@file:Suppress("unused")
package edu.kit.ifv.units.arrays

import edu.kit.ifv.units.Energy

/**
* This class is a typed array for Energy. At runtime, it is converted to a regular java array
* of the backing type double (double[]).
*/
@JvmInline
value class EnergyArray internal constructor(private val rawValues: DoubleArray) {
     
    // Developer-Note: This class was generated automatically and likely will again in the future, you might 
    // want to edit the code-generation instead of this class specifically. You can find the generating 
    // array.main-function in `src/test/edu/kit/ifv/units/arrays/ArrayGen.kt`.
    // The Gradle task `generateArrays` regenerates all Array classes.
    
    constructor(size: Int): this(DoubleArray(size))
    
    constructor(size: Int, init: (index: Int) -> Energy): 
        this(DoubleArray(size) { index -> init(index).rawValue })
        
    constructor(src: Array<Energy>): this(src.map { it.rawValue }.toDoubleArray())
    
    constructor(src: Collection<Energy>): this(src.map { it.rawValue }.toDoubleArray())

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

    operator fun get(index: Int) = Energy(rawValues[index])

    operator fun set(index: Int, value: Energy) {
        rawValues[index] = value.rawValue
    }

    fun mean() = Energy(rawValues.sumOf { it } / rawValues.size)

    fun sum() = Energy(rawValues.sumOf { it })

    fun iterator(): EnergyIterator = EnergyIterator(rawValues.iterator())

    /**
    * @returns true if all elements of this array match the given predicate.
    */
    fun all(predicate: (Energy) -> Boolean): Boolean 
        = rawValues.all { predicate(Energy(it)) }

    /**
    * @returns true if this array contains at least one element.
    */
    fun any(): Boolean = rawValues.any()

    /**
    * @returns true if at least one element of this array matches the given predicate.
    */
    fun any(predicate: (Energy) -> Boolean): Boolean  = rawValues.any { predicate(Energy(it)) }

    /**
    * @returns true if this array has no elements.
    */
    fun none(): Boolean = rawValues.none()

    /**
    * @returns true if no element of this array matches the given predicate.
    */
    fun none(predicate: (Energy) -> Boolean): Boolean  = rawValues.none { predicate(Energy(it)) }

    fun <K, V> associate(transform: (Energy) -> Pair<K, V>): Map<K, V> 
        = rawValues.associate { transform(Energy(it)) }

    fun <K> associateBy(keySelector: (Energy) -> K): Map<K, Energy> 
        = associate { keySelector(it) to it }

    fun <K, V> associateBy(keySelector: (Energy) -> K, valueTransform: (Energy) -> V): Map<K, V> 
        = associate { keySelector(it) to valueTransform(it)}

    fun <V> associateWith(valueSelector: (Energy) -> V): Map<Energy, V> 
        = associate {it to valueSelector(it)}

    fun average(): Energy = mean()

    /**
    * Returns the largest element.
    */
    fun max(): Energy
        = Energy(rawValues.max())

    /**
    * Returns the smallest element.
    */
    fun min(): Energy
        = Energy(rawValues.min())

    /**
    * Returns a list containing only elements matching the given predicate.
    */
    fun filter(predicate: (Energy) -> Boolean): List<Energy>
        = buildList { 
            for (element in rawValues) {
                val converted = Energy(element)
                if (predicate(converted)) add(converted)
            }
         }

    /**
     * Returns a list containing the results of applying the given transform function to each element in the original array.
     */
     fun <R> map(transform: (Energy) -> R): List<R> =
        rawValues.map { transform(Energy(it)) }

    companion object {
        @JvmInline
        value class EnergyIterator internal constructor(val iterator: DoubleIterator): Iterator<Energy> {
            override fun next(): Energy = Energy(iterator.next())
            override fun hasNext(): Boolean = iterator.hasNext()
        }
    }
}


fun Collection<Energy>.toEnergyArray() = EnergyArray(this)

fun Array<Energy>.toEnergyArray() = EnergyArray(this)