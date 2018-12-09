package aoc.`2018`.day1

import util.InputReader

//
// duplicate found: 709
//

fun main(args: Array<String>) {
    var sum: Int = 0;
    val freq = mutableSetOf<Int>(0);

    val input = InputReader("/day1.txt").readLines().map { it.toInt() }
    val result: Int? = generateSequence { input }
        .flatMap { it.asSequence() }
        .map { sum += it; sum }
        .find { !freq.add(it) }

    println("duplicate found: ${result}")
}
