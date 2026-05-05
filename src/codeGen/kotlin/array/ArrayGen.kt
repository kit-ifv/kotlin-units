package array

import java.io.BufferedWriter
import java.nio.file.Path
import java.util.Locale.getDefault
import kotlin.io.path.Path
import kotlin.io.path.createFile
import kotlin.io.path.createParentDirectories
import kotlin.io.path.deleteIfExists

const val targetDirectory: String = "src/main/kotlin/edu/kit/ifv/units/arrays/"

/**
 * Run this class to generate all arrays in the `targetDirectory`. You can also run the Gradle task `generateArrays`
 * under `other`.
 */
fun main() {
    println("Generating arrays for types: ${ArrayType.entries.map { it.className }}")
    println("Target directory: $targetDirectory")
    for (type in ArrayType.entries) {
        val targetFile = Path(targetDirectory  + type.className + "Array.kt")
        prepareFile(targetFile)

        targetFile.toFile().bufferedWriter().use { out ->
            arrayFileContent(type, out)
        }
    }
}

fun prepareFile(file: Path) {
    file.deleteIfExists()
    file.createParentDirectories()
    if(!file.toFile().exists()) file.createFile()
}

fun arrayFileContent(type: ArrayType, writer: BufferedWriter) {
    writer.writeHead(type)
    writer.writeClassDescription(type)
    writer.writeClassAndConstructors(type)
    writer.writeClassValues(type)
    writer.writeFunctions(type)
    writer.writeCompanion(type)
    writer.writeEndOfClass()
    writer.writeExtensionFunctions(type)
}

fun BufferedWriter.writeHead(type: ArrayType) {
    write(
        """
        package edu.kit.ifv.units.arrays
        """.trimIndent()
    )
    newLine()
    newLine()
    write(type.imports)
    newLine()
}

fun BufferedWriter.writeFunctions(type: ArrayType) {
    for (function in type.functions) {
        newLine()
        newLine()
        writeIndented(function.print(type))
    }
}

fun BufferedWriter.writeClassValues(type: ArrayType) {
    for (value in type.classValues) {
        newLine()
        newLine()
        writeIndented(value.print(type.className, type.rawValueType))
    }
}

fun BufferedWriter.writeExtensionFunctions(type: ArrayType) {
    for (extensionFunction in type.extensionFunctions) {
        newLine()
        newLine()
        write(extensionFunction.print(type))
    }
}

fun BufferedWriter.writeClassDescription(type: ArrayType) {
    newLine()
    write("""
        /**
        * This class is a typed array for ${type.className}. At runtime, it is converted to a regular java array
        * of the backing type ${type.rawValueType.lowercase(getDefault())} (${type.rawValueType.lowercase(getDefault())}[]).
        */
    """.trimIndent()
    )
}

fun BufferedWriter.writeCompanion(type: ArrayType) {
    newLine()
    newLine()
    writeIndented("""
        companion object {
            @JvmInline
            value class ${type.className}Iterator internal constructor(val iterator: ${type.rawValueType}Iterator): Iterator<${type.className}> {
                override fun next(): ${type.className} = ${type.constructor("iterator.next()")}
                override fun hasNext(): Boolean = iterator.hasNext()
            }
        }
    """.trimIndent())

}

fun BufferedWriter.writeEndOfClass() {
    newLine()
    write("}")
    newLine()
}

fun BufferedWriter.writeClassAndConstructors(type: ArrayType) {
    newLine()
    write(
        """
        @JvmInline
        value class ${type.className}Array internal constructor(private val rawValues: ${type.rawValueType}Array) {
             
            // Developer-Note: This class was generated automatically and likely will again in the future, you might 
            // want to edit the code-generation instead of this class specifically. You can find the generating 
            // array.main-function in `src/test/edu/kit/ifv/units/arrays/ArrayGen.kt`.
            // The Gradle task `generateArrays` regenerates all Array classes.
            
            constructor(size: Int): this(${type.rawValueType}Array(size))
            
            constructor(size: Int, init: (index: Int) -> ${type.className}): 
                this(${type.rawValueType}Array(size) { index -> ${type.rawValueAccess("init(index)")} })
                
            constructor(src: Array<${type.className}>): this(src.map { ${type.rawValueAccess("it")} }.to${type.rawValueType}Array())
            
            constructor(src: Collection<${type.className}>): this(src.map { ${type.rawValueAccess("it")} }.to${type.rawValueType}Array())
        """.trimIndent()
    )
}

/**
 * Writes all lines of the string indented by `level * oneIndentSpaces` spaces.
 */
fun BufferedWriter.writeIndented(content: String, level: Int = 1, oneIndentSpaces: Int = 4) {
    val indent = " ".repeat(level * oneIndentSpaces)
    val last = content.lines().size - 1
    for ( (index, line) in content.lines().mapIndexed { index, line -> index to line }) {
        write(indent + line)
        if (index != last) newLine()
    }
}