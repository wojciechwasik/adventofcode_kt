package aoc2019.day5

import util.*

//
// 7259358
// 11826654
//

fun main(args: Array<String>) {
    val input = InputReader("/aoc2019/day5.txt").readSequence(',', Integer::parseInt)

    problem1(input)

    problem2(input)
}

private fun problem1(input: List<Int>) {
    val intcode = Intcode(input)
    intcode.run(1, 2, 3, 4, 99)
}

private fun problem2(input: List<Int>) {
    val intcode = Intcode(input)
    intcode.run(1, 2, 3, 4, 5, 6, 7, 8, 99)
}
