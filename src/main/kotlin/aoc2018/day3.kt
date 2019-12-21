package aoc2018

import util.*

//
// overlap size: 110827
// not overlapped: 116
//

fun main(args: Array<String>) {
    val points = InputReader("/aoc2018/day3.txt")
            .readLines()
            .map { parseInput(it, entryPattern, ::validator, ::transformer) }
    val overlapped = mutableSetOf<String>()

    val xMax = points.maxBy { it.right }?.right
    val yMax = points.maxBy { it.bottom }?.bottom
    if (xMax != null && yMax != null) {
        val matrix = MutableList<MutableList<Boolean>>(xMax + 1) { MutableList<Boolean>(yMax + 1) { false } }
        compareAll(points) { l, r -> l.forEach { p -> if (r.contains(p)) { matrix[p.x][p.y] = true; overlapped.add(l.id); overlapped.add(r.id) }}}

        var count = 0
        for (x in 0 .. xMax)
            for (y in 0 .. yMax)
                if (matrix[x][y]) count++

        println("overlap size: $count")
        points.forEach { if (!overlapped.contains(it.id)) println("not overlapped: ${it.id}") }
    }
}

private val entryPattern = Regex("#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)")

private fun validator(values: List<String>) = values.size == 6

private fun transformer(values: List<String>) = Area(
        values[1],
        values[2].toInt(), values[3].toInt(),
        values[2].toInt() + values[4].toInt() - 1, values[3].toInt() + values[5].toInt() - 1
)

private data class Area(val id: String, val left: Int, val top: Int, val right: Int, val bottom: Int) {
    fun contains(p: Point) = p.x >= left && p.x <= right && p.y >= top && p.y <= bottom

    fun forEach(f: (Point) -> Unit) {
        for (x in left .. right)
            for (y in top .. bottom)
                f(Point(x, y))
    }
}
