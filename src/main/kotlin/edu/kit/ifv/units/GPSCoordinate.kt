@file:Suppress("unused")
package edu.kit.ifv.units


import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.math.tan

interface Coordinate {
    val latitudeRadians: Radians
    val longitudeRadians: Radians

    val latitudeDegrees: Double
        get() = latitudeRadians.toDegrees().toDouble()

    val longitudeDegrees: Double
        get() = longitudeRadians.toDegrees().toDouble()

    fun distance(other: Coordinate): Distance
}

// Certain numbers such as the radius of the earth or the fact that the maximum latitude is 90 does not feel like magic
@Suppress("MagicNumber")
class GPSCoordinate(
    override val latitudeRadians: Radians,
    override val longitudeRadians: Radians
) : Coordinate {


    override fun distance(other: Coordinate): Distance{
        val deltaLat = this.latitudeRadians.toDouble() - other.latitudeRadians.toDouble()
        val deltaLong = this.longitudeRadians.toDouble() - other.longitudeRadians.toDouble()

        val haversine =
            sin(deltaLat / 2).pow(2) + cos(this.latitudeRadians.toDouble()) *
                    cos(other.latitudeRadians.toDouble()) * sin(deltaLong / 2).pow(2)

        val normalized = 2 * asin(sqrt(haversine))
        val earthRadius = 6371.kilometers
        return earthRadius * normalized
    }

    override fun toString(): String {
        return "$latitudeDegrees°N $longitudeDegrees°E"
    }

    fun toUTM(): UTMPosition {
        return Converter.toUtm(latitudeRadians.toDegrees(), longitudeRadians.toDegrees())
    }

    override fun equals(other: Any?): Boolean {

        return other is GPSCoordinate
                && abs(latitudeRadians.toDouble() - other.latitudeRadians.toDouble()) < DEVIATION_MILLIMETERS
                && abs(longitudeRadians.toDouble() - other.longitudeRadians.toDouble()) < DEVIATION_MILLIMETERS

    }

    override fun hashCode(): Int {
        var result = (latitudeRadians.toDouble() * INVERSE_UPPER_BOUND_DEVIATION).roundToInt().hashCode()
        result = 31 * result + (longitudeRadians.toDouble() * INVERSE_UPPER_BOUND_DEVIATION).roundToInt().hashCode()
        return result
    }

    companion object {
        fun decimalDegree(lat: Double, lon: Double): GPSCoordinate {
            assert(lat in MIN_LAT..MAX_LAT)
            assert(lon in MIN_LONG..MAX_LON)
            return GPSCoordinate(lat.degrees.toRadians(), lon.degrees.toRadians())
        }
        fun degreesMinutes(lat: Int, latMinute : Double, lon: Int, lonMinute: Double): GPSCoordinate {
            assert(latMinute in 0.0..<60.0)
            assert(lonMinute in 0.0..<60.0)
            return decimalDegree(lat + latMinute / 60, lon + lonMinute / 60)
        }
        @Suppress("LongParameterList") //Unfortunately that is the amount of parameters when parsing this format
        fun degreesMinutesSeconds(
            lat: Int, latMinute: Int, latSecond: Double,
            lon: Int, lonMinute: Int, lonSecond: Double): GPSCoordinate {
            assert(latMinute in 0..59)
            assert(lonMinute in 0..59)
            assert(latSecond in 0.0..<60.0)
            assert(lonSecond in 0.0..<60.0)
            return degreesMinutes(lat, latMinute + latSecond / 60, lon, lonMinute + lonSecond / 60)
        }
    }
}

@Suppress("MagicNumber")
fun Pair<Number, Number>.toCoordinate(): GPSCoordinate {
    return GPSCoordinate.decimalDegree(this.first.toDouble(), this.second.toDouble())
}
/**
 * Source of the calculation https://github.com/Turbo87/utm/blob/master/utm/conversion.py python package adapted
 * to kotlin (Commit Hash 71bc564 at 25_01_2021)
 */


