package aoc2019

import util.*

//
// Area of tractor beam: 226
// Problem 2 solution:
//

fun main(args: Array<String>) {
    val input = InputReader("/aoc2019/day19.txt").readSequenceLong(',')

    problem1(input)

    problem2(input)
}

private fun problem1(input: List<Long>) {
    var count = 0
    for (x in 0..49)
        for (y in 0..49)
            if (Intcode(input).run(listOf(x.toLong(), y.toLong()))[0] == 1L) count++

    println("Area of tractor beam: ${count}")
}

private fun problem2(input: List<Long>) {

    println("Problem 2 solution: ")
}
