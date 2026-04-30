package edu.kit.ifv.units.arrays

import java.io.BufferedWriter
import java.nio.file.Path
import java.util.Locale
import java.util.Locale.getDefault
import kotlin.io.path.Path
import kotlin.io.path.createFile
import kotlin.io.path.deleteIfExists


val allTypes: List<ArrayTypeDescriptor> = listOf(
    accelerationArray,
    areaArray,
    cubicDurationArray,
    currencyArray,
    distanceArray,
    energyArray,
    forceArray,
    frequencyArray,
    impulseArray,
    massArray,
    powerArray,
    speedArray,
    squareDurationArray,
    temperatureArray,
    radiansArray,
    degreesArray,
    volumeArray
)

const val targetDirectory: String = "src/main/kotlin/edu/kit/ifv/units/arrays/"

fun main() {
    println("Generating arrays for types: ${allTypes.map { it.className }}")
    println("Target directory: $targetDirectory")
    for (type in allTypes) {
        val targetFile = Path(targetDirectory  + type.className + "Array.kt")
        prepareFile(targetFile)
        targetFile.toFile().bufferedWriter().use { out ->
            arrayFileContent(type, out)
        }
    }
}
fun prepareFile(dir: Path) {
    dir.deleteIfExists()
    dir.createFile()
}

fun arrayFileContent(type: ArrayTypeDescriptor, writer: BufferedWriter) {
    writer.writeHead(type)
    writer.writeClassDescription(type)
    writer.writeClassAndConstructors(type)
    writer.writeFunctions(type)
    writer.writeEndOfClass()
    writer.writeExtensionFunctions(type)
}

fun BufferedWriter.writeHead(type: ArrayTypeDescriptor) {
    write(
        """
        package edu.kit.ifv.units.arrays

        import edu.kit.ifv.units.${type.className}
        """.trimIndent()
    )
    newLine()
}

fun BufferedWriter.writeFunctions(type: ArrayTypeDescriptor) {
    for (function in type.functions) {
        newLine()
        writeIndented(function.print(type.className, type.rawValueType))
    }
}

fun BufferedWriter.writeExtensionFunctions(type: ArrayTypeDescriptor) {
    for (extensionFunction in type.extensionFunctions) {
        write(extensionFunction.print(type.className, type.rawValueType))
        newLine()
    }
}

fun BufferedWriter.writeClassDescription(type: ArrayTypeDescriptor) {
    newLine()
    write("""
        /**
        * This class is a typed array for ${type.className}. At runtime, it is converted to a regular java array
        * of the backing type ${type.rawValueType.lowercase(getDefault())} (${type.rawValueType.lowercase(getDefault())}[]).
        */
    """.trimIndent()
    )
}

fun BufferedWriter.writeEndOfClass() {
    newLine()
    write("}")
    newLine()
}

fun BufferedWriter.writeClassAndConstructors(type: ArrayTypeDescriptor) {
    newLine()
    write(
        """
        @JvmInline
        value class ${type.className}Array internal constructor(private val rawValues: ${type.rawValueType}Array) {
             
            // Developer-Note: This class was generated automatically and likely will again in the future, you might 
            // want to edit the code-generation instead of this class specifically. You can find the generating 
            // main-function in `src/test/edu/kit/ifv/units/arrays/ArrayGen.kt`.
            // The Gradle task `generateArrays` regenerates all Array classes.
            
            constructor(size: Int): this(${type.rawValueType}Array(size))
            constructor(size: Int, init: (index: Int) -> ${type.className}): this(${type.rawValueType}Array(size) { index -> init(index).rawValue })
            constructor(src: Array<${type.className}>): this(src.map { it.rawValue }.to${type.rawValueType}Array())
            constructor(src: Collection<${type.className}>): this(src.map { it.rawValue }.to${type.rawValueType}Array())
            
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