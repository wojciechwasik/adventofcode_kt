package aoc2019

import util.InputReader
import util.Intcode
import java.lang.Long.parseLong

//
// Value at 0 is: 3085697
// Noun and verb combination: 9425
//

fun main(args: Array<String>) {
    val input = InputReader("/aoc2019/day2.txt").readSequence(',') { it.toLong() }

    problem1(input)

    problem2(input)
}

private fun problem1(input: List<Long>) {
    val data = input.toMutableList()
    data[1] = 12
    data[2] = 2

    val intcode = Intcode(data, 1, 2, 99)
    intcode.run()

    println("Value at 0 is: ${intcode[0]}")
}

private fun problem2(input: List<Long>) {
    loop@ for (noun in 0..99) {
        for (verb in 0..99) {
            val data = input.toMutableList()
            data[1] = noun.toLong()
            data[2] = verb.toLong()

            val intcode = Intcode(data, 1, 2, 99)
            intcode.run()

            if (intcode[0] == 19690720L) {
                println("Noun and verb combination: ${noun * 100 + verb}")
                break@loop
            }
        }
    }
}
