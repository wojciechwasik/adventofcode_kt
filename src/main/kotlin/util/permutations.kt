package util

/**
 * Generate permutations using Heap's algorithm
 */

fun <T> permutations(elements: List<T>): List<List<T>> {
    return generate(elements.size, elements)
}

private fun <T> generate(k: Int, elements: List<T>): List<List<T>> {

    if (k <= 1) return listOf(copy(elements))

    val results = mutableListOf<List<T>>()
    results.addAll(generate(k - 1, copy(elements)))
    for (i in 0 until (k - 1)) {
        val data = copy(elements)
        swap(data, i, k - 1)
        results.addAll(generate(k - 1, data))
    }
    return results
}

private fun <T> copy(elements: List<T>): MutableList<T> {
    val data = mutableListOf<T>()
    data.addAll(elements)
    return data
}

private fun <T> swap(data: MutableList<T>, i: Int, j: Int):Unit {
    val tmp = data[i]
    data[i] = data[j]
    data[j] = tmp
}
