package aoc2018

import util.*

//
// 500
// duplicate found: 709
//

fun main(args: Array<String>) {
    val input = InputReader("/aoc2018/day1.txt").readLines().map { it.toInt() }

    problem1(input)

    problem2(input)
}

private fun problem1(input: List<Int>) {
    println(input.sum())
}

private fun problem2(input: List<Int>) {
    var sum: Int = 0;
    val freq = mutableSetOf(0);

    val result: Int? = generateSequence { input }
            .flatMap { it.asSequence() }
            .map { sum += it; sum }
            .find { !freq.add(it) }

    println("duplicate found: ${result}")
}
