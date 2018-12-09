package day1

import util.inputIntoLines

//
// duplicate found: 709
//

fun main(args: Array<String>) {
    var sum: Int = 0;
    val freq = mutableSetOf<Int>(0);

    val input = inputIntoLines("src\\day1\\data.txt").map { it.toInt() }
    val result: Int? = generateSequence { input }
        .flatMap { it.asSequence() }
        .map { sum += it; sum }
        .find { !freq.add(it) }

    println("duplicate found: ${result}")
}
