fun main() {

    fun calculate(expression: String): Int {
        return Regex("""\d{1,3}""").findAll(expression).toList()
            .map { result -> result.value.toInt() }
            .reduce { a, b -> a * b }
    }

    fun findExpressions(line: String): List<String> {
        return Regex("""mul[(]\d{1,3},\d{1,3}[)]""")
            .findAll(line).toList()
            .map { matchResult -> matchResult.value }
    }

    fun part1(input: List<String>): Int {

        val expressionLines = input.map { line -> findExpressions(line) }

        return expressionLines.map { expressionLine ->
            expressionLine.map { expression -> calculate(expression) }.sum()
        }.sum()
    }

    fun findOccurences(line: String, regex: String): List<IntRange> {
        return Regex(regex).findAll(line).toList().map { result -> result.range }
    }

    fun findActiveCodeParts(line: String): List<String> {

        println(line)

        val deactivations = findOccurences(line, "don't[(][)]").toMutableList()
        val activations = findOccurences(line, "do[(][)]").toMutableList()

        var index = 0
        var active = true
        var deactivationIndex = 0
        var activationIndex = 0
        val result = mutableListOf<String>()

        while (deactivationIndex < deactivations.size || activationIndex < activations.size) {
            if (active) {
                val nextDeactivation = deactivations[deactivationIndex]
                result.add(line.substring(index, nextDeactivation.start))
                index = nextDeactivation.endInclusive + 1
                deactivationIndex++
                active = false
            } else {
                val nextActivation = activations[activationIndex]
                index = nextActivation.endInclusive + 1
                activationIndex++
                active = true

                if (deactivationIndex == deactivations.size && activationIndex == activations.size) {
                    result.add(line.substring(index, line.length))
                }
            }
        }

        return result
    }

    fun findActiveCodePartsLists(line: String): List<String> {

        println(line)

        val deactivations = findOccurences(line, "don't[(][)]").toMutableList()
        val activations = findOccurences(line, "do[(][)]").toMutableList()

        println("Deactivations: $deactivations")
        println("Activations: $activations")

        var index = 0
        var active = true
        val result = mutableListOf<String>()

        while (activations.isNotEmpty() && activations.isNotEmpty() && activations.first().first < deactivations.first().first) {
            index = activations.removeFirst().endInclusive + 1
        }

        println("Start index: $index")

        while (deactivations.isNotEmpty() || activations.isNotEmpty()) {
            if (active) {
                if (deactivations.isNotEmpty()) {
                    var nextDeactivation = deactivations.removeFirst()
                    println("Next deactivation: $nextDeactivation")
                    result.add(line.substring(index, nextDeactivation.start))
                    index = nextDeactivation.endInclusive + 1
                    active = false

                    while (deactivations.isNotEmpty() && activations.isNotEmpty() && deactivations.first().first < activations.first().first) {
                        deactivations.removeFirst()
                    }
                }
            } else {
                if (activations.isNotEmpty()) {
                    var nextActivation = activations.removeFirst()
                    println("Next activation: $nextActivation")
                    index = nextActivation.endInclusive + 1
                    active = true

                    while (activations.isNotEmpty() && deactivations.isNotEmpty() && activations.first().first < deactivations.first().first) {
                        activations.removeFirst()
                    }

                    if (deactivations.isEmpty() && activations.isEmpty()) {
                        result.add(line.substring(index, line.length))
                    }
                }
            }
        }

        return result
    }

    fun findActiveCodeParts2(line: String): List<String> {
        return Regex("""(do[(][)])(.*?)(don't[(][)])""").findAll(line).toList()
            .map { match -> match.groupValues[2] }
    }

    fun part2(input: List<String>): Int {

        return input.joinToString().map { program ->
            findActiveCodeParts2("do()" + program + "don't()").map { activeCodePart ->
                findExpressions(activeCodePart).map { expression ->
                    calculate(expression)
                }.sum()
            }.sum()
        }.sum()
    }

    val testInput = readInput("Day03_test")
    val testResult1 = part1(testInput)
    println("Test result part 1: $testResult1")
    check(testResult1 == 161)

    val input = readInput("Day03")
    println("Solution part 1:    ${part1(input)}")

    val testResult2 = part2(listOf("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))"))
    println("Test result part 2: $testResult2")
    check(testResult2 == 48)

    println("Solution part 2:    ${part2(input)}")
}
