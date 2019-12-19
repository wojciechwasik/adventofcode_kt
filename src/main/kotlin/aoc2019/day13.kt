package aoc2019

import util.*
import kotlin.math.sign

//
// Number of blocks: 296
// Final score is 13824
//

fun main(args: Array<String>) {
    val input = InputReader("/aoc2019/day13.txt").readSequenceLong(',')

    problem1(input)

    problem2(input)
}

private fun problem1(input: List<Long>) {
    val intcode = Intcode(input)
    val output = intcode.run()

    println("Number of blocks: ${output.windowed(3, 3).filter { it[2] == 2L }.count()}")
}

private fun problem2(input: List<Long>) {
    val intcode = Intcode(input)
    intcode[0] = 2

    var ballX = 0
    var paddleX = 0
    var output = intcode.run(listOf(0L)).windowed(3, 3)
    while (!intcode.isFinished()) {
        ballX = output.find { it[2] == 4L }!![0].toInt()
        paddleX = output.find { it[2] == 3L }!![0].toInt()

        val move = ballX.compareTo(paddleX).sign.toLong()

        output = intcode.run(listOf(move)).windowed(3, 3)
    }

    println("Final score is ${output.find { it[0] == -1L }!![2].toInt()}")
}
