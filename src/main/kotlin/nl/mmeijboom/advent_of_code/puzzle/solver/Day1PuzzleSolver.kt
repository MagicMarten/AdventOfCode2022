package nl.mmeijboom.advent_of_code.puzzle.solver

import nl.mmeijboom.advent_of_code.tools.Log
import org.springframework.stereotype.Component

@Component
class Day1PuzzleSolver : PuzzleSolver {
    companion object : Log() {}

    override fun supports(day: Int): Boolean {
        return day == 1
    }

    override fun solve(input: List<String>) {
        log.info("SOLVING PUZZLE FOR DAY ONE")
        solvePartOne(input)
        solvePartTwo(input)
    }

    fun solvePartOne(input: List<String>) {
        var elfIndex = 0
        var elfCounter = 0

        val elves: HashMap<Int, Int> = HashMap()

        for (row in input) {
            if (row.isBlank()) {
                elves[elfIndex] = elfCounter

                elfIndex++
                elfCounter = 0
            } else {
                elfCounter += Integer.parseInt(row)
            }
        }

        val topElfCapacity = elves.values.maxOf { it }

        log.info("Most weight carries by an elf: $topElfCapacity")
    }

    fun solvePartTwo(input: List<String>) {
        var elfIndex = 0
        var elfCounter = 0

        val elves: HashMap<Int, Int> = HashMap()

        for (row in input) {
            if (row.isBlank()) {
                elves[elfIndex] = elfCounter

                elfIndex++
                elfCounter = 0
            } else {
                elfCounter += Integer.parseInt(row)
            }
        }

        val topThreeElvesCapacity = elves.values.sortedDescending().subList(0, 3).sum()

        log.info("Most weight carries by an elf: $topThreeElvesCapacity")
    }
}