package day2

import util.loadInput

//
// checksum is 5658
//

fun main(args: Array<String>) {
    val result = loadInput("src\\day2\\data.txt")
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