/**
 * This value is the difference in decimals degrees that is equal to a difference of a millimeters on the equator
 * between two coordinates. Note that the Inverse Upper bound for the deviation is significantly smaller than the actual
 * inverse for the hash function contract. A deviation in the scope of millimeters seems precise enough.
 */
const val DEVIATION_MILLIMETERS = 0.000000001 * 10 / 11
const val INVERSE_UPPER_BOUND_DEVIATION = 100000
private const val MAX_LAT = 90.0

private const val MIN_LAT = -MAX_LAT

private const val MAX_LON = 180.0

private const val MIN_LONG = -MAX_LON


@Suppress("MagicNumber") //The constants are well-founded for the earth spheroid
object Converter {


    private const val K0 = 0.9996

    private const val E = 0.00669438
    private const val E2 = E * E
    private const val E3 = E2 * E
    private const val E_P2 = E / (1 - E)
    private val SQRT_E = sqrt(1 - E)

    private val _E = (1 - SQRT_E) / (1 + SQRT_E)
    private val _E2 = _E * _E
    private val _E3 = _E2 * _E
    private val _E4 = _E3 * _E
    private val _E5 = _E4 * _E


    private const val M1 = (1 - E / 4 - 3 * E2 / 64 - 5 * E3 / 256)
    private const val M2 = (3 * E / 8 + 3 * E2 / 32 + 45 * E3 / 1024)
    private const val M3 = (15 * E2 / 256 + 45 * E3 / 1024)
    private const val M4 = (35 * E3 / 3072)

    private val P2 = (3.0 / 2 * _E - 27.0 / 32 * _E3 + 269.0 / 512 * _E5)
    private val P3 = (21.0 / 16 * _E2 - 55.0 / 32 * _E4)
    private val P4 = (151.0 / 96 * _E3 - 417.0 / 128 * _E5)
    private val P5 = (1097.0 / 512 * _E4)

    private const val R = 6378137

    /**
     * Unfortunately Norway exists - both the mainland and Spitsbergen break the usual UTM cell structure. This function
     * helps to determine the zone number with these two exceptions in mind
     */
    @Suppress("ReturnCount") // The return count is high because the number of exceptions is high
    private fun zoneNumber(lat: Double, lon: Double): Int {
        if(lat in 56.0..<64.0 && lon in 3.0..<12.0) {
            return 32
        }

        if (72 <= lat && lat < 84 && 0 <= lon) {
            when (lon) {
                in 0.0..<9.0 -> return 31
                in 9.0..<21.0 -> return 33
                in 21.0..<33.0 -> return 35
                in 33.0..<42.0 -> return 37
            }
        }
        return ((lon + 180) / 6).toInt() + 1

    }

    /**
     * For many UTM calculations the center meridian of the zone is required, sitting at in the middle of a 6° zone
     */
    private fun centralMeridian(i: Int): Degrees {
        return Degrees((i - 1) * 6.0 - 180 + 3)
    }

