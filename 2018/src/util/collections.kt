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
