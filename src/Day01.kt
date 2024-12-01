import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {

        val list1 = mutableListOf<Int>()
        val list2 = mutableListOf<Int>()

        input.map { it -> it.split("   ") }
            .forEach { it ->
                list1.add(it[0].toInt())
                list2.add(it[1].toInt())
            }

        list1.sort()
        list2.sort()

        var result = 0
        for (i in list1.indices) {
            result += abs(list1[i] - list2[i])
        }

        return result
    }

    fun part2(input: List<String>): Int {
        val list1 = mutableListOf<Int>()
        val list2 = mutableListOf<Int>()

        input.map { it -> it.split("   ") }
            .forEach { it ->
                list1.add(it[0].toInt())
                list2.add(it[1].toInt())
            }

        var result = 0
        for (i in list1.indices) {
            result += list1[i] * list2.count { it -> it == list1[i] }
        }

        return result
    }

    val testInput = readInput("Day01_test")
    val testResult1 = part1(testInput)
    println("Test result part 1: $testResult1")
    check(testResult1 == 11)

    val input = readInput("Day01")
    println("Solution part 1:    ${part1(input)}")

    val testResult2 = part2(testInput)
    println("Test result part 2: $testResult2")
    check(testResult2 == 31)

    println("Solution part 1:    ${part2(input)}")
}
