package util

/**
 * Intcode computer for AOC 2019.
 * - input - initial memory state
 */
class Intcode(input: List<Int>) {
    val state = input.toList()
    var memory = state.toMutableList()
    var ptr = 0

    /**
     * Read memory
     */
    operator fun get(addr:Int) = memory[addr]

    /**
     * Reset to initial state
     */
    fun reset() {
        memory = state.toMutableList()
        ptr = 0
    }

    /**
     * Run program
     * - opcodes - list of opcodes (commands) that are valid for this run
     */
    fun run(vararg opcodes: Int) {
        loop@ while (true) {
            val opcode = memory[ptr]
            if (opcode !in opcodes) abort(opcode)

            when (opcode) {
                1 -> setPtr(ptr + 3, getPtr(ptr + 1) + getPtr(ptr + 2))
                2 -> setPtr(ptr + 3, getPtr(ptr + 1) * getPtr(ptr + 2))
                99 -> break@loop
                else -> abort(opcode)
            }

            ptr += 4
        }
    }

    private fun abort(opcode: Int): Nothing = throw IllegalStateException("Illegal opcode: ${opcode} at ${ptr}")

    private fun getPtr(addr: Int) = memory[memory[addr]]

    private fun setPtr(addr: Int, value: Int) {
        memory[memory[addr]] = value
    }
}
