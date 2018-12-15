package util

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StreamTokenizer

class InputReader(val filename:String) {

    fun readLines() = getResourceReader().readLines()

    fun tokenize() = StreamTokenizer(BufferedReader(getResourceReader()))

    private fun getResourceReader() = InputStreamReader(this::class.java.getResourceAsStream(filename))
}

fun <T> parseInput(entry: String, regex: Regex, validator: (List<String>) -> Boolean, transformer: (List<String>) -> T): T {
    val matches = regex.find(entry)
    if (matches?.groupValues != null && validator(matches.groupValues)) {
        return transformer(matches.groupValues)
    }
    throw IllegalArgumentException("not matching: $entry")
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

inline fun <reified T> parseMatrix(lines: List<String>, transformer: (Char) -> T): Matrix<T> {
    val tmp = lines.map { it.toCharArray().map(transformer) }

    val x = tmp[0].size
    val y = tmp.size
    val data = Array<Array<T>>(x) { i -> Array<T>(y) { j -> tmp[j][i] } }

    return Matrix(data, x, y)
}
