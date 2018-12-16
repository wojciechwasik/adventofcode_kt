package util

data class Point(val x: Int, val y: Int)

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
