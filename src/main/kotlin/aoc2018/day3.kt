package aoc2018

import util.*

//
// overlap size: 110827
// not overlapped: 116
//

fun main(args: Array<String>) {
    val parts = InputReader("/aoc2018/day3.txt")
            .readLines()
            .map { parseInput(it, entryPattern, ::validator, ::transformer) }
    val overlapped = mutableSetOf<String>()

    val xMax = parts.maxBy { it.area.right }!!.area.right
    val yMax = parts.maxBy { it.area.bottom }!!.area.bottom
    val whole = Grid(false, null, xMax + 1, yMax + 1)
    compareAll(parts) { l, r -> l.area.forEach { p -> if (r.area.contains(p)) { whole[p.x, p.y] = true; overlapped.add(l.id); overlapped.add(r.id) }}}

    var count = 0
    whole.forEach { if (it) count++ }

    println("overlap size: $count")
    parts.forEach { if (!overlapped.contains(it.id)) println("not overlapped: ${it.id}") }
}

private val entryPattern = Regex("#(\\d+) @ (\\d+),(\\d+): (\\d+)x(\\d+)")

private fun validator(values: List<String>) = values.size == 6

private fun transformer(values: List<String>) =
        Fabric(values[1], Area(values[2].toInt(), values[3].toInt(),values[2].toInt() + values[4].toInt() - 1, values[3].toInt() + values[5].toInt() - 1))

private data class Fabric(val id: String, val area: Area)
