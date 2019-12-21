package aoc2018

import util.*

//
// checksum is 5658
// found match: nmgyjkpruszlbaqwficavxneo
//

fun main(args: Array<String>) {
    val input = InputReader("/aoc2018/day2.txt").readLines()

    problem1(input)

    problem2(input)
}

private fun problem1(input: List<String>) {
    val result = input.map(::extract).fold(Result(0, 0), ::addPartial)
    println("checksum is ${result.sumTwos * result.sumThrees}")
}

private fun problem2(input: List<String>) {
    val length = input[0].length - 1
    compareAll(input, ::extractMatch)
            .filter { it.length == length }
            .forEach { println("found match: $it") }
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

private fun extractMatch(l: String, r:String) = String(l.toCharArray().filterIndexed { index, c -> c == r[index] }.toCharArray())
