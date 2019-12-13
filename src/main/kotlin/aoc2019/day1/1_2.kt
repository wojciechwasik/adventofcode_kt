package aoc2019.day1

import util.*

//
// Required module fuel: 3152919
// Required total fuel: 4726527
//

fun main(args: Array<String>) {
    val input = InputReader("/aoc2019/day1.txt").readLines()

    problem1(input)

    problem2(input)
}

private fun problem1(input: List<String>) {
    val sum = input.map { it.toInt() }.map(::calculateFuel).sum()

    println("Required module fuel: $sum")
}

private fun problem2(input: List<String>) {
    val sum = input.map { it.toInt() }.map {
        var req = it;
        var sum = 0;
        while (req > 0) {
            req = calculateFuel(req)
            sum += req
        }
        sum
    }.sum()

    println("Required total fuel: $sum")
}

private fun calculateFuel(mass: Int) = Integer.max(mass / 3 - 2, 0)
