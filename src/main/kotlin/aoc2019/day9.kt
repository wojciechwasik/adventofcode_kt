package aoc2019

import util.*

//
// BOOST diagnostics: [3345854957]
// BOOST coordinates: [68938]
//

fun main(args: Array<String>) {
    val input = InputReader("/aoc2019/day9.txt").readSequenceLong(',')

    problem1(input)

    problem2(input)
}

private fun problem1(input: List<Long>) {
    val intcode = Intcode(input)
    val result = intcode.run(listOf(1L))

    println("BOOST diagnostics: ${result}")
}

private fun problem2(input: List<Long>) {
    val intcode = Intcode(input)
    val result = intcode.run(listOf(2L))

    println("BOOST coordinates: ${result}")
}
