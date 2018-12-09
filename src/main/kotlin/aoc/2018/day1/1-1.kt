package aoc.`2018`.day1

import util.InputReader

//
// 500
//

fun main(args: Array<String>) {
    val result = InputReader("/day1.txt").readLines().sumBy { it.toInt() }
    println(result)
}
