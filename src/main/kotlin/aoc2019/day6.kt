package aoc2019

import util.*

//
// Number of orbits: 223251
// Transfers required: 430
//

fun main(args: Array<String>) {
    val input = InputReader("/aoc2019/day6.txt").readLines().map {
        it.split(')')
    }.map {
        Pair(it[0], it[1])
    }

    problem1(input)

    problem2(input)
}

private fun problem1(input: List<Pair<String, String>>) {
    val tree = buildFromEdges<Int>(input)

    var count = 0
    tree["COM"]!!.traverse({
        val orbits = (it.parent?.value ?: -1) + 1
        it.value = orbits
        count += orbits
    })

    println("Number of orbits: ${count}")
}

private fun problem2(input: List<Pair<String, String>>) {
    val tree = buildFromEdges<Int>(input)

    val me = tree["YOU"]!!.parent!!
    me.value = 0
    me.climb {
        it.parent?.value = it.value!! + 1
    }

    var result: Int? = null
    val santa = tree["SAN"]!!.parent!!
    santa.value = 0
    santa.climb {
        if (it.parent?.value != null && result == null) result = it.parent!!.value!! + it.value!! + 1
        it.parent?.value = it.value!! + 1
    }

    println("Transfers required: ${result}")
}
