fun main() {

    fun blink(stones: List<Long>): List<Long> {

        val result = mutableListOf<Long>()

        stones.forEach { stone ->
            if (stone == 0L) {
                result.add(1)
            } else if (stone.toString().length % 2 == 0) {
                val middleIndex = stone.toString().length / 2
                result.add(stone.toString().substring(0, middleIndex).toLong())
                result.add(stone.toString().substring(middleIndex).toLong())
            } else {
                result.add(stone * 2024)
            }
        }

        return result
    }

    fun blink2(stones: MutableList<Long>) {
        for (index in stones.indices) {
            if (stones[index] != -1L) {
                if (stones[index] == 0L) {
                    stones[index] = 1
                } else if (stones[index].toString().length % 2 == 0) {
                    val stoneAsString = stones[index].toString()
                    val middleIndex = stoneAsString.length / 2
                    stones[index] = -1
                    stones.add(stoneAsString.substring(0, middleIndex).toLong())
                    stones.add(stoneAsString.substring(middleIndex).toLong())
                } else {
                    stones[index] = stones[index] * 2024
                }
            }
        }
    }

    fun part1(input: List<String>): Int {

        val initial = input[0].split(" ").map { it.toLong() }

        var result = initial

        var blinks = 0
        repeat(25) {
            blinks++
            result = blink(result)
        }

        return result.size
    }

    data class Stones(val stones: MutableMap<Long, Long> = mutableMapOf()) {
        fun add(engravedNumber: Long, countFromLastBlink: Long) {
            val currentValue = stones.getOrDefault(engravedNumber, 0L)
            stones[engravedNumber] = currentValue + countFromLastBlink
        }
    }

    fun blink3(stones: Stones): Stones {
        val result = Stones()
        stones.stones.forEach { stone ->
            when {
                stone.key == 0L -> {
                    result.add(1L, stone.value)
                }

                stone.key.toString().length % 2 == 0 -> {
                    val (first, second) = stone.key.toString().chunked(stone.key.toString().length / 2)
                    result.add(first.toLong(), stone.value)
                    result.add(second.toLong(), stone.value)
                }

                else -> {
                    result.add(stone.key * 2024, stone.value)
                }
            }
        }

        return result
    }

    fun part2(input: List<String>, blinks: Int): Long {

        var result = Stones()

        input[0].split(" ")
            .map { it.toLong() }
            .groupingBy { it -> it }
            .eachCount()
            .forEach { (engravedNumber, initialCount) ->
                result.add(engravedNumber, initialCount.toLong())
            }

        repeat(blinks) {
            result = blink3(result)
        }

        return result.stones.values.sum()
    }

    val testInput = readInput("Day11_test")
    val testResult1 = part1(testInput)
    println("Test result part 1: $testResult1")
    check(testResult1 == 55312)

    val input = readInput("Day11")
    println("Solution part 1:    ${part1(input)}")

    val testResult2 = part2(testInput, 6)
    println("Test result part 2: $testResult2")
    check(testResult2 == 22L)
    val testResult3 = part2(testInput, 25)
    println("Test result part 2: $testResult3")
    check(testResult3 == 55312L)
    println("Solution part 2:    ${part2(input, 75)}")
}
