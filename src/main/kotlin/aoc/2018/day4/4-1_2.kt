package aoc.`2018`.day4

import util.InputReader
import util.parseInput
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

//
// Strategy 1
// winner is: 1987, minute with most sleep: 34
// result: 67558
// Strategy 2
// winner is: 2633, minute with most sleep: 30
// result: 78990
//

fun main(args: Array<String>) {
    // log entries grouped by date
    val entries = InputReader("/day4.txt")
            .readLines()
            .map { parseInput(it, entryPattern, ::validator, ::transformer) }
            .sortedBy { it.dateTime }
            .groupBy { it.dateTime.toLocalDate() }

    // guard id to per-minute sleep summary
    val times = mutableMapOf<String, IntArray>()
    for ((date, events) in entries) {
        val time = times.getOrPut(events[0].entry) { IntArray(60) }
        for (i in 1 .. events.size - 1 step 2)
            for (m in events[i].dateTime.minute .. events[i + 1].dateTime.minute - 1)
                time[m] += 1
    }

    strategy1(times)
    strategy2(times)
}

private fun strategy1(times: MutableMap<String, IntArray>) {
    // strategy 1 - guard with most minutes slept, and his best minute
    val winner = times.entries.sortedBy { it.value.sum() }.last()

    val id = parseGuardId(winner.key)
    val minute = bestMinute(winner.value)

    println("Strategy 1")
    println("winner is: $id, minute with most sleep: $minute")
    println("result: ${id * minute}")
}

private fun strategy2(times: MutableMap<String, IntArray>) {
    // strategy 2 - guard with absolute best minute
    val winner = times.entries.sortedBy { bestMinuteValue(it.value) }.last()

    val id = parseGuardId(winner.key)
    val minute = bestMinute(winner.value)

    println("Strategy 2")
    println("winner is: $id, minute with most sleep: $minute")
    println("result: ${id * minute}")
}

private fun bestMinuteValue(minutes: IntArray) = minutes.max() ?: 0

private fun bestMinute(minutes: IntArray) = minutes.indexOf(bestMinuteValue(minutes))

private fun parseGuardId(event: String) =
    Regex(".*#(\\d+).*").find(event)?.groupValues?.get(1)?.toInt() ?: throw IllegalArgumentException("not matching: $event")

private data class Entry(val dateTime: LocalDateTime, val entry: String)

private val entryPattern = Regex("\\[(\\d{4}-\\d{2}-\\d{2}) (\\d{2}:\\d{2})\\] (.+)")

private fun validator(values: List<String>) = values.size == 4

private fun transformer(values: List<String>): Entry {
    var time = LocalDateTime.parse(values[1] + "T" + values[2] + ":01")
    if (time.hour == 23) time = time.plusDays(1).truncatedTo(ChronoUnit.DAYS)
    return Entry(time, values[3])
}
