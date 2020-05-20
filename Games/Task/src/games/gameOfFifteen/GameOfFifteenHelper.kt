package games.gameOfFifteen

/*
 * This function should return the parity of the permutation.
 * true - the permutation is even
 * false - the permutation is odd
 * https://en.wikipedia.org/wiki/Parity_of_a_permutation

 * If the game of fifteen is started with the wrong parity, you can't get the correct result
 *   (numbers sorted in the right order, empty cell at last).
 * Thus the initial permutation should be correct.
 */
fun isEven(permutation: List<Int>): Boolean {
    val original = permutation.sorted()
    val visited = IntArray(permutation.size)
    repeat(permutation.size) { i -> visited[i] = 0 }
    var ctr = 0
    var index = visited.indexOfFirst { it == 0 }

    while (index != -1) {
        val start = permutation[index]
        var current: Int
        visited[index] = 1
        while (start != original[index]) {
            ctr++
            current = original[index]
            index = permutation.indexOf(current)
            visited[index] = 1
        }
        index = visited.indexOfFirst { it == 0 }
    }
    return ctr % 2 == 0
}