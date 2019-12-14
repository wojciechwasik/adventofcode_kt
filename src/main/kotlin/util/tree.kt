package util

/**
 * Simple tree structure
 */
data class TreeNode<T>(val id: String, var value: T? = null) {

    var parent: TreeNode<T>? = null
        private set
    private val children: MutableList<TreeNode<T>> = mutableListOf()

    fun addChild(child: TreeNode<T>): Unit {
        children.add(child)
        child.parent = this
    }

    fun traverse(visitor: (TreeNode<T>) -> Unit): Unit {
        visitor(this)
        children.forEach {
            it.traverse(visitor)
        }
    }

    fun climb(visitor: (TreeNode<T>) -> Unit): Unit {
        visitor(this)
        parent?.climb(visitor)
    }
}

fun <T> buildFromEdges(input: List<Pair<String, String>>): MutableMap<String, TreeNode<T>> {
    val tree = mutableMapOf<String, TreeNode<T>>()

    input.forEach {
        val parentId = it.first
        val childId = it.second

        tree.putIfAbsent(parentId, TreeNode(parentId))
        tree.putIfAbsent(childId, TreeNode(childId))
        tree[parentId]!!.addChild(tree[childId]!!)
    }

    return tree
}
