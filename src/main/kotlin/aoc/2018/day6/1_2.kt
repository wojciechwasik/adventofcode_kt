package aoc.`2018`.day6

import util.InputReader
import util.parseInput
import kotlin.math.abs

//
// max area is: 3660
// limited area is: 35928
//

fun main(args: Array<String>) {
    val sources = InputReader("/day6.txt")
            .readLines()
            .mapIndexed {
                index, entry -> parseInput(
                    entry,
                    Regex("(\\d+), *(\\d+)"),
                    { it.size == 3 },
                    { Source(index, it[1].toInt(), it[2].toInt()) }
                )
            }

    // normalize
    val minX = sources.minBy { it.x } ?.x ?: abort()
    val minY = sources.minBy { it.y } ?.y ?: abort()
    val normalized = sources.map { it.transform(-minX, -minY) }

    val maxX = sources.maxBy { it.x } ?.x ?: abort()
    val maxY = sources.maxBy { it.y } ?.y ?: abort()

    // init grid with ids of closest sources (-1 means equal distance to 2 or more)
    val grid = MutableList<MutableList<Int>>(maxX + 1) { MutableList<Int>(maxY + 1) { -1 } }

    for (px in 0 .. maxX) {
        for (py in 0..maxY) {
            var closest: Pair<Source, Int>? = null
            var unique = false

            for (source in normalized) {
                val distance = source.distance(px, py)
                if (closest == null || distance < closest.second) {
                    closest = Pair(source, distance)
                    unique = true
                } else if (distance == closest.second) {
                    unique = false
                }
            }

            if (closest != null && unique) {
                grid[px][py] = closest.first.id
            }
        }
    }

    // compute areas (-1 means area is infinite and should be rejected)
    // assume that area touching boundary extends into infinity - too tired to prove it definitely but seems correct)
    val areas = MutableList<Int>(normalized.size) { 0 }
    for (px in 0 .. maxX) {
        for (py in 0..maxY) {
            val id = grid[px][py]
            if (id >= 0) {
                if (px == 0 || px == maxX || py == 0 || py == maxY) {
                    areas[id] = -1
                } else if (areas[id] >= 0){
                    areas[id] += 1
                }
            }
        }
    }

    val maxArea = areas.maxBy { it }
    println("max area is: $maxArea")

    // count area with sum of distances to all sources smaller than limit
    val limit = 10000
    var limitedArea = 0
    for (px in 0 .. maxX) {
        for (py in 0..maxY) {
            var totalDistance = 0
            for (source in normalized) {
                totalDistance += source.distance(px, py)
            }
            if (totalDistance < limit) {
                limitedArea++
            }
        }
    }

    println("limited area is: $limitedArea")
}

fun abort(): Nothing = throw IllegalStateException("This should never happen")

private data class Source(val id: Int, val x: Int, val y: Int) {

    fun transform(dx: Int, dy: Int) = Source(id,x + dx, y + dy)

    fun distance(px: Int, py:Int) = abs(x - px) + abs(y - py)
}
