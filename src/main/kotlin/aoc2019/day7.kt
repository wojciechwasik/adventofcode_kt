package aoc2019

import util.*

//
// Maximum power signal: 17406, phase setup: [2, 4, 1, 0, 3]
// Maximum looped power signal: 1047153, phase setup: [7, 8, 6, 9, 5]
//

fun main(args: Array<String>) {
    val input = InputReader("/aoc2019/day7.txt").readSequence(',') { it.toLong() }

    problem1(input)

    problem2(input)
}

private fun problem1(input: List<Long>) {
    var max = emptyList<Int>()
    var maxSignal = 0L

    permutations(listOf(0, 1, 2, 3, 4)).forEach { setup ->
        var power = 0L
        setup.forEach { phase ->
            val intcode = Intcode(input)
            power = intcode.run(listOf(phase.toLong(), power))[0]
        }

        if (power > maxSignal) {
            max = setup
            maxSignal = power
        }
    }

    println("Maximum power signal: ${maxSignal}, phase setup: ${max}")
}

private fun problem2(input: List<Long>) {
    var max = emptyList<Int>()
    var maxSignal = 0L

    permutations(listOf(5, 6, 7, 8, 9)).forEach { setup ->
        var power = 0L
        var amps = listOf(
                Intcode(input),
                Intcode(input),
                Intcode(input),
                Intcode(input),
                Intcode(input)
        )
        setup.forEachIndexed { i, phase -> amps[i].run(listOf(phase.toLong())) }

        while (!amps[4].isFinished()) {
            amps.forEach { amp ->
                power = amp.run(listOf(power))[0]
            }
        }

        if (power > maxSignal) {
            max = setup
            maxSignal = power
        }
    }

    println("Maximum looped power signal: ${maxSignal}, phase setup: ${max}")
}
