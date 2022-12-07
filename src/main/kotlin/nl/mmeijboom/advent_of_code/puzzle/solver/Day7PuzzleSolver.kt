package nl.mmeijboom.advent_of_code.puzzle.solver

import nl.mmeijboom.advent_of_code.tools.Log
import org.springframework.stereotype.Component

@Component
class Day7PuzzleSolver : PuzzleSolver {

    companion object : Log()

    override fun supports(day: Int): Boolean {
        return day == 7
    }

    override fun solve(input: List<String>) {
        Day6PuzzleSolver.log.info("SOLVING PUZZLE FOR DAY SEVEN")
        solvePartOne(input)
        solvePartTwo(input)
    }

    private fun solvePartOne(input: List<String>) {
        val rootFolder = processSearch(input)
        val fileList = unpackDir(rootFolder)
        val dirList = fileList.filterIsInstance<Dir>()

        log.info("Total size: ${rootFolder.getSize()}")

        val result = dirList.sumOf {
            val size = it.getSize()
            if (size <= 100000) size else 0
        }

        log.info("Size within limit: $result")
    }

    private fun solvePartTwo(input: List<String>) {
        val rootFolder = processSearch(input)
        val fileList = unpackDir(rootFolder)
        val dirList = fileList.filterIsInstance<Dir>()

        log.info("Total size: ${rootFolder.getSize()}")

        val freeSpace = 70000000 - rootFolder.getSize()
        val requiredSize = 30000000 - freeSpace

        var result = 70000000

        for(dir in dirList.sortedBy { it.getSize() }) {
            if(dir.getSize() in requiredSize until result) {
                result = dir.getSize()
            }
        }

        log.info("Smallest possible dir to remove: $result")
    }

    private fun unpackDir(input: Dir): List<DriveElement> {
        return input.getChildren().map {
            val result = arrayListOf(it)

            if(it is Dir) {
                result.addAll(unpackDir(it))
            }

            result
        }.flatten()
    }

    private fun processSearch(input: List<String>): Dir {
        val root = Dir(null, "/")
        var current = root

        for(line in input) {
            if(line == "$ ls") continue
            if(line == "$ cd /") {
                current = root
                continue
            }
            if(line == "$ cd ..") {
                val parent = current.getParent()
                if (parent != null) {
                    current = parent
                }
                continue
            }
            if(line.contains("$ cd ")) {
                val commandSplit = line.split(" ")
                val dirName = commandSplit[2]
                current = current.getChild(dirName) as Dir
                continue
            }

            val listingSplit = line.split(" ")
            val newItem = if(listingSplit[0] == "dir") {
                Dir(current, listingSplit[1])
            } else {
                File(current, listingSplit[1], Integer.parseInt(listingSplit[0]))
            }

            current.addChild(newItem)
        }

        return root
    }
}

class Dir(private val parent: Dir?, private val name: String) : DriveElement {

    private val children: ArrayList<DriveElement> = ArrayList()

    override fun getName(): String {
        return this.name
    }

    override fun getSize(): Int {
        return children.sumOf { it.getSize() }
    }

    override fun getParent(): Dir? {
        return this.parent
    }

    fun addChild(child: DriveElement) {
        this.children.add(child)
    }

    fun getChild(name: String): DriveElement {
        return children.find { it.getName() == name }!!
    }

    fun getChildren(): List<DriveElement> {
        return children
    }
}

class File(private val parent: Dir?, private val name: String, private val size: Int) : DriveElement {
    override fun getName(): String {
        return this.name
    }

    override fun getSize(): Int {
        return this.size
    }

    override fun getParent(): Dir? {
        return this.parent
    }
}

interface DriveElement {
    fun getName(): String
    fun getSize(): Int
    fun getParent(): Dir?
}