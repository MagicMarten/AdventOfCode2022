package nl.mmeijboom.advent_of_code.puzzle.solver

import nl.mmeijboom.advent_of_code.tools.Log
import org.springframework.stereotype.Component

@Component
class Day2PuzzleSolver : PuzzleSolver {

    companion object : Log() {}

    override fun supports(day: Int): Boolean {
        return day == 2
    }

    override fun solve(input: List<String>) {
        log.info("SOLVING PUZZLE FOR DAY TWO")
        solvePartOne(input)
        solvePartTwo(input)
    }

    private fun solvePartOne(input: List<String>) {
        var score = 0

        for (row in input) {
            val split = row.split(" ")
            val myPick = split[1][0]
            val opponentPick = split[0][0]

            score += matchScores[myPick]!![opponentPick]!!
            score += pickScores[myPick]!!
        }

        log.info("PART ONE: Score when following guide: $score")
    }

    private fun solvePartTwo(input: List<String>) {
        var score = 0

        for (row in input) {
            val split = row.split(" ")
            val preferredOutcome = split[1][0]
            val opponentPick = split[0][0]
            val myPick = playForOutcome[preferredOutcome]!![opponentPick]!!

            score += matchScores[myPick]!![opponentPick]!!
            score += pickScores[myPick]!!
        }

        log.info("PART TWO: Score when following guide: $score")
    }

    // A - X = rock
    // B - Y = paper
    // C - Z = scissors

    // X - lose
    // Y - draw
    // Z - win

    private var playForOutcome: Map<Char, Map<Char, Char>> = mapOf(
            'X' to mapOf(
                    'A' to 'Z',
                    'B' to 'X',
                    'C' to 'Y'
            ),
            'Y' to mapOf(
                    'A' to 'X',
                    'B' to 'Y',
                    'C' to 'Z'
            ),
            'Z' to mapOf(
                    'A' to 'Y',
                    'B' to 'Z',
                    'C' to 'X'
            )
    )

    private var matchScores: Map<Char, Map<Char, Int>> = mapOf(
            'X' to mapOf(
                    'A' to 3,
                    'B' to 0,
                    'C' to 6
            ),
            'Y' to mapOf(
                    'A' to 6,
                    'B' to 3,
                    'C' to 0
            ),
            'Z' to mapOf(
                    'A' to 0,
                    'B' to 6,
                    'C' to 3
            )
    )

    private var pickScores: Map<Char, Int> = mapOf(
            'X' to 1,
            'Y' to 2,
            'Z' to 3
    )
}