package day1

import util.inputIntoLines

//
// 500
//

fun main(args: Array<String>) {
    val result= inputIntoLines("src\\day1\\data.txt").sumBy { it.toInt() }
    println(result)
}
