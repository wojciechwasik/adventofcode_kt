package day2

import util.compareAll
import util.loadInput

//
// found match: nmgyjkpruszlbaqwficavxneo
//

fun main(args: Array<String>) {
    val input = loadInput("src\\day2\\data.txt")
    val length = input[0].length
    compareAll(input, ::extractMatch)
        .filter { it.length == length - 1 }
        .forEach { println("found match: $it") }
}

private fun extractMatch(l: String, r:String): String {
    var output = ""
    for (i in 0 .. (l.length - 1)) {
        if (l[i] == r[i]) {
            output += l[i]
        }
    }
    return output
}
