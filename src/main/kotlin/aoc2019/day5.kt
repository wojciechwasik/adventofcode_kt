package aoc2019

import util.*

//
// Diagnostic code for system 1: 7259358
// Diagnostic code for system 5: 11826654
//

fun main(args: Array<String>) {
    val input = InputReader("/aoc2019/day5.txt").readSequence(',', Integer::parseInt)

    problem1(input)

    problem2(input)
}

private fun problem1(input: List<Int>) {
    val intcode = Intcode(input, 1, 2, 3, 4, 99)
    val output = intcode.run(listOf(1))
    println("Diagnostic code for system 1: ${output.last()}")
}

private fun problem2(input: List<Int>) {
    val intcode = Intcode(input, 1, 2, 3, 4, 5, 6, 7, 8, 99)
    val output = intcode.run(listOf(5))
    println("Diagnostic code for system 5: ${output[0]}")
}