    /**
     * Calculates the value of an angle between -Pi and Pi
     */
    private fun angle(rad: Double): Double {
        return (rad + PI) % (2 * PI) - PI
    }
    fun toUtm(lat: Double, lon: Double): UTMPosition {
        return toUtm(Degrees(lat), Degrees(lon))
    }
    /**
     * Converts a given latitude and longitude into UTM format
     * @param lat the Latitude in degrees
     * @param lon the Longitude in degrees
     */
    fun toUtm(lat: Degrees, lon: Degrees): UTMPosition {
        assert(lat.toDouble() in -80.0..84.0)
        assert(lon.toDouble() in MIN_LONG..MAX_LON)
        val latRad = lat.toRadians().toDouble()

        val zone = zoneNumber(lat.toDouble(), lon.toDouble())

        val latSin = sin(latRad)
        val latCos = cos(latRad)

        val latTan = tan(latRad)
        val latTan2 = latTan.pow(2)
        val latTan4 = latTan.pow(4)

        val lonRad = lon.toRadians().toDouble()
        val centerRad = centralMeridian(zone).toRadians().toDouble()

        val n = R / sqrt(1 - E * latSin.pow(2))
        val c = E_P2 * latCos.pow(2)

        val a = latCos * angle(lonRad - centerRad)
        val a2 = a.pow(2)
        val a3 = a.pow(3)
        val a4 = a.pow(4)
        val a5 = a.pow(5)
        val a6 = a.pow(6)

        val m = R * (M1 * latRad -
                M2 * sin(2 * latRad) +
                M3 * sin(4 * latRad) -
                M4 * sin(6 * latRad))

        val east = K0 * n * (a +
                a3 / 6 * (1 - latTan2 + c) +
                a5 / 120 * (5 - 18 * latTan2 + latTan4 + 72 * c - 58 * E_P2)) + 500000


        val hemisphere = if (lat.toDouble() < 0) Hemisphere.SOUTHERN else Hemisphere.NORTHERN

        val north = K0 * (m + n * latTan * (a2 / 2 +
                a4 / 24 * (5 - latTan2 + 9 * c + 4 * c.pow(2)) +
                a6 / 720 * (61 - 58 * latTan2 + latTan4 + 600 * c - 330 * E_P2))) + hemisphere.offset

        return UTMPosition(east, north, zone, hemisphere)

    }

    /**
     * Converts a UTM coordinate into a latitude and longitude
     * @param east the Easting of the coordinate
     * @param north the Northing of the coordinate
     * @param zone the zone number
     * @param hemisphere the Hemisphere
     */
    fun toWGS84(east: Double, north: Double, zone: Int, hemisphere: Hemisphere): GPSCoordinate {
        val x = east - 500000
        val y = north - hemisphere.offset

        val m = y / K0
        val mu = m / (R * M1)

        val pRad = (mu +
                P2 * sin(2 * mu) +
                P3 * sin(4 * mu) +
                P4 * sin(6 * mu) +
                P5 * sin(8 * mu)
                )
        val pSin = sin(pRad)
        val pSin2 = pSin.pow(2)
        val pCos = cos(pRad)

        val pTan = tan(pRad)
        val pTan2 = pTan.pow(2)
        val pTan4 = pTan.pow(4)

        val epSin = 1 - E * pSin2
        val epSinSqrt = sqrt(epSin)

        val n = R / epSinSqrt
        val r = (1 - E) / epSin

        val c = E_P2 * pCos.pow(2)
        val c2 = c.pow(2)

        val d = x / (n * K0)
        val d2 = d.pow(2)
        val d3 = d.pow(3)
        val d4 = d.pow(4)
        val d5 = d.pow(5)
        val d6 = d.pow(6)

        val latitude = (pRad - (pTan / r) *
                (d2 / 2 -
                        d4 / 24 * (5 + 3 * pTan2 + 10 * c - 4 * c2 - 9 * E_P2)) +
                d6 / 720 * (61 + 90 * pTan2 + 298 * c + 45 * pTan4 - 252 * E_P2 - 3 * c2))

        val longitude = (d -
                d3 / 6 * (1 + 2 * pTan2 + c) +
                d5 / 120 * (5 - 2 * c + 28 * pTan2 - 3 * c2 + 8 * E_P2 + 24 * pTan4)) / pCos

        val l2 = angle(longitude + centralMeridian(zone).toRadians().toDouble())
        return GPSCoordinate(Radians(latitude), Radians(l2))
    }


}


/**
 * A class representing a UTM position consisting of an easting, a northing and a zone and hemisphere
 */
data class UTMPosition(val e: Double, val n: Double, val zone: Int, val hemisphere: Hemisphere) {
    fun toWGS84(): GPSCoordinate {
        return Converter.toWGS84(e, n, zone, hemisphere)
    }
}

/**
 * A representation of the hemispheres with the offset for UTM coordinate calculation
 */
enum class Hemisphere(val offset: Int) {
    NORTHERN(0),
    SOUTHERN(10000000)
}
