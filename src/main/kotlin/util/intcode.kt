package util

/**
 * Intcode computer for AOC 2019.
 * - input - initial memory state
 */
class Intcode(input: List<Int>) {
    val state = input.toList()
    var memory = state.toMutableList()
    var ptr = 0
    var opcode = 0
    var modes = ""

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
        modes = ""
    }

    /**
     * Run program
     * - opcodes - list of opcodes (commands) that are valid for this run
     */
    fun run(vararg opcodes: Int) {
        while (true) {
            opcode = memory[ptr] % 100
            modes = (memory[ptr] / 100).toString().reversed()
//            println("opcode: ${opcode}, modes: ${modes}")
            if (opcode !in opcodes) abort("Illegal opcode: ${opcode} at ${ptr}")
            if (opcode == 99) break
            command()
        }
    }

    private fun command() {
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
                print("> ")
                store(1, readLine()!!.toInt())
                ptr += 2
            }
            4 -> { // output
                println(load(1))
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
