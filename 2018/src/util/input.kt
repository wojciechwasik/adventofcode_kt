package util

import java.io.FileInputStream
import java.io.InputStreamReader

fun loadInput(filename: String) = InputStreamReader(FileInputStream(filename)).readLines()

fun <T> parseInput(entry: String, regex: Regex, validator: (List<String>) -> Boolean, transformer: (List<String>) -> T): T {
    val matches = regex.find(entry)
    if (matches?.groupValues != null && validator(matches.groupValues)) {
        return transformer(matches.groupValues)
    }
    throw IllegalArgumentException("not matching: $entry")
}
