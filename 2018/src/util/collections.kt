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

fun <T> parseInput(entry: String, regex: Regex, validator: (List<String>) -> Boolean, transformer: (List<String>) -> T): T {
    val matches = regex.find(entry)
    if (matches?.groupValues != null && validator(matches.groupValues)) {
        return transformer(matches.groupValues)
    }
    throw IllegalArgumentException("not matching: $entry")
}
