package aoc2019

import util.*

//
// Panels painted at least once: 2392
// EGBHLEUE
//

fun main(args: Array<String>) {
    val input = InputReader("/aoc2019/day11.txt").readSequence(',') { it.toLong() }

    problem1(input)

    problem2(input)
}

private fun problem1(input: List<Long>) {
    val panels = mutableMapOf<Point, Long>()

    paint(panels, input)

    println("Panels painted at least once: ${panels.keys.size}")
}

private fun problem2(input: List<Long>) {
    val panels = mutableMapOf<Point, Long>()
    // paint starting panel white
    panels[Point(0, 0)] = 1

    paint(panels, input)

    val minX = panels.keys.minBy { it.x }!!.x
    val maxX = panels.keys.maxBy { it.x }!!.x
    val minY = panels.keys.minBy { it.y }!!.y
    val maxY = panels.keys.maxBy { it.y }!!.y

    // init matrix
    val matrix = arrayListOf<ArrayList<Boolean>>()
    for (y in 0..(maxY - minY + 1)) {
        val row = arrayListOf<Boolean>()
        for (x in 0..(maxX - minX + 1)) {
            row.add(false)
        }
        matrix.add(row)
    }

    // copy white panels to matrix
    panels.keys.forEach {
        matrix[it.y][it.x] = (panels[it]!! == 1L)
    }

    // print out matrix
    matrix.forEach { row ->
        row.forEach { if (it) print('#') else print(' ') }
        print('\n')
    }
}

private fun paint(panels: MutableMap<Point, Long>, input: List<Long>): Unit {
    var robot = Point(0, 0)
    var direction = Direction.N
    val intcode = Intcode(input)

    while (!intcode.isFinished()) {
        // 0 - black, 1 - white, black if not painted yet
        val color = panels[robot] ?: 0
        val command = intcode.run(listOf(color))

        // paint with indicated color
        panels[robot] = command[0]

        // turn and move
        direction = if (command[1] == 1L) direction.turnRight() else direction.turnLeft()
        robot = direction.move(robot)
    }
}
