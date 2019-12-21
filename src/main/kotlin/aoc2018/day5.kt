package aoc2018

import util.*

//
// final lenght: 9900
// winner is o with length 4992
//

fun main (args: Array<String>) {
    val input = InputReader("/aoc2018/day5.txt").readLines()[0].toCharArray()

    val length = react(input)
    println("final lenght: $length")

    val results = mutableMapOf<Char, Int>()
    ('a' .. 'z').forEach { c ->
        val tmp = input.filter { it != c && it != c.toUpperCase() }.toCharArray()
        results.put(c, react(tmp))
    }
    val winner = results.minBy { it.value }
    println("winner is ${winner?.key} with length ${winner?.value}")
}

private fun react(input: CharArray): Int {
    do {
        var changed = false

        var l = Pointer(input)
        do {
            if (!l.findNextChar()) break
            val r = l.nextPointer()
            if (!r.findNextChar()) break
            if (l reactWith r) {
                changed = true
                break
            }
            l = r
        } while (true)
    } while (changed)

    return input.count { it != ' ' }
}

private class Pointer(private val data: CharArray, private var index: Int = 0) {

    fun reset() { index = 0 }

    fun findNextChar(): Boolean {
        while (valid() && data[index] == ' ')
            index++
        return valid()
    }

    fun valid() = index < data.size

    fun getChar() = if (valid()) data[index] else throw IllegalArgumentException("Index out of bounds")

    fun clear() = if (valid()) data[index] = ' ' else throw IllegalArgumentException("Index out of bounds")

    fun nextPointer() = Pointer(data, index + 1)

    infix fun reactWith(other: Pointer): Boolean {
        if (matchLowerToUpper(getChar(), other.getChar()) || matchLowerToUpper(other.getChar(), getChar())) {
            clear()
            other.clear()
            return true
        } else {
            return false
        }
    }
}

private fun matchLowerToUpper(a: Char, b:Char) = a.isLowerCase() && a.toUpperCase().equals(b)
