package aoc2019.day3

import util.*

//
// Nearest crossing is: Point(x=-325, y=384), at distance: 709
// Fastest crossing is: Point(x=-161, y=1208), at time: 13836
//

fun main(args: Array<String>) {
    val input = InputReader("/aoc2019/day3.txt").readLines()
    val wire1 = input[0].split(',')
    val wire2 = input[1].split(',')

    problem1(wire1, wire2)

    problem2(wire1, wire2)
}

private fun problem1(wire1: List<String>, wire2: List<String>) {
    val path = mutableSetOf<Point>()
    val crossings = mutableSetOf<Point>()

    trace(wire1) { path.add(it) }
    trace(wire2) {
        if (it in path) crossings.add(it)
    }

    val nearest = crossings.sortedBy { distance(it) }[0]

    println("Nearest crossing is: ${nearest}, at distance: ${distance(nearest)}")
}

private fun problem2(wire1: List<String>, wire2: List<String>) {
    val path = mutableMapOf<Point, Int>()
    val crossings = mutableMapOf<Point, Int>()

    var time = 0
    trace(wire1) {
        ++time
        path.putIfAbsent(it, time)
    }
    time = 0
    trace(wire2) {
        ++time
        if (path.containsKey(it)) crossings.putIfAbsent(it, time + path[it]!!)
    }

    val fastest = crossings.entries.sortedBy { it.value }[0]

    println("Fastest crossing is: ${fastest.key}, at time: ${fastest.value}")
}

private fun trace(wire: List<String>, op: (Point) -> Unit) {
    var pos = Point(0, 0)
    wire.forEach {
        val direction = when (it[0]) {
            'R' -> Direction.E
            'L' -> Direction.W
            'U' -> Direction.N
            'D' -> Direction.S
            else -> abort("Invalid direction ${it}")
        }
        repeat(it.substring(1).toInt()) {
            pos = direction.move(pos)
            op(pos)
        }
    }
}

private fun distance(p: Point) = Math.abs(p.x) + Math.abs(p.y)
