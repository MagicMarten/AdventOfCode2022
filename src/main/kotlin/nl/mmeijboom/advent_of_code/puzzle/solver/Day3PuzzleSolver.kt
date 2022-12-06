package nl.mmeijboom.advent_of_code.puzzle.solver

import nl.mmeijboom.advent_of_code.tools.Log
import org.springframework.stereotype.Component
import java.lang.RuntimeException

@Component
class Day3PuzzleSolver : PuzzleSolver {

    companion object : Log() {
        const val GROUP_SIZE = 3
    }

    override fun supports(day: Int): Boolean {
        return day == 3
    }

    override fun solve(input: List<String>) {
        log.info("SOLVING PUZZLE FOR DAY THREE")
        solvePartOne(input)
        solvePartTwo(input)
    }

    private fun solvePartOne(input: List<String>) {
        var result = 0

        for(row in input) {
            val duplicateItem = findDuplicate(row)
            val priority = getTypePriority(duplicateItem)

            result += priority
        }

        log.info("PART ONE: $result")
    }

    private fun solvePartTwo(input: List<String>) {
        var result = 0

        for (i in input.indices step 3) {
            val group = input.subList(i, i + 3)
            val groupBadge = findCommonType(group)
            val priority = getTypePriority(groupBadge)

            result += priority
        }

        log.info("PART TWO: $result")
    }

    private fun findCommonType(input: List<String>): Char {
        val items = input.stream()
                .map { it.toCharArray().toHashSet() }
                .toList()

        assert(items.size == GROUP_SIZE)

        items[0].retainAll(items[1])
        items[0].retainAll(items[2])

        return items[0].stream()
                .findFirst()
                .orElseThrow()
    }

    private fun findDuplicate(input: String): Char {
        val firstHalf = input.substring(0, input.length / 2)
        val secondHalf = input.subSequence(input.length / 2, input.length)

        assert(firstHalf.length == secondHalf.length)

        for (type in firstHalf) {
            if(secondHalf.contains(type)) {
                return type
            }
        }

        throw RuntimeException("Should not happen")
    }

    private fun getTypePriority(type: Char): Int {
        val asciiCode = type.code

        if (asciiCode < 91) {
            return asciiCode - 38
        }

        return asciiCode - 96
    }

}