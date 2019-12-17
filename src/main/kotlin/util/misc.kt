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
    val x = max(a, b)
    val y = min(a, b)
    val r = x % y
    return if (r == 0) y else gcd(y, r)
}

/**
 * Clockwise (starting from N and going E, S, W) angle in rad
 * (atan2 with X and Y swapped and X negated to get the starting point and direction right)
 */
fun clockwiseAngle(v: Vector): Double = atan2(-v.dx.toDouble(), v.dy.toDouble())

fun abort(message: String): Nothing = throw IllegalStateException(message)
