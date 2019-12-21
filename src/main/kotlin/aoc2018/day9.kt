package aoc2018

import util.*
import java.math.BigInteger

//
// max score is: 398730
// max score is: 3349635509
//

fun main(args: Array<String>) {
    problem(parse("438 players; last marble is worth 71626 points"))
    problem(parse("438 players; last marble is worth 7162600 points"))
}

private fun parse(input: String) = parseInput(
        input,
        Regex("(\\d+) players; last marble is worth (\\d+) points"),
        { it.size == 3 },
        { Pair(it[1].toInt(), it[2].toInt()) }
)

private fun problem(input: Pair<Int, Int>) {
    val (count, limit) = input

    val players = Array<BigInteger>(count) { BigInteger.ZERO }
    val board = GameBoard()

    (1 .. limit).forEach {
        val score = board.put(it.toLong())
        if (score != 0L) {
            val index = (it - 1) % count
            players[index] = players[index].add(BigInteger.valueOf(score))
        }
    }

    val result = players.max()
    println("max score is: $result")
}

private class GameBoard() {
    private var current = Field(0)

    fun put(value: Long):Long {
        if (value % 23 == 0L) {
            val removed = current.prev.prev.prev.prev.prev.prev.prev
            link(removed.prev, removed.next)
            current = removed.next

            return removed.value + value
        } else {
            val first = current.next
            val second = current.next.next
            current = Field(value)

            link(first, current)
            link(current, second)

            return 0
        }
    }

    private fun link(left: Field, right: Field): Unit {
        left.next = right
        right.prev = left
    }

    override fun toString(): String {
        var begin = current
        while (begin.value != 0L) begin = begin.next

        var out = ""
        var field = begin
        do {
            out += if (field == current) "(${field.value}) " else "${field.value} "
            field = field.next
        } while (field != begin)
        return out
    }
}

private data class Field(val value: Long) {
    var prev = this
    var next = this
}
