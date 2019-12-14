package aoc2019.day4

import util.*

//
// Number of possible passwords: 1653
// Number of possible passwords: 1133
//

fun main(args: Array<String>) {
    val min = 206938
    val max = 679128

    problem1(min, max)

    problem2(min, max)
}

private fun problem1(min: Int, max: Int) {
    var count = 0
    for (i in min..max) {
        var pair = false
        var descending = false
        val number = i.toString().toCharArray()
        for (j in 0..4) {
            if (number[j] == number[j+1]) pair = true
            if (number[j] > number[j+1]) descending = true
        }
        if (pair && !descending) count++
    }

    println("Number of possible passwords: ${count}")
}

private fun problem2(min: Int, max: Int) {
    var count = 0
    for (i in min..max) {
        var pair = false
        var descending = false
        val number = i.toString().toCharArray()
        for (j in 0..4) {
            if ((number[j] == number[j + 1]) && (j == 0 || number[j - 1] != number[j]) && (j == 4 || number[j + 2] != number[j])) pair = true
            if (number[j] > number[j+1]) descending = true
        }
        if (pair && !descending) count++
    }

    println("Number of possible passwords: ${count}")
}
