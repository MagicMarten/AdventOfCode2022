package nl.mmeijboom.advent_of_code.puzzle.solver

import nl.mmeijboom.advent_of_code.tools.Log
import org.springframework.stereotype.Component

@Component
class Day6PuzzleSolver : PuzzleSolver {

    companion object : Log()

    override fun supports(day: Int): Boolean {
        return day == 6
    }

    override fun solve(input: List<String>) {
        log.info("SOLVING PUZZLE FOR DAY SIX")
        solvePartOne(input[0])
        solvePartTwo(input[0])
    }

    private fun solvePartOne(input: String) {
        for(i in input.indices - 4) {
            val endIndex = i + 4
            val subList = input.subSequence(i, endIndex).toList()
            if(subList.size == subList.distinct().count()) {
                log.info("PART ONE: $endIndex")
                return
            }
        }
    }

    private fun solvePartTwo(input: String) {
        for(i in input.indices - 14) {
            val endIndex = i + 14
            val subList = input.subSequence(i, endIndex).toList()
            if(subList.size == subList.distinct().count()) {
                log.info("PART TWO: $endIndex")
                return
            }
        }
    }
}