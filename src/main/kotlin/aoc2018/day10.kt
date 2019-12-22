package aoc2018

import util.*

//
// RGRKHKNA
// time: 10117
//

fun main(args: Array<String>) {
    val input = InputReader("/aoc2018/day10.txt").readLines().map {
        parseInput(it,
                Regex("position=< *(-?\\d+), +(-?\\d+)> velocity=< *(-?\\d+), +(-?\\d+)>"),
                { it.size == 5 },
                { Pair(Point(it[1].toInt(), it[2].toInt()), Vector(it[3].toInt(), it[4].toInt())) }
        )
    }

    problem(input)
}

private fun problem(input: List<Pair<Point, Vector>>) {
    var points = input.map{ it.first }
    val vectors = input.map { it.second }

    var time = 0
    var height = calculateHeight(points)
    do {
        points = points.mapIndexed { i, it -> it.move(vectors[i]) }
        time++
        val oldHeight = height
        height = calculateHeight(points)
    } while (height < oldHeight)

    points = points.mapIndexed { i, it -> it.move(-vectors[i]) }
    time--
    height = calculateHeight(points)

    println("message:")
    printMessage(points, height)
    println("time: $time")
}

private fun calculateHeight(points: List<Point>) = (points.maxBy { it.y }?.y ?: abort("This should never happen")) - (points.minBy { it.y }?.y ?: abort("This should never happen")) + 1

private fun printMessage(points: List<Point>, heigh: Int) {
    val minX = points.minBy { it.x }?.x ?: abort("This should never happen")
    val length = (points.maxBy { it.x }?.x ?: abort("This should never happen")) - minX + 1
    val minY = points.minBy { it.y }?.y ?: abort("This should never happen")

    val message = Grid(false, null, length, heigh)
    points.forEach { message[it.x - minX, it.y - minY] = true }

    val display = GridDisplay<Boolean>(length, heigh) { if (it) '#' else ' ' }
    display.draw(message)
}
