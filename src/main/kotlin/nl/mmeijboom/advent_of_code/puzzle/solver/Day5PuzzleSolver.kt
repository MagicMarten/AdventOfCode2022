package nl.mmeijboom.advent_of_code.puzzle.solver

import nl.mmeijboom.advent_of_code.tools.Log
import org.springframework.stereotype.Component
import java.lang.RuntimeException

@Component
class Day5PuzzleSolver : PuzzleSolver {

    companion object : Log() {
        const val EMPTY_SPOT = '.'
    }

    override fun supports(day: Int): Boolean {
        return day == 5
    }

    override fun solve(input: List<String>) {
        log.info("SOLVING PUZZLE FOR DAY FIVE")
        solvePartOne(input)
        solvePartTwo(input)
    }

    private fun solvePartOne(input: List<String>) {
        val moveStartIndex = getStartingPointDefinitionEndIndex(input) + 1
        val moveRows = input.subList(moveStartIndex, input.size)

        val stack = buildStartingPoint(input)

        for(movement in moveRows) {
            val split = movement.split(" ")
            val count = Integer.valueOf(split[1])
            val from = Integer.valueOf(split[3])
            val to = Integer.valueOf(split[5])

            for(c in 0 until count) {
                val value = stack[from]?.removeLast()!!
                stack[to]?.addLast(value)
            }
        }

        val result = stack.values.map {
            if (it.size > 0) it.removeLast() else ""
        }.joinToString("")

        log.info("PART ONE: $result")
    }

    private fun solvePartTwo(input: List<String>) {
        val moveStartIndex = getStartingPointDefinitionEndIndex(input) + 1
        val moveRows = input.subList(moveStartIndex, input.size)

        val stack = buildStartingPoint(input)

        for(movement in moveRows) {
            val split = movement.split(" ")
            val count = Integer.valueOf(split[1])
            val from = Integer.valueOf(split[3])
            val to = Integer.valueOf(split[5])

            val itemsToAdd = ArrayList<Char>()

            for(c in 0 until count) {
                itemsToAdd.add(stack[from]?.removeLast()!!)
            }

            for(c in itemsToAdd.reversed()){
                stack[to]?.addLast(c)
            }
        }

        val result = stack.values.map {
            if (it.size > 0) it.removeLast() else ""
        }.joinToString("")

        log.info("PART TWO: $result")
    }

    private fun buildStartingPoint(input: List<String>): HashMap<Int, ArrayDeque<Char>> {
        val startingPointRows = getStartingPointDefinitionRows(input)
        val startingStacks: HashMap<Int, ArrayDeque<Char>> = HashMap()

        for(i in startingPointRows.size - 1 downTo 0) {
            for(j in 0 until startingPointRows[i].length) {
                val index = j + 1
                val c = startingPointRows[i][j]
                if(c != EMPTY_SPOT) {
                    if(!startingStacks.contains(index)) {
                        startingStacks[index] = ArrayDeque()
                    }

                    startingStacks[index]?.addLast(c)
                }
            }
        }

        return startingStacks
    }

    private fun getStartingPointDefinitionRows(input: List<String>): List<String> {
        val startIndex = getStartingPointDefinitionEndIndex(input) - 1

        return input.subList(0, startIndex).map {
            parseStartingRow(it)
        }
    }

    private fun parseStartingRow(row: String): String {
        return row
                .replace("    ", ".")
                .replace("[", "")
                .replace("]", "")
                .replace(" ", "")
    }

    private fun getStartingPointDefinitionEndIndex(input: List<String>): Int {
        for(i in 0..input.size) {
            if(input[i].isBlank()) {
                return i
            }
        }

        throw RuntimeException("No starting point definition found")
    }
}