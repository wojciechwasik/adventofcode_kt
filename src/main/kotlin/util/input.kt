package util

import java.io.BufferedReader
import java.io.FileInputStream
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
