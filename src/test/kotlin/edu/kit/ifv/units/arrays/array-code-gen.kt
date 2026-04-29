package edu.kit.ifv.units.arrays

import java.io.BufferedWriter
import java.nio.file.Path
import kotlin.io.path.Path
import kotlin.io.path.createFile
import kotlin.io.path.deleteIfExists


val allTypes: List<ArrayTypeDescriptor> = listOf(
    temperatureArray,
    areaArray,
)

val targetDirectory: String = "src/main/kotlin/edu/kit/ifv/units/arrays/"

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
    writer.writeClassAndConstructors(type)
    writer.writeExtensionFunctions(type)
    writer.newLine()
    writer.write("""
    }
    """.trimIndent()
    )
}

fun BufferedWriter.writeHead(type: ArrayTypeDescriptor) {
    write(
        """
        package edu.kit.ifv.units.arrays

        import edu.kit.ifv.units.${type.className}
    
        """.trimIndent()
    )
}

fun BufferedWriter.writeExtensionFunctions(type: ArrayTypeDescriptor) {
    for (function in type.functions) {

        write(function.print(type.className, type.rawValueType))
    }
}

fun BufferedWriter.writeClassAndConstructors(type: ArrayTypeDescriptor) {
    write(
        """
        @JvmInline
        value class ${type.className}Array internal constructor(private val rawValues: ${type.rawValueType}Array) {
            constructor(size: Int): this(${type.rawValueType}Array(size))
            constructor(size: Int, init: (index: Int) -> ${type.className}): this(${type.rawValueType}Array(size) { index -> init(index).rawValue })
            constructor(src: Array<${type.className}>): this(src.map { it.rawValue }.to${type.rawValueType}Array())
            constructor(src: Collection<${type.className}>): this(src.map { it.rawValue }.to${type.rawValueType}Array())
            
        """.trimIndent()
    )
}