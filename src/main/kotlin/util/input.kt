package util

import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StreamTokenizer

class InputReader(val filename:String) {

    fun readLines() = getResourceReader().readLines()

    fun <T> readSequence(separator: Char, transformer: (String) -> T): List<T> = readLines()[0].split(separator).map(transformer)

    fun <T> readSequences(separator: Char, count: Int, transformer: (String) -> T): List<List<T>> =
            readLines().subList(0, count).map { it.split(separator).map(transformer) }

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

fun <T> parseGrid(lines: List<String>, default: T, transformer: (Char) -> T): Grid<T> {
    val tmp = lines.map { it.toCharArray().map(transformer) }
    val x = tmp[0].size
    val y = tmp.size
    return Grid(default, tmp, x, y)
}
