package util

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.io.StreamTokenizer

fun inputIntoLines(filename: String) = inputReader(filename).readLines()

fun inputTokenizer(filename: String) = StreamTokenizer(BufferedReader(inputReader(filename)))

private fun inputReader(filename: String) = InputStreamReader(FileInputStream(filename))

fun <T> parseInput(entry: String, regex: Regex, validator: (List<String>) -> Boolean, transformer: (List<String>) -> T): T {
    val matches = regex.find(entry)
    if (matches?.groupValues != null && validator(matches.groupValues)) {
        return transformer(matches.groupValues)
    }
    throw IllegalArgumentException("not matching: $entry")
}
