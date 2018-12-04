package day1

import util.loadInput

//
// 500
//

fun main(args: Array<String>) {
    val result= loadInput("src\\day1\\data.txt").sumBy { it.toInt() }
    println(result)
}
