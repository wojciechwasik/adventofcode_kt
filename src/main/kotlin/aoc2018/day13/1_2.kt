package aoc2018.day13

import util.*

//
// Crash on turn 151, at [Cart(id=6, p=Point(x=57, y=104), dir=W, turn=L), Cart(id=2, p=Point(x=57, y=104), dir=N, turn=R)]
// Final cart on turn 14047: [Cart(id=11, p=Point(x=67, y=74), dir=W, turn=L)]
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

private fun initTracks(input: Matrix<Char>) = input.mapIndexed { _, _, v ->
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
            '^', 'v', '<', '>' -> carts.add(Cart(id++, Point(x, y), toDirection(v)))
        }
    }
    return carts
}

private fun toDirection(c: Char) = when (c) {
    '^' -> Direction.N
    'v' -> Direction.S
    '<' -> Direction.W
    '>' -> Direction.E
    else -> throw IllegalArgumentException("direction symbol: $c")
}

private fun moveCarts(tracks: Matrix<Char>, carts: List<Cart>): List<Cart> {
    val crashed = mutableListOf<Cart>()
    val order = carts.sortedBy { it.p.y * tracks.xMax + it.p.x }

    for (i in 0 until order.size) {
        val cart = order[i]

        // not crashed already ?
        if (!crashed.contains(cart)) {

            // move cart
            cart.move()

            // turn if necessary
            when (tracks[cart.p.x, cart.p.y]) {
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
                if (it.p.x == cart.p.x && it.p.y == cart.p.y && it != cart && !crashed.contains(it)) {
                    crashed.add(cart)
                    crashed.add(it)
                    return@forEach
                }
            }
        }
    }

    return crashed
}

private enum class Turn { L, S, R }

private data class Cart(val id: Int, var p: Point, var dir: Direction) {
    var turn = Turn.L

    fun move() {
        p = dir.move(p)
    }

    fun crossroads() {
        dir = when (turn) {
            Turn.L -> dir.turnLeft()
            Turn.R -> dir.turnRight()
            else -> dir
        }
        turn = Turn.values()[(turn.ordinal + 1) % 3]
    }

    override fun toString(): String {
        return "Cart(id=$id, p=$p, dir=$dir, turn=$turn)"
    }
}
