package aoc2018

import util.*
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

typealias LicenseNode = TreeNode<List<Int>>

private fun problem1(input: List<LicenseNode>) {
    val result = sumMetadata(input)
    println("medatada sum is: $result")
}

private fun problem2(input: List<LicenseNode>) {
    val result = nodeValue(input[0])
    println("root node value is: $result")
}

private fun sumMetadata(nodes: List<LicenseNode>): Int = nodes.sumBy { sumMetadata(it.children) + metadataValue(it) }

private fun nodeValue(node: LicenseNode): Int = if (node.children.isEmpty()) metadataValue(node) else childrenValue(node)

private fun metadataValue(node: LicenseNode) = node.value!!.sum()

private fun childrenValue(node: LicenseNode) = node.value!!.map {
    if (it < 1 || it > node.children.size)
        0
    else
        nodeValue(node.children[it - 1])
}
    .sum()

private fun readNode(count: Int): List<LicenseNode> = (1 .. count).map {
    val (x, y) = readHeader()
    val children = readNode(x)
    val node = LicenseNode(it.toString(), readMeta(y))
    children.forEach { node.addChild(it) }
    node
}

private fun readHeader() = Pair(readToken(), readToken())

private fun readMeta(count: Int) = (1 .. count).map { readToken() }

private val tokenizer = InputReader("/aoc2018/day8.txt").tokenize()

private fun readToken(): Int {
    val type = tokenizer.nextToken()
    if (type != TT_NUMBER) abort("Invalid token type: $type")
    return tokenizer.nval.toInt()
}
