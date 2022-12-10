package nl.mmeijboom.advent_of_code.puzzle.solver

import nl.mmeijboom.advent_of_code.tools.Log
import org.springframework.stereotype.Component
import java.util.LinkedList

@Component
class Day10PuzzleSolver : PuzzleSolver {

    companion object : Log()

    override fun supports(day: Int): Boolean {
        return day == 10
    }

    override fun solve(input: List<String>) {
        log.info("SOLVING PUZZLE FOR DAY TEN")
        solvePartOne(input)
    }

    private fun solvePartOne(input: List<String>) {
        var x = 1
        var cycle = 1
        var signalStrength = 0
        var runningMutation: Int? = null

        val rows = LinkedList(input)
        while (rows.size > 0) {
            val row = rows.peek()

            if(cycle % 40 == 20 && cycle < 221) {
                println("adding signal strength for cycle #$cycle * x:$x = ${x * cycle}")
                signalStrength += x * cycle
            } else if(cycle > 220) {
                break
            }

            if(runningMutation != null) {
                x += runningMutation
                runningMutation = null
            } else {
                if(row.contains("addx")) {
                    val split = row.split(" ")
                    val mutation = Integer.parseInt(split[1])

                    runningMutation = mutation
                }

                rows.pop()
            }

            cycle++
        }

        println(cycle)

        log.info("Sum of signal strength at requested cycles is $signalStrength")
    }
}