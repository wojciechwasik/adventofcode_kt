package day7

import util.inputIntoLines
import util.parseInput

//
// order is: MNQKRSFWGXPZJCOTVYEBLAHIUD
// total time taken is: 948
//

fun main(args: Array<String>) {
    val input = inputIntoLines("src\\day7\\data.txt")
        .map {
            parseInput(
                it,
                Regex("Step (\\w{1}) must be finished before step (\\w{1}) can begin."),
                { it.size == 3 },
                { Link(it[1][0], it[2][0]) })
        }

    problem1(input)

    problem2(input)
}

private fun problem1(input: List<Link>): Unit {
    val stateMachine = StateMachine(input)
    val order = mutableListOf<Char>()

    while (stateMachine.hasRemainigTasks()) {
        val task = stateMachine.findTaskToWorkOn() ?: throw IllegalStateException("Didn't get task, this should not happen here")
        stateMachine.claimTask(task)
        // mark as done immediately as we only want to know the order here
        stateMachine.taskDone(task)
        order.add(task.symbol)
    }

    println("order is: ${String(order.toCharArray())}")
}

private fun problem2(input: List<Link>): Unit {
    val stateMachine = StateMachine(input)
    val workers = WorkerTeam(5)
    var time = 0

    // really dumb solution that repeatedly checks for available tasks / workers
    // and progresses time in ticks instead of chunks of time required to finish at least one task
    while (stateMachine.hasRemainigTasks()) {
         // progress time until some worker becomes idle
        while (workers.findIdle() == null) {
            workers.doWork()
            time++
        }
        val worker = workers.findIdle() ?: throw IllegalStateException("At least 1 worker should be idle")

        // progress time until some task becomes doable
        while (stateMachine.findTaskToWorkOn() == null) {
            workers.doWork()
            time++
        }
        val task = stateMachine.findTaskToWorkOn() ?: throw IllegalStateException("At least 1 task should be doable")
        stateMachine.claimTask(task)
        worker.workOn(task)
    }

    // no more remaining tasks in machine - wait for all workers to finish
    while (workers.findBusy() != null) {
        time++
        workers.doWork()
    }

    println("total time taken is: $time")
}

private data class Link(val from: Char, val to: Char)

private class StateMachine(links: List<Link>) {
    private var remainingLinks: List<Link>
    private val remainingTasks: MutableList<Task>

    init {
        remainingLinks = links.toList()
        // states sorted to guarantiee proper order of execution
        remainingTasks = links
            .flatMap { listOf(it.from, it.to) }
            .toSortedSet()
            .map { Task(it, this) }
            .toMutableList()
    }

    fun hasRemainigTasks() = remainingTasks.size > 0

    fun findTaskToWorkOn() =
        if (remainingLinks.size > 0)
        // remaining task that has no prerequisites
            remainingTasks.find { task -> remainingLinks.find { task.symbol == it.to } == null }
        else
        // any remaining task
            remainingTasks.firstOrNull()

    fun claimTask(task: Task): Unit {
        remainingTasks.remove(task)
    }

    fun taskDone(task: Task) {
        if (remainingLinks.size > 0) {
            remainingLinks = remainingLinks.filter { it.from != task.symbol }
        }
    }

}



private class WorkerTeam(size: Int) {
    private val workers: List<Worker>

    init {
        workers = List<Worker>(size) { Worker() }
    }

    fun findIdle() = workers.find { it.isIdle() }

    fun findBusy() = workers.find { !it.isIdle() }

    fun doWork() = workers.forEach { it.tick() }
}

private class Worker {
    private var task: Task? = null

    fun workOn(newTask: Task) {
        if (!isIdle()) throw IllegalStateException("This worker is still busy")
        task = newTask
    }

    fun isIdle() = task?.isDone() ?: true

    fun tick(): Unit {
        task?.tick()
    }
}

private data class Task(val symbol: Char, private val machine: StateMachine) {
    private var workload = symbol - 'A' + 61

    fun tick(): Unit {
        workload--
        if (isDone())
            machine.taskDone(this)
    }

    fun isDone() = workload < 1
}
