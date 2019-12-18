package util

/**
 * ASCII screen output support
 * - width - screen width
 * - height - screen height
 */
class Screen(private val width: Int, private val height: Int) {
    private var screen = initScreen()

    // diagnostics messages, to be displayed after every update
    private val messages = mutableListOf<String>()

    private fun initScreen(): MutableList<MutableList<Char>> {
        val state = mutableListOf<MutableList<Char>>()
        for (y in 0 until height) {
            val row = mutableListOf<Char>()
            for (x in 0 until width) {
                row.add(' ')
            }
            state.add(row)
        }
        return state
    }

    fun addMessage(message: String): Unit {
        messages.add(message)
    }

    fun update(x: Int, y: Int, value: Char) {
        screen[y][x] = value
    }

    fun draw(): Unit {
        for (y in 0 until height) {
            for (x in 0 until width) {
                print(screen[y][x])
            }
            print('\n')
        }
        if (messages.isNotEmpty()) {
            println(messages)
            messages.clear()
        }
        screen = initScreen()
    }
}

/**
 * Display for sparse data (list of points with values)
 */
class SparseDisplay<T>(private val width: Int, private val height: Int, private val mapper: (T) -> Char) {
    private val screen = Screen(width, height)

    /**
     * Draw update with sparse data
     * - input - list of coordinates and values (data to display)
     */
    fun draw(input: List<Pair<Point, T>>): Unit {
        // determine boundaries
        val minX = input.minBy { it.first.x }!!.first.x
        val maxX = input.maxBy { it.first.x }!!.first.x
        val minY = input.minBy { it.first.y }!!.first.y
        val maxY = input.maxBy { it.first.y }!!.first.y

        // check required space
        val requiredWidth = maxX - minX + 1
        if (requiredWidth > width)
            screen.addMessage("Required width ${requiredWidth} greater than available width ${width}")
        val requiredHeight = maxY - minY + 1
        if (requiredHeight > height)
            screen.addMessage("Required height ${requiredHeight} greater than available height ${height}")

        // transform data coordinates to screen coordinates
        val deltaX = -minX
        val deltaY = -minY
        screen.addMessage("Input coordinates corrected by [${deltaX}, ${deltaY}] to match screen coordinates")

        // fill virtual screen with data
        input.forEach { screen.update(it.first.x + deltaX, it.first.y + deltaY, mapper(it.second)) }

        // draw
        screen.draw()
    }
}
