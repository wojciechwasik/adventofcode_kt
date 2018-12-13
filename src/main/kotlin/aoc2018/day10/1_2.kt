package aoc2018.day10

import util.InputReader
import util.parseInput

//
// RGRKHKNA
// time: 10117
//

fun main(args: Array<String>) {
    val input = InputReader("/day10.txt").readLines()
    val points = input.map {
        parseInput(it,
                Regex("position=< *(-?\\d+), +(-?\\d+)> velocity=< *(-?\\d+), +(-?\\d+)>"),
                { it.size == 5 },
                { Point(it[1].toInt(), it[2].toInt(), it[3].toInt(), it[4].toInt()) }
        )
    }

    problem(points)
}

private fun problem(points: List<Point>) {
    var time = 0
    var height = calculateHeight(points)
    do {
        points.forEach { it.move() }
        time++
        val oldHeight = height
        height = calculateHeight(points)
    } while (height < oldHeight)

    points.forEach { it.revert() }
    time--
    height = calculateHeight(points)

    println("message:")
    printMessage(points, height)
    println("time: $time")
}

private fun calculateHeight(points: List<Point>) = (points.maxBy { it.y }?.y ?: abort()) - (points.minBy { it.y }?.y ?: abort()) + 1

private fun printMessage(points: List<Point>, heigh: Int) {
    val minX = points.minBy { it.x }?.x ?: abort()
    val length = (points.maxBy { it.x }?.x ?: abort()) - minX + 1
    val minY = points.minBy { it.y }?.y ?: abort()

    val message = Array<Array<Boolean>>(length) { Array<Boolean>(heigh) { false } }
    points.forEach { message[it.x - minX][it.y - minY] = true }

    for (y in 0 .. heigh - 1) {
        for (x in 0..length - 1) {
            print(if (message[x][y]) "#" else " ")
        }
        println("")
    }
}

private fun abort(): Nothing = throw IllegalStateException("this should never happen")

private data class Point(var x: Int, var y: Int, val dx:Int, val dy: Int) {
    fun move() {
        x += dx
        y += dy
    }

    fun revert() {
        x -= dx
        y -= dy
    }
}
