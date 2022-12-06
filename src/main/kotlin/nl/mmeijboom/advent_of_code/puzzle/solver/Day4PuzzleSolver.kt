package nl.mmeijboom.advent_of_code.puzzle.solver

import nl.mmeijboom.advent_of_code.tools.Log
import org.springframework.stereotype.Component

@Component
class Day4PuzzleSolver : PuzzleSolver {

    companion object : Log() {}

    override fun supports(day: Int): Boolean {
        return day == 4
    }

    override fun solve(input: List<String>) {
        log.info("SOLVING PUZZLE FOR DAY FOUR")
        solvePartOne(input)
        solvePartTwo(input)
    }

    private fun solvePartOne(input: List<String>) {
        val result = input.stream().map { row ->
            val split = row.split(",")
            val firstSection = Section(split[0])
            val secondSection = Section(split[1])

            if(firstSection.containsCompletely(secondSection) || secondSection.containsCompletely(firstSection)) {
                1
            } else {
                0
            }
        }.toList().sum()

        log.info("PART ONE: $result")
    }

    private fun solvePartTwo(input: List<String>) {
        val result = input.stream().map { row ->
            val split = row.split(",")
            val firstSection = Section(split[0])
            val secondSection = Section(split[1])

            if(firstSection.contains(secondSection) || secondSection.contains(firstSection)) {
                1
            } else {
                0
            }
        }.toList().sum()

        log.info("PART TWO: $result")
    }
}

class Section(input: String) {

    private val start: Int
    private val end: Int

    init {
        val split = input.split("-")
        this.start = Integer.parseInt(split[0])
        this.end = Integer.parseInt(split[1])
    }

    fun contains(toCheck: Section): Boolean {
        return this.start <= toCheck.end && this.end >= toCheck.start
    }

    fun containsCompletely(toCheck: Section): Boolean {
        return this.start <= toCheck.start && this.end >= toCheck.end
    }
}