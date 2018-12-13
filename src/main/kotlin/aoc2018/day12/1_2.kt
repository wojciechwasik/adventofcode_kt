package aoc2018.day12

import util.InputReader
import util.parseInput

//
// Result: 3798
// [139] 11th consecutive diff equal 78, assuming stable solution
// Final eval: 3900000002212
//

fun main(args: Array<String>) {
    val input = InputReader("/day12.txt").readLines()
    val initialState = Regex("initial state: (.*)").find(input[0])?.groupValues!![1]
    val rules = input.subList(2, input.size).map { entry ->
        parseInput(entry,
                Regex("(.{5}) => (.{1})"),
                { it.size == 3 },
                { Rule(toBits(it[1].toCharArray()), toBits(it[2].toCharArray())[0]) })
    }

    problem1(initialState, rules, 20)
    problem2(initialState, rules, 50000000000)
}

private fun problem1(initialState: String, rules: List<Rule>, steps: Long) {
    var state = State(initialState)

    for (i in 1 .. steps) {
        state = state.apply(rules)
    }

    println("Result: ${state.eval()}")
}


private fun problem2(initialState: String, rules: List<Rule>, steps: Long) {
    var state = State(initialState)
    var eval = state.eval()

    val diffs = mutableListOf<Int>()
    for (i in 1 .. steps) {
        state = state.apply(rules)
        val newEval = state.eval()
        val diff = newEval - eval

        if (diffs.size < 10) {
            diffs.add(diff)
        } else if (diffs.filter { it != diff }.size > 0) {
            diffs.removeAt(0)
            diffs.add(diff)
        } else {
            println("[$i] 11th consecutive diff equal $diff, assuming stable solution")
            println("Final eval: ${newEval + (steps - i) * diff}")
            break
            //3900000002212
        }

        eval = newEval
    }
}

private fun toBits(stateChars: CharArray):BooleanArray {
    val bits = stateChars.map { it == '#' }
    return bits.toBooleanArray()
}

private fun toChars(stateBits: BooleanArray) = stateBits.map { if (it) '#' else '.' }.toCharArray()

private class State {
    private val offset: Int
    private val state: BooleanArray

    constructor(initialState: String) {
        offset = 4
        state = toBits("....${initialState}....".toCharArray())
    }

    private constructor(newOffset: Int, newState: BooleanArray) {
        offset = newOffset
        state = newState
    }

    fun apply(rules: List<Rule>): State {
        val tmp = (2 .. state.size - 3).map { index ->
            state.sliceArray(index - 2 .. index + 2)
        }.map { slice ->
            rules.find { it.match(slice) }?.output ?: false
        }.toBooleanArray()

        val prefix = if (tmp[0]) 4 else if (tmp[1]) 3 else 2
        val suffix = if (tmp[tmp.size - 1]) 4 else if (tmp[tmp.size - 2]) 3 else 2

        return State(offset + prefix - 2, BooleanArray(prefix) + tmp + BooleanArray(suffix))
    }

    fun eval() = state.mapIndexed { index, b -> if (b) index - offset else 0 }.sum()

    override fun toString(): String {
        return "[$offset]: ${String(toChars(state))}"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as State

        return eval() == other.eval()
    }

    override fun hashCode(): Int = eval()
}

private data class Rule(val pattern: BooleanArray, val output: Boolean) {
    fun match(source: BooleanArray) = (pattern.size == source.size) && (pattern.filterIndexed { index, b -> b != source[index] }.size == 0)
}
