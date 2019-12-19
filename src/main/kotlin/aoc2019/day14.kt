package aoc2019

import util.*

//
// Solution to problem 1
// Solution to problem 2
//

fun main(args: Array<String>) {
    val input = InputReader("/aoc2019/day14.txt").readLines()

    val recipes = input.map { it.split("=>") }
            .map { part -> Recipe(parseResource(part[1]), part[0].split(',').map {  parseResource(it) }) }

    println("Number of recipes vs number of products: ${recipes.size}-${recipes.map { it.product }.toSet().size}")

    problem1(recipes)
    problem2(recipes)
}

private fun problem1(input: List<Recipe>) {
    val catalog = input.map { it.product.symbol to it }.toMap()

    // start from FUEL, cover all requirements, end with ORE
    val layers = mutableListOf(setOf("FUEL"))
    while (true) {
        val nextLayer = layers.last().flatMap { catalog[it]?.ingridients ?: emptyList() }.map { it.symbol }.toSet()
        println("Next layer is: ${nextLayer}")
        if (nextLayer.isEmpty()) break
        val reused = nextLayer.intersect(layers.flatten().toSet())
        layers.add(nextLayer)
        if (reused.isNotEmpty()) println("Reuse detected in layer ${layers.size}: ${reused}")
    }

    println("Layers: ${layers}")

    println("Problem 1 solution: ")
}

private fun problem2(input: List<Recipe>) {

    println("Problem 2 solution: ")
}

private fun parseResource(input: String): Resource {
    val data = input.trim().split(' ')
    return Resource(data[1], data[0].toInt())
}

data class Resource(val symbol: String, val amount: Int)

data class Recipe(val product: Resource, val ingridients: List<Resource>)
