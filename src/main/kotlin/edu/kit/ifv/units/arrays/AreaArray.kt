@file:Suppress("unused")
package edu.kit.ifv.units.arrays

import edu.kit.ifv.units.Area

/**
* This class is a typed array for Area. At runtime, it is converted to a regular java array
* of the backing type double (double[]).
*/
@JvmInline
value class AreaArray internal constructor(private val rawValues: DoubleArray) {
     
    // Developer-Note: This class was generated automatically and likely will again in the future, you might 
    // want to edit the code-generation instead of this class specifically. You can find the generating 
    // array.main-function in `src/test/edu/kit/ifv/units/arrays/ArrayGen.kt`.
    // The Gradle task `generateArrays` regenerates all Array classes.
    
    constructor(size: Int): this(DoubleArray(size))
    
    constructor(size: Int, init: (index: Int) -> Area): 
        this(DoubleArray(size) { index -> init(index).rawValue })
        
    constructor(src: Array<Area>): this(src.map { it.rawValue }.toDoubleArray())
    
    constructor(src: Collection<Area>): this(src.map { it.rawValue }.toDoubleArray())

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

    operator fun get(index: Int) = Area(rawValues[index])

    operator fun set(index: Int, value: Area) {
        rawValues[index] = value.rawValue
    }

    fun mean() = Area(rawValues.sum() / rawValues.size)

    fun sum() = Area(rawValues.sum())

    fun iterator(): AreaIterator = AreaIterator(rawValues.iterator())

    /**
    * @returns true if all elements of this array match the given predicate.
    */
    fun all(predicate: (Area) -> Boolean): Boolean 
        = rawValues.all { predicate(Area(it)) }

    /**
    * @returns true if this array contains at least one element.
    */
    fun any(): Boolean = rawValues.any()

    /**
    * @returns true if at least one element of this array matches the given predicate.
    */
    fun any(predicate: (Area) -> Boolean): Boolean  = rawValues.any { predicate(Area(it)) }

    /**
    * @returns true if this array has no elements.
    */
    fun none(): Boolean = rawValues.none()

    /**
    * @returns true if no element of this array matches the given predicate.
    */
    fun none(predicate: (Area) -> Boolean): Boolean  = rawValues.none { predicate(Area(it)) }

    fun <K, V> associate(transform: (Area) -> Pair<K, V>): Map<K, V> 
        = rawValues.associate { transform(Area(it)) }

    fun <K> associateBy(keySelector: (Area) -> K): Map<K, Area> 
        = associate { keySelector(it) to it }

    fun <K, V> associateBy(keySelector: (Area) -> K, valueTransform: (Area) -> V): Map<K, V> 
        = associate { keySelector(it) to valueTransform(it)}

    fun <V> associateWith(valueSelector: (Area) -> V): Map<Area, V> 
        = associate {it to valueSelector(it)}

    fun average(): Area
        = Area(rawValues.average().toDouble())

    /**
    * Returns the largest element.
    */
    fun max(): Area
        = Area(rawValues.max())

    /**
    * Returns the smallest element.
    */
    fun min(): Area
        = Area(rawValues.min())

    /**
    * Returns a list containing only elements matching the given predicate.
    */
    fun filter(predicate: (Area) -> Boolean): List<Area>
        = buildList { 
            for (element in rawValues) {
                val converted = Area(element)
                if (predicate(converted)) add(converted)
            }
         }

    /**
     * Returns a list containing the results of applying the given transform function to each element in the original array.
     */
     fun <R> map(transform: (Area) -> R): List<R> =
        rawValues.map { transform(Area(it)) }

    companion object {
        @JvmInline
        value class AreaIterator internal constructor(val iterator: DoubleIterator): Iterator<Area> {
            override fun next(): Area = Area(iterator.next())
            override fun hasNext(): Boolean = iterator.hasNext()
        }
    }
}


fun Collection<Area>.toAreaArray() = AreaArray(this)

fun Array<Area>.toAreaArray() = AreaArray(this)