package day8

import java.io.BufferedReader
import java.io.FileInputStream
import java.io.InputStreamReader
import java.io.StreamTokenizer
import java.io.StreamTokenizer.TT_NUMBER

//
// medatada sum is: 37439
// root node value is: 20815
//

fun main(args: Array<String>) {
    val input = readNode(1)

    problem1(input)

    problem2(input)
}

private fun problem1(input: List<Node>) {
    val result = sumMetadata(input)
    println("medatada sum is: $result")
}

private fun sumMetadata(nodes: List<Node>): Int = nodes.sumBy { sumMetadata(it.children) + it.metadata.sum() }

private fun problem2(input: List<Node>) {
    val result = nodeValue(input[0])
    println("root node value is: $result")
}

private fun nodeValue(node: Node): Int {
    if (node.children.isEmpty()) return node.metadata.sum()

    return node.metadata.map {
        if (it < 1 || it > node.children.size)
            0
        else
            nodeValue(node.children[it - 1])
    }.sum()
}

private val tokenizer = StreamTokenizer(BufferedReader(InputStreamReader(FileInputStream("src\\day8\\data.txt"))))

private fun readNode(count: Int): List<Node> = (1 .. count).map {
    val (x, y) = readHeader()
    Node(readNode(x), readMeta(y))
}

private fun readHeader() = Pair(readToken(), readToken())

private fun readMeta(count: Int) = (1 .. count).map { readToken() }

private fun readToken(): Int {
    val type = tokenizer.nextToken()
    if (type != TT_NUMBER) throw IllegalStateException("Invalid token type: $type")
    return tokenizer.nval.toInt()
}

private data class Node(val children: List<Node>, val metadata: List<Int>)
