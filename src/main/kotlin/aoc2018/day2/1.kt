package aoc2018.day2

import util.InputReader
import kotlin.Array
import kotlin.Boolean
import kotlin.Char
import kotlin.Int
import kotlin.String

//
// checksum is 5658
//

fun main(args: Array<String>) {
    val result = InputReader("/aoc2018/day2.txt")
            .readLines()
            .map(::extract)
            .fold(Result(0, 0), ::addPartial)
    println("checksum is ${result.sumTwos * result.sumThrees}")
}

private data class Partial(val two: Boolean, val three: Boolean)

private data class Result(val sumTwos: Int, val sumThrees: Int)

private fun addPartial(result: Result, partial: Partial) =
        Result(
                result.sumTwos + (if (partial.two) 1 else 0),
                result.sumThrees + (if (partial.three) 1 else 0)
        )


private fun extract(id: String): Partial {
    val counts = id.asSequence().fold(mutableMapOf<Char, Int>(), ::putOrIncrement).values
    return Partial(counts.contains(2), counts.contains(3))
}

private fun putOrIncrement(acc: MutableMap<Char, Int>, c: Char): MutableMap<Char, Int> {
    acc.put(c, acc.getOrDefault(c, 0) + 1)
    return acc
}
