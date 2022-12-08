package nl.mmeijboom.advent_of_code.puzzle.solver

import nl.mmeijboom.advent_of_code.tools.Log
import org.springframework.stereotype.Component

@Component
class Day8PuzzleSolver : PuzzleSolver {

    companion object : Log()

    override fun supports(day: Int): Boolean {
        return day == 8
    }

    override fun solve(input: List<String>) {
        log.info("SOLVING PUZZLE FOR DAY EIGHT")
        solvePartOne(input)
        solvePartTwo(input)
    }

    private fun solvePartOne(input: List<String>) {
        val grid = buildGrid(input)

        assert(grid.size == 99)
        assert(grid[0].size == 99)

        var visibleCount = 0

        for(x in grid.indices) {
            for(y in 0 until grid[x].size) {
                if(!isHidden(x, y, grid)) {
                    visibleCount += 1
                }
            }
        }

        log.info("There are $visibleCount visible trees")
    }

    private fun solvePartTwo(input: List<String>) {
        val grid = buildGrid(input)

        assert(grid.size == 99)
        assert(grid[0].size == 99)

        var maxVision = 0

        for(x in grid.indices) {
            for(y in 0 until grid[x].size) {
                val vision = calculateView(x, y, grid)
                if(vision > maxVision) {
                    maxVision = vision
                }
            }
        }

        log.info("The furthest you van see is $maxVision trees")
    }

    private fun calculateView(x: Int, y: Int, grid: List<List<Int>>): Int {
        val toCheck = grid[x][y]
        return canSeePast(x - 1, y, toCheck, grid, Direction.LEFT) *
                canSeePast(x + 1, y, toCheck, grid, Direction.RIGHT) *
                canSeePast(x, y - 1, toCheck, grid, Direction.UP) *
                canSeePast(x, y + 1, toCheck, grid, Direction.DOWN)
    }

    private fun isHidden(x: Int, y: Int, grid: List<List<Int>>): Boolean {
        if(isEdge(x, y, grid)) {
            return false
        }

        val toCheck = grid[x][y]

        return isTallerOrSameHeight(x - 1, y, toCheck, grid, Direction.LEFT) &&
                isTallerOrSameHeight(x + 1, y, toCheck, grid, Direction.RIGHT) &&
                isTallerOrSameHeight(x, y - 1, toCheck, grid, Direction.UP) &&
                isTallerOrSameHeight(x, y + 1, toCheck, grid, Direction.DOWN)
    }

    private fun canSeePast(x: Int, y: Int, fromHeight: Int, grid: List<List<Int>>, direction: Direction): Int {
        if(x < 0 || y < 0 || x > grid.size - 1 || y > grid[x].size - 1) {
            return 0
        }

        val toCheck = grid[x][y]
        if(fromHeight > toCheck) {
            val next = getNeighbourLocation(x, y, direction)
            return canSeePast(next.first, next.second, fromHeight, grid, direction) + 1
        }

        return 1
    }

    private fun isTallerOrSameHeight(x: Int, y: Int, compareTo: Int, grid: List<List<Int>>, direction: Direction): Boolean {
        val toCheck = grid[x][y]
        if(toCheck >= compareTo) {
            return true
        } else if(isEdge(x, y, grid)) {
            return false
        }

        val next = getNeighbourLocation(x, y, direction)
        return isTallerOrSameHeight(next.first, next.second, compareTo, grid, direction)
    }

    private fun isEdge(x: Int, y: Int, grid: List<List<Int>>): Boolean {
        return x == 0 || x == grid.size - 1 || y == 0 || y == grid[x].size - 1
    }

    private fun getNeighbourLocation(x: Int, y: Int, direction: Direction): Pair<Int, Int> {
        return when (direction) {
            Direction.UP -> {
                Pair(x, y - 1)
            }

            Direction.DOWN -> {
                Pair(x, y + 1)
            }

            Direction.LEFT -> {
                Pair(x - 1, y)
            }

            Direction.RIGHT -> {
                Pair(x + 1, y)
            }
        }
    }

    private fun buildGrid(input: List<String>): List<List<Int>> {
        val rows = ArrayList<ArrayList<Int>>()
        for(inputRow in input) {
            val row = ArrayList<Int>()
            for(inputColumn in inputRow){
                row.add(Integer.parseInt(inputColumn.toString()))
            }

            rows.add(row)
        }

        return rows
    }
}

enum class Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
}