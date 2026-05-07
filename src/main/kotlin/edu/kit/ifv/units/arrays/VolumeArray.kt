@file:Suppress("unused")
package edu.kit.ifv.units.arrays

import edu.kit.ifv.units.Volume

/**
* This class is a typed array for Volume. At runtime, it is converted to a regular java array
* of the backing type double (double[]).
*/
@JvmInline
value class VolumeArray internal constructor(private val rawValues: DoubleArray) {
     
    // Developer-Note: This class was generated automatically and likely will again in the future, you might 
    // want to edit the code-generation instead of this class specifically. You can find the generating 
    // array.main-function in `src/test/edu/kit/ifv/units/arrays/ArrayGen.kt`.
    // The Gradle task `generateArrays` regenerates all Array classes.
    
    constructor(size: Int): this(DoubleArray(size))
    
    constructor(size: Int, init: (index: Int) -> Volume): 
        this(DoubleArray(size) { index -> init(index).rawValue })
        
    constructor(src: Array<Volume>): this(src.map { it.rawValue }.toDoubleArray())
    
    constructor(src: Collection<Volume>): this(src.map { it.rawValue }.toDoubleArray())

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

    operator fun get(index: Int) = Volume(rawValues[index])

    operator fun set(index: Int, value: Volume) {
        rawValues[index] = value.rawValue
    }

    fun mean() = Volume(rawValues.sumOf { it } / rawValues.size)

    fun sum() = Volume(rawValues.sumOf { it })

    fun iterator(): VolumeIterator = VolumeIterator(rawValues.iterator())

    /**
    * @returns true if all elements of this array match the given predicate.
    */
    fun all(predicate: (Volume) -> Boolean): Boolean 
        = rawValues.all { predicate(Volume(it)) }

    /**
    * @returns true if this array contains at least one element.
    */
    fun any(): Boolean = rawValues.any()

    /**
    * @returns true if at least one element of this array matches the given predicate.
    */
    fun any(predicate: (Volume) -> Boolean): Boolean  = rawValues.any { predicate(Volume(it)) }

    /**
    * @returns true if this array has no elements.
    */
    fun none(): Boolean = rawValues.none()

    /**
    * @returns true if no element of this array matches the given predicate.
    */
    fun none(predicate: (Volume) -> Boolean): Boolean  = rawValues.none { predicate(Volume(it)) }

    fun <K, V> associate(transform: (Volume) -> Pair<K, V>): Map<K, V> 
        = rawValues.associate { transform(Volume(it)) }

    fun <K> associateBy(keySelector: (Volume) -> K): Map<K, Volume> 
        = associate { keySelector(it) to it }

    fun <K, V> associateBy(keySelector: (Volume) -> K, valueTransform: (Volume) -> V): Map<K, V> 
        = associate { keySelector(it) to valueTransform(it)}

    fun <V> associateWith(valueSelector: (Volume) -> V): Map<Volume, V> 
        = associate {it to valueSelector(it)}

    fun average(): Volume = mean()

    /**
    * Returns the largest element.
    */
    fun max(): Volume
        = Volume(rawValues.max())

    /**
    * Returns the smallest element.
    */
    fun min(): Volume
        = Volume(rawValues.min())

    /**
    * Returns a list containing only elements matching the given predicate.
    */
    fun filter(predicate: (Volume) -> Boolean): List<Volume>
        = buildList { 
            for (element in rawValues) {
                val converted = Volume(element)
                if (predicate(converted)) add(converted)
            }
         }

    /**
     * Returns a list containing the results of applying the given transform function to each element in the original array.
     */
     fun <R> map(transform: (Volume) -> R): List<R> =
        rawValues.map { transform(Volume(it)) }

    companion object {
        @JvmInline
        value class VolumeIterator internal constructor(val iterator: DoubleIterator): Iterator<Volume> {
            override fun next(): Volume = Volume(iterator.next())
            override fun hasNext(): Boolean = iterator.hasNext()
        }
    }
}


fun Collection<Volume>.toVolumeArray() = VolumeArray(this)

fun Array<Volume>.toVolumeArray() = VolumeArray(this)