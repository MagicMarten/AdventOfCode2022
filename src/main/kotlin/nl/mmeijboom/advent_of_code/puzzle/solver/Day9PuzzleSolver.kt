package nl.mmeijboom.advent_of_code.puzzle.solver

import nl.mmeijboom.advent_of_code.tools.Log
import org.springframework.stereotype.Component
import java.lang.RuntimeException

@Component
class Day9PuzzleSolver : PuzzleSolver {

    companion object : Log()

    override fun supports(day: Int): Boolean {
        return day == 9
    }

    override fun solve(input: List<String>) {
        log.info("SOLVING PUZZLE FOR DAY NINE")
        solvePartOne(input)
        solvePartTwo(input)
    }

    private fun solvePartOne(input: List<String>) {
        var headPosition = Pair(0, 0)
        var tailPosition = Pair(0, 0)

        val seenPositions = HashSet<Pair<Int, Int>>()

        for(row in input) {
            val split = row.split(" ")
            val direction = split[0][0]
            val times = Integer.parseInt(split[1])

            for(i in 0 until times) {
                headPosition = moveHead(headPosition, direction)
                tailPosition = moveTailToHead(tailPosition, headPosition)

                seenPositions.add(tailPosition)
            }
        }

        log.info("Tail visited ${seenPositions.size} unique positions")
    }

    private fun solvePartTwo(input: List<String>) {
        val knots = ArrayList<Pair<Int, Int>>()
        for(i in 0 until 10) {
            knots.add(Pair(0, 0))
        }

        val seenPositions = HashSet<Pair<Int, Int>>()

        for(row in input) {
            val split = row.split(" ")
            val direction = split[0][0]
            val times = Integer.parseInt(split[1])

            for(i in 0 until times) {
                knots[0] = moveHead(knots[0], direction)

                for(k in 1 until knots.size) {
                    val current = knots[k]
                    val toFollow = knots[k - 1]

                    knots[k] = moveTailToHead(current, toFollow)
                }

                seenPositions.add(knots[9])
            }
        }

        log.info("Knot #9 visited ${seenPositions.size} unique positions")
    }

    private fun moveTailToHead(currentPosition: Pair<Int, Int>, headPosition: Pair<Int, Int>): Pair<Int, Int> {
        val cx = currentPosition.first
        val cy = currentPosition.second
        val hx = headPosition.first
        val hy = headPosition.second

        // no movement needed
        if(cx == hx && cy == hy) {
            return currentPosition
        }

        if(cx == hx && (cy == hy - 1 || cy == hy + 1)) {
            return currentPosition
        }

        if((cx == hx - 1 || cx == hx + 1) && cy == hy) {
            return currentPosition
        }

        if((cx == hx - 1 || cx == hx + 1) && (cy == hy - 1 || cy == hy + 1)) {
            return currentPosition
        }

        // vertical
        if(cx == hx) {
            if(cy < hy) {
                return Pair(cx, cy + 1)
            }

            return Pair(cx, cy - 1)
        }

        // horizontal
        if(cy == hy) {
            if(cx < hx) {
                return Pair(cx + 1, cy)
            }

            return Pair(cx - 1, cy)
        }

        // diagonal
        if(cx < hx) {
            if(cy > hy) {
                return Pair(cx + 1, cy - 1)
            }

            return Pair(cx + 1, cy + 1)
        }

        if(cy > hy) {
            return Pair(cx - 1, cy - 1)
        }

        return Pair(cx - 1, cy + 1)
    }

    private fun moveHead(position: Pair<Int, Int>, direction: Char): Pair<Int, Int> {
        val x = position.first
        val y = position.second

        return when (direction) {
            'U' -> {
                Pair(x, y - 1)
            }
            'D' -> {
                Pair(x, y + 1)
            }
            'L' -> {
                Pair(x - 1, y)
            }
            'R' -> {
                Pair(x + 1, y)
            }
            else -> {
                throw RuntimeException("Unknown direction $direction")
            }
        }
    }
}