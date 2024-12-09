fun main() {

    fun getUncompressed(diskMap: String): List<Int> {
        val result = mutableListOf<Int>()

        var isFile = true
        var fileId = 0
        for (character in diskMap) {
            repeat(character.toString().toInt()) {
                if (isFile) {
                    result.add(fileId)
                } else {
                    result.add(-1)
                }
            }

            if (isFile) {
                fileId++
                isFile = false
            } else {
                isFile = true
            }
        }

        return result
    }

    fun compress(uncompressed: List<Int>): List<Int> {
        var result = uncompressed.toMutableList()
        var currentIndex = 0
        var lastIndex = result.size - 1

        do {
            while (result[currentIndex] != -1) {
                currentIndex++
            }
            while (result[lastIndex] == -1) {
                lastIndex--
            }
            if (currentIndex < lastIndex) {
                result[currentIndex] = result[lastIndex]
                result[lastIndex] = -1
            }
        } while (currentIndex < lastIndex)

        return result
    }

    fun checksum(compressed: List<Int>): Long {
        var result = 0L
        for (index in compressed.filter { it != -1 }.indices) {
            result += index * compressed[index]
        }
        return result
    }

    fun part1(input: List<String>): Long {

        val uncompressed = getUncompressed(input[0])

        val compressed = compress(uncompressed)

        return checksum(compressed)
    }

    fun part2(input: List<String>): Int {

        return input.size
    }

    val testInput = readInput("Day09_test")
    val testResult1 = part1(testInput)
    println("Test result part 1: $testResult1")
    check(testResult1 == 1928L)

    val input = readInput("Day09")
    println("Solution part 1:    ${part1(input)}")
//
//    val testResult2 = part2(testInput)
//    println("Test result part 2: $testResult2")
//    check(testResult2 == 123)
//
//    println("Solution part 2:    ${part2(input)}")
}
