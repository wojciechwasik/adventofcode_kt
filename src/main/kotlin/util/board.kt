package util

import kotlin.math.abs
import kotlin.math.sign

data class Point(val x: Int, val y: Int)

data class Vector(val dx: Int, val dy: Int) {

    constructor(from: Point, to: Point): this(to.x - from.x, to.y - from.y)

    fun getDirection():Vector {
        if (dx != 0 && dy != 0) {
            val u = gcd(abs(dx), abs(dy))
            return Vector(dx / u, dy / u)
        }
        else return Vector(1 * dx.sign, 1 * dy.sign)
    }
}

enum class Direction {
    N {
        override fun move(p: Point) = Point(p.x,p.y - 1)
        override fun turnLeft() = W
        override fun turnRight() = E
    },
    E {
        override fun move(p: Point) = Point(p.x + 1,p.y)
        override fun turnLeft() = N
        override fun turnRight() = S
    },
    S {
        override fun move(p: Point) = Point(p.x,p.y + 1)
        override fun turnLeft() = E
        override fun turnRight() = W
    },
    W {
        override fun move(p: Point) = Point(p.x - 1,p.y)
        override fun turnLeft() = S
        override fun turnRight() = N
    };

    abstract fun move(p: Point): Point
    abstract fun turnLeft(): Direction
    abstract fun turnRight(): Direction
}
