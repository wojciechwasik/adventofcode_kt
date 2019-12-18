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

    val display = SparseDisplay<Long>(80, 10) { if (it == 1L) '#' else ' ' }
    display.draw(panels.entries.map { Pair(it.key, it.value) })
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
