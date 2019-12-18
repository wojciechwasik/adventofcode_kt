package aoc2019

import util.*
import kotlin.math.abs
import kotlin.math.sign

//
// Total energy after 1000 steps: 9958
// Cycles: x=28482, y=231614, z=193052, combined=318382803780324
//

fun main(args: Array<String>) {

    problem1()

    problem2()
}

private fun problem1() {
    // since calculations in every axis are independent:
    // - each nested array represents single axis
    // - moons are represented by indices into nested arrays

    // x, y, z
    val positions = arrayListOf(
            arrayListOf(7, -2, 12, 5),
            arrayListOf(10, 7, 5, -8),
            arrayListOf(17, 0, 12, 6)
    )

    // dx, dy, dz
    val velocities = arrayListOf(
            arrayListOf(0, 0, 0, 0),
            arrayListOf(0, 0, 0, 0),
            arrayListOf(0, 0, 0, 0)
    )

    for (i in 1..1000) {
        for (axis in 0..2) {
            tick(positions[axis], velocities[axis])
        }
    }

    println("Total energy after 1000 steps: ${getEnergy(positions, velocities)}")
}

private fun problem2() {
    // since changes are deterministic and we assume there is a cycle the starting state must repeat
    // since calculations in every axis are independent we can determine every cycle independently and calculate when
    // all 3 will match

    // positions
    val px = arrayListOf(7, -2, 12, 5)
    val py = arrayListOf(10, 7, 5, -8)
    val pz = arrayListOf(17, 0, 12, 6)

    // velocities
    val vx = arrayListOf(0, 0, 0 , 0)
    val vy = arrayListOf(0, 0, 0 , 0)
    val vz = arrayListOf(0, 0, 0 , 0)

    // cycles
    val tx = findCycle(px, vx)
    val ty = findCycle(py, vy)
    val tz = findCycle(pz, vz)

    val xy = tx / gcd(tx, ty) * ty
    val xyz = xy / gcd(xy, tz) * tz
    println("Cycles: x=${tx}, y=${ty}, z=${tz}, combined=${xyz}")
}

private fun findCycle(pos: List<Int>, vel: List<Int>): Long {
    val newPos = pos.toMutableList()
    val newVel = vel.toMutableList()

    var t = 0L
    while (true) {
        tick(newPos, newVel)
        t++
        if (newPos == pos && newVel == vel) return t
    }
}

private fun tick(pos: MutableList<Int>, vel: MutableList<Int>): Unit {
    updateVelocity(pos, vel, 0, 1)
    updateVelocity(pos, vel, 0, 2)
    updateVelocity(pos, vel, 0, 3)
    updateVelocity(pos, vel, 1, 2)
    updateVelocity(pos, vel, 1, 3)
    updateVelocity(pos, vel, 2, 3)

    pos[0] += vel[0]
    pos[1] += vel[1]
    pos[2] += vel[2]
    pos[3] += vel[3]
}

private fun updateVelocity(pos: MutableList<Int>, vel: MutableList<Int>, i: Int, j: Int): Unit {
    val update = pos[i].compareTo(pos[j]).sign
    vel[i] -= update
    vel[j] += update
}

private fun getEnergy(positions: List<List<Int>>, velocities: List<List<Int>>) =
        (0..3).map {
            (abs(positions[0][it]) + abs(positions[1][it]) + abs(positions[2][it])) * (abs(velocities[0][it]) + abs(velocities[1][it]) + abs(velocities[2][it]))
        }.sum()
