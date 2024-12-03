fun main() {

    fun isSafe(report: List<Int>): Boolean {

        val changes = mutableListOf<Int>()
        for (index in 1..report.size - 1) {
            changes.add(report[index - 1] - report[index])
        }

        return changes.all { change -> change > 0 && change <= 3 } ||
                changes.all { change -> change < 0 && change >= -3 }
    }

    fun part1(input: List<String>): Int {

        return input.map { line -> line.split(" ").map { value -> value.toInt() } }
            .map { report -> isSafe(report) }
            .filter { safe -> safe == true }
            .count()
    }

    fun isSafeWithDampener(report: List<Int>): Boolean {

        val reportsWithOneValueRemoved = mutableListOf<List<Int>>()

        for (index in report.indices) {
            reportsWithOneValueRemoved.add(report.subList(0, index) + report.subList(index + 1, report.size))
        }

        return reportsWithOneValueRemoved.any { isSafe(it) }
    }

    fun part2(input: List<String>): Int {

        return input.map { line -> line.split(" ").map { value -> value.toInt() } }
            .map { report -> isSafeWithDampener(report) }
            .filter { safe -> safe == true }
            .count()
    }

    val testInput = readInput("Day02_test")
    val testResult1 = part1(testInput)
    println("Test result part 1: $testResult1")
    check(testResult1 == 2)

    val input = readInput("Day02")
    println("Solution part 1:    ${part1(input)}")

    val testResult2 = part2(testInput)
    println("Test result part 2: $testResult2")
    check(testResult2 == 4)

    println("Solution part 2:    ${part2(input)}")
}
