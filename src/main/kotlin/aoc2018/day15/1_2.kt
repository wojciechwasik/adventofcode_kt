package aoc2018.day15

import util.*

//
//

fun main(args: Array<String>) {
    val input = InputReader("/day15.txt").readLines()

    problem1(input)

//    problem2(input)
}

private fun problem1(input: List<String>) {
    var board = parseMatrix(input, { it } )
    var units = mutableListOf<Warrior>()
    var id = 0
    board.forEachIndexed { x, y, v ->
        when (v) {
            'G' -> units.add(Warrior(id++, Type.Goblin, Point(x, y)))
            'E' -> units.add(Warrior(id++, Type.Elf, Point(x, y)))
        }
    }

    var tmp= round(board, units)
    board = tmp.first
    units = tmp.second
    printBoard(board)

    round(board, units)
    board = tmp.first
    units = tmp.second
    printBoard(board)

    round(board, units)
    board = tmp.first
    units = tmp.second
    printBoard(board)

    round(board, units)
    board = tmp.first
    units = tmp.second
    printBoard(board)

    round(board, units)
    board = tmp.first
    units = tmp.second
    printBoard(board)

    println("")
}

//private fun problem2(input: InputReader) {
//    println("")
//}

typealias Board = Matrix<Char>

private fun printBoard(board: Board) {
    for (y in 0 until board.yMax) {
        for (x in 0 until board.xMax) print(board[x, y])
        print('\n')
    }
}

// round
//   sort units, for each:
//     determine targets (unoccupied fields adjacent to enemies)
//       already at target - attack enemy (*)
//       determine shortest paths to targets - choose shortest of those - move (**)
//       no paths to targets - end turn
//
// (*) - choose enemy with lowest HP, then N/S/E/W
// (**) - N/S/E/W
private fun round(board: Board, units: MutableList<Warrior>): Pair<Board, MutableList<Warrior>> {
    val order = units.sortedBy { it.p.y * board.xMax + it.p.x }
    order.forEach { warrior ->
        val targets = findTargetLocations(board, units, warrior.type)
        if (targets.contains(warrior.p)) {
            val enemy = chooseEnemy(warrior, units)
            warrior.hit(enemy)
            if (!enemy.alive()) {
                units.remove(enemy)
                board[enemy.p] = '.'
            }
        } else {
            val root = Node(warrior.p)
            val leaves = findPath(board, targets, root)
            val move= chooseMove(root, leaves)
            println("warrior $warrior moves to $move")
            board[move] = board[warrior.p]
            board[warrior.p] = '.'
            warrior.p = move
        }
    }
    return Pair(board, units)
}

// determine target locations
// - filter units by type
// - generate target locations
// - filter locations outside board
// - filter non-empty locations
private fun findTargetLocations(board: Board, units: MutableList<Warrior>, friendly: Type) = units.filter {
    it.type != friendly
}.flatMap {
    Direction.values().map { d -> d.move(it.p) }
}.filter {
    board.contains(it) && board[it] == '.'
}.toSet()

// branch out from parent
// - skip branches going into occupied fields
// - skip branches looping back into path
private fun findPath(board: Board, targets: Set<Point>, parent: Node): Set<Node> {
    val branches = Direction.values().map {
        it.move(parent.p)
    }.filter {
        board.contains(it) && board[it] == '.'
    }.filter {
        var valid = true
        var n: Node? = parent
        do {
            if (n?.p == it) valid = false
            n = n?.parent
        } while (n != null && valid)
        valid
    }

    val leaves = mutableSetOf<Node>()
    branches.forEach {
        val child = Node(it, parent)
        parent.children.add(child)
        if (targets.contains(it))
            leaves.add(child)
        else
            leaves.addAll(findPath(board, targets, child))
    }
    return leaves
}

private fun chooseEnemy(warrior: Warrior, units: List<Warrior>): Warrior {
    // resolution order, only fields with enemies
    val candidates = arrayOf(
            Direction.N.move(warrior.p),
            Direction.S.move(warrior.p),
            Direction.E.move(warrior.p),
            Direction.W.move(warrior.p)
    ).mapNotNull {
        units.find { u -> u.type != warrior.type && u.p == it }
    }

    // start with highest priority, replace only if lower hp
    var enemy = candidates[0]
    for (i in 1 until candidates.size)
        if (candidates[i].hp < enemy.hp) enemy = candidates[i]
    return enemy
}

private fun chooseMove(root: Node, leaves: Set<Node>): Point {
    val min = leaves.minBy { it.length }!!
    val candidates= leaves.filter {
        it.length == min.length
    }.map {
        var n = it
        while (n.parent != root) n = n.parent!!
        n.p
    }.toSet()

    return arrayOf(
            Direction.N.move(root.p),
            Direction.S.move(root.p),
            Direction.E.move(root.p),
            Direction.W.move(root.p)
    ).first {
        candidates.contains(it)
    }
}

//
// paths
//

private data class Node(val p: Point, val parent: Node? = null) {
    val children = mutableListOf<Node>()
    val length: Int = (parent?.length ?: 0) + 1
}

//
// elfs and goblins
//
private enum class Type { Goblin, Elf }

private data class Warrior(val id: Int, val type: Type, var p: Point) {
    val power = 3
    var hp = 200

    fun hit(enemy: Warrior) {
        enemy.hp -= power
    }

    fun alive() = hp > 0
}
