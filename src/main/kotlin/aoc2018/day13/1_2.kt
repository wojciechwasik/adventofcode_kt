package aoc2018.day13

import util.InputReader
import util.Matrix
import util.parseMatrix

//
// Crash on turn 151, at [Cart(id=6, x=57, y=104, dir=W), Cart(id=2, x=57, y=104, dir=N)]
// Final cart on turn 14047: [Cart(id=11, x=67, y=74, dir=W)]
//

fun main(args: Array<String>) {
    val input = parseMatrix(InputReader("/day13.txt").readLines(), { it } )

    problem1(input)

    problem2(input)
}

private fun problem1(input: Matrix<Char>) {
    val tracks = initTracks(input)
    val carts = initCarts(input)

    var turn = 0
    var crashed: List<Cart>
    do {
        crashed = moveCarts(tracks, carts)
        turn++
    } while (crashed.size == 0)

    println("Crash on turn $turn, at $crashed")
}

private fun problem2(input: Matrix<Char>) {
    val tracks = initTracks(input)
    val carts = initCarts(input).toMutableList()

    var turn = 0
    var crashed: List<Cart>
    do {
        crashed = moveCarts(tracks, carts)
        turn++
        crashed.forEach {
            carts.remove(it)
        }
    } while (carts.size > 1)

    println("Final cart on turn $turn: $carts")
}

private fun initTracks(input: Matrix<Char>) = input.mapIndexed { x, y, v ->
        when (v) {
            '^', 'v' -> '|'
            '<', '>' -> '-'
            else -> v
        }
    }

private fun initCarts(input: Matrix<Char>): List<Cart> {
    var id = 0
    val carts = mutableListOf<Cart>()
    input.forEachIndexed { x, y, v ->
        when (v) {
            '^' -> {
                carts.add(Cart(id, x, y, Direction.N))
                id++
            }
            'v' -> {
                carts.add(Cart(id, x, y, Direction.S))
                id++
            }
            '<' -> {
                carts.add(Cart(id, x, y, Direction.W))
                id++
            }
            '>' -> {
                carts.add(Cart(id, x, y, Direction.E))
                id++
            }
        }
    }
    return carts
}

private fun moveCarts(tracks: Matrix<Char>, carts: List<Cart>): List<Cart> {
    val crashed = mutableListOf<Cart>()
    val order = carts.sortedBy { it.y * 10000 + it.x }

    for (i in 0 until order.size) {
        val cart = order[i]

        // not crashed already ?
        if (!crashed.contains(cart)) {

            // move cart
            cart.move()

            // turn if necessary
            when (tracks[cart.x, cart.y]) {
                '/' -> {
                    cart.dir = when (cart.dir) {
                        Direction.N -> Direction.E
                        Direction.E -> Direction.N
                        Direction.S -> Direction.W
                        Direction.W -> Direction.S
                    }
                }
                '\\' -> {
                    cart.dir = when (cart.dir) {
                        Direction.N -> Direction.W
                        Direction.W -> Direction.N
                        Direction.S -> Direction.E
                        Direction.E -> Direction.S
                    }
                }
                '+' -> cart.crossroads()
                ' ' -> throw IllegalStateException("Cart $cart went out of tracks")
            }

            // detect crash
            order.forEach {
                if (it.x == cart.x && it.y == cart.y && it != cart && !crashed.contains(it)) {
                    crashed.add(cart)
                    crashed.add(it)
                    return@forEach
                }
            }
        }
    }

    return crashed
}

private enum class Direction { N, E, S, W }

private enum class Turn { L, S, R }

private data class Cart(val id: Int, var x: Int, var y: Int, var dir: Direction) {
    var turn = Turn.L

    fun move() {
        when (dir) {
            Direction.N -> y--
            Direction.E -> x++
            Direction.S -> y++
            Direction.W -> x--
        }
    }

    fun crossroads() {
        val d = when (turn) {
            Turn.L -> dir.ordinal - 1
            Turn.R -> dir.ordinal + 1
            else -> dir.ordinal
        }
        dir = Direction.values()[((d % 4) + 4) % 4]
        turn = Turn.values()[(turn.ordinal + 1) % 3]
    }
}
