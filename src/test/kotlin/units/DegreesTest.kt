package units

import org.junit.jupiter.api.Assertions.*
import kotlin.math.PI
import kotlin.test.Test

class DegreesTest {

    @Test
    fun convert() {
        val test = 180.degrees
        assertEquals(test.toRadians().toDouble(),  PI)
        assertEquals(test.toRadians().toDegrees(), test)
    }
}