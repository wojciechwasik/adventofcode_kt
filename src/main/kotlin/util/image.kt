package util

/**
 *  Space Image Format for AOC 2019
 */
class SpaceImageFormat(val width: Int, val height: Int, private val data: String) {
    val layers = data.windowed(width * height, width * height).map { Layer(width, it) }
}

class Layer(val width: Int, private val data: String) {
    val rows = data.windowed(width, width)

    fun count(c: Char): Int = data.count { it == c }
}
