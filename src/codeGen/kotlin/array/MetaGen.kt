package array

import java.util.Locale.getDefault


val functions_to_convert = listOf(
    "fun <K, V> IntArray.associate(transform: (Int) -> Pair<K, V>): Map<K, V>",
    "fun <K> IntArray.associateBy(keySelector: (Int) -> K): Map<K, Int>",
    "fun <K, V> IntArray.associateBy(keySelector: (Int) -> K, valueTransform: (Int) -> V): Map<K, V>"
)
fun main() {
    for (function in functions_to_convert) {
        val functionName = function
            .replaceBefore('.', "")
            .replaceAfter('(', "")
            .replace(".", "")
            .replace("(", "")

        val cleaned = function
            .replace("IntArray.", "")
            .replace("Int", "\${type.className}")

        val full = """
            ${functionName.uppercase(getDefault())}({type ->
                ${"\"\"\""}
                    $cleaned = rawValues.$functionName()
                ${"\"\"\""}.trimIndent()
            }),
        """.trimIndent()
        println(full)
    }
}