package aoc2019.day2

import util.InputReader
import util.Intcode

//
// Value at 0 is: 3085697
// Noun and verb combination: 9425
//

fun main(args: Array<String>) {
    val input = InputReader("/aoc2019/day2.txt").readLines()[0].split(',').map(Integer::parseInt)

    problem1(input)

    problem2(input)
}

private fun problem1(input: List<Int>) {
    val data = input.toMutableList()
    data[1] = 12
    data[2] = 2

    val intcode = Intcode(data)
    intcode.run(1, 2, 99)

    println("Value at 0 is: ${intcode[0]}")
}

private fun problem2(input: List<Int>) {
    loop@ for (noun in 0..99) {
        for (verb in 0..99) {
            val data = input.toMutableList()
            data[1] = noun
            data[2] = verb

            val intcode = Intcode(data)
            intcode.run(1, 2, 99)

            if (intcode[0] == 19690720) {
                println("Noun and verb combination: ${noun * 100 + verb}")
                break@loop
            }
        }
    }
}
