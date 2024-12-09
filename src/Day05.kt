fun main() {

    fun orderingRules(input: List<String>): List<Pair<Int, Int>> {
        return input.subList(0, input.indexOf(""))
            .map { line -> Pair(line.substringBefore("|").toInt(), line.substringAfter("|").toInt()) }
    }

    fun updates(input: List<String>): List<List<Int>> {
        return input.subList(input.indexOf("") + 1, input.size)
            .map { line -> line.split(",").map { page -> page.toInt() } }
    }

    fun checkPagesBefore(currentPage: Int, pagesBefore: List<Int>, rules: List<Pair<Int, Int>>): Boolean {

        val applicableRules = rules.filter { rule -> rule.second == currentPage }
        return pagesBefore.all { page -> applicableRules.any { rule -> rule.first == page } }
    }

    fun checkPagesAfter(currentPage: Int, pagesAfter: List<Int>, rules: List<Pair<Int, Int>>): Boolean {

        val applicableRules = rules.filter { rule -> rule.first == currentPage }
        return pagesAfter.all { page -> applicableRules.any { rule -> rule.second == page } }
    }

    fun part1(input: List<String>): Int {

        val rules = orderingRules(input)
        val updates = updates(input)

        val correctUpdates = updates.filter { update ->
            update.all { page ->
                checkPagesBefore(page, update.subList(0, update.indexOf(page)), rules)
                        && checkPagesAfter(page, update.subList(update.indexOf(page) + 1, update.size), rules)
            }
        }

        return correctUpdates.map { pages -> pages[pages.size / 2] }.sum()
    }

    fun part2(input: List<String>): Int {

        val rules = orderingRules(input)
        val updates = updates(input)

        println(rules)
        println(updates)

        val incorrectUpdates = updates.filter { update ->
            update.any { page ->
                !checkPagesBefore(page, update.subList(0, update.indexOf(page)), rules)
                        || !checkPagesAfter(page, update.subList(update.indexOf(page) + 1, update.size), rules)
            }
        }

        println(incorrectUpdates)

        val fixedUpdates = incorrectUpdates.map { update ->
            {
                val fixedUpdate = update.toMutableList()

                println(update.windowed(2))

                for (index in 0..update.lastIndex - 1) {
                    if (rules.contains(Pair(update[index + 1], update[index]))) {
                        fixedUpdate[index] = update[index + 1]
                        fixedUpdate[index + 1] = update[index]
                    }
                }

                fixedUpdate
            }.invoke()
        }

        println(fixedUpdates)

        return fixedUpdates.map { pages -> pages[pages.size / 2] }.sum()
    }

    val testInput = readInput("Day05_test")
    val testResult1 = part1(testInput)
    println("Test result part 1: $testResult1")
    check(testResult1 == 143)

    val input = readInput("Day05")
    println("Solution part 1:    ${part1(input)}")

    val testResult2 = part2(testInput)
    println("Test result part 2: $testResult2")
    check(testResult2 == 123)

//    println("Solution part 2:    ${part2(input)}")
}
