package util

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

data class Matrix<T>(val data: Array<Array<T>>, val xMax: Int, val yMax: Int) {

    operator fun get(x: Int, y: Int) = data[x][y]

    operator fun set(x: Int, y: Int, value: T) {
        data[x][y] = value
    }

    inline fun <reified R> mapIndexed(transform: (Int, Int, T) -> R) =
            Matrix(data.mapIndexed { x, column ->
                column.mapIndexed { y, value ->
                    transform(x, y, value)
                }.toTypedArray()
            }.toTypedArray(),
                    xMax, yMax)

    fun forEachIndexed(consumer: (Int, Int, T) -> Unit) {
        for (x in 0 until xMax) {
            for (y in 0 until yMax) {
                consumer(x, y, data[x][y])
            }
        }
    }
}
