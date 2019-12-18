package util

/**
 * Intcode computer for AOC 2019.
 * - input - initial memory state
 * - opcodes - list of opcodes that are enabled
 */
class Intcode(private val state: List<Long>, vararg val opcodes: Int) {
    private val memory = Memory(state)
    private var ptr = 0
    private var opcode = 0
    private var base = 0
    private var modes = ""
    private var finished = false

    /**
     * Read memory
     */
    operator fun get(addr: Int) = memory.read(addr)

    /**
     * Write memory
     */
    operator fun set(addr: Int, value: Long): Unit {
        memory.write(addr, value)
    }

    /**
     * Check if program finished
     */
    fun isFinished() = finished

    /**
     * Run program with optional inputs
     */
    fun run(data: List<Long> = emptyList()): List<Long> {
        val input = data.toMutableList()
        val output = mutableListOf<Long>()
        while (true) {
            opcode = memory.read(ptr).toInt() % 100
            modes = (memory.read(ptr) / 100).toString().reversed()
            if (opcodes.isNotEmpty() && opcode !in opcodes) abort("Illegal opcode: ${opcode} at ${ptr}")
            if (command(input, output)) break
        }
        return output
    }

    private enum class Mode {
        POSITION,
        IMMEDIATE,
        RELATIVE
    }

    private fun command(input: MutableList<Long>, output: MutableList<Long>): Boolean {
        var halt = false
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
                    // halt execution until more input is available
                    halt = true
                }
                else {
                    store(1, input.removeAt(0))
                    ptr += 2
                }
            }
            4 -> { // output
                output.add(load(1))
                ptr += 2
            }
            5 -> { // jump if true
                if (load(1) > 0) ptr = load(2).toInt()
                else ptr += 3
            }
            6 -> { // jump if false
                if (load(1) == 0L) ptr = load(2).toInt()
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
            9 -> { // set relative base
                base += load(1).toInt()
                ptr += 2
            }
            99 -> { // program finished
                finished = true
                halt = true
            }
            else -> abort("Illegal opcode: ${opcode} at ${ptr}")
        }
        return halt
    }

    private fun mode(offset: Int): Mode {
        if (offset > modes.length) return Mode.POSITION
        val mode = modes[offset - 1]
        return when (mode) {
            '0' -> Mode.POSITION
            '1' -> Mode.IMMEDIATE
            '2' -> Mode.RELATIVE
            else -> abort("Illegal command mode: ${mode} at ${ptr}")
        }
    }

    private fun load(offset: Int): Long {
        val value = memory.read(ptr + offset)
        return when (mode(offset)) {
            Mode.POSITION -> memory.read(value.toInt())
            Mode.RELATIVE -> memory.read(base + value.toInt())
            else -> value
        }
    }

    private fun store(offset: Int, value: Long) {
        val addr = memory.read(ptr + offset).toInt()
        if (mode(offset) == Mode.RELATIVE) memory.write(base + addr, value)
        else memory.write(addr, value)
    }
}

private class Memory(state: List<Long>) {
    private var memory = state.toLongArray()

    fun read(addr: Int): Long = if (addr >= memory.size) 0 else memory[addr]

    fun write(addr: Int, value: Long): Unit {
        if (addr >= memory.size) {
            val newMemory = LongArray(addr + 1024)
            memory.copyInto(newMemory)
            memory = newMemory
        }
        memory[addr] = value
    }
}
