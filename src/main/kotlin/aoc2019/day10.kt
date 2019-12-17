package aoc2019

import util.*
import kotlin.math.abs

//
// Best location is: Point(x=31, y=20) with 319 asteroids visible
// 200th removed target: Point(x=5, y=17), checksum: 517
//

fun main(args: Array<String>) {
    val input = InputReader("/aoc2019/day10.txt").readLines()
    val asteroids = mutableListOf<Point>()
    input.forEachIndexed { y, s ->
        s.toCharArray().forEachIndexed { x, c ->
            if (c == '#') asteroids.add(Point(x, y))
        }
    }

    val base = problem1(asteroids)
    asteroids.remove(base)

    problem2(base, asteroids)
}

private fun problem1(asteroids: List<Point>):Point {
    val results = mutableMapOf<Point, Int>()

    // for each asteroid determine the number of unique direction vectors
    asteroids.forEach { source ->
        val vectors = mutableSetOf<Vector>()
        asteroids.forEach { target -> if (target != source) vectors.add(Vector(source, target).getDirection()) }
        results.put(source, vectors.size)
    }

    // select asteroid with most direction vectors - most other asteroids visible
    val best = results.maxBy { it.value }!!
    println("Best location is: ${best.key} with ${best.value} asteroids visible")
    return best.key
}

private fun problem2(base: Point, asteroids: List<Point>) {
    // starting from base:
    // - group asteroids by their direction vector
    val targets = mutableMapOf<Vector, MutableList<Pair<Point, Vector>>>()
    asteroids.forEach { target ->
        val attackVector = Vector(base, target)
        val direction = attackVector.getDirection()
        targets.putIfAbsent(direction, mutableListOf())
        targets[direction]!!.add(Pair(target, attackVector))
    }

    // - sort every group by distance from base
    targets.forEach { _, target ->
        target.sortBy { abs(it.second.dx) + abs(it.second.dy) }
    }

    // - go clockwise from north [0,-1] and remove first from every group until 200
    val orderedDirections = targets.keys.toMutableList().sortedBy { clockwiseAngle(it) }
    var i = 200
    while (i > 0) {
        orderedDirections.forEach { direction ->
            val line = targets[direction]!!
            if (line.isNotEmpty()) {
                val target = line.removeAt(0)
                i--
                if (i == 0) {
                    println("200th removed target: ${target.first}, checksum: ${target.first.x * 100 + target.first.y}")
                }
            }
        }
    }
}
