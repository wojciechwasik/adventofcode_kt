package aoc2019

import util.*

//
// Image checksum is: 2286
// CJZLP
//

fun main(args: Array<String>) {
    val input = InputReader("/aoc2019/day8.txt").readLines()[0]

    val image = SpaceImageFormat(25, 6, input)

    problem1(image)

    problem2(image)
}

private fun problem1(image: SpaceImageFormat) {
    val layer = image.layers.sortedBy { layer -> layer.count('0') }[0]
    println("Image checksum is: ${layer.count('1') * layer.count('2')}")
}

private fun problem2(image: SpaceImageFormat) {
    for (y in 0..5) {
        for (x in 0..24) {
            var skip = false
            image.layers.forEach {
                if (!skip) {
                    when (it.rows[y][x]) {
                        '0' -> {
                            print(' ')
                            skip = true
                        }
                        '1' -> {
                            print('*')
                            skip = true
                        }
                    }
                }
            }
        }
        print('\n')
    }
}
