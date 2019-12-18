package util

import kotlin.math.atan2
import kotlin.math.max
import kotlin.math.min

inline fun <T> timer(message: String, code: () -> T):T {
    val t1 = System.nanoTime()
    try {
       return code()
    } finally {
        val t2 = System.nanoTime()
        println("$message: ${t2 - t1} ns")
    }
}

fun gcd(a: Int, b: Int): Int {
    var x = max(a, b)
    var y = min(a, b)
    var r = x % y
    while (r != 0) {
        x = max(y, r)
        y = min(y, r)
        r = x % y
    }
    return y
}

fun gcd(a: Long, b: Long): Long {
    var x = max(a, b)
    var y = min(a, b)
    var r = x % y
    while (r != 0L) {
        x = max(y, r)
        y = min(y, r)
        r = x % y
    }
    return y
}

/**
 * Clockwise (starting from N and going E, S, W) angle in rad
 * (atan2 with X and Y swapped and X negated to get the starting point and direction right)
 */
fun clockwiseAngle(v: Vector): Double = atan2(-v.dx.toDouble(), v.dy.toDouble())

fun abort(message: String): Nothing = throw IllegalStateException(message)
