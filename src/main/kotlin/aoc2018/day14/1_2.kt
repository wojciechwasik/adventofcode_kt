package aoc2018.day14

//
// [6, 1, 2, 6, 4, 9, 1, 0, 2, 7]
// 20191616
//

fun main(args: Array<String>) {
    problem1(Recipes(mutableListOf(3, 7)), 209231)

    problem2(Recipes(mutableListOf(3, 7)), "209231")
}

private fun problem1(recipes: Recipes, initialRecipes: Int) {

    while (recipes.reg.size < initialRecipes + 10) {
        recipes.generate()
        recipes.move()
    }

    println(recipes.reg.subList(initialRecipes, initialRecipes + 10))
}

private fun problem2(recipes: Recipes, pattern: String) {
    while (!matches(recipes.reg, pattern)) {
        recipes.generate()
        recipes.move()
    }
}

private fun matches(reg: List<Int>, pattern: String): Boolean {
    if (reg.size >= pattern.length) {
        val m = reg.subList(reg.size - pattern.length, reg.size).joinToString("")
        if (m == pattern) {
            println(reg.size - pattern.length)
            return true
        }
    }
    if (reg.size > pattern.length) {
        val m = reg.subList(reg.size - pattern.length - 1, reg.size - 1).joinToString("")
        if (m == pattern) {
            println(reg.size - pattern.length - 1)
            return true
        }
    }
    return false
}

private data class Recipes(val reg: MutableList<Int>) {
    var e1 = 0
    var e2 = 1

    fun generate() {
        val sum = reg[e1] + reg[e2]
        if (sum >= 10) reg.add(1)
        reg.add(sum % 10)
    }

    fun move() {
        e1 = (e1 + reg[e1] + 1) % reg.size
        e2 = (e2 + reg[e2] + 1) % reg.size
    }
}
