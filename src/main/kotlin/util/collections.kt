package util

import java.lang.Integer.max

fun <T, R> compareAll(list: List<T>, comparator: (T, T) -> R): List<R> {
    if (list.isEmpty()) return emptyList<R>()

    val result = mutableListOf<R>()
    for (i in 0 .. list.size - 2) {
        for (j in i + 1 .. list.size - 1) {
            result.add(comparator(list[i], list[j]))
        }
    }
    return result
}

/**
 * 2D grid representation
 * - defaultValue - value to be used for uninitialized parts
 * - rows - list of row data, used for initialization
 * - width - used to extend grid beyond initial data range
 * - height - used to extend grid beyond initial data range
 */
class Grid<T>(val defaultValue: T, rows: List<List<T>>?, width: Int?, height: Int?) {

    private var data: MutableList<MutableList<T>>
    var xMax = 0
        private set
    var yMax = 0
        private set

    init {
        xMax = max(width ?: 0, rows?.map { it.size }?.max() ?: 0)
        yMax = max(height ?: 0, rows?.size ?: 0)
        data = (0 until yMax).map { y ->
            (0 until xMax).map { x -> rows?.get(y)?.get(x) ?: defaultValue }.toMutableList()
        }.toMutableList()
    }

    operator fun get(x: Int, y: Int) = data[y][x]

    operator fun get(p: Point) = get(p.x, p.y)

    operator fun set(x: Int, y: Int, value: T) {
        data[y][x] = value
    }

    operator fun set(p: Point, value: T) = set(p.x, p.y, value)

    fun <R> map(transform: (T) -> R) =
            Grid(transform(defaultValue), data.map { row -> row.map { value -> transform(value) } }, xMax, yMax)

    fun <R> mapIndexed(transformedDefault: R, transform: (Int, Int, T) -> R) =
            Grid(transformedDefault, data.mapIndexed { y, row -> row.mapIndexed { x, value -> transform(x, y, value) } }, xMax, yMax)

    fun forEach(consumer: (T) -> Unit) {
        for (y in 0 until yMax) {
            for (x in 0 until xMax) {
                consumer(data[y][x])
            }
        }
    }

    fun forEachIndexed(consumer: (Int, Int, T) -> Unit) {
        for (y in 0 until yMax) {
            for (x in 0 until xMax) {
                consumer(x, y, data[y][x])
            }
        }
    }

    fun contains(x: Int, y: Int) = (x in 0 until xMax) && (y in 0 until yMax)

    fun contains(p: Point) = contains(p.x, p.y)
}
