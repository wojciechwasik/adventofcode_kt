package util

/**
 * Intcode computer for AOC 2019.
 * - input - initial memory state
 * - opcodes - list of opcodes that are enabled
 */
class Intcode(private val state: List<Int>, vararg val opcodes: Int) {
    private var memory = state.toMutableList()
    private var ptr = 0
    private var opcode = 0
    private var modes = ""

    /**
     * Read memory
     */
    operator fun get(addr:Int) = memory[addr]

    /**
     * Run program with optional inputs
     */
    fun run(data: List<Int> = emptyList()): MutableList<Int> {
        val input = data.toMutableList()
        val output = mutableListOf<Int>()
        while (true) {
            opcode = memory[ptr] % 100
            modes = (memory[ptr] / 100).toString().reversed()
            if (opcode !in opcodes) abort("Illegal opcode: ${opcode} at ${ptr}")
            if (opcode == 99) break
            command(input, output)
        }
        return output
    }

    private fun command(input: MutableList<Int>, output: MutableList<Int>) {
        when (opcode) {
            1 -> { // add
                store(3, load(1) + load(2))
                ptr += 4
            }
            2 -> { // multiply
                store(3, load(1) * load(2))
                ptr += 4
            }
            3 -> { // input
                if (input.isEmpty()) {
                    print("> ")
                    store(1, readLine()!!.toInt())
                }
                else {
                    store(1, input.removeAt(0))
                }
                ptr += 2
            }
            4 -> { // output
                output.add(load(1))
                ptr += 2
            }
            5 -> { // jump if true
                if (load(1) > 0) ptr = load(2)
                else ptr += 3
            }
            6 -> { // jump if false
                if (load(1) == 0) ptr = load(2)
                else ptr += 3
            }
            7 -> { // less than
                if (load(1) < load(2)) store(3, 1)
                else store(3, 0)
                ptr += 4
            }
            8 -> { // equals
                if (load(1) == load(2)) store(3, 1)
                else store(3, 0)
                ptr += 4
            }
            else -> abort("Illegal opcode: ${opcode} at ${ptr}")
        }
    }

    private fun positionMode(offset: Int) = offset > modes.length || modes[offset - 1] == '0'

    private fun load(offset: Int): Int {
        var value = memory[ptr + offset]
        if (positionMode(offset)) value = memory[value]
        return value
    }

    private fun store(offset: Int, value: Int) {
        memory[memory[ptr + offset]] = value
    }
}
