package aoc2018.day11

//
// X: 243, Y: 49
// X: 285, Y: 169, S: 15
//

fun main(args: Array<String>) {
    val input = 5093

    val grid = Grid(300) { Array<Int>(300) { 0 } }
    for (x in 1 .. 300)
        for (y in 1 .. 300)
            grid[x - 1][y - 1] = powerLevel(x, y, input)

    problem1(grid)

    problem2(grid)
}

private typealias Grid = Array<Array<Int>>

private fun problem1(grid: Grid) {

    var maxP = 0
    var maxX = -1
    var maxY = -1
    for (x in 1 .. 298)
        for (y in 1 .. 298) {
            val p = power(x, y, 3, grid)
            if (p > maxP) {
                maxP = p
                maxX = x
                maxY = y
            }
        }

    println("X: $maxX, Y: $maxY")
}

private fun problem2(grid: Grid) {
    var maxP = 0
    var maxX = -1
    var maxY = -1
    var maxS = -1
    for (s in 1 .. 300)
        for (x in 1 .. 300 - s + 1)
            for (y in 1 .. 300 - s + 1) {
                val p = power(x, y, s, grid)
                if (p > maxP) {
                    maxP = p
                    maxX = x
                    maxY = y
                    maxS = s
                }
            }

    println("X: $maxX, Y: $maxY, S: $maxS")

    println("")
}

private fun powerLevel(x: Int, y: Int, gridSn: Int) =
    (((x + 10) * y + gridSn) * (x + 10) / 100) % 10 - 5

private fun power(x: Int, y: Int, size: Int, grid: Grid):Int {
    var p = 0
    for (px in x - 1 .. x + size - 2)
        for (py in y - 1 .. y + size - 2)
            p += grid[px][py]
    return p
}
